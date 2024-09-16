package view;

import database.AdminDB;
import handle.AdminHandle;
import view.HomePageUser;

import java.util.Scanner;

public class PageAdmin {
    public void homePage(Scanner sc){
        System.out.println("              Trang chủ Admin");
        System.out.println("1.Duyệt đơn hàng");
        System.out.println("2.Quản lý sản phẩm");
        System.out.println("3.Đăng xuất");
        HomePageUser homePageHandle=new HomePageUser();
        int choice =homePageHandle.choice(sc);
        AdminDB adminDB=new AdminDB();
        AdminHandle adminHandle=new AdminHandle();
        switch (choice){
            case 1:
                boolean check=false;
                while(!check){
                    adminDB.displayOrder();
                    System.out.println("1.Xem chi tiết đơn hàng trước khi duyệt");
                    System.out.println("2.Duyệt đơn hàng");
                    System.out.println("3.Quay lại");
                    int choice1=homePageHandle.choice(sc);
                    switch (choice1){
                        case 1:
                            System.out.println("Mời bạn nhập mã đơn hàng:");
                            String order_id=sc.nextLine();
                            adminDB.displayOrderDetail(order_id);
                            System.out.println("1.Quay lại");
                            int choice2=homePageHandle.choice(sc);
                            if(choice2==1){
                                check=false;
                            }
                            break;
                        case 2:
                            adminHandle.orderBrowsing(sc);
                            break;
                        case 3:
                            this.homePage(sc);
                            break;
                    }
                }
                break;
            case 2:
                adminHandle.pictureManager(sc);
                break;
            case 3:
                HomePageUser homePageUser=new HomePageUser();
                homePageUser.homePage(sc);
        }
    }
}
