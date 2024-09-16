package entity;


public class CartDetail {
    private String id;
    private String cart_id;
    private String product_id;
    private int quantity;
    private double totalMoney;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public CartDetail(String id, String cart_id, String product_id, int quantity, double totalMoney) {
        this.id = id;
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.totalMoney = totalMoney;
    }

    public CartDetail() {
    }
}
