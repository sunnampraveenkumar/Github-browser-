package com.example.githubbrowser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button add;
    TextView add2,tv;
    RecyclerView recyclerView;
    String REPO_URL = "https://api.github.com/repos/";
    FirebaseFirestore fstore;
    RequestQueue requestQueue;

    List<String> urls;
    List<ModelClass> data;
    List<String> docids;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.bt);
        add2 = findViewById(R.id.add);
        tv=findViewById(R.id.tv);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading Repos! Please Wait...");
        dialog.show();

        fstore = FirebaseFirestore.getInstance();

        fstore.collection("Repo Details").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                urls = new ArrayList<>();
                data = new ArrayList<>();
                docids=new ArrayList<>();
                for (DocumentSnapshot dc : value.getDocuments()) {
                    urls.add(REPO_URL + dc.get("owner") + "/" + dc.get("repo"));
                    docids.add(dc.get("DocId").toString());
                }
                if(urls.size()>0)
                {
                   add.setVisibility(View.GONE);
                   tv.setVisibility(View.GONE);
                }
                for (int i = 0; i < urls.size(); i++) {

                    int finalI = i;
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urls.get(i), null,
                            new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                ModelClass o=new ModelClass();
                                o.setRepo_name(response.getString("name"));
                                o.setDescription(response.getString("description"));
                                o.setHtml_url(response.getString("html_url"));
                                o.setIssues_cnt(response.getString("open_issues"));
                                o.setDocids(docids.get(finalI));
                                data.add(o);

                                RepoAdapter adapter=new RepoAdapter(data,MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    requestQueue= Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(jsonObjectRequest);


                }
                dialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRepoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRepoActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }
}