package com.moonlight.nasa.lostandfound.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by NaSa on 2015/7/20.
 */
public class GoodItemGridView extends GridView {

    public boolean hasScrollBar = true;

    public GoodItemGridView(Context context) {
        super(context);
    }

    public GoodItemGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GoodItemGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = heightMeasureSpec;
        if (hasScrollBar) {
            expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
