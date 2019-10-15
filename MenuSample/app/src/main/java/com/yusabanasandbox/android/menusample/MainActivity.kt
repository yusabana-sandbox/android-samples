package com.yusabanasandbox.android.menusample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter

class MainActivity : AppCompatActivity() {

    private var _menuList: MutableList<MutableMap<String, Any>> = mutableListOf()
    private val FROM = arrayOf("name", "price")
    private val TO = intArrayOf(R.id.tvMenuName, R.id.tvMenuPrice)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var _curreyList = createCurreyList()
        var _teishokuList = createTeishokuList()

        _menuList.addAll(_curreyList)
        _menuList.addAll(_teishokuList)


        val lvMenu = findViewById<ListView>(R.id.lvMenu)

        val adapter = SimpleAdapter(applicationContext, _menuList, R.layout.row, FROM, TO)
        lvMenu.adapter = adapter

        lvMenu.onItemClickListener = ListItemClickListener()
    }

    private fun createTeishokuList(): MutableList<MutableMap<String, Any>> {
        val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()

        var menu =
            mutableMapOf("name" to "唐揚げ定食", "price" to 800, "desc" to "若鶏の唐揚げにサラダ、ご飯とお味噌汁がつきます")
        menuList.add(menu)

        menu =
            mutableMapOf("name" to "ハンバーグ定食", "price" to 850, "desd" to "手ごねハンバーグにサラダ、ご飯とお味噌汁がつきます")
        menuList.add(menu)

        menu =
            mutableMapOf("name" to "インドカレー定食", "price" to 950, "desd" to "インドカレーにサラダ、ご飯とお味噌汁がつきます")
        menuList.add(menu)

        return menuList
    }

    private fun createCurreyList(): MutableList<MutableMap<String, Any>> {
        val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()

        var menu =
            mutableMapOf("name" to "ビーフカレー", "price" to 520, "desc" to "特製スパイスを効かせた国産ビーフ100%のカレーです")
        menuList.add(menu)

        menu = mutableMapOf("name" to "ポークカレー", "price" to 420, "desc" to "特製スパイスを効かせた国産ポーク100%のカレーです")
        menuList.add(menu)

        return menuList
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            // タップされた行のデータを取得 SimpleAdapterはMutableMap型
            val item = parent.getItemAtPosition(position) as MutableMap<String, Any>

            val name = item["name"] as String
            val price = item["price"] as Int

            // インテント
            val intent = Intent(applicationContext, MenuThanksActivity::class.java)

            intent.putExtra("menuName", name)
            intent.putExtra("menuPrice", "${price}円")

            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // オプションメニュー用のxmlをインフレイト
        menuInflater.inflate(R.menu.menu_options_menu_list, menu)

        // 親クラスの同名メソッドを呼び出しその戻り値を返却
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 選択されたメニューIDのR値による処理の分岐
        when(item.itemId) {
            R.id.menuListOptionTeishoku ->
                _menuList = createTeishokuList()
            R.id.menuListOptionCurry ->
                _menuList = createCurreyList()
        }

        val lvMenu = findViewById<ListView>(R.id.lvMenu)

        val adapter = SimpleAdapter(applicationContext, _menuList, R.layout.row, FROM, TO)

        lvMenu.adapter = adapter

        return super.onOptionsItemSelected(item)
    }
}
