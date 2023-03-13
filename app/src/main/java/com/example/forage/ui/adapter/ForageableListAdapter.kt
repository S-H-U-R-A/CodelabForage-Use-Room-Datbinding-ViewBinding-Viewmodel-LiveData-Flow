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
package com.example.forage.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forage.databinding.ListItemForageableBinding
import com.example.forage.model.Forageable

/**
 * ListAdapter for the list of [Forageable]s retrieved from the database
 */
class ForageableListAdapter(
    private val clickListener: (Forageable) -> Unit
) : ListAdapter<Forageable, ForageableListAdapter.ForageableViewHolder>(DiffCallback) {

    class ForageableViewHolder(
        private var binding: ListItemForageableBinding
    ): RecyclerView.ViewHolder( binding.root ) {

        fun bind(forageable: Forageable) {

            //SE DA VALOR A LA VARIABLE A USAR POR EL XML
            binding.forageable = forageable
            //SE EJECUTA LAS ACTUALIZACIONES PENDIENTES
            binding.executePendingBindings()
        }
    }

    //CUANDO SE CREA EL VIEWHOLDER
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForageableViewHolder {
        //SE INFLA LA VISTA Y SE CREA EL VIEWHOLDER
        val layoutInflater = LayoutInflater.from(parent.context)
        return ForageableViewHolder(
            ListItemForageableBinding.inflate(layoutInflater, parent, false)
        )
    }

    //ESTE MÉTODO SE REPIRTE VARIAS VECES POR LOS ELEMENTOS DE LA LISTA A VINCULAR
    //CUANDO SE INVOCA ESTE MÉTODO INTERNAMENTE NOS RETORNA EL VIEWHOLDER Y LA
    //POSICIÓN PULSADA POR EL SUAURIO
    override fun onBindViewHolder(holder: ForageableViewHolder, position: Int) {
        //SE OBTIENE EL ITEM EN LA POSICION
        val forageable = getItem(position)
        //SE CONFIGURA EL EVENTO CLICK DEL ELEMENTO
        holder.itemView.setOnClickListener{
            clickListener(forageable)
        }
        //SE ENVIA AL VIEWHOLDER EL ITEM SELECIONADO
        //PARA QUE EN EL SE TERMINEN LAS CONFIGURACIONES NECESARIAS
        //CON BASE EN LOS DATOS
        holder.bind(forageable)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Forageable>() {
        override fun areItemsTheSame(oldItem: Forageable, newItem: Forageable): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Forageable, newItem: Forageable): Boolean {
            return oldItem == newItem
        }

    }
}
