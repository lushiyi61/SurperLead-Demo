package com.lushiyi.super_lead_demo.utils

class ULogTag {
    companion object {
        var Tag = "iwof920125@hotmail.com<<=>>"

        fun getTag(text:String):String {
            return Tag + text
        }
    }
}