package com.wss.module.main.ui.view.ca;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "WeakerAccess", "unused", "deprecation" })
public class SenseCamera {
    public static final int CAMERA_FACING_BACK = Camera.CameraInfo.CAMERA_FACING_BACK;

    public static final int CAMERA_FACING_FRONT = Camera.CameraInfo.CAMERA_FACING_FRONT;

    private static final int DUMMY_TEXTURE_NAME = 100;

    private static final float ASPECT_RATIO_TOLERANCE = 0.01f;

    private Context mContext;

    private final Object mCameraLock = new Object();

    private Camera mCamera;

    private int mFacing = CAMERA_FACING_BACK;

    private int mRotation;

    // Default preview size, Reference https://stackoverflow.com/a/21676309.
    private Size mPreviewSize = new Size(640, 480);

    private float mRequestedFps = 120.0f;
    private int mRequestedPreviewWidth = 640;
    private int mRequestedPreviewHeight = 480;

    private Map<byte[], ByteBuffer> mBytesToByteBuffer = new HashMap<>();

    private Camera.PreviewCallback mPreviewCallback;

    public static class Builder {
        private final SenseCamera mSenseCamera = new SenseCamera();

        /**
         * Camera Builder.
         */
        public Builder(final Context context) {
            if (context == null) {
                throw new IllegalArgumentException("No context supplied.");
            }

            this.mSenseCamera.mContext = context;
        }

        /**
         * set requested fps.
         */
        public Builder setRequestedFps(final float fps) {
            if (fps <= 0) {
                throw new IllegalArgumentException("Invalid fps: " + fps);
            }

            this.mSenseCamera.mRequestedFps = fps;

            return this;
        }

        /**
         * set requested preview size.
         */
        public Builder setRequestedPreviewSize(final int width, final int height) {
            final int max = 1000000;

            if ((width <= 0) || (width > max) || (height <= 0) || (height > max)) {
                throw new IllegalArgumentException("Invalid preview size: " + width + "x" + height);
            }

            this.mSenseCamera.mRequestedPreviewWidth = width;
            this.mSenseCamera.mRequestedPreviewHeight = height;

            return this;
        }

        /**
         * set camera facing.
         */
        public Builder setFacing(final int facing) {
            if ((facing != CAMERA_FACING_BACK) && (facing != CAMERA_FACING_FRONT)) {
                throw new IllegalArgumentException("Invalid camera: " + facing);
            }

            this.mSenseCamera.mFacing = facing;

            return this;
        }

        /**
         * build camera.
         */
        public SenseCamera build() {
            return this.mSenseCamera;
        }
    }

    /**
     * release.
     */
    public void release() {
        synchronized (this.mCameraLock) {
            this.stop();
        }
    }

    /**
     * start.
     */
    @SuppressLint("MissingPermission")
    public SenseCamera start(final SurfaceHolder surfaceHolder) throws IOException, RuntimeException {
        synchronized (this.mCameraLock) {
            if (this.mCamera != null) {
                return this;
            }

            this.mCamera = createCamera();
            this.mCamera.setPreviewDisplay(surfaceHolder);
            this.mCamera.startPreview();
        }
        return this;
    }

    /**
     * stop.
     */
    public void stop() {
        synchronized (this.mCameraLock) {
            this.mBytesToByteBuffer.clear();

            if (this.mCamera != null) {
                this.mCamera.stopPreview();
                this.mCamera.setPreviewCallbackWithBuffer(null);
                try {
                    this.mCamera.setPreviewDisplay(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.mCamera.release();
                this.mCamera = null;
            }
        }
    }

    /**
     * get preview size.
     */
    public Size getPreviewSize() {
        return this.mPreviewSize;
    }

    /**
     * get camera facing.
     */
    public int getCameraFacing() {
        return this.mFacing;
    }

    private SenseCamera() {
    }

    private Camera createCamera() throws RuntimeException {
        final int requestedCameraId = SenseCamera.getIdForRequestedCamera(this.mFacing);

        if (requestedCameraId == -1) {
            throw new IllegalStateException("Could not find requested camera.");
        }

        final Camera camera = Camera.open(requestedCameraId);

        if (camera == null) {
            throw new IllegalStateException("Unknown camera error.");
        }

        final SizePair sizePair =
                SenseCamera.selectSizePair(camera, this.mRequestedPreviewWidth, this.mRequestedPreviewHeight);

        if (sizePair == null) {
            throw new IllegalStateException("Could not find suitable preview size.");
        }

        final Size pictureSize = sizePair.pictureSize();

        this.mPreviewSize = sizePair.previewSize();

        final int[] previewFpsRange = selectPreviewFpsRange(camera, this.mRequestedFps);

        if (previewFpsRange == null) {
            throw new IllegalStateException("Could not find suitable preview frames per second range.");
        }

        final Camera.Parameters parameters = camera.getParameters();

        if (pictureSize != null) {
            parameters.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());
        }

        parameters.setPreviewSize(this.mPreviewSize.getWidth(), this.mPreviewSize.getHeight());
        parameters.setPreviewFpsRange(previewFpsRange[Camera.Parameters.PREVIEW_FPS_MIN_INDEX],
                previewFpsRange[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]);
        parameters.setPreviewFormat(ImageFormat.NV21);

        this.setRotation(camera, parameters, requestedCameraId);

        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }

        camera.setParameters(parameters);

        camera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                camera.addCallbackBuffer(SenseCamera.this.mBytesToByteBuffer.get(data).array());

                if (SenseCamera.this.mPreviewCallback != null) {
                    SenseCamera.this.mPreviewCallback.onPreviewFrame(
                            SenseCamera.this.mBytesToByteBuffer.get(data).array(), camera);
                }
            }
        });
        camera.addCallbackBuffer(this.createPreviewBuffer(this.mPreviewSize));

        return camera;
    }

    /**
     * set preview Callback.
     * @param callback callback.
     */
    public void setOnPreviewFrameCallback(final Camera.PreviewCallback callback) {
        this.mPreviewCallback = callback;
    }

    private static int getIdForRequestedCamera(final int facing) {
        final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == facing) {
                return i;
            }
        }
        return -1;
    }

    private static SizePair selectSizePair(final Camera camera, final int desiredWidth, final int desiredHeight) {
        final List<SizePair> validPreviewSizes = generateValidPreviewSizeList(camera);

        SizePair selectedPair = null;
        int minDiff = Integer.MAX_VALUE;
        for (SizePair sizePair : validPreviewSizes) {
            final Size size = sizePair.previewSize();
            int diff = Math.abs(size.getWidth() - desiredWidth) + Math.abs(size.getHeight() - desiredHeight);
            if (diff < minDiff) {
                selectedPair = sizePair;
                minDiff = diff;
            }
        }

        return selectedPair;
    }

    private static class SizePair {
        private Size mPreview;
        private Size mPicture;

        public SizePair(final Camera.Size previewSize, final Camera.Size pictureSize) {
            mPreview = new Size(previewSize.width, previewSize.height);
            if (pictureSize != null) {
                mPicture = new Size(pictureSize.width, pictureSize.height);
            }
        }

        public Size previewSize() {
            return this.mPreview;
        }

        @SuppressWarnings("unused")
        public Size pictureSize() {
            return this.mPicture;
        }
    }

    private static List<SizePair> generateValidPreviewSizeList(final Camera camera) {
        final Camera.Parameters parameters = camera.getParameters();
        final List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        final List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
        final List<SizePair> validPreviewSizes = new ArrayList<>();
        for (final Camera.Size previewSize : supportedPreviewSizes) {
            final float previewAspectRatio = (float) previewSize.width / (float) previewSize.height;

            for (Camera.Size pictureSize : supportedPictureSizes) {
                float pictureAspectRatio = (float) pictureSize.width / (float) pictureSize.height;
                if (Math.abs(previewAspectRatio - pictureAspectRatio) < ASPECT_RATIO_TOLERANCE) {
                    validPreviewSizes.add(new SizePair(previewSize, pictureSize));
                    break;
                }
            }
        }

        if (validPreviewSizes.isEmpty()) {
            for (Camera.Size previewSize : supportedPreviewSizes) {
                validPreviewSizes.add(new SizePair(previewSize, null));
            }
        }

        return validPreviewSizes;
    }

    private int[] selectPreviewFpsRange(final Camera camera, final float desiredPreviewFps) {
        final int desiredPreviewFpsScaled = (int) (desiredPreviewFps * 1000.0f);

        int[] selectedFpsRange = null;
        int minDiff = Integer.MAX_VALUE;
        List<int[]> previewFpsRangeList = camera.getParameters().getSupportedPreviewFpsRange();
        for (int[] range : previewFpsRangeList) {
            int deltaMin = desiredPreviewFpsScaled - range[Camera.Parameters.PREVIEW_FPS_MIN_INDEX];
            int deltaMax = desiredPreviewFpsScaled - range[Camera.Parameters.PREVIEW_FPS_MAX_INDEX];
            int diff = Math.abs(deltaMin) + Math.abs(deltaMax);
            if (diff < minDiff) {
                selectedFpsRange = range;
                minDiff = diff;
            }
        }

        return selectedFpsRange;
    }

    private void setRotation(final Camera camera, final Camera.Parameters parameters, final int cameraId) {
        final WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int degrees = 0;
        int rotation = windowManager != null ? windowManager.getDefaultDisplay().getRotation() : Surface.ROTATION_0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                break;
        }

        final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);

        int angle;
        int displayAngle;
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            angle = (cameraInfo.orientation + degrees) % 360;
            displayAngle = (360 - angle) % 360; // compensate for it being mirrored
        } else {  // back-facing
            angle = (cameraInfo.orientation - degrees + 360) % 360;
            displayAngle = angle;
        }

        this.mRotation = angle / 90;

        camera.setDisplayOrientation(displayAngle);
        parameters.setRotation(angle);
    }

    /**
     * get Rotation Degrees.
     * @return Degrees.
     */
    public int getRotationDegrees() {
        return this.mRotation * 90;
    }

    private byte[] createPreviewBuffer(Size previewSize) {
        final int bitsPerPixel = ImageFormat.getBitsPerPixel(ImageFormat.NV21);
        final long sizeInBits = previewSize.getHeight() * previewSize.getWidth() * bitsPerPixel;
        final int bufferSize = (int) Math.ceil(sizeInBits / 8.0d) + 1;

        final byte[] byteArray = new byte[bufferSize];
        final ByteBuffer buffer = ByteBuffer.wrap(byteArray);

        if (!buffer.hasArray() || (buffer.array() != byteArray)) {
            throw new IllegalStateException("Failed to create valid buffer for camera.");
        }

        this.mBytesToByteBuffer.put(byteArray, buffer);

        return byteArray;
    }
}


