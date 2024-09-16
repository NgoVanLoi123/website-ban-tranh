package handle;

import database.CartDB;
import database.CategoryDB;
import database.OrderDB;
import database.ReceiverDB;
import entity.Customers;
import entity.Orders;
import entity.Receiver;
import view.HomePageUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class OrderHandle {
    public static ArrayList<Integer> navigation=new ArrayList<>();
    HomePageUser homePageHandle = new HomePageUser();
    OrderDB orderDB = new OrderDB();

    public void buyNow(Scanner sc, String artworkId) {
        CartHandle cartHandle = new CartHandle();
        CartDB cartDB = new CartDB();
        ReceiverHandle receiverHandle = new ReceiverHandle();
        String customer_id = Customers.customers.getId();
        ReceiverDB receiverDB = new ReceiverDB();
        int checkStatusReceiver = receiverDB.checkStatusReceiver(customer_id);
        if (checkStatusReceiver == 0) {
            System.out.println("Nhập thông tin địa chỉ nhận hàng");
            System.out.println("Mời bạn nhập tên:");
            String fullName = sc.nextLine();
            String phone = receiverHandle.phone(sc);
            System.out.println("Mời bạn nhập địa chỉ:");
            String address = sc.nextLine();
            System.out.println("Bạn có muốn đặt làm địa chỉ mặc định");
            System.out.println("1.Có");
            System.out.println("2.Không");
            int choice = homePageHandle.choice(sc);
            if (choice == 1) {
                receiverDB.insertReceiverHaveStatus(customer_id, fullName, phone, address, Receiver.sttReceiver++);
                ReceiverHandle.receiverID = receiverDB.selectReceiverIdHaveStatus(customer_id);
            } else if (choice == 2) {
                receiverDB.insertReceiverNoStatus(customer_id, fullName, phone, address,Receiver.sttReceiver++);
                ReceiverHandle.receiverID = receiverDB.selectReceiverIdNoStatus(customer_id, fullName, phone, address);
            } else {
                System.err.println("Yêu cầu lựa chọn đúng");
            }
        } else {
            ReceiverHandle.receiverID = receiverDB.selectReceiverIdHaveStatus(customer_id);
        }
        int quantity = cartHandle.getQuantity(sc);
        this.orderConfirm(sc, customer_id, artworkId, quantity);
    }

    public void orderConfirm(Scanner sc, String customer_id, String artworkId, int quantity) {
        CartDB cartDB = new CartDB();
        String picture_id = cartDB.idPictureByArtworkId(artworkId);
        double price = orderDB.selectPrice(picture_id);
        double totalPrice = quantity * price;
        boolean check = false;
        while (!check) {
            CategoryDB categoryDB=new CategoryDB();
            String title=categoryDB.selectTitle(picture_id);
            String artworkId1=categoryDB.selectArtworkId(picture_id);
            System.out.println("Title:" + title);
            System.out.println("ArtworkId:" + artworkId1);
            System.out.println("Quantity:" + quantity);
            System.out.println("Price:" + price);
            System.out.println("TotalMoney" + totalPrice);
            ReceiverDB receiverDB = new ReceiverDB();
            receiverDB.displayReceiverByReceiverId(ReceiverHandle.receiverID);
            System.out.println("1.Đặt hàng");
            System.out.println("2.Chọn địa chỉ nhận hàng khác");
            System.out.println("3.Quay lại");
            int choice2 = homePageHandle.choice(sc);
            if (choice2 == 1) {
                LocalDateTime date_time = LocalDateTime.now();
                DateTimeFormatter vietnameseDateTimeFormatter = DateTimeFormatter
                        .ofPattern("EEEE, dd MMMM yyyy HH:mm:ss", new Locale("vi", "VN"));
                String dateTime = date_time.format(vietnameseDateTimeFormatter);
                String fullname=receiverDB.selectFullNameByReceiverId(ReceiverHandle.receiverID);
                String phone=receiverDB.selectPhoneByReceiverId(ReceiverHandle.receiverID);
                String address=receiverDB.selectAddressByReceiverId(ReceiverHandle.receiverID);
                orderDB.insertOrders(customer_id, dateTime, fullname,phone,address,Orders.sttOrder++);
                String order_id = orderDB.selectOrderId(dateTime, customer_id);
                orderDB.insertOrderDetail(order_id,title,artworkId1, quantity, price, totalPrice);
                System.out.println("Chúc mừng bạn mua hàng thành công");
                homePageHandle.homePage(sc);
            } else if (choice2 == 2) {
                ReceiverHandle receiverHandle = new ReceiverHandle();
                receiverHandle.selectReceiver(sc, customer_id,artworkId,quantity);

            }else{
                homePageHandle.homePage(sc);
            }
        }
    }
    public void displayOrdersWaitConfirm() {
        ArrayList<Orders> orders = orderDB.selectOrderIdByWaitConfirm(Customers.customers.getId());
        for (Orders order : orders) {
            orderDB.displayOrders(Customers.customers.getId(), order.getId());
        }
    }
    public void displayOrdersDelivering() {
        ArrayList<Orders> orders = orderDB.selectOrderIdByDelivering(Customers.customers.getId());
        for (Orders order : orders) {
            orderDB.displayOrders(Customers.customers.getId(), order.getId());
        }
    }
    public void displayOrdersDelivered() {
        ArrayList<Orders> orders = orderDB.selectOrderIdByDelivered(Customers.customers.getId());
        for (Orders order : orders) {
            orderDB.displayOrders(Customers.customers.getId(), order.getId());
        }
    }
    public void displayOrdersCancelled() {
        ArrayList<Orders> orders = orderDB.selectOrderIdByCancelled(Customers.customers.getId());
        for (Orders order : orders) {
            orderDB.displayOrders(Customers.customers.getId(), order.getId());
        }
    }
    public void displayWaitOrder() {
        ArrayList<Orders> orders = orderDB.selectOrderIdByWaitOrder(Customers.customers.getId());
        for (Orders order : orders) {
            orderDB.displayOrders(Customers.customers.getId(), order.getId());
        }
    }
}
