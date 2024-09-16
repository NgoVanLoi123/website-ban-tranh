package handle;

import database.AdminDB;
import entity.OrderDetail;
import entity.Orders;
import view.HomePageUser;
import view.PageAdmin;

import java.util.*;

public class AdminHandle {
    AdminDB adminDB=new AdminDB();
    HomePageUser homePageHandle=new HomePageUser();
    public void orderBrowsing(Scanner sc){
        ArrayList<Orders> orderDetailArrayList = new ArrayList<>();
        PageAdmin pageAdmin=new PageAdmin();
        boolean check=false;
        while(!check){
            System.out.println("Mời bạn nhập mã đơn hàng cần duyệt:");
            String order_id=sc.nextLine();
            //check
            int checkOrderId=adminDB.checkOrder_id(order_id);
            if(checkOrderId==0){
                System.out.println("Mã đơn hàng không đúng!Vui lòng kiểm tra lại");
                check=false;
            }else {
                check = true;
                Orders orders = adminDB.selectOrderById(order_id);
                orderDetailArrayList.add(orders);
                System.out.println("1.Tiếp tục chọn mã đơn hàng");
                System.out.println("2.Duyệt những mã đơn hàng đã chọn");
                int choice2 = homePageHandle.choice(sc);
                if (choice2 == 1) {
                    check = false;
                } else if (choice2 == 2) {
                    System.out.printf("%-42s%-42s%-42s%s\n", "            Mã Đơn Hàng", "           Mã Khách Hàng", "        Thời Gian Đặt Hàng", "Trạng Thái");
                    for (Orders orders1 : orderDetailArrayList) {
                        System.out.printf("%-42s%-42s%-42s%s\n", orders1.getId(), orders1.getCustomer_id(), orders1.getDate_time(), orders1.getStatus());
                    }
                    System.out.println("Thiết lập trạng thái đơn hàng");
                    System.out.println("1.Chờ giao hàng");
                    System.out.println("2.Đang giao hàng");
                    System.out.println("3.Đã giao hàng ");
                    int choice3 = homePageHandle.choice(sc);
                    ArrayList<String> arrayOrderId=new ArrayList<>();
                    switch (choice3) {
                        case 1:
                            for (Orders orders1 : orderDetailArrayList) {
                                boolean updateSuccessful =adminDB.updateStatus(orders1.getId(),"Chờ giao hàng");
                            }
                            System.out.println("Duyệt thành công");
                            pageAdmin.homePage(sc);
                            break;
                        case 2:
                            for (Orders orders1 : orderDetailArrayList) {
                                adminDB.updateStatus(orders1.getId(),"Đang giao hàng");
                            }
                            System.out.println("Duyệt thành công");
                            pageAdmin.homePage(sc);
                            break;
                        case 3:
                            for (Orders orders1 : orderDetailArrayList) {
                                boolean updateSuccessful =adminDB.updateStatus(orders1.getId(),"Đã giao hàng");
                                if(updateSuccessful){
                                    arrayOrderId.add(orders1.getId());
                                }
                            }
                            if(arrayOrderId.size()>0){
                                ArrayList<OrderDetail> orderDetails=adminDB.selectQuantityAndArtworkId(arrayOrderId);
                                if(orderDetails!=null && orderDetails.size()>0){
                                    for(OrderDetail orderDetail:orderDetails){
                                        adminDB.updateQuantity(orderDetail.getQuantity(),orderDetail.getArtworkId());
                                    }
                                }

//                                Map<String,Integer> map=new HashMap<>();
//                                for(OrderDetail o:orderDetails){
//                                    String artworkId=o.getArtworkId();
//                                    int quantity=o.getQuantity();
//                                    if(map.containsKey(artworkId)){
//                                        int newQuantity=map.get(artworkId)+quantity;
//                                        map.put(artworkId,newQuantity);
//                                    }else{
//                                        map.put(artworkId,quantity);
//                                    }
//                                }
//                                if(!map.isEmpty()){
//                                    Set<Map.Entry<String,Integer>> entrySet=map.entrySet();
//                                    for(Map.Entry<String,Integer> entry : entrySet){
//                                        adminDB.updateQuantity(entry.getValue(),entry.getKey());
//                                    }
//
//                                }
                            }
                            System.out.println("Duyệt thành công");
                            pageAdmin.homePage(sc);
                            break;
                    }
                }
            }

        }
    }
    public void pictureManager(Scanner sc){
        System.out.println("1.Xem sản phẩm đã bán");
        System.out.println("2.Thêm sản phẩm");
        System.out.println("3.Update số lượng");
        System.out.println("4.Xóa sản phẩm");
        System.out.println("5.Quay lại");
        int choice =homePageHandle.choice(sc);
        switch(choice){
            case 1:
                ArrayList<String> orders_id=adminDB.selectAllOrder_id();
                adminDB.displaySellPictures(orders_id);
                System.out.println("1.Quay lại");
                int choice4=Integer.parseInt(sc.nextLine());
                if(choice4==1){
                    this.pictureManager(sc);
                }
                break;
            case 2:
                System.out.println("Mời bạn nhập tiêu đề:");
                String title=sc.nextLine();
                System.out.println("Mời bạn nhập mã số tranh:");
                String artworkId=sc.nextLine();
                System.out.println("Mời bạn nhập giá tiền:");
                double price=Double.parseDouble(sc.nextLine());
                System.out.println("Mời bạn nhập xuất xứ:");
                String origin=sc.nextLine();
                System.out.println("Mời bạn nhập kích thước:");
                String size=sc.nextLine();
                System.out.println("Mời bạn nhập khối lượng:");
                double weight=Double.parseDouble(sc.nextLine());
                System.out.println("Mời bạn nhập số lượng:");
                int quantity=Integer.parseInt(sc.nextLine());
                System.out.println("Mời bạn nhập tên chủ đề tranh:");
                String topic=sc.nextLine();
                String topicId=adminDB.selectTopicId(topic);
                adminDB.insertPicture(title,artworkId,price,origin,size,weight,quantity,topicId);
                adminDB.displayPictureDetail();
                System.out.println("1.Quay lại");
                int choice1=Integer.parseInt(sc.nextLine());
                if(choice1==1){
                    this.pictureManager(sc);
                }
                break;
            case 3:
                adminDB.displayPictureDetail();
                System.out.println("Mời bạn nhập mã số tranh cần cập nhật:");
                String artworkId1=sc.nextLine();
                System.out.println("Mời bạn nhập số lượng:");
                int quantity1=Integer.parseInt(sc.nextLine());
                adminDB.updateQuantityByArtworkId(quantity1,artworkId1);
                adminDB.displayPictureDetail();
                System.out.println("1.Quay lại");
                int choice2=Integer.parseInt(sc.nextLine());
                if(choice2==1){
                    this.pictureManager(sc);
                }
                break;
            case 4:
                adminDB.displayPictureDetail();
                System.out.println("Mời bạn nhập mã số tranh cần xóa:");
                String artworkId2=sc.nextLine();
                String picture_id=adminDB.selectPictureId(artworkId2);
                int check=adminDB.checkPictureIdByCartDetail(picture_id);
                if(check>=1){
                    adminDB.deletePictureInCart(picture_id);
                    adminDB.deletePicture(artworkId2);
                }else{
                    adminDB.deletePicture(artworkId2);
                }
                adminDB.displayPictureDetail();
                System.out.println("1.Quay lại");
                int choice3=Integer.parseInt(sc.nextLine());
                if(choice3==1){
                    this.pictureManager(sc);
                }
                break;
            default:
                PageAdmin pageAdmin=new PageAdmin();
                pageAdmin.homePage(sc);
                break;
        }

    }
}
