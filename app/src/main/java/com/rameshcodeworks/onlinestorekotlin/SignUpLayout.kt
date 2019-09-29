package com.rameshcodeworks.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.sign_up_layout.*

class SignUpLayout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_layout)

        sign_up_layout_btnSignUp.setOnClickListener {

            if (sign_up_layout_edt_password.text.toString().equals(
                    sign_up_layout_edt_confirm_pass.text.toString())) {

                // Registration Process
                val signUpURL = "http://192.168.1.101/OnlineStoreApp/join_new_user.php?email=" +
                        sign_up_layout_edt_email.text.toString() + "&username=" +
                        sign_up_layout_edt_username.text.toString() + "&pass=" +
                        sign_up_layout_edt_password.text.toString()

                val requestQueue = Volley.newRequestQueue(this@SignUpLayout)
                val stringRequest = StringRequest(Request.Method.GET, signUpURL, Response.Listener {
                    response ->

                    if (response.equals("A user with this Email address already exists")) {

                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Message")
                        dialogBuilder.setMessage(response)
                        dialogBuilder.create().show()

                    } else {

//                        val dialogBuilder = AlertDialog.Builder(this)
//                        dialogBuilder.setTitle("Message")
//                        dialogBuilder.setMessage(response)
//                        dialogBuilder.create().show()

                        Person.email = sign_up_layout_edt_email.text.toString()
                        Toast.makeText(this@SignUpLayout, response, Toast.LENGTH_LONG).show()

                        val homeIntent = Intent(this@SignUpLayout, HomeScreen::class.java)
                        startActivity(homeIntent)
                    }
                    
                }, Response.ErrorListener { 
                    error ->
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage(error.message)
                    dialogBuilder.create().show()
                })

                requestQueue.add(stringRequest)


            } else {

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("Password Mismatch")
                dialogBuilder.create().show()
            }
        }

        sign_up_layout_btnLogin.setOnClickListener {

            finish()
        }
    }
}
