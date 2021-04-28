package com.example.dotify1


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.example.dotify1.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private val navController by lazy { findNavController() }
//    private val safeArgs: SettingsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)

        with(binding) {

            profileBtn.setOnClickListener{
                navController.navigate(R.id.profileFragment)
            }

//            StatisticsBtn.setOnClickListener{
//                navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToStatisticsFragment(safeArgs.song, safeArgs.playCount))
//            }

            aboutBtn.setOnClickListener{
                navController.navigate(R.id.aboutFragment)
            }

        }
        return binding.root
    }
}