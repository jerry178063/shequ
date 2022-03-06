package com.unique.blockchain.nft.domain.wallet;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class TransferWalletBean extends BaseBean {
    private Body body;
    private AuthInfo auth_info;
    private List<String> signatures;

    public class Body{
        private List<Messages> messages;
        private String memo;
        private String timeout_height;
        private List<String> extension_options;
        private List<String> non_critical_extension_options;

        public class Messages{
            private String type;
            private String from_address;
            private String to_address;
            private List<Amount> amount;

            public class Amount{
                private String denom;
                private String amount;

                public String getDenom() {
                    return denom;
                }

                public void setDenom(String denom) {
                    this.denom = denom;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }
            }

        }


    }
    public class AuthInfo{
        private List<String> signer_infos;
        private Fee fee;

        public List<String> getSigner_infos() {
            return signer_infos;
        }

        public void setSigner_infos(List<String> signer_infos) {
            this.signer_infos = signer_infos;
        }

        public Fee getFee() {
            return fee;
        }

        public void setFee(Fee fee) {
            this.fee = fee;
        }

        public class Fee{
            private List<Amount> amount;
            private String gas_limit;
            private String payer;
            private String granter;

            public List<Amount> getAmount() {
                return amount;
            }

            public void setAmount(List<Amount> amount) {
                this.amount = amount;
            }

            public String getGas_limit() {
                return gas_limit;
            }

            public void setGas_limit(String gas_limit) {
                this.gas_limit = gas_limit;
            }

            public String getPayer() {
                return payer;
            }

            public void setPayer(String payer) {
                this.payer = payer;
            }

            public String getGranter() {
                return granter;
            }

            public void setGranter(String granter) {
                this.granter = granter;
            }

            public class Amount{
                private String denom;
                private String amount;

                public String getDenom() {
                    return denom;
                }

                public void setDenom(String denom) {
                    this.denom = denom;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }
            }

        }

    }
}
