package entity;

import java.util.ArrayList;

public class Cart {
    private String id;
    private double total_amount;
    private String customer_id;
    private ArrayList<CartDetail> cartDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public Cart(String id, String customer_id) {
        this.id = id;
        this.customer_id = customer_id;
    }

    public Cart() {
    }
}
