package com.ayushapp.adeptstudy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore


class Login : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this , gso)

        signInGoogle()
        findViewById<Button>(R.id.gSignInBtn).setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful && account.email == "ayush.adeptstudy@gmail.com"){
                val intent = Intent(this , LoginAdminSuccessful::class.java)
                intent.putExtra("account",account)
                startActivity(intent)
                finish()
            }
            else if (it.isSuccessful && account.email != "ayush.adeptstudy@gmail.com"){
                gotoFirestore(account)
                finish()
            }
            else{
                Toast.makeText(this, it.exception.toString() , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoFirestore(account: GoogleSignInAccount) {
        val docRef = FirebaseFirestore.getInstance().collection("users").document(""+account.email)
        docRef.get()
            .addOnSuccessListener {
                if(it.exists() && it.getBoolean("access")==true)
                {
                    val intent= Intent(this,UserLoginSuccessful::class.java)
                    intent.putExtra("account", account)
                    startActivity(intent)
                }
                else{
                    val intent= Intent(this,AdminPermission::class.java)
                    intent.putExtra("account", account)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                val intent= Intent(this,AdminPermission::class.java)
                intent.putExtra("account", account)
                startActivity(intent)
            }
    }
}