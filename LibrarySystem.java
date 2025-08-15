import java.util.Scanner;

public class LibrarySystem {
    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Books");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: manager.addBook(); break;
                case 2: manager.addMember(); break;
                case 3: manager.issueBook(); break;
                case 4: manager.returnBook(); break;
                case 5: manager.viewBooks(); break;
                case 0: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
}
