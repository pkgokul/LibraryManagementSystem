import java.sql.*;
import java.util.Scanner;

public class LibraryManager {
    Scanner sc = new Scanner(System.in);

    public void addBook() {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter book title: ");
            String title = sc.nextLine();
            System.out.print("Enter author name: ");
            String author = sc.nextLine();

            String sql = "INSERT INTO books(title, author) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMember() {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter member name: ");
            String name = sc.nextLine();
            System.out.print("Enter email: ");
            String email = sc.nextLine();

            String sql = "INSERT INTO members(name, email) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("Member added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void issueBook() {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter book ID: ");
            int bookId = sc.nextInt();
            System.out.print("Enter member ID: ");
            int memberId = sc.nextInt();
            sc.nextLine(); // consume newline

            String checkSql = "SELECT available FROM books WHERE book_id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkSql);
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && !rs.getBoolean("available")) {
                System.out.println("Book is not available.");
                return;
            }

            String loanSql = "INSERT INTO loans(book_id, member_id, issue_date) VALUES (?, ?, CURDATE())";
            PreparedStatement loanStmt = con.prepareStatement(loanSql);
            loanStmt.setInt(1, bookId);
            loanStmt.setInt(2, memberId);
            loanStmt.executeUpdate();

            String updateSql = "UPDATE books SET available = false WHERE book_id = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateSql);
            updateStmt.setInt(1, bookId);
            updateStmt.executeUpdate();

            System.out.println("Book issued successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook() {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter book ID to return: ");
            int bookId = sc.nextInt();
            sc.nextLine();

            String sql = "UPDATE books SET available = true WHERE book_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, bookId);
            stmt.executeUpdate();

            String updateLoan = "UPDATE loans SET return_date = CURDATE() WHERE book_id = ? AND return_date IS NULL";
            PreparedStatement loanStmt = con.prepareStatement(updateLoan);
            loanStmt.setInt(1, bookId);
            loanStmt.executeUpdate();

            System.out.println("Book returned successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewBooks() {
        try (Connection con = DBConnection.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");

            System.out.println("Book List:");
            while (rs.next()) {
                System.out.printf("ID: %d, Title: %s, Author: %s, Available: %b%n",
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("available"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
