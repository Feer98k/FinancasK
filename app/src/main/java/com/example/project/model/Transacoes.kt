package com.example.project.model

import java.math.BigDecimal
import java.util.*

class Transacoes(val valor: BigDecimal,
                 val tipo : TipoEnum,
                 val categoria: String = "",
                 val  data: Calendar = Calendar.getInstance())
