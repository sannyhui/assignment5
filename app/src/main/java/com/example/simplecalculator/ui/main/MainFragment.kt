package com.example.simplecalculator.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.simplecalculator.databinding.MainFragmentBinding

/**
 * Fragment control and start MainViewModel (See OnActivity Created)
 */

class MainFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    //Declare variable for seekbar
    var seekvalue = 1

    //Declare seekbar listener variable and interface method
    //Important: method must be implemented
    //(under MainActivity : FragmentActivity() in this project)
    var activityCallback: MainFragment.ToolbarListener? = null
    interface ToolbarListener {
        fun onButtonClick(positon: Int, text: String)
    }

    //Method for seekbar
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            activityCallback = context as ToolbarListener}
            catch (e: ClassCastException) {
            throw ClassCastException(context.toString() +
            "must implement ToolbarListener")
        }
    }

    //Companion object for Fragment
    companion object {
        fun newInstance()=MainFragment()
    }

    //Declare variables for Fragment
    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    //Method for Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Method for Fragment
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Starting point of the Fragment class
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.seekBar1.setOnSeekBarChangeListener(this)

        //Declare ViewModel = MainViewModel
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        //Display result in resultText textView
        binding.resultText.text = viewModel.getResult().toString()
        binding.resultText.movementMethod=ScrollingMovementMethod.getInstance()

        //On click listener for Calculate button
        binding.convertButton.setOnClickListener {

            //Make sure formula is not empty
            if (binding.formulaText.text.isNotEmpty()) {

                //On click listener of seekbar
                activityCallback?.onButtonClick(seekvalue, binding.resultText.text.toString())

                //Call mainProgram in MainViewModel class.
                viewModel.mainProgram(binding.formulaText.text.toString(),seekvalue)

                //Get result with getResult() and show here
                binding.resultText.text = viewModel.getResult()
            } else {
                binding.resultText.text = "No Formula"
            }

        }

        //On click listener for switch to change font size.
        binding.switch1.setOnClickListener {
            if(binding.switch1.isChecked) {
                binding.resultText.textSize = 24.0f
            } else {
                binding.resultText.textSize = 12.0f
            }
        }

    }

    //variable of the seekbar of seekbar
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        seekvalue = progress
    }

    //Implement abstract method of seekbar
    override fun onStartTrackingTouch(arg0: SeekBar) {
    }

    //Implement abstract method of seekbar
    override fun onStopTrackingTouch(arg0: SeekBar) {
    }

}