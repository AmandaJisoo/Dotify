package com.example.dotify1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.dotify1.R
import com.example.dotify1.databinding.FragmentStatisticsBinding


class StatisticsFragment : Fragment() {

    private val safeArgs: StatisticsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatisticsBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Statistics"

        with(binding) {
            settingAlbumCover.load(safeArgs.curSong.largeImageURL)
            numOfPlay.text = root.context.getString(
                R.string.statInfo,
                safeArgs.curSong.title,
                safeArgs.numOfPlay)
        }

        return binding.root

    }
}