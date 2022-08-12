
package com.econet.app.uitl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.econet.app.solwallet.R;

/**
 * @author dai.jianhui
 */
public class SwipeRefreshListView extends RelativeLayout implements SwipeRefreshLayout.OnRefreshListener, PressMoreListView.OnRefreshLoadingMoreListener {

    private PressMoreListView mListView;
    public  SwipeRefresh refreshView;
    private BaseAdapter mAdapter;
    private RelativeLayout emptyView;
    private LayoutInflater mInflater;
    private Context mContext;
    private OnFooterRefreshListener mOnFooterRefreshListener;
    private OnHeadRefreshListener onHeadRefreshListener;

    private int mFooterState;
    private int mHeadState;

    private static final int PULL_TO_REFRESH = 2;
    private static final int REFRESHING = 4;


    private boolean empty_cancel = false;
    private boolean empty_clickable = true; // 空数据界面是否可点击
    private boolean hasMeasured = false;

    private TextView tvEmpty;
    private TextView tv_pullview_empty_top;
    private View v_pullview_empty_top;

    public interface OnFooterRefreshListener {
        public void onFooterRefresh();
    }
    public interface OnHeadRefreshListener {
        public void onHeadRefresh();
    }
    public PressMoreListView getListView() {
        return mListView;
    }

    public void setOnFooterRefreshListener(OnFooterRefreshListener footerRefreshListener) {
        mOnFooterRefreshListener = footerRefreshListener;
    }

    public void setOnHeadRefreshListener(OnHeadRefreshListener onHeadRefreshListener) {
        this.onHeadRefreshListener = onHeadRefreshListener;
        refreshView.setOnRefreshListener(this);
    }

    public class SwipeRefresh extends SwipeRefreshLayout {

        public SwipeRefresh(Context context) {
            super(context);
            init(context);
        }

        public SwipeRefresh(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        private void init(Context context) {
            addListview();
            addView(mListView);
        }
    }

    public SwipeRefreshListView(Context context) {
        super(context);
        init(context);
    }

    public SwipeRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(getContext());
        refreshView = new SwipeRefresh(context);
        setColorScheme(R.color.background_blue);
        addView(refreshView);
        addEmptyView();
        addView(emptyView);
    }
    @Override
    public void onRefresh() {
        onHeadRefreshing();
    }

    private void addEmptyView() {
        emptyView = (RelativeLayout) mInflater.inflate(R.layout.pullview_empty_view, this, false);
        tv_pullview_empty_top = (TextView) emptyView.findViewById(R.id.tv_pullview_empty_top);
        v_pullview_empty_top = emptyView.findViewById(R.id.v_pullview_empty_top);
        tvEmpty = (TextView) emptyView.findViewById(R.id.tv_empty);
        emptyView.setVisibility(View.GONE);
        emptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!empty_clickable) {
                    return;
                }
                emptyView.setVisibility(View.GONE);
                onHeadRefreshing();
            }
        });
    }

    public void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
        if (mListView != null) {
            mListView.setBottomIsVisiable(false);
        }
        getListView().setAdapter(adapter);
    }

    @Override
    public void onLoadMore() {
        if (mOnFooterRefreshListener == null || mHeadState == REFRESHING || mFooterState == REFRESHING) {
            return;
        }
        mFooterState = REFRESHING;
        mListView.updateLoadMoreViewState(PressMoreListView.DListViewLoadingMore.LV_LOADING);
        refreshView.setEnabled(false);
        mOnFooterRefreshListener.onFooterRefresh();
    }

    private void addListview() {
        mListView = new PressMoreListView(mContext);
        mListView.setSelector(getResources().getDrawable(R.drawable.list_selector));
        mListView.setOnRefreshLoadingMoreListener(this);
    }



    public void onHeadRefreshing() {
        if (onHeadRefreshListener == null || mHeadState == REFRESHING || mFooterState == REFRESHING) {
            return;
        }
        refreshView.setEnabled(false);
        setToRefreshing(true);
        mHeadState = REFRESHING;
        onHeadRefreshListener.onHeadRefresh();
    }
    public void setColorScheme(int... colorRes) {
        refreshView.setColorSchemeResources(colorRes);
    }
    public void setToRefreshing(final boolean isRefreshing) {
        post(new Runnable() {

            @Override
            public void run() {
                refreshView.setRefreshing(isRefreshing);
            }
        });
    }
    public void onHeaderRefreshComplete() {
        emptyView.setVisibility(View.GONE);
        if (mAdapter.getCount() == 0 && !empty_cancel) {
            emptyView.setVisibility(View.VISIBLE);
        }
        // 下边的模块判断是否需要显示，加载更多，footview的显示
        hasMeasured = false;
        ViewTreeObserver vto = mListView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!hasMeasured && mListView.getChildCount() > 0) {
                    int totalHeight = 0;
                    for (int i = 0; i < mListView.getChildCount(); i++) {
                        totalHeight += mListView.getChildAt(i).getHeight();
                    }
                    if ((mListView.getCount() > mListView.getChildCount() || totalHeight >= mListView.getHeight())
                            && mOnFooterRefreshListener != null) {
                        mListView.setBottomIsVisiable(true);
                    } else {
                        mListView.setBottomIsVisiable(false);
                    }
                    hasMeasured = true;
                }
                return true;
            }
        });
        mAdapter.notifyDataSetChanged();
        mHeadState = PULL_TO_REFRESH;
        refreshView.setRefreshing(false);
        refreshView.setEnabled(true);
    }

    public void onFooterRefreshComplete() {
        mListView.updateLoadMoreViewState(PressMoreListView.DListViewLoadingMore.LV_NORMAL);
        mFooterState = PULL_TO_REFRESH;
        mAdapter.notifyDataSetChanged();
        refreshView.setEnabled(true);
    }

    public void onCompleteAll() {
        if (mFooterState == REFRESHING) {
            onFooterRefreshComplete();
        }
        if (mHeadState == REFRESHING) {
            onHeaderRefreshComplete();
        }
    }








    /** 是否取消空数据界面 */
    public void setEmptyCancel(boolean bl) {
        empty_cancel = bl;
    }
    public void setProgressViewOffset(int start, int end) {
        refreshView.setProgressViewOffset(false, start, end);
    }
    public void setEmptyDrawable(int resource, boolean empty_clickable) {
        if (tvEmpty != null) {
            tvEmpty.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(resource), null, null);
        }
    }

    public void setEmptyDrawable(int resource, String content, boolean empty_clickable) {
        if (tvEmpty != null) {
            tvEmpty.setText(content);
            tvEmpty.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(resource), null, null);
        }
    }

    public void showEmptyTop(int height) {
        if (tv_pullview_empty_top != null) {
            LayoutParams lp_tv_empty_top = (LayoutParams) tv_pullview_empty_top
                    .getLayoutParams();
            lp_tv_empty_top.height = height;
            tv_pullview_empty_top.setLayoutParams(lp_tv_empty_top);
            tv_pullview_empty_top.setVisibility(height == 0 ? View.GONE : View.VISIBLE);
        }
        if (v_pullview_empty_top != null) {
            v_pullview_empty_top.setVisibility(height == 0 ? View.GONE : View.VISIBLE);
        }

    }

    public boolean isEmptyClickable() {
        return empty_clickable;
    }

    public void setEmptyClickable(boolean empty_clickable) {
        this.empty_clickable = empty_clickable;
    }
    public void showEmptyView(boolean isShow) {
        emptyView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

}
