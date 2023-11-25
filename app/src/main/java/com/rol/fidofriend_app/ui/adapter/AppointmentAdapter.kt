package com.rol.fidofriend_app.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rol.fidofriend_app.databinding.ItemAppointmentBinding
import com.rol.fidofriend_app.model.User
import com.rol.fidofriend_app.ui.activity.AddMeetingActivity

class AppointmentAdapter() : ListAdapter<User, AppointmentAdapter.AppointmentViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding = ItemAppointmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppointmentViewHolder(private val binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.nameTextUser.text = "Nombre: ${user.firstName} ${user.lastName}"
            binding.emailTextUser.text = "Email: " + user.email
            binding.vetText.text = if (user.isVet) "Tipo Usuario: Veterinario" else ""
            Glide.with(binding.root)
                .load(user.imgUrl)
                .into(binding.profileImageUser)

            binding.buttonReservarMeet.setOnClickListener {
                val intent = Intent(binding.root.context, AddMeetingActivity::class.java)
                intent.putExtra("VET_ID", user.id)
                binding.root.context.startActivity(intent)
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.firstName == newItem.firstName
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}