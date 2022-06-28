package com.doubleclick.marktinhome.ui.MainScreen.AddStoryActivity;

import static com.doubleclick.marktinhome.BaseFragment.myId;
import static com.doubleclick.marktinhome.Model.Constantes.STORIES;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.doubleclick.marktinhome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddStoryActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 100;
    private ImageView image;
    private Button upload;
    private Uri uri;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);
        image = findViewById(R.id.image);
        upload = findViewById(R.id.upload);
        reference = FirebaseDatabase.getInstance().getReference();

        image.setOnClickListener(view -> {
            openImage();
        });

        upload.setOnClickListener(view -> {
            Upload();
        });

    }

    private void Upload() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();
        if (!uri.toString().equals("")) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Stories");
            storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri)).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    uriTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                long time = new Date().getTime();
                                String id = reference.push().getKey().toString() + ":" + time;
                                Map<String, Object> map = new HashMap<>();
                                map.put("imageUri", task.getResult().toString());
                                map.put("time", "" + time);
                                map.put("id", id);
                                reference.child(STORIES).child(myId).child(id).updateChildren(map);
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double p = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                    progressDialog.setMessage(p + "% Uploading...");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddStoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

        }

    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.getData();
            image.setImageURI(uri);
        }
    }

    public void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }
}