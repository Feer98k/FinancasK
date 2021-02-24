package com.example.project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.financask.R
import com.example.project.extensions.formataCategoria
import com.example.project.extensions.formataMoedaBrasil
import com.example.project.extensions.formatarDataBr

import com.example.project.model.TipoEnum
import com.example.project.model.Transacoes

class ListTransacoesAdapter(private val transacoes: List<Transacoes>,
                            private val context: Context) : BaseAdapter() {
    private val CARACTERES_MAXIMO = 14

    override fun getCount(): Int {
        return transacoes.size
    }

    override fun getItem(position: Int): Transacoes {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflate = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)
        val transacao = transacoes[position]
        inflateViews(inflate, transacao)
        setColorAndIcon(transacao, inflate)
        return inflate
    }

    private fun inflateViews(
        inflate: View,
        transacao: Transacoes
    ) {
        inflate.findViewById<TextView>(R.id.transacao_valor).text = transacao.valor.formataMoedaBrasil()
        inflate.findViewById<TextView>(R.id.transacao_categoria).text = transacao.categoria.formataCategoria(CARACTERES_MAXIMO)
        inflate.findViewById<TextView>(R.id.transacao_data).text = transacao.data.formatarDataBr()
    }

    private fun setColorAndIcon(
        transacao: Transacoes,
        inflate: View
    ) {
        if (transacao.tipo == TipoEnum.RECEITA) {
            inflate.findViewById<TextView>(R.id.transacao_valor).setTextColor(ContextCompat.getColor(context, R.color.green))
            inflate.findViewById<ImageView>(R.id.transacao_icone).setBackgroundResource(R.drawable.icone_transacao_item_receita)
        } else {
            inflate.findViewById<TextView>(R.id.transacao_valor).setTextColor(ContextCompat.getColor(context, R.color.red))
            inflate.findViewById<ImageView>(R.id.transacao_icone).setBackgroundResource(R.drawable.icone_transacao_item_despesa)

        }
    }

}
