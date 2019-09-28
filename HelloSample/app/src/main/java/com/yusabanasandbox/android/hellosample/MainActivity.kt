package com.yusabanasandbox.android.hellosample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 表示ボタンであるButtonオブジェクトを取得
        val btClick = findViewById<Button>(R.id.btClick)
        val btClear = findViewById<Button>(R.id.btClear)

        // リスなクラスのインスタンス生成
        val listener = HelloListener()

        btClick.setOnClickListener(listener)
        btClear.setOnClickListener(listener)
    }

    // ボタンをクリックしたときノリスなクラス
    private inner class HelloListener : View.OnClickListener {
        override fun onClick(view: View) {
            val input = findViewById<EditText>(R.id.etName)
            val output = findViewById<TextView>(R.id.tvOutput)

            when (view.id) {
                R.id.btClick -> {
                    val inputStr = input.text.toString()
                    output.text = inputStr + "さん、こんにちは！！"
                }
                R.id.btClear -> {
                    input.setText("")
                    output.text = ""

                }
            }
        }
    }
}
