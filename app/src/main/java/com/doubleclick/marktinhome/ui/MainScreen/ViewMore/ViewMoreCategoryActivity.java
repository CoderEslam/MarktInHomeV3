package com.doubleclick.marktinhome.ui.MainScreen.ViewMore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.doubleclick.ViewModel.ProductViewModel;
import com.doubleclick.marktinhome.Model.CategoricalProduct;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ViewMoreCategoryActivity extends AppCompatActivity {

    private CategoricalProduct categoricalProduct;
    private RecyclerView ViewMoreRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more_category);
        ViewMoreRecyclerView = findViewById(R.id.ViewMoreRecyclerView);
        categoricalProduct = getIntent().getParcelableExtra("categoricalProduct");
        new PriorityAsyncTask().execute("https://marketinhome-99d25-default-rtdb.firebaseio.com/product.json?orderBy=%22lastPrice%22&limitToLast=1&print=pretty");
        new PriorityAsyncTask().execute("https://marketinhome-99d25-default-rtdb.firebaseio.com/product/-N39ef4mIuZFcNlRtqbs1653741365922/.json");
    }


    public class PriorityAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {

            URL url = createUrl(urls[0]);
            try {
                String json = makeHttpRequest(url);
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Product product = parse(jsonObject);
                    Log.e("jsonObject", product.getProductName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e("PROGRESS", "" + values[0]);
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException {

            String jsonResponse = "";
            if (url == null) {
                return jsonResponse;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e("Main Fragment at 406", "Error Resopns Code");
                }
            } catch (IOException e) {
                Log.e("Main Fragment at 410", "Error Resopns Code" + e);
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        public Product parse(JSONObject jsonObject) {
            return new Gson().fromJson(String.valueOf(jsonObject), Product.class);
        }
    }

}