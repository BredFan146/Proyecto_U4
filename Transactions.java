public class Transactions {
    private String id;
    private String type;
    private Client client;
    private Book book;
    private Date date;

    public Transactions(String id, String type, Client client, Book book, Date date) {
        this.id = id;
        this.type = type;
        this.client = client;
        this.book = book;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
