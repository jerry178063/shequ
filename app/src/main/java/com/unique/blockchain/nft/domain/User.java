package com.unique.blockchain.nft.domain;

import com.unique.blockchain.nft.domain.me.MifrateStandBean;
import com.unique.blockchain.nft.domain.wallet.AssectConverent;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

@Entity
public class User {

    @Id(autoincrement = true)
    Long  id;
    private String name;
    private String psd;
    private String did;
    private byte[]  did_psd;
    @Convert(/**指定转换器 **/converter = UserInfoConverent.class,/**指定数据库中的列字段**/columnType =String.class )
    private List<UserInfoItem> mObjectList;

    @Convert(/**指定转换器 **/converter = AssectConverent.class,/**指定数据库中的列字段**/columnType =String.class )
    private List<MifrateStandBean> massectList;

    @Generated(hash = 2105202477)
    public User(Long id, String name, String psd, String did, byte[] did_psd,
                List<UserInfoItem> mObjectList, List<MifrateStandBean> massectList) {
        this.id = id;
        this.name = name;
        this.psd = psd;
        this.did = did;
        this.did_psd = did_psd;
        this.mObjectList = mObjectList;
        this.massectList = massectList;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsd() {
        return this.psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public List<UserInfoItem> getMObjectList() {
        return this.mObjectList;
    }

    public void setMObjectList(List<UserInfoItem> mObjectList) {
        this.mObjectList = mObjectList;
    }

    public List<MifrateStandBean> getMassectList() {
        return this.massectList;
    }

    public void setMassectList(List<MifrateStandBean> massectList) {
        this.massectList = massectList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", psd='" + psd + '\'' +
                ", mObjectList=" + mObjectList +
                ", massectList=" + massectList +
                '}';
    }

    public String getDid() {
        return this.did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public byte[] getDid_psd() {
        return this.did_psd;
    }

    public void setDid_psd(byte[] did_psd) {
        this.did_psd = did_psd;
    }
}
