package com.example.budget.model.utils

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy


object AppLogger : ILogger {
    override fun init() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    override fun v(message: String, vararg args: Any) {
        Logger.v(message, args)
    }

    override fun d(message: String, vararg args: Any) {
        Logger.d(message, args)
    }

    override fun i(message: String, vararg args: Any) {
        Logger.i(message, args)
    }

    override fun w(message: String, vararg args: Any) {
        Logger.w(message, args)
    }

    override fun e(throwable: Throwable, message: String, vararg args: Any) {
        Logger.e(throwable, message, args)
    }
}