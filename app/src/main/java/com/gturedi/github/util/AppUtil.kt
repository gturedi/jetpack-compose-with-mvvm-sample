package com.gturedi.github.util

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.android.BuildConfig

//// global methods with 'glo' prefix ////

fun logI(msg: String?) =
    logI(getCallerClassName(), msg.orEmpty())

fun logI(tag: String?, msg: String?) {
    if (BuildConfig.DEBUG) {
        Log.i(tag.orEmpty(), msg.orEmpty())
    }
}

fun logE(e: Throwable, msg: String = "-") =
    logE(e, getCallerClassName(), msg)

fun logE(e: Throwable, tag: String = "-", msg: String = "-") {
    //Firebase.crashlytics.recordException(e)
    if (BuildConfig.DEBUG) {
        Log.e(tag, msg, e)
    }
}

fun gloRunIfDebug(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}

fun gloRunIfDebug(case: Boolean, block: () -> Unit) {
    if (BuildConfig.DEBUG && case) {
        block()
    }
}

fun gloRunSafely(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        logE(e)
    }
}

fun gloRunOnMainThread(block: () -> Unit) {
    Handler(Looper.getMainLooper()).post(block)
}

fun gloRunOnBackgroundThread(block: () -> Unit) {
    AsyncTask.execute(block)
}

fun gloPostDelay(delayInMillis: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(block, delayInMillis)
}

fun gloToJson(obj: Any?) =
    obj?.let { Gson().toJson(it) }

//@Throws(JsonSyntaxException::class)
inline fun <reified T> gloFromJson(json: String?) = try {
    Gson().fromJson<T>(json, object : TypeToken<T>() {}.type)
} catch (e: Throwable) {
    null
}

fun getCallerClassName(): String {
    val sts = Thread.currentThread().stackTrace
    for (item in sts) {
        if (item.isNativeMethod) continue
        if (item.className == Thread::class.java.name) continue
        //if (item.className == AppUtil::class.java.getFullName()) continue
        if (item.className.contains("AppUtil") || item.className.contains("EventWrapper")) continue
        //if (item.className.contains("PopBaseFragment")) continue

        //inner anonymous siniflarda olusan generated sayıyı sil
        val str = item.className.split("\\$".toRegex()).toTypedArray()[0]
        //return str.replace(BuildConfig.APPLICATION_ID+".", "");
        //package fullName ve path'ini sil
        val parts = str.split("\\.".toRegex()).toTypedArray()
        return parts[parts.size - 1]
    }
    return "-"
}