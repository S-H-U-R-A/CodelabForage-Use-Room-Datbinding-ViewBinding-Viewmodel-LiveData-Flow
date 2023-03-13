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
package com.example.forage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.forage.BaseApplication
import com.example.forage.R
import com.example.forage.databinding.FragmentAddForageableBinding
import com.example.forage.model.Forageable
import com.example.forage.ui.viewmodel.ForageableViewModel
import com.example.forage.ui.viewmodel.ForageableViewModelFactory


/**
 * A fragment to enter data for a new [Forageable] or edit data for an existing [Forageable].
 * [Forageable]s can be saved or deleted from this fragment.
 */
class AddForageableFragment : Fragment() {

    //SAFE ARGS
    private val navigationArgs: AddForageableFragmentArgs by navArgs()

    //VARIABLE DE VINCULACIÓN
    private var _binding: FragmentAddForageableBinding? = null
    private val binding get() = _binding!!

    //OBJETO FORAGE
    private lateinit var forageable: Forageable

    // TODO: Refactor the creation of the view model to take an instance of
    //  ForageableViewModelFactory. The factory should take an instance of the Database retrieved
    //  from BaseApplication
    private val viewModel: ForageableViewModel by activityViewModels{
        ForageableViewModelFactory(
            (requireActivity().application as BaseApplication).database.forageableDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //SE VINCULA LA VISTA XML CON SU CODEBEHIND
        _binding = FragmentAddForageableBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //SE OBTIENE EL VALOR DE SAFEARGS
        val id = navigationArgs.id

        //SE VALIDA PARA SABER EN ESTA PANTALLA QUE SE DEBE HACER
        //SI EDITAR EL FORAGE O CREAR UNO NUEVO, ESTA VALIDACIÓN
        //SE HACE CON BASE EN EL VALOR POR DEFECTO DE -1 QUE SE
        //SE ASIGNO EN EL NAVIGATION GRAPH
        if (id > 0) {

            //SI ENTRO POR ACÁ QUIERE DECIR QUE EL ITEM EXISTE, ENTONCES
            //HAY QUE RECUPERARLO

            // TODO: Observe a Forageable that is retrieved by id, set the forageable variable,
            //  and call the bindForageable method
            //SE RECUPERA EL OBJETO POR EL ID
            viewModel.getForageable(id).observe( viewLifecycleOwner){
                forageable = it
                bindForageable(forageable)
            }

            //SE MUESTRA EL BOTÓN DE BORRAR EL ITEM
            binding.deleteBtn.visibility = View.VISIBLE

            //SE LLAMA AL MÉTODO QUE ELIMINA EL ITEM
            binding.deleteBtn.setOnClickListener {
                deleteForageable(forageable)
            }

        } else {
            //SI ESTA ACÁ QUIERE DECIR QUE QUIERE CREAR UN NUEVO ITEM
            binding.saveBtn.setOnClickListener {
                addForageable()
            }
        }
    }

    //SE VINCULAN LOS DATOS Y SE ASIGNAN EVENTOS
    private fun bindForageable(forageable: Forageable) {
        binding.apply{
            nameInput.setText(forageable.name, TextView.BufferType.SPANNABLE)
            locationAddressInput.setText(forageable.address, TextView.BufferType.SPANNABLE)
            inSeasonCheckbox.isChecked = forageable.inSeason
            notesInput.setText(forageable.notes, TextView.BufferType.SPANNABLE)
            saveBtn.setOnClickListener {
                //SE ASIGNA EL CLICK AL BTN GUARDAR
                updateForageable()
            }
        }

    }

    //SE CERA EL ITEM
    private fun addForageable() {
        //SE VALIDAN LOS CAMPOS PRINCIPALES
        if (isValidEntry()) {
            viewModel.addForageable(
                binding.nameInput.text.toString(),
                binding.locationAddressInput.text.toString(),
                binding.inSeasonCheckbox.isChecked,
                binding.notesInput.text.toString()
            )
            //SE NAVEGA A LA LISTA DESPUES DE GUARDAR
            findNavController().navigate(
                R.id.action_addForageableFragment_to_forageableListFragment
            )
        }
    }

    //GUARADAR SI SE HA EDITADO UN ITEM
    private fun updateForageable() {
        //SE VALIDAN LOS CAMPOS
        if ( isValidEntry() ) {
            //
            viewModel.updateForageable(
                id = navigationArgs.id,
                name = binding.nameInput.text.toString(),
                address = binding.locationAddressInput.text.toString(),
                inSeason = binding.inSeasonCheckbox.isChecked,
                notes = binding.notesInput.text.toString()
            )
            //REGRESAMOS A LA LISTA
            findNavController().navigate(
                R.id.action_addForageableFragment_to_forageableListFragment
            )
        }
    }

    //SE ELOIMINA EL ITEM, CUANDO SE ESTA EDITANDO
    private fun deleteForageable(forageable: Forageable) {
        viewModel.deleteForageable(forageable)
        //SE NAVEGA A LA LISTA
        findNavController().navigate(
            R.id.action_addForageableFragment_to_forageableListFragment
        )
    }

    private fun isValidEntry(): Boolean = viewModel.isValidEntry(
        binding.nameInput.text.toString(),
        binding.locationAddressInput.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
