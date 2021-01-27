package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var buttons: Array<Array<Button>>
    lateinit var tv_score_p1: TextView
    lateinit var tv_score_p2: TextView
    lateinit var btn_reset: Button

    private var p1Turn: Boolean = true
    private var round: Int = 0;
    private var p1Point: Int = 0;
    private var p2Point: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_score_p1 = findViewById(R.id.tv_score_p1)
        tv_score_p2 = findViewById(R.id.tv_score_p2)
        btn_reset = findViewById(R.id.btn_reset)
        btn_reset.setOnClickListener(this)

        buttons = Array(3) { r -> Array(3) { c -> initButton(r, c) } }

    }

    private fun initButton(r: Int, c: Int): Button {
        val btn: Button = findViewById(resources.getIdentifier("btn$r$c", "id", packageName))
        btn.setOnClickListener {
            onTicTacClick(btn)
        }
        return btn
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_reset -> {
                clearBoard()
            }
        }
    }

    private fun onTicTacClick(btn: Button) {
        if (btn.text != "") return
        if (p1Turn) {
            btn.text = "X"
        } else {
            btn.text = "O"
        }
        round++

        if (checkForWin()) {
            if (p1Turn) win(1) else win(2)
        } else if (round == 9) {
            draw()
        } else {
            p1Turn = !p1Turn
        }
    }

    private fun checkForWin(): Boolean {
        val fields = Array(3) { r ->
            Array(3) { c ->
                buttons[r][c].text
            }
        }

        for (i in 0..2) {
            if ((fields[i][0] == fields[i][1]) &&
                (fields[i][0] == fields[i][2]) &&
                (fields[i][0] != "")
            ) return true
        }

        for (i in 0..2) {
            if (
                (fields[0][i] == fields[1][i]) &&
                (fields[0][i] == fields[2][i]) &&
                (fields[0][i] != "")
            ) return true
        }

        if (
            (fields[0][0] == fields[1][1]) &&
            (fields[0][0] == fields[2][2]) &&
            (fields[0][0] != "")
        ) return true

        if (
            (fields[0][2] == fields[1][1]) &&
            (fields[0][2] == fields[2][0]) &&
            (fields[0][2] != "")
        ) return true

        return false
    }

    private fun win(player: Int) {
        if (player == 1)
            p1Point++
        else
            p2Point++
        Toast.makeText(applicationContext, "Player $player Won!", Toast.LENGTH_SHORT).show()
        updateScore()
        disableButton()
        //clearBoard()
    }

    private fun draw() {
        Toast.makeText(
            applicationContext,
            "Match Draw!",
            Toast.LENGTH_SHORT
        ).show()
        disableButton()
        //clearBoard()
    }

    private fun clearBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
            }
        }
        enableButton()
        round = 0
        p1Turn = true
    }

    private fun enableButton(){
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = true
            }
        }
    }

    private fun disableButton(){
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    private fun updateScore() {
        tv_score_p1.text = "Player 1: $p1Point"
        tv_score_p2.text = "Player 2: $p2Point"
    }


}