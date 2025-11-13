import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Her starter login programmet
        User currentUser = Login.showLoginMenu(); // antager Login-klassen returnerer en User
        if (currentUser != null) {
            MenuSystem.option(currentUser); // Vis menuen med den nuværende bruger
        } else {
            System.out.println("Login mislykkedes. Program afsluttes.");
        }
    }
}
