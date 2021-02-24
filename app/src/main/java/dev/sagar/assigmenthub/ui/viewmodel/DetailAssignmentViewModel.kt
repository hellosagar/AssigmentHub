package dev.sagar.assigmenthub.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dev.sagar.assigmenthub.data.repositories.DatabaseRepo

class DetailAssignmentViewModel @ViewModelInject constructor(
    private val databaseRepo: DatabaseRepo
) : ViewModel()
