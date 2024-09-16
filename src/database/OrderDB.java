package database;

import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDB {
    ConnectionDB connectionDB = new ConnectionDB();

    public void insertOrders(String customer_id,String date_time,String fullname,String phone,String address,int sttOrder){
        Connection conn=connectionDB.getConnection();
        String sql="insert into orders values(?,?,?,?,?,?,?,?)";
        try {
            UUID order_id=UUID.randomUUID();
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,order_id.toString());
            prs.setString(2,customer_id);
            prs.setString(3,"Chờ xác nhận");
            prs.setString(4,date_time);
            prs.setString(5,fullname);
            prs.setString(6,phone);
            prs.setString(7,address);
            prs.setInt(8,sttOrder);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String selectOrderId(String date_time,String customer_id){
        String order_id=null;
        Connection conn=connectionDB.getConnection();
        String sql="select *from orders where date_time=? and customer_id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,date_time);
            prs.setString(2,customer_id);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                order_id= rs.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order_id;
    }
    public double selectPrice(String picture_id){
        double price=0;
        Connection conn=connectionDB.getConnection();
        String sql="select price from pictures where id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,picture_id);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
               price=rs.getDouble("price");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return price;
    }
    public void insertOrderDetail(String order_id,String title,String artworkId,int quantity,double price,double totalPrice){
        Connection conn=connectionDB.getConnection();
        String sql="insert into orderDetail values(?,?,?,?,?,?,?)";
        try {
            UUID orderDetail=UUID.randomUUID();
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,orderDetail.toString());
            prs.setString(2,order_id);
            prs.setString(3,title);
            prs.setString(4,artworkId);
            prs.setInt(5,quantity);
            prs.setDouble(6,price);
            prs.setDouble(7,totalPrice);

            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Orders> selectOrderIdByWaitConfirm(String customer_id){
        ArrayList<Orders> orders=new ArrayList<>();
        Connection conn=connectionDB.getConnection();
        String sql="select *from orders where customer_id=? and status=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,customer_id);
            prs.setString(2,"Chờ xác nhận");
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                String order_id= rs.getString("id");
                Orders order=new Orders();
                order.setId(order_id);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
    public ArrayList<Orders> selectOrderIdByWaitOrder(String customer_id){
        ArrayList<Orders> orders=new ArrayList<>();
        Connection conn=connectionDB.getConnection();
        String sql="select *from orders where customer_id=? and status=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,customer_id);
            prs.setString(2,"Chờ giao hàng");
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                String order_id= rs.getString("id");
                Orders order=new Orders();
                order.setId(order_id);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
    public ArrayList<Orders> selectOrderIdByDelivering(String customer_id){
        ArrayList<Orders> orders=new ArrayList<>();
        Connection conn=connectionDB.getConnection();
        String sql="select *from orders where customer_id=? and status=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,customer_id);
            prs.setString(2,"Đang giao hàng");
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                String order_id= rs.getString("id");
                Orders order=new Orders();
                order.setId(order_id);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
    public ArrayList<Orders> selectOrderIdByDelivered(String customer_id){
        ArrayList<Orders> orders=new ArrayList<>();
        Connection conn=connectionDB.getConnection();
        String sql="select *from orders where customer_id=? and status=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,customer_id);
            prs.setString(2,"Đã giao hàng");
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                String order_id= rs.getString("id");
                Orders order=new Orders();
                order.setId(order_id);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
    public ArrayList<Orders> selectOrderIdByCancelled(String customer_id){
        ArrayList<Orders> orders=new ArrayList<>();
        Connection conn=connectionDB.getConnection();
        String sql="select *from orders where customer_id=? and status=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,customer_id);
            prs.setString(2,"Đã hủy");
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                String order_id= rs.getString("id");
                Orders order=new Orders();
                order.setId(order_id);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
    public void displayOrders(String customer_id,String order_id){
        ArrayList<CartDetail> cartDetails=new ArrayList<>();
        Connection conn=connectionDB.getConnection();
        String sql="select od.title,od.artworkId,od.quantity,od.price,od.totalPrice,o.fullname,o.phone,o.address,o.status,o.date_time,o.STT " +
                "from orderDetail od inner join orders o on od.order_id=o.id " +
                "where o.customer_id=? and o.id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,customer_id);
            prs.setString(2,order_id);
            ResultSet rs=prs.executeQuery();
            System.out.println("                    Đơn hàng của "+Customers.customers.getUsername());
            System.out.printf("%-35s%-15s%-20s%-15s%s\n","title","artworkId","price","quantity","TotalPrice");
            double totalALLPrice=0;
            String fullname=null;String phone=null;String status=null;String date_time=null;String address=null;int STT=0;
            while(rs.next()){
                String title=rs.getString("title");
                String artworkId=rs.getString("artworkId");
                double price =rs.getDouble("price");
                int quantity =rs.getInt("quantity");
                double totalPrice=rs.getDouble("totalPrice");
                STT=rs.getInt("STT");
                totalALLPrice+=totalPrice;
                fullname=rs.getString("fullname");
                phone=rs.getString("phone");
                address=rs.getString("address");
                status=rs.getString("status");
                date_time=rs.getString("date_time");
                System.out.printf("%-35s%-15s%-20s%-15s%s\n",title,artworkId,price+"VND",quantity,price*quantity+"VND");
            }
            System.out.println("Đơn hàng thứ:"+STT);
            System.out.println("Tổng tiền:"+totalALLPrice);
            System.out.println("Thời điểm đặt mua:"+date_time);
            System.out.println("Tên người nhận hàng:"+fullname);
            System.out.println("Số điện thoại:"+phone);
            System.out.println("Địa chỉ:"+address);
            System.out.println("Trạng thái:"+status);
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public String cartIdByCustomer(String customer_id){
        Connection conn=connectionDB.getConnection();
        String cartId=null;
        String sql="select id from cart where customer_id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,customer_id);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                cartId=rs.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cartId;
    }
    public int quantityByCart(String cartId,String picture_id){
        Connection conn=connectionDB.getConnection();
        int quantity=0;
        String sql="select quantity from cartDetail where cart_id=? and picture_id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,cartId);
            prs.setString(2,picture_id);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                quantity=rs.getInt("quantity");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quantity;
    }
    public void updateCancelledBySTTOrder(int sttOrder,String customer_id){
        Connection conn=connectionDB.getConnection();
        String sql="update orders set status=? where STT=? and customer_id=?";
        try {
            PreparedStatement prs =conn.prepareStatement(sql);
            prs.setString(1,"Đã hủy");
            prs.setInt(2,sttOrder);
            prs.setString(3,customer_id);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateWaitConfirmBySTTOrder(int sttOrder,String customer_id){
        Connection conn=connectionDB.getConnection();
        String sql="update orders set status=? where STT=? and customer_id=?";
        try {
            PreparedStatement prs =conn.prepareStatement(sql);
            prs.setString(1,"Chờ xác nhận");
            prs.setInt(2,sttOrder);
            prs.setString(3,customer_id);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    public Pictures displayPicture(String picture_id){
//        Pictures pictures=null;
//        Connection conn=connectionDB.getConnection();
//        String sql="select title,artworkId from pictures where id=?";
//        try {
//            PreparedStatement prs=conn.prepareStatement(sql);
//            prs.setString(1,picture_id);
//            ResultSet rs=prs.executeQuery();
//            while(rs.next()){
//                String title=rs.getString("title");
//                String artworkId=rs.getString("artworkId");
//                pictures=new Pictures();
//                pictures.setTitle(title);
//                pictures.setArtworkId(artworkId);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return pictures;
//    }
public Orders selectOrderById(String orderId){
    Orders orders=null;
    Connection conn=connectionDB.getConnection();
    String sql="select *from orders where id=?";
    try {
        PreparedStatement prs=conn.prepareStatement(sql);
        prs.setString(1,orderId);
        ResultSet rs=prs.executeQuery();
        while(rs.next()){
            String order_id= rs.getString("id");
            String customer_id=rs.getString("customer_id");
            String status=rs.getString("status");
            String date_time=rs.getString("date_time");
            orders=new Orders(order_id,customer_id,status,date_time);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return orders;
}
}
