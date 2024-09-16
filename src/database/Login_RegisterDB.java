package database;

import entity.Cart;
import entity.Customers;
import entity.Receiver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Login_RegisterDB {
    ConnectionDB connectionDB=new ConnectionDB();
    public int checkUsername(String username) {
        Connection conn = connectionDB.getConnection();
        String sql = "Select count(*) from customers where username = ?";
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
    public int checkEmail(String email) {
        Connection conn = connectionDB.getConnection();
        String sql = "Select count(*) from customers where email=?";
        int count = 0;
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, email);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public void insertCustomer(String username, String email, String password,String fullname,String phone) {
        UUID uuid = UUID.randomUUID();
        Connection conn = connectionDB.getConnection();
        String sql = "insert into customers values(?,?,?,?,?,?)";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, uuid.toString());
            prs.setString(2, username);
            prs.setString(3, email);
            prs.setString(4, password);
            prs.setString(5,fullname);
            prs.setString(6,phone);
            int result = prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public int checkPassword(String username,String password) {
        Connection conn = connectionDB.getConnection();
        String sql = "Select count(*) from customers where username= ? and password= ?";
        int count = 0;
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, username);
            prs.setString(2,password);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public int checkPhone(String phone) {
        Connection conn = connectionDB.getConnection();
        String sql = "Select count(*) from customers where phone=?";
        int count = 0;
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1,phone);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public void updatePassword(String password, String email) {
        Connection conn = connectionDB.getConnection();
        String sql = "update customers set password=? where email=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, password);
            prs.setString(2, email);
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Customers selectCustomer(String username, String password) {
        Connection conn = connectionDB.getConnection();
        Customers customer = null;
        String sql = "select *from customers where  username=? and  password=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, username);
            prs.setString(2, password);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String username1 = rs.getString("username");
                String email = rs.getString("email");
                String password1 = rs.getString("password");
                customer = new Customers(id, username1, email, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }
    public void displayPersonalInformation(String customer_id){
        Connection conn = connectionDB.getConnection();
        String sql = "select username,fullname,phone from customers where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1,customer_id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String fullname=rs.getString("fullname");
                String phone=rs.getString("phone");
                System.out.println("username:"+username);
                System.out.println("Full name:"+fullname);
                System.out.println("Phone:"+phone);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateFullname(String fullname,String customer_id) {
        Connection conn = connectionDB.getConnection();
        String sql = "update customers set fullname=? where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1,fullname);
            prs.setString(2,customer_id);
            int result = prs.executeUpdate();
            if (result == 1) {
                System.out.println("Thay đổi họ tên thành công");
            } else {
                System.out.println("Cập nhật thất bại");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updatePhone(String phone, String customer_id) {
        Connection conn = connectionDB.getConnection();
        String sql = "update customers set phone=? where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, phone);
            prs.setString(2, customer_id);
             prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
