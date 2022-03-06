package com.space.exchange.biz.net.bean;

import java.math.BigDecimal;
import java.util.List;

public class  AssetsBillBean extends BaseResponse {


    /**
     * current_page : 1
     * data : [{"order_no":"214815831316537344","user_id":1,"scene":"recharge","position":"type_available","currency_id":"1","english_abbreviation":"USDT","num":"9.0000000000000000","action_user":"system","remarks":[],"source_order_no":"214815831203291136","created_at":"2021-02-01T10:41:25.000000Z","updated_at":"2021-02-01T10:41:25.000000Z"},{"order_no":"214818573594439680","user_id":1,"scene":"recharge","position":"type_available","currency_id":"1","english_abbreviation":"USDT","num":"9.0000000000000000","action_user":"system","remarks":[],"source_order_no":"214818573426667521","created_at":"2021-02-01T10:52:19.000000Z","updated_at":"2021-02-01T10:52:19.000000Z"},{"order_no":"214818844504535040","user_id":1,"scene":"recharge","position":"type_available","currency_id":"1","english_abbreviation":"USDT","num":"9.0000000000000000","action_user":"system","remarks":[],"source_order_no":"214818819309350912","created_at":"2021-02-01T10:53:23.000000Z","updated_at":"2021-02-01T10:53:23.000000Z"},{"order_no":"215090716945223680","user_id":1,"scene":"draw","position":"type_available","currency_id":"1","english_abbreviation":"USDT","num":"-0.1000000000000000","action_user":"system","remarks":[],"source_order_no":"215090716827783169","created_at":"2021-02-02T04:53:43.000000Z","updated_at":"2021-02-02T04:53:43.000000Z"},{"order_no":"215090717062664192","user_id":1,"scene":"draw","position":"type_draw","currency_id":"1","english_abbreviation":"USDT","num":"0.1000000000000000","action_user":"system","remarks":[],"source_order_no":"215090716827783169","created_at":"2021-02-02T04:53:43.000000Z","updated_at":"2021-02-02T04:53:43.000000Z"},{"order_no":"215091623527260160","user_id":1,"scene":"draw","position":"type_available","currency_id":"1","english_abbreviation":"USDT","num":"-0.1000000000000000","action_user":"system","remarks":[],"source_order_no":"215091618716393473","created_at":"2021-02-02T04:57:19.000000Z","updated_at":"2021-02-02T04:57:19.000000Z"},{"order_no":"215091623858610176","user_id":1,"scene":"draw","position":"type_draw","currency_id":"1","english_abbreviation":"USDT","num":"0.1000000000000000","action_user":"system","remarks":[],"source_order_no":"215091618716393473","created_at":"2021-02-02T04:57:19.000000Z","updated_at":"2021-02-02T04:57:19.000000Z"},{"order_no":"215100971846545408","user_id":1,"scene":"draw","position":"type_available","currency_id":"1","english_abbreviation":"USDT","num":"-0.1000000000000000","action_user":"system","remarks":[],"source_order_no":"215100971750076417","created_at":"2021-02-02T05:34:28.000000Z","updated_at":"2021-02-02T05:34:28.000000Z"},{"order_no":"215100971980763136","user_id":1,"scene":"draw","position":"type_draw","currency_id":"1","english_abbreviation":"USDT","num":"0.1000000000000000","action_user":"system","remarks":[],"source_order_no":"215100971750076417","created_at":"2021-02-02T05:34:28.000000Z","updated_at":"2021-02-02T05:34:28.000000Z"},{"order_no":"215101182291554304","user_id":1,"scene":"draw","position":"type_available","currency_id":"1","english_abbreviation":"USDT","num":"-0.1000000000000000","action_user":"system","remarks":[],"source_order_no":"215101182249611264","created_at":"2021-02-02T05:35:18.000000Z","updated_at":"2021-02-02T05:35:18.000000Z"}]
     * first_page_url : http://172.18.82.149:9530/assets_rpc/getAssetsBills?pn=1
     * from : 1
     * last_page : 3
     * last_page_url : http://172.18.82.149:9530/assets_rpc/getAssetsBills?pn=3
     * next_page_url : http://172.18.82.149:9530/assets_rpc/getAssetsBills?pn=2
     * path : http://172.18.82.149:9530/assets_rpc/getAssetsBills
     * per_page : 10
     * prev_page_url : null
     * to : 10
     * total : 23
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private String next_page_url;
        private String path;
        private int per_page;
        private Object prev_page_url;
        private int to;
        private int total;
        /**
         * order_no : 214815831316537344
         * user_id : 1
         * scene : recharge
         * position : type_available
         * currency_id : 1
         * english_abbreviation : USDT
         * num : 9.0000000000000000
         * action_user : system
         * remarks : []
         * source_order_no : 214815831203291136
         * created_at : 2021-02-01T10:41:25.000000Z
         * updated_at : 2021-02-01T10:41:25.000000Z
         */

        private List<DataBean1> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public String getFirst_page_url() {
            return first_page_url;
        }

        public void setFirst_page_url(String first_page_url) {
            this.first_page_url = first_page_url;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getLast_page_url() {
            return last_page_url;
        }

        public void setLast_page_url(String last_page_url) {
            this.last_page_url = last_page_url;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public Object getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(Object prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean1> getData() {
            return data;
        }

        public void setData(List<DataBean1> data) {
            this.data = data;
        }

        public static class DataBean1 {
            private String order_no;
            private int user_id;
            private String scene;
            private String position;
            private String currency_id;
            private String english_abbreviation;
            private BigDecimal num;
            private String action_user;
            private String source_order_no;
            private String created_at;
            private String updated_at;

            public void setRemarks(DataBean2 remarks) {
                this.remarks = remarks;
            }

            public DataBean2 getRemarks() {
                return remarks;
            }

            private DataBean2 remarks;

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getScene() {
                return scene;
            }

            public void setScene(String scene) {
                this.scene = scene;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getCurrency_id() {
                return currency_id;
            }

            public void setCurrency_id(String currency_id) {
                this.currency_id = currency_id;
            }

            public String getEnglish_abbreviation() {
                return english_abbreviation;
            }

            public void setEnglish_abbreviation(String english_abbreviation) {
                this.english_abbreviation = english_abbreviation;
            }

            public BigDecimal getNum() {
                return num;
            }

            public void setNum(BigDecimal num) {
                this.num = num;
            }

            public String getAction_user() {
                return action_user;
            }

            public void setAction_user(String action_user) {
                this.action_user = action_user;
            }

            public String getSource_order_no() {
                return source_order_no;
            }

            public void setSource_order_no(String source_order_no) {
                this.source_order_no = source_order_no;
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



            public static class DataBean2 {
                String rule_id;
                int status;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getRule_id() {
                    return rule_id;
                }

                public void setRule_id(String rule_id) {
                    this.rule_id = rule_id;
                }
            }
        }
    }
}
