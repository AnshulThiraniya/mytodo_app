package com.example.mytodoapp

class Event <out T>(private val statusMessage : T){
    var isHandled = false
        private set
    fun getContentIfNotHandled():T?{
        return if (isHandled){
            null
        }
        else{
            isHandled = true
            statusMessage
        }
    }
}