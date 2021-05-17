package com.example.dotify1.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dotify1.R
//import com.example.dotify1.SettingsFragmentArgs
//import com.example.dotify1.SettingsFragmentDirections
import com.example.dotify1.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private val safeArgs: SettingsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Settings"

        with(binding) {

            profileBtn.setOnClickListener{
                navController.navigate(R.id.profileFragment)
            }

            StatisticsBtn.setOnClickListener{
                navController.navigate(
                    SettingsFragmentDirections.actionSettingsFragmentToStatisticsFragment(
                        safeArgs.numOfPlay,
                        safeArgs.curSong
                    )
                )
            }

            aboutBtn.setOnClickListener{
                navController.navigate(R.id.aboutFragment)
            }

        }
        return binding.root
    }
}