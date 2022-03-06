package com.unique.blockchain.nft.domain.me;

import com.unique.blockchain.nft.domain.BaseBean;

/**
 * 商家注册 Bean对象
 * @author 潘浩
 */
public class MerchantRegisterBean extends BaseBean {

    /**
     * 钱包地扯
     */
    private String walletAddr;

    /**
     * 账号
     */
    private String accountName;

    /**
     * 密码
     */
    private String password;

    /**
     * 单位类型
     */
    private Integer type;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 社会信用代码
     */
    private String creditCode;

    /**
     * 企业对公银行账号
     */
    private String bankCard;

    /**
     * 法定代表人姓名
     */
    private String artificialPerson;

    /**
     * 身份证号
     */
    private String certificateNo;

    /**
     * 通讯地址（注册地址）
     */
    private String address;

    /**
     * 营业执照
     */
    private String businessLicence;

    /**
     * 身份证正面照
     */
    private String certificateFront;

    /**
     * 身份证反面照
     */
    private String certificateBack;

    /**
     * 法定代表人归属地 （广东-广州）
     */
    private String homeLocation;

    /**
     * 手机号
     */
    private String mobile;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getArtificialPerson() {
        return artificialPerson;
    }

    public void setArtificialPerson(String artificialPerson) {
        this.artificialPerson = artificialPerson;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWalletAddr() {
        return walletAddr;
    }

    public void setWalletAddr(String walletAddr) {
        this.walletAddr = walletAddr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
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

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
