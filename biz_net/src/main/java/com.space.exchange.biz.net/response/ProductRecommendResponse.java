package com.space.exchange.biz.net.response;

import com.space.exchange.biz.net.bean.BaseResponse;

import java.util.List;

public class ProductRecommendResponse extends BaseResponse {

    /**
     * id : 10036
     * app_id : 3
     * title : usdt产品测试
     * category_id : 9
     * currency_id : 11
     * raise_amount : 10.0000
     * is_raise_out : 1
     * buy_max_count : 3
     * buy_min_num : 1.0000
     * buy_max_num : 0.0000
     * raise_start_time : 2021-02-22 14:29:00
     * raise_end_time : 2021-02-23 14:28:59
     * run_start_time : 2021-02-24 14:28:00
     * run_end_time : 2021-02-25 14:28:00
     * settle_time : 2021-02-25 14:28:00
     * grant_earning_count : 1
     * expect_rate_title : 编发按时发萨达
     * expect_rate : 20.00
     * rate_cycle : 4
     * earning_type_id : 21
     * tag : [{"id":"1613975449830","sort":"3","backgroundColor":"#E5F7F4","textColor":"#04AD90","text":"标签1"},{"id":"1613975459676","sort":"5","backgroundColor":"#E5F7F4","textColor":"#D8A856","text":"标签 2"}]
     * show_tag : [{"id":1,"label":"资管团队","status":0},{"id":2,"label":"收益类型","status":0},{"id":3,"label":"产品简介","status":0},{"id":4,"label":"开启复投","status":0},{"id":5,"label":"交易规则","status":0},{"id":6,"label":"费率须知","status":0},{"id":7,"label":"认购记录","status":1},{"id":8,"label":"产品介绍","status":0},{"id":9,"label":"常见问答","status":1}]
     * product_intro : 撒旦法GV是
     * mis_id : 17
     * trade_rule : [{"id":"1613975481012","sort":0,"behaviorDescription":"","timing":"","t":"","descriptionCopywriting":""}]
     * rate_info : [{"id":1,"label":"申购费","value":"1","status":1},{"id":2,"label":"托管费","value":"","status":0},{"id":3,"label":"管理费","value":"","status":0},{"id":4,"label":"赎回费","value":"","status":0},{"id":5,"label":"终止费","value":"","status":0},{"id":6,"label":"佣金","value":"","status":0}]
     * product_info : &lt;p&gt;阿三哥发丧放的v&lt;/p&gt;
     * faq_list : [{"id":17,"question":"如何注册12","answer":"&lt;p&gt;注册哈哈哈哈哈123&lt;/p&gt;","class_id":17,"sort":"1","is_deleted":0,"app_id":3,"created_at":"2021-02-20 15:44:15","updated_at":"2021-02-20 15:44:15","class_name":"如何注册？","added":true},{"id":16,"question":"JN-问答1","answer":"&lt;p&gt;解答1&lt;/p&gt;&lt;p&gt;test&lt;/p&gt;","class_id":23,"sort":5,"is_deleted":0,"app_id":3,"created_at":"2021-02-20 14:23:07","updated_at":"2021-02-22 09:34:51","class_name":"JN","added":true},{"id":18,"question":"JN-问答2哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈","answer":"&lt;p&gt;吼吼吼吼吼吼吼吼吼吼吼吼吼吼吼&lt;/p&gt;&lt;p&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;有有有有有有有&lt;/p&gt;","class_id":23,"sort":6,"is_deleted":0,"app_id":3,"created_at":"2021-02-22 11:25:28","updated_at":"2021-02-22 11:26:48","class_name":"JN","added":true}]
     * is_settle : 1
     * grant_earning : 0.0000
     * is_show : 1
     * progress : 0.00
     * created_at : 2021-02-22 14:31:57
     * updated_at : 2021-02-22 14:47:21
     * total_earning : 0
     * net_value : 0.0000
     * date_time :
     * english_abbreviation : USDT
     * chinese_name : 虚拟币
     * client_display_precision : 4
     * raise_status : 2
     * raise_status_name : 募集中
     * cycle_day : 1
     * category_name : 测试
     * earning_type : {"id":21,"name":"JN-测试","logo":"","introduction":"哈哈哈","is_deleted":0,"app_id":3,"created_at":"2021-02-20 18:25:08","updated_at":"2021-02-20 18:25:08"}
     * mis : {"id":17,"name":"产品234","logo":"http://ckfpmapi.coinka.cn/storage/2021_02_19/04149f6b9130ed43f23f1e75117a13f96772.png","introduction":"&lt;p&gt;&lt;br&gt;&lt;/p&gt;","is_deleted":0,"app_id":3,"created_at":"2021-02-19 09:40:29","updated_at":"2021-02-19 09:40:29"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String app_id;
        private String title;
        private String category_id;
        private String currency_id;
        private String raise_amount;
        private String is_raise_out;
        private String buy_max_count;
        private String buy_min_num;
        private String buy_max_num;
        private String raise_start_time;
        private String raise_end_time;
        private String run_start_time;
        private String run_end_time;
        private String settle_time;
        private String grant_earning_count;
        private String expect_rate_title;
        private String expect_rate;
        private String rate_cycle;
        private String earning_type_id;
        private String product_intro;
        private String mis_id;
        private String product_info;
        private String is_settle;
        private String grant_earning;
        private String is_show;
        private String progress;
        private String created_at;
        private String updated_at;
        private String total_earning;
        private String net_value;
        private String date_time;
        private String english_abbreviation;
        private String chinese_name;
        private int client_display_precision;
        private int raise_status;
        private String raise_status_name;
        private String cycle_day;
        private String category_name;
        /**
         * id : 21
         * name : JN-测试
         * logo : 
         * introduction : 哈哈哈
         * is_deleted : 0
         * app_id : 3
         * created_at : 2021-02-20 18:25:08
         * updated_at : 2021-02-20 18:25:08
         */

        private EarningTypeBean earning_type;
        /**
         * id : 17
         * name : 产品234
         * logo : http://ckfpmapi.coinka.cn/storage/2021_02_19/04149f6b9130ed43f23f1e75117a13f96772.png
         * introduction : &lt;p&gt;&lt;br&gt;&lt;/p&gt;
         * is_deleted : 0
         * app_id : 3
         * created_at : 2021-02-19 09:40:29
         * updated_at : 2021-02-19 09:40:29
         */

        private MisBean mis;
        /**
         * id : 1613975449830
         * sort : 3
         * backgroundColor : #E5F7F4
         * textColor : #04AD90
         * text : 标签1
         */

        private List<TagBean> tag;
        /**
         * id : 1
         * label : 资管团队
         * status : 0
         */

        private List<ShowTagBean> show_tag;
        /**
         * id : 1613975481012
         * sort : 0
         * behaviorDescription :
         * timing :
         * t :
         * descriptionCopywriting :
         */

        private List<TradeRuleBean> trade_rule;
        /**
         * id : 1
         * label : 申购费
         * value : 1
         * status : 1
         */

        private List<RateInfoBean> rate_info;
        /**
         * id : 17
         * question : 如何注册12
         * answer : &lt;p&gt;注册哈哈哈哈哈123&lt;/p&gt;
         * class_id : 17
         * sort : 1
         * is_deleted : 0
         * app_id : 3
         * created_at : 2021-02-20 15:44:15
         * updated_at : 2021-02-20 15:44:15
         * class_name : 如何注册？
         * added : true
         */

        private List<FaqListBean> faq_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCurrency_id() {
            return currency_id;
        }

        public void setCurrency_id(String currency_id) {
            this.currency_id = currency_id;
        }

        public String getRaise_amount() {
            return raise_amount;
        }

        public void setRaise_amount(String raise_amount) {
            this.raise_amount = raise_amount;
        }

        public String getIs_raise_out() {
            return is_raise_out;
        }

        public void setIs_raise_out(String is_raise_out) {
            this.is_raise_out = is_raise_out;
        }

        public String getBuy_max_count() {
            return buy_max_count;
        }

        public void setBuy_max_count(String buy_max_count) {
            this.buy_max_count = buy_max_count;
        }

        public String getBuy_min_num() {
            return buy_min_num;
        }

        public void setBuy_min_num(String buy_min_num) {
            this.buy_min_num = buy_min_num;
        }

        public String getBuy_max_num() {
            return buy_max_num;
        }

        public void setBuy_max_num(String buy_max_num) {
            this.buy_max_num = buy_max_num;
        }

        public String getRaise_start_time() {
            return raise_start_time;
        }

        public void setRaise_start_time(String raise_start_time) {
            this.raise_start_time = raise_start_time;
        }

        public String getRaise_end_time() {
            return raise_end_time;
        }

        public void setRaise_end_time(String raise_end_time) {
            this.raise_end_time = raise_end_time;
        }

        public String getRun_start_time() {
            return run_start_time;
        }

        public void setRun_start_time(String run_start_time) {
            this.run_start_time = run_start_time;
        }

        public String getRun_end_time() {
            return run_end_time;
        }

        public void setRun_end_time(String run_end_time) {
            this.run_end_time = run_end_time;
        }

        public String getSettle_time() {
            return settle_time;
        }

        public void setSettle_time(String settle_time) {
            this.settle_time = settle_time;
        }

        public String getGrant_earning_count() {
            return grant_earning_count;
        }

        public void setGrant_earning_count(String grant_earning_count) {
            this.grant_earning_count = grant_earning_count;
        }

        public String getExpect_rate_title() {
            return expect_rate_title;
        }

        public void setExpect_rate_title(String expect_rate_title) {
            this.expect_rate_title = expect_rate_title;
        }

        public String getExpect_rate() {
            return expect_rate;
        }

        public void setExpect_rate(String expect_rate) {
            this.expect_rate = expect_rate;
        }

        public String getRate_cycle() {
            return rate_cycle;
        }

        public void setRate_cycle(String rate_cycle) {
            this.rate_cycle = rate_cycle;
        }

        public String getEarning_type_id() {
            return earning_type_id;
        }

        public void setEarning_type_id(String earning_type_id) {
            this.earning_type_id = earning_type_id;
        }

        public String getProduct_intro() {
            return product_intro;
        }

        public void setProduct_intro(String product_intro) {
            this.product_intro = product_intro;
        }

        public String getMis_id() {
            return mis_id;
        }

        public void setMis_id(String mis_id) {
            this.mis_id = mis_id;
        }

        public String getProduct_info() {
            return product_info;
        }

        public void setProduct_info(String product_info) {
            this.product_info = product_info;
        }

        public String getIs_settle() {
            return is_settle;
        }

        public void setIs_settle(String is_settle) {
            this.is_settle = is_settle;
        }

        public String getGrant_earning() {
            return grant_earning;
        }

        public void setGrant_earning(String grant_earning) {
            this.grant_earning = grant_earning;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getTotal_earning() {
            return total_earning;
        }

        public void setTotal_earning(String total_earning) {
            this.total_earning = total_earning;
        }

        public String getNet_value() {
            return net_value;
        }

        public void setNet_value(String net_value) {
            this.net_value = net_value;
        }

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }

        public String getEnglish_abbreviation() {
            return english_abbreviation;
        }

        public void setEnglish_abbreviation(String english_abbreviation) {
            this.english_abbreviation = english_abbreviation;
        }

        public String getChinese_name() {
            return chinese_name;
        }

        public void setChinese_name(String chinese_name) {
            this.chinese_name = chinese_name;
        }

        public int getClient_display_precision() {
            return client_display_precision;
        }

        public void setClient_display_precision(int client_display_precision) {
            this.client_display_precision = client_display_precision;
        }

        public int getRaise_status() {
            return raise_status;
        }

        public void setRaise_status(int raise_status) {
            this.raise_status = raise_status;
        }

        public String getRaise_status_name() {
            return raise_status_name;
        }

        public void setRaise_status_name(String raise_status_name) {
            this.raise_status_name = raise_status_name;
        }

        public String getCycle_day() {
            return cycle_day;
        }

        public void setCycle_day(String cycle_day) {
            this.cycle_day = cycle_day;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public EarningTypeBean getEarning_type() {
            return earning_type;
        }

        public void setEarning_type(EarningTypeBean earning_type) {
            this.earning_type = earning_type;
        }

        public MisBean getMis() {
            return mis;
        }

        public void setMis(MisBean mis) {
            this.mis = mis;
        }

        public List<TagBean> getTag() {
            return tag;
        }

        public void setTag(List<TagBean> tag) {
            this.tag = tag;
        }

        public List<ShowTagBean> getShow_tag() {
            return show_tag;
        }

        public void setShow_tag(List<ShowTagBean> show_tag) {
            this.show_tag = show_tag;
        }

        public List<TradeRuleBean> getTrade_rule() {
            return trade_rule;
        }

        public void setTrade_rule(List<TradeRuleBean> trade_rule) {
            this.trade_rule = trade_rule;
        }

        public List<RateInfoBean> getRate_info() {
            return rate_info;
        }

        public void setRate_info(List<RateInfoBean> rate_info) {
            this.rate_info = rate_info;
        }

        public List<FaqListBean> getFaq_list() {
            return faq_list;
        }

        public void setFaq_list(List<FaqListBean> faq_list) {
            this.faq_list = faq_list;
        }

        public static class EarningTypeBean {
            private int id;
            private String name;
            private String logo;
            private String introduction;
            private int is_deleted;
            private int app_id;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public int getIs_deleted() {
                return is_deleted;
            }

            public void setIs_deleted(int is_deleted) {
                this.is_deleted = is_deleted;
            }

            public int getApp_id() {
                return app_id;
            }

            public void setApp_id(int app_id) {
                this.app_id = app_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }

        public static class MisBean {
            private int id;
            private String name;
            private String logo;
            private String introduction;
            private int is_deleted;
            private int app_id;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public int getIs_deleted() {
                return is_deleted;
            }

            public void setIs_deleted(int is_deleted) {
                this.is_deleted = is_deleted;
            }

            public int getApp_id() {
                return app_id;
            }

            public void setApp_id(int app_id) {
                this.app_id = app_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }

        public static class TagBean {
            private String id;
            private String sort;
            private String backgroundColor;
            private String textColor;
            private String text;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getBackgroundColor() {
                return backgroundColor;
            }

            public void setBackgroundColor(String backgroundColor) {
                this.backgroundColor = backgroundColor;
            }

            public String getTextColor() {
                return textColor;
            }

            public void setTextColor(String textColor) {
                this.textColor = textColor;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        public static class ShowTagBean {
            private int id;
            private String label;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class TradeRuleBean {
            private String id;
            private int sort;
            private String behaviorDescription;
            private String timing;
            private String t;
            private String descriptionCopywriting;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getBehaviorDescription() {
                return behaviorDescription;
            }

            public void setBehaviorDescription(String behaviorDescription) {
                this.behaviorDescription = behaviorDescription;
            }

            public String getTiming() {
                return timing;
            }

            public void setTiming(String timing) {
                this.timing = timing;
            }

            public String getT() {
                return t;
            }

            public void setT(String t) {
                this.t = t;
            }

            public String getDescriptionCopywriting() {
                return descriptionCopywriting;
            }

            public void setDescriptionCopywriting(String descriptionCopywriting) {
                this.descriptionCopywriting = descriptionCopywriting;
            }
        }

        public static class RateInfoBean {
            private int id;
            private String label;
            private String value;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class FaqListBean {
            private int id;
            private String question;
            private String answer;
            private int class_id;
            private String sort;
            private int is_deleted;
            private int app_id;
            private String created_at;
            private String updated_at;
            private String class_name;
            private boolean added;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public int getClass_id() {
                return class_id;
            }

            public void setClass_id(int class_id) {
                this.class_id = class_id;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public int getIs_deleted() {
                return is_deleted;
            }

            public void setIs_deleted(int is_deleted) {
                this.is_deleted = is_deleted;
            }

            public int getApp_id() {
                return app_id;
            }

            public void setApp_id(int app_id) {
                this.app_id = app_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getClass_name() {
                return class_name;
            }

            public void setClass_name(String class_name) {
                this.class_name = class_name;
            }

            public boolean isAdded() {
                return added;
            }

            public void setAdded(boolean added) {
                this.added = added;
            }
        }
    }
}
