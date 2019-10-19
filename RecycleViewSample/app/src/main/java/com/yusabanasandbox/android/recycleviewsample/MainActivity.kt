package com.yusabanasandbox.android.recycleviewsample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ツールバー関連
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setLogo(R.mipmap.ic_launcher)
        setSupportActionBar(toolbar)
        val toolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toobarLayout)
        toolbarLayout.title = getString(R.string.toolbar_title)
        toolbarLayout.setExpandedTitleColor(Color.WHITE)
        toolbarLayout.setCollapsedTitleTextColor(Color.LTGRAY)

        // RecyclerViewを取得
        val lvMenu = findViewById<RecyclerView>(R.id.lvMenu)

        // RecyclerViewにレイアウトマネージャーとしてLinerLayoutManagerを設定
        lvMenu.layoutManager = LinearLayoutManager(applicationContext)

        // 定食メニューリストデータを生成
        val menuList = createTeishokuList()
        // アダプタオブジェクトを生成
        val adapter = RecyclerListAdapter(menuList)
        // RecyclerViewにアダプタオブジェクトを設定
        lvMenu.adapter = adapter
    }

    private fun createTeishokuList(): MutableList<MutableMap<String, Any>> {
        val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()

        menuList.add(
            mutableMapOf(
                "name" to "唐揚げ定食",
                "price" to 800,
                "desc" to "若鶏の唐揚げにサラダ、ご飯とお味噌汁がつきます"
            )
        )
        menuList.add(
            mutableMapOf(
                "name" to "ハンバーグ定食",
                "price" to 850,
                "desc" to "手ごねハンバーグにサラダ、ご飯とお味噌汁がつきます"
            )
        )
        menuList.add(
            mutableMapOf(
                "name" to "インドカレー定食",
                "price" to 950,
                "desc" to "インドカレーにサラダ、ご飯とお味噌汁がつきます"
            )
        )
        menuList.add(
            mutableMapOf(
                "name" to "生姜焼き",
                "price" to 950,
                "desc" to "生姜焼きにサラダ、ご飯とお味噌汁がつきます"
            )
        )
        menuList.add(mutableMapOf("name" to "焼肉", "price" to 950, "desc" to "焼肉にサラダ、ご飯とお味噌汁がつきます"))

        return menuList
    }

    private inner class RecyclerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // リスト1行分中でメニュー名を表示する画面部品
        var tvMenuName: TextView
        // リスト1行分中で金額を表示する画面部品
        var tvMenuPrice: TextView

        init {
            // 引数で渡されたリスト一行分の画面部品の表示に使われるTextViewを取得
            tvMenuName = itemView.findViewById(R.id.tvMenuName)
            tvMenuPrice = itemView.findViewById(R.id.tvMenuPrice)
        }

    }

    private inner class RecyclerListAdapter(private val _listData: MutableList<MutableMap<String, Any>>) :
        RecyclerView.Adapter<RecyclerListViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerListViewHolder {
            // レイアウトインフレータを取得
            val inflater = LayoutInflater.from(applicationContext)

            // row.xmlをインフレートし、1行分の画面部品とする
            val view = inflater.inflate(R.layout.row, parent, false)

            // ビューホルダオブジェクト生成して、returnする
            return RecyclerListViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerListViewHolder, position: Int) {
            // リストデータから該当1行分のデータを取得
            val item = _listData[position]

            // メニュー名文字列を取得
            val menuName = item["name"] as String

            // メニュー金額を取得
            val menuPrice = item["price"] as Int
            // 表示用に金額を文字列に変換
            val menuPriceStr = menuPrice.toString()

            // メニュー名と金額をビューホルダ中のTextViewに設定
            holder.tvMenuName.text = menuName
            holder.tvMenuPrice.text = menuPriceStr
        }

        override fun getItemCount(): Int {
            // リストデータ中の件数をリターン
            return _listData.size
        }
    }
}
