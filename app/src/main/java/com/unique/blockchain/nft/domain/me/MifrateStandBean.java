package com.unique.blockchain.nft.domain.me;

public class MifrateStandBean {

    private String oldAdress;
    private String total;
    private String adress;
    private String mneninic;
    private String usdgAddress;
    private String uadgAmount;
    private String coin;
    private boolean iscom;

    @Override
    public String toString() {
        return "MifrateStandBean{" +
                "oldAdress='" + oldAdress + '\'' +
                ", total='" + total + '\'' +
                ", adress='" + adress + '\'' +
                ", mneninic='" + mneninic + '\'' +
                ", usdgAddress='" + usdgAddress + '\'' +
                ", uadgAmount='" + uadgAmount + '\'' +
                ", coin='" + coin + '\'' +
                ", iscom=" + iscom +
                ", did='" + did + '\'' +
                '}';
    }

    private String did;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public boolean isIscom() {
        return iscom;
    }

    public void setIscom(boolean iscom) {
        this.iscom = iscom;
    }

    public String getOldAdress() {
        return oldAdress;
    }

    public void setOldAdress(String oldAdress) {
        this.oldAdress = oldAdress;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMneninic() {
        return mneninic;
    }

    public void setMneninic(String mneninic) {
        this.mneninic = mneninic;
    }

    public String getUsdgAddress() {
        return usdgAddress;
    }

    public void setUsdgAddress(String usdgAddress) {
        this.usdgAddress = usdgAddress;
    }

    public String getUadgAmount() {
        return uadgAmount;
    }

    public void setUadgAmount(String uadgAmount) {
        this.uadgAmount = uadgAmount;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }
}
