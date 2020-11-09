package com.jwhh.notekeeper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    data class Question(
            val text: String,
            val answers: List<String>)

    // the fist answer is the correct one
    private val questions: MutableList<Question> = mutableListOf(
            Question(text = "What is Android Jetpack?",
                    answers = listOf("All of these", "Tools", "Documentation", "Libraries")),
            Question(text = "What is the base class for layouts?",
                    answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
            Question(text = "What layout do you use for complex screens?",
                    answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
            Question(text = "What do you use to push structured data into a layout?",
                    answers = listOf("Data binding", "Data pushing", "Set text", "An OnClick method")),
            Question(text = "What method do you use to inflate layouts in fragments?",
                    answers = listOf("onCreateView()", "onActivityCreated()", "onCreateLayout()", "onInflateLayout()")),
            Question(text = "What's the build system for Android?",
                    answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
            Question(text = "Which class do you use to create a vector drawable?",
                    answers = listOf("VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
            Question(text = "Which one of these is an Android navigation component?",
                    answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")),
            Question(text = "Which XML element lets you register an activity with the launcher activity?",
                    answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")),
            Question(text = "What do you use to mark a layout for data binding?",
                    answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater, R.layout.fragment_game, container, false)

        //shuffles the questions
        randomizeQuestions()

        binding.game = this

        binding.submitButton.setOnClickListener { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            if (checkedId != -1) {
                var anserIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> anserIndex = 1
                    R.id.thirdAnswerRadioButton -> anserIndex = 2
                    R.id.fourthAnswerRadioButton -> anserIndex = 3
                }

                if (answers[anserIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    if (questionIndex < numQuestions) {
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // we won!
                        view.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
                    }
                } else {
                    // we fail
                    view.findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment2)
                }
            }
        }

        return binding.root
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()
    }
}