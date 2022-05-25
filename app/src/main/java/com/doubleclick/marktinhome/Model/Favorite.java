package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class Favorite {

    public Favorite() {
    }

    public String getFravoritId() {
        return FravoritId;
    }

    public void setFravoritId(String fravoritId) {
        FravoritId = fravoritId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    private String FravoritId;
    private String ProductId;

}
