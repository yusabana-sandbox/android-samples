package com.yusabanasandbox.android.servicesample

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder

class SoundManageService : Service() {
    private var _player: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        _player = MediaPlayer()
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
        }
    }

    /**
     * メディア再生が終了した時のリスナクラス
     */
    private inner class PlayerCompletionListener : MediaPlayer.OnCompletionListener {
        override fun onCompletion(mp: MediaPlayer) {
            stopSelf()
        }
    }

}
