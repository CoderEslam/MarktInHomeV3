package com.doubleclick.marktinhome.ui.MainScreen.Frgments.Profile;

import static android.content.Context.WINDOW_SERVICE;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doubleclick.DashBoard.DashBoardActivity;
import com.doubleclick.RichEditor.sample.RichEditorActivity;
import com.doubleclick.ViewModel.UserViewModel;
import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.MainActivity;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.ReadQRCode.google.zxing.client.android.Intents;
import com.doubleclick.marktinhome.Views.ReadQRCode.journeyapps.barcodescanner.ScanContract;
import com.doubleclick.marktinhome.Views.ReadQRCode.journeyapps.barcodescanner.ScanOptions;
import com.doubleclick.marktinhome.Views.qrgenearator.QRGEncoder;
import com.doubleclick.marktinhome.ui.Add.AddActivity;
import com.doubleclick.marktinhome.ui.Advertisement.AdvertisementActivity;
import com.doubleclick.marktinhome.ui.MainScreen.AddStoryActivity.AddStoryActivity;
import com.doubleclick.marktinhome.ui.MainScreen.Chat.ChatActivity;
import com.doubleclick.marktinhome.ui.MainScreen.Frgments.BottomDialogComment;
import com.doubleclick.marktinhome.ui.MainScreen.Frgments.BottomDialogQRCode;
import com.doubleclick.marktinhome.ui.MainScreen.Groups.GroupsActivity;
import com.doubleclick.marktinhome.ui.MainScreen.RecentOrderActivity;
import com.doubleclick.marktinhome.ui.ReadQRCodeActivity;
import com.doubleclick.marktinhome.ui.Trademark.TrademarkActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseException;

import java.util.HashMap;
import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGSaver;
import de.hdodenhof.circleimageview.CircleImageView;


public class menu_profileFragment extends BaseFragment {


    private UserViewModel userViewModel;
    private CircleImageView person;
    private TextView username, address, email, phone;
    private ImageView editAddress, editPhone, editname;
    private AlertDialog.Builder builder;
    private FloatingActionButton fab;
    private ConstraintLayout AddProduct, AddAdv, AddTradmark, recentOrder, chat, statistices, QRCode, ReadQRCode;
    private ConstraintLayout logout;


    public menu_profileFragment() {
        // Required empty public constructor
    }


    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Log.d("MainActivity", "Cancelled scan");
                        Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show();
                    } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        Log.d("MainActivity", "Cancelled scan due to missing camera permission");
                        Toast.makeText(requireContext(), "Cancelled due to missing camera permission", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("MainActivity", "Scanned");
                    // todo send my code
                    try {
                        if (result.getContents().contains("/chat")) {
                            Intent intent = new Intent(requireContext(), ChatActivity.class);
                            intent.putExtra("userId", result.getContents().replace("/chat", ""));
                            startActivity(intent);
                        }
                        if (result.getContents().contains("/group")) {
                            Intent intent = new Intent(requireContext(), GroupsActivity.class);
                            intent.putExtra("id", result.getContents().replace("/group", ""));
                            startActivity(intent);
                        }
//                    Toast.makeText(requireContext(), "Scanned Resulte = : " + result.getContents(), Toast.LENGTH_LONG).show();
                    } catch (DatabaseException e) {
                        Toast.makeText(requireContext(), "it's not exist", Toast.LENGTH_LONG).show();
                    }
                }
            });

    public static menu_profileFragment newInstance(String param1, String param2) {
        menu_profileFragment fragment = new menu_profileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_profile, container, false);
        person = view.findViewById(R.id.person);
        username = view.findViewById(R.id.username);
        address = view.findViewById(R.id.address);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        fab = view.findViewById(R.id.fab);
        editAddress = view.findViewById(R.id.editAddress);
        editPhone = view.findViewById(R.id.editPhone);
        editname = view.findViewById(R.id.editname);
        AddProduct = view.findViewById(R.id.AddProduct);
        logout = view.findViewById(R.id.logout);
        AddAdv = view.findViewById(R.id.AddAdv);
        AddTradmark = view.findViewById(R.id.AddTradmark);
        recentOrder = view.findViewById(R.id.recentOrder);
        statistices = view.findViewById(R.id.statistices);
        QRCode = view.findViewById(R.id.QRCode);
        ReadQRCode = view.findViewById(R.id.ReadQRCode);
        chat = view.findViewById(R.id.chat);

        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Glide.with(getContext()).load(user.getImage()).into(person);
                username.setText(user.getName());
                address.setText(user.getAddress());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());
            }

        });
        fab.setOnClickListener(v -> {
            openImage("", USER, "");
        });
        editAddress.setOnClickListener(v -> {
            ShowEdit("address");
        });
        editPhone.setOnClickListener(v -> {
            ShowEdit("phone");
        });
        editname.setOnClickListener(v -> {
            ShowEdit("name");
        });
        AddProduct.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddActivity.class));
        });
        AddAdv.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AdvertisementActivity.class));
        });
        AddTradmark.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), TrademarkActivity.class));
        });
        logout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getContext(), MainActivity.class));
            requireActivity().finish();
        });
        chat.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            startActivity(intent);
//            Navigation.findNavController(v).navigate(menu_profileFragmentDirections.actionMenuProfileToAllUserFragment());
        });

        statistices.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DashBoardActivity.class);
            startActivity(intent);
        });

        recentOrder.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), RecentOrderActivity.class));
        });

        QRCode.setOnClickListener(v -> {
            BottomDialogQRCode bottomDialogQRCode = new BottomDialogQRCode(myId + "/chat");
            bottomDialogQRCode.show(requireActivity().getSupportFragmentManager(), "QR Code");
        });
        ReadQRCode.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions().setCaptureActivity(ReadQRCodeActivity.class);
            barcodeLauncher.launch(options);
        });
        return view;
    }

    private void ShowEdit(String type) {
        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_edit, null, false);
        TextView ok = view.findViewById(R.id.ok);
        TextInputEditText edit = view.findViewById(R.id.edit);
        ok.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(edit.getText().toString())) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(type, edit.getText().toString());
                reference.child(USER).child(myId).updateChildren(map);
                edit.setText("");
                ShowToast("Done");
            } else {
                ShowToast("you can't set empty text");
            }
        });
        builder.setView(view);
        builder.setCancelable(true);
        builder.show();
    }
}