package com.yusabanasandbox.android.practice1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var _list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvTags = findViewById<ListView>(R.id.lvTags)

        val adapter =
            ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, _list)

        lvTags.adapter = adapter

        val btScan = findViewById<ToggleButton>(R.id.btScan)

        btScan.setOnCheckedChangeListener { _, isChecked ->
            // 棚番focusを外す、キーボード閉じる
            val tfShelf = findViewById<TextInputLayout>(R.id.tfShelf)
            tfShelf.clearFocus()
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(
                currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

            if (isChecked) {
                // クリックされた時にONになった時
                Log.i("UG:Checked", _list.size.toString())

                findViewById<Button>(R.id.mbtCancel).isEnabled = false
                findViewById<Button>(R.id.mbtSend).isEnabled = false
            } else {
                Log.i("UG:No-Checked", _list.size.toString())
                // クリックされた時にOFFになった時

                // データの追加はAdapterを介して行う
                adapter.add("A1234 001")
                adapter.add("A1234 002")
                adapter.add("A1234 003")
                adapter.add("A1234 004")
                adapter.add("A1234 005")
                adapter.add("A1234 006")
                adapter.add("A1234 007")
                adapter.add("A1234 008")
                adapter.add("A1234 009")
                adapter.add("A1234 010")
                adapter.add("A1234 011")
                adapter.add("A1234 012")

//                以下のように要素自体のデータを追加してしまった場合は notifyDataSetChanged() を実行する必要がある
//                _list.add("A1234 001")
//                _list.add("B1234 002")
//                adapter.notifyDataSetChanged()

                btScan.isEnabled =  _list.size == 0

                // ここで_listにひとつでもデータがあったらボタンを出す
                if (_list.size > 0) {
//                    Snackbar.make(lvTags, "スナックバーでなにする", Snackbar.LENGTH_SHORT).show()
                    findViewById<Button>(R.id.mbtCancel).isEnabled = true
                    findViewById<Button>(R.id.mbtSend).isEnabled = true
                }
            }
        }
    }

    fun onClearClick(view: View) {
        if (_list.size > 0) {
            val lvTags = findViewById<ListView>(R.id.lvTags)
            val adapter = lvTags.adapter as ArrayAdapter<String>
            adapter.clear()
        }

        findViewById<ToggleButton>(R.id.btScan).isEnabled = true
        findViewById<Button>(R.id.mbtCancel).isEnabled = false
        findViewById<Button>(R.id.mbtSend).isEnabled = false
    }

    fun onSendClick(view: View) {
        if (_list.size > 0) {
            val lvTags = findViewById<ListView>(R.id.lvTags)
            val adapter = lvTags.adapter as ArrayAdapter<String>
            adapter.clear()
        }

        findViewById<ToggleButton>(R.id.btScan).isEnabled = true
        findViewById<Button>(R.id.mbtCancel).isEnabled = false
        findViewById<Button>(R.id.mbtSend).isEnabled = false
    }
}
