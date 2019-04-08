package com.example.uhc;

public class Product2 {

    private String County, Uhcrating, Uhccomment;

    public Product2(){


    }

    public Product2(String county, String uhcrating, String uhccomment) {
        this.County = county;
        this.Uhcrating = uhcrating;
        this.Uhccomment = uhccomment;

    }

    public String getCounty() { return County; }

    public String getUhcrating() { return Uhcrating; }

    public String getUhccomment() {
        return Uhccomment;
    }



}
