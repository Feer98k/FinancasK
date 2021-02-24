package com.example.project.model

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.financask.R
import com.example.project.extensions.formataMoedaBrasil
import org.w3c.dom.Text
import java.math.BigDecimal

class ResumoView(
    private val view: View,
    private val transacoes: List<Transacoes>,
    private val contexto: Context
) {
    var totalReceita = BigDecimal.ZERO;
    var totalDespesa = BigDecimal.ZERO
    var totalLiquido = BigDecimal.ZERO

    fun configuraViewResumo() {
        definaValoresParaResumo()
        configuraViews()
    }

    private fun definaValoresParaResumo() {
        for (Transacoes in transacoes) {
            if (Transacoes.tipo == TipoEnum.RECEITA) totalReceita =
                totalReceita.plus(Transacoes.valor)
            else totalDespesa = totalDespesa.plus(Transacoes.valor)
        }
        totalLiquido = totalReceita.subtract(totalDespesa)
    }

    private fun configuraViews() {
        with(view.findViewById<TextView>(R.id.resumo_card_despesa)) {
            text = totalDespesa.formataMoedaBrasil()
            setTextColor(ContextCompat.getColor(contexto, configuraCorResumo(totalDespesa)))
        }
        with(view.findViewById<TextView>(R.id.resumo_card_receita)) {
            text = totalReceita.formataMoedaBrasil()
            setTextColor(ContextCompat.getColor(contexto, configuraCorResumo(totalReceita)))
        }
        with(view.findViewById<TextView>(R.id.resumo_card_receita)) {
            text = totalLiquido.formataMoedaBrasil()
            setTextColor(ContextCompat.getColor(contexto, configuraCorResumo(totalLiquido)))
        }
    }

    private fun configuraCorResumo(total: BigDecimal): Int {
        if (total >= BigDecimal.ZERO)
            return R.color.blue
        return R.color.red
    }
}