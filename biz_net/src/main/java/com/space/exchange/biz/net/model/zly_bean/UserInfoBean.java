package com.space.exchange.biz.net.model.zly_bean;

import com.space.exchange.biz.net.bean.BaseResponse;

public class UserInfoBean  extends BaseResponse {


    /**
     * success : true
     * data : {"id":7040,"code":"FZQC334481","name":"12345","nation":null,"internationalCode":null,"image":null,"loginPwd":"","dealPwd":"e10adc3949ba59abbe56e057f20f883e","gradeId":0,"realGrade":null,"operateCenter":null,"sex":2,"idCard":null,"qq":null,"registerDatetime":"2020-11-03 00:38:03","lastloginDatetime":null,"referrerId":7039,"contactAddress":null,"addressDetail":null,"phone":"13333333333","email":null,"receiptAddress":null,"status":1,"shareImage":null,"shareUrl":null,"active":1,"activeDatetime":"2020-11-03 00:38:30","delFlag":0,"cashlock":0,"reportcenter":0,"forbidmining":0,"miningMachineNum":0,"globalDiscount":null,"capacity":0,"location":null,"operateDatetime":"2020-11-03 00:38:30","operator":"13243727687","smsCode":null,"referrerCode":"LDPP389308","gradeName":"一般会员"}
     * logs :
     */

    private boolean success;
    /**
     * id : 7040
     * code : FZQC334481
     * name : 12345
     * nation : null
     * internationalCode : null
     * image : null
     * loginPwd :
     * dealPwd : e10adc3949ba59abbe56e057f20f883e
     * gradeId : 0
     * realGrade : null
     * operateCenter : null
     * sex : 2
     * idCard : null
     * qq : null
     * registerDatetime : 2020-11-03 00:38:03
     * lastloginDatetime : null
     * referrerId : 7039
     * contactAddress : null
     * addressDetail : null
     * phone : 13333333333
     * email : null
     * receiptAddress : null
     * status : 1
     * shareImage : null
     * shareUrl : null
     * active : 1
     * activeDatetime : 2020-11-03 00:38:30
     * delFlag : 0
     * cashlock : 0
     * reportcenter : 0
     * forbidmining : 0
     * miningMachineNum : 0
     * globalDiscount : null
     * capacity : 0
     * location : null
     * operateDatetime : 2020-11-03 00:38:30
     * operator : 13243727687
     * smsCode : null
     * referrerCode : LDPP389308
     * gradeName : 一般会员
     */

    private DataBean data;
    private String logs;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public static class DataBean {
        private int id;
        private String code;
        private String name;
        private Object nation;
        private Object internationalCode;
        private Object image;
        private String loginPwd;
        private String dealPwd;
        private int gradeId;
        private Object realGrade;
        private Object operateCenter;
        private int sex;
        private Object idCard;
        private Object qq;
        private String registerDatetime;
        private Object lastloginDatetime;
        private int referrerId;
        private Object contactAddress;
        private Object addressDetail;
        private String phone;
        private Object email;
        private Object receiptAddress;
        private int status;
        private Object shareImage;
        private Object shareUrl;
        private int active;
        private String activeDatetime;
        private int delFlag;
        private int cashlock;
        private int reportcenter;
        private int forbidmining;
        private int miningMachineNum;
        private Object globalDiscount;
        private int capacity;
        private Object location;
        private String operateDatetime;
        private String operator;
        private Object smsCode;
        private String referrerCode;
        private String gradeName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getNation() {
            return nation;
        }

        public void setNation(Object nation) {
            this.nation = nation;
        }

        public Object getInternationalCode() {
            return internationalCode;
        }

        public void setInternationalCode(Object internationalCode) {
            this.internationalCode = internationalCode;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public String getLoginPwd() {
            return loginPwd;
        }

        public void setLoginPwd(String loginPwd) {
            this.loginPwd = loginPwd;
        }

        public String getDealPwd() {
            return dealPwd;
        }

        public void setDealPwd(String dealPwd) {
            this.dealPwd = dealPwd;
        }

        public int getGradeId() {
            return gradeId;
        }

        public void setGradeId(int gradeId) {
            this.gradeId = gradeId;
        }

        public Object getRealGrade() {
            return realGrade;
        }

        public void setRealGrade(Object realGrade) {
            this.realGrade = realGrade;
        }

        public Object getOperateCenter() {
            return operateCenter;
        }

        public void setOperateCenter(Object operateCenter) {
            this.operateCenter = operateCenter;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public Object getIdCard() {
            return idCard;
        }

        public void setIdCard(Object idCard) {
            this.idCard = idCard;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
        }

        public String getRegisterDatetime() {
            return registerDatetime;
        }

        public void setRegisterDatetime(String registerDatetime) {
            this.registerDatetime = registerDatetime;
        }

        public Object getLastloginDatetime() {
            return lastloginDatetime;
        }

        public void setLastloginDatetime(Object lastloginDatetime) {
            this.lastloginDatetime = lastloginDatetime;
        }

        public int getReferrerId() {
            return referrerId;
        }

        public void setReferrerId(int referrerId) {
            this.referrerId = referrerId;
        }

        public Object getContactAddress() {
            return contactAddress;
        }

        public void setContactAddress(Object contactAddress) {
            this.contactAddress = contactAddress;
        }

        public Object getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(Object addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getReceiptAddress() {
            return receiptAddress;
        }

        public void setReceiptAddress(Object receiptAddress) {
            this.receiptAddress = receiptAddress;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getShareImage() {
            return shareImage;
        }

        public void setShareImage(Object shareImage) {
            this.shareImage = shareImage;
        }

        public Object getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(Object shareUrl) {
            this.shareUrl = shareUrl;
        }

        public int getActive() {
            return active;
        }

        public void setActive(int active) {
            this.active = active;
        }

        public String getActiveDatetime() {
            return activeDatetime;
        }

        public void setActiveDatetime(String activeDatetime) {
            this.activeDatetime = activeDatetime;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getCashlock() {
            return cashlock;
        }

        public void setCashlock(int cashlock) {
            this.cashlock = cashlock;
        }

        public int getReportcenter() {
            return reportcenter;
        }

        public void setReportcenter(int reportcenter) {
            this.reportcenter = reportcenter;
        }

        public int getForbidmining() {
            return forbidmining;
        }

        public void setForbidmining(int forbidmining) {
            this.forbidmining = forbidmining;
        }

        public int getMiningMachineNum() {
            return miningMachineNum;
        }

        public void setMiningMachineNum(int miningMachineNum) {
            this.miningMachineNum = miningMachineNum;
        }

        public Object getGlobalDiscount() {
            return globalDiscount;
        }

        public void setGlobalDiscount(Object globalDiscount) {
            this.globalDiscount = globalDiscount;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public String getOperateDatetime() {
            return operateDatetime;
        }

        public void setOperateDatetime(String operateDatetime) {
            this.operateDatetime = operateDatetime;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public Object getSmsCode() {
            return smsCode;
        }

        public void setSmsCode(Object smsCode) {
            this.smsCode = smsCode;
        }

        public String getReferrerCode() {
            return referrerCode;
        }

        public void setReferrerCode(String referrerCode) {
            this.referrerCode = referrerCode;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }
    }
}
