package com.example.project.dao

import com.example.project.model.Transacoes

class ListaTransacoesDao {
    val listaTransacoes = Companion.listaTransacoes
    companion object {
         val listaTransacoes = mutableListOf<Transacoes>()
    }

    fun adiciona(transacoes: Transacoes) {
        Companion.listaTransacoes.add(transacoes)
    }

    fun altera(position: Int, transacao: Transacoes) {
        Companion.listaTransacoes[position] = transacao
    }

    fun remove(position: Int) {
        Companion.listaTransacoes.removeAt(position)
    }
}