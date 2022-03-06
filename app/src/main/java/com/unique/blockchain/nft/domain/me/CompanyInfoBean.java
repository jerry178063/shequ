package com.unique.blockchain.nft.domain.me;

import com.unique.blockchain.nft.domain.BaseBean;

public class CompanyInfoBean extends BaseBean {

    private int code;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private int type;//企业类型
        private int approvalState;//审批状态
        private String accountName;//账户名称
        private String walletAddr;//钱包地址
        private String companyName;//企业名称
        private String creditCode;//社会信用代码
        private String businessLicence;//营业执照
        private String homeLocation;//归属地
        private String artificialPerson;//法定代表人
        private String bankCard;//银行对工账户
        private String certificateNo;//证件号
        private String certificateFront;//身份证正面
        private String certificateBack;//身份证反面
        private String mobile;//手机号码
        private int state;//启动或者禁用状态
        private String address;//通讯地址
        private String cautionMoney;//保证金
        private String payCautionMoney;//已缴纳保证金
        private String endTime;//过期时间
        private String loginUrl;//商家后台访问地址
        private String approvalRemark;//失败原因

        public String getApprovalRemark() {
            return approvalRemark;
        }

        public void setApprovalRemark(String approvalRemark) {
            this.approvalRemark = approvalRemark;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getApprovalState() {
            return approvalState;
        }

        public void setApprovalState(int approvalState) {
            this.approvalState = approvalState;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getWalletAddr() {
            return walletAddr;
        }

        public void setWalletAddr(String walletAddr) {
            this.walletAddr = walletAddr;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCreditCode() {
            return creditCode;
        }

        public void setCreditCode(String creditCode) {
            this.creditCode = creditCode;
        }

        public String getBusinessLicence() {
            return businessLicence;
        }

        public void setBusinessLicence(String businessLicence) {
            this.businessLicence = businessLicence;
        }

        public String getHomeLocation() {
            return homeLocation;
        }

        public void setHomeLocation(String homeLocation) {
            this.homeLocation = homeLocation;
        }

        public String getArtificialPerson() {
            return artificialPerson;
        }

        public void setArtificialPerson(String artificialPerson) {
            this.artificialPerson = artificialPerson;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public String getCertificateNo() {
            return certificateNo;
        }

        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }

        public String getCertificateFront() {
            return certificateFront;
        }

        public void setCertificateFront(String certificateFront) {
            this.certificateFront = certificateFront;
        }

        public String getCertificateBack() {
            return certificateBack;
        }

        public void setCertificateBack(String certificateBack) {
            this.certificateBack = certificateBack;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCautionMoney() {
            return cautionMoney;
        }

        public void setCautionMoney(String cautionMoney) {
            this.cautionMoney = cautionMoney;
        }

        public String getPayCautionMoney() {
            return payCautionMoney;
        }

        public void setPayCautionMoney(String payCautionMoney) {
            this.payCautionMoney = payCautionMoney;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getLoginUrl() {
            return loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }
    }

}
