package database;

import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class AdminDB {
    ConnectionDB connectionDB = new ConnectionDB();

    public int checkUserAdmin(String username) {
        Connection conn = connectionDB.getConnection();
        String sql = "Select count(*) from admin where username = ?";
        int count = 0;
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, username);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public int checkPassword(String username, String password) {
        Connection conn = connectionDB.getConnection();
        String sql = "Select count(*) from admin where username= ? and password= ?";
        int count = 0;
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, username);
            prs.setString(2, password);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public Admin selectAdmin(String username, String password) {
        Connection conn = connectionDB.getConnection();
        Admin admin = null;
        String sql = "select *from admin where  username=? and  password=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, username);
            prs.setString(2, password);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String username1 = rs.getString("username");
                String password1 = rs.getString("password");
                admin = new Admin(id, username1, password1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admin;
    }

    public void displayOrder() {
        Connection conn = connectionDB.getConnection();
        String sql = "select id,customer_id,date_time,status from orders where status!=? and status!=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, "Đã giao hàng");
            prs.setString(2,"Đã hủy");
            ResultSet rs = prs.executeQuery();
            System.out.printf("%-42s%-42s%-42s%s\n", "            Mã Đơn Hàng", "           Mã Khách Hàng", "        Thời Gian Đặt Hàng", "Trạng Thái");
            while (rs.next()) {
                String id = rs.getString("id");
                String customer_id = rs.getString("customer_id");
                String date_time = rs.getString("date_time");
                String status = rs.getString("status");
                System.out.printf("%-42s%-42s%-42s%s\n", id, customer_id, date_time, status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkOrder_id(String order_id) {
        Connection conn = connectionDB.getConnection();
        String sql = "Select count(*) from orders where id=?";
        int count = 0;
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, order_id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public Orders selectOrderById(String orderId) {
        Orders orders = null;
        Connection conn = connectionDB.getConnection();
        String sql = "select *from orders where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, orderId);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                String order_id = rs.getString("id");
                String customer_id = rs.getString("customer_id");
                String status = rs.getString("status");
                String date_time = rs.getString("date_time");
                orders = new Orders(order_id, customer_id, status, date_time);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    public boolean updateStatus(String order_id,String status) {
        Connection conn = connectionDB.getConnection();
        boolean result=false;
        String sql = "update orders set status=? where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, status);
            prs.setString(2, order_id);
            int status1=prs.executeUpdate();
            if(status1==1){
                result=true;
            }
            connectionDB.closeConnection(conn);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public void updateStatusDelivering(String order_id) {
//        Connection conn = connectionDB.getConnection();
//        String sql = "update orders set status=? where id=?";
//        try {
//            PreparedStatement prs = conn.prepareStatement(sql);
//            prs.setString(1, "Đang giao hàng");
//            prs.setString(2, order_id);
//            prs.executeUpdate();
//            connectionDB.closeConnection(conn);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public void updateStatusDelivered(String order_id) {
//        Connection conn = connectionDB.getConnection();
//        String sql = "update orders set status=? where id=?";
//        try {
//            PreparedStatement prs = conn.prepareStatement(sql);
//            prs.setString(1, "Đã giao hàng");
//            prs.setString(2, order_id);
//            prs.executeUpdate();
//            connectionDB.closeConnection(conn);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void displayOrderDetail(String order_id) {
        ArrayList<CartDetail> cartDetails = new ArrayList<>();
        Connection conn = connectionDB.getConnection();
        String sql = "select od.title,od.artworkId,od.quantity,od.price,od.totalPrice,o.customer_id,o.fullname,o.phone,o.address,o.status,o.date_time,o.STT " +
                "from orderDetail od inner join orders o on od.order_id=o.id " +
                "where o.id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, order_id);
            ResultSet rs = prs.executeQuery();
            System.out.printf("%-35s%-15s%-20s%-15s%s\n", "title", "artworkId", "price", "quantity", "TotalPrice");
            double totalALLPrice = 0;
            String fullname = null;
            String phone = null;
            String status = null;
            String date_time = null;
            String address = null;
            int STT = 0;
            String customer_id = null;
            while (rs.next()) {
                customer_id = rs.getString("customer_id");
                String title = rs.getString("title");
                String artworkId = rs.getString("artworkId");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                double totalPrice = rs.getDouble("totalPrice");
                STT = rs.getInt("STT");
                totalALLPrice += totalPrice;
                fullname = rs.getString("fullname");
                phone = rs.getString("phone");
                address = rs.getString("address");
                status = rs.getString("status");
                date_time = rs.getString("date_time");
                System.out.printf("%-35s%-15s%-20s%-15s%s\n", title, artworkId, price + "VND", quantity, price * quantity + "VND");
            }
            System.out.println("Mã Khách Hàng:" + customer_id);
            System.out.println("Đơn hàng thứ:" + STT);
            System.out.println("Tổng tiền:" + totalALLPrice);
            System.out.println("Thời điểm đặt mua:" + date_time);
            System.out.println("Tên người nhận hàng:" + fullname);
            System.out.println("Số điện thoại:" + phone);
            System.out.println("Địa chỉ:" + address);
            System.out.println("Trạng thái:" + status);
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<OrderDetail> selectQuantityAndArtworkId(ArrayList<String> arrayListOrderId){
        ArrayList<OrderDetail> orderDetailArrayList=new ArrayList<>();
        OrderDetail orderDetail=null;
        Connection conn=connectionDB.getConnection();

        String sql="select sum(quantity) as totalQuantity,artworkId from orderDetail " +
                "where order_id in(";
        ArrayList<String> questionMark=new ArrayList<>();
        for(String x:arrayListOrderId){
            questionMark.add("?");
        }
        String joinQuestion=String.join(",",questionMark);
        sql=sql+joinQuestion+")"+" group by artworkId";

        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            int i=0;
            for(String x:arrayListOrderId){
                i=i+1;
                prs.setString(i,x);
            }
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                int sellQuantity=rs.getInt("totalQuantity");
                String artworkId=rs.getString("artworkId");
                orderDetail=new OrderDetail();
                orderDetail.setQuantity(sellQuantity);
                orderDetail.setArtworkId(artworkId);
                orderDetailArrayList.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDetailArrayList;
    }
//    public ArrayList<OrderDetail> selectQuantityAndArtworkId(){
//        ArrayList<OrderDetail> orderDetailArrayList=new ArrayList<>();
//        OrderDetail orderDetail=null;
//        Connection conn=connectionDB.getConnection();
//        String sql="select od.quantity,od.artworkId from orderDetail od inner join orders o on o.id=od.order_id " +
//                "where o.status=? or o.status=? or o.status=?";
//        try {
//            PreparedStatement prs=conn.prepareStatement(sql);
//            prs.setString(1,"Chờ giao hàng");
//            prs.setString(2,"Đang giao hàng");
//            prs.setString(3,"Đã giao hàng");
//            ResultSet rs=prs.executeQuery();
//            while(rs.next()){
//                int sellQuantity=rs.getInt("quantity");
//                String artworkId=rs.getString("artworkId");
//                orderDetail=new OrderDetail();
//                orderDetail.setQuantity(sellQuantity);
//                orderDetail.setArtworkId(artworkId);
//                orderDetailArrayList.add(orderDetail);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return orderDetailArrayList;
//    }
    public void updateQuantity(int sellQuantity,String artworkId){
        Connection conn=connectionDB.getConnection();
        String sql="update pictures set quantity=quantity-? where artworkId=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setInt(1,sellQuantity);
            prs.setString(2,artworkId);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void displayPictureDetail() {
        Connection conn = connectionDB.getConnection();
        String sql = "select *from pictures";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            ResultSet rs = prs.executeQuery();
            System.out.printf("%-35s%-15s%-20s%-25s%-15s%-15s%s\n", "        Tiêu đề", "Mã số tranh", "Giá tiền", "Xuất xứ", "kích thước", "khối lượng","Số lượng");
            while (rs.next()) {
                String id=rs.getString("id");
                String title = rs.getString("title");
                String artworkId1 = rs.getString("artworkId");
                Float price = rs.getFloat("price");
                String origin = rs.getString("origin");
                String size = rs.getString("size");
                String weight = rs.getString("weight");
                int quantity=rs.getInt("quantity");
                System.out.printf("%-35s%-15s%-20s%-25s%-15s%-15s%s\n", title, artworkId1, price + "VND", origin, size, weight,quantity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String selectTopicId(String topic){
        Connection conn=connectionDB.getConnection();
        String sql="select id from categories where topic=?";
        String id=null;
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,topic);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                id=rs.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public void insertPicture(String title,String artworkId,double price,String origin,String size,double weight,int quantity,String topicId){
        Connection conn=connectionDB.getConnection();
        String sql="insert into pictures values(?,?,?,?,?,?,?,?,?)";
        try {
            UUID id=UUID.randomUUID();
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,id.toString());
            prs.setString(2,title);
            prs.setString(3,artworkId);
            prs.setDouble(4,price);
            prs.setString(5,origin);
            prs.setString(6,size);
            prs.setDouble(7,weight);
            prs.setInt(8,quantity);
            prs.setString(9,topicId);
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateQuantityByArtworkId(int quantity,String artworkId){
        Connection conn=connectionDB.getConnection();
        String sql="update pictures set quantity=? where artworkId=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setInt(1,quantity);
            prs.setString(2,artworkId);
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String selectPictureId(String artworkId){
        Connection conn=connectionDB.getConnection();
        String sql="select id from pictures where artworkId=?";
        String id=null;
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,artworkId);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                id=rs.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public int checkPictureIdByCartDetail(String picture_id){
        Connection conn=connectionDB.getConnection();
        int result=0;
        String sql="select count(*) from cartDetail where picture_id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,picture_id);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
               result=rs.getInt("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public void deletePictureInCart(String picture_id){
        Connection conn=connectionDB.getConnection();
        String sql="delete cartDetail where picture_id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,picture_id);
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deletePicture(String artworkId){
        Connection conn=connectionDB.getConnection();
        String sql="delete pictures where artworkId=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,artworkId);
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> selectAllOrder_id(){
        ArrayList<String> ordersArrayList=new ArrayList<>();
        Connection conn=connectionDB.getConnection();
        String sql="select id from orders where status=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,"Đã giao hàng");
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                String id=rs.getString("id");
                ordersArrayList.add(id);
            }
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersArrayList;
    }
    public void displaySellPictures( ArrayList<String> orders_id ){
        Connection conn=connectionDB.getConnection();
        String sql="select title,artworkId,SUM(quantity) as totalQuantity, price,SUM(totalPrice) as totalPrice " +
                "from orderDetail " +
                "where order_id in (";
        ArrayList<String> questionMark=new ArrayList<>();
        for(String x:orders_id){
            questionMark.add("?");
        }
        String joinQuestion=String.join(",",questionMark);
        sql=sql+joinQuestion+") "+"group by title,artworkId,price";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            int i=0;
            for(String x:orders_id){
                i=i+1;
                prs.setString(i,x);
            }
            ResultSet rs=prs.executeQuery();
            System.out.printf("%-35s%-15s%-15s%-15s%s\n","Title","ArtworkId","Quantity","Price","Total Price");
            while(rs.next()){
                String title=rs.getString("title");
                String artworkId=rs.getString("artworkId");
                int quantity=rs.getInt("totalQuantity");
                double price=rs.getDouble("price");
                double totalPrice=rs.getDouble("totalPrice");
                System.out.printf("%-35s%-15s%-15s%-15s%s\n",title,artworkId,quantity,price,totalPrice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

