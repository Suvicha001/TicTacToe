package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonsInRow: ArrayList<Button>
    private lateinit var aRow: ArrayList<ArrayList<Button>>
    private lateinit var tv_size: TextView
    private lateinit var tv_score: TextView
    private lateinit var bt_decrease: Button
    private lateinit var bt_increase: Button
    private lateinit var btn_reset: Button

    lateinit var linearLayout: LinearLayout

    private var p1Turn: Boolean = true
    private var round: Int = 0;
    private var lenGrid: Int = 3;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayout = findViewById(R.id.board)
        tv_size = findViewById(R.id.tv_size)
        tv_score = findViewById(R.id.tv_score)
        bt_decrease = findViewById(R.id.bt_decreasesize)
        bt_decrease.setOnClickListener(this)
        bt_increase = findViewById(R.id.bt_increasesize)
        bt_increase.setOnClickListener(this)
        btn_reset = findViewById(R.id.btn_reset)
        btn_reset.setOnClickListener(this)

        tv_size.text = "$lenGrid x $lenGrid"

        createButton(lenGrid)
    }

    private fun createButton(len: Int) {

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val bt = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        bt.weight = 1F
        aRow = ArrayList(len)
        var rowloop = 0
        while (rowloop < len) {
            rowloop += 1
            var buttonloop = 0
            val rowLinearLayout = LinearLayout(this)
            rowLinearLayout.layoutParams = lp
            linearLayout.addView(rowLinearLayout)
            buttonsInRow = ArrayList(len)
            while (buttonloop < len) {
                buttonloop += 1
                val button = Button(this)
                button.layoutParams = bt
                button.setOnClickListener {
                    onTicTacClick(button)
                }
                rowLinearLayout.addView(button)
                buttonsInRow.add(button)
            }
            aRow.add(buttonsInRow)
        }
    }

    private fun clearButton(){
        linearLayout.removeAllViews()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_reset -> {
                clearBoard()
            }

            R.id.bt_decreasesize -> {
                clearBoard()
                lenGrid--
                tv_size.text = "$lenGrid x $lenGrid"
                clearButton()
                createButton(lenGrid)
            }

            R.id.bt_increasesize -> {
                clearBoard()
                lenGrid++;
                tv_size.text = "$lenGrid x $lenGrid"
                clearButton()
                createButton(lenGrid)
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
        } else if (round == lenGrid*lenGrid) {
            draw()
        } else {
            p1Turn = !p1Turn
        }
    }

    private fun checkForWin(): Boolean {
        val fields = Array(lenGrid) { r ->
            Array(lenGrid) { c ->
                aRow[r][c].text
            }
        }

        for (i in fields.indices) {
            for (j in fields[i].indices) {
                if(fields[i][0] != fields[i][j] || fields[i][0] == ""){
                    break
                }
                if(j == fields[i].size-1)
                    return true
            }
        }

        for (i in 0 until lenGrid) {
            for (j in 0 until lenGrid) {
                if(fields[i][0] != fields[j][i] || fields[i][0] == ""){
                    break
                }
                if(j == lenGrid-1)
                    return true
            }
        }

        for (i in 0 until lenGrid) {
            if(fields[0][0] != fields[i][i] || fields[0][0] == ""){
                return false
            }
            if(i == lenGrid-1)
                return true
        }

        var j: Int = lenGrid-1
        for (i in 0 until lenGrid) {
            if(fields[0][4] != fields[i][j] || fields[0][4] == ""){
                return false
            }
            if(i == lenGrid-1)
                return true
            j--
        }

        return false
    }


    private fun win(player: Int) {
        Toast.makeText(applicationContext, "Player $player Won!", Toast.LENGTH_SHORT).show()
        tv_score.text = "Player $player: Won!"
        disableButton()
    }

    private fun draw() {
        Toast.makeText(
            applicationContext,
            "Match Draw!",
            Toast.LENGTH_SHORT
        ).show()
        disableButton()
    }

    private fun clearBoard() {
        for (i in 0 until lenGrid) {
            for (j in 0 until lenGrid) {
                aRow[i][j].text = ""
            }
        }
        tv_score.text = ""
        enableButton()
        round = 0
        p1Turn = true
    }

    private fun enableButton() {
        for (i in 0 until lenGrid) {
            for (j in 0 until lenGrid) {
                aRow[i][j].isEnabled = true
            }
        }
    }

    private fun disableButton() {
        for (i in 0 until lenGrid) {
            for (j in 0 until lenGrid) {
                aRow[i][j].isEnabled = false
            }
        }
    }


}