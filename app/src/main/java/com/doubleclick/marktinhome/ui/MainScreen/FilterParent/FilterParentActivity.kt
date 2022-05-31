package com.doubleclick.marktinhome.ui.MainScreen.FilterParent

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.OnProduct
import com.doubleclick.ViewModel.ProductViewModel
import com.doubleclick.marktinhome.Adapters.ProductAdapter
import com.doubleclick.marktinhome.Model.ParentCategory
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.ui.ProductActivity.productActivity
import com.google.gson.Gson
import com.todkars.shimmer.ShimmerRecyclerView
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets

class FilterParentActivity : AppCompatActivity(), OnProduct {

    lateinit var FilterParent: ShimmerRecyclerView
    lateinit var productViewModel: ProductViewModel
    lateinit var parentCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_parent)
        FilterParent = findViewById(R.id.FilterParent);
        parentCategory = intent.getStringExtra("parentCategoryId").toString();
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        productViewModel.FilterByParent(parentCategory)
        productViewModel.FilterByParentLiveDate().observe(this, Observer {
            val productAdapter = ProductAdapter(it, this)
            FilterParent.adapter = productAdapter
        })

    }

    override fun onItemProduct(product: Product?) {
        val intent = Intent(this, productActivity::class.java)
        intent.putExtra("product", product);
        startActivity(intent)
    }

    class PriorityAsyncTask : AsyncTask<String?, Int?, String?>() {
        override fun doInBackground(vararg params: String?): String? {
            val url = params[0]?.let { createUrl(it) }
            try {
                return makeHttpRequest(url)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(response: String?) {
            super.onPostExecute(response)
            if (response != null) {
                try {
                    val jsonObject = JSONObject(response)
                    val product = parse(jsonObject)
                    Log.e("jsonObject", product.productName)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }

        protected override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            Log.e("PROGRESS", "" + values[0])
        }

        private fun createUrl(stringUrl: String): URL? {
            var url: URL? = null
            try {
                url = URL(stringUrl)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            return url
        }

        @Throws(IOException::class)
        private fun makeHttpRequest(url: URL?): String {
            var jsonResponse = ""
            if (url == null) {
                return jsonResponse
            }
            var urlConnection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            try {
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.readTimeout = 10000
                urlConnection!!.connectTimeout = 15000
                urlConnection.requestMethod = "GET"
                urlConnection.connect()
                if (urlConnection.responseCode == 200) {
                    inputStream = urlConnection.inputStream
                    jsonResponse = readFromStream(inputStream)
                } else {
                    Log.e("Main Fragment at 406", "Error Resopns Code")
                }
            } catch (e: IOException) {
                Log.e("Main Fragment at 410", "Error Resopns Code$e")
                e.printStackTrace()
            } finally {
                urlConnection?.disconnect()
                inputStream?.close()
            }
            return jsonResponse
        }

        @Throws(IOException::class)
        private fun readFromStream(inputStream: InputStream?): String {
            val output = StringBuilder()
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream, StandardCharsets.UTF_8)
                val reader = BufferedReader(inputStreamReader)
                var line = reader.readLine()
                while (line != null) {
                    output.append(line)
                    line = reader.readLine()
                }
            }
            return output.toString()
        }

        fun parse(jsonObject: JSONObject): Product {
            return Gson().fromJson(jsonObject.toString(), Product::class.java)
        }
    }
}