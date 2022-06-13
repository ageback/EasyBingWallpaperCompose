package com.bigflowertiger.compopsebingwallpaper.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigflowertiger.compopsebingwallpaper.data.model.BingItem
import com.bigflowertiger.compopsebingwallpaper.domain.BingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BingViewModel @Inject constructor(
    private val bingRepository: BingRepository
) : ViewModel() {
    private val _bingList: MutableState<List<BingItem>> = mutableStateOf(emptyList())
    val bingList: State<List<BingItem>> = _bingList

    init {
        getBingWallpaperList3("0")
    }

    private fun getBingWallpaperList3(ensearch: String) {
        viewModelScope.launch {
            try {
                // coroutineScope is needed, else in case of any network error, it will crash
                coroutineScope {
                    val deferred1 = async { bingRepository.getBingWallpaperList3(0, ensearch) }
                    val deferred2 = async { bingRepository.getBingWallpaperList3(8, ensearch) }
                    val result = awaitAll(deferred1, deferred2)
                    val list1 = result.flatMap {
                        it.images
                    }
                    consume(list1)
                }
            } catch (e: Exception) {
            }
        }

    }

    private fun getBingWallpaperList2(ensearch: String) {

        val flow1 = bingRepository.getBingWallpaperList2(0, ensearch)
        val flow2 = bingRepository.getBingWallpaperList2(8, ensearch)

        viewModelScope.launch {
            flow1.zip(flow2) { f1, f2 ->
                f1.images + f2.images
            }.collect { bingList ->
                consume(bingList)
            }
        }
    }

    private fun getBingWallpaperList(ensearch: String) {
        val flow1 = bingRepository.getBingWallpaperList(0, ensearch)
        val flow2 = bingRepository.getBingWallpaperList(8, ensearch)
        viewModelScope.launch {
            flow1.combineTransform(flow2) { f1, f2 ->
                if (f1.data != null && f2.data != null)
                    emit(f1.data!!.images + f2.data!!.images)
            }.collect { bingList ->
                consume(bingList)
            }
        }
    }

    private fun consume(bingList: List<BingItem>) {
        _bingList.value = bingList
            .sortedByDescending { it.startdate }
            .distinctBy { it.hsh }
    }
}