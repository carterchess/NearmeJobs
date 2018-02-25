package com.modernizeworkers.jobapplication.customviews;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Bino on 8/20/2017.
 */

public class JATextView extends TextView{


    public JATextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public JATextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JATextView(Context context) {
        super(context);
        init();
    }

    public void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cabin-Regular.ttf");
        setTypeface(tf ,1);

    }
}
