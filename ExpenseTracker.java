import java.io.*;
import java.util.*;

public class ExpenseTracker {

    private static final String DATA_FILE = "expense_data.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Expense> expenses = loadExpenses();

        while (true) {
            System.out.println("Expense Tracker Menu:");
            System.out.println("1. Register User");
            System.out.println("2. Add Expense");
            System.out.println("3. List Expenses");
            System.out.println("4. Category Wise Summation");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    addExpense(scanner, expenses);
                    break;
                case 3:
                    listExpenses(expenses);
                    break;
                case 4:
                    categoryWiseSummation(expenses);
                    break;
                case 5:
                    saveExpenses(expenses);
                    System.out.println("Exiting Expense Tracker. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.println("User '" + username + "' registered successfully!");
    }

    private static void addExpense(Scanner scanner, List<Expense> expenses) {
        System.out.print("Enter expense description: ");
        String description = scanner.next();
        System.out.print("Enter expense amount: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter expense category: ");
        String category = scanner.next();

        Expense expense = new Expense(description, amount, category);
        expenses.add(expense);

        System.out.println("Expense added successfully!");
    }

    private static void listExpenses(List<Expense> expenses) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to display.");
        } else {
            System.out.println("Expense List:");
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    private static void categoryWiseSummation(List<Expense> expenses) {
        Map<String, Double> categorySumMap = new HashMap<>();

        for (Expense expense : expenses) {
            String category = expense.getCategory();
            double amount = categorySumMap.getOrDefault(category, 0.0);
            categorySumMap.put(category, amount + expense.getAmount());
        }

        System.out.println("Category Wise Summation:");
        for (Map.Entry<String, Double> entry : categorySumMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            expenses = (List<Expense>) ois.readObject();
            System.out.println("Expense data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading expense data. Starting fresh.");
        }

        return expenses;
    }

    private static void saveExpenses(List<Expense> expenses) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(expenses);
            System.out.println("Expense data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving expense data.");
        }
    }
}

class Expense implements Serializable {
    private String description;
    private double amount;
    private String category;

    public Expense(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                '}';
    }
}
