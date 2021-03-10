package com.lojavirtual.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.lojavirtual.R
import kotlinx.android.synthetic.main.activity_form_cadastro.*
import com.google.android.material.snackbar.Snackbar
import com.lojavirtual.TelaPrincipal
import kotlinx.android.synthetic.main.activity_form_cadastro.edit_email
import kotlinx.android.synthetic.main.activity_form_cadastro.edit_senha
import kotlinx.android.synthetic.main.activity_form_cadastro.frameLayout
import kotlinx.android.synthetic.main.activity_form_login.*

class FormCadastro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)

        supportActionBar!!.hide()

        btn_cadastrar.setOnClickListener {
            CadastrarUsuario()
        }
    }
    private fun CadastrarUsuario(){
        val email = edit_email.text.toString()
        val senha = edit_senha.text.toString()

        if (email.isEmpty() || senha.isEmpty()){  // SE O EMAIL ESTIVER VAZIO E A SENHA ESTIVER VAZIA
            // SNACKBAR  <- MENSAGEM PARSONALIZADA
            var snackbar = Snackbar.make(form_cadastro, "Insira seu e-mail e senha!", Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(Color.BLACK)

            snackbar.show()

        // MENSAGEM PADRÃO
        // Toast.makeText(this,"Ensira seu e-mail e senha", Toast.LENGTH_SHORT).show() //ENVIA MENSAGEM... LENGTH_SHORT(velocidade) .show (executar)



        }else{ // NÃO ESTA VAZIO
            // PEGA A INSTANCIA DO FIREBASE E CRIA UM USUÁRIO COM EMAIL E SENHA
            // .addOnCompleteListener { it. Task<AuthResult!>     <- recebe um objeto it (de autenticação)
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener {

                if (it.isSuccessful){ // SE OBJETO IT QUE RECEBE A AUTENTICAÇÃO FOR UM SUCESSO
                    Toast.makeText(this,"Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show() //ENVIA MENSAGEM
                    frameLayout.visibility = View.VISIBLE  // TORNA O FRAMELAYOUT VISIVEL
                    Handler().postDelayed({IrParaTelaprincipal()}, 2000) //METODO PARA TARDAR A ABERTURA DA OUTRA TELA
                    // IrParaTelaPrincipal()  vai pra outra tela

                }
            }.addOnFailureListener {
                //SE O OBJETO NÃO FOI UM SUCESSO
                Toast.makeText(this,"Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show()
            }
            }
        }
        private fun IrParaTelaprincipal(){
            val intent = Intent(this, TelaPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }
