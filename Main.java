import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Auxiliary aux = new Auxiliary();
        Seeder seeder = new Seeder();
        seeder.initialize();
        Scanner scanner = new Scanner(System.in);
        boolean x = true;
        while (x) {
            AdministratorController.log();
        }
    }
}