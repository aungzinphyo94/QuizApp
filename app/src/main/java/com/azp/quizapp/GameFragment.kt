package com.azp.quizapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.azp.quizapp.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 */


class GameFragment : Fragment() {

    data class Question(
        var text: String,
        var answers: List<String>
    )

    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "What is Android Jetpack?",
            answers = listOf("All of these", "Tools", "Documentation", "Libraries")
        ),
        Question(
            text = "What is the base class for layout?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")
        ),
        Question(
            text = "What layout do you for complex screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")
        ),
        Question(
            text = "What do you use to push structured data into layout?",
            answers = listOf("DataBinding", "Data pushing", "setText", "OnClick Method")
        ),
        Question(
            text = "What is the build system for Android?",
            answers = listOf("Gradle", "Groyle", "Maven", "Ants")
        ),
        Question(
            text = "Which one of these is Android Navigation component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitch")
        ),
        Question(
            text = "What do you use to mark a layout for databinding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")
        ),
        Question(
            text = "Which XML element lets you register an activity with the launcher activity?",
            answers = listOf("intent-filter", "app-registry", "laucher-registry", "app-launcher")
        ),
        Question(
            text = "Which class do you to create a vector drawable?",
            answers = listOf(
                "VectorDrawable",
                "AndroidVectorDrawable",
                "DrawableVector",
                "AndroidVector"
            )
        )
    )

    private var questionIndex = 0
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private val numQuestion = 5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false
        )

        randomQuestions()

        binding.game = this

        binding.submitButton.setOnClickListener { view: View ->

            val checkId = binding.questionRadioGroup.checkedRadioButtonId
            if (-1 != checkId) {

                var answerIndex = 0

                when (checkId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }

                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++

                    if (questionIndex < numQuestion) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        //refresh the ui
                        binding.invalidateAll()
                    } else {
                        view.findNavController()
                            .navigate(R.id.action_gameFragment_to_gameWonFragment)
                    }
                } else {
                    view.findNavController()
                        .navigate(R.id.action_gameFragment_to_gameLoseFragment)
                }
            }

        }

        return binding.root
    }

    //random the questions
    private fun randomQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    //Set the question and random the answer.
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()

        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.action_title,
                questionIndex+1, numQuestion)

        //Question 1/5
    }
}
