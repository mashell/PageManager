package com.mashell.pagestatesmanager;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by mashell on 18/3/13.
 * PageLayout 页面
 */

public class PageLayout extends FrameLayout{

    //空白view
    private View mEmptyView;
    //加载view
    private View mLoadingView;
    //错误view
    private View mErrorView;
    //内容view
    private View mContentView;
    private LayoutInflater mLayoutInflater;

    public PageLayout(@NonNull Context context) {
        this(context,null);
    }

    public PageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public PageLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 更新显示状态
     * @param view
     */
    public void showView(View view){
        if (view != null){
            //根据view类型更新显示状态
            if (view == mLoadingView){
                mLoadingView.setVisibility(VISIBLE);

                if (mErrorView != null){
                    mErrorView.setVisibility(GONE);
                }
                if (mEmptyView != null){
                    mEmptyView.setVisibility(GONE);
                }
                if (mContentView != null){
                    mContentView.setVisibility(GONE);
                }
            }

            if (view == mErrorView){
                mErrorView.setVisibility(VISIBLE);

                if (mLoadingView != null){
                    mLoadingView.setVisibility(GONE);
                }
                if (mEmptyView != null){
                    mEmptyView.setVisibility(GONE);
                }
                if (mContentView != null){
                    mContentView.setVisibility(GONE);
                }
            }

            if (view == mEmptyView){
                mEmptyView.setVisibility(VISIBLE);

                if (mLoadingView != null){
                    mLoadingView.setVisibility(GONE);
                }
                if (mErrorView != null){
                    mErrorView.setVisibility(GONE);
                }
                if (mContentView != null){
                    mContentView.setVisibility(GONE);
                }
            }

            if (view == mContentView){
                mContentView.setVisibility(VISIBLE);

                if (mLoadingView != null){
                    mLoadingView.setVisibility(GONE);
                }
                if (mEmptyView != null){
                    mEmptyView.setVisibility(GONE);
                }
                if (mErrorView != null){
                    mErrorView.setVisibility(GONE);
                }
            }
        }
    }

    /**
     * 显示 Content
     */
    public void showContentView(){
        showView(mContentView);
    }

    /**
     * 显示 ErrorView
     */
    public void showErrorView(){
        showView(mErrorView);
    }

    /**
     * 显示 EmptyView
     */
    public void showEmptyView(){
        showView(mEmptyView);
    }

    /**
     * 显示 LoadingView
     */
    public void showLoadingView(){
        showView(mLoadingView);
    }


    /**
     * 一系列 getter setter
     * @return
     */
    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyView(View emptyView) {
        if (emptyView != null){
            if (mEmptyView != null){
                removeView(mEmptyView);
            }
            addView(emptyView);
            mEmptyView = emptyView;
        }
    }

    public void setEmptyView(int resId){
        setEmptyView(mLayoutInflater.inflate(resId,this,false));
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public void setLoadingView(View loadingView) {
        if (loadingView != null){
            if(mLoadingView != null){
                removeView(mLoadingView);
            }
            addView(loadingView);
            mLoadingView = loadingView;
        }
    }

    public void setLoadingView(int resId){
        setLoadingView(mLayoutInflater.inflate(resId,this,false));
    }

    public View getErrorView() {
        return mErrorView;
    }

    public void setErrorView(View errorView) {
        if (errorView != null){
            if (mErrorView != null){
                removeView(mErrorView);
            }
            addView(errorView);
            mErrorView = errorView;
        }
    }

    public void setErrorView(int resId){
        setErrorView(mLayoutInflater.inflate(resId,this,false));
    }

    public View getContentView() {
        return mContentView;
    }

    public void setContentView(View contentView) {
        if (contentView != null){
            if (mContentView != null){
                removeView(mContentView);
            }
            addView(contentView);
            mContentView = contentView;
        }
    }

    public void setContentView(int resId){
        setContentView(mLayoutInflater.inflate(resId,this,false));
    }
}
