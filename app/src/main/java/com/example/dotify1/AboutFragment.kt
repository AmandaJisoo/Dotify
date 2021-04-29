package com.example.dotify1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dotify1.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAboutBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "About"

        with(binding) {
            val context = requireContext()
            appVersion.text = context.getString(R.string.version, BuildConfig.VERSION_NAME)
        }
        return binding.root
    }
}