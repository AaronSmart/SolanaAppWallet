package com.econet.app.homepage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class SelectGridView extends GridView {
    public SelectGridView(Context context) {
        super(context);
    }

    public SelectGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SelectGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
//    public void onMeasure(int i, int i2) {
//        super.onMeasure(i, MeasureSpec.makeMeasureSpec(536870911,  -2147483648 ));
//    }
}