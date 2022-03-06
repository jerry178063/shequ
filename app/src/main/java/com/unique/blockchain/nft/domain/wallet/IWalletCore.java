package com.unique.blockchain.nft.domain.wallet;

import wallet.core.jni.CoinType;

public interface IWalletCore {
    public String mnemonic();
    public String getAddress(CoinType coinType, String path);
    public String getDenom();
}
