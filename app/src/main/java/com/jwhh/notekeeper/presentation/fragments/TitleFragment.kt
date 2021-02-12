package com.jwhh.notekeeper.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater, R.layout.fragment_title, container, false)
        binding.playButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_titleFragment_to_gameFragment)
        }
        return binding.root
    }


}