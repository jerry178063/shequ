package com.space.exchange.biz.net.response;

import com.space.exchange.biz.net.bean.BaseResponse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class UserListCurrentResponse extends BaseResponse implements Serializable {


    /**
     * total_cny : 649620.00
     * data : [{"user_id":2,"wallet_type":"coin","num":"100190.000000","forzen_num":"60.000000","currency_forzen":{"type_coin":{"user_id":"2","wallet_type":"coin","forzen_type":"type_coin","num":"60.0000000000"},"type_draw":{"user_id":"2","wallet_type":"coin","forzen_type":"type_draw","num":"0.0000000000"},"type_income":{"user_id":"2","wallet_type":"coin","forzen_type":"type_income","num":"0.0000000000"}},"currency_id":1,"logo":"http://ckfpmapi.coinka.cn/storage/2021_02_04/85fbffe54132226d71c4000fd3196bf85234.png","english_abbreviation":"USDT","chinese_name":"211","client_display_precision":6,"chains":[{"chain_id":2,"label_name":"ETH","chain_name":"TRC20","need_contract":2,"smart_contract":"xxxxxxxxxxxxxx","can_recharge":2,"can_withdraw":1,"withdraw_fee":"0.0010000000000000","min_recharge_amount":"0.0100000000000000","min_withdraw_amount":"0.0100000000000000","recharge_copywriting":"","draw_copywriting":""},{"chain_id":4,"label_name":"ETH","chain_name":"1","need_contract":2,"smart_contract":"2","can_recharge":1,"can_withdraw":1,"withdraw_fee":"5.0000000000000000","min_recharge_amount":"3.0000000000000000","min_withdraw_amount":"4.0000000000000000","recharge_copywriting":"6","draw_copywriting":"7"},{"chain_id":5,"label_name":"ETH","chain_name":"t","need_contract":1,"smart_contract":"23","can_recharge":1,"can_withdraw":1,"withdraw_fee":"5.0000000000000000","min_recharge_amount":"3.0000000000000000","min_withdraw_amount":"4.0000000000000000","recharge_copywriting":"233","draw_copywriting":"333"},{"chain_id":6,"label_name":"ETH","chain_name":"h","need_contract":2,"smart_contract":"n1","can_recharge":1,"can_withdraw":1,"withdraw_fee":"5.0000000000000000","min_recharge_amount":"5.0000000000000000","min_withdraw_amount":"5.0000000000000000","recharge_copywriting":"51","draw_copywriting":"51"}],"total_cny":"649620.00","updated_at":"2021-02-04T02:44:55.000000Z","created_at":"2021-01-27T23:47:45.000000Z"},{"user_id":2,"wallet_type":"coin","num":"0.0000","forzen_num":"0.0000","currency_forzen":{"type_coin":{"user_id":"2","wallet_type":"coin","forzen_type":"type_coin","num":"0.0000000000"},"type_draw":{"user_id":"2","wallet_type":"coin","forzen_type":"type_draw","num":"0.0000000000"},"type_income":{"user_id":"2","wallet_type":"coin","forzen_type":"type_income","num":"0.0000000000"}},"currency_id":3,"logo":"https://ckfp.xyz/storage/files/logo/usdt.jpg","english_abbreviation":"XRP","chinese_name":"瑞波1","client_display_precision":4,"chains":[],"total_cny":"0.00","updated_at":"2021-02-02T19:19:56.000000Z","created_at":"2021-02-02T18:57:29.000000Z"}]
     * income : 0.0000000000000000
     * usdt_total : 100250.0000
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private String total_cny;
        private String income;
        private String usdt_total;
        /**
         * user_id : 2
         * wallet_type : coin
         * num : 100190.000000
         * forzen_num : 60.000000
         * currency_forzen : {"type_coin":{"user_id":"2","wallet_type":"coin","forzen_type":"type_coin","num":"60.0000000000"},"type_draw":{"user_id":"2","wallet_type":"coin","forzen_type":"type_draw","num":"0.0000000000"},"type_income":{"user_id":"2","wallet_type":"coin","forzen_type":"type_income","num":"0.0000000000"}}
         * currency_id : 1
         * logo : http://ckfpmapi.coinka.cn/storage/2021_02_04/85fbffe54132226d71c4000fd3196bf85234.png
         * english_abbreviation : USDT
         * chinese_name : 211
         * client_display_precision : 6
         * chains : [{"chain_id":2,"label_name":"ETH","chain_name":"TRC20","need_contract":2,"smart_contract":"xxxxxxxxxxxxxx","can_recharge":2,"can_withdraw":1,"withdraw_fee":"0.0010000000000000","min_recharge_amount":"0.0100000000000000","min_withdraw_amount":"0.0100000000000000","recharge_copywriting":"","draw_copywriting":""},{"chain_id":4,"label_name":"ETH","chain_name":"1","need_contract":2,"smart_contract":"2","can_recharge":1,"can_withdraw":1,"withdraw_fee":"5.0000000000000000","min_recharge_amount":"3.0000000000000000","min_withdraw_amount":"4.0000000000000000","recharge_copywriting":"6","draw_copywriting":"7"},{"chain_id":5,"label_name":"ETH","chain_name":"t","need_contract":1,"smart_contract":"23","can_recharge":1,"can_withdraw":1,"withdraw_fee":"5.0000000000000000","min_recharge_amount":"3.0000000000000000","min_withdraw_amount":"4.0000000000000000","recharge_copywriting":"233","draw_copywriting":"333"},{"chain_id":6,"label_name":"ETH","chain_name":"h","need_contract":2,"smart_contract":"n1","can_recharge":1,"can_withdraw":1,"withdraw_fee":"5.0000000000000000","min_recharge_amount":"5.0000000000000000","min_withdraw_amount":"5.0000000000000000","recharge_copywriting":"51","draw_copywriting":"51"}]
         * total_cny : 649620.00
         * updated_at : 2021-02-04T02:44:55.000000Z
         * created_at : 2021-01-27T23:47:45.000000Z
         */

        private List<DataBeans> data;

        public String getTotal_cny() {
            return total_cny;
        }

        public void setTotal_cny(String total_cny) {
            this.total_cny = total_cny;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getUsdt_total() {
            return usdt_total;
        }

        public void setUsdt_total(String usdt_total) {
            this.usdt_total = usdt_total;
        }

        public List<DataBeans> getData() {
            return data;
        }

        public void setData(List<DataBeans> data) {
            this.data = data;
        }

        public static class DataBeans implements  Serializable {
            private int user_id;
            private String wallet_type;
            private BigDecimal num;
            private BigDecimal forzen_num;
            /**
             * type_coin : {"user_id":"2","wallet_type":"coin","forzen_type":"type_coin","num":"60.0000000000"}
             * type_draw : {"user_id":"2","wallet_type":"coin","forzen_type":"type_draw","num":"0.0000000000"}
             * type_income : {"user_id":"2","wallet_type":"coin","forzen_type":"type_income","num":"0.0000000000"}
             */

            private CurrencyForzenBean currency_forzen;
            private int currency_id;
            private String logo;
            private String english_abbreviation;
            private String chinese_name;
            private int client_display_precision;
            private String total_cny;
            private String updated_at;
            private String created_at;
            /**
             * chain_id : 2
             * label_name : ETH
             * chain_name : TRC20
             * need_contract : 2
             * smart_contract : xxxxxxxxxxxxxx
             * can_recharge : 2
             * can_withdraw : 1
             * withdraw_fee : 0.0010000000000000
             * min_recharge_amount : 0.0100000000000000
             * min_withdraw_amount : 0.0100000000000000
             * recharge_copywriting :
             * draw_copywriting :
             */

            private List<ChainsBean> chains;

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

            public BigDecimal getForzen_num() {
                return forzen_num;
            }

            public void setForzen_num(BigDecimal forzen_num) {
                this.forzen_num = forzen_num;
            }

            public CurrencyForzenBean getCurrency_forzen() {
                return currency_forzen;
            }

            public void setCurrency_forzen(CurrencyForzenBean currency_forzen) {
                this.currency_forzen = currency_forzen;
            }

            public int getCurrency_id() {
                return currency_id;
            }

            public void setCurrency_id(int currency_id) {
                this.currency_id = currency_id;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
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

            public String getTotal_cny() {
                return total_cny;
            }

            public void setTotal_cny(String total_cny) {
                this.total_cny = total_cny;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public List<ChainsBean> getChains() {
                return chains;
            }

            public void setChains(List<ChainsBean> chains) {
                this.chains = chains;
            }

            public static class CurrencyForzenBean implements Serializable{
                /**
                 * user_id : 2
                 * wallet_type : coin
                 * forzen_type : type_coin
                 * num : 60.0000000000
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

                public static class TypeCoinBean implements Serializable {
                    private String user_id;
                    private String wallet_type;
                    private String forzen_type;
                    private String num;

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

                    public String getNum() {
                        return num;
                    }

                    public void setNum(String num) {
                        this.num = num;
                    }
                }

                public static class TypeDrawBean implements Serializable {
                    private String user_id;
                    private String wallet_type;
                    private String forzen_type;
                    private String num;

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

                    public String getNum() {
                        return num;
                    }

                    public void setNum(String num) {
                        this.num = num;
                    }
                }

                public static class TypeIncomeBean implements Serializable {
                    private String user_id;
                    private String wallet_type;
                    private String forzen_type;
                    private String num;

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

                    public String getNum() {
                        return num;
                    }

                    public void setNum(String num) {
                        this.num = num;
                    }
                }
            }

            public static class ChainsBean implements Serializable{
                private int chain_id;
                private String label_name;
                private String chain_name;
                private int need_contract;
                private String smart_contract;
                private int can_recharge;
                private int can_withdraw;
                private BigDecimal withdraw_fee;
                private String min_recharge_amount;
                private BigDecimal min_withdraw_amount;
                private String recharge_copywriting;
                private String draw_copywriting;
                private boolean isselected;

                public boolean isIsselected() {
                    return isselected;
                }

                public void setIsselected(boolean isselected) {
                    this.isselected = isselected;
                }

                public int getChain_id() {
                    return chain_id;
                }

                public void setChain_id(int chain_id) {
                    this.chain_id = chain_id;
                }

                public String getLabel_name() {
                    return label_name;
                }

                public void setLabel_name(String label_name) {
                    this.label_name = label_name;
                }

                public String getChain_name() {
                    return chain_name;
                }

                public void setChain_name(String chain_name) {
                    this.chain_name = chain_name;
                }

                public int getNeed_contract() {
                    return need_contract;
                }

                public void setNeed_contract(int need_contract) {
                    this.need_contract = need_contract;
                }

                public String getSmart_contract() {
                    return smart_contract;
                }

                public void setSmart_contract(String smart_contract) {
                    this.smart_contract = smart_contract;
                }

                public int getCan_recharge() {
                    return can_recharge;
                }

                public void setCan_recharge(int can_recharge) {
                    this.can_recharge = can_recharge;
                }

                public int getCan_withdraw() {
                    return can_withdraw;
                }

                public void setCan_withdraw(int can_withdraw) {
                    this.can_withdraw = can_withdraw;
                }

                public BigDecimal getWithdraw_fee() {
                    return withdraw_fee;
                }

                public void setWithdraw_fee(BigDecimal withdraw_fee) {
                    this.withdraw_fee = withdraw_fee;
                }

                public String getMin_recharge_amount() {
                    return min_recharge_amount;
                }

                public void setMin_recharge_amount(String min_recharge_amount) {
                    this.min_recharge_amount = min_recharge_amount;
                }

                public BigDecimal getMin_withdraw_amount() {
                    return min_withdraw_amount;
                }

                public void setMin_withdraw_amount(BigDecimal min_withdraw_amount) {
                    this.min_withdraw_amount = min_withdraw_amount;
                }

                public String getRecharge_copywriting() {
                    return recharge_copywriting;
                }

                public void setRecharge_copywriting(String recharge_copywriting) {
                    this.recharge_copywriting = recharge_copywriting;
                }

                public String getDraw_copywriting() {
                    return draw_copywriting;
                }

                public void setDraw_copywriting(String draw_copywriting) {
                    this.draw_copywriting = draw_copywriting;
                }
            }
        }
    }
}
