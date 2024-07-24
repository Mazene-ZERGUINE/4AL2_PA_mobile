package com.example.esgithub.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.esgithub.R
import com.example.esgithub.ui.viewModels.ProfileViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()

    private lateinit var userNameTv: TextView
    private lateinit var userImage: ImageView
    private lateinit var emailTv: TextView
    private lateinit var bioTv: TextView
    private lateinit var userName2Tv: TextView

    private val imageBaseUrl: String = "http://"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        userNameTv = rootView.findViewById(R.id.tv_name)
        userImage = rootView.findViewById(R.id.profileImageId)
        emailTv = rootView.findViewById(R.id.emailTv)
        bioTv = rootView.findViewById(R.id.bioTv)
        userName2Tv = rootView.findViewById(R.id.userNameTv2)

        setCurrentUserData()

        return rootView
    }

    private fun setCurrentUserData() {
        profileViewModel.currentUserData.observe(viewLifecycleOwner) {
            userNameTv.text = it.firstName + it.lastName
            it.avatarUrl?.let { it1 -> setUserAvatar(it1) }
            emailTv.text = it.email
            bioTv.text = it.bio
            userName2Tv.text = it.userName
        }
    }

    private fun setUserAvatar(avatarLink: String) {
        Picasso
            .get()
            .load("$imageBaseUrl/$avatarLink")
            .into(
                userImage,
                object : Callback {
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        Log.e("Picasso", "Error loading image: ${e?.message}")
                    }
                }
            )
    }
}
