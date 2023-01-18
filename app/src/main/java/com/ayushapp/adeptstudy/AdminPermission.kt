package com.ayushapp.adeptstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

class AdminPermission : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_permission)

        supportActionBar!!.hide()

        val rightTick =  findViewById<LottieAnimationView>(R.id.animationRightTick)
        Handler(Looper.getMainLooper()).postDelayed({
            rightTick.visibility = View.VISIBLE
            rightTick.playAnimation()
        },1000)




    }
}