package com.unique.blockchain.nft.infrastructure.utils

import com.google.protobuf.ByteString
import wallet.core.java.AnySigner
import wallet.core.jni.CoinType
import wallet.core.jni.PrivateKey
import wallet.core.jni.proto.Cosmos

object TransactionUtilNew {


    private const val TAG = "TransactionUtil"

    /***
     * 固定价格
     */
    fun uniqueGudingPriceSigningTransaction(
            key: PrivateKey,
            fee1: Long,
            gasFeeCap: Long,
            value2:String,
            equence1: Long,
            accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="fixedprice/CreateOrder"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uunique"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "unique"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
    /***
     * 拍卖固定价格
     */
    fun uniquePaiPriceSigningTransaction(
        key: PrivateKey,
        fee1: Long,
        gasFeeCap: Long,
        value2:String,
        equence1: Long,
        accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="auction/CreateOrder"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uunique"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "unique"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
    /***
     * 竞价
     */
    fun uniqueBidPriceSigningTransaction(
        key: PrivateKey,
        fee1: Long,
        gasFeeCap: Long,
        value2:String,
        equence1: Long,
        accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="nft/CreateOrder"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uunique"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "unique"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
    /***
     * 提货-冻结
     */
    fun uniqueTiHuoSigningTransaction(
        key: PrivateKey,
        fee1: Long,
        gasFeeCap: Long,
        value2:String,
        equence1: Long,
        accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="nft/FrozenNft"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uunique"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "unique"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
    /***
     * 转移nft
     */
    fun uniqueTrasferNftSigningTransaction(
        key: PrivateKey,
        fee1: Long,
        gasFeeCap: Long,
        value2:String,
        equence1: Long,
        accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="nft/TransferNft"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uunique"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "unique"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
    /***
     * 撤销固定价格
     */
    fun uniqueDeleteNftGudingOrderSigningTransaction(
        key: PrivateKey,
        fee1: Long,
        gasFeeCap: Long,
        value2:String,
        equence1: Long,
        accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="fixedprice/DeleteOrder"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uunique"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "unique"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
    /***
     * 撤销nft订单-拍卖
     */
    fun uniqueDeleteNdtOrderSigningTransaction(
        key: PrivateKey,
        fee1: Long,
        gasFeeCap: Long,
        value2:String,
        equence1: Long,
        accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="auction/DeleteOrder"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uunique"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "unique"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
    /***
     * 购买NFT
     */
    fun uniqueBuyNftSigningTransaction(
        key: PrivateKey,
        fee1: Long,
        gasFeeCap: Long,
        value2:String,
        equence1: Long,
        accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="fixedprice/BidOrder"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uunique"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "unique"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
    /***
     * 拍卖NFT
     */
    fun uniqueOutNftSigningTransaction(
        key: PrivateKey,
        fee1: Long,
        gasFeeCap: Long,
        value2:String,
        equence1: Long,
        accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="auction/BidOrder"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uunique"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "unique"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
    /***
     * 质押保证金
     */
    fun uniqueZhiyaSigningTransaction(
        key: PrivateKey,
        fee1: Long,
        gasFeeCap: Long,
        value2:String,
        equence1: Long,
        accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="pool/Delegate"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uunique"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "unique"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
}