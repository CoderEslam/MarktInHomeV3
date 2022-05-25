package com.doubleclick.marktinhome.ui.MainScreen.Groups;

import static com.doubleclick.marktinhome.Model.Constantes.GROUPS;
import static com.doubleclick.marktinhome.Model.Constantes.POSTS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.doubleclick.ViewModel.UserViewModel;
import com.doubleclick.marktinhome.Adapters.ImagesGroupAdapter;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.myCarousellayoutmanager.CarouselZoomPostLayoutListener;
import com.doubleclick.marktinhome.Views.myCarousellayoutmanager.CenterScrollListener;
import com.doubleclick.marktinhome.Views.myCarousellayoutmanager.myCarouselLayoutManager;
import com.doubleclick.marktinhome.Views.socialtextview.widget.SocialEditText;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPostActivity extends AppCompatActivity {


    private static final int IMAGE_REQUEST = 100;
    private static final int VIDEO_REQUEST = 1001;
    // TODO id of Group
    private String idGroup;
    // TODO id of Post
    private String idPost;
    private CircleImageView myImage;
    private TextView myName;
    private SocialEditText postText;
    private RecyclerView imageRecycler;
    private VideoView video;
    private ImageView addImages, addVideo;
    private Button postbtn;
    private ArrayList<Uri> uris = new ArrayList<>();
    private DatabaseReference reference;
    private UserViewModel userViewModel;
    private User user;
    private StorageTask uploadTask;
    private StorageReference storageReference;
    private String type = "";
    private ArrayList<String> urls = new ArrayList<>();
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        idGroup = getIntent().getStringExtra("idGroup" /* group id*/);
        idPost = getIntent().getStringExtra("idPost" /* post id*/);
        myImage = findViewById(R.id.myImage);
        myName = findViewById(R.id.myName);
        postText = findViewById(R.id.postText);
        imageRecycler = findViewById(R.id.imageRecycler);
        video = findViewById(R.id.video);
        addImages = findViewById(R.id.addImages);
        addVideo = findViewById(R.id.addVideo);
        postbtn = findViewById(R.id.postbtn);
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("Posts");
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUser().observe(this, u -> {
            user = u;
            Glide.with(EditPostActivity.this).load(u.getImage()).into(myImage);
            myName.setText(u.getName());
        });

        myCarouselLayoutManager layoutManager = new myCarouselLayoutManager(myCarouselLayoutManager.HORIZONTAL);
        imageRecycler.setLayoutManager(layoutManager);
        imageRecycler.setHasFixedSize(true);
        imageRecycler.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        addImages.setOnClickListener(v -> {
            type = "image";
            openImage("image/*", IMAGE_REQUEST);
        });
        addVideo.setOnClickListener(v -> {
            type = "video";
            openImage("video/*", VIDEO_REQUEST);
        });

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }


    public void openImage(String mime, int request) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mime);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, request);
    }

    public String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO Image
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    uris.add(item.getUri());
                }
                imageRecycler.setAdapter(new ImagesGroupAdapter(uris, "uri"));
            }
        }
        // TODO Video
        if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK && data != null) {
            video.setVisibility(View.VISIBLE);
            videoUri = data.getData();
            video.setVideoURI(data.getData());
        }
    }


    private void UploadPost(String url) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("adminId", user.getId());
        map.put("id", idPost);
        map.put("time", -1 * new Date().getTime());
        map.put("text", postText.getText().toString());
        map.put("type", type);
        map.put("meme", url.toString());
        map.put("groupId", idGroup);

        reference.child(GROUPS).child(idGroup).child(POSTS).child(idPost).updateChildren(map);
    }


    public void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        if (uris.size() != 0) {
            for (int i = 0; i < uris.size(); i++) {
                final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uris.get(i)));
                uploadTask = fileReference.putFile(uris.get(i));
                uploadTask.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String url = downloadUri.toString();
                        urls.add(url);
                        if (urls.size() == uris.size()) {
                            UploadPost(urls.toString());
                        }
                        pd.dismiss();
                    } else {
                        Toast.makeText(EditPostActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditPostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            }
        } else {
            Toast.makeText(EditPostActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
        if (!videoUri.toString().equals("")) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(videoUri));
            uploadTask = fileReference.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                    while (!task.isSuccessful()) ;
                    if (task.isSuccessful()) {
                        UploadPost(task.getResult().toString());
                        pd.dismiss();
                    } else {
                        Toast.makeText(EditPostActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditPostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
    }
}