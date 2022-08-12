package com.econet.app.homepage;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author dai.jianhui
 */
public class MarqueTextView extends AppCompatTextView {
    public MarqueTextView(Context context) {
        super(context);
    }

    public MarqueTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MarqueTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean isFocused() {
        return true;
    }
}