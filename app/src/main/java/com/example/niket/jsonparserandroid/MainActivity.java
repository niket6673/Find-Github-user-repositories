package com.example.niket.jsonparserandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView textView;
    RequestQueue requestQueue;

    String BaseURL = "https://api.github.com/users/";
    String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textVieww);
        textView.setMovementMethod(new ScrollingMovementMethod());

        editText = findViewById(R.id.tv_daily);
        requestQueue = Volley.newRequestQueue(this);

        button = findViewById(R.id.repobutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textView.setText("");


                URL = BaseURL + editText.getText().toString().trim() + "/repos";

                final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JsonArrayRequest.Method.GET, URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length() > 0) {

                            for (int i = 0; i < response.length(); i++) {

                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String repoName = jsonObject.get("name").toString();
                                    String lastUpdatedTime = jsonObject.get("updated_at").toString();


                                    textView.setText(textView.getText().toString().trim() + "\n\n" + repoName + " last updated " + lastUpdatedTime);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        } else {
                            textView.setText("no repo found");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error while calling REST API");
                    }
                });

                requestQueue.add(jsonArrayRequest);
            }
        });
    }


}
