package com.yusabanasandbox.android.asyncsample

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 画面部品ListViewを取得
        val lvCityList = findViewById<ListView>(R.id.lvCityList)

        // SimpleAdapterで利用するMutableListオブジェクト
        val cityList = mutableListOf<MutableMap<String, String>>()

        cityList.add(mutableMapOf("name" to "大阪", "id" to "270000"))
        cityList.add(mutableMapOf("name" to "神戸", "id" to "280010"))
        cityList.add(mutableMapOf("name" to "豊岡", "id" to "280020"))
        cityList.add(mutableMapOf("name" to "京都", "id" to "260010"))
        cityList.add(mutableMapOf("name" to "舞鶴", "id" to "260020"))
        cityList.add(mutableMapOf("name" to "那覇", "id" to "471010"))
        cityList.add(mutableMapOf("name" to "名護", "id" to "471020"))

        val from = arrayOf("name")
        val to = intArrayOf(android.R.id.text1)

        val adapter = SimpleAdapter(
            applicationContext,
            cityList,
            android.R.layout.simple_list_item_1,
            from,
            to
        )

        lvCityList.adapter = adapter
        lvCityList.onItemClickListener = ListItemClickListener()

    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            // ListViewでタップされた行の都市名と都市IDを取得
            val item = parent.getItemAtPosition(position) as Map<String, String>
            val cityName = item["name"]
            val cityId = item["id"]

            val tvCityName = findViewById<TextView>(R.id.tvCityName)
            tvCityName.setText(cityName + "の天気: ")

            val reciever = WeatherInfoReciever()
            reciever.execute(cityId)
        }

    }

    private inner class WeatherInfoReciever() : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String): String {
            // 可変長引数一つ目 都市IDを取得
            val id = params[0]

            val urlStr = "http://weather.livedoor.com/forecast/webservice/json/v1?city=${id}"

            val url = URL(urlStr)
            val con = url.openConnection() as HttpURLConnection // キャストする
            con.requestMethod = "GET"
            con.connect()

            Log.i("Async-LOG-Status", con.responseCode.toString())

            // HttpURLConnectionオブジェクトからレスポンスデータを取得。天気情報が格納されている
            val stream = con.inputStream
            val result = is2String(stream)

            con.disconnect()
            stream.close()

            return result
        }

        private fun is2String(stream: InputStream): String {
            val sb = StringBuilder()
            val reader = BufferedReader(InputStreamReader(stream, "UTF-8"))
            var line = reader.readLine()
            while(line != null) {
                sb.append(line)
                line = reader.readLine()
            }
            reader.close()
            return sb.toString()
        }

        override fun onPostExecute(result: String) {
            Log.i("Async-LOG", result)

            val rootJSON = JSONObject(result)

            // テスト的に都市名を取得
            val locationJSON = rootJSON.getJSONObject("location")
            Log.i("Async-LOG-Cityname", locationJSON.getString("city"))

            val descriptionJSON = rootJSON.getJSONObject("description")
            val desc = descriptionJSON.getString("text")
            val forecasts = rootJSON.getJSONArray("forecasts")
            val forecastNow = forecasts.getJSONObject(0)
            val telop = forecastNow.getString("telop")

            // 画面に描画
            val tvWeatherTelop = findViewById<TextView>(R.id.tvWeatherTelop)
            val tvWeatherDesc = findViewById<TextView>(R.id.tvWeatherDesc)
            tvWeatherTelop.text = telop
            tvWeatherDesc.text = desc
        }

    }
}
