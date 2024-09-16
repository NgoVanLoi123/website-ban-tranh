package entity;

public class OrderDetail {
    private String id;
    private String order_id;
    private String picture_id;
    private String title;
    private String artworkId;
    private int quantity;
    private double price;
    private double totalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(String artworkId) {
        this.artworkId = artworkId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(String picture_id) {
        this.picture_id = picture_id;
    }

    public OrderDetail() {
    }

    public OrderDetail(String picture_id, String title, String artworkId, int quantity, double price, double totalPrice) {
        this.picture_id=picture_id;
        this.title = title;
        this.artworkId = artworkId;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }
}
