package com.unique.blockchain.nft.domain.me;

import com.unique.blockchain.nft.domain.BaseBean;

public class PersonInfoBean extends BaseBean {
    private String walletAddr;
//    private String nickName;
    private String portraitUrl;
//    private int userAddressId;
//    private String nftSign;;

//    public String getNftSign() {
//        return nftSign;
//    }
//
//    public void setNftSign(String nftSign) {
//        this.nftSign = nftSign;
//    }
//
//    public int getUserAddressId() {
//        return userAddressId;
//    }
//
//    public void setUserAddressId(int userAddressId) {
//        this.userAddressId = userAddressId;
//    }

    public String getWalletAddr() {
        return walletAddr;
    }

    public void setWalletAddr(String walletAddr) {
        this.walletAddr = walletAddr;
    }

//    public String getNickName() {
//        return nickName;
//    }
//
//    public void setNickName(String nickName) {
//        this.nickName = nickName;
//    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }


}
