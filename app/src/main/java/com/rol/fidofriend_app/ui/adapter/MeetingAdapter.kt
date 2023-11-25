package com.rol.fidofriend_app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rol.fidofriend_app.databinding.ItemMeetingBinding
import com.rol.fidofriend_app.model.Meeting
import java.text.SimpleDateFormat
import java.util.*

class MeetingAdapter() : ListAdapter<Meeting, MeetingAdapter.MeetingViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        val binding = ItemMeetingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MeetingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MeetingViewHolder(private val binding: ItemMeetingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(meeting: Meeting) {
            //val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormatTime = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = inputFormat.parse(meeting.date)
            val formattedDate = outputFormatDate.format(date)
            val formattedTime = outputFormatTime.format(date)

            binding.textDateMeet.text = "Fecha: $formattedDate"
            binding.textTimeMeet.text = "Hora: $formattedTime"
            binding.textFinishMeet.text = if (meeting.finish) {
                "Reunión: Realizada"
            } else {
                "Reunión: Pendiente"
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Meeting>() {
        override fun areItemsTheSame(oldItem: Meeting, newItem: Meeting): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Meeting, newItem: Meeting): Boolean {
            return oldItem == newItem
        }
    }
}