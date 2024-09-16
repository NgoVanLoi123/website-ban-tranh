package view;

import database.Login_RegisterDB;
import database.OrderDB;
import database.ReceiverDB;
import entity.Customers;
import entity.Receiver;
import handle.*;

import java.util.Scanner;

public class HomePageUser {
    CategoryHandle categoryHandle = new CategoryHandle();
    CartHandle cartHandle = new CartHandle();




    public void homePage(Scanner sc) {

        System.out.println("                    Tranh sơn mài Việt Nam");
        System.out.println("1.Tài khoản");
        System.out.println("2.Tất cả sản phẩm");
        System.out.println("3.Danh mục sản phẩm");
        System.out.println("4.Tìm kiếm sản phẩm theo tên");
        System.out.println("5.Giỏ hàng");
        System.out.println("6.Đơn hàng của tôi");
        int choice = this.choice(sc);
        switch (choice) {
            case 1:
                int tk = 0;
                do {
                    if (Customers.customers != null) {
                        System.out.println("1.Thông tin tài khoản");
                        System.out.println("2.Sổ địa chỉ");
                        System.out.println("3.Đăng xuất");
                        System.out.println("4.Quay lại");
                        int choice1 = this.choice(sc);
                        if (choice1 == 1) {
                            boolean check = false;
                            while (!check) {
                                Login_RegisterDB loginRegisterDB = new Login_RegisterDB();
                                loginRegisterDB.displayPersonalInformation(Customers.customers.getId());
                                System.out.println("1.Thay đổi full name");
                                System.out.println("2.Cập nhật số điện thoại");
                                System.out.println("3.Đổi mật khẩu");
                                System.out.println("4.Quay lại");
                                int choice2 = this.choice(sc);
                                if (choice2 == 1) {
                                    System.out.println("Mời bạn nhập họ tên mới");
                                    String fullname = sc.nextLine();
                                    loginRegisterDB.updateFullname(fullname, Customers.customers.getId());
                                    check = false;
                                } else if (choice2 == 2) {
                                    int dem = 0;
                                    do {
                                        ReceiverHandle receiverHandle = new ReceiverHandle();
                                        String phone = receiverHandle.phone(sc);
                                        int checkPhone = loginRegisterDB.checkPhone(phone);
                                        if (checkPhone == 1) {
                                            System.err.println("Số điện thoại đã tồn tại.Vui lòng nhập số điện thoại mới");
                                            dem = 1;
                                        } else {
                                            loginRegisterDB.updatePhone(phone, Customers.customers.getId());
                                            check = false;
                                        }
                                    } while (dem == 1);


                                } else if (choice2 == 3) {
                                    int tmp = 0;
                                    do {
                                        System.out.println("Mời bạn nhập email :");
                                        String email = sc.nextLine();
                                        int checkEmail = loginRegisterDB.checkEmail(email);
                                        if (checkEmail == 0) {
                                            System.err.println("Email không đúng.Vui lòng kiểm tra lại");
                                            tmp = 1;
                                        } else {
                                            Login_RegisterHandle loginRegisterHandle = new Login_RegisterHandle();
                                            String password1 = loginRegisterHandle.Password(sc);
                                            loginRegisterDB.updatePassword(password1, email);
                                            tmp=0;
                                            check = false;
                                        }
                                    } while (tmp == 1);
                                } else{
                                    check=true;
                                    tk = 1;
                                }
                            }

                        } else if (choice1 == 2) {
                            int dc = 0;
                            do {
                                ReceiverDB receiverDB = new ReceiverDB();
                                ReceiverHandle receiverHandle = new ReceiverHandle();
                                String customer_id = Customers.customers.getId();
                                System.out.println("                Địa chỉ");
                                receiverDB.selectAllReceiverByCustomer(customer_id);
                                System.out.println("1.Chỉnh sửa");
                                System.out.println("2.Thêm địa chỉ mới");
                                System.out.println("3.Quay lại");
                                int choice2 = this.choice(sc);
                                if (choice2 == 1) {
                                    System.out.println("Chỉnh sửa địa chỉ thứ:");
                                    int stt = Integer.parseInt(sc.nextLine());
                                    String receiverId = receiverDB.selectReceiverIdBySTT(stt, customer_id);
                                    boolean check2 = false;
                                    while (!check2) {
                                        receiverDB.displayReceiverByReceiverId(receiverId);
                                        System.out.println("1.Sửa thông tin");
                                        System.out.println("2.Đặt làm mặc định");
                                        System.out.println("3.Xóa");
                                        System.out.println("4.Hoàn thành");
                                        int choice3 = this.choice(sc);
                                        if (choice3 == 1) {
                                            System.out.println("Mời bạn nhập tên:");
                                            String fullName = sc.nextLine();
                                            System.out.println("Mời bạn nhập số điện thoại:");
                                            String phone = sc.nextLine();
                                            System.out.println("Mời bạn nhập địa chỉ:");
                                            String address = sc.nextLine();
                                            receiverDB.updateReceiverByReceiverId(fullName, phone, address, receiverId);
                                            check2 = false;
                                        } else if (choice3 == 2) {
                                            System.out.println("Bạn có muốn đặt làm địa chỉ mặc định");
                                            System.out.println("1.Có");
                                            System.out.println("2.Không");
                                            int choice4 = this.choice(sc);
                                            if (choice4 == 1) {
                                                int checkReceiver = receiverDB.checkStatusReceiver(customer_id);
                                                if (checkReceiver == 1) {
                                                    receiverDB.updateReceiverToNoStatus(customer_id);
                                                    receiverDB.updateToHaveStatusById(receiverId);
                                                } else {
                                                    receiverDB.updateToHaveStatusById(receiverId);
                                                }
                                            } else if (choice4 == 2) {
                                                check2 = false;
                                            } else {
                                                System.err.println("Yêu cầu lựa chọn đúng");
                                            }
                                        } else if (choice3 == 3) {
                                            System.out.println("1.Xác nhận xóa");
                                            System.out.println("2.Thoát");
                                            int choice5 = this.choice(sc);
                                            if (choice5 == 1) {
                                                receiverDB.deleteReceiverByReceiverId(receiverId);
                                                check2=true;
                                                dc = 1;
                                            } else if (choice5 == 2) {
                                                check2 = false;
                                            } else {
                                                System.err.println("Yêu cầu bạn lựa chọn đúng");
                                            }
                                        } else  {
                                            check2=true;
                                            dc = 1;
                                        }
                                    }
                                } else if (choice2 == 2) {
                                    System.out.println("Mời bạn nhập tên:");
                                    String fullName = sc.nextLine();
                                    System.out.println("Mời bạn nhập số điện thoại:");
                                    String phone = sc.nextLine();
                                    System.out.println("Mời bạn nhập địa chỉ:");
                                    String address = sc.nextLine();
                                    System.out.println("Bạn có muốn đặt làm địa chỉ mặc định");
                                    System.out.println("1.Có");
                                    System.out.println("2.Không");
                                    int choice5 = this.choice(sc);
                                    if (choice5 == 1) {
                                        int checkReceiver = receiverDB.checkStatusReceiver(customer_id);
                                        if (checkReceiver == 1) {
                                            receiverDB.updateReceiverToNoStatus(customer_id);
                                            receiverDB.insertReceiverHaveStatus(customer_id, fullName, phone, address, Receiver.sttReceiver++);
                                        } else {
                                            receiverDB.insertReceiverHaveStatus(customer_id, fullName, phone, address,Receiver.sttReceiver++);
                                        }
                                    } else if (choice5 == 2) {
                                        receiverDB.insertReceiverNoStatus(customer_id, fullName, phone, address,Receiver.sttReceiver++);
                                    } else {
                                        System.err.println("Yêu cầu lựa chọn đúng");
                                    }
                                    dc = 1;
                                } else {
                                    dc=0;
                                    tk = 1;
                                }
                            } while (dc == 1);

                        } else if (choice1 == 3) {
                            System.out.println("Bạn có chắc chắn đăng xuất");
                            System.out.println("1.Có");
                            System.out.println("2.Không");
                            int choice2 = this.choice(sc);
                            if (choice2 == 1) {
                                Customers.customers = null;
                                System.out.println("Bạn đã đăng xuất thành công");
                                this.homePage(sc);
                            } else {
                                this.homePage(sc);
                            }
                        }else if(choice1==4){
                            this.homePage(sc);
                        }
                    } else {
                        Login_RegisterHandle login_register = new Login_RegisterHandle();
                        login_register.Login_Register(sc);
                    }
                } while (tk == 1);
                break;
            case 2:
                categoryHandle.allPicture(sc);
                break;
            case 3:
                categoryHandle.menuCategories(sc);
                break;
            case 4:
                categoryHandle.PictureByKeyword(sc);
                break;
            case 5:
                if (Customers.customers == null) {
                    System.out.println("Giỏ hàng rỗng. Bạn cần phải đăng nhập trước");
                    this.homePage(sc);
                } else {
                    cartHandle.displayCart(sc);
                }
            case 6:
                if (Customers.customers == null) {
                    System.out.println("Đơn hàng trống. Bạn cần phải đăng nhập trước.");
                    this.homePage(sc);
                } else {
                    boolean check=false;
                    while(!check){
                        OrderHandle orderHandle=new OrderHandle();
                        OrderDB orderDB=new OrderDB();
                        System.out.println("                Thông tin đơn hàng");
                        System.out.println("1.Chờ xác nhận");
                        System.out.println("2.Chờ giao hàng");
                        System.out.println("3.Đang vận chuyển");
                        System.out.println("4.Đã giao hàng");
                        System.out.println("5.Đã hủy");
                        System.out.println("6.Quay lại");
                        int choice1=this.choice(sc);
                        switch (choice1){
                            case 1:
                                orderHandle.displayOrdersWaitConfirm();
                                System.out.println("1.Xác nhận hủy");
                                System.out.println("2.Quay lại");
                                int choice2=this.choice(sc);
                                switch (choice2){
                                    case 1:
                                        System.out.println("Chọn đơn hàng thứ:");
                                        int sttOrder=Integer.parseInt(sc.nextLine());
                                        orderDB.updateCancelledBySTTOrder(sttOrder,Customers.customers.getId());
                                        check=false;
                                        break;
                                    case 2:
                                        check=false;
                                        break;
                                }
                                break;
                            case 2:
                                orderHandle.displayWaitOrder();
                                System.out.println("1.Quay lại");
                                int choice4=this.choice(sc);
                                if(choice4==1){
                                    check=false;
                                }
                                break;
                            case 3:
                                orderHandle.displayOrdersDelivering();
                                System.out.println("1.Quay lại");
                                int choice5=this.choice(sc);
                                if(choice5==1){
                                    check=false;
                                }
                                break;
                            case 4:
                                orderHandle.displayOrdersDelivered();
                                System.out.println("1.Quay lại");
                                int choice6=this.choice(sc);
                                if(choice6==1){
                                    check=false;
                                }
                                break;
                            case 5:
                                orderHandle.displayOrdersCancelled();
                                System.out.println("1.Mua lại");
                                System.out.println("2.Thoát");
                                int choice3=this.choice(sc);
                                switch (choice3){
                                    case 1:
                                        System.out.println("Chọn đơn hàng thứ:");
                                        int choi=Integer.parseInt(sc.nextLine());
                                        orderDB.updateWaitConfirmBySTTOrder(choi,Customers.customers.getId());
                                        check=false;
                                        break;
                                    case 2:
                                        check=false;
                                        break;
                                }
                                break;
                            default:
                                this.homePage(sc);
                        }
                    }

                }
                break;
            default:
                this.homePage(sc);
                break;
        }
    }
    public int choice(Scanner sc) {
        try {
            System.out.printf("Mời bạn nhập lựa chọn:");
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Bạn vui lòng nhập 1 số");
            return choice(sc);
        }
    }
}
