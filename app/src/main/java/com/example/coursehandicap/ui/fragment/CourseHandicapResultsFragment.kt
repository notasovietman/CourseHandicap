package com.example.coursehandicap.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.coursehandicap.R
import com.example.coursehandicap.databinding.FragmentCourseHandicapResultsBinding

class CourseHandicapResultsFragment : Fragment(R.layout.fragment_course_handicap_results) {

    private lateinit var binding: FragmentCourseHandicapResultsBinding
    private val args: CourseHandicapResultsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCourseHandicapResultsBinding.bind(view)
        val unroundedResult = args.unroundedResult
        val formatted = String.format("%.3f", unroundedResult)

        binding.courseHandicapResultRounded.text = args.roundedResult.toString()
        binding.courseHandicapResultUnrounded.text = formatted
    }
}