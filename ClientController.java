import java.util.Scanner;

public class ClientController extends UserController implements Controller {
    Colors color = new Colors();
    Scanner scanner = new Scanner(System.in);
    public static Administrator adm;

    @Override
    public void execute() {
        Menu clientMenu = new Menu();
        for (Permissions perm : adm.getPermissions()) {
            if (perm.toString().equalsIgnoreCase("WRITE")) {

                clientMenu.addMenuItem(1, new MenuItem("Create clienet", this::create));
                clientMenu.addMenuItem(2, new MenuItem("Edit client", this::edit));
            }
            if (perm.toString().equalsIgnoreCase("DELETE")) {
                clientMenu.addMenuItem(3, new MenuItem("Delete Client", super::delete));
            }
            if (perm.toString().equalsIgnoreCase("READ")) {
                clientMenu.addMenuItem(4, new MenuItem("Show clients", this::getArray));
            }
        }
        clientMenu.display();
    }


    @Override
    public void create() {
        System.out.println("User information");
        Profile profile = profCon.createProfile();

        StringValidator userNameValidator = (value) -> {
            return !aux.checkPass(value);
        };
        String userName = reader.readString("UserName", userNameValidator);

        StringValidator passwordValidator = (value) -> {
            return value.length() >= 6 && value.length() <= 8;
        };
        String password = reader.readString("Password (6-8 characters)", passwordValidator);
        Client client = new Client(profile, userName, password);
        super.addToArray(client);
    }

    @Override
    public void edit() {
        if (!UserRepository.users.isEmpty()) {
            this.getArray();
            System.out.println("User to edit");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Lastname: ");
            String last = scanner.nextLine();
            if (this.getUser(name, last) != null) {
                Menu editMenu = new Menu();
                Controller editName = () -> profCon.editName(this.getUser(name, last));
                Controller editLast = () -> profCon.editLastName(this.getUser(name, last));
                Controller editBirth = () -> profCon.editBirth(this.getUser(name, last));
                editMenu.addMenuItem(1, new MenuItem("Edit name", editName));
                editMenu.addMenuItem(2, new MenuItem("Edit lastname", editLast));
                editMenu.addMenuItem(3, new MenuItem("Edit birth date", editBirth));
                editMenu.display();
            } else {
                System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Client did not found\n");
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "There are not users registered\n");
        }
    }

    @Override
    public void getArray() {
        if (!UserRepository.users.isEmpty()) {
            System.out.printf("%30s %n----------------------------------------------%n", "Clients");
            System.out.printf("| %-12s | %-12s | %-12s |%n", "Name", "Lastname", "Birth date");
            for (User user : UserRepository.users) {
                if (user instanceof Client) {
                    System.out.printf("| %-12s | %-12s | %-12s |%n" +
                            "----------------------------------------------%n", user.getProfile().getName(), user.getProfile().getLastName(), user.getProfile().getBirthDate().dateString());
                }
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Not users registered\n");
        }
    }
}
