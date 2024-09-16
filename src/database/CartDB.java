package database;

import entity.Cart;
import entity.CartDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class CartDB {
    ConnectionDB connectionDB=new ConnectionDB();
    public void insertToCart(String customer_id){
        Connection conn=connectionDB.getConnection();
        Cart cart = new Cart();
        String sql2 = "insert into cart values(?,?)";
        try {
            PreparedStatement prs = conn.prepareStatement(sql2);
            UUID cartId = UUID.randomUUID();
            cart.setId(cartId.toString());
            prs.setString(1, cart.getId());
            prs.setString(2, customer_id);
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertToCartDetail(String cart_id,String picture_id) {
        Connection conn = connectionDB.getConnection();
        CartDetail cartDetail=new CartDetail();
        String sql = "insert into cartDetail values(?,?,?,?)";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            UUID idCartDetail = UUID.randomUUID();
            cartDetail.setId(idCartDetail.toString());
            prs.setString(1,cartDetail.getId());
            prs.setInt(2,1);
            prs.setString(3,cart_id);
            prs.setString(4,picture_id);
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String selectIdCartByIdCustomer(String idCustomer) {
        Connection conn = connectionDB.getConnection();
        String id=null;
        String sql = "Select *from cart where customer_id=?";
        int count = 0;
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, idCustomer);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                id = rs.getString("id");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public String idPictureByArtworkId(String artworkId) {
        String id=null;
        Connection conn = connectionDB.getConnection();
        String sql = "select *from pictures where artworkId=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, artworkId);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                id=rs.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public int checkPictureInCart(String picture_id,String cart_id){
        int count=0;
        Connection conn=connectionDB.getConnection();
        String sql="select count(*) from cartDetail where picture_id=? and cart_id=?";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,picture_id);
            prs.setString(2,cart_id);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                count=rs.getInt("");
            }
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public void updateQuantityInCart(String picture_id,String cart_id){
        Connection conn=connectionDB.getConnection();
        String sql="update cartDetail set quantity=quantity+1 where picture_id=? and cart_id=?";
        try {
            PreparedStatement prs =conn.prepareStatement(sql);
            prs.setString(1,picture_id);
            prs.setString(2,cart_id);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void displayCart(String cart_id){
        ArrayList<CartDetail> cartDetails=new ArrayList<>();
        Connection conn=connectionDB.getConnection();
        String sql="select p.title,p.artworkId,p.price,c.quantity " +
                "from cartDetail c inner join pictures p on p.id=c.picture_id where c.cart_id=? ";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,cart_id);
            ResultSet rs=prs.executeQuery();
            System.out.printf("%-35s%-15s%-20s%-15s%s\n","title","artworkId","price","quantity","totalMoney");
            while(rs.next()){
                String title=rs.getString("title");
                String artworkId=rs.getString("artworkId");
                double price =rs.getDouble("price");
                int quantity =rs.getInt("quantity");
                System.out.printf("%-35s%-15s%-20s%-15s%s\n",title,artworkId,price,quantity,price*quantity);
            }
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateQuantityInCart(int quantity,String picture_id,String cart_id){
        Connection conn=connectionDB.getConnection();
        String sql="update cartDetail set quantity=? where picture_id=? and cart_id=?";
        try {
            PreparedStatement prs =conn.prepareStatement(sql);
            prs.setInt(1,quantity);
            prs.setString(2,picture_id);
            prs.setString(3,cart_id);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deletePictureInCart(String picture_id,String cart_id){
        Connection conn=connectionDB.getConnection();
        String sql="delete from cartDetail where cart_id=? and picture_id=? ";
        try {
            PreparedStatement prs=conn.prepareStatement(sql);
            prs.setString(1,cart_id);
            prs.setString(2,picture_id);
            prs.executeUpdate();
            connectionDB.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
