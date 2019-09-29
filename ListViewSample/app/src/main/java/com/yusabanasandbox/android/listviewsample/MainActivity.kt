package com.yusabanasandbox.android.listviewsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ListViewオブジェクトを取得
        val lvMenu = findViewById<ListView>(R.id.lvMenu)

        // lvMenu.setOnItemClickListener(ListItemClickListener()) のsetterを呼び出しているコードをkotlinでは以下のようにかける
        lvMenu.onItemClickListener = ListItemClickListener()
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            // タップされたものを取得
            val item = parent.getItemAtPosition(position) as String
            // こういう書き方もあり
            // val tvText = view as TextView
            // val item = tvText.text.toString()

            // トーストで表示する文字列を生成
            val show = "あなたが選んだ定食: ${item}"

            Toast.makeText(applicationContext, show, Toast.LENGTH_LONG).show()
        }
    }
}
