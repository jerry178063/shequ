package com.unique.blockchain.nft.domain.wallet;

import com.unique.blockchain.nft.domain.BaseBean;

public class EthResultBean extends BaseBean {

    private int code;
    private String message;
    private Result result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        private String erc20_gas_limit;
        private String chainId;
        private String gravity_contract_address;
        private String gas_price_average;
        private String nonce;
        private String gas_price_low;
        private String eth_gas_limit;
        private String gasPrice;
        private String gas_price_high;

        public String getErc20_gas_limit() {
            return erc20_gas_limit;
        }

        public void setErc20_gas_limit(String erc20_gas_limit) {
            this.erc20_gas_limit = erc20_gas_limit;
        }

        public String getChainId() {
            return chainId;
        }

        public void setChainId(String chainId) {
            this.chainId = chainId;
        }

        public String getGravity_contract_address() {
            return gravity_contract_address;
        }

        public void setGravity_contract_address(String gravity_contract_address) {
            this.gravity_contract_address = gravity_contract_address;
        }

        public String getGas_price_average() {
            return gas_price_average;
        }

        public void setGas_price_average(String gas_price_average) {
            this.gas_price_average = gas_price_average;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        public String getGas_price_low() {
            return gas_price_low;
        }

        public void setGas_price_low(String gas_price_low) {
            this.gas_price_low = gas_price_low;
        }

        public String getEth_gas_limit() {
            return eth_gas_limit;
        }

        public void setEth_gas_limit(String eth_gas_limit) {
            this.eth_gas_limit = eth_gas_limit;
        }

        public String getGasPrice() {
            return gasPrice;
        }

        public void setGasPrice(String gasPrice) {
            this.gasPrice = gasPrice;
        }

        public String getGas_price_high() {
            return gas_price_high;
        }

        public void setGas_price_high(String gas_price_high) {
            this.gas_price_high = gas_price_high;
        }
    }

}
