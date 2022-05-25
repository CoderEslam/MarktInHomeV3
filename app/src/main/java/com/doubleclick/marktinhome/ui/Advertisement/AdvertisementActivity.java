package com.doubleclick.marktinhome.ui.Advertisement;

import static com.doubleclick.marktinhome.Model.Constantes.ADVERTISEMENT;
import static com.doubleclick.marktinhome.Model.Constantes.CHILDREN;
import static com.doubleclick.marktinhome.Model.Constantes.PARENTS;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.doubleclick.AdvInterface;
import com.doubleclick.Servies.FileUtil;
import com.doubleclick.ViewModel.AdvertisementViewModel;
import com.doubleclick.marktinhome.Adapters.AdvAdapter;
import com.doubleclick.marktinhome.Model.Advertisement;
import com.doubleclick.marktinhome.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import id.zelory.compressor.Compressor;


public class AdvertisementActivity extends AppCompatActivity implements AdvInterface {

    private ImageView imageAdv;
    private EditText description;
    private RecyclerView myAdv;
    private final int IMAGE_REQUEST = 1000;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private static Uri imageUri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    private Button upload;
    private AdvertisementViewModel advertisementViewModel;
    private ArrayList<Advertisement> advertisements = new ArrayList<>();
    private AdvAdapter advAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        myAdv = findViewById(R.id.myAdv);
        imageAdv = findViewById(R.id.imageAdv);
        description = findViewById(R.id.description);
        upload = findViewById(R.id.upload);
        advertisementViewModel = new AdvertisementViewModel();
        storageReference = FirebaseStorage.getInstance().getReference(ADVERTISEMENT);
        reference = FirebaseDatabase.getInstance("https://marketinhome-99d25-default-rtdb.firebaseio.com").getReference();
        advertisementViewModel.getAdvAdd().observe(this, new Observer<ArrayList<Advertisement>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ArrayList<Advertisement> advertisements) {
                advAdapter = new AdvAdapter(advertisements, AdvertisementActivity.this);
                myAdv.setAdapter(advAdapter);
                advAdapter.notifyDataSetChanged();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        imageAdv.setOnClickListener(v -> {
            openImage();
        });

    }

    public void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    public String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageAdv.setImageURI(imageUri);
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(this, "Upload in preogress", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);
                   /* .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                    while (!task.isSuccessful()) ;
                    Uri u = task.getResult();
                    Log.e("uuuuuuuuuuu", u.toString());
                }
            });*/
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();
                    String pushId = reference.push().getKey().toString();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("image", mUri);
                    if (description.getText().toString().equals("")) {
                        map.put("text", "");
                    } else {
                        map.put("text", description.getText().toString());
                    }
                    map.put("id", pushId);
                    description.setText("");
                    imageAdv.setImageURI(null);
                    reference.child(ADVERTISEMENT).child(pushId).setValue(map);
                    pd.dismiss();
                } else {
                    Toast.makeText(AdvertisementActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdvertisementActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(AdvertisementActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void OnEditAdvertisement(@NonNull Advertisement advertisement) {
        Intent intent = new Intent(AdvertisementActivity.this, EditAdvertisementActivity.class);
        intent.putExtra("Advertisement", advertisement);
        startActivity(intent);
    }

    @Override
    public void OnDeleteAdvertisement(@NonNull Advertisement advertisement) {
        reference.child(ADVERTISEMENT).child(advertisement.getId()).removeValue();
    }

    @Override
    public void AllAdvertisement(@NonNull ArrayList<Advertisement> advertisement) {

    }
}