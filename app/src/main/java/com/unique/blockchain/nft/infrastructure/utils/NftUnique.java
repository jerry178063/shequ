package com.unique.blockchain.nft.infrastructure.utils;


import java.util.ArrayList;
import java.util.List;

public class NftUnique {

    private List<String> strings = new ArrayList<>();
    public void init(){
        if(strings != null){
            strings.clear();
        }
        strings.add("AETERNITY");//457
        strings.add("AION");//425
        strings.add("BINANCE");//714
        strings.add("BITCOIN");//0
        strings.add("BITCOINCASH");//145
        strings.add("BITCOINGOLD");//156
        strings.add("CALLISTO");//820
        strings.add("CARDANO");//1815
        strings.add("COSMOS");//118
        strings.add("DASH");//5
        strings.add("DECRED");//42
        strings.add("DIGIBYTE");//20
        strings.add("DOGECOIN");//3
        strings.add("EOS");//194
        strings.add("ETHEREUM");//60
        strings.add("ETHEREUMCLASSIC");//61
        strings.add("FIO");//235
        strings.add("GOCHAIN");//6060
        strings.add("GROESTLCOIN");//17
        strings.add("ICON");//74
        strings.add("IOTEX");//304
        strings.add("KAVA");//459
        strings.add("KIN");//2017
        strings.add("LITECOIN");//2
        strings.add("MONACOIN");//22
        strings.add("NEBULAS");//2718
        strings.add("NULS");//8964
        strings.add("NANO");//165
        strings.add("NEAR");//397
        strings.add("NIMIQ");//242
        strings.add("ONTOLOGY");//1024
        strings.add("POANETWORK");//178
        strings.add("QTUM");//2301
        strings.add("XRP");//144
        strings.add("SOLANA");//501
        strings.add("STELLAR");//148
        strings.add("TON");//396
        strings.add("TEZOS");//1729
        strings.add("THETA");//500
        strings.add("THUNDERTOKEN");//1001
        strings.add("NEO");//888
        strings.add("TOMOCHAIN");//889
        strings.add("TRON");//195
        strings.add("VECHAIN");//818
        strings.add("VIACOIN");//14
        strings.add("WANCHAIN");//5718350
        strings.add("ZCASH");//133
        strings.add("ZCOIN");//136
        strings.add("ZILLIQA");//313
        strings.add("ZELCASH");//19167
        strings.add("RAVENCOIN");//175
        strings.add("WAVES");//5741564
        strings.add("TERRA");//330
        strings.add("HARMONY");//1023
        strings.add("ALGORAND");//283
        strings.add("KUSAMA");//434
        strings.add("POLKADOT");//354
        strings.add("FILECOIN");//461
        strings.add("ELROND");//508
        strings.add("BANDCHAIN");//494
        strings.add("SMARTCHAINLEGACY");//10000714
        strings.add("SMARTCHAIN");//20000714
        strings.add("OASIS");//474
        strings.add("POLYGON");//966
        strings.add("THORCHAIN");//931
        strings.add("USDG");//881
        strings.add("GAUSS");//991
        strings.add("IGPC");//992
        strings.add("FEC");//993
        strings.add("GPB");//994
        strings.add("UNIQUE");//995
    }
    public String isCunZai(String name){
        if(strings != null && strings.size()> 0){
            for (int i = 0; i < strings.size(); i++) {
                if(strings.get(i).equals(name)){
                    return strings.get(i);
                }
            }
        }
        return "不存在";
    }

}
