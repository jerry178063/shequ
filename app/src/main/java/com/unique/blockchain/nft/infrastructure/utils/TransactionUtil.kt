package com.unique.blockchain.nft.infrastructure.utils

import android.util.Log
import com.google.protobuf.ByteString
import wallet.core.java.AnySigner
import wallet.core.jni.AnyAddress
import wallet.core.jni.CoinType
import wallet.core.jni.PrivateKey
import wallet.core.jni.proto.Cosmos
import java.math.BigInteger
import kotlin.experimental.and

object TransactionUtil {


    private const val TAG = "TransactionUtil"
    public fun ByteArray.toHexString(withPrefix: Boolean = true): String {
        val stringBuilder = StringBuilder()
        if (withPrefix) {
            stringBuilder.append("0x")
        }
        for (element in this) {
            stringBuilder.append(String.format("%02x", element and 0xFF.toByte()))
        }
        return stringBuilder.toString()
    }

    public fun BigInteger.toByteString(): ByteString {
        return ByteString.copyFrom(this.toByteArray())
    }

    public fun String.hexStringToByteArray(): ByteArray {
        val HEX_CHARS = "0123456789ABCDEF"
        val result = ByteArray(length / 2)
        for (i in 0 until length step 2) {
            val firstIndex = HEX_CHARS.indexOf(this[i].toUpperCase());
            val secondIndex = HEX_CHARS.indexOf(this[i + 1].toUpperCase());
            val octet = firstIndex.shl(4).or(secondIndex)
            result.set(i.shr(1), octet.toByte())
        }
        return result
    }


    fun gaussSigningTransaction(
            key: PrivateKey,
            toadress: String,
            price: Long,
            fee1: Long,
            sequence1: Long,
            gasFeeCap: Long,
            accountNumber1: Long

    ): String {
        val publicKey = key.getPublicKeySecp256k1(true)
        val from = AnyAddress(publicKey, CoinType.UNIQUE).description()

        val txAmount = Cosmos.Amount.newBuilder().apply {
            amount = price
            denom = "uunique"
        }.build()

        val sendCoinsMsg = Cosmos.Message.Send.newBuilder().apply {
            fromAddress = from
            toAddress = toadress
            addAllAmounts(listOf(txAmount))
        }.build()


        val message = Cosmos.Message.newBuilder().apply {
            sendCoinsMessage = sendCoinsMsg
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
            accountNumber = accountNumber1
            chainId = "unique"
            memo = ""
            sequence = sequence1
            fee = cosmosFee
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))

        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        return output.json
    }
    fun gaussSigningTransaction2(
        key: PrivateKey,
        toadress: String,
        price: Long,
        fee1: Long,
        sequence1: Long,
        gasFeeCap: Long,
        accountNumber1: Long

    ): String {
        val publicKey = key.getPublicKeySecp256k1(true)
        val from = AnyAddress(publicKey, CoinType.UNIQUE).description()

        val txAmount = Cosmos.Amount.newBuilder().apply {
            amount = price
            denom = "ugpb"
        }.build()

        val sendCoinsMsg = Cosmos.Message.Send.newBuilder().apply {
            fromAddress = from
            toAddress = toadress
            addAllAmounts(listOf(txAmount))
        }.build()


        val message = Cosmos.Message.newBuilder().apply {
            sendCoinsMessage = sendCoinsMsg
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            accountNumber = accountNumber1
            chainId = "gpb"
            memo = ""
            sequence = sequence1
            fee = cosmosFee
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))

        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        return output.json
    }
    fun usdgSigningTransaction(
            key: PrivateKey,
            toadress: String,
            price: Long,
            fee1: Long,
            sequence1: Long,
            gasFeeCap: Long,
            accountNumber1: Long

    ) :String{
        val publicKey = key.getPublicKeySecp256k1(true)
        val from = AnyAddress(publicKey, CoinType.USDG).description()

        val txAmount = Cosmos.Amount.newBuilder().apply {
            amount = price
            denom = "uusdg"
        }.build()

        val sendCoinsMsg = Cosmos.Message.Send.newBuilder().apply {
            fromAddress = from
            toAddress = toadress
            addAllAmounts(listOf(txAmount))
        }.build()


        val message = Cosmos.Message.newBuilder().apply {
            sendCoinsMessage = sendCoinsMsg
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "uusdg"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            accountNumber = accountNumber1
            chainId = "usdg"
            memo = ""
            sequence = sequence1
            fee = cosmosFee
            mode=Cosmos.BroadcastMode.ASYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()

        val output = AnySigner.sign(signingInput, CoinType.USDG, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        Log.d(TAG, "gaussSigningTransaction: "+jsonPayload)
        return  jsonPayload
    }

    /***
     * 委托
     */
    fun gaussWSigningTransaction(
            key: PrivateKey,
            delegator: String,
            validator: String,
            price: Long,
            fee1: Long,
            gasFeeCap: Long

    ): String {
        val adress = Cosmos.Message.Delegate.newBuilder().apply {
            delegatorAddress = delegator
            validatorAddress = validator
            amountBuilder.setAmount(price).denom = "ugpb"

        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            stakeMessage = adress
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()

        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        return output.json
    }

    /***
     * 委托-非验证人
     */
    fun gaussWTSigningTransaction(
            key: PrivateKey,
            fee1: Long,
            gasFeeCap: Long,
            value2:String,
            equence1: Long,
            accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="cosmos-sdk/MsgDelegate"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "gpb"
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
     * 委托-验证人
     */
    fun gaussWTValateSigningTransaction(
            key: PrivateKey,
            fee1: Long,
            gasFeeCap: Long,
            value2:String,
            equence1: Long,
            accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="cosmos-sdk/MsgDelegate"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "gpb"
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
     * 赎回佣金
     */
    fun gaussGetMoneryBackSigningTransaction(
            key: PrivateKey,
            fee1: Long,
            gasFeeCap: Long,
            value2:String,
            equence1: Long,
            accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="cosmos-sdk/MsgUndelegate"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "gpb"
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
     * 提取收益
     */
    fun gaussGetShouyiInSigningTransaction(
            key: PrivateKey,
            fee1: Long,
            gasFeeCap: Long,
            value1:String,
            equence1: Long,
            accountNumber1: Long
    ): String {
        val adress1 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="cosmos-sdk/MsgWithdrawDelegationReward"
            value=value1
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress1
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "gpb"
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
     * 提取佣金
     */
    fun gaussGetYongJInSigningTransaction(
            key: PrivateKey,
            fee1: Long,
            gasFeeCap: Long,
            value2:String,
            equence1: Long,
            accountNumber1: Long
    ): String {

        val messages= mutableListOf<Cosmos.Message>()
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="cosmos-sdk/MsgWithdrawValidatorCommission"
            value=value2
        }.build()
        val message2 = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()
        messages.addAll(listOf(message2))
        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()
        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "gpb"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message2))


        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
    /***
     * 修改验证人佣金比例
     */
    fun gaussChangeRateSigningTransaction(
            key: PrivateKey,
            fee1: Long,
            gasFeeCap: Long,
            value2:String,
            equence1: Long,
            accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="cosmos-sdk/MsgEditValidator"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "gpb"
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
     * 修改验证人自留佣金收益
     */
    fun gaussYzrSelfMonerySigningTransaction(
            key: PrivateKey,
            fee1: Long,
            gasFeeCap: Long,
            value2:String,
            equence1: Long,
            accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="cosmos-sdk/MsgEditValidator"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "gpb"
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
     * 提取收益
     */
    fun gaussBackMonerySigningTransaction(
            key: PrivateKey,
            fee1: Long,
            gasFeeCap: Long,
            value2:String,
            equence1: Long,
            accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="cosmos-sdk/MsgWithdrawDelegationReward"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "gpb"
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
     * 成为验证人
     */
    fun gaussCreateVilesitionSigningTransaction(
            key: PrivateKey,
            fee1: Long,
            gasFeeCap: Long,
            value2:String,
            equence1: Long,
            accountNumber1: Long
    ): String {
        val adress2 = Cosmos.Message.RawJSON.newBuilder().apply {
            type="cosmos-sdk/MsgCreateValidator"
            value=value2
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            rawJsonMessage = adress2
        }.build()
        val feeAmount = Cosmos.Amount.newBuilder().apply {
            amount = fee1
            denom = "ugpb"
        }.build()

        val cosmosFee = Cosmos.Fee.newBuilder().apply {
            gas = gasFeeCap
            addAllAmounts(listOf(feeAmount))
        }.build()

        val signingInput = Cosmos.SigningInput.newBuilder().apply {
            memo = ""
            fee = cosmosFee
            accountNumber = accountNumber1
            chainId = "gpb"
            sequence = equence1
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()
        val output = AnySigner.sign(signingInput, CoinType.UNIQUE, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
/**
 * USDT---兑换---UNIUQE
 * */
    fun usdtShortcutSigningTransaction(
        key: PrivateKey,
        toadress: String,
        price: Long,
        fee1: Long,
        sequence1: Long,
        gasFeeCap: Long,
        accountNumber1: Long,
        memos:String,
        mDenom:String,
        coinType:CoinType
    ): String {

        val publicKey = key.getPublicKeySecp256k1(true)
        val from = AnyAddress(publicKey, coinType).description()

        val txAmount = Cosmos.Amount.newBuilder().apply {
            amount = price
            denom = mDenom

        }.build()

        val sendCoinsMsg = Cosmos.Message.Send.newBuilder().apply {
            fromAddress = from
            toAddress = toadress

            addAllAmounts(listOf(txAmount))
        }.build()
        val message = Cosmos.Message.newBuilder().apply {
            sendCoinsMessage = sendCoinsMsg
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
            accountNumber = accountNumber1
            chainId = "unique"
            memo = memos
            sequence = sequence1
            fee = cosmosFee
            mode = Cosmos.BroadcastMode.SYNC
            privateKey = ByteString.copyFrom(key.data())
            addAllMessages(listOf(message))
        }.build()

        val output = AnySigner.sign(signingInput, coinType, Cosmos.SigningOutput.parser())
        val jsonPayload = output.json
        return jsonPayload
    }
}