package com.doubleclick.marktinhome.ui.MainScreen;


import static com.doubleclick.marktinhome.BaseApplication.ShowToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.util.view.SearchInputView;
import com.bumptech.glide.Glide;
import com.doubleclick.ViewModel.FavoriteViewModel;
import com.doubleclick.ViewModel.OrderViewModel;
import com.doubleclick.ViewModel.ProductViewModel;
import com.doubleclick.ViewModel.UserViewModel;
import com.doubleclick.marktinhome.Adapters.NavAdapter;
import com.doubleclick.marktinhome.Model.ChildCategory;
import com.doubleclick.marktinhome.Model.ClassificationPC;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Seller.SellerActivity;
import com.doubleclick.marktinhome.Views.SmoothButtom.OnItemSelectedListener;
import com.doubleclick.marktinhome.Views.SmoothButtom.SmoothBottomBar;
import com.doubleclick.marktinhome.ui.Filter.FilterActivity;
import com.doubleclick.marktinhome.ui.MainScreen.Favorite.FavoriteActivity;
import com.doubleclick.marktinhome.ui.MainScreen.Frgments.HomeFragment;
import com.doubleclick.marktinhome.ui.MainScreen.Groups.GroupsActivity;
import com.doubleclick.marktinhome.ui.MainScreen.Parents.ParentActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainScreenActivity extends AppCompatActivity implements NavAdapter.onClickChild, SwipeRefreshLayout.OnRefreshListener {

    private SmoothBottomBar bottomBar;
    private NavController navController;
    private RecyclerView menu_recycler_view;
    private ProductViewModel productViewModel;
    private EditText search;
    private FlowingDrawer drawerLayout;
    private ImageView openDrawer, favorite, myOrder;
    private UserViewModel userViewModel;
    private CircleImageView myImage;
    private View main_fragment;
    //    private RecentSearchViewModel recentSearchViewModel;
    private String ShareUrl;
    private String type;
    private SwipeRefreshLayout refreshCategorical;
    private NavAdapter catecoriesAdapter;
    private TextView count, countOrder;
    private FavoriteViewModel favoriteViewModel;
    private OrderViewModel orderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
//        recentSearchViewModel = new ViewModelProvider(this).get(RecentSearchViewModel.class);
        ShareUrl = getIntent().getStringExtra("ShareUrl");
        type = getIntent().getStringExtra("type");
        main_fragment = findViewById(R.id.main_fragment);
        navController = Navigation.findNavController(this, main_fragment.getId());
        menu_recycler_view = findViewById(R.id.menu_recycler_view);
        search = findViewById(R.id.search);
        bottomBar = findViewById(R.id.bottomBar);
        favorite = findViewById(R.id.favorite);
        count = findViewById(R.id.count);
        myOrder = findViewById(R.id.myOrder);
        countOrder = findViewById(R.id.countOrder);
        refreshCategorical = findViewById(R.id.refreshCategorical);
        refreshCategorical.setOnRefreshListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        openDrawer = findViewById(R.id.openDrawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        myImage = findViewById(R.id.myImage);
        drawerLayout.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        setupSmoothBottomMenu();
        loadCategorical();
        userViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Glide.with(MainScreenActivity.this).load(user.getImage()).into(myImage);
            }
        });
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.menu_Cart, R.id.menu_group, R.id.homeFragment, R.id.menu_profile).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        openDrawer.setOnClickListener(v -> {
            drawerLayout.openMenu(true);
        });

        myOrder.setOnClickListener(view -> {
            startActivity(new Intent(this, SellerActivity.class));
        });

        orderViewModel.getMyOrderCountLiveData().observe(this, c -> {
            countOrder.setText(String.format("%d", c));
        });

        favoriteViewModel.getCount().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long c) {
                count.setText(String.format("%d", c));
            }
        });

        favorite.setOnClickListener(view -> {
            startActivity(new Intent(this, FavoriteActivity.class));
        });
        search.setOnKeyListener((v, keyCode, event) -> {
            if (search.getText().toString().contains("https://www.market.doubleclick.com/product/")) {
                String[] url = search.getText().toString().split("www.market.doubleclick.com/product/");
                String idProduct = url[1];
                Log.e("idProduct", idProduct);
                Intent intent = new Intent(MainScreenActivity.this, FilterActivity.class);
                intent.putExtra("type", "ShareUrl");
                intent.putExtra("id", idProduct);
                startActivity(intent);
                return true;
            } else if (!search.getText().toString().equals("")) {
//                    Sending.Check(query, MainScreenActivity.this, MainScreenActivity.this);
                Intent intent = new Intent(MainScreenActivity.this, FilterActivity.class);
                intent.putExtra("type", "search");
                intent.putExtra("id", search.getText().toString());
                startActivity(intent);
                return true;
            } else {
                return false;
            }
        });

        refreshCategorical.setColorScheme(R.color.blue, R.color.ripplecoloreffect, R.color.green, R.color.orange);

        //  https://developer.android.com/training/sharing/receive#java
        Share(ShareUrl, type);
    }

    private void Share(String ShareUrl, String type) {
        try {
            if (!ShareUrl.equals("") && type.equals("group")) {
                Intent intent = new Intent(MainScreenActivity.this, GroupsActivity.class);
                intent.putExtra("id", ShareUrl);
                intent.putExtra("type", "ShareUrl");
                startActivity(intent);
            }
            if (!ShareUrl.equals("") && type.equals("product")) {
                Intent intent = new Intent(MainScreenActivity.this, FilterActivity.class);
                intent.putExtra("id", ShareUrl);
                intent.putExtra("type", "ShareUrl");
                startActivity(intent);
            }
        } catch (NullPointerException e) {

        }

    }


    @SuppressLint("NotifyDataSetChanged")
    private void loadCategorical() {
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getClassificationPC().observe(this, classificationPCS -> {
            catecoriesAdapter = new NavAdapter(classificationPCS, this);
            menu_recycler_view.setAdapter(catecoriesAdapter);
            catecoriesAdapter.notifyDataSetChanged();

        });
    }

    private void setupSmoothBottomMenu() {
        PopupMenu popupMenu = new PopupMenu(this, null);
        popupMenu.inflate(R.menu.menu_bottom);
        Menu menu = popupMenu.getMenu();
        bottomBar.setupWithNavController(menu, navController);
    }

    @Override
    public void onClickedNavChild(ChildCategory childCategory) {
        Intent intent = new Intent(MainScreenActivity.this, FilterActivity.class);
        intent.putExtra("id", childCategory.getPushId());
        intent.putExtra("type", "childId");
        startActivity(intent);
    }

    @Override
    public void onClickedNavParent(ClassificationPC classificationPC) {
        Intent intent = new Intent(MainScreenActivity.this, ParentActivity.class);
        intent.putExtra("classificationPC", classificationPC);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCategorical();
                refreshCategorical.setRefreshing(false);
            }
        }, 2000);
    }
}