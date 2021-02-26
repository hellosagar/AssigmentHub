package dev.sagar.assigmenthub.utils

import android.content.Context
import android.text.TextUtils
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import dev.hellosagar.assigmenthub.R
import dev.sagar.assigmenthub.utils.Constants.DATE_FORMAT
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random
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

fun View.gone() {
    this.visibility = View.GONE
}

fun Long.getFormattedDate(): String {
    val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return sdf.format(Date(this))
}

fun Context.getRandomColorForPlaceholder(): Int {
    val androidColors = resources.getIntArray(R.array.placeholderColor)
    return androidColors[Random().nextInt(androidColors.size)]
}

fun getRandomColor(): Int {
    val colors = arrayOf(
        R.color.blue,
        R.color.dark_gray,
        R.color.hint_text_color,
        R.color.text_gray,
        R.color.purple,
        R.color.green,
        R.color.red,
        R.color.mustard

    )
    return colors.random()
}

fun String.getShortName(): String {
    val fullNameIndex = this.lastIndexOf(" ")
    if (fullNameIndex == -1) {
        return this.substring(0, 1)
    }

    val firstName = this.substring(0, 1)
    val lastName = this.substring(fullNameIndex + 1, fullNameIndex + 2)
    return "$firstName$lastName".toUpperCase(Locale.getDefault())
}

suspend fun getTeacherInfo(dataStore: DataStore<Preferences>, key: String): String {
    val dataStoreKey = preferencesKey<String>(key)
    val preferences = dataStore.data.first()
    return preferences[dataStoreKey] as String
}
