package com.example.project.model

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.financask.R
import com.example.project.extensions.converteParaCalendar
import com.example.project.extensions.formatarDataBr
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(
    private val context: Context,
    private val viewGroup: View,
    private val tipoTransacao: TipoEnum
) {
    private val viewCriada = criarLayout()
    protected val recursoTransacaoValor = viewCriada.form_transacao_valor
    protected val recursoTransacaoCategoria = viewCriada.form_transacao_categoria
    protected val recursoTransacaoData = viewCriada.form_transacao_data
    abstract val tituloBotaoPositivo: String

    fun configuraDialog(delegate: (transacao: Transacoes) -> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipoTransacao)
        criaDialog(delegate)
    }

    private fun criaDialog(delegate:(transacao: Transacoes)-> Unit) {
        val titulo = devolveTipoTransacao()

        AlertDialog
            .Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(tituloBotaoPositivo) { _, _ ->
                val campoValorTransacao = recursoTransacaoValor.text.toString()
                val campoTransacaoCategoria = recursoTransacaoCategoria.selectedItem.toString()
                val campoTransacaoData = recursoTransacaoData.text.toString()

                val valorTransacaoFormatado = converteValorParaBigDecimal(campoValorTransacao)

                val data = campoTransacaoData.converteParaCalendar()
                val tipoEnumCriado = tipoTransacao
                val transacoes1 =
                    Transacoes(
                        valor = valorTransacaoFormatado,
                        tipo = tipoEnumCriado,
                        categoria = campoTransacaoCategoria,
                        data = data
                    )
                delegate(transacoes1)

            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun converteValorParaBigDecimal(campoValorTransacao: String) = try {
        BigDecimal(campoValorTransacao)
    } catch (exception: NumberFormatException) {
        BigDecimal.ZERO
    }

    private fun configuraCampoCategoria(tipo: TipoEnum) {
        val categoria = devolveCategoria(tipo)
        val adapterSpinnerDepesa = ArrayAdapter.createFromResource(
            context, categoria,
            android.R.layout.simple_spinner_dropdown_item
        )
        recursoTransacaoCategoria.adapter = adapterSpinnerDepesa

    }

    protected fun devolveCategoria(tipo: TipoEnum): Int {
        if (tipo == TipoEnum.DESPESA) {
            return R.array.categorias_de_despesa
        }
        return R.array.categorias_de_receita

    }

    private fun configuraCampoData() {
        var dataAtual = Calendar.getInstance()
        recursoTransacaoData.setText(dataAtual.formatarDataBr())
        val ano = dataAtual.get(Calendar.YEAR)
        val mes = dataAtual.get(Calendar.MONTH)
        val dia = dataAtual.get(Calendar.DAY_OF_MONTH)


        recursoTransacaoData
            .setOnClickListener {
                DatePickerDialog(
                    context,
                    { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        recursoTransacaoData
                            .setText(dataSelecionada.formatarDataBr())
                    }, ano, mes, dia
                )
                    .show()
            }
    }

    private fun criarLayout(): View {
        return LayoutInflater
            .from(context)
            .inflate(R.layout.form_transacao, viewGroup as ViewGroup, false)
    }

    abstract fun devolveTipoTransacao(): Int
}