package com.example.budget.model.utils

interface ILogger {
    fun init()
    fun v(message: String, vararg args: Any)
    fun d(message: String, vararg args: Any)
    fun i(message: String, vararg args: Any)
    fun w(message: String, vararg args: Any)
    fun e(throwable: Throwable, message: String, vararg args: Any)
}