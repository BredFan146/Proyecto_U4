import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Auxiliary{
    Scanner scanner = new Scanner(System.in);
    public static String hashString(String input) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");


            byte[] hash = md.digest(input.getBytes());


            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not found.");
            return null;
        }
    }


    public boolean checkPass(String userName) {
        for (User user : UserRepository.users) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
}
