package com.unique.blockchain.nft.infrastructure.other

import com.google.protobuf.ByteString

fun ByteArray.toHex(): String {
    return Numeric.toHexString(this)
}

fun ByteArray.toDelHex(): String {
    return Numeric.toHexdelString(this)
}

fun String.toHexBytes(): ByteArray {
    return Numeric.hexStringToByteArray(this)
}

fun String.toHexByteArray(): ByteArray {
    return Numeric.hexStringToByteArray(this)
}

fun String.toByteString(): ByteString {
    return ByteString.copyFrom(this, Charsets.UTF_8)
}

fun String.toHexBytesInByteString(): ByteString {
    return ByteString.copyFrom(this.toHexBytes())
}