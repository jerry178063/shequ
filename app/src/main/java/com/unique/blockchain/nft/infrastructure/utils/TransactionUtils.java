package com.unique.blockchain.nft.infrastructure.utils;

import com.blankj.utilcode.util.StringUtils;
import com.google.protobuf.ByteString;

import wallet.core.java.AnySigner;
import wallet.core.jni.BitcoinScript;
import wallet.core.jni.BitcoinSigHashType;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;
import wallet.core.jni.PrivateKey;
import wallet.core.jni.proto.Bitcoin;

public class TransactionUtils {

    /***
     *  btc签名生成
     * @param hdWallet
     * @param price
     * @param num
     * @param toAddress
     * @param changeAddress
     * @return
     * @throws Exception
     */
    public static String getBtcCode(HDWallet hdWallet, long price, int num, String toAddress, String changeAddress) throws Exception {

        if (CoinType.createFromValue(num) != null) {
            CoinType fromValue = CoinType.createFromValue(num);
            PrivateKey keyForCoin = hdWallet.getKeyForCoin(fromValue);
            byte[] script = BitcoinScript.lockScriptForAddress(keyForCoin.toString(), fromValue).data();
            Bitcoin.OutPoint outbuild = Bitcoin.OutPoint.newBuilder()
                    .setHash(ByteString.copyFrom(TransactionUtil.INSTANCE.hexStringToByteArray(changeAddress)))
                    .setIndex(2).build();
            Bitcoin.UnspentTransaction utxo = Bitcoin.UnspentTransaction.newBuilder().setAmount(price).setOutPoint(outbuild)
                    .setScript(ByteString.copyFrom(script)).build();
            Bitcoin.SigningInput.Builder input = Bitcoin.SigningInput.newBuilder().setAmount(price)
                    .setHashType(BitcoinSigHashType.ALL.value())
                    .setToAddress(toAddress)
                    .setChangeAddress(changeAddress)
                    .setByteFee(2)
                    .setCoinType(fromValue.value())
                    .addUtxo(utxo)
                    .addPrivateKey(ByteString.copyFrom(keyForCoin.data()));
            Bitcoin.TransactionPlan plan = AnySigner.plan(input.build(), fromValue, Bitcoin.TransactionPlan.parser());
            input.setPlan(plan);
            input.setAmount(plan.getAmount());
            Bitcoin.SigningOutput sign = AnySigner.sign(input.build(), fromValue, Bitcoin.SigningOutput.parser());
            byte[] bytes = sign.getEncoded().toByteArray();
            return TransactionUtil.INSTANCE.toHexString(bytes, false);
        }

        return null;
    }
    public static String  getFilCode(String privateKey){




        return null;
    }


    public static byte[] hexString2Bytes(String hexString) {
        if (StringUtils.isSpace(hexString)) return new byte[0];
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }
    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
