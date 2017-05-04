package com.halohoop.partsshowtextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * Created by Pooholah on 2017/5/4.
 */

public class PartsShowTextView extends AppCompatTextView {

    private Matrix mMatrix;
    private float mGradientSize;
    private float mTextWidth;
    private LinearGradient mLinearGradient;
    private int mTrans;
    private int mFarestLeft;
    private int mFarestRight;

    public PartsShowTextView(Context context) {
        this(context, null);
    }

    public PartsShowTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartsShowTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int[] mColors = {0x22ffffff, 0xffffea00, 0x22ffffff};
    private int mMoveDelta = 10;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TextPaint paint = getPaint();
        //GradientSize=两个文字的大小
        String text = getText().toString();
        mTextWidth = paint.measureText(text);
        mGradientSize = (int) (3 * mTextWidth / text.length());
        mTrans = (int) -mMoveDelta;
        /*等价new float[]{0,0.5f,1}*/
        mLinearGradient = new LinearGradient(-mGradientSize, 0, 0, 0, mColors, null/*等价new float[]{0,0.5f,1}*/, Shader.TileMode.CLAMP);
        paint.setShader(mLinearGradient);
        mMatrix = new Matrix();

        //效果移动的最左边和最右边的调整
        mFarestLeft = (int) (mTextWidth + mGradientSize);
        mFarestRight = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTrans += mMoveDelta;
        if (mTrans > mFarestLeft || mTrans < mFarestRight) {
            mMoveDelta = -mMoveDelta;
        }

        mMatrix.setTranslate(mTrans, 0);
        mLinearGradient.setLocalMatrix(mMatrix);

        //循环运动
        postInvalidateDelayed(16);
    }
}
