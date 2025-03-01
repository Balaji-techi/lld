package Practice.Library;

import java.util.*;

public class LibraryManagementSystem {
    static List<Book> books = new ArrayList<>();
    static List<Member> members = new ArrayList<>();
    static Member loggedInMember = null;

    static final String adminUsername = "admin";
    static final String adminPassword = "password";

    static final String userUsername="user";
    static final String userPassword="password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to Library Management System");
            System.out.println("Please choose an option:");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1/2/3): ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1: loginAsAdmin(sc);break;
                case 2: loginAsUser(sc);break;
                case 3: System.out.println("we'r Leaving the Sysytem..Thanks");break;
                default:
                    System.out.println("Invalid Chice please choose (1-3)");
            }
        }
    }
    private static void loginAsAdmin(Scanner scanner) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            System.out.println("Logged in Library Admin.");
            adminMenu(scanner);
        } else System.out.println("Invalid data");

    }
    private static void loginAsUser(Scanner scanner) {
        System.out.print("Enter User Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter User Password:");
        String password=scanner.nextLine();

        if (username.equals(userUsername) && password.equals(userPassword)){
            System.out.println("Logged in a Library User");
            userMenu(scanner);
        }else System.out.println("Invalid data");
    }
    private static void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Remove Book");
            System.out.println("4. Add Member");
            System.out.println("5. Display All Books");
            System.out.println("6. Display All Members");
            System.out.println("7. Logout");
            System.out.print("Enter your choice (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                addBook(scanner);
            } else if (choice == 2) {
                updateBook(scanner);
            } else if (choice == 3) {
                removeBook(scanner);
            } else if (choice == 4) {
                addMember(scanner);
            } else if (choice == 5) {
                displayAllBooks(scanner);
            } else if (choice == 6) {
                displayAllMembers(scanner);
            } else if (choice == 7) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void userMenu(Scanner sc) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Display Borrowed Books");
            System.out.println("5. Logout");
            System.out.print("Enter your choice (1-5): ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:borrowBook(sc);break;
                case 2:returnBook(sc);break;
                case 3:displayAllBooks(sc);break;
                case 4:displayAllMembers(sc);break;
                case 5:
                    System.out.println("Logging out..");break;
                default:
                    System.out.println("Invalid choice please enter (1-5)");
            }
        }
    }
    private static void addBook(Scanner scanner) {
        System.out.println("*************Please provide the following details to add a book:*************");
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book genre: ");
        String genre = scanner.nextLine();

        books.add(new Book(title, author, genre));
        System.out.println("Book added successfully.");
    }
    private static void updateBook(Scanner scanner) {
        System.out.println("*************Please provide the following details to update a book:*************");

        System.out.print("Enter the book title you want to update: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);
        if (book != null) {
            System.out.print("Enter new author: ");
            String newAuthor = scanner.nextLine();
            System.out.print("Enter new genre: ");
            String newGenre = scanner.nextLine();

            book.author = newAuthor;
            book.genre = newGenre;
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }
    private static void removeBook(Scanner scanner) {
        System.out.println("*************Please provide the following details to remove a book:*************");
        System.out.print("Enter the book title you want to remove: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);
        if (book != null) {
            books.remove(book);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }
    private static void addMember(Scanner scanner) {
        System.out.print("Enter member username: ");
        String username = scanner.nextLine();
//        System.out.println("");
        members.add(new Member(username));
        System.out.println("Member added successfully.");
    }
    private static void displayAllMembers(Scanner scanner) {
        if (members.isEmpty()) {
            System.out.println("No members in the system.");
        } else {
            System.out.println("\nList of Members:");
            for (Member member : members) {
                System.out.println("Username: " + member.username);
            }
        }
    }
    private static void borrowBook(Scanner scanner) {
        System.out.print("Enter book title you want to borrow: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);
        if (book != null && loggedInMember.borrowBook(book)) {
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Book is either unavailable or you have reached your borrowing limit.");
        }
    }
    private static void returnBook(Scanner scanner) {
        System.out.print("Enter book title you want to return: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);
        if (book != null && !book.isAvailable) {
            loggedInMember.returnBook(book);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book not found or it wasn't borrowed.");
        }
    }
    private static void displayAllBooks(Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("\nList of Books:");
            for (Book book : books) {
                System.out.println("Title: " + book.title + ", Author: " + book.author + ", Genre: " + book.genre + ", Available: " + book.isAvailable);
            }
        }
    }
    private static void displayBorrowedBooks(Scanner scanner) {
        if (loggedInMember.borrowedBooks.isEmpty()) {
            System.out.println("no books you borrowed.");
        } else {
            System.out.println("\nYour borrowed Books:");
            for (Book book : loggedInMember.borrowedBooks) {
                System.out.println(book.title);
            }
        }
    }
    private static Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
    private static Member findOrCreateMember(String username) {
        for (Member member : members) {
            if (member.username.equals(username)) {
                return member;
            }
        }
        Member newMember = new Member(username);
        members.add(newMember);
        return newMember;
    }
}
class Book {
    String title;
    String author;
    String genre;
    boolean isAvailable;
    Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }
}
class Member {
    String username;
    List<Book> borrowedBooks;
    Member(String username) {
        this.username = username;
        this.borrowedBooks = new ArrayList<>();
    }
    boolean borrowBook(Book book) {
        if (borrowedBooks.size() < 5 && book.isAvailable) {
            borrowedBooks.add(book);
            book.isAvailable = false;
            return true;
        }
        return false;
    }
    void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.isAvailable = true;
    }
}