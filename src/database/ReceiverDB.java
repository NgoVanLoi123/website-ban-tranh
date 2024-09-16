package database;

import entity.Receiver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ReceiverDB {
    ConnectionDB connectionDB=new ConnectionDB();
    public void insertReceiverHaveStatus(String customer_id,String fullname,String phone,String address,int sttReceiver){
        Connection conn=connectionDB.getConnection();
        String sql="insert into receiver values(?,?,?,?,?,?,?)";
        try {
            Receiver receiver=new Receiver();
            UUID receiver_id=UUID.randomUUID();
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,receiver_id.toString());
            prs.setString(2,customer_id);
            prs.setString(3,fullname);
            prs.setString(4,phone);
            prs.setString(5,address);
            prs.setString(6,"Mặc định");
            prs.setInt(7,sttReceiver);
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertReceiverNoStatus(String customer_id,String fullname,String phone,String address,int sttReceiver){
        Connection conn=connectionDB.getConnection();
        String sql="insert into receiver values(?,?,?,?,?,?,?)";
        try {
            Receiver receiver=new Receiver();
            UUID receiver_id=UUID.randomUUID();
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,receiver_id.toString());
            prs.setString(2,customer_id);
            prs.setString(3,fullname);
            prs.setString(4,phone);
            prs.setString(5,address);
            prs.setString(6,"");
            prs.setInt(7,sttReceiver);
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int checkStatusReceiver(String customer_id){
        Connection conn = connectionDB.getConnection();
        String sql = "Select count(*) from receiver where status=? and customer_id=?";
        int count = 0;
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, "Mặc định");
            prs.setString(2,customer_id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public void displayReceiverHaveStatus(String customer_id){
        Connection conn=connectionDB.getConnection();
        String sql="select fullname,phone,address from receiver where status=? and customer_id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,"Mặc định");
            prs.setString(2,customer_id);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                String fullname=rs.getString("fullname");
                String phone=rs.getString("phone");
                String address=rs.getString("address");
                System.out.println("Full Name:"+fullname);
                System.out.println("Phone:"+phone);
                System.out.println("Address:"+address);
            }
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void displayReceiverByReceiverId(String receiver_id){
        Connection conn=connectionDB.getConnection();
        String sql="select fullname,phone,address from receiver where id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,receiver_id);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                String fullname=rs.getString("fullname");
                String phone=rs.getString("phone");
                String address=rs.getString("address");
                System.out.println("Full Name:"+fullname);
                System.out.println("Phone:"+phone);
                System.out.println("Address:"+address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateReceiverByReceiverId(String fullname,String phone,String address,String receiver_id){
        Connection conn=connectionDB.getConnection();
        String sql="update receiver set fullname=?,phone=?,address=? where id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,fullname);
            prs.setString(2,phone);
            prs.setString(3,address);
            prs.setString(4,receiver_id);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteReceiverByReceiverId(String receiver_id){
        Connection conn=connectionDB.getConnection();
        String sql="delete from receiver where id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,receiver_id);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    public String selectReceiverIdByCustomer(String customer_id){
//        Connection conn=connectionDB.getConnection();
//        String receverId=null;
//        String sql="select id from receiver where customer_id=?";
//        try {
//            PreparedStatement prs=conn.prepareStatement(sql);
//            prs.setString(1,customer_id);
//            ResultSet rs=prs.executeQuery();
//            while(rs.next()){
//                receverId=rs.getString("id");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return receverId;
//    }
    public String selectReceiverIdHaveStatus(String customer_id) {
        String id=null;
        Connection conn = connectionDB.getConnection();
        String sql = "select *from receiver where status=? and customer_id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, "Mặc định");
            prs.setString(2,customer_id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                id=rs.getString("id");
            }
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public String selectReceiverIdNoStatus(String customer_id,String fullname,String phone,String address) {
        String id=null;
        Connection conn = connectionDB.getConnection();
        String sql = "select *from receiver where customer_id=? and fullname=? and phone=? and address=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, customer_id);
            prs.setString(2,fullname);
            prs.setString(3,phone);
            prs.setString(4,address);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                id=rs.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public void updateReceiverToNoStatus(String customer_id){
        Connection conn = connectionDB.getConnection();
        String sql = "update receiver set status=? where status=? and customer_id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, "");
            prs.setString(2,"Mặc định");
            prs.setString(3,customer_id);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateToHaveStatusById(String receiver_id){
        Connection conn = connectionDB.getConnection();
        String sql = "update receiver set status=? where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, "Mặc định");
            prs.setString(2,receiver_id);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectAllReceiverByCustomer(String customer_id){
        Connection conn=connectionDB.getConnection();
        String sql="select STT,fullname,phone,address,status from receiver where customer_id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,customer_id);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                int stt=rs.getInt("STT");
                String fullname=rs.getString("fullname");
                String phone=rs.getString("phone");
                String address=rs.getString("address");
                String status=rs.getString("status");
                System.out.println("Địa chỉ thứ:"+stt);
                System.out.println("Full name:"+fullname);
                System.out.println("Phone:"+phone);
                System.out.println("Address:"+address);
                System.out.println("Status:"+status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String selectReceiverIdBySTT(int stt,String customer_id) {
        String id=null;
        Connection conn = connectionDB.getConnection();
        String sql = "select *from receiver where STT=? and customer_id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setInt(1, stt);
            prs.setString(2,customer_id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                id=rs.getString("id");
            }
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public String selectFullNameByReceiverId(String receiver_id) {
        String fullname=null;
        Connection conn = connectionDB.getConnection();
        String sql = "select fullname from receiver where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
           prs.setString(1,receiver_id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                fullname=rs.getString("fullname");
            }
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fullname;
    }
    public String selectPhoneByReceiverId(String receiver_id) {
        String phone=null;
        Connection conn = connectionDB.getConnection();
        String sql = "select phone from receiver where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1,receiver_id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                phone=rs.getString("phone");
            }
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return phone;
    }
    public String selectAddressByReceiverId(String receiver_id) {
        String address=null;
        Connection conn = connectionDB.getConnection();
        String sql = "select address from receiver where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1,receiver_id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                address=rs.getString("address");
            }
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return address;
    }
}
