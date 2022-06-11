package com.bigflowertiger.compopsebingwallpaper.data

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigflowertiger.compopsebingwallpaper.data.model.BingItem
import com.bigflowertiger.compopsebingwallpaper.data.model.BingResponse
import com.bigflowertiger.compopsebingwallpaper.domain.BingRepository
import com.hadiyarajesh.flower.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BingViewModel @Inject constructor(
    private val bingRepository: BingRepository
) : ViewModel() {
    private val _bingList: MutableState<List<BingItem>> = mutableStateOf(emptyList())
    val bingList: State<List<BingItem>> = _bingList

    init {
        getBingWallpaperList("0")
    }

    private fun getBingWallpaperList(ensearch: String) {
        val flow1 = bingRepository.getBingWallpaperList(0, ensearch)
        val flow2 = bingRepository.getBingWallpaperList(8, ensearch)
        viewModelScope.launch {
            flow1.combineTransform(flow2) { f1, f2 ->
                if (f1.data != null && f2.data != null)
                    emit(f1.data!!.images + f2.data!!.images)
            }.collect { bingList ->
                _bingList.value = bingList
                    .sortedByDescending { it.startdate }
                    .distinctBy { it.hsh }
            }
        }
    }
}