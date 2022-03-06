package com.unique.blockchain.nft.domain.wallet;

import com.google.protobuf.Message;
import com.unique.blockchain.nft.infrastructure.other.Numeric;

import wallet.core.java.AnySigner;
import wallet.core.jni.CoinType;
import wallet.core.jni.proto.Cosmos;
import wallet.core.jni.proto.Ethereum;

public class WalletCore implements IWalletCore{


    public static String Gaussign(Message input, CoinType coinType){

        return _sign(input,coinType);
    }
    public static String Ethsign(Message input, CoinType coinType){

        return eth_sign(input,coinType);
    }
    private static String _sign(Message input, CoinType coinType){
        try {
            Cosmos.SigningOutput sign = AnySigner.sign(input, coinType, Cosmos.SigningOutput.parser());
            return sign.getJson();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String eth_sign(Message input, CoinType coinType){
        try {
            Ethereum.SigningOutput sign = AnySigner.sign(input, coinType, Ethereum.SigningOutput.parser());
            return Numeric.INSTANCE.toHexString(sign.getEncoded().toByteArray()) ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String mnemonic() {
        return null;
    }

    @Override
    public String getAddress(CoinType coinType, String path) {
        return null;
    }

    @Override
    public String getDenom() {
        return null;
    }
}
