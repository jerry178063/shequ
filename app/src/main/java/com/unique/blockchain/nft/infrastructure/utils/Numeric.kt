package com.unique.blockchain.nft.infrastructure.utils

import android.util.Log
import com.blankj.utilcode.util.StringUtils
import wallet.core.jni.Hash
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.experimental.and
import kotlin.math.log

// TODO: Move to native
object Numeric {

    fun containsHexPrefix(input: String): Boolean {
        return input.length > 1 && input[0] == '0' && input[1] == 'x'
    }

    fun cleanHexPrefix(input: String): String {
        return if (containsHexPrefix(input)) {
            input.substring(2)
        } else {
            input
        }
    }

    fun hexStringToByteArray(input: String): ByteArray {
        val cleanInput = cleanHexPrefix(input)

        val len = cleanInput.length

        if (len == 0) {
            return byteArrayOf()
        }

        val data: ByteArray
        val startIdx: Int
        if (len % 2 != 0) {
            data = ByteArray(len / 2 + 1)
            data[0] = Character.digit(cleanInput.get(0), 16).toByte()
            startIdx = 1
        } else {
            data = ByteArray(len / 2)
            startIdx = 0
        }

        var i = startIdx
        while (i < len) {
            data[(i + 1) / 2] =
                ((Character.digit(cleanInput.get(i), 16) shl 4) + Character.digit(cleanInput.get(i + 1), 16)).toByte()
            i += 2
        }
        return data
    }

    fun toHexString(input: ByteArray?, offset: Int, length: Int, withPrefix: Boolean): String {
        val stringBuilder = StringBuilder()
        if (withPrefix) {
            stringBuilder.append("0x")
        }
        for (i in offset until offset + length) {
            stringBuilder.append(String.format("%02x", input!![i] and 0xFF.toByte()))
        }

        return stringBuilder.toString()
    }


    fun toHexdelString(input: ByteArray?, offset: Int, length: Int, withPrefix: Boolean): String {
        val stringBuilder = StringBuilder()

        for (i in offset until offset + length) {
            stringBuilder.append(String.format("%02x", input!![i] and 0xFF.toByte()))
        }

        return stringBuilder.toString()
    }

    fun toHexString(input: ByteArray?): String {
        return toHexString(input, 0, input!!.size, true)
    }

    fun toHexdelString(input: ByteArray?): String {
        return toHexdelString(input, 0, input!!.size, true)
    }

    fun  Sha256(content:String):String {
        val bytes = Hash.sha256(content.toByteArray())
        val hex = Numeric.toHexString(bytes)
        return hex
    }

    fun  Sha25612(content:String):ByteArray {
        val bytes = Hash.sha256(content.toByteArray())
        return bytes
    }

     fun intToHex(n: String): String? {
        //StringBuffer s = new StringBuffer();

         val b = BigInteger( BigDecimal(n).setScale(0,BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString(), 10)
         val r: String = b.toString(16)

        return r;
    }
    fun inttwoToHex(n: String): String? {
        //StringBuffer s = new StringBuffer();

        val b = BigInteger( BigDecimal(n).setScale(0,BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString(), 10)
        val r: String = b.toString(16)
        val sb = StringBuilder(r)
        for (index in 1..100){
            if(sb.length!=64){
                sb.insert(0,"0")
            }
        }
        Log.d("inttwoToHex", "inttwoToHex: "+sb.length)
        Log.d("inttwoToHex", "inttwoToHex: "+sb)
        return "0x"+sb.toString();
    }
}