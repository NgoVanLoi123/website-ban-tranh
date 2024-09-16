package handle;

import database.ReceiverDB;
import entity.Receiver;
import view.HomePageUser;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiverHandle {
    HomePageUser homePageHandle=new HomePageUser();
    public static String receiverID=null;
    public String phone(Scanner sc){
            System.out.println("Mời bạn nhập số điện thoại:");
            String phone=sc.nextLine();
            String regex="^0+[1-9]+[0-9]{8}$";
            Pattern pattern=Pattern.compile(regex);
            Matcher matcher=pattern.matcher(phone);
            if(matcher.matches()){
                return phone;
            }else{
                System.err.println("Số điện thoại không hợp lệ.Vui lòng kiểm tra lại");
                return phone(sc);
            }
    }
    public void selectReceiver(Scanner sc, String customer_id,String artworkId,int quantity) {
        OrderHandle orderHandle=new OrderHandle();
        ReceiverDB receiverDB = new ReceiverDB();
        System.out.println("                Địa chỉ");
        receiverDB.selectAllReceiverByCustomer(customer_id);
        System.out.println("1.Chọn địa chỉ để nhận hàng");
        System.out.println("2.Chỉnh sửa");
        System.out.println("3.Thêm địa chỉ mới");
        System.out.println("4.Quay lại");
        int choice = homePageHandle.choice(sc);
        if (choice == 1) {
            System.out.println("Chọn địa chỉ thứ:");
            int stt = Integer.parseInt(sc.nextLine());
            ReceiverHandle.receiverID = receiverDB.selectReceiverIdBySTT(stt,customer_id);
            orderHandle.orderConfirm(sc,customer_id,artworkId,quantity);
        } else if (choice == 2) {
            this.editReceiver(sc,customer_id,artworkId,quantity);
        } else if (choice == 3) {
            this.insertReceiver(sc, customer_id,artworkId,quantity);
        } else if(choice==4) {
            orderHandle.orderConfirm(sc,customer_id,artworkId,quantity);
        }else{
            System.err.println("Yêu cầu bạn nhập đúng");
        }
    }
    public void insertReceiver(Scanner sc, String customer_id,String artworkId,int quantity) {
        ReceiverDB receiverDB = new ReceiverDB();
        System.out.println("Mời bạn nhập tên:");
        String fullName = sc.nextLine();
        System.out.println("Mời bạn nhập số điện thoại:");
        String phone = sc.nextLine();
        System.out.println("Mời bạn nhập địa chỉ:");
        String address = sc.nextLine();
        System.out.println("Bạn có muốn đặt làm địa chỉ mặc định");
        System.out.println("1.Có");
        System.out.println("2.Không");
        int choice = homePageHandle.choice(sc);
        if (choice == 1) {
            int checkReceiver=receiverDB.checkStatusReceiver(customer_id);
            if(checkReceiver==1){
                receiverDB.updateReceiverToNoStatus(customer_id);
                receiverDB.insertReceiverHaveStatus(customer_id, fullName, phone, address, Receiver.sttReceiver++);
            }else{
                receiverDB.insertReceiverHaveStatus(customer_id, fullName, phone, address,Receiver.sttReceiver++);
            }
        } else if (choice == 2) {
            receiverDB.insertReceiverNoStatus(customer_id, fullName, phone, address,Receiver.sttReceiver++);
        } else {
            System.err.println("Yêu cầu lựa chọn đúng");
        }
        this.selectReceiver(sc,customer_id,artworkId,quantity);
    }
    public void editReceiver(Scanner sc,String customer_id,String artworkId,int quantity) {
        ReceiverDB receiverDB = new ReceiverDB();
        System.out.println("Chỉnh sửa địa chỉ thứ:");
        int stt = Integer.parseInt(sc.nextLine());
        String receiverId = receiverDB.selectReceiverIdBySTT(stt,customer_id);
        boolean check = false;
        while (!check) {
            receiverDB.displayReceiverByReceiverId(receiverId);
            System.out.println("1.Sửa thông tin");
            System.out.println("2.Đặt làm mặc định");
            System.out.println("3.Xóa");
            System.out.println("4.Hoàn thành");
            int choice = homePageHandle.choice(sc);
            if (choice == 1) {
                System.out.println("Mời bạn nhập tên:");
                String fullName = sc.nextLine();
                System.out.println("Mời bạn nhập số điện thoại:");
                String phone = sc.nextLine();
                System.out.println("Mời bạn nhập địa chỉ:");
                String address = sc.nextLine();
                receiverDB.updateReceiverByReceiverId(fullName, phone, address, receiverId);
                check = false;
            } else if(choice==2){
                System.out.println("Bạn có muốn đặt làm địa chỉ mặc định");
                System.out.println("1.Có");
                System.out.println("2.Không");
                int choice1 = homePageHandle.choice(sc);
                if (choice1 == 1) {
                    int checkReceiver=receiverDB.checkStatusReceiver(customer_id);
                    if(checkReceiver==1){
                        receiverDB.updateReceiverToNoStatus(customer_id);
                        receiverDB.updateToHaveStatusById(receiverId);
                    }else{
                        receiverDB.updateToHaveStatusById(receiverId);
                    }
                } else if (choice1 == 2) {
                    check=false;
                } else {
                    System.err.println("Yêu cầu lựa chọn đúng");
                }
            } else if (choice == 3) {
                System.out.println("1.Xác nhận xóa");
                System.out.println("2.Thoát");
                int choice1 = homePageHandle.choice(sc);
                if (choice1 == 1) {
                    receiverDB.deleteReceiverByReceiverId(receiverId);
                    this.selectReceiver(sc,customer_id,artworkId,quantity);
                } else if (choice1 == 2) {
                    check = false;
                } else {
                    System.err.println("Yêu cầu bạn lựa chọn đúng");
                }

            } else if (choice == 4) {
                this.selectReceiver(sc,customer_id,artworkId,quantity);
            } else {
                System.err.println("Yêu cầu bạn nhập đúng");
            }
        }
    }

}
