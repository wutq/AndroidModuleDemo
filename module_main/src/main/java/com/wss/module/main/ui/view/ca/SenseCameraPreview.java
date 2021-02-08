package com.wss.module.main.ui.view.ca;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created on 2018/03/11.
 *
 * @author Zhu Xiangdong
 */
public class SenseCameraPreview extends ViewGroup {

    private Context mContext;

    private SurfaceView mSurfaceView;

    private boolean mStartRequested;

    private boolean mSurfaceAvailable;

    private SenseCamera mCamera;

    private StartListener mStartListener;

    /**
     * SenseCameraPreview.
     *
     * @param context context.
     * @param attrs attrs.
     */
    public SenseCameraPreview(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;
        this.mStartRequested = false;
        this.mSurfaceAvailable = false;

        this.mSurfaceView = new SurfaceView(context);
        this.mSurfaceView.getHolder().addCallback(new SurfaceCallback());
        addView(this.mSurfaceView);
    }

    public void setStartListener(final StartListener listener) {
        this.mStartListener = listener;
    }

    /**
     * start with SenseCamera.
     *
     * @param senseCamera senseCamera.
     */
    public void start(final SenseCamera senseCamera) throws IOException, RuntimeException {
        if (senseCamera == null) {
            this.stop();
        }

        this.mCamera = senseCamera;

        if (this.mCamera != null) {
            this.mStartRequested = true;
            this.startIfReady();
        }
    }

    /**
     * stop.
     */
    public void stop() {
        if (this.mCamera != null) {
            this.mCamera.stop();
        }
    }

    /**
     * release.
     */
    public void release() {
        if (this.mCamera != null) {
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    private void startIfReady() throws IOException, RuntimeException {
        if (this.mStartRequested && this.mSurfaceAvailable) {
            this.mCamera.start(this.mSurfaceView.getHolder());
            this.requestLayout();
            this.mStartRequested = false;
        }
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right,
            final int bottom) {
        if (this.mCamera != null) {
            Size size = this.mCamera.getPreviewSize();
            if (size != null) {
                int width = size.getWidth();
                int height = size.getHeight();

                if (this.isPortraitMode()) {
                    int tmp = width;
                    //noinspection SuspiciousNameCombination
                    width = height;
                    height = tmp;
                }

                final int layoutWidth = right - left;
                final int layoutHeight = bottom - top;

                int childWidth;
                int childHeight;

                final float layoutAspectRatio = layoutWidth / (float) layoutHeight;
                final float cameraPreviewAspectRatio = width / (float) height;

                if (Float.compare(layoutAspectRatio, cameraPreviewAspectRatio) <= 0) {
                    childWidth = (int) (layoutHeight * cameraPreviewAspectRatio);
                    childHeight = layoutHeight;
                } else {
                    childWidth = layoutWidth;
                    childHeight = (int) (layoutWidth / cameraPreviewAspectRatio);
                }

                for (int i = 0; i < this.getChildCount(); ++i) {
                    this.getChildAt(i).layout(0, 0, childWidth, childHeight);
                }

                try {
                    this.startIfReady();
                } catch (Exception e) {
                    if (SenseCameraPreview.this.mStartListener != null) {
                        SenseCameraPreview.this.mStartListener.onFail();
                    }
                }
            }
        }
    }

    /**
     * convert viewRect.
     *
     * @param viewRect viewRect.
     * @return converted Rect.
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public Rect convertViewRectToCameraPreview(final Rect viewRect) {
        final int viewWidth = this.getWidth();
        final int viewHeight = this.getHeight();
        final int cameraRotationDegrees = this.mCamera.getRotationDegrees();
        final int imageWidth = this.mCamera.getPreviewSize().getWidth();
        final int imageHeight = this.mCamera.getPreviewSize().getHeight();

        float widthRatio;
        float heightRatio;

        switch (cameraRotationDegrees) {
            case 90:
            case 270:
                widthRatio = (float) imageHeight / viewWidth;
                heightRatio = (float) imageWidth / viewHeight;
                break;
            case 0:
            case 180:
            default:
                widthRatio = (float) imageWidth / viewWidth;
                heightRatio = (float) imageHeight / viewHeight;
                break;
        }

        final float scale = widthRatio < heightRatio ? widthRatio : heightRatio;

        final Rect scaledRect =
                new Rect((int) (viewRect.left * scale + 0.5f), (int) (viewRect.top * scale + 0.5f),
                        (int) (viewRect.right * scale + 0.5f),
                        (int) (viewRect.bottom * scale + 0.5f));

        switch (cameraRotationDegrees) {
            case 90:
                return new Rect(scaledRect.top, imageHeight - scaledRect.right, scaledRect.bottom,
                        imageHeight - scaledRect.left);
            case 180:
                return new Rect(imageWidth - scaledRect.right, imageHeight - scaledRect.bottom,
                        imageWidth - scaledRect.left, imageHeight - scaledRect.top);
            case 270:
                return new Rect(imageWidth - scaledRect.bottom, scaledRect.left,
                        imageWidth - scaledRect.top, scaledRect.right);
            case 0:
            default:
                return scaledRect;
        }
    }

    /**
     * convert viewRect.
     *
     * @param viewRect viewRect.
     * @return converted Rect.
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public Rect convertViewRectToPicture(final Rect viewRect) {
        final int viewWidth = this.getWidth();
        final int viewHeight = this.getHeight();
        final int cameraRotationDegrees = this.mCamera.getRotationDegrees();
        final int imageWidth = this.mCamera.getPreviewSize().getWidth();
        final int imageHeight = this.mCamera.getPreviewSize().getHeight();

        float widthRatio;
        float heightRatio;

        switch (cameraRotationDegrees) {
            case 90:
            case 270:
                widthRatio = (float) imageHeight / viewWidth;
                heightRatio = (float) imageWidth / viewHeight;
                break;
            case 0:
            case 180:
            default:
                widthRatio = (float) imageWidth / viewWidth;
                heightRatio = (float) imageHeight / viewHeight;
                break;
        }

        final float scale = widthRatio < heightRatio ? widthRatio : heightRatio;

        final Rect scaledRect =
                new Rect((int) (viewRect.left * scale + 0.5f), (int) (viewRect.top * scale + 0.5f),
                        (int) (viewRect.right * scale + 0.5f),
                        (int) (viewRect.bottom * scale + 0.5f));

        Rect rotateRect = new Rect(scaledRect);
        switch (cameraRotationDegrees) {
            case 90:
                rotateRect =
                        new Rect(scaledRect.top, imageHeight - scaledRect.right, scaledRect.bottom,
                                imageHeight - scaledRect.left);
                break;
            case 180:
                rotateRect =
                        new Rect(imageWidth - scaledRect.right, imageHeight - scaledRect.bottom,
                                imageWidth - scaledRect.left, imageHeight - scaledRect.top);
                break;
            case 270:
                rotateRect = new Rect(imageWidth - scaledRect.bottom, scaledRect.left,
                        imageWidth - scaledRect.top, scaledRect.right);
                break;
            case 0:
            default:
                break;
        }

        Rect resultRect = new Rect(rotateRect);
        if (this.mCamera.getCameraFacing() == SenseCamera.CAMERA_FACING_FRONT) {
            switch (cameraRotationDegrees) {
                case 90:
                case 270:
                    resultRect = new Rect(rotateRect.left, imageHeight - rotateRect.bottom,
                            rotateRect.right, imageHeight - rotateRect.top);
                    break;
                case 0:
                case 180:
                    resultRect = new Rect(imageWidth - rotateRect.right, rotateRect.top,
                            imageWidth - rotateRect.left, rotateRect.bottom);
                    break;
                default:
                    break;
            }
        }

        return resultRect;
    }

    private boolean isPortraitMode() {
        final int orientation = this.mContext.getResources().getConfiguration().orientation;
        return orientation != Configuration.ORIENTATION_LANDSCAPE
                && orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public interface StartListener {
        void onFail();
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(final SurfaceHolder surface) {
            SenseCameraPreview.this.mSurfaceAvailable = true;
            try {
                SenseCameraPreview.this.startIfReady();
            } catch (Exception e) {
                if (SenseCameraPreview.this.mStartListener != null) {
                    SenseCameraPreview.this.mStartListener.onFail();
                }
            }
        }

        @Override
        public void surfaceDestroyed(final SurfaceHolder surface) {
            SenseCameraPreview.this.mSurfaceAvailable = false;
        }

        @Override
        public void surfaceChanged(final SurfaceHolder holder, final int format, final int width,
                                   final int height) {
            // noting.
        }
    }
}
