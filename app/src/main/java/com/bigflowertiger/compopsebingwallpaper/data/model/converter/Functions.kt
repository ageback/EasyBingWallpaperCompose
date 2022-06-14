package com.bigflowertiger.compopsebingwallpaper.data.model.converter

object Functions {
    fun <R, T> getElementList(sourceList: List<R>, block: (sourceElem: R) -> T): List<T> {
        val resultList = mutableListOf<T>()
        for (source in sourceList) {
            resultList.add(block(source))
        }
        return resultList
    }
}