import java.util.ArrayList;

public class Client extends User{
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public Client(Profile profile, String userName, String password) {
        super(profile, userName, password);
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Book borrowedBooks) {
        this.borrowedBooks.add(borrowedBooks);
    }
}
