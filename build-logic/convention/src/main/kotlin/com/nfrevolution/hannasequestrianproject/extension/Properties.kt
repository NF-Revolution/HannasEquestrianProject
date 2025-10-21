package com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.extension

import java.io.FileInputStream
import java.util.Properties

fun loadProperties(path: String): Properties = FileInputStream(path).use { inputStream ->
    Properties().apply {
        load(inputStream)
    }
}

fun Properties.propertyInt(key: String): Int {
    val stringValue = propertyString(key)
    return try {
        stringValue.toInt()
    } catch (_: NumberFormatException) {
        error("Cast exception for $key")
    }
}

fun Properties.propertyString(key: String): String {
    val property = getProperty(key)

    return property ?: ""
}