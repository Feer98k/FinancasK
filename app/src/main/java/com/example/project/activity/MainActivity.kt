package com.example.project.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.project.adapter.ListTransacoesAdapter
import com.example.project.dao.ListaTransacoesDao
import com.example.project.model.*
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class MainActivity : AppCompatActivity() {
    private val dao = ListaTransacoesDao()
    private val listaTransacoes = dao.listaTransacoes

    private val viewList by lazy {
        window.decorView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)
        configuraResumo()
        configuraFAB()
    }

    override fun onResume() {
        atualizaLista()
        super.onResume()

    }

    private fun configuraFAB() {
        lista_transacoes_adiciona_despesa.setOnClickListener {
            invocarDialog(TipoEnum.DESPESA)
        }
        lista_transacoes_adiciona_receita.setOnClickListener {
            invocarDialog(TipoEnum.RECEITA)
        }
    }

    private fun invocarDialog(tipo: TipoEnum) {
        AdicionaTransacao(this, viewList as ViewGroup, tipo)
            .configuraDialog (delegate = {transacaoAdicionar ->
                adicona(transacaoAdicionar)
                lista_transacoes_adiciona_menu.close(true)
            })

    }

    private fun adicona(transacao: Transacoes) {
        dao.adiciona(transacao)
        atualizaLista()
    }

    private fun configuraResumo(): View {
        ResumoView(viewList, listaTransacoes, this).configuraViewResumo()
        return viewList
    }

    private fun atualizaLista() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraLista() {
        with(lista_transacoes_listview) {
            adapter = ListTransacoesAdapter(listaTransacoes, this@MainActivity)
            setOnItemClickListener { _, _, posicao, _ ->
                onItemListClick(posicao)
            }
            setOnCreateContextMenuListener { menu, v, menuInfo ->
                menu.add(Menu.NONE,1,Menu.NONE,"Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val itemId = item?.itemId
        if(itemId ==1){
            removeTransacao(item)
        }
        atualizaLista()
        return super.onContextItemSelected(item)
    }

    private fun removeTransacao(item: MenuItem) {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        dao.remove(menuInfo.position)
    }

    private fun onItemListClick(posicao: Int) {
        val transacaoClicada = listaTransacoes[posicao]
        AlteraTransacao(this@MainActivity, viewList as ViewGroup, transacaoClicada)
            .configuraDialog (transacaoClicada, delegate = { transacaoAlterada ->
                altera(posicao, transacaoAlterada)
                })

    }

    private fun altera(posicao: Int, transacao: Transacoes) {
        dao.altera(posicao,transacao)
        atualizaLista()
    }


}