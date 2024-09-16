package database;

import entity.Categories;
import entity.Pictures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class CategoryDB {
    ConnectionDB connectionDB=new ConnectionDB();
    public void insertTopic() {
        ArrayList<Categories> categories = new ArrayList<>();
        categories.add(new Categories("Tranh tân gia phong thủy"));
        categories.add(new Categories("Tranh trang trí"));
        categories.add(new Categories("Tranh tứ quý"));
        categories.add(new Categories("Tranh chủ đề Việt Nam"));
        Connection conn = connectionDB.getConnection();
        String sql = "insert into categories " +
                "values(?,?)";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            for (Categories category : categories) {
                UUID id = UUID.randomUUID();
                category.setId(id.toString());
                prs.setString(1, category.getId());
                prs.setString(2, category.getTopic());
                prs.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Categories> selectCategory() {
        ArrayList<Categories> categories = new ArrayList<>();
        Connection conn = connectionDB.getConnection();
        String sql = "select *from categories";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                String topic = rs.getNString("topic");
                categories.add(new Categories(topic));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
    public void insertPictures() {
        ArrayList<Pictures> pictureList = new ArrayList<>();
        //tranh tan gia phong thuy
        pictureList.add(new Pictures("Tranh sơn mài vẽ sen dát vàng", "SMVSDH1", 1000000, "Sơn Mài Việt Nam", "50x70 cm", 2.5f, 100));
        pictureList.add(new Pictures("Tranh hoa sen treo tường", "HSTT2", 2000000, "Sơn Mài Việt Nam", "60x80 cm", 3.0f, 100));
        pictureList.add(new Pictures("Tranh đĩa sơn mài thuận buồm", "DSMTB3", 1500000, "Sơn Mài Việt Nam", "70x90 cm", 3.5f, 100));
        pictureList.add(new Pictures("Tranh cô gái sen", "CGS4", 2500000, "Sơn Mài Việt Nam", "60x100 cm", 4.0f, 100));
        //tranh trang trí
        pictureList.add(new Pictures("Tranh phố cổ ", "PC5", 1200000, "Sơn Mài Việt Nam", "40x60 cm", 1.5f, 100));
        pictureList.add(new Pictures("Tranh mai vàng trúc xanh", "MVTX6", 1800000, "Sơn Mài Việt Nam", "50x70 cm", 2.0f, 100));
        pictureList.add(new Pictures("Tranh phố cổ Hà Nội", "PCHN7", 2200000, "Sơn Mài Việt Nam", "60x80 cm", 2.5f, 100));
        pictureList.add(new Pictures("Tranh sơn mài 3D cá mẫu", "SM3DCM", 2800000, "Sơn Mài Việt Nam", "70x100 cm", 3.5f, 100));
        //tranh chủ đề Việt Nam
        pictureList.add(new Pictures("Tranh đĩa sơn mài cảnh đồng quê", "SMCDQ8", 1200000, "Sơn Mài Việt Nam", "60x90 cm", 3.0f, 100));
        pictureList.add(new Pictures("Tranh hoa sen trăng nền đen", "HSTND9", 1500000, "Sơn Mài Việt Nam", "70x100 cm", 3.5f, 100));
        pictureList.add(new Pictures("Tranh đồng quê khói", "DQK11", 2000000, "Sơn Mài Việt Nam", "80x120 cm", 4.0f, 100));
        pictureList.add(new Pictures("Tranh sơn mài vỏ trai", "SMVT112", 2500000, "Sơn Mài Việt Nam", "90x130 cm", 4.5f, 100));
        //tranh tứ quý
        pictureList.add(new Pictures("Tranh tứ quý mai lan", "TQML22", 3000000, "Sơn Mài Việt Nam", "60x60 cm", 2.0f, 100));
        pictureList.add(new Pictures("Tranh cửu ngư", "CNT24", 4000000, "Sơn Mài Việt Nam", "60x60 cm", 2.0f, 100));
        pictureList.add(new Pictures("Tranh cúc trúc sơn thủy ", "CTST21", 5000000, "Sơn Mài Việt Nam", "60x60 cm", 2.0f, 100));
        pictureList.add(new Pictures("Tranh cúc trúc nền xanh", "CCTNX12", 6000000, "Sơn Mài Việt Nam", "60x60 cm", 2.0f, 100));
        Connection conn = connectionDB.getConnection();
        String sql = "insert into pictures values(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);

            for (int i = 0; i < pictureList.size(); i++) {
                Pictures pictures = pictureList.get(i);
                String category_id;
                if (i >= 0 && i <= 3) {
                    category_id = "0b399c40-3ff7-4ca2-a326-66b0e84316ba";
                } else if (i >= 4 && i < 8) {
                    category_id = "5f7485c5-c6e0-4ff1-8570-3de9cb8f8314";
                } else if (i >= 8 && i < 12) {
                    category_id = "383ecc35-8051-4a53-887a-2d3ed382b078";
                } else {
                    category_id = "e3908b05-c395-4b9a-822c-3c0859d9f6d4";
                }
                UUID id = UUID.randomUUID();
                pictures.setId(id.toString());
                prs.setString(1, pictures.getId());
                prs.setString(2, pictures.getTitle());
                prs.setString(3, pictures.getArtworkId());
                prs.setDouble(4, pictures.getPrice());
                prs.setString(5, pictures.getOrigin());
                prs.setString(6, pictures.getSize());
                prs.setFloat(7, pictures.getWeight());
                prs.setInt(8, pictures.getQuantity());
                prs.setString(9, category_id);
                prs.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectPictureByTopic(String topic) {
        Connection conn = connectionDB.getConnection();
        String sql = "select p.title,p.artworkId,p.price from pictures p inner join categories c on c.id=p.category_id where c.topic=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, topic);
            ResultSet rs = prs.executeQuery();
            System.out.printf("%-35s%-15s%s\n", "        Tiêu đề", "Mã số tranh", "Giá tiền");
            while (rs.next()) {
                String title = rs.getString("title");
                String artworkId = rs.getString("artworkId");
                Float price = rs.getFloat("price");
                System.out.printf("%-35s%-15s%s\n", title, artworkId, price);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectAllPicture() {
        Connection conn = connectionDB.getConnection();
        String sql = "select p.title,p.artworkId,p.price from pictures p inner join categories c on c.id=p.category_id";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            ResultSet rs = prs.executeQuery();
            System.out.printf("%-35s%-15s%s\n", "        Tiêu đề", "Mã số tranh", "Giá tiền");
            while (rs.next()) {
                String title = rs.getString("title");
                String artworkId = rs.getString("artworkId");
                Float price = rs.getFloat("price");
                System.out.printf("%-35s%-15s%s\n", title, artworkId, price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int checkArtworkId(String artworkId) {
        Connection conn = connectionDB.getConnection();
        String sql = "Select count(*) from pictures where artworkId=?";
        int count = 0;
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, artworkId);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public void selectPictureDetail(String artworkId) {
        Connection conn = connectionDB.getConnection();
        String sql = "select *from pictures where artworkId=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, artworkId);
            ResultSet rs = prs.executeQuery();
            System.out.printf("%-35s%-15s%-20s%-25s%-15s%s\n", "        Tiêu đề", "Mã số tranh", "Giá tiền", "Xuất xứ", "kích thước", "khối lượng");
            while (rs.next()) {
                String id=rs.getString("id");
                String title = rs.getString("title");
                String artworkId1 = rs.getString("artworkId");
                Float price = rs.getFloat("price");
                String origin = rs.getString("origin");
                String size = rs.getString("size");
                String weight = rs.getString("weight");
                System.out.printf("%-35s%-15s%-20s%-25s%-15s%s\n", title, artworkId1, price + "VND", origin, size, weight);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String  selectTitle(String picture_id) {
        Connection conn = connectionDB.getConnection();
        String title=null;
        String sql = "select  title from pictures where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, picture_id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                title = rs.getString("title");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return title;
    }
    public String selectArtworkId(String picture_id) {
        Connection conn = connectionDB.getConnection();
        String artworkId=null;
        String sql = "select artworkId from pictures where id=?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1, picture_id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                artworkId = rs.getString("artworkId");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artworkId;
    }
    public void selectPictureByKeyWork(String keyword) {
        Connection conn = connectionDB.getConnection();
        String sql = "select title,artworkId,price from pictures where title like ?";
        try {
            PreparedStatement prs = conn.prepareStatement(sql);
            prs.setString(1,"%"+ keyword+"%");
            ResultSet rs = prs.executeQuery();
            System.out.printf("%-35s%-15s%s\n", "        Tiêu đề", "Mã số tranh", "Giá tiền");
            while (rs.next()) {
                String title = rs.getString("title");
                String artworkId = rs.getString("artworkId");
                Float price = rs.getFloat("price");
                System.out.printf("%-35s%-15s%s\n", title, artworkId, price);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
