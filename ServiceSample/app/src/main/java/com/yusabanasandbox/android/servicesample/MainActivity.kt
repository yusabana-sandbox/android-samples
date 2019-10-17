package com.yusabanasandbox.android.servicesample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fromNotification = intent.getBooleanExtra("fromNotification", false)

        if (fromNotification) {
            // 再生ボタンをタップ不可に停止ボタンをタップ可に変更
            val btPlay = findViewById<Button>(R.id.btPlay)
            val btStop = findViewById<Button>(R.id.btStop)

            btPlay.isEnabled = false
            btStop.isEnabled = true
        }
    }

    fun onPlayButtonClick(view: View) {
        // インテントオブジェクト作成
        val intent = Intent(applicationContext, SoundManageService::class.java)

        startService(intent)

        // 再生ボタンをタップ不可に停止ボタンをタップ可に変更
        val btPlay = findViewById<Button>(R.id.btPlay)
        val btStop = findViewById<Button>(R.id.btStop)

        btPlay.isEnabled = false
        btStop.isEnabled = true
    }

    fun onStopButtonClick(view: View) {
        val intent = Intent(applicationContext, SoundManageService::class.java)

        stopService(intent)

        // 再生ボタンをタップ可能に、停止ボタンをタップ不可に
        val btPlay = findViewById<Button>(R.id.btPlay)
        val btStop = findViewById<Button>(R.id.btStop)

        btPlay.isEnabled = true
        btStop.isEnabled = false
    }
}
