package com.rameshcodeworks.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_product.*

class CartProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_product)

        var cartProductURL = "http://192.168.1.101/OnlineStoreApp/fetch_temporary_order.php?email=${Person.email}"
        var cartProductsList = ArrayList<String>()
        var requestQueue = Volley.newRequestQueue(this@CartProductActivity)
        var jsonArrayRequest = JsonArrayRequest(Request.Method.GET, cartProductURL, null, Response.Listener {
            response ->

            for (joIndex in 0.until(response.length())) {

                cartProductsList.add("${response.getJSONObject(joIndex).getInt("id")}" +
                        " \n ${response.getJSONObject(joIndex).getString("name")}" +
                        " \n ${response.getJSONObject(joIndex).getInt("price")}" +
                        " \n ${response.getJSONObject(joIndex).getString("email")} " +
                        "\n ${response.getJSONObject(joIndex).getInt("amount")}")
            }

            var cartproductAdapter = ArrayAdapter(this@CartProductActivity, android.R.layout.simple_list_item_1, cartProductsList)
            cartProductListView.adapter = cartproductAdapter

        }, Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })

        requestQueue.add(jsonArrayRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item?.itemId == R.id.continueShoppingItem) {
            var intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)

        } else if(item?.itemId == R.id.declineOrderItem) {

            var deleteURL = "http://192.168.1.101/OnlineStoreApp/decline_order.php?email=${Person.email}"
            var requestQueue = Volley.newRequestQueue(this@CartProductActivity)
            var stringRequest = StringRequest(Request.Method.GET, deleteURL, Response.Listener { 
                response ->  
                var intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)
                
            }, Response.ErrorListener { 
                error ->
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()
            })

            requestQueue.add(stringRequest)

        } else if (item?.itemId == R.id.verifyOrderItem) {

            var verifyOrderURL = "http://192.168.1.101/OnlineStoreApp/verify_order.php?email=${Person.email}"
            var requestQueue = Volley.newRequestQueue(this@CartProductActivity)
            var stringRequest = StringRequest(Request.Method.GET, verifyOrderURL, Response.Listener {
                response ->

                var intent = Intent(this, FinalizeShoppingActivity::class.java)
                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                intent.putExtra("LATEST_INVOICE_NUMBER", response)
                startActivity(intent)

            }, Response.ErrorListener {
                error ->
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()
            })

            requestQueue.add(stringRequest)
        }
        return super.onOptionsItemSelected(item)
    }
}
