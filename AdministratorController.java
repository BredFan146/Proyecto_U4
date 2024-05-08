import java.util.Scanner;

public class AdministratorController extends UserController implements Controller {
    Colors color = new Colors();
    static Scanner scanner = new Scanner(System.in);
    static Auxiliary aux = new Auxiliary();
    public static Administrator adm;

    public static void mainMenu(Administrator admins) {
        Menu mainMenu = new Menu();

        Controller authorController = new AuthorController();
        Controller clientController = new ClientController();
        Controller bookController = new BookController();
        Controller transactionController = new TransactionController();
        Controller administratorController = new AdministratorController();
        Controller log = () -> log();
        mainMenu.addMenuItem(1, new MenuItem("Author Menu", authorController));
        mainMenu.addMenuItem(2, new MenuItem("Client Menu", clientController));
        mainMenu.addMenuItem(3, new MenuItem("Book Menu", bookController));
        mainMenu.addMenuItem(4, new MenuItem("Transactions Menu", transactionController));
        if (admins.isSuperAdmin()) {
            mainMenu.addMenuItem(5, new MenuItem("Administrator Menu", administratorController));
        }
        mainMenu.addMenuItem(6, new MenuItem("Log out", log));
        mainMenu.display();
    }

    public static void clientMenu(Client client) {
        Menu clientMen = new Menu();
        BookController cont = new BookController();
        TransactionController tran = new TransactionController();
        Controller transactionController = () -> tran.clientReport();
        Controller bookController = () -> cont.showBooks();
        clientMen.addMenuItem(1, new MenuItem("Show books", bookController));
        clientMen.addMenuItem(2, new MenuItem("See transactions", transactionController));
    }

    @Override
    public void execute() {
        Menu adminMenu = new Menu();
        for (Permissions perm : adm.getPermissions()) {
            if (perm.toString().equalsIgnoreCase("WRITE")) {
                adminMenu.addMenuItem(1, new MenuItem("Create administrator", this::create));
                adminMenu.addMenuItem(2, new MenuItem("Edit administrator", this::edit));
            }
            if (perm.toString().equalsIgnoreCase("DELETE")) {
                adminMenu.addMenuItem(3, new MenuItem("Delete administrator", super::delete));
            }
            if (perm.toString().equalsIgnoreCase("READ")) {
                adminMenu.addMenuItem(4, new MenuItem("Show administrator", this::getArray));
            }
        }
        adminMenu.display();
    }

    /**
     * Este metodo crea admins
     */
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

        Administrator admin = new Administrator(profile, userName, password, false);
        super.addToArray(admin);
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
                Controller editPermissions = () -> this.editPermissions((Administrator) this.getUser(name, last));
                editMenu.addMenuItem(1, new MenuItem("Edit name", editName));
                editMenu.addMenuItem(2, new MenuItem("Edit lastname", editLast));
                editMenu.addMenuItem(3, new MenuItem("Edit birth date", editBirth));
                editMenu.addMenuItem(4, new MenuItem("Edit permissions ", editPermissions));
                editMenu.display();
            } else {
                System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Administrator did not found\n");
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "There are not users registered\n");
        }
    }

    public void editPermissions(Administrator administrator) {
        System.out.println("Admin permissions");
        administrator.getPermissions().clear();
        IntegerValidator valOption = (value) -> value > 0 && value <= 2;
        int option = reader.readInt("Admin can delete\n1)Yes\n2)No\nOption", valOption);
        int option2 = reader.readInt("Admin can read\n1)Yes\n2)No\nOption", valOption);
        int option3 = reader.readInt("Admin can write\n1)Yes\n2)No\nOption", valOption);
        if (option == 1) {
            administrator.setDelete();
        }
        if (option2 == 1) {
            administrator.setRead();
        }
        if (option3 == 1) {
            administrator.setWrite();
        }
    }

    @Override
    public void getArray() {
        if (!UserRepository.users.isEmpty()) {
            System.out.printf("%30s %n----------------------------------------------%n", "Administrators");
            System.out.printf("| %-12s | %-12s | %-12s |%n", "Name", "Lastname", "Birth date");
            for (User user : UserRepository.users) {
                if (user instanceof Administrator) {
                    System.out.printf("| %-12s | %-12s | %-12s |%n" +
                            "----------------------------------------------%n", user.getProfile().getName(), user.getProfile().getLastName(), user.getProfile().getBirthDate().dateString());
                }
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Not users registered\n");
        }
    }
    public static void log(){
        System.out.print("Username: ");
        String userName = scanner.nextLine();
        for (User user : UserRepository.users) {
            if (user.getUserName().equals(userName)) {
                System.out.print("Password: ");
                String password = scanner.nextLine();
                if (User.checkPassword(password, user)) {
                    if (user instanceof Administrator) {
                        AuthorController.adm = (Administrator) user;
                        BookController.adm = (Administrator) user;
                        AdministratorController.adm = (Administrator) user;
                        AdministratorController.mainMenu((Administrator) user);
                        ClientController.adm = (Administrator) user;
                    } else {
                        AdministratorController.clientMenu((Client) user);
                    }
                }
            }
        }
    }
}
