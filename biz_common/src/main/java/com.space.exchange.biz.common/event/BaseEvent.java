package com.space.exchange.biz.common.event;

public class BaseEvent {
    /**
     * 默认设置String
     */
    public String what;
    /**
     * 默认设置对象
     */
    public Object obj;
    public BaseEvent next;
    public static int count;
    public static BaseEvent ePool;


    public static BaseEvent obtainMessage() {
        synchronized (BaseEvent.class) {
            if (ePool != null) {
                BaseEvent msg = ePool;
                ePool = msg.next;
                count--;
                return msg;
            }
        }
        return new BaseEvent();
    }

    public void recycle() {
        this.next = ePool;
        ePool = this;
        count++;
    }

}
