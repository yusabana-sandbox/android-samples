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

    val _helper = DatabaseHelper(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvCocktail = findViewById<ListView>(R.id.lvCocktail)
        lvCocktail.onItemClickListener = ListItemClickListener()
    }

    override fun onDestroy() {
        _helper.close()
        super.onDestroy()
    }

    /**
     * 保存ボタンをタップされた時の処理. 諸々クリアする
     */
    fun onSaveButtonClick(view: View) {
        val etNote = findViewById<EditText>(R.id.etNote)

        val note = etNote.text.toString()

        // データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        val db = _helper.writableDatabase

        // まず、リストで選択されたカクテルのメモデータを削除、その後インサートを行う
        // 削除用のSQL文字列を用意
        val sqlDelete = "DELETE FROM cocktailmemos WHERE _id = ?"
        var stmt = db.compileStatement(sqlDelete)
        stmt.bindLong(1, _cocktailId.toLong())
        stmt.executeUpdateDelete()

        // インサート用のSQL文字列の用意
        val sqlInsert = "INSERT INTO cocktailmemos (_id, name, note) VALUES (?, ?, ?)"
        stmt = db.compileStatement(sqlInsert)
        stmt.bindLong(1, _cocktailId.toLong())
        stmt.bindString(2, _cocktailName)
        stmt.bindString(3, note)
        stmt.executeInsert()

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

            val db = _helper.writableDatabase
            val sql = "SELECT * FROM cocktailmemos WHERE _id = ${_cocktailId}"
            val cursor = db.rawQuery(sql, null)

            var note = ""
            while(cursor.moveToNext()) {
                val idxNote = cursor.getColumnIndex("note")
                note = cursor.getString(idxNote)
            }

            val etNote = findViewById<EditText>(R.id.etNote)
            etNote.setText(note)
        }
    }
}
