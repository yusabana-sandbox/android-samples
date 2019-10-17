package com.yusabanasandbox.android.servicesample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationCompat

class SoundManageService : Service() {
    private var _player: MediaPlayer? = null

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "soundmanagerservice_notification_channel"
    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        // メディアプレイヤーを作成
        _player = MediaPlayer()

        val id = NOTIFICATION_CHANNEL_ID
        val name = getString(R.string.notification_channel_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // 音声ファイルのURI文字列からUriオブジェクトを作成
        val mediaFileUriStr = "android.resource://${packageName}/${R.raw.rain}"
        val mediaFileUri = Uri.parse(mediaFileUriStr)

        // メディアプレイヤーに音声ファイルを指定
        _player?.setDataSource(applicationContext, mediaFileUri)
        // 非同期でのメディア再生準備が完了した際のリスナを設定
        _player?.setOnPreparedListener(PlayerPreparedListener())
        // メディア再生が終了した際のリスナを設定
        _player?.setOnCompletionListener(PlayerCompletionListener())
        // 非同期でメディア再生を準備
        _player?.prepareAsync()

        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        _player?.let {
            if (it.isPlaying) {
                it.stop()
            }
            // プレイヤーを解放
            it.release()
            _player = null
        }
    }

    /**
     * メディア再生準備が完了時のリスナクラス
     */
    private inner class PlayerPreparedListener : MediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: MediaPlayer) {
            mp.start()

            // Notificationを作成するBuilderクラス
            val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            // Notificationアイコン設定
            builder.setSmallIcon(android.R.drawable.ic_dialog_info)
            // Notificationタイトル設定
            builder.setContentTitle(getString(R.string.msg_notification_title_start))
            // Notification本文設定
            builder.setContentText(getString(R.string.msg_notification_text_start))

            // 起動先Activityクラスを指定したIntentオブジェクトを作成
            val intent = Intent(applicationContext, MainActivity::class.java)
            // 起動先Activityに引き継ぎデータを格納
            intent.putExtra("fromNotification", true)

            // PendingIntentオブジェクトを取得
            val stopServiceIntent = PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            // PendingIntentオブジェクトをビルダーに設定
            builder.setContentIntent(stopServiceIntent)
            // タップされた通知メッセージを自動的に消去するように設定
            builder.setAutoCancel(true)

            // BuilderからNotificationオブジェクトをさ空晴
            val notification = builder.build()
            // NotificationManagerを取得
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // 通知
            manager.notify(1, notification)
        }
    }

    /**
     * メディア再生が終了した時のリスナクラス
     */
    private inner class PlayerCompletionListener : MediaPlayer.OnCompletionListener {
        override fun onCompletion(mp: MediaPlayer) {
            // Notificationを作成するBuilderクラス
            val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            builder.setSmallIcon(android.R.drawable.ic_dialog_info)
            builder.setContentTitle(getString(R.string.msg_notification_title_finish))
            builder.setContentText(getString(R.string.msg_notification_text_finish))
            val notification = builder.build()
            // NotificationManagerから通知する
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // 通知の第一引数のidはアプリケーション内でユニークな数値
            manager.notify(0, notification)

            // Serviceをストップ
            stopSelf()
        }
    }
}
