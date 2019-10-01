package com.yusabanasandbox.android.intentsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvMenu = findViewById<ListView>(R.id.lvMenu)

        val menulist: MutableList<MutableMap<String, String>> = mutableListOf()

        var menu = mutableMapOf("name" to "唐揚げ定食", "price" to "800円")
        menulist.add(menu)

        menu = mutableMapOf("name" to "ハンバーグ定食", "price" to "850円")
        menulist.add(menu)

        val from = arrayOf("name", "price")
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)

        // simpleadapterを使ってデータをビューに渡す
        val adapter = SimpleAdapter(applicationContext, menulist, android.R.layout.simple_list_item_2, from, to)
        lvMenu.adapter = adapter

        // 画面遷移するためのボタンのリスナー
        lvMenu.onItemClickListener = ListItemClickListener()
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            // タップされた行のデータを取得 SimpleAdapterはMutableMap型
            val item = parent.getItemAtPosition(position) as MutableMap<String, String>

            val name = item["name"]
            val price = item["price"]

            // インテント
            val intent = Intent(applicationContext, MenuThanksActivity::class.java)

            intent.putExtra("menuName", name)
            intent.putExtra("menuPrice", price)

            startActivity(intent)
        }
    }
}
