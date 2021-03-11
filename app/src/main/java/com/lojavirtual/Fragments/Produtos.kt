package com.lojavirtual.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.dialog.MaterialDialogs
import com.google.firebase.firestore.FirebaseFirestore
import com.lojavirtual.Model.Dados
import com.lojavirtual.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_produtos.*
import kotlinx.android.synthetic.main.lista_produtos.view.*
import kotlinx.android.synthetic.main.pagamento.*
import java.security.acl.Group
import java.text.ParsePosition


class Produtos : Fragment() {


    private lateinit var Adapter: GroupAdapter<ViewHolder>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_produtos, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Adapter = GroupAdapter()
        recycler_produtos.adapter = Adapter
        Adapter.setOnItemClickListener { item, view -> // EVENTO DE CLICK NA LISTA

            val DialogView = LayoutInflater.from(context).inflate(R.layout.pagamento, null)
            val builder = AlertDialog.Builder(context)
                .setView(DialogView)
                .setTitle("Formas de Pagamento")

            val mAlertDialog = builder.show()
            mAlertDialog.btn_pagar.setOnClickListener {

                mAlertDialog.dismiss()
                val pagamento = mAlertDialog.forma_pagamento.text.toString()

                if (pagamento.isEmpty()){
                    Toast.makeText(context, "Pagamento Recusado", Toast.LENGTH_SHORT).show()
                }else{
                    MaterialDialog.Builder(this!!.requireContext())
                        .title("Pagamento Concluído")
                        .content("Obrigado pela compra! Volte sempre.")
                        .show()
                }
            }
        }

        BuscarProdutos()

    }
    private inner class ProdutosItem(internal  val adProdutos: Dados): Item<ViewHolder>() {
        override fun getLayout(): Int {
            return R.layout.lista_produtos

        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.edit_nome_produto.text = adProdutos.nome
            viewHolder.itemView.edit_preco_produto.text = adProdutos.preco
            Picasso.get().load(adProdutos.uid).into(viewHolder.itemView.foto_produto)
        }
    }

    private fun BuscarProdutos(){         // METODO QUE VAI BUSCAR OS PRODUTOS QUE ESTÃO SALVOS

        FirebaseFirestore.getInstance().collection("Produtos") // BUSCAR OS PRODUTOS NA COLEÇÃO DE Produtos
            .addSnapshotListener { snapshot, exception ->
                exception?.let {
                    return@addSnapshotListener
                }
                snapshot?.let {
                    for (doc in snapshot) {
                        val produtos = doc.toObject(Dados::class.java)
                        Adapter.add(ProdutosItem(produtos))
                    }
                }
            }
    }
}