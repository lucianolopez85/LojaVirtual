package com.lojavirtual

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.firebase.auth.FirebaseAuth
import com.lojavirtual.Form.FormLogin
import com.lojavirtual.Fragments.CadastroProdutos
import com.lojavirtual.Fragments.Produtos

class TelaPrincipal : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val produtosFragment = Produtos()
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.frame_container, produtosFragment)
        fragment.commit()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

    //INICIO NAVBAR LATERAL
    //É NECESSÁRIO REFAZER O MENU, POIS SERÁ USADO FRAGMENTOS E EVENTOS DE CLICK
        val toggle = ActionBarDrawerToggle(this,
        drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId


        //AÇÕES DENTRO DO MENU
        if (id == R.id.nav_home_produtos){ // SE ID FOR IGUAL ID (item do menu)

            val produtosFragment = Produtos()
            val fragment = supportFragmentManager.beginTransaction()
            fragment.replace(R.id.frame_container, produtosFragment)
            fragment.commit()

        }else if (id == R.id.nav_cadastrar_produto){

            var intent = Intent(this, CadastroProdutos::class.java)
            startActivity(intent)

        }else if (id == R.id.nav_contato){

            enviarEmail()

        }
        val drawer =  findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    // FIM NAV BAR LATERAL

    // INICIO DESLOGAR
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.tela_principal,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.deslogar -> {
                FirebaseAuth.getInstance().signOut()
                VoltarTelaLogin()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun VoltarTelaLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
    //FIM DESLOGAR

    //INICIO ENVIAR E-MAIL

    private fun enviarEmail(){

        val PACKAGEM_GOOGLEMAIL = "com.google.android.gm"
        val email = Intent(Intent.ACTION_SEND) //ACTION_SEND <- AÇÃO DE ENVIAR E-MAIL
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf("")) //ENVIAR UM E-MAIL
        email.putExtra(Intent.EXTRA_SUBJECT, "") //DEFINIR O ASSUNTO DO E-MAIL
        email.putExtra(Intent.EXTRA_TEXT, "")//DEFINIR TEXTO

        //CONFIGURAÇÃO de apps de envio de e-mail

        email.type = "message/rec822" //DEFINIR QUE ABRA APLICATIVOS DE E-MAIL
        email.setPackage(PACKAGEM_GOOGLEMAIL)
        startActivity(Intent.createChooser(email, "Escolha o app de e-mail"))

    }
    //FIM ENVIAR E-MAIL

}