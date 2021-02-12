package com.jwhh.notekeeper.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.databinding.FragmentGameOverBinding


class GameOverFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentGameOverBinding>(inflater, R.layout.fragment_game_over, container, false)
        binding.tryAgainButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_gameOverFragment_to_gameFragment)
        }
        return binding.root
    }

}