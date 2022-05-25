package com.doubleclick.marktinhome.ui.Trademark;

import static com.doubleclick.marktinhome.Model.Constantes.ADVERTISEMENT;
import static com.doubleclick.marktinhome.Model.Constantes.TRADEMARK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.doubleclick.Tradmarkinterface;
import com.doubleclick.ViewModel.TradmarkViewModel;
import com.doubleclick.marktinhome.Adapters.AdvAdapter;
import com.doubleclick.marktinhome.Adapters.TrademarkAdapter;
import com.doubleclick.marktinhome.Model.Trademark;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Repository.TradmarkRepository;
import com.doubleclick.marktinhome.ui.Advertisement.AdvertisementActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrademarkActivity extends AppCompatActivity implements Tradmarkinterface {


    private ImageView imageTrademark;
    private EditText trademark;
    private RecyclerView MyTrademark;
    private final int IMAGE_REQUEST = 1000;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference reference;
    private String myId;
    private StorageReference storageReference;
    private static Uri imageUri;
    private StorageTask uploadTask;
    private Button upload;
    private TradmarkViewModel tradmarkViewModel;
    private TrademarkAdapter trademarkAdapter;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trademark);
        imageTrademark = findViewById(R.id.imageTrademark);
        trademark = findViewById(R.id.trademark);
        MyTrademark = findViewById(R.id.MyTrademark);
        upload = findViewById(R.id.upload);
        storageReference = FirebaseStorage.getInstance().getReference(TRADEMARK);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance("https://marketinhome-99d25-default-rtdb.firebaseio.com").getReference();
        tradmarkViewModel = new TradmarkViewModel();
        tradmarkViewModel.getAllMark().observe(this, new Observer<ArrayList<Trademark>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ArrayList<Trademark> trademarks) {
                trademarkAdapter = new TrademarkAdapter(trademarks, TrademarkActivity.this);
                MyTrademark.setAdapter(trademarkAdapter);
                trademarkAdapter.notifyDataSetChanged();
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Trademark trademark = trademarkAdapter.getTrademarkAt(position);
                Toast.makeText(TrademarkActivity.this, "" + trademark.toString(), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(MyTrademark);

        imageTrademark.setOnClickListener(v -> {
            openImage();
        });

        upload.setOnClickListener(v -> {
            uploadImage();
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
            imageTrademark.setImageURI(imageUri);
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(TrademarkActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
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
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        String pushId = reference.push().getKey().toString();
                        HashMap<String, Object> map = new HashMap<>();
                        if (!trademark.getText().toString().equals("") && !mUri.equals("")) {
                            map.put("image", mUri);
                            map.put("name", trademark.getText().toString());
                            map.put("id", pushId);
                        } else {
                            Toast.makeText(TrademarkActivity.this, "name is empty", Toast.LENGTH_LONG).show();
                        }
                        reference.child(TRADEMARK).child(pushId).setValue(map);
                        pd.dismiss();
                        trademark.setText("");
                        imageTrademark.setImageURI(null);
                    } else {
                        Toast.makeText(TrademarkActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TrademarkActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(TrademarkActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void AllNameTradmark(@Nullable List<String> names) {

    }

    @Override
    public void OnItemTradmark(@Nullable Trademark tradmark) {

    }

    @Override
    public void onEditTradmark(@NonNull Trademark tradmark) {
        Intent intent = new Intent(TrademarkActivity.this, EditTradmarkActivity.class);
        intent.putExtra("tradmark", tradmark);
        startActivity(intent);
    }

    @Override
    public void onDeleteTradmark(@NonNull Trademark tradmark) {
        FirebaseDatabase.getInstance().getReference().child(TRADEMARK).child(tradmark.getId()).removeValue();
    }


    @Override
    public void AllTradmark(@NonNull ArrayList<Trademark> tradmark) {

    }
}