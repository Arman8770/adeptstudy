package com.ayushapp.adeptstudy.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ayushapp.adeptstudy.OpenGrad
import com.ayushapp.adeptstudy.R


class Home : Fragment() {

    private lateinit var grad:TextView
    private lateinit var postGrad:TextView
    private lateinit var doc:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        grad = view.findViewById(R.id.graduation)
        postGrad = view.findViewById(R.id.postGraduation)
        doc = view.findViewById(R.id.doctorate)

        grad.setOnClickListener{
            activity?.let{
                val intent = Intent (it, OpenGrad::class.java)
                intent.putExtra("grad", "Graduation")
                it.startActivity(intent)
            }
        }

        postGrad.setOnClickListener{
            activity?.let{
                val intent = Intent (it, OpenGrad::class.java)
                intent.putExtra("grad", "Post Graduation")
                it.startActivity(intent)
            }
        }

        doc.setOnClickListener{
            activity?.let{
                val intent = Intent (it, OpenGrad::class.java)
                intent.putExtra("grad", "Doctorate")
                it.startActivity(intent)
            }
        }
        return (view)
    }

}