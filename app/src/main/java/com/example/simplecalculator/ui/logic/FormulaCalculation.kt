package com.example.simplecalculator.ui.logic

import kotlin.math.pow

/**
 * Main calculation
 */

class FormulaCalculation (val formulaByString: ArrayList<ArrayList<String>>, val seekvalue: Int){
    //Declare variable to store calculation steps
    var formulaSteps = ""

    //Declare number of decimal place
    val decimalPlace = "%." + seekvalue + "f"


    //Add method
    fun funcAdd (a: Double, b: Double): Double {
        val sum = a + b
        return sum
    }

    //Substract method
    fun funcSubstract (a: Double, b: Double): Double {
        val sum = a - b
        return sum
    }

    //Multiple method
    fun funcMultiple (a: Double, b: Double): Double {
        val sum = a * b
        return sum
    }

    //Divide method
    fun funcDivide (a: Double, b: Double): Double {
        val sum = a / b
        return sum
    }

    //Power method
    fun funcPower (a: Double, b: Double): Double {
        val sum = a.pow(b)
        return sum
    }

    //Remainder method
    fun funcRemainder (a: Double, b: Double): Double {
        val sum = a % b
        return sum
    }

    fun funcArbitraryRoot (a: Double, b: Double): Double {
        val sum = a.pow(1/b)
        return sum
    }

    //This is a local function to trim records after operator calculation.
    fun reduceFormula (indexNumber: Int,
                       changedValue: Double) : Boolean {

        val mergeValue = String.format(decimalPlace, changedValue).toDouble()
        formulaByString.removeAt(indexNumber + 1)
        formulaByString.removeAt(indexNumber)
        formulaByString.removeAt(indexNumber - 1)
        formulaByString.add(indexNumber - 1, arrayListOf("Value", mergeValue.toString()))
        return true
    }

    //This is a local function to record calculation steps by steps
    fun addFormulaSteps(formulaForAdding: ArrayList<ArrayList<String>>) : String {
        formulaSteps += "= "
        for (i in 0..formulaForAdding.size - 1) {
            formulaSteps = formulaSteps + formulaForAdding[i][1] + " "
        }
        formulaSteps +="\n\n"
        return formulaSteps
    }

    //This is a function to calculate trigonometric formula
    fun trigonometricCalculation () {

        //Declare local variables
        val formulaForChecking = arrayListOf(arrayListOf("0","0"))
        var mergeTrigonometric = 0.0000001
        var mergeTrigonometric1 = 0.0
        var calculateChecking = true

        //Calculate trigonometric values (Sin, Cos, Tan)
        while (calculateChecking) {
            formulaForChecking.clear()
            //Copy array formulaByString to formulaForChecking
            formulaForChecking.addAll(formulaByString)
            //flag to make sure all trigonometric are calculated. False is the exit flag.
            calculateChecking = false

            //Define trigonometric
            for (i in 0..formulaForChecking.size - 2) {
                if (formulaForChecking[i][0] == "Trigonometric") {
                    when (formulaForChecking[i][1]) {
                        "deg" -> mergeTrigonometric =
                            Math.toDegrees(formulaForChecking[i + 1][1].toDouble())
//                            kotlin.math.sin(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "rad" -> mergeTrigonometric =
                            Math.toRadians(formulaForChecking[i + 1][1].toDouble())
//                            kotlin.math.sin(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "sin" -> mergeTrigonometric =
                            kotlin.math.sin(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "cos" -> mergeTrigonometric =
                            kotlin.math.cos(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "tan" -> mergeTrigonometric =
                            kotlin.math.tan(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "asin" -> mergeTrigonometric =
                            kotlin.math.asin(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "acos" -> mergeTrigonometric =
                            kotlin.math.acos(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "atan" -> mergeTrigonometric =
                            kotlin.math.atan(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "sinh" -> mergeTrigonometric =
                            kotlin.math.sinh(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "cosh" -> mergeTrigonometric =
                            kotlin.math.cosh(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "tanh" -> mergeTrigonometric =
                            kotlin.math.tanh(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "asinh" -> mergeTrigonometric =
                            kotlin.math.asinh(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "acosh" -> mergeTrigonometric =
                            kotlin.math.acosh(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "atanh" -> mergeTrigonometric =
                            kotlin.math.atanh(Math.toRadians(formulaForChecking[i + 1][1].toDouble()))
                        "sqrt" -> mergeTrigonometric =
                            kotlin.math.sqrt(formulaForChecking[i + 1][1].toDouble())
                        "log" -> mergeTrigonometric =
                            kotlin.math.log(formulaForChecking[i + 1][1].toDouble(), 10.0)
                        "!" ->
                            {
                                var total: Double = 1.0
                                for (j in 1..formulaForChecking[i + 1][1].toInt()) {
                                    total *= j
                                }
                                mergeTrigonometric = total
                            }
                    }
                    //Trim decimal place
                    mergeTrigonometric1 = String.format(decimalPlace, mergeTrigonometric).toDouble()

                    //Trim formulaByString after calculation.
                    formulaByString.removeAt(i + 1)
                    formulaByString.removeAt(i)
                    formulaByString.add(i, arrayListOf("Value", mergeTrigonometric1.toString()))

                    //if Trigonometric calculation is found, flag turns true and need to loop again.
                    calculateChecking = true
                    break
                }
            }
        }
    }

    // his is a function to calculate using function reference
    fun mainCalculation(operatorString: String, myfunc: (Double, Double) -> Double) {

        //Declare variables
        val formulaForChecking = arrayListOf(arrayListOf("0","0"))
        var calculateChecking = true
        while (calculateChecking) {
            calculateChecking=false
            formulaForChecking.clear()
            //Copy array formulaByString to formulaForChecking
            formulaForChecking.addAll(formulaByString)
            for (i in 0..formulaForChecking.size - 2) {
                if (formulaForChecking[i][1] == operatorString) {
                    //Pass values to calculation methods using high order function
                    val calculateFirst=myfunc(formulaForChecking[i - 1][1].toDouble(),
                        formulaForChecking[i + 1][1].toDouble())

                    //Trim formulaByString after calculation
                    calculateChecking = reduceFormula(i, calculateFirst)
                    break
                }
            }
        }
    }

    //Calculate by operator
    fun operatorsCalculation(): String {
        //Calculate in below priority
        mainCalculation("^", ::funcPower)
        mainCalculation("%", ::funcRemainder)
        mainCalculation("*", ::funcMultiple)
        mainCalculation("/", ::funcDivide)
        mainCalculation("+", ::funcAdd)
        mainCalculation("-", ::funcSubstract)
        mainCalculation("arbr", ::funcArbitraryRoot)
        return formulaSteps
    }

    //Return calculation steps to View Model
    fun returnValue(): String {
        return formulaByString[0][1]
    }
}