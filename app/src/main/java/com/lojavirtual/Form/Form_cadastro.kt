package com.lojavirtual.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.lojavirtual.R
import kotlinx.android.synthetic.main.activity_form_cadastro.*
import com.google.android.material.snackbar.Snackbar
import com.lojavirtual.TelaPrincipal

class Form_cadastro : AppCompatActivity() {

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
                    IrParaTelaLogin()
                }
            }.addOnFailureListener {
                //SE O OBJETO NÃO FOI UM SUCESSO
                Toast.makeText(this,"Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show()
            }
            }
        }
        private fun IrParaTelaLogin(){
            val intent = Intent(this, TelaPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }
