package com.rol.fidofriend_app.ui.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.rol.fidofriend_app.R
import com.rol.fidofriend_app.data.sharedpref.SessionManager
import com.rol.fidofriend_app.databinding.ActivityMainBinding
import com.rol.fidofriend_app.ui.fragment.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aquí es donde obtienes el ID del usuario de SharedPreferences
        val sessionManager = SessionManager(this)
        val userId = sessionManager.userId

        navigationView = binding.navView
        visibilityOptions()

        binding.btnSalirMain.setOnClickListener {
            exitApp()
        }

        drawerLayout = binding.drawerLayout

        navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectOptionMenu(menuItem.itemId)
            true
        }

        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.menu_24)
            setDisplayHomeAsUpEnabled(true)
            title = null
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.open_menu,
            R.string.close_menu
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null || supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            // Cargar la data del usuario mediante el ID
            Log.d("MainActivity", "User ID probando: $userId")
            if (userId != -1) {
                Log.d("MainActivity", "ID del usuario: $userId")
                val bundle = Bundle()
                bundle.putInt("USER_ID", userId)
                val fragment1 = F_Profile()
                fragment1.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment1)
                    .commit()
            } else {
                Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectOptionMenu(option: Int){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()


        /*when (option) {

            R.id.option_profile -> {
                val userId = intent.getIntExtra("USER_ID", -1)
                if (userId != -1) {
                    val bundle = Bundle()
                    bundle.putInt("USER_ID", userId)
                    val fragment1 = F_Profile()
                    fragment1.arguments = bundle
                    fragmentTransaction.replace(R.id.fragment_container, fragment1)
                } else {
                    // Manejar error
                }
                fragmentTransaction.commit()
            }

            R.id.option_pet -> {
                val userId = intent.getIntExtra("USER_ID", -1)
                if (userId != -1) {
                    val bundle = Bundle()
                    bundle.putInt("USER_ID", userId)
                    val fragment2 = F_Pet()
                    fragment2.arguments = bundle
                    fragmentTransaction.replace(R.id.fragment_container, fragment2)
                } else {
                    // Manejar error
                }
                fragmentTransaction.commit()
            }*/
        // Aquí es donde obtienes el ID del usuario de SharedPreferences
        val sessionManager = SessionManager(this)
        val userId = sessionManager.userId

        when (option) {

            R.id.option_profile -> {
                if (userId != -1) {
                    val bundle = Bundle()
                    bundle.putInt("USER_ID", userId)
                    val fragment1 = F_Profile()
                    fragment1.arguments = bundle
                    fragmentTransaction.replace(R.id.fragment_container, fragment1)
                } else {
                    // Manejar error
                }
                fragmentTransaction.commit()
            }

            R.id.option_pet -> {
                if (userId != -1) {
                    val bundle = Bundle()
                    bundle.putInt("USER_ID", userId)
                    val fragment2 = F_Pet()
                    fragment2.arguments = bundle
                    fragmentTransaction.replace(R.id.fragment_container, fragment2)
                } else {
                    // Manejar error
                }
                fragmentTransaction.commit()
            }

            R.id.option_products -> {
                val isVet = intent.getBooleanExtra("IS_VET", false)
                val fragment3 = F_Product().apply {
                    arguments = Bundle().apply {
                        putBoolean("IS_VET", isVet)
                    }
                }
                fragmentTransaction.replace(R.id.fragment_container, fragment3)
                fragmentTransaction.commit()
            }

            R.id.option_payment -> {
                val fragment4 = F_Payment()
                fragmentTransaction.replace(R.id.fragment_container, fragment4)
                fragmentTransaction.commit()
            }

            R.id.option_services -> {
                val isVet = intent.getBooleanExtra("IS_VET", false)
                val fragment5 = F_Service().apply {
                    arguments = Bundle().apply {
                        putBoolean("IS_VET", isVet)
                    }
                }
                fragmentTransaction.replace(R.id.fragment_container, fragment5)
                fragmentTransaction.commit()
            }

            R.id.option_appointments -> {
                val fragment6 = F_Appointments()
                fragmentTransaction.replace(R.id.fragment_container, fragment6)
                fragmentTransaction.commit()
            }

            R.id.option_petFav -> {
                val fragment7 = F_Favorite()
                fragmentTransaction.replace(R.id.fragment_container, fragment7)
                fragmentTransaction.commit()
            }

            /*R.id.option_meeting -> {
                val fragment8 = F_Meeting()
                fragmentTransaction.replace(R.id.fragment_container, fragment8)
                fragmentTransaction.commit()
            }*/
            R.id.option_meeting -> {
                val bundle = Bundle()
                bundle.putInt("USER_ID", sessionManager.userId)
                bundle.putBoolean("IS_VET", sessionManager.isVet)
                val fragment8 = F_Meeting()
                fragment8.arguments = bundle
                fragmentTransaction.replace(R.id.fragment_container, fragment8)
                fragmentTransaction.commit()
            }

            R.id.option_settings -> {
                val fragment9 = F_Setting()
                fragmentTransaction.replace(R.id.fragment_container, fragment9)
                fragmentTransaction.commit()
            }

            R.id.option_exit_session -> {
                exitSesion()
            }
        }

        // Cierra el MenuDrawer
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
    }


    private fun exitSesion() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Sí") { dialog, _ ->
                val sessionManager = SessionManager(this)
                sessionManager.logoutUser()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    private fun exitApp() {
        AlertDialog.Builder(this)
            .setMessage("¿Está seguro que desea salir de la aplicación?")
            .setCancelable(false)
            .setTitle("Confirmación")
            .setPositiveButton("Sí") { dialog, id ->
                finishAffinity()
                System.exit(0)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
            }
            .show()
    }

    private fun visibilityOptions(){
        // Obténer si el usuario es un Veterinario del Intent
        //val isVet = intent.getBooleanExtra("IS_VET", false)
        val sessionManager = SessionManager(this)
        val isVet = sessionManager.isVet

        navigationView.menu.findItem(R.id.option_profile).isVisible = true // Visible para ambos
        navigationView.menu.findItem(R.id.option_meeting).isVisible = true // Visible para ambos
        navigationView.menu.findItem(R.id.option_services).isVisible = true // Visible para ambos
        navigationView.menu.findItem(R.id.option_products).isVisible = true // Visible para ambos
        navigationView.menu.findItem(R.id.option_settings).isVisible = true // Visible para ambos
        navigationView.menu.findItem(R.id.option_pet).isVisible = !isVet // Solo visible para clientes
        navigationView.menu.findItem(R.id.option_petFav).isVisible = !isVet // Solo visible para clientes
        navigationView.menu.findItem(R.id.option_appointments).isVisible = !isVet // Solo visible para clientes
        navigationView.menu.findItem(R.id.option_payment).isVisible = !isVet // Solo visible para clientes

    }

}