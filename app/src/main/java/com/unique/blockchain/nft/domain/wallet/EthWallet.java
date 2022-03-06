package com.unique.blockchain.nft.domain.wallet;

import android.util.Log;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.unique.blockchain.nft.infrastructure.other.Bech32;
import com.unique.blockchain.nft.infrastructure.other.ExtensionsKt;
import com.unique.blockchain.nft.infrastructure.utils.ConvertBits;
import com.unique.blockchain.nft.infrastructure.utils.Numeric;

import wallet.core.jni.CoinType;
import wallet.core.jni.PrivateKey;
import wallet.core.jni.proto.Ethereum;

public class EthWallet extends WalletCore {
    public static final String denom="ueth";
    private PrivateKey privateKey;
    public EthWallet(PrivateKey privateKey){
        this.privateKey=privateKey;
    }



    public static class ETHTxCommonParams {
        long AccountNumber;
        long AccountSequence;

        @Override
        public String toString() {
            return "ETHTxCommonParams{" +
                    "AccountNumber=" + AccountNumber +
                    ", AccountSequence=" + AccountSequence +
                    ", AccountAddress='" + AccountAddress + '\'' +
                    ", AccountPrivateKey='" + AccountPrivateKey + '\'' +
                    ", ChainId='" + ChainId + '\'' +
                    ", toadress='" + toadress + '\'' +
                    ", Memo='" + Memo + '\'' +
                    ", FeeNumber='" + FeeNumber + '\'' +
                    ", GasLimit='" + GasLimit + '\'' +
                    '}';
        }

        String AccountAddress;
        String AccountPrivateKey;
        String ChainId;

        public String getToadress() {
            return toadress;
        }

        public void setToadress(String toadress) {
            this.toadress = toadress;
        }

        String toadress;
        public ETHTxCommonParams( String feeNumber, String gasLimit,String chainId,String toadress) {
            FeeNumber = feeNumber;
            GasLimit = gasLimit;
            ChainId = chainId;
            this.toadress=toadress;
        }

        String Memo;
        String FeeNumber;
        String GasLimit;
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

        return denom;
    }


    /****
     *
     *Eth交易
     *
     *
     */

    public String EthereumTransactionSigning(ETHTxCommonParams txCommonParams,String amount,String nonce){
        Log.d("Ethereum", "EthereumTransactionSigning: "+ Numeric.INSTANCE.intToHex(amount));
        Log.e("FFDDD3","txCommonParams:" + new Gson().toJson(txCommonParams));
        Log.e("FFDDD3","amount:" + amount);
        Log.e("FFDDD3","nonce:" + nonce);

        Ethereum.Transaction.Transfer transferbuild = Ethereum.Transaction.Transfer.newBuilder()
                .setAmount(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(amount))).build();
        Ethereum.Transaction Ethbuild = Ethereum.Transaction.newBuilder().setTransfer(transferbuild).build();
        Ethereum.SigningInput input = Ethereum.SigningInput.newBuilder()
                .setChainId(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.ChainId)))
                .setGasLimit(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.GasLimit)))
                .setPrivateKey(ByteString.copyFrom(privateKey.data()))
                .setNonce(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(nonce)))
                .setToAddress(txCommonParams.toadress)

                .setTransaction(Ethbuild)
                .setGasPrice(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.FeeNumber))).build();
        return Ethsign(input,CoinType.ETHEREUM);
    }


    //授权
    public String EthereumERC20AppSigning(ETHTxCommonParams txCommonParams,String amount,String nonce,String spender){
        Log.d("Ethereum", "EthereumERC20Signing: "+txCommonParams);
        Log.d("Ethereum", "EthereumERC20Signing: "+amount);
        Log.d("Ethereum", "EthereumERC20Signing: "+nonce);
        Log.d("Ethereum", "EthereumERC20Signing: "+spender);
        Ethereum.Transaction.ERC20Approve build = Ethereum.Transaction.ERC20Approve.newBuilder()
                .setSpender(spender)
                .setAmount(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(amount))).build();
        Ethereum.Transaction Ethbuild = Ethereum.Transaction.newBuilder().setErc20Approve(build).build();
        Ethereum.SigningInput input = Ethereum.SigningInput.newBuilder()
                .setChainId(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.ChainId)))
                .setGasLimit(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.GasLimit)))
                .setPrivateKey(ByteString.copyFrom(privateKey.data()))
                .setToAddress(txCommonParams.toadress)
                .setNonce(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(nonce)))
                .setTransaction(Ethbuild)
                .setGasPrice(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.FeeNumber))).build();
        return Ethsign(input,CoinType.ETHEREUM);
    }



    /****
     *
     *
     *Eth ERC20 交易
     *
     *
     *
     */


    public String EthereumERC20Signing(ETHTxCommonParams txCommonParams,String amount,String nonce,String to){
        Log.d("Ethereum", "EthereumERC20Signing: "+txCommonParams);
        Log.d("Ethereum", "EthereumERC20Signing: "+amount);
        Log.d("Ethereum", "EthereumERC20Signing: "+nonce);
        Log.d("Ethereum", "EthereumERC20Signing: "+to);
        Ethereum.Transaction.ERC20Transfer build = Ethereum.Transaction.ERC20Transfer.newBuilder()
                .setTo(to).setAmount(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(amount))).build();
        Ethereum.Transaction Ethbuild = Ethereum.Transaction.newBuilder().setErc20Transfer(build).build();
        Ethereum.SigningInput input = Ethereum.SigningInput.newBuilder()

                .setChainId(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.ChainId)))
                .setGasLimit(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.GasLimit)))
                .setPrivateKey(ByteString.copyFrom(privateKey.data()))
                .setToAddress(txCommonParams.toadress)
                .setNonce(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(nonce)))
                .setTransaction(Ethbuild)
                .setGasPrice(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.FeeNumber))).build();
        return Ethsign(input,CoinType.ETHEREUM);
    }
//    public String EthereumERC20KuaSigning(ETHTxCommonParams txCommonParams, BigInteger amount, String nonce, String to, String cons_address){
//        Log.d("Ethereum", "EthereumERC20Signing: "+txCommonParams);
//        Log.d("Ethereum", "EthereumERC20Signing: "+amount);
//        Log.d("Ethereum", "EthereumERC20Signing: "+nonce);
//        Log.d("Ethereum", "EthereumERC20Signing: "+to);
//        Log.d("Ethereum", "EthereumERC20Signing: "+cons_address);
//
//        Function function = sendToCosmos(cons_address, to, amount);
//        String encodedFunction = FunctionEncoder.encode(function);
//        Log.d("Ethereum", "EthereumERC20KuaSigning: "+encodedFunction);
//        Ethereum.Transaction.ContractGeneric build = Ethereum.Transaction.ContractGeneric.newBuilder()
//                .setData(ExtensionsKt.toHexBytesInByteString(encodedFunction))
//                .build();
//        Ethereum.Transaction Ethbuild = Ethereum.Transaction.newBuilder().setContractGeneric(build).build();
//        Ethereum.SigningInput input = Ethereum.SigningInput.newBuilder()
//                .setChainId(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.ChainId)))
//                .setGasLimit(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.GasLimit)))
//                .setPrivateKey(ByteString.copyFrom(privateKey.data()))
//                .setToAddress(txCommonParams.toadress)
//                .setNonce(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(nonce)))
//                .setTransaction(Ethbuild)
//                .setGasPrice(ExtensionsKt.toHexBytesInByteString(Numeric.INSTANCE.intToHex(txCommonParams.FeeNumber))).build();
//        return Ethsign(input,CoinType.ETHEREUM);
//    }
    /**
     *
     *Eth ERC721 交易
     *
     *
     *
     *
     */


    public String EthereumERC721Signing(ETHTxCommonParams txCommonParams,String from_adress,String to_adress,String tokenid){

        Ethereum.Transaction.ERC721Transfer build = Ethereum.Transaction.ERC721Transfer.newBuilder().setFrom(from_adress)
                .setTo(to_adress)
                .setTokenId(ByteString.copyFrom(ExtensionsKt.toHexByteArray(tokenid)))
                .build();
        Ethereum.Transaction Ethbuild = Ethereum.Transaction.newBuilder().setErc721Transfer(build).build();
        Ethereum.SigningInput input = Ethereum.SigningInput.newBuilder().setChainId(ByteString.copyFrom(ExtensionsKt.toHexByteArray(txCommonParams.ChainId)))
                .setGasLimit(ByteString.copyFrom(ExtensionsKt.toHexByteArray(txCommonParams.GasLimit)))
                .setPrivateKey(ByteString.copyFrom(privateKey.data()))
                .setToAddress(txCommonParams.toadress)
                .setTransaction(Ethbuild)
                .setGasPrice(ByteString.copyFrom(ExtensionsKt.toHexByteArray(txCommonParams.FeeNumber))).build();
        return Ethsign(input,CoinType.ETHEREUM);
    }

    /*****
     * ERC1155 交易
     * @param txCommonParams
     * @param from_adress
     * @param to_adress
     * @param tokenid
     * @return
     */
    public String EthereumERC1155Signing(ETHTxCommonParams txCommonParams,String from_adress,String to_adress,String tokenid){

        Ethereum.Transaction.ERC1155Transfer build = Ethereum.Transaction.ERC1155Transfer.newBuilder().setFrom(from_adress)
                .setTo(to_adress)
                .setTokenId(ByteString.copyFrom(ExtensionsKt.toHexByteArray(tokenid)))
                .build();
        Ethereum.Transaction Ethbuild = Ethereum.Transaction.newBuilder().setErc1155Transfer(build).build();
        Ethereum.SigningInput input = Ethereum.SigningInput.newBuilder().setChainId(ByteString.copyFrom(ExtensionsKt.toHexByteArray(txCommonParams.ChainId)))
                .setGasLimit(ByteString.copyFrom(ExtensionsKt.toHexByteArray(txCommonParams.GasLimit)))
                .setPrivateKey(ByteString.copyFrom(privateKey.data()))
                .setToAddress(txCommonParams.toadress)
                .setTransaction(Ethbuild)
                .setGasPrice(ByteString.copyFrom(ExtensionsKt.toHexByteArray(txCommonParams.FeeNumber))).build();
        return Ethsign(input,CoinType.ETHEREUM);
    }



//    public Function sendToCosmos(String tokenContract, String cosmosAddress, BigInteger amountBig) {
//        byte[] bytes = decodeAddress(cosmosAddress);
//        byte[] byte32 = new byte[32];
//        for (int i = 0; i < byte32.length; i++) {//补齐32位
//            if (i < 12) {
//                byte32[i] = 0;
//            } else {
//                byte32[i] = bytes[i - 12];
//            }
//        }
//
//        org.web3j.abi.datatypes.Function function = new Function("sendToCosmos", Arrays.asList(new Address(tokenContract), new Bytes32(byte32), new Uint256(amountBig)), Collections.emptyList());
//        return function;
//    }

    public static byte[] decodeAddress(String address){
        byte[] dec = Bech32.decode(address).getData();
        return ConvertBits.convertBits(dec, 0, dec.length, 5, 8, false);
    }
}

