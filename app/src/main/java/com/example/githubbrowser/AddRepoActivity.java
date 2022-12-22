package com.example.githubbrowser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRepoActivity extends AppCompatActivity {
    EditText ownername, reponame;
    Button Add;
    TextView back;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrepo);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Adding  data! Please Wait...");
        dialog.setCanceledOnTouchOutside(false);

        ownername = findViewById(R.id.ownername);
        reponame = findViewById(R.id.reponame);
        back=findViewById(R.id.back);
        Add = findViewById(R.id.add);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRepoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                DocumentReference reference = fstore.collection("Repo Details").document();
                Map<String, Object> map = new HashMap<>();
                map.put("DocId", reference.getId());
                map.put("owner", ownername.getText().toString());
                map.put("repo", reponame.getText().toString());

                reference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(AddRepoActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddRepoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
