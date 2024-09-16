package entity;

import java.sql.Date;

public class Orders {
    private String id;
    private String codeOrder;
    private String customer_id;
    private String status;
    private String date_time;
    private String receiverDetail_id;
    public static int sttOrder=0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getReceiverDetail_id() {
        return receiverDetail_id;
    }

    public void setReceiverDetail_id(String receiverDetail_id) {
        this.receiverDetail_id = receiverDetail_id;
    }

    public String getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder;
    }

    public Orders() {
    }

    public Orders(String id, String customer_id, String status, String date_time) {
        this.id = id;
        this.customer_id = customer_id;
        this.status = status;
        this.date_time = date_time;
    }
}
