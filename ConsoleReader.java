import java.util.Scanner;
public class ConsoleReader {
    Scanner scanner = new Scanner(System.in);
    Colors color = new Colors();
    public int readInt(String prompt, IntegerValidator validator){
        boolean valid = false;
        while (!valid){
            System.out.print(prompt +  ": ");
            int val = scanner.nextInt();
            scanner.nextLine();
            boolean isValid = validator.validate(val);
            if(isValid) return val;
            else System.out.println(color.getRed() + "Error: " + color.getReset() + "Invalid option");
        }
        return 0;
    }
    public String readString(String prompt, StringValidator validator){
        boolean valid = false;
        String val = "";
        while (!valid){
            System.out.print(prompt + ": ");
            val = scanner.nextLine();
            boolean isValid = validator.validate(val);
            if (isValid) return val;
            else System.out.println(color.getRed() + "Error: " + color.getReset() + "Invalid option");

        }
        return "";
    }
    public boolean confirmIsbn(String isbn){
        for (Book book: BookRepository.books){
            if(book.getIsbn().equals(isbn)){
                return true;
            }
        }
        return false;
    }

}