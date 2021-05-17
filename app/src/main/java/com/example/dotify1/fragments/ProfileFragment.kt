package com.example.dotify1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.dotify1.databinding.FragmentProfileBinding
import com.example.dotify1.manager.ApiManager
import com.example.dotify1.model.UserInfo
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    lateinit var apiManager: ApiManager
    lateinit var user: UserInfo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Profile"
        this.apiManager = ApiManager()
        with (binding) {
            swipeToRefresh.setOnRefreshListener {
                loadProfile(binding)
                swipeToRefresh.isRefreshing = false
            }
        }
        loadProfile(binding)
        return binding.root
    }

    private fun loadProfile(binding: FragmentProfileBinding) {
        with(binding) {
            lifecycleScope.launch {
                runCatching {
                    errorMsg.setVisibility(View.GONE)
                    user = apiManager.dataRepository.getUserInfo()
                    username.text = user.username
                    firstName.text = user.firstName
                    lastName.text = user.lastName
                    platform.text = user.platform.toString()
                    profilePic.load(user.profilePicURL)
                    hasNose.text = user.hasNose.toString()
                    Toast.makeText(activity, "Downloading completed", Toast.LENGTH_SHORT).show()

                }.onFailure {
                    errorMsg.setVisibility(View.VISIBLE)
                }
            }
        }
    }
}