
package com.econet.app.uitl;

import com.econet.app.solwallet.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PressMoreListView extends ListView implements OnClickListener {

    public enum DListViewLoadingMore {
        /**
         * 普通状态
         */
        LV_NORMAL,
        /**
         * 加载状态
         */
        LV_LOADING,
        /**
         *  结束状态
         */
        LV_OVER;
    }

    /**
     * 尾部mFootView
     */
    private View mFootView;
    /**
     * 加载更多.(mFootView)
     */
    private TextView mLoadMoreTextView;
    /**
     *  加载中...View(mFootView)
     */
    private View mLoadingView;
    /**
     * 上拉刷新和下拉加载更多接口监听
     */
    private OnRefreshLoadingMoreListener onRefreshLoadingMoreListener;
    /**
     * 最后一项
     */
    private int lastItem;

    public PressMoreListView(Context context) {
        super(context, null);
        initDragListView(context);
    }

    public PressMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDragListView(context);
    }

    /***
     * 初始化ListView
     */
    public void initDragListView(Context context) {
        initLoadMoreView(context);// 初始化footer
    }

    /***
     * 初始化底部加载更多控件
     */
    private void initLoadMoreView(Context context) {
        mFootView = LayoutInflater.from(context).inflate(R.layout.pullview_refresh_footer, null);
        mFootView.findViewById(R.id.pull_to_refresh_footer);
        mLoadMoreTextView = (TextView) mFootView.findViewById(R.id.load_more_tv);
        mLoadingView = (RelativeLayout) mFootView.findViewById(R.id.loading_layout);
        mFootView.setOnClickListener(this);
        addFooterView(mFootView);
        setCacheColorHint(context.getResources().getColor(android.R.color.transparent));
        //添加listview滚动监听  
        setOnScrollListener(new PressMoreListScrollListener());
    }

    /**
     * 
     * <P><B>Description: </B> 加载更多的监听   </P>
     
     *                 2016年10月11日 Json Lai CREATE
     * @author Json Lai
     * @version 1.0
     */
    public class PressMoreListScrollListener implements OnScrollListener {
        //AbsListView view 这个view对象就是listview  
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                if (view.getLastVisiblePosition() == view.getCount() - 1 && getFooterViewsCount() != 0
                        && mFootView.getVisibility() == View.VISIBLE && onRefreshLoadingMoreListener != null) {
                    onRefreshLoadingMoreListener.onLoadMore();   //自动刷新
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            lastItem = firstVisibleItem + visibleItemCount - 1;
        }
    }

    /***
     * 点击加载更多
     * 
     * @param flag
     *            数据是否已全部加载完毕
     */
    public void onLoadMoreComplete(boolean flag) {
        if (flag) {
            updateLoadMoreViewState(DListViewLoadingMore.LV_OVER);
        } else {
            updateLoadMoreViewState(DListViewLoadingMore.LV_NORMAL);
        }
    }

    public void onHeadRefreashComplete(boolean flag) {
        if (flag) {
            mLoadMoreTextView.setVisibility(View.VISIBLE);
        } else {
            mLoadMoreTextView.setVisibility(View.GONE);
        }
    }

    // 更新Footview视图
    public void updateLoadMoreViewState(DListViewLoadingMore state) {
        switch (state) {
        // 普通状态
            case LV_NORMAL:
                mLoadingView.setVisibility(View.GONE);
                mLoadMoreTextView.setVisibility(View.VISIBLE);
                mLoadMoreTextView.setText("swipe to load more");
                break;
            // 加载中状态
            case LV_LOADING:
                mLoadingView.setVisibility(View.VISIBLE);
                mLoadMoreTextView.setVisibility(View.GONE);
                break;
            // 加载完毕状态
            case LV_OVER:
                mLoadingView.setVisibility(View.GONE);
                mLoadMoreTextView.setVisibility(View.VISIBLE);
                mLoadMoreTextView.setText("loading complete");
                break;
            default:
                break;
        }
    }

    /***
     * 底部点击事件
     */
    @Override
    public void onClick(View v) {
        if (onRefreshLoadingMoreListener != null) {
            onRefreshLoadingMoreListener.onLoadMore();
        }
    }

    /***
     * 自定义接口
     */
    public interface OnRefreshLoadingMoreListener {
        void onLoadMore();
    }

    public void setOnRefreshLoadingMoreListener(OnRefreshLoadingMoreListener onRefreshLoadingMoreListener) {
        this.onRefreshLoadingMoreListener = onRefreshLoadingMoreListener;
    }

    public void setBottomIsVisiable(boolean isVisiAble) {
        if (isVisiAble) {
            mFootView.setVisibility(View.VISIBLE);
        } else {
            mFootView.setVisibility(View.GONE);
        }
    }

    public void removeFooterView() {
        if (mFootView != null) {
            removeFooterView(mFootView);
        }
    }

    public void addFooterView() {
        setBottomIsVisiable(true);
        if (getFooterViewsCount() == 0) {
            addFooterView(mFootView);
        }
    }

    public View getFooterView() {
        return mFootView;
    }
}
