package handle;

import database.AdminDB;
import database.CartDB;
import database.Login_RegisterDB;
import entity.Admin;
import entity.Customers;
import entity.Orders;
import entity.Receiver;
import view.HomePageUser;
import view.PageAdmin;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_RegisterHandle {
    public boolean regexEmail(String email) {
        String regex = "^[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+){1,3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean regexPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[.,\\-_;])[a-zA-Z0-9.,\\-_;]{7,15}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public String Email(Scanner sc) {
        System.out.println("Mời bạn nhập email :");
        String email = sc.nextLine();
        boolean check = this.regexEmail(email);
        if (check) {
            return email;
        } else {
            System.err.println("Email không đúng định dạng.Vui lòng nhập lại email");
            return Email(sc);
        }
    }

    public String Password(Scanner sc) {
        System.out.println("Mời bạn nhập password:");
        String password = sc.nextLine();
        boolean check = this.regexPassword(password);
        if (check) {
            return password;
        } else {
            System.err.println("Password không mạnh.Vui lòng nhập password mạnh hơn chứa ít nhất 1 số và 1 kí tự (.,-_;)");
            return Password(sc);
        }
    }

    public void Login_Register(Scanner sc) {
        System.out.println("1.Đăng kí");
        System.out.println("2.Đăng nhập");
        System.out.println("3.Quay lại trang chủ");
        HomePageUser page = new HomePageUser();
        Login_RegisterDB loginRegisterDB = new Login_RegisterDB();
        int choice = page.choice(sc);
        switch (choice) {
            case 1:
                boolean check = false;
                while (!check) {
                    System.out.println("Mời bạn nhập username:");
                    String username = sc.nextLine();
                    int count = loginRegisterDB.checkUsername(username);
                    if (count == 0) {
                        check = true;
                        boolean check1 = false;
                        while (!check1) {
                            String email = this.Email(sc);
                            int count1 = loginRegisterDB.checkEmail(email);
                            if (count1 == 0) {
                                check1 = true;
                                String password = this.Password(sc);
                                System.out.println("Mời bạn nhập họ tên :");
                                String name = sc.nextLine();
                                ReceiverHandle receiverHandle = new ReceiverHandle();
                                boolean check2 = false;
                                while (!check2) {
                                    String phone = receiverHandle.phone(sc);
                                    int checkPhone = loginRegisterDB.checkPhone(phone);
                                    if (checkPhone == 1) {
                                        System.err.println("Số điện thoại đã tồn tại.Vui lòng đăng kí số điện thoại khác");
                                        check2 = false;
                                    } else {
                                        check2 = true;
                                        loginRegisterDB.insertCustomer(username, email, password, name, phone);
                                        Customers customers = loginRegisterDB.selectCustomer(username, password);
                                        CartDB cartDB = new CartDB();
                                        cartDB.insertToCart(customers.getId());
                                        System.out.println("Chúc mừng bạn đã đăng kí thành công");

                                        page.homePage(sc);
                                    }
                                }
                            } else {
                                System.err.println("Email đã tồn tại.Vui lòng nhập email khác");
                            }
                        }
                    } else {
                        System.err.println("Username đã tồn tại.Vui lòng nhập username khác");
                    }
                }
                break;
            case 2:
                boolean check1 = false;
                do {
                    System.out.println("Mời bạn nhập username:");
                    String username = sc.nextLine();
                    System.out.println("Mời bạn nhập password:");
                    String password = sc.nextLine();
                    AdminDB adminDB=new AdminDB();
                    int checkUserAdmin=adminDB.checkUserAdmin(username);
                    int checkPassAdmin=adminDB.checkPassword(username,password);
                    if(checkUserAdmin==1 && checkPassAdmin==1){
                        Admin.admin=adminDB.selectAdmin(username,password);
                        System.out.println("Chào mừng admin mời bạn thực hiện các chức năng ");
                        PageAdmin pageAdmin=new PageAdmin();
                        pageAdmin.homePage(sc);
                    }
                    int checkUer = loginRegisterDB.checkUsername(username);
                    int checkPass = loginRegisterDB.checkPassword(username, password);
                    if (checkUer == 0 || checkPass == 0) {
                        System.err.println("Username hoặc Password không đúng!Vui lòng kiểm tra lại");
                        System.out.println("1.Đăng nhập lại");
                        System.out.println("2.Bạn quên mật khẩu");
                        System.out.println("3.Tạo tài khoản mới");
                        int choice1 = page.choice(sc);
                        switch (choice1) {
                            case 1:
                                break;
                            case 2:
                                check1 = true;
                                boolean emailFalse = false;
                                do {
                                    System.out.println("Mời bạn nhập email :");
                                    String email = sc.nextLine();
                                    int checkEmail = loginRegisterDB.checkEmail(email);
                                    if (checkEmail == 0) {
                                        System.err.println("Email không đúng.Vui lòng kiểm tra lại");
                                    } else {
                                        emailFalse = true;
                                        String password1 = this.Password(sc);
                                        loginRegisterDB.updatePassword(password1, email);
                                        page.homePage(sc);
                                    }
                                } while (!emailFalse);

                                break;
                            case 3:
                                check1 = true;
                                this.Login_Register(sc);
                                break;
                            default:
                                System.err.println("Yêu cầu bạn lựa chọn đúng");
                                break;
                        }
                    } else if (checkUer == 1 && checkPass == 1) {
                        System.out.println("Chúc mừng tài khoản " + username + " đã đăng nhập thành công.");
                        Customers.customers = loginRegisterDB.selectCustomer(username, password);
                        Receiver.sttReceiver=1;
                        Orders.sttOrder=1;
                        page.homePage(sc);

                    }
                } while (!check1);

                break;
            case 3:
                page.homePage(sc);
                break;
            default:
                System.out.println("Yêu cầu bạn lựa chọn đúng");
                break;
        }
    }
}
