package com.space.exchange.biz.common;

public class EventStatus {

    private static final int BASE_ID = 200;

    public static final int GET_CONTENT_DETAIL_SUCCESS = BASE_ID + 1;

    public static final int GET__FAILURE = BASE_ID + 2;

    public static final int NET_ERROR = BASE_ID + 3;

    public static final int GET_ARTICLE_DONE = BASE_ID + 4;

    public static final int REFRESH_ARTICLE = BASE_ID + 5;

    public static final int ARTICLE_LIST_ITEM_CLICK = BASE_ID + 6;

    public static final int GET_FANS_LIST_DONE = BASE_ID + 7;

    public static final int REFRESH_FANS_LIST_DONE = BASE_ID + 8;

    public static final int FANS_LIST_FOLLOW_CLICK = BASE_ID + 9;

    public static final int FANS_LIST_ICON_CLICK = BASE_ID +10;

    public static final int MASTER_RANK_INFOS = BASE_ID + 11;

    public static final int MASTER_RANK_ICON_CLICK = BASE_ID + 12;

    public static final int GET_MESSAGE_EXPRESS_DONE = BASE_ID + 13;

    public static final int REFRESH_MESSAGE_EXPRESS_DONE = BASE_ID + 14;

    public static final int ARTICLE_TAG_DEL_CLICK = BASE_ID + 15;

    public static final int GET_TAG_INFOS_DONE = BASE_ID + 16;

    public static final int COMMENT_USER_CLICK = BASE_ID + 17;

    public static final int COMMENT_MORE_CLICK = BASE_ID + 18;

    public static final int START_COMMENT_CONTENT = BASE_ID + 19;

    public static final int START_FIRST_COMMENT_CONTENT = BASE_ID+20;

    public static final int GET_COMMENT_DETAIL_DONE = BASE_ID + 21;

    public static final int AUTHORITY_FOLLOW_SUCCESS = BASE_ID + 22;//关注作者成功

    public static final int AUTHORITY_FOLLOW_FAILURE = BASE_ID + 23;//关注作者失败

    public static final int GET_ARTICLE_COMMENT_INFOS_DONE = BASE_ID + 24;

    public static final int MASTER_RANK_ITEM_CLICK = BASE_ID + 25;//大咖榜条目点击

    public static final int CHOOSE_COIN_TYPE_ITEM_CLICK = BASE_ID + 26;

    public static final int EXTRACT_COIND_FAILURE = BASE_ID+27;

    public static final int EXTRACT_COIN_SUCCESS = BASE_ID + 28;

    public static final int CHOOSE_ADDRESS_ITEM_CLICK = BASE_ID +29;

    public static final int FULL_SCREEN_CLICK = BASE_ID+30;

    public static final int GO_TO_TRADE_INDEX = BASE_ID+31;

    public static final int GO_TO_TRADE_FRAGMENT = BASE_ID + 32;

    public static final int ORDER_CANCEL_EVENT = BASE_ID + 33;

    public static final int CLOSE_ALL_EVENT = BASE_ID + 34;

    public static final int ORDER_LOCK_EVENT = BASE_ID + 35;

    public static final int FABI_ORDER_BUY = BASE_ID + 36;

    public static final int FABI_ORDER_SELL = BASE_ID + 37;

    public static final int BUY_OR_SELL_DIALOG_DISMISS = BASE_ID + 38;

    public static final int REFRESH_PEND = BASE_ID + 39;

    public static final int REFRESH_ORDER = BASE_ID + 40;

    public static final int SHOW_BIND_USER_POP = BASE_ID + 41;

    public static final int CHEAK_FINGERPRINT_OR_PATTERN = BASE_ID + 42;

    //法币币种列表
    public static final int FABICOINLIST = BASE_ID + 43;

    public static final int HINT_0_ASSECT = BASE_ID + 44;

    public static final int ASSET_COIN_BUY = BASE_ID + 45;

    public static final int ASSET_COIN_SELL = BASE_ID + 46;
    public static final int DRAWER_DIALOG_ONE = BASE_ID + 47;
    public static final int DRAWER_DIALOG_TWO = BASE_ID + 48;
    //资产页面划转改变自动刷新
    public static final String ASSECT_CHANGE = "ASSECT_CHANGE";//划转
    public static final String ASSECT_EXTRACT_CHANGE = "ASSECT_EXTRACT_CHANGE";//提币
    public static final String ASSECT_LOCK_CHANGE = "ASSECT_LOCK_CHANGE";//ck锁仓
    public static final String ASSECT_UNLOCK_CHANGE = "ASSECT_UNLOCK_CHANGEs";//ck解仓

    /**UN
     * 提交google验证码
     * */

    public static final int GOOGLE_CODE = BASE_ID + 45;

    /**
     *
     * 资产页面刷新数据传递给主资产页面
     *  */
    public static final int ASSECT_TOTAL = BASE_ID + 46;

    /**
     *
     * 划转
     *  */
    public static final int COIN_EXCHANGE = BASE_ID + 47;

    /*
    *  刷新订单信息
    * */
    public static final int REFRESH_ORDER_STATUS = BASE_ID + 48;

    /*
     *  刷新用户信息
     * */
    public static final int REFRESH_USERINFO = BASE_ID + 49;

    /*
     *  取消刷新
     * */
    public static final int END_REFRESH = BASE_ID + 50;


    /**
     *
     *  是否可以隐藏零资产
     * */
    public static final String IS_HIDE_ZERO_ENABLE = "isHideZeroEnable";


    /**
     *  string 类型的status
     * **/
    public static final String MAIN_GOTO_ASSET_FRAGMENT = "assetFragment";

    /**
     * h5活动的充提币
     * */

    public static final String AVTIVITY_CHONGTI = "AVTIVITY_CHONGTI";


    /**
     * 资产页面是否因隐藏0资产
     * */

    public static final String IS_HIDDEN_0 = "IS_HIDDEN_0";
    /**
     * 资产页面是否因隐藏全部资产
     * */

    public static final String IS_HIDDEN_ASSECT = "IS_HIDDEN_ASSECT";

    public static final String ASSET_STATUS_COIN_BUY = "assetCoinBuy";

    public static final String ASSET_STATUS_COIN_SELL = "assetCoinSell";

}
