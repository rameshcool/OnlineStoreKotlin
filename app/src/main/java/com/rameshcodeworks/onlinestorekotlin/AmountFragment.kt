package com.rameshcodeworks.onlinestorekotlin


import android.app.Dialog
import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

/**
 * A simple [Fragment] subclass.
 */
class AmountFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var fragmentView =  inflater.inflate(R.layout.fragment_amount, container, false)

        var edtEnterAmount = fragmentView.findViewById<EditText>(R.id.edtEnterAmount)
        var btnAddToCart = fragmentView.findViewById<ImageButton>(R.id.btnAddToCart)

        btnAddToCart.setOnClickListener {

            var tempURL = "http://192.168.1.101/OnlineStoreApp/insert_temporary_order.php?email=${Person.email}&product_id=${Person.addToCartProductID}&amount=${edtEnterAmount.text.toString()}"
            var requestQueue = Volley.newRequestQueue(activity)
            var stringRequest = StringRequest(Request.Method.GET, tempURL, Response.Listener { response ->

                var intent = Intent(activity, CartProductActivity::class.java)
                startActivity(intent)



            }, Response.ErrorListener { error ->

                val dialogBuilder = AlertDialog.Builder(activity)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()


            })

            requestQueue.add(stringRequest)
        }

        return fragmentView
    }


}
