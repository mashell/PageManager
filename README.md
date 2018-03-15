> 该库基于鸿洋 [LoadingAndRetryManager](https://github.com/hongyangAndroid/LoadingAndRetryManager),看完源码后自己按照理解实现一遍

## 介绍
低侵入的页面状态管理库，可为 Activity,View 切换 Content,Loading,Error,Empty 视图

## 安装
1. 将JitPack存储库添加到构建文件中

	将其添加到 项目的 build.gradle 的 repositories 中：

	```
	allprojects {
	  repositories {
		...
		  maven { url 'https://jitpack.io' }
	  }
	}
	```
	
2. 在 app 的 build.gradle 中 dependency 下添加依赖

	```
	dependencies {
		compile 'com.github.xcc3641:watcher:1.0'
	}
	```

## 使用
1. 在全局设置通用的 ```Loading```,```empty```,```error```的布局
	
	```Java
	PageManager.initApp(R.layout.loading,R.layout.error,R.layout.empty);
	```
2. 在 Activity 中初始化

	```Java
	mPageManager = PageManager.init(this, true, new PageListener() {
	    @Override
	    public void setErrorView(View view) {
	        
	    }
	});
	```
	
	对于 单个View，参数传入该 View 即可.**该 View 必须有父 View**
	
	对于 Loading 界面，Empty 界面，想设置回调函数重写对应方法即可,比如 
	
	```Java
	mPageManager = PageManager.init(this, true, new PageListener() {
        ...
        @Override
        public void setEmptyView(View emptyView) {
            super.setEmptyView(emptyView);
            emptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doSomething();
                }
            });
        }
    });
	```
3. 对于需要定制页面的 Activity 或者 View,重写接口的对应函数即可,比如:

	```Java
	@Override
	public int generateLoadingId() {
	    return R.layout.special_loading_layout;
	}
	```
	
	或者
	
	```	Java
	@Override
	public View generateLoadingView() {
	    return specialLoadingView;
	}
	```

## 方法
在需要的地方，想显示指定页面，调用对应方法即可

- ```showContent()``` 显示内容界面
- ```showLoading()``` 显示 Loading 界面
- ```showEmpty()``` 显示 Empty 界面
- ```showError```	显示 Error 界面