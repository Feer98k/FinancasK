package com.example.project.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formatarDataBr(): String{
    return SimpleDateFormat("dd/MM/yyyy").format(this.time)
}