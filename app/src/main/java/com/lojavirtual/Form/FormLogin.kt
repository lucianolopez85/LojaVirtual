package com.lojavirtual.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.lojavirtual.R
import com.lojavirtual.TelaPrincipal
import com.lojavirtual.databinding.ActivityFormLoginBinding
import kotlinx.android.synthetic.main.activity_form_cadastro.*
import kotlinx.android.synthetic.main.activity_form_login.*
import kotlinx.android.synthetic.main.activity_form_login.edit_email
import kotlinx.android.synthetic.main.activity_form_login.edit_senha

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        VerificarUsuarioLogado()

        binding.textCadastrarConta.setOnClickListener {
            val intent = Intent(this, Form_cadastro::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val msg_erro = binding.msgErroLogin

            if (email.isEmpty() || senha.isEmpty()){
                msg_erro.setText("Preencha todos os campos")
            }else{
                AutenticarUsuario()
            }
        }
    }
    private fun AutenticarUsuario(){
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()
        val msg_erro = binding.msgErroLogin

        // .signInWithEmailAndPassword(email,senha)   <- metodo para entrar com email e senha
        // .addOnCompleteListener {     <- objeto responsável para criar a conta ou autenticar
        // (it.isSuccessful)   <- se usuário foi cadastrado corretamente
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener{
            if(it.isSuccessful) { // se usuário foi logado com sucesso
                Toast.makeText(this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show() //manda a msg
                IrParaTelaPrincipal() // vai pra outra tela
            }
        }.addOnFailureListener {

            var erro = it

            when{
                erro is FirebaseAuthInvalidCredentialsException -> msg_erro.setText("E-mail ou Senha estão incorretos")
                erro is FirebaseNetworkException -> msg_erro.setText("Sem conexão com a internet")
                else -> msg_erro.setText("Erro ao logar usuário") //erro global
            }
        }
    }

    private fun VerificarUsuarioLogado(){

        // .currentUser   <- pega o usuário atual que foi logado
        val usuarioLogado = FirebaseAuth.getInstance().currentUser

        if (usuarioLogado != null){
            IrParaTelaPrincipal()
        }
    }
    private fun IrParaTelaPrincipal(){
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }
}