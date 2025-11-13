import java.util.List;

public class Main {
    public static void main(String[] args) {


        //Her starter login programmet
        User currentUser = Login.showLoginMenu();
        MenuSystem.option(currentUser); // Her vises Menu Systemet

    }
}