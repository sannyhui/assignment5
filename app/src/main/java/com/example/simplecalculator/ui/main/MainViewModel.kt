package com.example.simplecalculator.ui.main

import androidx.lifecycle.ViewModel
import com.example.simplecalculator.ui.logic.BracketHandling
import com.example.simplecalculator.ui.logic.FormulaToArray
import com.example.simplecalculator.ui.logic.FormulaValidation

/**
 * ViewModel with main process
 */

class MainViewModel : ViewModel() {

    //Declare class variable and 2d ArrayList
    private var formulaText = ""
    private var formulaSteps = "? to display help"
    var seekvalue = 0

    //Instance classes
    private val formulaValidation = FormulaValidation()
    private val formulaToArray = FormulaToArray()

    //This is a local function to display help when ? is entered.
    fun helpScreen () : Unit {
        formulaSteps = "Supported below features\n\n" +
                "+ - * / %\nE.g. 1+1 or (2*3)\n\n" +
                "(a/h) sin cos tan in degree\nE.g. sin30 or (acos30) or tanh(30) or asinh30\n\n" +
                "pi sqrt log10 ! ^ arbr\nE.g. sqrt 26 or log 23 or 5! or 3^4 or 16arbr2\n\n"
    }

    //This is the main program, entry point of the program logic
    fun mainProgram(formulaString: String, seekvalue: Int) {

        //Pass formula from MainFragment
        this.formulaText = formulaString
        this.seekvalue = seekvalue

        //Change formula to array
        val formulaByString = formulaToArray.changeToArray(formulaText)

        //Check error
        val errorDetected = formulaValidation.validateFormula(formulaByString)

        //If no error found, do the following code.
        if (errorDetected == 0) {

            //Clear initial value
            formulaSteps = ""

            //Instance BracketHandling class and run bracketCalculation method.
            val bracketHandling = BracketHandling(formulaByString, seekvalue)
            bracketHandling.bracketCalculation()

            //Get process log (Steps on calculation) from BracketHandling class
            formulaSteps += bracketHandling.returnFormulaSteps()

        } else {
            when (errorDetected) {
                //Display help screen
                8888 -> helpScreen()
                //Display bracket is missing
                in 100..199 -> formulaSteps = "Bracket(s) are missing!"
                //Display error message
                else -> formulaSteps = "Formula syntax is incorrect!"
            }
        }
    }

    //Return result to MainFragment.
    fun getResult(): String {
        return formulaSteps
    }
}