package com.example.esgithub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.esgithub.R
import com.example.esgithub.ui.viewModels.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()

    private lateinit var userNameTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        userNameTv = rootView.findViewById(R.id.userNameTextView)

        setCurrentUserData()

        return rootView
    }

    private fun setCurrentUserData()  {
        profileViewModel.currentUserData.observe(viewLifecycleOwner) {
            userNameTv.text = it.firstName
        }
    }
}
