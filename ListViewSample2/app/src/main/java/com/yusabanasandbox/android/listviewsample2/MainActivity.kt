package com.yusabanasandbox.android.listviewsample2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvMenu = findViewById<ListView>(R.id.lvMenu)

        var menuList = mutableListOf(
            "唐揚げ定食",
            "ハンバーグ定食",
            "生姜焼き定食",
            "ステーキ定食",
            "野菜炒め定食",
            "とんかつ定食",
            "ミンチかつ定食",
            "チキンカツ定食",
            "コロッケ定食",
            "焼き魚定食",
            "焼肉定食"
        )

        // android.R はandroid SDK で用意されたResouceを指定する場合に利用
        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, menuList)

        lvMenu.adapter = adapter

        lvMenu.onItemClickListener = ListItemClickListener()
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val dialogFragment = OrderConfirmDialogFragment()

            dialogFragment.show(supportFragmentManager, "OrderConfirmDialogFragment")
        }
    }
}
