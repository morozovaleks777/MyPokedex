package com.example.mypokedex.presentation.ero

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mypokedex.R
import com.example.mypokedex.presentation.adapter.EroAdapter

class EroActivity : AppCompatActivity() {
    private var playButton: Button? = null
    private var pauseButton: Button? = null
    private var stopButton: Button? = null
    private lateinit var eroAdapter: EroAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private var pause: Boolean = false

    private lateinit var like: TextView
    private lateinit var prefs: SharedPreferences
    private val APP_PREFERENCES_COUNTER = "counter"
    private var likecount = 0
    private var dislikecount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eron)
        eroAdapter = EroAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = eroAdapter
        playButton = findViewById(R.id.start)
        pauseButton = findViewById(R.id.pause)
        stopButton = findViewById(R.id.stop)
        MediaPlayer.create(this, R.raw.music)
        playButton?.setOnClickListener {
            if (pause) {
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start()
                pause = false
                Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()
            } else {
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.music)
                mediaPlayer.start()
                Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()
            }
            playButton?.isEnabled = false
            pauseButton?.isEnabled = true
            stopButton?.isEnabled = true
            mediaPlayer.setOnCompletionListener {
                playButton?.isEnabled = true
                pauseButton?.isEnabled = false
                stopButton?.isEnabled = false
                Toast.makeText(this, "end", Toast.LENGTH_SHORT).show()
            }
        }
        pauseButton?.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                pause = true
                playButton?.isEnabled = true
                pauseButton?.isEnabled = false
                stopButton?.isEnabled = true
                Toast.makeText(this, "media pause", Toast.LENGTH_SHORT).show()
            }
        }
        stopButton?.setOnClickListener {
            if (mediaPlayer.isPlaying || pause) {
                pause = false
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                playButton?.isEnabled = true
                pauseButton?.isEnabled = false
                stopButton?.isEnabled = false
                Toast.makeText(this, "media stop", Toast.LENGTH_SHORT).show()
            }
        }
        prefs =
            getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    val MediaPlayer.seconds: Int
        get() {
            return this.duration / 1000
        }
    val MediaPlayer.currentSeconds: Int
        get() {
            return this.currentPosition / 1000
        }

    fun dislikeClick(view: View) {
        val dislike = findViewById<TextView>(R.id.dislikeCountText)
        val countString = dislike.text.toString()
        dislikecount = Integer.parseInt(countString)
        dislikecount--
        dislike.text = dislikecount.toString();
    }

    fun likeClick(view: View) {
        val like = findViewById<TextView>(R.id.likecountText)
        val countString = like.text.toString()
        likecount = Integer.parseInt(countString)
        likecount++
        like.text = likecount.toString()
    }

    fun applyEro(view: View) {
        val eroImage = findViewById<ImageView>(R.id.eroImage)
        eroImage.visibility = View.VISIBLE
    }

//    override fun onPause() {
//        super.onPause()
//
//        // Запоминаем данные
//        val editor = prefs.edit()
//        editor.putInt(APP_PREFERENCES_COUNTER,likecount).apply()
//
//    }
//    override fun onResume() {
//        super.onResume()
//
//        if(prefs.contains(APP_PREFERENCES_COUNTER)){
//            // Получаем число из настроек
//            likecount = prefs.getInt(APP_PREFERENCES_COUNTER,0)
//            // Выводим на экран данные из настроек
//            val like = findViewById<TextView>(R.id.likecountText)
//            like.text =likecount.toString()
//        }
//    }
}

