package com.rol.fidofriend_app.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rol.fidofriend_app.data.sharedpref.SessionManager
import com.rol.fidofriend_app.databinding.FragmentFMeetingBinding
import com.rol.fidofriend_app.ui.adapter.MeetingAdapter
import com.rol.fidofriend_app.ui.viewmodel.MeetingViewModel


class F_Meeting : Fragment() {

    private var _binding: FragmentFMeetingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MeetingViewModel by activityViewModels()
    private lateinit var adapter: MeetingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFMeetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MeetingAdapter()

        binding.recyclerViewMeeting.adapter = adapter

        val sessionManager = SessionManager(requireContext())
        val userId = sessionManager.userId
        val isVet = sessionManager.isVet

        Log.d("F_Meeting", "ID del usuario: $userId")
        Log.d("F_Meeting", "Es veterinario: $isVet")

        if (isVet) {
            viewModel.getMeetingsByVet(userId)
        } else {
            viewModel.getMeetingsByOwner(userId)
        }

        viewModel.meetings.observe(viewLifecycleOwner, Observer { meetings ->
            adapter.submitList(null)
            adapter.submitList(meetings)
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

