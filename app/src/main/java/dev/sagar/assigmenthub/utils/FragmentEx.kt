package dev.sagar.assigmenthub.utils

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String) = requireActivity().toast(msg)
fun Fragment.toast(@StringRes msgRes: Int) = requireActivity().toast(msgRes)
