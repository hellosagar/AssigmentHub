package dev.sagar.assigmenthub.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context.toast(@StringRes msgRes: Int) = toast(getString(msgRes))

fun Throwable.isInternetError() = this is UnknownHostException || this is SocketTimeoutException
