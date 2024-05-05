package org.d3if3063.miniproject02.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3063.miniproject02.database.PasienDao
import org.d3if3063.miniproject02.ui.screen.DetailViewModel
import org.d3if3063.miniproject02.ui.screen.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val dao: PasienDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}