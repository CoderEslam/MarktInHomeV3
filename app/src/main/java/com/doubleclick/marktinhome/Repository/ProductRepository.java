package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.CHILDREN;
import static com.doubleclick.marktinhome.Model.Constantes.PARENTS;
import static com.doubleclick.marktinhome.Model.Constantes.PRODUCT;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.Products;
import com.doubleclick.marktinhome.Model.CategoricalProduct;
import com.doubleclick.marktinhome.Model.ChildCategory;
import com.doubleclick.marktinhome.Model.ClassificationPC;
import com.doubleclick.marktinhome.Model.ParentCategory;
import com.doubleclick.marktinhome.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.components.DependencyException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/2/2022
 */
public class ProductRepository extends BaseRepository {

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Product> QueryProducts = new ArrayList<>();
    private ArrayList<Product> QueryProductsByParents = new ArrayList<>();
    private ArrayList<Product> QueryProductsByChild = new ArrayList<>();
    private ArrayList<Product> productWithTrademark = new ArrayList<>();
    private ArrayList<Product> productsTopDeals = new ArrayList<>();
    private ArrayList<ParentCategory> parentCategories = new ArrayList<>();
    private ArrayList<ChildCategory> childCategories = new ArrayList<>();
    //    private ArrayList<Product> recentSearchProduct = new ArrayList<>();
//    private static ArrayList<ArrayList<ArrayList<Product>>> arrayListOfArrayLists = new ArrayList<>();
    private Products product;

    public ProductRepository(Products product) {
        this.product = product;
    }

    // to get All products from Firebase
    public void getProduct() {
//        reference.child(PRODUCT).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                try {
//                    if (isNetworkConnected()) {
//                        if (snapshot.exists()) {
//                            Product p = snapshot.getValue(Product.class);
////                                    HashMap<String, Object> map = new HashMap<>();
////                                    map.put("keywords","");
////                                    dataSnapshot.getRef().updateChildren(map);
//                            products.add(p);
//                            product.getProduct(products);
//
//                        }
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        reference.child(PRODUCT).limitToLast(1000).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            getDataAsHash(snapshot);
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Product product = dataSnapshot.getValue(Product.class);
//                                    HashMap<String, Object> map = new HashMap<>();
//                                    map.put("keywords","");
//                                    dataSnapshot.getRef().updateChildren(map);
                                products.add(product);
                            }
                            // to sort products according to total rate
//                            Collections.sort(products, Collections.reverseOrder());
                            product.getProduct(products);
                        }

                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptiongetProduct", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    // to get all parents from Firebase
    public void getParents() {
        reference.child(PARENTS).orderByChild("order").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                ParentCategory parentCategory = dataSnapshot.getValue(ParentCategory.class);
//                                    HashMap<String, Object> map = new HashMap<>();
//                                    map.put("discount",0);
//                                    dataSnapshot.getRef().updateChildren(map);
                                parentCategories.add(parentCategory);
                            }
                            product.Parentproduct(parentCategories);
                            LoadCategorical();
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptiongetParents", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    // to get all Children from Firebase
    public void getChild() {
        reference.child(CHILDREN).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            childCategories.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                ChildCategory childCategory = dataSnapshot.getValue(ChildCategory.class);
                                childCategories.add(childCategory);
                            }
                            product.Childproduct(childCategories);
                            new Handler().postDelayed(() -> Classification(), 3000);
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptiongetChild", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // to Classification all Children to according parents
    public void getChildren(String child) {
        reference.child(CHILDREN).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                try {
                    if (isNetworkConnected()) {
                        if (task.getResult().exists()) {
                            if (task.isComplete()) {
                                DataSnapshot snapshot = task.getResult();
                                childCategories.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    ChildCategory childCategory = dataSnapshot.getValue(ChildCategory.class);
                                    if (childCategory.getPushIdParents().equals(child)) {
                                        childCategories.add(childCategory);
                                    }
                                }
                            }
                            product.Childrenproduct(childCategories);
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptiongetChildren", e.getMessage());
                }

            }
        });

    }

    // to associate Children with his parents
    public void Classification() {
        ArrayList<ClassificationPC> classificationPCS = new ArrayList<>();
        for (ParentCategory parentCategory : parentCategories) {
            ArrayList<ChildCategory> c1 = new ArrayList<>();
            for (ChildCategory childCategory : childCategories) {
                if (parentCategory.getPushId().equals(childCategory.getPushIdParents())) {
                    c1.add(childCategory);
                }
            }
            classificationPCS.add(new ClassificationPC(c1, parentCategory.getName(), parentCategory.getImage(), false, parentCategory.getPushId()));
        }
        product.getClassificationPC(classificationPCS);
    }


    //  لم تستخدم بعد
   /* private void Rearrange() {
        for (int i = 0; i < parentCategories.size(); i++) {
            ArrayList<ArrayList<Product>> arrayListArrayList = new ArrayList<>();
            for (int j = 0; j < childCategories.size(); j++) {
                ArrayList<Product> al = new ArrayList<>();
                for (Product product : products) {
                    if (product.getParentCategoryId().equals(parentCategories.get(i).getName())) {
                        if (product.getChildCategoryId().equals(childCategories.get(j).getName())) {
                            al.add(product);
                        }
                    }
                }
                arrayListArrayList.add(al);
            }
            arrayListOfArrayLists.add(arrayListArrayList);
        }
        product.product(arrayListOfArrayLists);
    }*/


    public void getSearch(String query) {
        Query q = reference.child(PRODUCT).orderByChild("productName");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Product product = dataSnapshot.getValue(Product.class);
                                if (Objects.requireNonNull(product).getProductName().contains(query) || product.getTradeMark().contains(query) || product.getChildCategoryName().contains(query) || product.getParentCategoryName().contains(query) || product.getKeywords().contains(query)) {
                                    QueryProducts.add(product);
                                }
                            }
                            product.getQueryProducts(QueryProducts);
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptiongetQuery", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // to get all product which in the same parent
    public void FilterByParent(String parentId) {
        reference.child(PRODUCT).orderByChild("parentCategoryId").equalTo(parentId).limitToLast(1000).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Product product = dataSnapshot.getValue(Product.class);
                                assert product != null;
                                if (product.getParentCategoryId().equals(parentId)) {
                                    QueryProductsByParents.add(product);
                                }
                            }
                            product.getQueryByParents(QueryProductsByParents);
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }

                } catch (Exception e) {
                    Log.e("ExceptionFilterByParent", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getSearchByChild(String ChildId) {
        reference.child(PRODUCT).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                try {
                    if (isNetworkConnected()) {
                        if (task.getResult().exists()) {
                            DataSnapshot snapshot = task.getResult();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Product product = dataSnapshot.getValue(Product.class);
                                if (product.getChildCategoryId().equals(ChildId)) {
                                    QueryProductsByChild.add(product);
                                }
                            }
                            product.getQueryByChild(QueryProductsByChild);
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }

                } catch (Exception e) {
                    Log.e("ExceptionSearchByChild", e.getMessage());
                }

            }
        });
    }

    // filter with tradmark
    public void ProductWithTrademark(String name) {
        reference.child(PRODUCT).limitToLast(1000).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Product product = dataSnapshot.getValue(Product.class);
                                assert product != null;
                                if (product.getTradeMark().equals(name)) {
                                    productWithTrademark.add(product);
                                }
                            }
                        }
                        product.getProductWithTrademark(productWithTrademark);
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptionProductTramark", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // to get Top Deals
    public void TopDeals(/*DataSnapshot dataSnapshot*/) {
        reference.child(PRODUCT).orderByChild("discount").limitToFirst(50).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Product product = dataSnapshot.getValue(Product.class);
                                productsTopDeals.add(product);
                            }
                            product.getProductTopDeals(productsTopDeals);
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }

                } catch (Exception e) {
                    Log.e("ExceptionTopDeals", e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getProductById(String id) {
        reference.child(PRODUCT).child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<Product> productArrayList = new ArrayList<>();
                try {
                    if (isNetworkConnected()) {
                        if (task.getResult().exists()) {
                            Product p = task.getResult().getValue(Product.class);
                            productArrayList.add(p);
                            product.getProductById(productArrayList);
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptiongetProductById", e.toString());
                }
            }
        });

    }


    //================================categorical===================================

    public void LoadCategorical() {
        ArrayList<CategoricalProduct> categorical = new ArrayList<>();
        reference.child(PRODUCT).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (ParentCategory parent : parentCategories) {
                    ArrayList<Product> productArrayList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Product product = dataSnapshot.getValue(Product.class);
                        assert product != null;
                        if (parent.getPushId().equals(product.getParentCategoryId())) {
                            productArrayList.add(product);
                        }
                    }
                    categorical.add(new CategoricalProduct(productArrayList, parent.getName(), parent.getPushId()));
                }
                product.loadCategorical(categorical);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //================================categorical===================================

    public void Query(String value) {
        Query query = reference.child(PRODUCT).orderByChild("parentCategoryId").equalTo(value);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public HashMap getDataAsHash(DataSnapshot snapshot) {
        Object o = new Object();
        Map<String, Object> map = new HashMap();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            try {
                map = (HashMap) dataSnapshot.getValue(o.getClass());

//                Log.e("fldObjValues", fldObj.values().toString());
//                Log.e("fldObjKey", fldObj.keySet().toString());
            } catch (Exception ex) {
                // My custom error handler. See 'ErrorHandler' in Gist
                continue;
            }
        }
        Log.e("ProductRepo450", map.toString());
        return (HashMap) map;
    }
}
