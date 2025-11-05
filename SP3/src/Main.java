public class Main {
    public static void main(String[] args) {
        User currentUser = Login.showLoginMenu();

        System.out.println("\nVelkommen " + currentUser.getUsername() + "!");

        // Her starter jeres hovedmenu
        MenuSystem menu = new MenuSystem(currentUser);
        menu.showMainMenu();
    }
}

