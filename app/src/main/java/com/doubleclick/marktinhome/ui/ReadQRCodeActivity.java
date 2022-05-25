package com.doubleclick.marktinhome.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.KeyEvent;

import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.ReadQRCode.journeyapps.barcodescanner.CaptureManager;
import com.doubleclick.marktinhome.Views.ReadQRCode.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.Objects;

public class ReadQRCodeActivity extends AppCompatActivity {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_qrcode);

        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Scan Barcode");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();

    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}