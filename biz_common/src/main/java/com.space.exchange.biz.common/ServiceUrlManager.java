package com.space.exchange.biz.common;


public class ServiceUrlManager {

    public static String login() {
        return "api/login";
    }

    //    获取短信验证码
    public static String getSmsCode() {
        return "api/get_sms_code";
    }

    //    获取邮箱验证码
    public static String getEmailCode() {
        return "api/get_email_code";
    }

    //    照片上传 单个
    public static String uploadPhoto() {
        return "/Home/Home/uploadApi";
    }

    //    投顾文章列表
    public static String articleList() {
        return "api/article_list";
    }

    //    投顾个人主页
    public static String personInformation() {
        return "api/home_user_info";
    }

    //    投顾个人主页文章列表
    public static String personArticleList() {
        return "api/home_user_articles";
    }

    //    投顾关注添加
    public static String getAddGaunZhu() {
        return "api/attention_counselor";
    }

    //未读消息
    public static String unreadMessage() {
        return "api/notifies_num";
    }

    //    投顾我的通知
    public static String getMyNotice() {
        return "api/notify_list";
    }

    //    投顾我的消息
    public static String getMyXiaoxi() {
        return "api/message_list";
    }

    //    资讯快递
    public static String getMessageExpress() {
        return "api/news_flash";
    }

    //    获取标签
    public static String getTagInfosUrl() {
        return "api/article_labels";
    }

    //    发表文章
    public static String publishArticle() {
        return "api/create_article";
    }

    //    上传封面
    public static String uploadCover() {
////        return "https://tougu.coinka.cn/api/v1/uploadArticlePic";
        return "";
////        return "https://i.coinka.com/api/v1/uploadArticlePic";
////        return "https://tougu.coinka.com/api/v1/uploadArticlePic";
    }

    //    发表评论
    public static String publishComment() {
        return "api/comment";
    }

    //    获取评论详情
    public static String getCommentDetail() {
        return "api/comment_detail";
    }

    //    点赞文章
    public static String starArticle() {
        return "api/article_like";
    }

    //    收藏文章
    public static String collectArticle() {
        return "api/collect_article";
    }

    //    关注作者
    public static String followAuthority() {
        return "api/attention_counselor";
    }

    //    用户协议的网址
    public static String userProtocol() {
//        return "https://beta.coinka.com/Home/Index/app_agrement.html";
//        return "/Home/Index/app_agrement.html";
        return "help/agreenment";
//        return "https://beta.coinka.com/Home/Index/app_agreenment.html";
    }

    public static String helpCenter() {
//        return "http://tgm.phpyuan.com/help";
//        return "/Home/Page/Appguide.html";
        return "/MobileHelp";
    }

    //    问题反馈
    public static String feedBackUrl() {
        return "api/support";
    }

    //    阅读文章三十秒增加读者ck
    public static String ck2Reader() {
        return "api/ck_to_reader";
    }

    //    阅读文章六十秒增加作者ck
    public static String ck2Author() {
        return "api/ck_to_author";
    }


    //    获取文章详情不带评论
    public static String getArticleDetailWithoutComment() {
        return "api/only_detail";
    }

    //    获取评论信息
    public static String getArticleCommentInfos() {
        return "api/article_comments";
    }

    //    获取币种列表
    public static String getCoinTypeList() {
        return "api/assect/currency_list";
    }

    //    提币
    public static String extractCoin() {
        return "api/assect/extract";
    }

    //    最近十次提币记录
    public static String latestExtractRecord() {
        return "api/assect/extract_record";
    }

    //    最近十次充值记录
    public static String latestChargeRecord() {
        return "api/assect/recharge_record";
    }


    //    费率界面
    public static String getFeilv() {
        return "/Home/page/app_alertpay.html";
    }

    //    获取行情详情
    public static String getTradeDetailInfo() {
        return "api/trade/trade_detail";
    }


    public static String colllectOrCancelTradePair() {
        return "api/trade/collect";
    }

    //获取banner
    public static String getBanner() {
        return "api/banner";
    }

    //获取首页数据
    public static String getIndexInfo() {
        return "api/index";
    }

    //提醒卖家放行
    public static String remindPermit() {
        return "api/c2c/remind_permit";
    }

    //  获取法币首页侧拉信息
    public static String getuserinfo() {
        return "api/c2c/user_info";
    }

    //获取资产账单
    public static String getassetBillList() {
        return "/api/assect/bill_list";
    }

    //获取账单菜单栏
    public static String getBill_menu() {
        return "api/assect/bill_menu";
    }

    //    利空利好操作
    public static String bullBearOperation() {
        return "api/bull_bear";
    }

    //    根据标签获取文章列表
    public static String getArticleListByTagid() {
        return "api/label_article_list";
    }

    //    获取文章标签
    public static String getArticleTags() {
        return "api/article_labels";
    }

}
