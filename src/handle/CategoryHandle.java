package handle;

import database.CategoryDB;
import entity.Customers;
import view.HomePageUser;

import java.util.Scanner;

public class CategoryHandle {
    CategoryDB categoryDB = new CategoryDB();
    CartHandle cartHandle=new CartHandle();

    public void menuCategories(Scanner sc) {
        HomePageUser page = new HomePageUser();
        System.out.println("                    Danh mục tranh");
        System.out.println("1.Tranh chủ đề Việt Nam");
        System.out.println("2.Tranh tứ quý");
        System.out.println("3.Tranh trang trí");
        System.out.println("4.Tranh tân gia phong thủy");
        System.out.println("5.Quay lại trang chủ");
        int choice = page.choice(sc);
        switch (choice) {
            case 1:
                System.out.println("                Tranh chủ đề Việt Nam");
                this.PictureByTopic1(sc);
                break;
            case 2:
                System.out.println("                Tranh tứ quý");
                this.PictureByTopic2(sc);
                break;
            case 3:
                System.out.println(                 "Tranh trang trí");
                this.PictureByTopic3(sc);
                break;
            case 4:
                System.out.println(                 "Tranh tân gia phong thủy");
                this.PictureByTopic4(sc);
                break;
            case 5:
                page.homePage(sc);
                break;
            default:
                System.err.println("Yêu cầu bạn lựa chọn đúng");
                break;
        }
    }

    public void PictureByTopic1(Scanner sc) {
        HomePageUser page = new HomePageUser();
        categoryDB.selectPictureByTopic("Tranh chủ đề Việt Nam");
        System.out.println("1.Xem chi tiết tranh");
        System.out.println("2.Quay lại danh mục tranh");
        int choice1 = page.choice(sc);
        switch (choice1) {
            case 1:
                boolean artworkI = false;
                while (!artworkI) {
                    System.out.println("Mời bạn nhập mã số tranh:");
                    String artworkId = sc.nextLine();
                    int checkArtworkId = categoryDB.checkArtworkId(artworkId);
                    if (checkArtworkId == 0) {
                        System.err.println("Mã số tranh không tồn tại!Vui lòng nhập đúng mã số tranh ");
                    } else {
                        artworkI = true;
                        this.PictureDetail(artworkId, sc);
                    }
                }
                break;
            case 2:
                this.menuCategories(sc);
                break;
            default:
                System.out.println("Yêu cầu bạn lựa chọn đúng");
                break;
        }
    }
    public void PictureByTopic2(Scanner sc) {
        HomePageUser page = new HomePageUser();
        categoryDB.selectPictureByTopic("Tranh tứ quý");
        System.out.println("1.Xem chi tiết tranh");
        System.out.println("2.Quay lại danh mục tranh");
        int choice1 = page.choice(sc);
        switch (choice1) {
            case 1:
                boolean artworkI = false;
                while (!artworkI) {
                    System.out.println("Mời bạn nhập mã số tranh:");
                    String artworkId = sc.nextLine();
                    int checkArtworkId = categoryDB.checkArtworkId(artworkId);
                    if (checkArtworkId == 0) {
                        System.err.println("Mã số tranh không tồn tại!Vui lòng nhập đúng mã số tranh ");
                    } else {
                        artworkI = true;
                        this.PictureDetail(artworkId, sc);
                    }
                }
                break;
            case 2:
                this.menuCategories(sc);
                break;
            default:
                System.out.println("Yêu cầu bạn lựa chọn đúng");
                break;
        }
    }
    public void PictureByTopic3(Scanner sc) {
        HomePageUser page = new HomePageUser();
        categoryDB.selectPictureByTopic("Tranh trang trí");
        System.out.println("1.Xem chi tiết tranh");
        System.out.println("2.Quay lại danh mục tranh");
        int choice1 = page.choice(sc);
        switch (choice1) {
            case 1:
                boolean artworkI = false;
                while (!artworkI) {
                    System.out.println("Mời bạn nhập mã số tranh:");
                    String artworkId = sc.nextLine();
                    int checkArtworkId = categoryDB.checkArtworkId(artworkId);
                    if (checkArtworkId == 0) {
                        System.err.println("Mã số tranh không tồn tại!Vui lòng nhập đúng mã số tranh ");
                    } else {
                        artworkI = true;
                        this.PictureDetail(artworkId, sc);
                    }
                }
                break;
            case 2:
                this.menuCategories(sc);
                break;
            default:
                System.out.println("Yêu cầu bạn lựa chọn đúng");
                break;
        }
    }
    public void PictureByTopic4(Scanner sc) {
        HomePageUser page = new HomePageUser();
        categoryDB.selectPictureByTopic("Tranh tân gia phong thủy");
        System.out.println("1.Xem chi tiết tranh");
        System.out.println("2.Quay lại danh mục tranh");
        int choice1 = page.choice(sc);
        switch (choice1) {
            case 1:
                boolean artworkI = false;
                while (!artworkI) {
                    System.out.println("Mời bạn nhập mã số tranh:");
                    String artworkId = sc.nextLine();
                    int checkArtworkId = categoryDB.checkArtworkId(artworkId);
                    if (checkArtworkId == 0) {
                        System.err.println("Mã số tranh không tồn tại!Vui lòng nhập đúng mã số tranh ");
                    } else {
                        artworkI = true;
                        this.PictureDetail(artworkId, sc);
                    }
                }
                break;
            case 2:
                this.menuCategories(sc);
                break;
            default:
                System.out.println("Yêu cầu bạn lựa chọn đúng");
                break;
        }
    }
    public void allPicture(Scanner sc) {
        HomePageUser page = new HomePageUser();
        categoryDB.selectAllPicture();
        System.out.println("1.Xem chi tiết tranh");
        System.out.println("2.Quay lại ");
        int choice1 = page.choice(sc);
        switch (choice1) {
            case 1:
                boolean artworkI = false;
                while (!artworkI) {
                    System.out.println("Mời bạn nhập mã số tranh:");
                    String artworkId = sc.nextLine();
                    int checkArtworkId = categoryDB.checkArtworkId(artworkId);
                    if (checkArtworkId == 0) {
                        System.err.println("Mã số tranh không tồn tại!Vui lòng nhập đúng mã số tranh ");
                    } else {
                        artworkI = true;
                        this.PictureDetail(artworkId, sc);
                    }
                }
                break;
            case 2:
                HomePageUser homePageHandle=new HomePageUser();
                homePageHandle.homePage(sc);
                break;
            default:
                System.out.println("Yêu cầu bạn lựa chọn đúng");
                break;
        }
    }
    public void PictureDetail(String artworkId, Scanner sc) {
        HomePageUser page = new HomePageUser();
        categoryDB.selectPictureDetail(artworkId);
        System.out.println("1.Thêm tranh vào giỏ hàng");
        System.out.println("2.Mua ngay");
        System.out.println("3.Quay lại");
        int choice2 = page.choice(sc);
        switch (choice2) {
            case 1:
                if(Customers.customers==null){
                    System.out.println("Bạn cần đăng nhập trước khi thêm tranh vào giỏ hàng");
                    this.PictureDetail(artworkId,sc);
                }else{
                    boolean check = cartHandle.insertToCartDetail(artworkId);
                    if (check) {
                        this.PictureDetail(artworkId, sc);
                    }
                }

                break;
            case 2:
                if(Customers.customers==null){
                    System.out.println("Bạn cần đăng nhập trước khi mua hàng");
                    this.PictureDetail(artworkId,sc);
                }else{
                    OrderHandle orderHandle=new OrderHandle();
                    orderHandle.buyNow(sc,artworkId);
                }
                break;
            case 3:
                HomePageUser homePageHandle=new HomePageUser();
                homePageHandle.homePage(sc);
                break;
            default:
                System.out.println("Yêu cầu bạn lựa chọn đúng");
                break;
        }
    }
    public void PictureByKeyword(Scanner sc){
        HomePageUser page = new HomePageUser();
        System.out.println("Mời bạn nhập từ khóa cần tìm:");
        String keyword=sc.nextLine();
        categoryDB.selectPictureByKeyWork(keyword);
        System.out.println("1.Xem chi tiết tranh");
        System.out.println("2.Quay lại ");
        int choice1 = page.choice(sc);
        switch (choice1) {
            case 1:
                boolean artworkI = false;
                while (!artworkI) {
                    System.out.println("Mời bạn nhập mã số tranh:");
                    String artworkId = sc.nextLine();
                    int checkArtworkId = categoryDB.checkArtworkId(artworkId);
                    if (checkArtworkId == 0) {
                        System.err.println("Mã số tranh không tồn tại!Vui lòng nhập đúng mã số tranh ");
                    } else {
                        artworkI = true;
                        this.PictureDetail(artworkId, sc);
                    }
                }
                break;
            case 2:
                HomePageUser homePageHandle=new HomePageUser();
                homePageHandle.homePage(sc);
                break;
            default:
                System.out.println("Yêu cầu bạn lựa chọn đúng");
                break;
        }
    }
}