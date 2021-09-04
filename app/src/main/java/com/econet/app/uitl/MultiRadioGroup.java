package com.econet.app.uitl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * multi radio group  by dai.jianhui
 */
public class MultiRadioGroup extends RadioGroup {
    private static final String TAG = "MultiRadioGroup";
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private int mCheckId = -1;

    public MultiRadioGroup(Context context) {
        super(context);
    }

    public MultiRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    public void addView(final View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof LinearLayout) {
            int childCount = ((LinearLayout) child).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = ((LinearLayout) child).getChildAt(i);
                if (view instanceof RadioButton) {
                    final RadioButton button = (RadioButton) view;
                    button.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            mCheckId = button.getId();
                            button.setChecked(true);
                            checkRadioButton(button);
                            if (mOnCheckedChangeListener != null) {
                                mOnCheckedChangeListener.onCheckedChanged(MultiRadioGroup.this, button.getId());
                            }
                            return true;
                        }
                    });
                }
            }
        }
        super.addView(child, index, params);
    }

    private void checkRadioButton(RadioButton radioButton) {
        View child;
        int radioCount = getChildCount();
        for (int i = 0; i < radioCount; i++) {
            child = getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton button = (RadioButton) child;
                if (button == radioButton) {
                    // do nothing
                } else {
                    button.setChecked(false);
                }
            } else if (child instanceof LinearLayout) {
                int childCount = ((LinearLayout) child).getChildCount();
                for (int j = 0; j < childCount; j++) {
                    View view = ((LinearLayout) child).getChildAt(j);
                    if (view instanceof RadioButton) {
                        RadioButton button = (RadioButton) view;
                        if (button == radioButton) {
                            // do nothing
                        } else {
                            button.setChecked(false);
                        }
                    }
                }
            }
        }
    }

    /**
     * 用于获取选中位置button的id
     */
    public int getCheckId() {
        return mCheckId;
    }
}