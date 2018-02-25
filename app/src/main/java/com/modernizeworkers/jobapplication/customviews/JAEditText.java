package com.modernizeworkers.jobapplication.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import static java.security.AccessController.getContext;

/**
 * Created by Bino on 9/3/2017.
 */

public class JAEditText extends EditText {

    public JAEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public JAEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JAEditText(Context context) {
        super(context);
        init();
    }

    public void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cabin-Regular.ttf");
        setTypeface(tf ,1);

    }
}
