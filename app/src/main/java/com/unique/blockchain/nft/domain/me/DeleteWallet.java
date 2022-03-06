package com.unique.blockchain.nft.domain.me;

public class DeleteWallet {
    private String name;
    public DeleteWallet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
