import java.util.ArrayList;

public class Author {
    private Profile profile;
    private ArrayList<Book> books = new ArrayList<>();

    public Author(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(Book books) {
        this.books.add(books);
    }
}
