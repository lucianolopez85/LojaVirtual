package com.lojavirtual.Fragments

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.lojavirtual.Model.Dados
import com.lojavirtual.R
import kotlinx.android.synthetic.main.activity_cadastro_produtos.*
import java.util.*

class CadastroProdutos : AppCompatActivity() {

    private var SelecionarUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_produtos)

        supportActionBar!!.hide()

        btn_selecionar_foto.setOnClickListener {

            SelecionarFotoGaleria()
        }

        btn_cadastrar_produto.setOnClickListener {

            SalvarDadosFirebase()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0){  // SE O REQUEST CODE FOI IGUAL A ZERO
            SelecionarUri = data?.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, SelecionarUri)
            foto_produto.setImageBitmap(bitmap)
            btn_selecionar_foto.alpha = 0f

        }
    }

    private fun SelecionarFotoGaleria(){

        val intent = Intent(Intent.ACTION_PICK) // FUNÇÃO PARA PEGAR FOTO DA GALERIA OU FOTO
        intent.type = "image/*"  // DEFININDO O TIPO IMAGEM - CASO CONTRÁRIO PODE SELECIONAR QUALQUER COISA
        startActivityForResult(intent, 0) //
    }

    private fun SalvarDadosFirebase(){

        val nomeArquivo = UUID.randomUUID().toString()
        val referencia = FirebaseStorage.getInstance().getReference("/image/${nomeArquivo}") // CRIA PASTA NO FIREBASE

        SelecionarUri?.let {

            referencia.putFile(it)
                .addOnSuccessListener {
                    referencia.downloadUrl.addOnSuccessListener {

                        val url = it.toString()
                        val nome = edit_nome_produto.text.toString()
                        val preco = edit_preço_produto.text.toString()
                        val uid = FirebaseAuth.getInstance().uid

                        val Produtos = Dados(url, nome, preco)
                        FirebaseFirestore.getInstance().collection("Produtos")
                            .add(Produtos)
                            .addOnSuccessListener {
                                Toast.makeText(this,"Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {
                                Toast.makeText(this,"Erro ao cadastrar o Produto", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
        }
    }
}