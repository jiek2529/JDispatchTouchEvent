# 事件分发机制

通过 ViewPager 嵌套 ListView 事件分发机制原理


Activity .dispatchTouchEvent()
    getWindow() .superDispatchTouchEvent() // --> 去找 DecorView 的superDispatchTouchEvent
        decor .superDispatchTouchEvent()
            Framelayout.dispatchTouchEvent() 
                ViewGroup.dispatchTouchEvent();
                
                View.dispatchTouchEvent();
                View.onTouchEvent();
                View.onClick();//如果有
                
            dispatchTransformedTouchEvent 事件的分发与处理
                
消费链以 ViewGroup 的 mFirstTarget 和 allReadyDispatchTouchEventTarget 标识来标记住

当前 View 想把事件放弃给父控件时，使用 getParent().requestDisallowInterceptTouchEvent(false)来给父 ViewGroup。

如果当前 View 想拦截所有事件，不被父 View 拦截掉，可用getParent().requestDisallowInterceptTouchEvent(true),父 ViewGroup的 onInterceptTouchEvent 不被拦截执行。

