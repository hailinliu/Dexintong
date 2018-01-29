package com.runtai.newdexintong.module.fenlei.bean;

public class ChaoShiModelBean {

    private int id;
    private int stock;
    private String spec;
    private double price;
    private String name;
    private String minpic;
    private String maxpic;

    /**
     * @param id      商品ID
     * @param stock   商品库存
     * @param spec    商品规格
     * @param price   商品价格
     * @param name    商品名称
     * @param minpic  商品小图
     * @param maxpic  商品大图
     */
    public ChaoShiModelBean(int id, int stock, String spec, double price, String name, String minpic, String maxpic) {
        this.id = id;
        this.stock = stock;
        this.spec = spec;
        this.price = price;
        this.name = name;
        this.minpic = minpic;
        this.maxpic = maxpic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinpic() {
        return minpic;
    }

    public void setMinpic(String minpic) {
        this.minpic = minpic;
    }

    public String getMaxpic() {
        return maxpic;
    }

    public void setMaxpic(String maxpic) {
        this.maxpic = maxpic;
    }

}
