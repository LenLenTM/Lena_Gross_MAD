package LenaGross.MAD.LD_1

import LenaGross.MAD.R
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.math.pow
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var userInput: EditText
    lateinit var generateButton: Button
    lateinit var guessButton: Button
    lateinit var log: TextView
    lateinit var number: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userInput = findViewById(R.id.userInput)
        generateButton = findViewById(R.id.generateButton)
        generateButton.setBackgroundColor(Color.BLUE)
        guessButton = findViewById(R.id.guessButton)
        guessButton.setBackgroundColor(Color.LTGRAY)
        guessButton.isEnabled = false
        log = findViewById(R.id.log)
        log.movementMethod = ScrollingMovementMethod()

        generateButton.setOnClickListener {
            number = generateNumber()
            log.text = ""
            userInput.text.clear()
            guessButton.isEnabled = true
            guessButton.setBackgroundColor(Color.BLUE)

            //log.text = listToNumber(number).toString()
        }

        guessButton.setOnClickListener{
            log.text = "${tryGuess()}\n${log.text}"
        }

        userInput.setOnKeyListener(View.OnKeyListener{x, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                log.text = "${tryGuess()}\n${log.text}"
                return@OnKeyListener true
            }
            false
        })

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    fun tryGuess(): String{

        if(userInput.text.toString().length != 4 || !userInput.text.toString().all {char -> char.isDigit()}){
            return "Enter a 4 character long number"
        }

        var guess: List<Int> = numberToList(userInput.text.toString().toInt())
        if (guess == number){
            guessButton.setBackgroundColor(Color.LTGRAY)
            guessButton.isEnabled = false
            return "Correct! Your number is ${listToNumber(number)}"
        }
        else {
            var correctDigit: Int = 0
            var correctPosition: Int = 0
            for(i in 0..3){
                if(number[i] == guess[i]){correctPosition++}
                for(j in 0..3){
                    if(number[i] == guess[j]){correctDigit++}
                }
            }
            return "User input: ${listToNumber(guess)}, Output: $correctDigit:$correctPosition"
        }
    }

    fun generateNumber(): List<Int> {
        var numbers = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        var output: Double = 0.0

        for (i in 0..3){
            var index = Random.nextInt(numbers.size)
            output += numbers[index] * 10.0.pow(i.toDouble())
            numbers.removeAt(index)
        }
        return numberToList(output.toInt())
    }


    fun numberToList(number: Int): List<Int>{
        var ourNumber = number
        var numberList = mutableListOf<Int>()
        for(i in 0 .. 3){
            numberList.add((ourNumber / 10.0.pow(3 - i)).toInt())
            ourNumber -= 10.0.pow(3 - i).toInt() * numberList[i]
        }
        return numberList
    }

    fun listToNumber(list : List<Int>): Int{
        var number: Int = 0
        for(i in 0 ..3){
            number += (list[i] * 10.0.pow(3 - i).toInt())
        }
        return number
    }
}

