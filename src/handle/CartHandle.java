package handle;

import database.CartDB;
import database.CategoryDB;
import database.OrderDB;
import database.ReceiverDB;
import entity.*;
import view.HomePageUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class CartHandle {

    CartDB cartDB = new CartDB();

    public int getQuantity(Scanner sc) {
        try {
            System.out.println("Mời bạn nhập số lượng:");
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Bạn vui lòng nhập 1 số:");
            return getQuantity(sc);
        }
    }

    public boolean insertToCartDetail(String artworkId) {
        String card_id = cartDB.selectIdCartByIdCustomer(Customers.customers.getId());
        String picture_id = cartDB.idPictureByArtworkId(artworkId);
        int checkPicture = cartDB.checkPictureInCart(picture_id, card_id);
        if (checkPicture == 0) {
            cartDB.insertToCartDetail(card_id, picture_id);
            System.out.println("Thêm giỏ hàng thành công");
            return true;
        } else {
            cartDB.updateQuantityInCart(picture_id, card_id);
            System.out.println("Thêm giỏ hàng thành công");
            return true;
        }
    }

    public void displayCart(Scanner sc) {
        CategoryDB categoryDB = new CategoryDB();
        HomePageUser homePageHandle = new HomePageUser();
        String card_id = cartDB.selectIdCartByIdCustomer(Customers.customers.getId());
        cartDB.displayCart(card_id);
        System.out.println("1.Cập nhật số lượng");
        System.out.println("2.Xóa");
        System.out.println("3.Mua ngay");
        System.out.println("4.Quay lại trang chủ");
        int choice = homePageHandle.choice(sc);
        switch (choice) {
            case 1:
                boolean check = false;
                while (!check) {
                    System.out.println("Mời bạn nhập mã số tranh cần cập nhật:");
                    String artworkId = sc.nextLine();
                    int quantity = this.getQuantity(sc);
                    int checkArtworkId = categoryDB.checkArtworkId(artworkId);
                    if (checkArtworkId == 0) {
                        System.err.println("Không tồn tại mã số tranh trên trong giỏ hàng.Vui lòng kiểm tra lại");
                    } else {
                        check = true;
                        String picture_id = cartDB.idPictureByArtworkId(artworkId);
                        String cart_id = cartDB.selectIdCartByIdCustomer(Customers.customers.getId());
                        cartDB.updateQuantityInCart(quantity, picture_id, cart_id);
                        System.out.println("Cập nhật số lượng thành công");
                        this.displayCart(sc);
                    }
                }
                break;
            case 2:
                boolean check1 = false;
                while (!check1) {
                    System.out.println("Mời bạn nhập mã số tranh cần xóa:");
                    String artworkId = sc.nextLine();
                    int checkArtworkId = categoryDB.checkArtworkId(artworkId);
                    if (checkArtworkId == 0) {
                        System.err.println("Không tồn tại mã số tranh trên trong giỏ hàng.Vui lòng kiểm tra lại");
                    } else {
                        check1 = true;
                        System.out.println("Bạn có chắc chắn xóa");
                        System.out.println("1.Có");
                        System.out.println("2.Không");
                        int choice1 = homePageHandle.choice(sc);
                        if (choice1 == 1) {
                            String picture_id = cartDB.idPictureByArtworkId(artworkId);
                            String cart_id = cartDB.selectIdCartByIdCustomer(Customers.customers.getId());
                            cartDB.deletePictureInCart(picture_id, cart_id);
                            System.out.println("Xóa thành công");
                            this.displayCart(sc);
                        } else if (choice1 == 2) {
                            this.displayCart(sc);
                        } else {
                            System.out.println("Yêu cầu bạn lựa chọn đúng");
                        }
                    }
                }
                break;
            case 3:
                ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<>();
                CartDB cartDB = new CartDB();
                OrderDB orderDB = new OrderDB();
                String cartId = orderDB.cartIdByCustomer(Customers.customers.getId());
                boolean check2 = false;
                while (!check2) {
                    cartDB.displayCart(cartId);
                    System.out.println("Mời bạn nhập mã số tranh cần mua:");
                    String artworkId = sc.nextLine();
                    int checkArtwork = categoryDB.checkArtworkId(artworkId);
                    if (checkArtwork == 0) {
                        System.out.println("Mã số tranh không tồn tại.Vui lòng kiểm tra lại");
                        check2 = false;
                    } else {
                        String picture_id = cartDB.idPictureByArtworkId(artworkId);
                        int checkArtworkId = cartDB.checkPictureInCart(picture_id,cartId);
                        if (checkArtworkId == 0) {
                            System.err.println("Không tồn tại mã số tranh trên trong giỏ hàng.Vui lòng kiểm tra lại");
                            check2 = false;
                        } else {
                            check2 = true;
                            String title=categoryDB.selectTitle(picture_id);
                            String artworkId1=categoryDB.selectArtworkId(picture_id);
                            int quantity = orderDB.quantityByCart(cartId, picture_id);
                            double price = orderDB.selectPrice(picture_id);
                            double totalPrice = quantity * price;
                            OrderDetail orderDetail = new OrderDetail(picture_id,title,artworkId1, quantity, price, totalPrice);
                            orderDetailArrayList.add(orderDetail);
                            System.out.println("1.Tiếp tục mua thêm");
                            System.out.println("2.Mua những sản phẩm đã chọn");
                            int choice1 = homePageHandle.choice(sc);
                            if (choice1 == 1) {
                                check2 = false;
                            } else if (choice1 == 2) {
                                ReceiverDB receiverDB = new ReceiverDB();
                                ReceiverHandle receiverHandle = new ReceiverHandle();
                                int checkStatusReceiver = receiverDB.checkStatusReceiver(Customers.customers.getId());
                                String customer_id = Customers.customers.getId();
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
                                    int choice2 = homePageHandle.choice(sc);
                                    if (choice2 == 1) {
                                        receiverDB.insertReceiverHaveStatus(customer_id, fullName, phone, address, Receiver.sttReceiver++);
                                        ReceiverHandle.receiverID = receiverDB.selectReceiverIdHaveStatus(customer_id);
                                    } else if (choice2 == 2) {
                                        receiverDB.insertReceiverNoStatus(customer_id, fullName, phone, address,Receiver.sttReceiver++);
                                        ReceiverHandle.receiverID = receiverDB.selectReceiverIdNoStatus(customer_id, fullName, phone, address);
                                    } else {
                                        System.err.println("Yêu cầu lựa chọn đúng");
                                    }
                                } else {
                                    ReceiverHandle.receiverID = receiverDB.selectReceiverIdHaveStatus(customer_id);
                                }
                                //display OrderConfirm
                                int orderConfirm=0;
                                do{
                                    int x=1;
                                    for (OrderDetail orderDetail1 : orderDetailArrayList) {
                                        System.out.println(x);
                                        System.out.println("Title:" + orderDetail1.getTitle());
                                        System.out.println("ArtworkId:" + orderDetail1.getArtworkId());
                                        System.out.println("Quantity:" + orderDetail1.getQuantity());
                                        System.out.println("Price:" + orderDetail1.getPrice());
                                        System.out.println("TotalMoney" + orderDetail1.getTotalPrice());
                                        x++;
                                    }
                                    receiverDB.displayReceiverByReceiverId(ReceiverHandle.receiverID);
                                    System.out.println("1.Đặt hàng");
                                    System.out.println("2.Chọn địa chỉ nhận hàng khác");
                                    System.out.println("3.Quay lại");
                                    int choice3 = homePageHandle.choice(sc);
                                    if (choice3 == 1) {
                                        LocalDateTime date_time = LocalDateTime.now();
                                        DateTimeFormatter vietnameseDateTimeFormatter = DateTimeFormatter
                                                .ofPattern("EEEE, dd MMMM yyyy HH:mm:ss", new Locale("vi", "VN"));
                                        String dateTime = date_time.format(vietnameseDateTimeFormatter);
                                        String fullname=receiverDB.selectFullNameByReceiverId(ReceiverHandle.receiverID);
                                        String phone=receiverDB.selectPhoneByReceiverId(ReceiverHandle.receiverID);
                                        String address=receiverDB.selectAddressByReceiverId(ReceiverHandle.receiverID);
                                        orderDB.insertOrders(customer_id, dateTime,fullname,phone,address,Orders.sttOrder++);
                                        String order_id = orderDB.selectOrderId(dateTime, customer_id);
                                        for (OrderDetail orderDetail1 : orderDetailArrayList) {
                                            orderDB.insertOrderDetail(order_id, orderDetail1.getTitle(),orderDetail1.getArtworkId(), orderDetail1.getQuantity(), orderDetail1.getPrice(), orderDetail1.getTotalPrice());
                                            cartDB.deletePictureInCart(orderDetail1.getPicture_id(),cartId);
                                        }
                                        System.out.println("Chúc mừng bạn mua hàng thành công");
                                        homePageHandle.homePage(sc);
                                    } else if (choice3 == 2) {
                                        System.out.println("                Địa chỉ");
                                        receiverDB.selectAllReceiverByCustomer(customer_id);
                                        System.out.println("1.Chọn địa chỉ để nhận hàng");
                                        System.out.println("2.Chỉnh sửa");
                                        System.out.println("3.Thêm địa chỉ mới");
                                        System.out.println("4.Quay lại");
                                        int choice2 = homePageHandle.choice(sc);
                                        if (choice2 == 1) {
                                            System.out.println("Chọn địa chỉ thứ:");
                                            int stt = Integer.parseInt(sc.nextLine());
                                            ReceiverHandle.receiverID = receiverDB.selectReceiverIdBySTT(stt,customer_id);
                                            orderConfirm=1;
                                        } else if (choice2 == 2) {
                                            receiverHandle.editReceiver(sc,customer_id,artworkId,quantity);
                                        } else if (choice2 == 3) {
                                            receiverHandle.insertReceiver(sc, customer_id,artworkId,quantity);
                                        } else if(choice2==4) {
                                            orderConfirm=1;
                                        }else{
                                            System.err.println("Yêu cầu bạn nhập đúng");
                                        }
                                    }else if(choice3==3){
                                        this.displayCart(sc);
                                    }
                                }while(orderConfirm==1);

                            }
                        }
                    }
                }
                break;
            case 4:
                homePageHandle.homePage(sc);
                break;
            default:
                System.err.println("Yêu cầu bạn lựa chọn đúng");
                break;
        }
    }
}

