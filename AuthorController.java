import java.util.Scanner;

public class AuthorController implements ControllerCom, Controller {
    ConsoleReader reader = new ConsoleReader();
    Colors color = new Colors();
    ProfileController profCon = new ProfileController();
    Scanner scanner = new Scanner(System.in);
    public static Administrator adm;

    @Override
    public void execute() {
        Menu authorMenu = new Menu();
        for (Permissions perm : adm.getPermissions()) {
            if (perm.toString().equalsIgnoreCase("WRITE")) {
                authorMenu.addMenuItem(1, new MenuItem("Create author", this::create));
                authorMenu.addMenuItem(2, new MenuItem("Edit authors", this::edit));
            }
            if (perm.toString().equalsIgnoreCase("DELETE")) {
                authorMenu.addMenuItem(3, new MenuItem("Delete author", this::delete));
            }
            if (perm.toString().equalsIgnoreCase("READ")) {
                authorMenu.addMenuItem(4, new MenuItem("Show authors", this::getArray));
            }
        }
            authorMenu.display();
        }
        /**
         * Este metodo crea autores
         */
        @Override
        public void create () {
            System.out.println("Author information");
            Profile profile = profCon.createProfile();
            Author author = new Author(profile);
            this.addToArray(author);
        }

        /**
         * este los elimina
         */
        @Override
        public void delete () {
            if (!AuthorRepository.authors.isEmpty()) {
                StringValidator nameValidator = (value) -> !value.isEmpty();
                String name = reader.readString("Author name", nameValidator);

                StringValidator lastValidator = (value) -> !value.isEmpty();
                String lastName = reader.readString("Author last name", lastValidator);

                if (this.getAuthor(name, lastName) != null) {
                    AuthorRepository.authors.remove(this.getAuthor(name, lastName));
                } else {
                    System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Author did not found\n");
                }
            } else {
                System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Not authors registered\n");
            }
        }


        public void addToArray (Author author){
            AuthorRepository.authors.add(author);
        }


        @Override
        public void edit () {
            if (!AuthorRepository.authors.isEmpty()) {
                this.getArray();
                System.out.println("Author to edit");
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Lastname: ");
                String last = scanner.nextLine();
                if (this.getAuthor(name, last) != null) {
                    Menu editMenu = new Menu();
                    Controller editName = () -> profCon.editName(this.getAuthor(name, last));
                    Controller edidLast = () -> profCon.editLastName(this.getAuthor(name, last));
                    Controller editBirth = () -> profCon.editBirth(this.getAuthor(name, last));
                    editMenu.addMenuItem(1, new MenuItem("Edit name", editName));
                    editMenu.addMenuItem(2, new MenuItem("Edit lastname", edidLast));
                    editMenu.addMenuItem(3, new MenuItem("Edit birth date", editBirth));
                    editMenu.display();
                } else {
                    System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Author did not found\n");
                }
            } else {
                System.out.print(color.getRed() + "ERROR: " + color.getReset() + "There are not authors registered\n");
            }
        }


        @Override
        public void getArray () {
            if (!AuthorRepository.authors.isEmpty()) {
                System.out.printf(color.getBlue() + "%40s %n" + color.getReset() + "--------------------------------------------------------------------------\n", ">>> Author <<<");
                System.out.printf("| %-12s | %-12s | %-12s | %-25s |%n" +
                        "--------------------------------------------------------------------------%n", "Name", "Lastname", "Birth date", "Books");
                for (Author author : AuthorRepository.authors) {
                    String books = this.authorBooks(author);
                    System.out.printf("| %-12s | %-12s | %-12s | %-25s |%n" +
                            "--------------------------------------------------------------------------%n", author.getProfile().getName(), author.getProfile().getLastName(), author.getProfile().getBirthDate().dateString(), books);
                }
            } else {
                System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Not authors registered\n");
            }
        }


        private String authorBooks (Author author){
            String book = "";
            if (author.getBooks().isEmpty()) {
                return "No books";
            } else {
                for (Book books : author.getBooks()) {
                    book += books.getTitle() + "\n----------------------------------------------";
                }
            }
            return book;
        }


        public Author getAuthor (String name, String lastName){
            for (Author author : AuthorRepository.authors) {
                if (author.getProfile().getName().equals(name) && author.getProfile().getLastName().equals(lastName)) {
                    return author;
                }
            }
            return null;
        }
    }