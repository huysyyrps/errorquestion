package com.example.errorquestion.data

import com.example.errorquestion.R


object BannerData {
    fun setBannerData(): ArrayList<Int> {
        val bannerList = ArrayList<Int>()
        bannerList.apply {
            add(R.drawable.banner1)
            add(R.drawable.banner2)
            add(R.drawable.banner3)
            add(R.drawable.banner4)
            add(R.drawable.banner5)
        }
        return bannerList
    }
}
