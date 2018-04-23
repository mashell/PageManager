package com.mashell.pagestatesmanager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mashell on 18/3/13.
 */

public class PageManager {
    public static final String TAG = PageManager.class.getClass().getSimpleName();

    public static final int NO_LAYOUT_ID = -1;

    public static int BASE_LOADING_LAYOUT_ID = NO_LAYOUT_ID;
    public static int BASE_ERROR_LAYOUT_ID = NO_LAYOUT_ID;
    public static int BASE_EMPTY_LAYOUT_ID = NO_LAYOUT_ID;

    public PageLayout mPageLayout;

    private PageListener mPageListener;

    private boolean ifGeneraLoadingView = false;
    private boolean ifGeneraErrorView = false;
    private boolean ifGeneraEmptyView = false;

    /**
     * 首次初始化的时候调用，指定通用的 loadingLayout retryLayout emptyLayout
     */
    public static void initApp(int loadingLayout, int retryLayout, int emptyLayout) {
        if (loadingLayout > 0) {
            BASE_LOADING_LAYOUT_ID = loadingLayout;
        }
        if (retryLayout > 0) {
            BASE_ERROR_LAYOUT_ID = retryLayout;
        }
        if (emptyLayout > 0) {
            BASE_EMPTY_LAYOUT_ID = emptyLayout;
        }
    }

    /**
     * 单独页面创建 PageManager实例
     *
     * @param container 需要替换的对象，可以使activity或者有 parentView 的子View
     * @param ifShowLoading 首次进入页面时加载 loadingView 还是 contentView
     * @param listener PageManager 的回调，需要特殊设置三种页面的回调请重写对应的 setXXXView() 方法
     */
    public static PageManager init(Object container, boolean ifShowLoading, PageListener listener) {
        return new PageManager(container, ifShowLoading, listener);
    }

    private PageManager(Object container, boolean ifShowLoading, PageListener listener) {
        mPageListener = listener;
        ViewGroup containerParent = null;
        Context context = null;
        int index = 0;
        if (container instanceof Activity) {
            //Activity 直接找到 contentView
            Activity containerActivity = (Activity) container;
            containerParent = containerActivity.findViewById(android.R.id.content);
            context = containerActivity;

        } else if (container instanceof View) {
            //View 找到 父view
            View containerView = (View) container;
            containerParent = (ViewGroup) containerView.getParent();

            if (containerParent == null) {
                throw new IllegalArgumentException(TAG + " the View must be has a parent");
            }
            context = containerView.getContext();

            //遍历viewGroup树，找到该view所在位置
            for (int i = 0; i < containerParent.getChildCount(); i++) {
                View childView = containerParent.getChildAt(i);
                if (childView == container) {
                    index = i;
                }
            }
        } else {
            throw new IllegalArgumentException(TAG + " container must be Activity or View");
        }
        View findView = containerParent.getChildAt(index);
        //移除旧view
        containerParent.removeView(findView);

        mPageLayout = new PageLayout(context);
        ViewGroup.LayoutParams lp = findView.getLayoutParams();
        //通过 index 添加 PageLayout
        containerParent.addView(mPageLayout, index, lp);
        //设置 contentView
        mPageLayout.setContentView(findView);

//        //初始化 emptyView
//        generaEmptyView(listener);
//        //初始化 retryView
//        generaErrorView(listener);
//        //初始化 loadingView
//        generaLoadingView(listener);

        //回调
        listener.setErrorView(mPageLayout.getErrorView());
        listener.setEmptyView(mPageLayout.getEmptyView());
        listener.setLoadingView(mPageLayout.getLoadingView());

        if (ifShowLoading) {
            showLoadingView();
        } else {
            showContentView();
        }
    }

    /**
     * 初始化 EmptyView
     */
    public void generaEmptyView(PageListener pageListener) {
        //先判断是否重写方法，加载定制的view
        if (pageListener.generateEmptyView() != null) {
            mPageLayout.setEmptyView(pageListener.generateEmptyView());
        } else if (pageListener.generateEmptyId() != PageManager.NO_LAYOUT_ID) {
            //再判断是否重写过,加载定制的view布局Id
            mPageLayout.setEmptyView(pageListener.generateEmptyView());
        } else if (BASE_EMPTY_LAYOUT_ID != NO_LAYOUT_ID) {
            //没有重写方法来定制view，那么加载初始化时的 base view
            mPageLayout.setEmptyView(BASE_EMPTY_LAYOUT_ID);
        } else {
            ifGeneraEmptyView = false;
            throw new IllegalArgumentException("You have not use the function 'initApp()' to init the BASE_EMPTY_LAYOUT_ID");
        }
        ifGeneraEmptyView = true;
    }

    /**
     * 初始化 ErrorView
     */
    public void generaErrorView(PageListener pageListener) {
        if (pageListener.generateErrorView() != null) {
            mPageLayout.setErrorView(pageListener.generateErrorView());
        } else if (pageListener.generateErrorId() != NO_LAYOUT_ID) {
            mPageLayout.setErrorView(pageListener.generateErrorId());
        } else if (BASE_ERROR_LAYOUT_ID != NO_LAYOUT_ID) {
            mPageLayout.setErrorView(BASE_ERROR_LAYOUT_ID);
        } else {
            ifGeneraErrorView = false;
            throw new IllegalArgumentException("You have not use the function 'initApp()' to init the BASE_LOADING_LAYOUT_ID");
        }
        ifGeneraErrorView = true;
    }

    /**
     * 初始化 LoadingView
     */
    public void generaLoadingView(PageListener pageListener) {
        if (pageListener.generateLoadingView() != null) {
            mPageLayout.setLoadingView(pageListener.generateLoadingView());
        } else if (pageListener.generateLoadingId() != NO_LAYOUT_ID) {
            mPageLayout.setLoadingView(pageListener.generateLoadingId());
        } else if (BASE_LOADING_LAYOUT_ID != NO_LAYOUT_ID) {
            mPageLayout.setLoadingView(BASE_LOADING_LAYOUT_ID);
        } else {
            ifGeneraLoadingView = false;
            throw new IllegalArgumentException("You have not use the function 'initApp()' to init the BASE_LOADING_LAYOUT_ID");
        }
        ifGeneraLoadingView = true;
    }

    /**
     * 显示 ContentView
     */
    public void showContentView() {
        mPageLayout.showContentView();
    }

    /**
     * 显示 ErrorView
     */
    public void showErrorView() {
        if (!ifGeneraErrorView) {
            generaErrorView(mPageListener);
        }
        mPageLayout.showErrorView();
    }

    /**
     * 显示 EmptyView
     */
    public void showEmptyView() {
        if (!ifGeneraEmptyView) {
            generaEmptyView(mPageListener);
        }
        mPageLayout.showEmptyView();
    }

    /**
     * 显示 LoadingView
     */
    public void showLoadingView() {
        if (!ifGeneraLoadingView) {
            generaLoadingView(mPageListener);
        }
        mPageLayout.showLoadingView();
    }

}
