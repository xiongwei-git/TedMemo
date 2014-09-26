
package com.tedmemo.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;

    public Rotate3dAnimation(float fromDegrees, float toDegrees,float centerX, float centerY, float depthZ, boolean reverse) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        camera.save();

        float goDegrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        float backDegrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        if (mReverse) {
            camera.translate(0.0f, 0.0f, (float)(mDepthZ * Math.sin(goDegrees*Math.PI/180)));
        } else {
            camera.translate(0.0f, 0.0f, (float)(mDepthZ * Math.sin(-backDegrees*Math.PI/180)));
        }

        camera.rotateX(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
