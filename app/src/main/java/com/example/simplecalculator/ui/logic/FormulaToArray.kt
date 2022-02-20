package com.example.simplecalculator.ui.logic

import android.util.Log

/**
 * Divide strings into 2d ArrayList with types and strings.
 */

class FormulaToArray {

    fun changeToArray (formulaString: String) : ArrayList<ArrayList<String>> {
        //Declare variables
        val formulaByString = arrayListOf(arrayListOf("0","0"))
        formulaByString.clear()
        var j : Int = 0 //The position of the array.
        var numberString : String = "" //String to store number
        var trigonometricString : String = "" //String to store trigonometric
        var bracketString : String = "" //String to solve negative number issue

        //Local function to check if trigonometricString is exists, store it to array and empty it.
        fun storeTrigonometric() {
            if (trigonometricString != "") {
                if(trigonometricString == "pi") {
                    formulaByString.add(arrayListOf("Value", Math.PI.toString()))
                    //"Value", "3.14159265"
                } else if (trigonometricString == "arbr"){
                    formulaByString.add(arrayListOf("Operator", trigonometricString))
                } else {
                    formulaByString.add(arrayListOf("Trigonometric", trigonometricString))
                }
                trigonometricString = ""
            }
        }

        //Local function to check if numberString is exists, store number to array and empty it.
        fun storeNumber() {
            if (numberString != "") {
                formulaByString.add(arrayListOf("Value", numberString))
                numberString = ""
            }
        }

        //i is a character which iterate from string formulaString
        for (i in formulaString) {
            j += 1 //Array position starting from 1

            //if ? is the first character of the string, set it as operator type.
            if (i == '?' && j == 1) formulaByString.add(arrayListOf("Operator", i.toString()))

            //Store to numberString if character is 0~9 or decimal.
            if (i >= '0' && i <= '9' || i == '.')  {
                numberString += i.toString()
                storeTrigonometric()

                //Put value to array when the loop reach the end.
                if (j == formulaString.length) {
                    formulaByString.add(arrayListOf("Value", numberString))
                }

            //Process operator
            } else if (i == '+' || i == '-' || i == '*' || i == '/' || i == '^' || i == '%') {
                //Check if "-" is a negative sign.
                if ((j == 1 && i == '-') || (bracketString =="(" && i =='-' && numberString == "")) {
                    numberString += i.toString()
                    bracketString = "" //Empty flag.
                } else {
                    storeNumber()
                    storeTrigonometric()

                    //Store operator to array.
                    formulaByString.add(arrayListOf("Operator", i.toString()))
                }

            } else if (i == '!') {
                trigonometricString += i.toString()
                storeTrigonometric()
                storeNumber()
            //Process bracket
            } else if (i == '(' || i == ')') {
                storeNumber()
                storeTrigonometric()
                bracketString = i.toString()

                //Store bracket to array.
                formulaByString.add(arrayListOf("Bracket", i.toString()))

            //All other characters are classified as trigonometric.
            } else if (i != ' ') {
                trigonometricString += i.toString()

                if (trigonometricString == "arbr") {
                    storeNumber()
                    storeTrigonometric()
                }

                //Store trigonometric to array.
                if (j == formulaString.length) {
                    storeTrigonometric()
                }
            }
        }

        //Add bracket to first and last record of the array.
        formulaByString.add(0, arrayListOf("Bracket","("))
        formulaByString.add(arrayListOf("Bracket",")"))

        //Debug purpose
        var debug = ""
        for (i in formulaByString) {
            debug += i + " "
        }
        Log.i("Debug", debug)

        //Return array
        return formulaByString
    }
}