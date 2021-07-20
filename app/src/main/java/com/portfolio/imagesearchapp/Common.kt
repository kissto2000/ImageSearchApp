package com.portfolio.imagesearchapp

import java.text.SimpleDateFormat
import java.util.*

class Common {
    companion object {
        @JvmStatic
        fun dateString(time: String?): String {
            if (time.isNullOrEmpty()) return ""
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).parse(time)!!
            return SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초", Locale.getDefault()).format(date)
        }
    }
}