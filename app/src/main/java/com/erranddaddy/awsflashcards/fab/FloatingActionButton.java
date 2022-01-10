package com.erranddaddy.awsflashcards.fab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.Button;

import com.erranddaddy.awsflashcards.R;

public class FloatingActionButton extends Button {

    private final Paint mTextPaint = new TextPaint();

    private int mColor;

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

    }
}
