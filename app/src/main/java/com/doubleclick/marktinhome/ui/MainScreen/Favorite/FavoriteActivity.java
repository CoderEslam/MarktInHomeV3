package com.doubleclick.marktinhome.ui.MainScreen.Favorite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.doubleclick.OnProduct;
import com.doubleclick.ViewModel.FavoriteViewModel;
import com.doubleclick.marktinhome.Adapters.ProductAdapter;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.ui.ProductActivity.productActivity;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements OnProduct {


    private FavoriteViewModel favoriteViewModel;
    private RecyclerView favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favorite = findViewById(R.id.favorite);
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        favoriteViewModel.getmyFavorite().observe(this, products -> {
            favorite.setAdapter(new ProductAdapter(products, this));
        });
    }

    @Override
    public void onItemProduct(@Nullable Product product) {

        Intent intent = new Intent(this, productActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);

    }
}