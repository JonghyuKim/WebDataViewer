package com.hyu.webdataviewer.util.log

import android.util.Log

/**
 * Android Log Rapper
 * print Class & Line
 */
class HLog private constructor() {
    companion object {
        const val LEVEL_VERBOSE = Log.VERBOSE
        const val LEVEL_DEBUG = Log.DEBUG
        const val LEVEL_INFO = Log.INFO
        const val LEVEL_WARN = Log.WARN
        const val LEVEL_ERROR = Log.ERROR

        val level = LEVEL_VERBOSE
        val tag = "HLog"
        private val logMakeBuilder = StringBuilder()
        private var traceElements: Array<StackTraceElement>? = null

        fun v(message: String) {
            print(LEVEL_VERBOSE, message)
        }

        fun d(message: String) {
            print(LEVEL_DEBUG, message)
        }

        fun i(message: String) {
            print(LEVEL_INFO, message)
        }

        fun w(message: String) {
            print(LEVEL_WARN, message)
        }

        fun e(message: String) {
            print(LEVEL_ERROR, message)
        }

        fun v(exception: Exception) {
            print(
                LEVEL_VERBOSE,
                getExceptionMessage(exception)
            )
        }

        fun d(exception: Exception) {
            print(
                LEVEL_DEBUG,
                getExceptionMessage(exception)
            )
        }

        fun i(exception: Exception) {
            print(
                LEVEL_INFO,
                getExceptionMessage(exception)
            )
        }

        fun w(exception: Exception) {
            print(
                LEVEL_WARN,
                getExceptionMessage(exception)
            )
        }

        fun e(exception: Exception) {
            print(
                LEVEL_ERROR,
                getExceptionMessage(exception)
            )
        }

        fun v(format: String, vararg args: Any) {
            print(LEVEL_VERBOSE, String.format(format, *args))
        }

        fun d(format: String, vararg args: Any) {
            print(LEVEL_DEBUG, String.format(format, *args))
        }

        fun i(format: String, vararg args: Any) {
            print(LEVEL_INFO, String.format(format, *args))
        }

        fun w(format: String, vararg args: Any) {
            print(LEVEL_WARN, String.format(format, *args))
        }

        fun e(format: String, vararg args: Any) {
            print(LEVEL_ERROR, String.format(format, *args))
        }

        @Synchronized
        private fun print(level: Int, message: String) {
            if (level >= level) {
                traceElements = Throwable().stackTrace
                if (traceElements != null && traceElements!!.size > 2) {
                    logMakeBuilder.append(message).append(" (").append(
                        traceElements!![2].fileName).append(", ")
                        .append(traceElements!![2].lineNumber).append(")\n")
                } else {
                    logMakeBuilder.append(message).append(" (").append("unknown").append(", ").append("-1").append(")")
                }

                traceElements = null

                when (Companion.level) {
                    LEVEL_VERBOSE -> Log.v(tag, message)
                    LEVEL_DEBUG -> Log.d(tag, message)
                    LEVEL_INFO -> Log.i(tag, message)
                    LEVEL_WARN -> Log.w(tag, message)
                    LEVEL_ERROR -> Log.e(tag, message)
                }

                logMakeBuilder.setLength(0)
            }
        }

        private fun getExceptionMessage(e: Exception): String {
            e.printStackTrace()
            return Log.getStackTraceString(e)
        }

    }
}
