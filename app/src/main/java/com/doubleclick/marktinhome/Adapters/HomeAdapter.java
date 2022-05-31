package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.ViewHolder.BannerSliderViewholder;
import com.doubleclick.ViewHolder.CategoricalViewHolder;
import com.doubleclick.ViewHolder.RecentResearchViewHolder;
import com.doubleclick.ViewHolder.TopDealsViewHolder;
import com.doubleclick.ViewHolder.ProductViewHolder;
import com.doubleclick.ViewHolder.TopViewHolder;
import com.doubleclick.ViewHolder.TrademarkViewHolder;
import com.doubleclick.marktinhome.Model.HomeModel;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/4/2022
 */
public class HomeAdapter extends RecyclerView.Adapter {

    private ArrayList<HomeModel> homeModels;

    public HomeAdapter(ArrayList<HomeModel> homeModels) {
        this.homeModels = homeModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomeModel.Advertisement:
                return new BannerSliderViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slids_ad_banner, parent, false));
            case HomeModel.TopCategory:
                return new TopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.h_top_recyclerview, parent, false));
            case HomeModel.Products:
                return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.v_product_recyclerview, parent, false));
            case HomeModel.TopDeal:
                return new TopDealsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_top_deals, parent, false));
            case HomeModel.RecentSearch:
                return new RecentResearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_search_layout, parent, false));
            case HomeModel.Trademarks:
                return new TrademarkViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.h_treademark_recyclerview, parent, false));
            case HomeModel.Categorical:
                return new CategoricalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_categorical, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homeModels.get(position).getType()) {
            case HomeModel.Products:
                ((ProductViewHolder) holder).setProduct(homeModels.get(position).getProductArrayList(), homeModels.get(position).getOnProduct());
                break;
            case HomeModel.Advertisement:
                ((BannerSliderViewholder) holder).setBannerSliderViewPager(homeModels.get(position).getAdvertisements());
                break;
            case HomeModel.Trademarks:
                ((TrademarkViewHolder) holder).setTrademark(homeModels.get(position).getTrademarkArrayList(), homeModels.get(position).getTradmarkinterface());
                break;
            case HomeModel.TopDeal:
                ((TopDealsViewHolder) holder).setTopDeals(homeModels.get(position).getProductsTopDaels(), homeModels.get(position).getOnProduct(), homeModels.get(position).getViewMore());
                break;
            case HomeModel.RecentSearch:
                ((RecentResearchViewHolder) holder).setRecentSearch(homeModels.get(position).getProductsRecentSearch(), homeModels.get(position).getOnProduct(), homeModels.get(position).getViewMore());
                break;
            case HomeModel.TopCategory:
                ((TopViewHolder) holder).setParent(homeModels.get(position).getParentCategories(), homeModels.get(position).getOnItemPerantTop());
            case HomeModel.Categorical:
                ((CategoricalViewHolder) holder).setCategorical(homeModels.get(position).getCategoricalProducts(), homeModels.get(position).getOnProduct(), homeModels.get(position).getCategorical());
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (homeModels.get(position).getType()) {
            case HomeModel.TopCategory:
                return HomeModel.TopCategory;
            case HomeModel.Advertisement:
                return HomeModel.Advertisement;
            case HomeModel.TopDeal:
                return HomeModel.TopDeal;
            case HomeModel.Products:
                return HomeModel.Products;
            case HomeModel.Trademarks:
                return HomeModel.Trademarks;
            case HomeModel.RecentSearch:
                return HomeModel.RecentSearch;
            case HomeModel.Categorical:
                return HomeModel.Categorical;
            default:
                return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return homeModels.size();
    }

}
