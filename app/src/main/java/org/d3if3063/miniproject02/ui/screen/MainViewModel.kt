package org.d3if3063.miniproject02.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3063.miniproject02.database.PasienDao
import org.d3if3063.miniproject02.model.Pasien


class MainViewModel(dao: PasienDao) : ViewModel(){
    val data: StateFlow<List<Pasien>> = dao.getPasien().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}