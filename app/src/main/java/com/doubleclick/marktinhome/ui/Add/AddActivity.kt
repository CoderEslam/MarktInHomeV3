package com.doubleclick.marktinhome.ui.Add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add.RichFragment
import com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add.UploadProduct.UploadFragment

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO this activity to cary fragments
        setContentView(R.layout.activity_add)
    }


    override fun onBackPressed() {
        finish();
    }

}