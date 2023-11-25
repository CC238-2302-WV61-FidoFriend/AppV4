package com.rol.fidofriend_app.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.rol.fidofriend_app.data.sharedpref.SessionManager
import com.rol.fidofriend_app.databinding.ActivityAddMeetingBinding
import com.rol.fidofriend_app.model.Meeting
import com.rol.fidofriend_app.ui.viewmodel.MeetingViewModel
import java.util.*

class AddMeetingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMeetingBinding
    private val viewModel: MeetingViewModel by viewModels()
    private val vetId by lazy { intent.getIntExtra("VET_ID", 0) }
    private val clientId by lazy { SessionManager(this).userId }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
                binding.editTextDate.setText(selectedDate)
            }, year, month, day).show()
        }


        binding.editTextTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                binding.editTextTime.setText(selectedTime)
            }, hour, minute, true).show()
        }

        binding.btnSubmit.setOnClickListener {
            val letra = "T"
            val date = binding.editTextDate.text.toString()
            val time = binding.editTextTime.text.toString()
            val dateTime = "$date$letra$time:00.000Z"
            val meeting = Meeting(
                date = dateTime,
                finish = false,
                vetId = vetId,
                clientId = clientId
            )
            viewModel.postMeeting(meeting)
        }

        viewModel.postStatus = { meeting ->
            if (meeting != null) {
                Toast.makeText(this, "Reunión creada con éxito", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al crear la reunión", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
