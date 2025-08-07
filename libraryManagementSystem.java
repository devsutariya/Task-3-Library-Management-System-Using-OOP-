import java.util.ArrayList;
import java.util.Scanner;

// Book Class
class Book {
    private String title;
    private String author;
    private boolean isIssued;
    private User issuedTo;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isIssued = false;
        this.issuedTo = null;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public User getIssuedTo() {
        return issuedTo;
    }


    public void issueBook(User user) {
        this.isIssued = true;
        this.issuedTo = user;
    }

    public void returnBook() {
        this.isIssued = false;
        this.issuedTo = null;
    }

    @Override
    public String toString() {
        if (isIssued) {
            return title + " by " + author + " (Issued to: " + issuedTo.getName() + ")";
        } else {
            return title + " by " + author + " (Available)";
        }
    }
}

// User Class
class User {
    private String name;
    private int userId;

    public User(String name, int userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User ID: " + userId + ", Name: " + name;
    }
}

// Library Class
class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void showBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void issueBook(String title, User user) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isIssued()) {
                book.issueBook(user);
                System.out.println("Book issued to " + user.getName() + " successfully.");
                return;
            }
        }
        System.out.println("Book not available or already issued.");
    }

    public void returnBook(String title, User user) {
       for (Book book : books) {
        if (book.getTitle().equalsIgnoreCase(title)) {
            if (!book.isIssued()) {
                System.out.println("Book is not issued to anyone.");
                return;
            }
            if (book.getIssuedTo().getUserId() == user.getUserId()) {
                book.returnBook();
                System.out.println("Book returned by " + user.getName() + " successfully.");
            } else {
                System.out.println("This book was not issued to you. Return denied.");
            }
            return;
        }
    }
    System.out.println("Book not found.");
}

// Main Class
public class libraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        // Add books data
        library.addBook(new Book("Clean Code", "Robert C. Martin"));
        library.addBook(new Book("Sapiens", "Yuval Noah Harari"));
        library.addBook(new Book("Head First Java", "Kathy Sierra"));

        // Add users data
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Krish", 1));
        users.add(new User("Vinay", 2));

        System.out.println("Welcome to the Library Management System");

        while (true) {
            System.out.println("\n1. Show Books\n2. Issue Book\n3. Return Book\n4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    library.showBooks();
                    break;

                case 2:
                    System.out.println("Enter user ID: ");
                    int issueUserId = sc.nextInt();
                    sc.nextLine();
                    User issueUser = findUserById(users, issueUserId);
                    if (issueUser == null) {
                        System.out.println("User not found.");
                        break;
                    }
                    System.out.print("Enter book title to issue: ");
                    String issueTitle = sc.nextLine();
                    library.issueBook(issueTitle, issueUser);
                    break;

                case 3:
                    System.out.println("Enter user ID: ");
                    int returnUserId = sc.nextInt();
                    sc.nextLine();
                    User returnUser = findUserById(users, returnUserId);
                    if (returnUser == null) {
                        System.out.println("User not found.");
                        break;
                    }
                    System.out.print("Enter book title to return: ");
                    String returnTitle = sc.nextLine();
                    library.returnBook(returnTitle, returnUser);
                    break;

                case 4:
                    System.out.println("Exiting system. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // find user by ID
    private static User findUserById(ArrayList<User> users, int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }
}
}