package com.example.coursehandicap.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coursehandicap.R
import com.example.coursehandicap.databinding.FragmentCourseHandicapCalculationBinding
import com.example.coursehandicap.model.CalculationData
import kotlin.math.roundToInt

class CourseHandicapCalculationFragment : Fragment(R.layout.fragment_course_handicap_calculation) {

    private lateinit var binding: FragmentCourseHandicapCalculationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCourseHandicapCalculationBinding.bind(view)
        listeners()

        binding.calculateButton.setOnClickListener {
            if (isDataValid()) {
                val calculationData = CalculationData(
                    handicapIndex = binding.handicapIndexEditText.text.toString().toDouble(),
                    courseRating = binding.courseRatingEditText.text.toString().toDouble(),
                    slopeRating = binding.slopeRatingEditText.text.toString().toInt(),
                    par = binding.parEditText.text.toString().toInt()
                )
                val result = calculateCourseHandicap(calculationData)
                val roundedResult = result.roundToInt()
                clearEditText()
                showResult(result, roundedResult)
            } else {
                Toast.makeText(context, "Проверьте введенные данные", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        with(binding) {
            slopeRatingEditText.text = null
            slopeRatingTextInput.error = null
            parEditText.text = null
            parTextInput.error = null
            handicapIndexEditText.text = null
            handicapIndexTextInput.error = null
            courseRatingEditText.text = null
            courseRatingTextInput.error = null
            numberOfHolesRadioGroup.clearCheck()
        }
    }

    private fun showResult(result: Double, roundedResult: Int) {
        val direction =
            CourseHandicapCalculationFragmentDirections.actionCourseHandicapCalculationFragmentToCourseHandicapResultsFragment(
                roundedResult,
                result.toFloat()
            )
        findNavController().navigate(direction)
    }

    private fun calculateCourseHandicap(data: CalculationData): Double {
        return (data.handicapIndex * data.slopeRating) / 113 + (data.courseRating - data.par)
    }

    private fun listeners() {
        handicapIndexListener()
        slopeRatingListener()
        courseRatingListener()
        parRatingListener()
    }

    private fun isDataValid(): Boolean {
        return isParValid() && isSlopeRatingValid() && isCourseRatingValid() && isHandicapIndexValid()
    }

    private fun isParValid(): Boolean {
        val holeNumber = binding.numberOfHolesRadioGroup.checkedRadioButtonId
        val par = binding.parEditText.text.toString()
        return when (holeNumber) {
            R.id.nine_holes_radio_button -> {
                par.isNotEmpty() && (par.toInt() in 25..45)
            }
            R.id.eighteen_holes_radio_button -> {
                par.isNotEmpty() && (par.toInt() in 50..85)

            }
            else -> false
        }
    }

    private fun isCourseRatingValid(): Boolean {
        val holeNumber = binding.numberOfHolesRadioGroup.checkedRadioButtonId
        val courseRating = binding.courseRatingEditText.text.toString()
        return when (holeNumber) {
            R.id.nine_holes_radio_button -> {
                courseRating.isNotEmpty() && (courseRating.toDouble() in 25.0..43.0)
            }
            R.id.eighteen_holes_radio_button -> {
                courseRating.isNotEmpty() && (courseRating.toDouble() in 50.0..86.0)
            }
            else -> false
        }
    }

    private fun isHandicapIndexValid(): Boolean {
        val handicapIndex = binding.handicapIndexEditText.text.toString()
        return (handicapIndex.isNotEmpty() && (handicapIndex.toDouble() in -10.0..54.0))
    }

    private fun isSlopeRatingValid(): Boolean {
        val slopeRating = binding.slopeRatingEditText.text.toString()
        return (slopeRating.isNotEmpty() && (slopeRating.toInt() in 55..155))
    }

    private fun handicapIndexListener() {
        binding.handicapIndexEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                if (!isHandicapIndexValid()) {
                    binding.handicapIndexTextInput.error = getString(R.string.handicapIndexError)
                } else {
                    binding.handicapIndexTextInput.error = null
                }
            }
        }
    }

    private fun slopeRatingListener() {
        binding.slopeRatingEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                if (!isSlopeRatingValid()) {
                    binding.slopeRatingTextInput.error = getString(R.string.slopeRatingError)
                } else {
                    binding.slopeRatingTextInput.error = null
                }
            }
        }
    }

    private fun courseRatingListener() {
        binding.courseRatingEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                if (!isCourseRatingValid()) {
                    val holeNumber = binding.numberOfHolesRadioGroup.checkedRadioButtonId
                    when (holeNumber) {
                        R.id.nine_holes_radio_button -> {
                            binding.courseRatingTextInput.error =
                                getString(R.string.courseRatingNineError)
                        }
                        R.id.eighteen_holes_radio_button -> {
                            binding.courseRatingTextInput.error =
                                getString(R.string.courseRatingEighteenError)
                        }
                        else -> binding.courseRatingTextInput.error =
                            getString(R.string.notCheckedError)
                    }
                } else {
                    binding.courseRatingTextInput.error = null
                }
            }
        }
    }

    private fun parRatingListener() {
        binding.parEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                if (!isParValid()) {
                    val holeNumber = binding.numberOfHolesRadioGroup.checkedRadioButtonId
                    when (holeNumber) {
                        R.id.nine_holes_radio_button -> {
                            binding.parTextInput.error =
                                getString(R.string.parNineError)
                        }
                        R.id.eighteen_holes_radio_button -> {
                            binding.parTextInput.error =
                                getString(R.string.parEighteenError)
                        }
                        else -> binding.parTextInput.error = getString(R.string.notCheckedError)
                    }
                } else {
                    binding.parTextInput.error = null
                }
            }
        }
    }
}