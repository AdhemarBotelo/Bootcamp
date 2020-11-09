package com.jwhh.notekeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.jwhh.notekeeper.databinding.ActivitySurveyBinding


class SurveyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySurveyBinding>(this, R.layout.activity_survey)

        val navController = this.findNavController(R.id.NavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.NavHostFragment)
        return navController.navigateUp()
    }
}