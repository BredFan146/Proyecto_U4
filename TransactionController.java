import java.util.Random;
import java.util.Scanner;

public class TransactionController implements ControllerCom, Controller {
    Colors color = new Colors();
    ConsoleReader reader = new ConsoleReader();
    ClientController client = new ClientController();
    BookController bookCont = new BookController();
    Random random = new Random();
    Scanner scanner = new Scanner(System.in);
    @Override
    public void execute() {
        Menu transactionMenu = new Menu();
        transactionMenu.addMenuItem(1, new MenuItem("Create transaction", this::create));
        transactionMenu.addMenuItem(2, new MenuItem("Show transactions", this::getArray));
        transactionMenu.display();
    }

    @Override
    public void create() {
        if (!UserRepository.users.isEmpty()) {
            Menu transaction = new Menu();
            transaction.addMenuItem(1, new MenuItem("Borrow a book", this::borrowBook));
            transaction.addMenuItem(2, new MenuItem("Return a book", this::returnBook));

        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "There are not users to borrow books\n");
        }
    }


    @Override
    public void delete() {
    }


    @Override
    public void edit() {
    }


    @Override
    public void getArray() {
        Menu getArrayMenu = new Menu();
        getArrayMenu.addMenuItem(1, new MenuItem("Client report", this::report));
        getArrayMenu.addMenuItem(2, new MenuItem("Client report", this::clientReport));
        getArrayMenu.display();
    }
    public void clientReport(){
        if (!TransactionRepository.transactions.isEmpty()){
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Lastname: ");
            String last = scanner.nextLine();
            ClientController client = null;
            Client client1 = (Client) client.getUser(name, last);
            System.out.printf("%s", "Client");
            System.out.printf("| %-15 | %-15s | %-8s | %-12s%n" +
                    "------------------------------------------------------------------%n", "Client", "Book", "Type", "Date");
            for (Transactions transaction: TransactionRepository.transactions){
                if (transaction.getClient().equals(client1)){
                    System.out.printf("| %-15 | %-15s | %-8s | %-12s%n" +
                            "------------------------------------------------------------------%n", transaction.getClient().getProfile().getName(), transaction.getBook().getTitle(), transaction.getType(), transaction.getDate().dateString());
                }
            }
        } else {
            System.out.print(color.getRed() + "ERROR: " + color.getReset() + "Client does not have transactions\n");
        }
    }
    public void report(){
        if (!TransactionRepository.transactions.isEmpty()){
            System.out.printf("%s", "Client");
            System.out.printf("| %-15 | %-15s | %-8s | %-12s%n" +
                    "------------------------------------------------------------------%n", "Client", "Book", "Type", "Date");
            for (Transactions trans: TransactionRepository.transactions){
                System.out.printf("| %-15 | %-15s | %-8s | %-12s%n" +
                        "------------------------------------------------------------------%n", trans.getClient().getProfile().getName(), trans.getBook().getTitle(), trans.getType(), trans.getDate().dateString());
            }
        } else {
            System.out.print(color.getRed() + "--ERROR: " + color.getReset() + "Client does not have transactions\n");
        }
    }
    public void borrowBook() {
        client.getArray();
        Book book = null;
        System.out.println("Client to borrow book");
        StringValidator nameValidator = (value) -> !value.isEmpty();
        String name = reader.readString("Name", nameValidator);

        StringValidator lastValidator = (value) -> !value.isEmpty();
        String lastName = reader.readString("Last name", lastValidator);

        if (client.getUser(name, lastName) != null) {
            String isbn;

            StringValidator isbnValidator = (value) -> !value.isEmpty();
            isbn = reader.readString("ISBN of the book to delete", isbnValidator);

            for (Book books : BookRepository.books) {
                if (books.getIsbn().equals(isbn)) {
                    book = books;
                }
            }
            if (book != null) {
                for (User client1 : UserRepository.users) {
                    if (client1 instanceof Client) {
                        ((Client) client1).setBorrowedBooks(book);
                        book.setAvailable(false);
                        Date date = null;
                        date.setBorrowedDate();
                        Transactions transactions = new Transactions(this.generateId(), "Borrow", (Client) client1, book, date);
                        TransactionRepository.transactions.add(transactions);
                    }
                }
            } else {
                System.out.print(color.getRed() + "--ERROR: " + color.getReset() + "Book did not found\n");
            }

        } else {
            System.out.print(color.getRed() + "--ERROR: " + color.getReset() + "Client did not found\n");
        }
    }
    public void returnBook(){
        String isbn;

        bookCont.getBorrowedBooks();
        StringValidator isbnValidator = (value) -> !value.isEmpty();
        isbn = reader.readString("ISBN of the book to delete", isbnValidator);

        if(bookCont.getBook(isbn) != null){
            for (Book books: BookRepository.books){
                if (books.getIsbn().equals(isbn)){
                    for (User client3: UserRepository.users){
                        if (client3 instanceof Client){
                            ((Client) client3).getBorrowedBooks().remove(books);
                            Date date = null;
                            Transactions transactions = new Transactions(this.generateId(), "Borrow", (Client) client3, books, date);
                            TransactionRepository.transactions.add(transactions);
                        }
                    }
                }
            }
        }
    }
    private String generateId() {
        String id = "";
        boolean rep = false;
        while (!rep) {
            for (int i = 0; i < 6; i++) {
                int ran = random.nextInt(10);
                String ids = String.valueOf(ran);
                id += ids;
            }
            if (!TransactionRepository.transactions.isEmpty()) {
                for (Transactions transactionId : TransactionRepository.transactions) {
                    if (transactionId.getId().equalsIgnoreCase(id)) {
                        id = generateId();
                        rep = true;
                    } else {
                        rep = false;
                    }
                }
            } else {
                rep = true;
            }
        }
        return id;
    }
}