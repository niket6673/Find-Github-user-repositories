package com.example.niket.jsonparserandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    EditText editText;
    Button button;
    POJO pojo;
    Adapter adapter;
    RequestQueue requestQueue;
    String name = "niket";
    String BaseURL = "https://api.github.com/users/";
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        editText = findViewById(R.id.edRepo);
        button = findViewById(R.id.getrepo);
        recyclerView = findViewById(R.id.listItems);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        requestQueue = Volley.newRequestQueue(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArrayList<POJO> arrayList = new ArrayList<>();
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

                                    JSONObject jsonObject1 = jsonObject.getJSONObject("owner");

                                    String imageURL = jsonObject1.get("avatar_url").toString();

                                    pojo = new POJO();
                                    pojo.setRepoName(repoName);
                                    pojo.setImageUrl(imageURL);
                                    pojo.setLastUpdated(lastUpdatedTime);

                                    arrayList.add(pojo);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            adapter = new Adapter(Main3Activity.this, arrayList);
                            recyclerView.setAdapter(adapter);
                        } else {
                            recyclerView.setAdapter(null);
                            Toast.makeText(Main3Activity.this, "No repo available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main3Activity.this, "Error while calling REST API", Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(jsonArrayRequest);
            }
        });


    }
}
