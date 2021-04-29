package com.example.dotify1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.dotify1.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private val safeArgs: StatisticsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatisticsBinding.inflate(inflater)

        with(binding) {
            settingAlbumCover.setImageResource(safeArgs.curSong.largeImageID)
            numOfPlay.text = root.context.getString(
                R.string.statInfo,
                safeArgs.curSong.title,
                safeArgs.numOfPlay)
        }

        return binding.root
    }
}