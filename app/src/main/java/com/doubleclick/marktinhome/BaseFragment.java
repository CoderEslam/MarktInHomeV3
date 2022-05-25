package com.doubleclick.marktinhome;

import static android.app.Activity.RESULT_OK;

import static com.doubleclick.marktinhome.Model.Constantes.CHILDREN;
import static com.doubleclick.marktinhome.Model.Constantes.PARENTS;
import static com.doubleclick.marktinhome.Model.Constantes.PRODUCT;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.doubleclick.Api.APIService;
import com.doubleclick.ViewModel.CartViewModel;
import com.doubleclick.marktinhome.Model.Data;
import com.doubleclick.marktinhome.Model.MyResponse;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.Model.Sender;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.Notifications.Client;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class BaseFragment extends Fragment {

    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    public DatabaseReference reference;
    public static String myId;
    private StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    public static Uri imageUri;
    public StorageTask<UploadTask.TaskSnapshot> uploadTask;
    private String Name;
    private String Location = "";
    private String PushIdParents;
    private APIService apiService;

    public BaseFragment() {
        super();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://marketinhome-99d25-default-rtdb.firebaseio.com").getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        if (currentUser != null) {
            apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
            myId = mAuth.getCurrentUser().getUid().toString();
            reference.keepSynced(true);
        }
    }


    public void StartFragment(int mainFragment, Fragment login) throws Exception {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.lefttoright, R.anim.righttoleft);
        transaction.replace(mainFragment, login);
        transaction.commit();
    }

    public void ShowToast(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void openImage(String name, String location, String PushIdParents) {
        this.Location = location;
        this.Name = name;
        this.PushIdParents = PushIdParents;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    public void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }


    public String getFileExtension(Uri uri) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getContext(), "Upload in preogress", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    if (!Location.isEmpty()) {
                        uploadImage("image", Name, Location, myId, null);
                    }
                } catch (NullPointerException e) {
                }
            }

        }
    }


    public void uploadImage(String type, String name, String location, String pushId, Product product) {
        final ProgressDialog pd = new ProgressDialog(getContext());
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
                        HashMap<String, Object> map = new HashMap<>();
                        if (location.equals(USER)) {
                            map.put(type, mUri);
                            reference.child(location).child(pushId).updateChildren(map);

                        }
                        if (location.equals(PARENTS)) {
                            String push = reference.push().getKey();
                            assert push != null;
                            map.put(type, mUri);
                            map.put("name", name);
                            map.put("pushId", push);
                            map.put("order", "0");
                            reference.child(location).child(push).updateChildren(map);
                        }

                        if (location.equals(CHILDREN)) {
                            String push = reference.push().getKey();
                            assert push != null;
                            map.put(type, mUri);
                            map.put("name", Name);
                            map.put("pushId", push);
                            map.put("PushIdParents", PushIdParents);
                            reference.child(location).child(push).updateChildren(map);
                        }

                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void upload(String name, double price, double LastPrice, String descroiprion, String keywords, String trademark, String ParentId, String ChildId, String ParentName, String ChildName) {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();
        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        String push = reference.push().getKey();
                        Date date = new Date();
                        HashMap<String, Object> map = new HashMap<>();
                        double discount = (100.0 - (-1.0 * ((price / LastPrice) * 100.0)));
                        map.put("productId", push);
                        map.put("price", price);
                        map.put("description", descroiprion + "");
                        map.put("date", date.getTime());
                        map.put("adminId", myId);
                        map.put("productName", name);
                        map.put("lastPrice", LastPrice);
                        map.put("tradeMark", trademark);
                        map.put("parentCategoryId", ParentId);
                        map.put("childCategoryId", ChildId);
                        map.put("parentCategoryName", ParentName);
                        map.put("childCategoryName", ChildName);
                        map.put("keywords", keywords + "");
                        map.put("Image", mUri);
                        map.put("TotalRating", 0);
                        map.put("discount", discount);
                        reference.child(PRODUCT).child(Objects.requireNonNull(push)).setValue(map);
                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendNotifiaction(Context context, String receiver, final String Productname) {
        reference.child(USER).child(receiver).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                User user = dataSnapshot.getValue(User.class);
                Data data = new Data(myId, R.drawable.icon_app, "Product name" + ": " + Productname, "New Order", receiver);
                Sender sender = new Sender(data, user.getToken());
                apiService.sendNotification(sender)
                        .enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                if (response.code() == 200) {
                                    if (response.body().success != 1) {
                                        Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {

                            }
                        });
            }

        });
    }
}
