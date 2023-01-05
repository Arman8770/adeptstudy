package com.ayushapp.adeptstudy

import android.animation.Animator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import java.util.Objects

class LoginSuccessfull : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_successfull)
        supportActionBar!!.hide()

        val rightTick =  findViewById<LottieAnimationView>(R.id.animationRightTick)
        val btnlogs = findViewById<TextView>(R.id.btnLogSuccess)
        Handler(Looper.getMainLooper()).postDelayed({
            rightTick.visibility = View.VISIBLE
            rightTick.playAnimation()
        },1000)

        rightTick.setOnClickListener{
            rightTick.playAnimation()
        }
        btnlogs.text = "Login Successfully"
    }
}