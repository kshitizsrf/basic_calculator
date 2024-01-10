package com.demo.android.calculator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ReverseScrollView extends ViewGroup {
    public ReverseScrollView(Context context) {
        super(context);
    }

    public ReverseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int top = b;

        for (int i = 0; i < childCount; i++){
            View child = getChildAt(i);
            int childHeight = child.getMeasuredHeight();
            top -= childHeight;
            child.layout(l, top, r, top + childHeight);
        }
    }
}
