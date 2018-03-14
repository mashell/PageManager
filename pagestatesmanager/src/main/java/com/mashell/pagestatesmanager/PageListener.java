package com.mashell.pagestatesmanager;

import android.view.View;

/**
 * Created by mashell on 18/3/13.
 * 页面管理的检测监听类,需要单独设置不同样式可重写方法
 */

public abstract class PageListener {

    /**
     * 获取loading状态的layout，默认空
     * @return
     */
    public int generateLoadingId(){
        return PageManager.NO_LAYOUT_ID;
    }

    /**
     * 获取error状态的layout，默认空
     * @return
     */
    public int generateErrorId(){
        return PageManager.NO_LAYOUT_ID;
    }

    /**
     * 获取empty状态的layout，默认空
     * @return
     */
    public int generateEmptyId(){
        return PageManager.NO_LAYOUT_ID;
    }

    /**
     * 获取loading状态的view
     * @return
     */
    public View generateLoadingView(){
        return null;
    }

    /**
     * 获取error状态的view
     * @return
     */
    public View generateErrorView(){
        return null;
    }

    /**
     * 获取empty状态的view
     * @return
     */
    public View generateEmptyView(){
        return null;
    }

    /**
     * 判断是否设置过LoadingView
     * @return
     */
    public boolean ifSetLoadingView(){
        if (generateLoadingId() != PageManager.NO_LAYOUT_ID || generateLoadingView() != null){
            return true;
        } else return false;
    }

    /**
     * 判断是否设置过ErrorView
     * @return
     */
    public boolean ifSetErrorView(){
        if (generateErrorId() != PageManager.NO_LAYOUT_ID || generateErrorView() != null){
            return true;
        } else return false;
    }

    /**
     * 判断是否设置过EmptyView
     * @return
     */
    public boolean ifSetEmptyView(){
        if (generateEmptyId() != PageManager.NO_LAYOUT_ID || generateEmptyView() != null){
            return true;
        } else return false;
    }

    /**
     * ErrorView的回调函数
     */
    public abstract void setErrorView(View errorView);

    /**
     * EmptyView的回调函数
     */
    public void setEmptyView(View emptyView){

    }

    /**
     * LoadingView的回调函数
     */
    public void setLoadingView(View loadingView){

    }
}
