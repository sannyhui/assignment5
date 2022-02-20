package com.example.simplecalculator.ui.logic

/**
 * Process brackets in order, divide them and call functions in FormulaCalculation
 */

class BracketHandling (val stringInsideBracket: ArrayList<ArrayList<String>>, val seekvalue: Int){

    //Declare class variables
    var formulaSteps = ""

    //Method to get maximum bracket level
    fun bracketCount() : Int {

        //Declare local variables
        var bracketLevel = 0 //Current bracket level
        var bracketMaxLevel = 0 //Count maximum bracket level

        //Check maximum bracket level and return value.
        for (i in 0..stringInsideBracket.size - 1) {
            if (stringInsideBracket[i][0] == "Bracket") {
                if (stringInsideBracket[i][1] == "(") {
                    bracketLevel ++
                    if (bracketLevel > bracketMaxLevel) {
                        bracketMaxLevel = bracketLevel
                    }
                } else {
                    bracketLevel --
                }
            }
        }
        return bracketMaxLevel
    }

    //Method to calculate formula inside brackets
    fun bracketCalculation() : ArrayList<ArrayList<String>> {

        //Declare variables
        var bracketCount = 0 //Current numbers of open bracket
        var bracketStartPoint = 0 //Bracket starting position
        var bracketEndPoint = 0 //Bracket ending position
        var updateStart = true //Flag to determine bracketStartPoint is updated.
        var updateEnd = true //Flag to determine bracketEndPoint is updated.

        //Array to store formula in the inner bracket
        val formula = arrayListOf(arrayListOf("0","0"))
        //Declare variables of class FormulaCalculation
        var formulaCalculation: FormulaCalculation

        //Get bracketMaxLevel from method bracketCount()
        var bracketMaxLevel = bracketCount()

        //Loop end when all brackets are calculated.
        while (bracketMaxLevel > 0) {
            for (i in 0.. stringInsideBracket.size - 1) {
                //Count brackets
                if (stringInsideBracket[i][0] == "Bracket") {
                    if (stringInsideBracket[i][1] == "(") {
                        bracketCount ++
                    } else {
                        bracketCount --
                    }
                }
                //Update bracketStartPoint when hit the number of max level for the first time.
                if (updateStart && bracketCount == bracketMaxLevel) {
                    bracketStartPoint = i
                    updateStart = false
                }
                //Update bracketEndPoint when bracketCount < bracketMaxLevel for the first time.
                if (updateEnd && !updateStart && bracketCount == bracketMaxLevel - 1) {
                    bracketEndPoint = i
                    updateEnd = false
                }
            }

            //Clear formula array and put array inside the inner bracket into it.
            formula.clear()
            for (i in (bracketStartPoint + 1)..(bracketEndPoint) - 1) {
                formula.add(stringInsideBracket[i])
            }

            //Instance formulaCalculation.
            formulaCalculation = FormulaCalculation(formula, seekvalue)
            //Call trigonometricCalculation method in FormulaCalculation class.
            formulaCalculation.trigonometricCalculation()
            //Call operatorsCalculation method in FormulaCalculation class.
            formulaCalculation.operatorsCalculation()

            //Remove formula in the array with one bracket (Remain one for update value)
            for (j in bracketStartPoint..bracketEndPoint-1) {
                stringInsideBracket.removeAt(bracketStartPoint)
            }

            //Call returnValue to retrieve value and update array.
            stringInsideBracket[bracketStartPoint][0] = "Value"
            stringInsideBracket[bracketStartPoint][1] = formulaCalculation.returnValue()

            //Call addFormulaSteps to get the updated calculation steps with proper format
            formulaSteps += formulaCalculation.addFormulaSteps(stringInsideBracket)

            //Reset variables
            bracketCount = 0
            updateStart = true
            updateEnd = true

            //Update latest bracketMaxLevel
            bracketMaxLevel = bracketCount()
        }

        //Return result to MainViewModel
        return stringInsideBracket
    }

    //Method to return calculation steps
    fun returnFormulaSteps () : String {
        return formulaSteps
    }
}