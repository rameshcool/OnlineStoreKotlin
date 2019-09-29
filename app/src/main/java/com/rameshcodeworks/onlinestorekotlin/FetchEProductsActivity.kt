package com.rameshcodeworks.onlinestorekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_eproducts.*

class FetchEProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_eproducts)

        val selectedBrand: String = intent.getStringExtra("BRAND")
        txtBrandName.text = "Product of $selectedBrand"
        var productList = ArrayList<EProduct>()
        val productURL = "http://192.168.1.101/OnlineStoreApp/fetch_eproducts.php?brand=$selectedBrand"
        val requestQueue = Volley.newRequestQueue(this@FetchEProductsActivity)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, productURL, null, Response.Listener {
                response ->
            for (productJOIndex in 0.until(response.length())) {

                productList.add(EProduct(response.getJSONObject(productJOIndex).getInt("id"), response.getJSONObject(productJOIndex).getString("name"), response.getJSONObject(productJOIndex).getInt("price"), response.getJSONObject(productJOIndex).getString("picture")))

            }
            val pAdapter = EProductAdapter(this@FetchEProductsActivity, productList)
            productRV.layoutManager = LinearLayoutManager(this@FetchEProductsActivity)
            productRV.adapter = pAdapter


        }, Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()

        })

        requestQueue.add(jsonArrayRequest)
    }
}
