package com.example.simplecalculator.ui.logic

/**
 * Class to validate formula.
 */

class FormulaValidation {

    //Check whether content in the specific type is correct or not
    fun checkValidation (formulaType: String, formulaString: String) : Int {
        var error = 0

        val trigonometricChecking: Array<String> = arrayOf("sin", "cos", "tan", "log", "!",
            "arbr", "rad", "deg", "asin", "acos", "atan", "sinh", "cosh", "tanh", "asinh",
            "acosh", "atanh")

        //Logic (error = 0 means no further checking is required.)
        when (formulaType) {
            "Value" -> if (formulaString.toDoubleOrNull() == null) error = 1
            "Operator" -> error = 0
            "Trigonometric" -> {
                var errorFound = true
                for (i in trigonometricChecking) {
                    if (formulaString == i) errorFound = false
                }
                if (errorFound) error = 1
            }
//                if (formulaString != "sin" && formulaString != "cos" &&
//                formulaString != "tan" && formulaString != "sqrt" &&
//                formulaString != "log" && formulaString != "!") error = 1
            "Bracket" -> error = 0
        }
        return error
    }

    //Validate formula
    fun validateFormula (formula: ArrayList<ArrayList<String>>): Int {

        //Declare local variables
        var errorDetected = 0 //How many error is detected.
        var openBracketCount : Int = 0 //Number of open brackets (0 as default)
        var closeBracketCount : Int = 1 //Number of close brackets (1 as default)

        //Check each component in array
        for (i in 0..formula.size - 1) {
            val errorFound = checkValidation(formula[i][0], formula[i][1])
            errorDetected += errorFound
        }

        //Check the component in loop and the component after it.
        //e.g. Formula 1 + * 20 is considered as invalid.
        for (i in 0..formula.size - 2) {
            when (formula[i][0]) {
                //Only operator and close bracket are allowed after value.
                "Value" -> if (formula[i + 1][0] != "Operator" &&
                    formula[i + 1][1] != ")") errorDetected++
                //Operator and close bracket are not allowed after operator.
                "Operator" -> if (formula[i + 1][0] == "Operator" ||
                   formula[i + 1][1] == ")" ) errorDetected++
                //Only value and bracket are allowed after trigonometric
                "Trigonometric" -> if (formula[i + 1][0] != "Value" &&
                    formula[i + 1][1] != "(") errorDetected++
                //Calculate if brackets are paired.
                "Bracket" -> {
                    if (formula[i][1] == ")") {
                        if (formula[i+1][0] != "Operator" && formula[i+1][1] != ")") {
                            errorDetected++
                        }
                    }

                    if (formula[i][1] == "(") {
                        if (formula[i+1][1] ==")") {
                            errorDetected ++
                        }
                        openBracketCount ++
                    } else {
                        closeBracketCount ++
                    }
                }
            }
        }

        //Error if number of open bracket is not equal to number of close bracket
        if (openBracketCount != closeBracketCount) errorDetected += 100

        //Show help if first character is ?
        if (formula[1][1].first() == '?' ) {
            errorDetected = 8888 //Show help
        }

        return errorDetected
    }
}