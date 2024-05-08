import java.util.HashMap;

public class Menu {
    ConsoleReader reader = new ConsoleReader();
    private HashMap<Integer, MenuItem> menuItems;

    Menu() {
        menuItems = new HashMap<>();
    }

    void addMenuItem(int key, MenuItem menuItem) {
        menuItems.put(key, menuItem);
    }

    void display() {
        System.out.println("<<<< MENU >>>>");
        for (Integer key : menuItems.keySet()) {
            MenuItem menuItem = menuItems.get(key);
            System.out.println(key + ".- " + menuItem.getText());
        }

        IntegerValidator optionValidator = (value) -> value > 0 && value <= menuItems.size();
        int option = reader.readInt("Option", optionValidator);
        MenuItem menuItem = menuItems.get(option);
        menuItem.getController().execute();
    }
}
