package dev.sagar.assigmenthub.utils

import android.text.TextUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun Date.getStandardFormat(): String {
    val targetFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    return targetFormat.format(this) // 20120821
}
