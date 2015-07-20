package com.moonlight.nasa.lostandfound.Util;

import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.moonlight.nasa.lostandfound.UI.GoodItemGridView;

/**
 * Created by NaSa on 2015/7/20.
 */
public final class GridViewUtils {
    //存储宽度
    static SparseIntArray mGridViewWidth = new SparseIntArray();

    /**
     * 计算GridView的高度
     */

    public static void updateGridViewLayoutParams(GridView gridView, int maxColumn) {
        int childs = gridView.getAdapter().getCount();
        if (childs > 0) {
            int columns = childs < maxColumn ? childs % maxColumn : maxColumn;
            gridView.setNumColumns(columns);
            int width = 0;
            int cacheWidth = mGridViewWidth.get(columns);
            if (cacheWidth != 0) {
                width = cacheWidth;
            } else {
                // 计算gridview每行的宽度, 如果item小于3则计算所有item的宽度;
                // 否则只计算maxColumn个child宽度，因此一行最多maxColumn个child。
                int rowCounts = childs < maxColumn ? childs : maxColumn;
                for (int i = 0; i < rowCounts; i++) {
                    View childView = gridView.getAdapter().getView(i, null, gridView);
                    childView.measure(0, 0);
                    width += childView.getMeasuredWidth();
                }
            }
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.width = width;
            gridView.setLayoutParams(params);
            if (mGridViewWidth.get(columns) == 0) {
                mGridViewWidth.append(columns, width);
            }
        }
    }
}
