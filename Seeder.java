public class Seeder {
    public void initialize() {
        //Authors
        /// Author 1
        Date date1 = new Date(30,05,1967);
        Profile profile1 = new Profile("J.K", "Rowling", date1);
        Author author1 = new Author(profile1 );
        Date date1a = new Date(6, 3, 1927);
        Book book1 = new Book("Harry Potter y la piedra filosofal", "2763", date1a, author1, true);
        author1.setBooks(book1);
        AuthorRepository.authors.add(author1);
        BookRepository.books.add(book1);

        /// Author 2
        Date date2 = new Date(26, 06, 1997);
        Profile profile2 = new Profile("Gabriel", "Marquez", date2);
        Author author2 = new Author(profile2);
        Date date2a = new Date(30, 05,1965);
        Book book2 = new Book("Cien años de soledad", "9867", date2a, author2, true);
        author2.setBooks(book2);
        AuthorRepository.authors.add(author2);
        BookRepository.books.add(book2);

        /////////////////////////////////////////////////////////////////////////

        //Clients
        /// Client 1

        Date date3 = new Date(04,10,2003);
        Profile profile3 = new Profile("juan", "alberto", date3);
        Client client1 = new Client(profile3, "wanalberto", "berto");
        UserRepository.users.add(client1);

        /// Client 2
        Date date4 = new Date(28, 12, 2012);
        Profile profile4 = new Profile("Cristiano", "Ronaldo", date4);
        Client client2 = new Client(profile4, "cr7", "cr7");
        UserRepository.users.add(client2);

        //Admins
        // super Admin hacer el constructor
        Date date5 = new Date(22,03,1985);
        Profile profile5 = new Profile("Sebastian", "Damian", date5);
        Administrator superAdministrator = new Administrator(profile5, "sebas971", "sebas", true);
        superAdministrator.setRead();
        superAdministrator.setDelete();
        superAdministrator.setWrite();
        UserRepository.users.add(superAdministrator);

        // admin1
        Date date6 = new Date(25, 07, 1990);
        Profile profile6 = new Profile("daisy", "peisy", date6);
        Administrator administrator = new Administrator(profile6, "peisy", "peisy", false);
        administrator.setWrite();
        administrator.setRead();
        UserRepository.users.add(administrator);
    }
}
