package com.yusabanasandbox.android.databasesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * 選択されたカクテルの主キーIDを表すフィールド
     */
    private var _cocktailId = -1

    /**
     * 選択されたカクテル名を表すフィールド
     */
    private var _cocktailName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvCocktail = findViewById<ListView>(R.id.lvCocktail)
        lvCocktail.onItemClickListener = ListItemClickListener()
    }

    /**
     * 保存ボタンをタップされた時の処理. 諸々クリアする
     */
    fun onSaveButtonClick(view: View) {
        val etNote = findViewById<EditText>(R.id.etNote)
        // 感想欄の入力を消去
        etNote.setText("")

        val tvCocktailName = findViewById<TextView>(R.id.tvCocktailName)
        // カクテル名を未選択に変更
        tvCocktailName.text = getString(R.string.tv_name)

        val btnSave = findViewById<Button>(R.id.btnSave)
        // 保存ボタンをタップできないようにする
        btnSave.isEnabled = false
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            // タップされた行番号をフィールドの主キーIDに代入
            _cocktailId = position
            // タップされた行のデータを取得、カクテル名となるのでフィールドに代入
            _cocktailName = parent.getItemAtPosition(position) as String

            val tvCocktailName = findViewById<TextView>(R.id.tvCocktailName)
            tvCocktailName.text = _cocktailName

            val btnSave = findViewById<Button>(R.id.btnSave)
            btnSave.isEnabled = true
        }
    }
}
