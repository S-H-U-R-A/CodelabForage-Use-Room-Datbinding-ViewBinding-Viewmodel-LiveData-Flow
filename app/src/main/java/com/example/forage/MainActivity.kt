/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.forage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.forage.databinding.ActivityMainBinding

/**
 * A Main activity that hosts all [Fragment]s for this application and hosts the nav controller.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //SE VINCULA LA VISTA XML
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //SE LE INDICA A LA APP QUE EL ACTION BAR
        //VA A SER EL TOOLBAR PROPIO INCLUIDO EN
        //EL APPBAR_LAYOUT PROPIO DEL XML
        setSupportActionBar(binding.toolbar)

        //SE OBTIENE EL NAVCONTROLLER, EN LAS NUEVAS VERSIONES DE NAVIGATION
        //SE HACE A TRAVES DE SUPPORTFRAGMENT
        val navController = findNavController(R.id.nav_host_fragment_content_main)


        //SE CONFIGURA LA BARRA DE APLICACIONES Y SE LE PASA EL PARÁMETRO
        //DE GRÁFICO DE NAVEGACIÓN, QUE ES LA REPRESENTACIÓN VISUAL DE LAS
        //PANTALLAS Y FLUJOS DE NAVEHGACIÓN
        appBarConfiguration = AppBarConfiguration( navController.graph )

        //SE CONFIGURA EL ACTION BAR, PARA QUE SEA CONTROLADO POR EL NAVCONTROLLER
        //Y LA CONFIGURACIÓN DEL APPBAR
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    //SE CREA EL MENÚ DE OPCIONES
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //SE OBTIENE EL ITEM AL CUAL LE HICIERON CLICK
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //SE CONFIGURA LA NAVEGACIÓN HACIA ATRÁS MEDIANTE EL NAVCONTROLLER
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
