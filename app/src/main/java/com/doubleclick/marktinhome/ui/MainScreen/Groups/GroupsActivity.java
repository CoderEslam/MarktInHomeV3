package com.doubleclick.marktinhome.ui.MainScreen.Groups;

import static com.doubleclick.marktinhome.Model.Constantes.GROUPS;
import static com.doubleclick.marktinhome.Model.Constantes.POSTS;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.doubleclick.ViewModel.GroupViewModel;
import com.doubleclick.ViewModel.PostsViewModel;
import com.doubleclick.ViewModel.UserViewModel;
import com.doubleclick.marktinhome.Adapters.GroupsAdapter;
import com.doubleclick.marktinhome.Model.GroupData;
import com.doubleclick.marktinhome.Model.PostData;
import com.doubleclick.marktinhome.Model.PostsGroup;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.CircleImageView;
import com.doubleclick.marktinhome.Views.ReadQRCode.google.zxing.client.android.Intents;
import com.doubleclick.marktinhome.Views.ReadQRCode.journeyapps.barcodescanner.ScanContract;
import com.doubleclick.marktinhome.Views.ReadQRCode.journeyapps.barcodescanner.ScanOptions;
import com.doubleclick.marktinhome.Views.socialtextview.SocialTextView;
import com.doubleclick.marktinhome.ui.MainScreen.Chat.ChatActivity;
import com.doubleclick.marktinhome.ui.MainScreen.Frgments.BottomDialogQRCode;
import com.doubleclick.marktinhome.ui.MainScreen.MainScreenActivity;
import com.doubleclick.marktinhome.ui.ReadQRCodeActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class GroupsActivity extends AppCompatActivity implements GroupsAdapter.Loadmore, GroupsAdapter.OptionPost {


    private static final int IMAGE_REQUEST = 100;
    private String id; /* Group id */
    private ImageView back, QRCode;
    private CircleImageView imageCreator;
    private CircleImageView imageGroup, selectImage;
    private LinearProgressIndicator progressIndicator;
    private TextView username, history, nothing, header, tvToolbarTitle;
    private SocialTextView title;
    private LinearLayout create_post;
    private ShimmerRecyclerView post;
    private GroupViewModel groupViewModel;
    private PostsViewModel postsViewModel;
    private GroupsAdapter groupsAdapter;
    private DatabaseReference reference;
    private ImageView option;
    private ArrayList<PostData> postDataArrayList = new ArrayList<>();
    private Uri imageUri;
    private String type = "";
    private UserViewModel userViewModel;
    private LinearLayout creatorLayout;
    private AppBarLayout appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        // id of Group
        id = getIntent().getStringExtra("id" /* id of group*/);
        back = findViewById(R.id.back);
        imageGroup = findViewById(R.id.imageGroup);
        progressIndicator = findViewById(R.id.progressBar);
        username = findViewById(R.id.username);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        title = findViewById(R.id.title);
        header = findViewById(R.id.header);
        creatorLayout = findViewById(R.id.creatorLayout);
        history = findViewById(R.id.history);
        option = findViewById(R.id.option);
        selectImage = findViewById(R.id.selectImage);
        appbar = findViewById(R.id.appbar);
        imageCreator = findViewById(R.id.imageCreator);
        reference = FirebaseDatabase.getInstance().getReference();
        // todo show when there is nothing to show
        nothing = findViewById(R.id.nothing);
        post = findViewById(R.id.post);
        QRCode = findViewById(R.id.QRCode);
        create_post = findViewById(R.id.create_post);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        postsViewModel.loadPosts(id /*  Group Id */, 1000);
        groupViewModel.getGroupDataById(id);
        groupViewModel.GroupData().observe(this, new Observer<GroupData>() {
            @Override
            public void onChanged(GroupData groupData) {
                Glide.with(GroupsActivity.this).load(groupData.getGroup().getImage()).into(imageGroup);
                // reference -> https://www.tutorialspoint.com/how-does-one-use-glide-to-download-an-image-into-a-bitmap
                Glide.with(GroupsActivity.this).asDrawable().load(groupData.getGroup().getCover()).into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        appbar.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

                tvToolbarTitle.setText(groupData.getGroup().getName());
                header.setText(groupData.getGroup().getName());
                username.setText(groupData.getUser().getName());
                Glide.with(GroupsActivity.this).load(groupData.getUser().getImage()).into(imageCreator);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm aa");
                history.setText(String.format("Created By %s at %s ", groupData.getUser().getName(), simpleDateFormat.format(groupData.getGroup().getTime())));
                title.setText(groupData.getGroup().getDetails());
                creatorLayout.setOnClickListener(v -> {
                    Intent intent = new Intent(GroupsActivity.this, ChatActivity.class);
                    intent.putExtra("userId", groupData.getUser().getId());
                    startActivity(intent);
                });
            }
        });
        post.showShimmer();
        postsViewModel.getPosts().observe(this, postData -> {
            if (postData.size() != 0) {
                postDataArrayList = postData;
                progressIndicator.setVisibility(View.GONE);
                groupsAdapter = new GroupsAdapter(postDataArrayList, GroupsActivity.this, GroupsActivity.this);
                post.setAdapter(groupsAdapter);
                post.hideShimmer();
            }
        });

        userViewModel.getUser().observe(this, user -> {
            Glide.with(GroupsActivity.this).load(user.getImage()).into(selectImage);
        });

        create_post.setOnClickListener(v -> {
            Intent intent = new Intent(GroupsActivity.this, CreatePostActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });

        back.setOnClickListener(v -> {
            startActivity(new Intent(GroupsActivity.this, MainScreenActivity.class));
            finish();
        });
        QRCode.setOnClickListener(v -> {
            BottomDialogQRCode bottomDialogQRCode = new BottomDialogQRCode(id + "/group");
            bottomDialogQRCode.show(GroupsActivity.this.getSupportFragmentManager(), "QR Code");
        });
        option.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_group, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int ItemId = item.getItemId();
                    if (ItemId == R.id.share) {
                        Share();
                        return true;
                    }
                    if (ItemId == R.id.editDetils) {
                        openBottomSheet(id /*group id */, "details");
                        return true;
                    }
                    if (ItemId == R.id.editCover) {
                        type = "cover";
                        openImage(type);
                        return true;
                    }
                    if (ItemId == R.id.editProfile) {
                        type = "image";
                        openImage(type);
                        return true;
                    }
                    if (ItemId == R.id.editName) {
                        openBottomSheet(id /*group id */, "name");
                        return true;
                    }
                    return false;
                }
            });
            popupMenu.show();
        });
    }

    public void openImage(String type) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private void openBottomSheet(String id, String type) {
        BottomSheetEditor bottomSheetFragmentUsernameAndBioUpdate = new BottomSheetEditor(id, type, reference);
        assert getFragmentManager() != null;
        bottomSheetFragmentUsernameAndBioUpdate.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void loadmore(int num) {
        postsViewModel.loadPosts(id /*  Group Id */, num);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void delete(String idPost, int pos) {
        reference.child(GROUPS).child(id/* group id */).child(POSTS).child(idPost).removeValue();
        groupsAdapter.notifyItemRemoved(pos);
        groupsAdapter.notifyDataSetChanged();
        postDataArrayList.remove(pos);

    }

    @Override
    public void edit(String idPost, int pos) {
        Intent intent = new Intent(this, EditPostActivity.class);
        intent.putExtra("idGroup", id);
        intent.putExtra("idPost", idPost);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            uploadImage();
        }
    }

    public void uploadImage() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        if (!imageUri.toString().equals("")) {
            final StorageReference fileReference = FirebaseStorage.getInstance().getReference().child("GroupsImages").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                uri.addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put(type, task.getResult().toString());
                            reference.child(GROUPS).child(id).updateChildren(map);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(GroupsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double p = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                    progressDialog.setMessage(p + " % Uploading...");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GroupsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void Share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.market.doubleclick.com/group/" + id);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }

}