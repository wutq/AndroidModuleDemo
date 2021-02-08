package com.wss.module.main.ui.view.ca;

/**
 * Created on 2018/11/21.
 *
 * @author Zhu Xiangdong
 */
public final class Size {
    private final int mWidth;
    private final int mHeight;

    Size(final int width, final int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj instanceof Size) {
            final Size other = (Size) obj;
            return this.mWidth == other.mWidth && this.mHeight == other.mHeight;
        }

        return false;
    }

    @Override
    public String toString() {
        return this.mWidth + "x" + this.mHeight;
    }

    @Override
    public int hashCode() {
        return this.mHeight ^ ((this.mWidth << (Integer.SIZE / 2)) | (this.mWidth >>> (Integer.SIZE / 2)));
    }
}