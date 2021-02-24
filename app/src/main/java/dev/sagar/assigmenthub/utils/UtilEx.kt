package dev.sagar.assigmenthub.utils

import android.text.TextUtils
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import dev.sagar.assigmenthub.utils.Constants.DATE_FORMAT
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isNumeric(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.getUUIDString(): String {
    val uuid = UUID.nameUUIDFromBytes(this.toByteArray())
    return uuid.toString()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Long.getFormattedDate(): String {
    val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return sdf.format(Date(this))
}

suspend fun getTeacherInfo(dataStore: DataStore<Preferences>, key: String): String {
    val dataStoreKey = preferencesKey<String>(key)
    val preferences = dataStore.data.first()
    return preferences[dataStoreKey] as String
}
