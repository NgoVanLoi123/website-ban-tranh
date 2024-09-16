
import view.HomePageUser;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        HomePageUser homePageHandle=new HomePageUser();
        homePageHandle.homePage(sc);
    }
}