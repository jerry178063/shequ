package com.unique.blockchain.nft.domain;

import java.io.Serializable;
import java.util.Arrays;

public class UserInfoItem implements Serializable {
        private String coin_name;
        private String coin_psd;
        private int num;
        private String img;
        private byte[] psd_psd;

        public byte[] getPsd_psd() {
            return psd_psd;
        }

        public UserInfoItem(String coin_name, String img) {
            this.coin_name = coin_name;
            this.img = img;
        }

        public UserInfoItem(String coin_name, String coin_psd, String img) {
            this.coin_name = coin_name;
            this.coin_psd = coin_psd;
            this.img = img;
        }

        public void setPsd_psd(byte[] psd_psd) {
            this.psd_psd = psd_psd;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getNum() {
            return num;
        }


    @Override
    public String toString() {
        return "UserInfoItem{" +
                "coin_name='" + coin_name + '\'' +
                ", coin_psd='" + coin_psd + '\'' +
                ", num=" + num +
                ", img='" + img + '\'' +
                ", psd_psd=" + Arrays.toString(psd_psd) +
                '}';
    }

    public UserInfoItem(String coin_name, String coin_psd, int num, String img) {
            this.coin_name = coin_name;
            this.coin_psd = coin_psd;
            this.num = num;
            this.img = img;
        }



        public UserInfoItem(String coin_name, String coin_psd, int num) {
            this.coin_name = coin_name;
            this.coin_psd = coin_psd;
            this.num = num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public UserInfoItem(String coin_name, String coin_psd, int num, String img, byte[] psd_psd) {
            this.coin_name = coin_name;
            this.coin_psd = coin_psd;
            this.num = num;
            this.img = img;
            this.psd_psd = psd_psd;
        }

        public String getCoin_name() {
            return coin_name;
        }

        public void setCoin_name(String coin_name) {
            this.coin_name = coin_name;
        }

        public String getCoin_psd() {
            return coin_psd;
        }

        public void setCoin_psd(String coin_psd) {
            this.coin_psd = coin_psd;
        }
    }
