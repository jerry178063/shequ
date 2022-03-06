package com.space.exchange.biz.net.bean;

import java.math.BigDecimal;

public class AssectBean  extends BaseResponse{


    /**
     * user_id : 2
     * wallet_type : coin
     * num : 0.0000000000
     * forzen_num : 0.0000000000
     * currency_forzen : {"type_coin":{"user_id":"2","wallet_type":"coin","forzen_type":"type_coin","num":"0.0000000000"},"type_draw":{"user_id":"2","wallet_type":"coin","forzen_type":"type_draw","num":"0.0000000000"},"type_income":{"user_id":"2","wallet_type":"coin","forzen_type":"type_income","num":"0.0000000000"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int user_id;
        private String wallet_type;
        private BigDecimal num;
        private String forzen_num;
        /**
         * type_coin : {"user_id":"2","wallet_type":"coin","forzen_type":"type_coin","num":"0.0000000000"}
         * type_draw : {"user_id":"2","wallet_type":"coin","forzen_type":"type_draw","num":"0.0000000000"}
         * type_income : {"user_id":"2","wallet_type":"coin","forzen_type":"type_income","num":"0.0000000000"}
         */

        private CurrencyForzenBean currency_forzen;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getWallet_type() {
            return wallet_type;
        }

        public void setWallet_type(String wallet_type) {
            this.wallet_type = wallet_type;
        }

        public BigDecimal getNum() {
            return num;
        }

        public void setNum(BigDecimal num) {
            this.num = num;
        }

        public String getForzen_num() {
            return forzen_num;
        }

        public void setForzen_num(String forzen_num) {
            this.forzen_num = forzen_num;
        }

        public CurrencyForzenBean getCurrency_forzen() {
            return currency_forzen;
        }

        public void setCurrency_forzen(CurrencyForzenBean currency_forzen) {
            this.currency_forzen = currency_forzen;
        }

        public static class CurrencyForzenBean {
            /**
             * user_id : 2
             * wallet_type : coin
             * forzen_type : type_coin
             * num : 0.0000000000
             */

            private TypeCoinBean type_coin;
            /**
             * user_id : 2
             * wallet_type : coin
             * forzen_type : type_draw
             * num : 0.0000000000
             */

            private TypeDrawBean type_draw;
            /**
             * user_id : 2
             * wallet_type : coin
             * forzen_type : type_income
             * num : 0.0000000000
             */

            private TypeIncomeBean type_income;

            public TypeCoinBean getType_coin() {
                return type_coin;
            }

            public void setType_coin(TypeCoinBean type_coin) {
                this.type_coin = type_coin;
            }

            public TypeDrawBean getType_draw() {
                return type_draw;
            }

            public void setType_draw(TypeDrawBean type_draw) {
                this.type_draw = type_draw;
            }

            public TypeIncomeBean getType_income() {
                return type_income;
            }

            public void setType_income(TypeIncomeBean type_income) {
                this.type_income = type_income;
            }

            public static class TypeCoinBean {
                private String user_id;
                private String wallet_type;
                private String forzen_type;
                private BigDecimal num;

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getWallet_type() {
                    return wallet_type;
                }

                public void setWallet_type(String wallet_type) {
                    this.wallet_type = wallet_type;
                }

                public String getForzen_type() {
                    return forzen_type;
                }

                public void setForzen_type(String forzen_type) {
                    this.forzen_type = forzen_type;
                }

                public BigDecimal getNum() {
                    return num;
                }

                public void setNum(BigDecimal num) {
                    this.num = num;
                }
            }

            public static class TypeDrawBean {
                private String user_id;
                private String wallet_type;
                private String forzen_type;
                private BigDecimal num;

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getWallet_type() {
                    return wallet_type;
                }

                public void setWallet_type(String wallet_type) {
                    this.wallet_type = wallet_type;
                }

                public String getForzen_type() {
                    return forzen_type;
                }

                public void setForzen_type(String forzen_type) {
                    this.forzen_type = forzen_type;
                }

                public BigDecimal getNum() {
                    return num;
                }

                public void setNum(BigDecimal num) {
                    this.num = num;
                }
            }

            public static class TypeIncomeBean {
                private String user_id;
                private String wallet_type;
                private String forzen_type;
                private BigDecimal num;

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getWallet_type() {
                    return wallet_type;
                }

                public void setWallet_type(String wallet_type) {
                    this.wallet_type = wallet_type;
                }

                public String getForzen_type() {
                    return forzen_type;
                }

                public void setForzen_type(String forzen_type) {
                    this.forzen_type = forzen_type;
                }

                public BigDecimal getNum() {
                    return num;
                }

                public void setNum(BigDecimal num) {
                    this.num = num;
                }
            }
        }
    }
}
