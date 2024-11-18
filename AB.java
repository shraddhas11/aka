import java.util.ArrayList;
import java.util.Scanner;

class AB {
    static class Contact {
        String name;
        String phone;
        String email;

        Contact(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }


        public String toString() {
            return "Name: " + name + ", Phone: " + phone + ", Email: " + email;
        }
    }

    private final ArrayList<Contact> contacts = new ArrayList<>();

    public void createAddressBook() {
        contacts.clear();
        System.out.println("Address book created successfully.");
    }

    public void viewAddressBook() {
        if (contacts.isEmpty()) {
            System.out.println("Address book is empty.");
        } else {
            System.out.println("Address Book:");
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i));
            }
        }
    }

    public void insertRecord(String name, String phone, String email) {
        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact added successfully.");
    }

    public void deleteRecord(int index) {
        if (index >= 0 && index < contacts.size()) {
            System.out.println("Deleting: " + contacts.get(index));
            contacts.remove(index);
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Invalid index. Contact not found.");
        }
    }

    public void modifyRecord(int index, String name, String phone, String email) {
        if (index >= 0 && index < contacts.size()) {
            System.out.println("Modifying: " + contacts.get(index));
            contacts.set(index, new Contact(name, phone, email));
            System.out.println("Contact modified successfully.");
        } else {
            System.out.println("Invalid index. Contact not found.");
        }
    }

    public void exit() {
        System.out.println("Exiting the address book. Goodbye!");
    }
}

class ABapp {
    public static void main(String[] args) {
        AB addressBook = new AB();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nAddress Book Options:");
            System.out.println("a) Create Address Book");
            System.out.println("b) View Address Book");
            System.out.println("c) Insert a Record");
            System.out.println("d) Delete a Record");
            System.out.println("e) Modify a Record");
            System.out.println("f) Exit");
            System.out.print("Enter your choice: ");
            char choice = scanner.next().toLowerCase().charAt(0);

            switch (choice) {
                case 'a':
                    addressBook.createAddressBook();
                    break;
                case 'b':
                    addressBook.viewAddressBook();
                    break;
                case 'c':
                    System.out.print("Enter name: ");
                    scanner.nextLine(); // Consume newline
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter phone: ");
                    String phone = scanner.nextLine().trim();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine().trim();
                    addressBook.insertRecord(name, phone, email);
                    break;
                case 'd':
                    System.out.print("Enter the index of the record to delete: ");
                    int deleteIndex = scanner.nextInt() - 1;
                    addressBook.deleteRecord(deleteIndex);
                    break;
                case 'e':
                    System.out.print("Enter the index of the record to modify: ");
                    int modifyIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine().trim();
                    System.out.print("Enter new phone: ");
                    String newPhone = scanner.nextLine().trim();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine().trim();
                    addressBook.modifyRecord(modifyIndex, newName, newPhone, newEmail);
                    break;
                case 'f':
                    addressBook.exit();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
