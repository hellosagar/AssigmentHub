package dev.sagar.assigmenthub.utils

import com.amplifyframework.datastore.generated.model.Assignment

interface OnDataUpdate {
    fun onDataUpdate(data: List<Assignment>)
}
