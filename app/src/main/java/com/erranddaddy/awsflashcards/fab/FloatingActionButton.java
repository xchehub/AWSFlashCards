package com.erranddaddy.awsflashcards.fab;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;

import com.erranddaddy.awsflashcards.R;

public class FloatingActionButton extends Button {

    private final Paint mTextPaint = new TextPaint();
    private final Paint mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mDrawablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private int mColor;
    private Bitmap mBitmap;
    private int mScreenHeight;
    private boolean mHidden = false;
    private float mCurrentY;

    public FloatingActionButton(Context context) {
//        super(context);
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Typeface mFont = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "icons.ttf");
        mTextPaint.setTypeface(mFont);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.fab_button));

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FloatingActionButton);
        mColor = a.getColor(R.styleable.FloatingActionButton_rippleColor, Color.WHITE);
        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setColor(mColor);

        float radius, dx, dy;
        radius = a.getFloat(R.styleable.FloatingActionButton_shadowColor, 10.0f);
        dx = a.getFloat(R.styleable.FloatingActionButton_shadowDx, 0.0f);
        dy = a.getFloat(R.styleable.FloatingActionButton_shadowDy, 3.5f);

        int color = a.getInteger(R.styleable.FloatingActionButton_shadowColor, Color.argb(100, 0, 0, 0));
        mButtonPaint.setShadowLayer(radius, dx, dy, color);

        Drawable drawable = a.getDrawable(R.styleable.FloatingActionButton_drawable);
        if (null != drawable) {
            mBitmap = ((BitmapDrawable)drawable).getBitmap();
        }

        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenHeight = size.y;
    }

    public void setColor(int color) {
        mColor = color;
        mButtonPaint.setColor(mColor);
        invalidate();
    }

    public void setDrawable(Drawable drawable) {
        mBitmap = ((BitmapDrawable) drawable).getBitmap();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null != mBitmap) {
            canvas.drawBitmap(mBitmap, (getWidth() - mBitmap.getWidth()) / 2,
                    (getHeight() - mBitmap.getHeight()) / 2, mDrawablePaint);
        } else {
            float xPos = getWidth() / 2;
            float yPos = getHeight() / 2;
            canvas.drawCircle(xPos, yPos, (float) (getWidth() / 2.6), mButtonPaint);
            Rect textRect = new Rect();
            mTextPaint.getTextBounds(this.getText().toString(), 0,
                    this.getText().toString().length(), textRect);
            int offSet = textRect.height() / 2;
            canvas.drawText(this.getText().toString(), xPos, yPos + offSet + 10f, mTextPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int color;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            color = mColor;
        } else {
            color = darkenColor(mColor);
        }
        mButtonPaint.setColor(color);
        invalidate();
        return super.onTouchEvent(event);
    }

    public static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    public void hide(boolean hide) {
        if (mHidden != hide) {
            float offset;
            if (mHidden) {
                offset = mCurrentY;
            } else {
                mCurrentY = getY();
                offset = mScreenHeight;
            }
            mHidden = hide;
            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "Y", offset);
            animator.setInterpolator(mInterpolator);
            animator.start();
        }
    }

    @Override
    public void setTextColor(int color) {
        mTextPaint.setColor(color);
    }
}
