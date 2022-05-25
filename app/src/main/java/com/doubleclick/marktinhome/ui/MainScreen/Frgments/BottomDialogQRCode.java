package com.doubleclick.marktinhome.ui.MainScreen.Frgments;

import static android.content.Context.WINDOW_SERVICE;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.qrgenearator.QRGEncoder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidmads.library.qrgenearator.QRGContents;

/**
 * Created By Eslam Ghazy on 5/18/2022
 */
public class BottomDialogQRCode extends BottomSheetDialogFragment {

    private ImageView qr_image;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private String code;

    public BottomDialogQRCode(String code) {
        this.code = code;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_qr_code, container, false);
        qr_image = view.findViewById(R.id.qr_image);

        if (TextUtils.isEmpty(code)) {
            // if the edittext inputs are empty then execute
            // this method showing a toast message.
            Toast.makeText(requireContext(), "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
        } else {

            qr_image.setVisibility(View.VISIBLE);
            // below line is for getting
            // the windowmanager service.
            WindowManager manager = (WindowManager) requireActivity().getSystemService(WINDOW_SERVICE);

            // initializing a variable for default display.
            Display display = manager.getDefaultDisplay();

            // creating a variable for point which
            // is to be displayed in QR Code.
            Point point = new Point();
            display.getSize(point);

            // getting width and
            // height of a point
            int width = point.x;
            int height = point.y;

            // generating dimension from width and height.
            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;

            // setting this dimensions inside our qr code
            // encoder to generate our qr code.
            qrgEncoder = new QRGEncoder(code, null, QRGContents.Type.TEXT, dimen);
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.getBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qr_image.setImageBitmap(bitmap);

        }
        return view;
    }
}
