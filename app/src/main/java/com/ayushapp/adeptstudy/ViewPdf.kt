@file:Suppress("DEPRECATION")

package com.ayushapp.adeptstudy

import android.os.AsyncTask
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


@Suppress("DEPRECATION", "NAME_SHADOWING")
class ViewPdf : AppCompatActivity() {
    private lateinit var pdfFileView:PDFView
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_view_pdf)

            supportActionBar!!.hide()
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

            pdfFileView = findViewById(R.id.pdfViewByURL)

            val filename: String = intent.getStringExtra("filename").toString()
            val fileurl: String = intent.getStringExtra("fileurl").toString()

        Toast.makeText(this,"PDF Opening Please wait!", Toast.LENGTH_SHORT).show()
        RetrievePDFFromURL(pdfFileView).execute(fileurl)
    }

    class RetrievePDFFromURL(pdfFileView: PDFView?):
        AsyncTask<String, Void, InputStream>() {
        val mypdfView: PDFView? = pdfFileView
        override fun doInBackground(vararg params: String?): InputStream? {
            var inputStream:InputStream?=null
            try {
                val url = URL(params.get(0))
                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
                if (urlConnection.responseCode == 200) {
                    // on below line we are initializing our input stream
                    // if the response is successful.
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            }catch (e: Exception) {
                // on below line we are simply printing
                // our exception and returning null
                e.printStackTrace()
                return null;
            }
            return inputStream;
        }

        override fun onPostExecute(result: InputStream?) {
            super.onPostExecute(result)

            mypdfView!!.fromStream(result).load()
        }

    }
}


