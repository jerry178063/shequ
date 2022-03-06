package com.unique.blockchain.nft.infrastructure.utils

import android.text.TextUtils
import java.math.BigDecimal
import java.math.RoundingMode

object BigDecimalUtils {
    /**主要是去掉无效的0
     * 1.小数位有就显示，
    2.超过4位，保留4位小数，
    3.无小数，就不需要补0：
     */
    fun formatBigDecimal(marketPrice: String, precision: Int = 4): String {
        try {
            var formatString: String
            if (!TextUtils.isEmpty(marketPrice) && marketPrice != "null") {
                //去掉无效的0
                formatString = BigDecimal(marketPrice).stripTrailingZeros().toPlainString()
                if (formatString.contains(".")) {
                    val split = formatString.split(".")
                    val decimalString = split[1]//小数位
                    if (decimalString.length >= precision) {
                        formatString = BigDecimal(formatString).setScale(precision, RoundingMode.FLOOR).toString()
                    }
                }
                if (formatString.indexOf(".") > 0) {
                    formatString = formatString.replace("0+?$", "");//删掉尾数为0的字符
                    formatString = formatString.replace("[.]$", "");//结尾如果是小数点，则去掉
                }
                if (BigDecimal(formatString).compareTo(BigDecimal.ZERO) == 0) {
                    formatString = "0"
                }
            } else {
                formatString = "0"
            }
            return formatString
        } catch (e: Exception) {
            return "0"
        }
    }
}