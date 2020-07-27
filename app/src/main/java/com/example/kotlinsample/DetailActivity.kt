package com.example.kotlinsample

import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imagePath = intent.getStringExtra("EXTRA_PATH")

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)

        Glide.with(this).load(imagePath).override(size.x, size.y).into(iv_detail)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

}