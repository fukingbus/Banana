package com.bus.banana

class UT {
    companion object {
        fun Log(msg: String) {
            val stackTrace = Thread.currentThread().stackTrace[3]
            val methodName = stackTrace.methodName
            val fileName = stackTrace.fileName
            val lineNumber = stackTrace.lineNumber
            android.util.Log.d("DEBUG","($fileName:$lineNumber) -> $methodName() ->| $msg")
        }
    }
}