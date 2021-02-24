package com.example.project.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*


fun BigDecimal.formataMoedaBrasil(): String {
    val format = DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))
    return format
        .format(this)
        .replace("R$", "R$ ")
        .replace("-R$", "R$ -")

}
