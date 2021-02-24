package com.example.project.model

import android.content.Context
import android.view.ViewGroup
import com.example.financask.R
import com.example.project.extensions.formatarDataBr

class AlteraTransacao(
    private val context: Context,
    viewGroup: ViewGroup,
    private val transacao: Transacoes
): FormularioTransacaoDialog(context, viewGroup,transacao.tipo) {



    fun configuraDialog(transacao: Transacoes,delegate:(transacao: Transacoes) -> Unit) {
        val tipo = transacao.tipo
        super.configuraDialog(delegate)
        inicializandoCampos(transacao, tipo)
    }

    fun inicializandoCampos(
        transacao: Transacoes,
        tipo: TipoEnum
    ) {
        recursoTransacaoValor.setText(transacao.valor.toString())
        recursoTransacaoData.setText((transacao.data.formatarDataBr()))
        val indexOf = pegaCategoria(tipo, transacao)
        recursoTransacaoCategoria.setSelection(indexOf)
    }

    private fun pegaCategoria(
        tipo: TipoEnum,
        transacao: Transacoes
    ): Int {
        val categoriaTipo = context.resources.getStringArray(devolveCategoria(tipo))
        val indexOf = categoriaTipo.indexOf(transacao.categoria)
        return indexOf
    }

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    override fun devolveTipoTransacao(): Int {
        if(transacao.tipo==TipoEnum.RECEITA){
            return R.string.altera_receita
        }
        return  R.string.altera_despesa
    }


}