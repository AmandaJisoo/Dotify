package com.example.dotify1.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ericchee.songdataprovider.Song
import com.example.dotify1.*
//import com.example.dotify1.SettingsFragmentArgs
//import com.example.dotify1.SettingsFragmentDirections
import com.example.dotify1.databinding.FragmentSettingsBinding
import com.example.dotify1.manager.NotificatonManager

class SettingsFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private val dotifyApplication by lazy { requireActivity().application as DotifyApplication }
    private val preferences by lazy { dotifyApplication.preferences }
    private val safeArgs: SettingsFragmentArgs by navArgs()
    private val newSongNotificationManager: NotificatonManager by lazy { dotifyApplication.newSongNotificationManager }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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


            //notifiaction begins
            notificatonBtn.isChecked = preferences.getBoolean(NOTIFICATION_ON, false)


            notificatonBtn.setOnCheckedChangeListener { _, isOn ->
                preferences.edit {
                    putBoolean(NOTIFICATION_ON, isOn)
                }

                //TODO: maybe here?
                if (isOn) {
                    newSongNotificationManager.triggerNotificationRepetitive()
                } else {
                    newSongNotificationManager.cancelNotificationRepetitive()
                }
            }
        }

        return binding.root
    }
}