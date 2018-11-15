package com.aluckens.testvolley;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import android.support.design.widget.Snackbar;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    EditText et_search;
    TextView tv_title, tv_year, tv_runtime, tv_country, tv_poster,tv_lang,tv_rated;
    ImageView iv_poster;
    Button bt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        bt_search.setOnClickListener(clickEvent);

    }

    private void initializeView() {
        et_search = findViewById(R.id.et_search);
        tv_title = findViewById(R.id.tv_title);
        tv_year = findViewById(R.id.tv_year);
        tv_runtime = findViewById(R.id.tv_runtime);
        tv_country = findViewById(R.id.tv_country);
        tv_poster = findViewById(R.id.tv_poster);
        tv_lang = findViewById(R.id.tv_lang);
        iv_poster = findViewById(R.id.iv_poster);
        bt_search = findViewById(R.id.bt_search);
        tv_rated = findViewById(R.id.tv_rated);
    }

    private View.OnClickListener clickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
       querySever(et_search.getText().toString());
        }};





    /**/



    private void querySever(String title) {
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url ="http://www.omdbapi.com/?apikey=dd5e2adc&t="+title.trim();

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Snackbar.make(MainActivity.this.iv_poster, "Searching...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            tv_title.setText(jsonObject.getString("Title"));
                            tv_year.setText(jsonObject.getString("Year"));
                            tv_runtime.setText(jsonObject.getString("Runtime"));
                            tv_country.setText(jsonObject.getString("Country"));

                            tv_lang.setText(jsonObject.getString("Language"));
                            tv_poster.setText(jsonObject.getString("Genre"));
                            tv_rated.setText(jsonObject.getString("Rated"));


                            new DownLoadImageTask(iv_poster).execute(jsonObject.getString("Poster"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // mTextView.setText("That didn't work!");
                Toast.makeText(MainActivity.this,"That didn't work!",Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
