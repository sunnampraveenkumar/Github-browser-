package com.example.githubbrowser;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailsActivity extends AppCompatActivity {
    TextView repo_name, description, back, eye, delete;
    Button issues;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        repo_name = findViewById(R.id.repo_name_details);
        description = findViewById(R.id.description_details);
        issues = findViewById(R.id.issues);

        back = findViewById(R.id.back2);
        eye = findViewById(R.id.eye);
        delete = findViewById(R.id.delete);


        String repo = getIntent().getStringExtra("repo name");
        String des = getIntent().getStringExtra("description");
        String cnt = getIntent().getStringExtra("issues count");
        String Docid = getIntent().getStringExtra("DocId");
        String url=getIntent().getStringExtra("url");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        fstore = FirebaseFirestore.getInstance();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRepo(Docid,repo);
            }
        });

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        repo_name.setText(repo);
        description.setText(des);
        issues.setText("ISSUES (" + cnt + ")");
    }

    private void deleteRepo(String Docid,String repo)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        builder.setMessage("This process cannot be undone!")
                .setCancelable(true)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DocumentReference documentReference = fstore.collection("Repo Details").document(Docid);
                        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(DetailsActivity.this, "Deleted " + repo, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else
                                    Toast.makeText(DetailsActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
