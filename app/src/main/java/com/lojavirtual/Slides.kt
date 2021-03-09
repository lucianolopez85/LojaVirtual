package com.lojavirtual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide
import com.lojavirtual.Form.FormLogin

class Slides : IntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_slides)

        isButtonBackVisible = false  // deixa o botão de retornar invisível
        isButtonNextVisible = false
        isButtonCtaVisible = false


        // SLIDE 1
        addSlide(

            SimpleSlide.Builder()
                .background(R.color.Roxo)
                .backgroundDark(R.color.white)
                .image(R.drawable.drawer)
                .title("Loja Online de Calçados")
                .description("Entre e confira as promoções que preparamos para você!")
                .build()
        )

        // SLIDE 2
        addSlide(

            SimpleSlide.Builder()
                .background(R.color.AV)
                .backgroundDark(R.color.white)
                .title("Crie uma conta grátis")
                .description("Cadastra-se agora mesmo! E venha conhecer os nossos produtos")
                .canGoBackward(false) //NÃO DA PRA VOLTAR AO SLIDE ANTERIOR
                .build()

        )
    }

    // VAI DESTRUIR OS DOIS SLIDES ACIMA E VAI EXECUTAR O LAYOUT FORM LOGIN
    override fun onDestroy() {
        super.onDestroy()

        var intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }

}