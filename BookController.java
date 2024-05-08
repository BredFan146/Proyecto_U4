import java.util.Scanner;

public class BookController implements ControllerCom, Controller {
    ConsoleReader reader = new ConsoleReader();
    Colors color = new Colors();
    Scanner scanner = new Scanner(System.in);
    AuthorController authCon;
    public static Administrator adm;

    @Override
    public void execute() {
        Menu bookMenu = new Menu();
        boolean write = false;

        for (Permissions perm : adm.getPermissions()) {
            if (perm.toString().equalsIgnoreCase("WRITE")) {
                bookMenu.addMenuItem(1, new MenuItem("Create book", this::create));
                bookMenu.addMenuItem(2, new MenuItem("Edit book", this::edit));
            }
            if (perm.toString().equalsIgnoreCase("DELETE")) {
                bookMenu.addMenuItem(3, new MenuItem("Delete book", this::delete));
            }
            if (perm.toString().equalsIgnoreCase("READ")) {
                bookMenu.addMenuItem(4, new MenuItem("Show book", this::showBooks));
            }
        }
        bookMenu.display();
    }

    @Override
    public void create() {
        if (!AuthorRepository.authors.isEmpty()) {
            String isbn;

            System.out.println("Book information");

            StringValidator titleValidator = (value) -> !value.isEmpty();
            String title = reader.readString("Book title", titleValidator);

            StringValidator isbnValidator = (value) -> {
                for (int i = 0; i < value.length(); i++) {
                    return Character.isDigit(value.charAt(i));
                }
                return false;
            };

            isbn = reader.readString("ISBN", isbnValidator);

            System.out.println("Book publish date");

            IntegerValidator dayValidator = (value) -> value > 0 && value <= 31;
            int day = reader.readInt("Day (DD)", dayValidator);

            IntegerValidator monthValidator = (value) -> value > 0 && value <= 12;
            int month = reader.readInt("Mes (MM)", monthValidator);

            IntegerValidator yearValidator = (value) -> value > 999 && value <= 9999;
            int year = reader.readInt("Year (YYYY)", yearValidator);

            authCon.getArray();
            System.out.print("Choose an author");

            StringValidator nameValidator = (value) -> !value.isEmpty();
            String name = reader.readString("Name", nameValidator);

            StringValidator lastValidator = (value) -> !value.isEmpty();
            String lastName = reader.readString("Last name", lastValidator);

            if(authCon.getAuthor(name, lastName) != null) {
                Date date = new Date(day, month, year);
                Book book = new Book(title, isbn, date, authCon.getAuthor(name, lastName), true);
                this.addToArray(book);
            } else {
                System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Author did not found\n");
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "There are not authors\n");
        }
    }


    @Override
    public void delete() {
        if (!BookRepository.books.isEmpty()) {
            String isbn;
            StringValidator isbnValidator = (value) -> !value.isEmpty();
            isbn = reader.readString("ISBN of the book to delete", isbnValidator);
            if (!reader.confirmIsbn(isbn)) {
                System.out.println("Book did not found");
            } else {
                BookRepository.books.remove(this.getBook(isbn));
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "There are not books\n");
        }
    }


    public void addToArray(Book book) {
        BookRepository.books.add(book);
    }


    @Override
    public void edit() {
        if (!BookRepository.books.isEmpty()) {
            this.getAvailableArray();
            System.out.println("Book to edit");
            System.out.print("isbn: ");
            String isbn = scanner.nextLine();
            if (this.getBook(isbn) != null) {

                Menu editBook = new Menu();
                Controller editTitle = () -> this.editTitle(this.getBook(isbn));
                Controller editIsbn = () -> this.editIsbn(this.getBook(isbn));
                Controller editPublish = () -> this.editPublishDate(this.getBook(isbn));
                editBook.addMenuItem(1, new MenuItem("Edit title", editTitle));
                editBook.addMenuItem(1, new MenuItem("Edit title", editIsbn));
                editBook.addMenuItem(1, new MenuItem("Edit title", editPublish));
            } else {
                System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Book did not found\n");
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "There are not books\n");
        }
    }

    private void editTitle(Book book) {
        StringValidator titleValidator = (value) -> !value.isEmpty();
        String title = reader.readString("Book title", titleValidator);
        book.setTitle(title);
    }
    private void editIsbn(Book book){
        String isbn;
        StringValidator isbnValidator = (value) -> {
            for (int i = 0; i < value.length(); i++) {
                return Character.isDigit(value.charAt(i));
            }
            return false;
        };

        isbn = reader.readString("ISBN", isbnValidator);
        book.setIsbn(isbn);
    }
    private void editPublishDate(Book book){
        System.out.println("Book publish date");

        IntegerValidator dayValidator = (value) -> value > 0 && value <= 31;
        int day = reader.readInt("Day (DD)", dayValidator);

        IntegerValidator monthValidator = (value) -> value > 0 && value <= 12;
        int month = reader.readInt("Mes (MM)", monthValidator);

        IntegerValidator yearValidator = (value) -> value > 999 && value <= 9999;
        int year = reader.readInt("Year (YYYY)", yearValidator);

        Date date = new Date(day, month, year);
        book.setPublishDate(date);
    }


    public void showBooks() {
        Menu showBookMenu = new Menu();
        showBookMenu.addMenuItem(1, new MenuItem("All books", this::getArray));
        showBookMenu.addMenuItem(2, new MenuItem("Available books", this::getAvailableArray));
        showBookMenu.addMenuItem(3, new MenuItem("Borrowed books", this::getBorrowedBooks));
        showBookMenu.display();
    }


    @Override
    public void getArray() {
        if (!BookRepository.books.isEmpty()) {
            System.out.printf(color.getBlue() + "%50s " + color.getReset() + "%n-------------------------------------------------------------------------------------%n", ">>> Books <<<");
            System.out.printf("| %-25s | %-5s | %-12s | %-15s | %-12s |%n" +
                    "-------------------------------------------------------------------------------------%n", "Title", "ISBN", "Publish date", "Author", "Available");
            for (Book books : BookRepository.books) {
                String available;
                if (books.isAvailable()) {
                    available = "Available";
                } else {
                    available = "No available";
                }
                System.out.printf("| %-25s | %-5s | %-12s | %-15s | %-12s |" +
                        "%n-------------------------------------------------------------------------------------%n", books.getTitle(), books.getIsbn(), books.getPublishDate().dateString(), books.getAuthor(), available);
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "There are not books\n");
        }
    }


    public void getAvailableArray() {
        if (!BookRepository.books.isEmpty()) {
            System.out.printf(color.getBlue() + "%50s " + color.getReset() + "%n-------------------------------------------------------------------------------------%n", ">>> Books <<<");
            System.out.printf("| %-25s | %-5s | %-12s | %-15s | %-12s |%n" +
                    "-------------------------------------------------------------------------------------%n", "Title", "ISBN", "Publish date", "Author", "Available");
            for (Book books : BookRepository.books) {
                String available;
                if (books.isAvailable()) {
                    available = "Available";
                    System.out.printf("| %-25s | %-5s | %-12s | %-15s | %-12s |" +
                            "%n-------------------------------------------------------------------------------------%n", books.getTitle(), books.getIsbn(), books.getPublishDate().dateString(), books.getAuthor(), available);
                }
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "There are not books\n");
        }
    }


    public void getBorrowedBooks() {
        if (!BookRepository.books.isEmpty()) {
            System.out.printf(color.getBlue() + "%50s " + color.getReset() + "%n-------------------------------------------------------------------------------------%n", ">>> Books <<<");
            System.out.printf("| %-25s | %-5s | %-12s | %-15s | %-12s |%n" +
                    "-------------------------------------------------------------------------------------%n", "Title", "ISBN", "Publish date", "Author", "Available");
            for (Book books : BookRepository.books) {
                String available;
                if (!books.isAvailable()) {
                    available = "No available";
                    System.out.printf("| %-25s | %-5s | %-12s | %-15s | %-12s |" +
                            "%n-------------------------------------------------------------------------------------%n", books.getTitle(), books.getIsbn(), books.getPublishDate().dateString(), books.getAuthor(), available);
                }
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "There are not books\n");
        }
    }


    public Book getBook(String isbn) {
        for (Book book : BookRepository.books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
}