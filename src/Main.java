import java.util.*;

public class Main {
    public static void main(String[] args) {
        FinanceManager financeManager = new FinanceManager();
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("Введите команду (register, login, exit): ");
            command = scanner.nextLine();

            switch (command) {
                case "register":
                    System.out.print("Введите имя пользователя: ");
                    String username = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    financeManager.registerUser(username, password);
                    break;

                case "login":
                    System.out.print("Введите имя пользователя: ");
                    username = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    password = scanner.nextLine();
                    if (financeManager.loginUser(username, password)) {
                        User user = financeManager.getCurrentUser();
                        String userCommand;
                        while (true) {
                            System.out.print("Введите команду (add_income, add_expense, set_budget, transfer, save, load, logout): ");
                            userCommand = scanner.nextLine();

                            switch (userCommand) {
                                case "add_income":
                                    System.out.print("Введите категорию: ");
                                    String incomeCategory = scanner.nextLine();
                                    System.out.print("Введите сумму: ");
                                    double incomeAmount = scanner.nextDouble();
                                    scanner.nextLine(); // consume newline
                                    user.getWallet().addIncome(incomeCategory, incomeAmount);
                                    break;

                                case "add_expense":
                                    System.out.print("Введите категорию: ");
                                    String expenseCategory = scanner.nextLine();
                                    System.out.print("Введите сумму: ");
                                    double expenseAmount = scanner.nextDouble();
                                    scanner.nextLine(); // consume newline
                                    user.getWallet().addExpense(expenseCategory, expenseAmount);
                                    break;

                                case "set_budget":
                                    System.out.print("Введите категорию: ");
                                    String budgetCategory = scanner.nextLine();
                                    System.out.print("Введите сумму бюджета: ");
                                    double budgetAmount = scanner.nextDouble();
                                    scanner.nextLine(); // consume newline
                                    user.getWallet().setBudget(budgetCategory, budgetAmount);
                                    break;

                                case "transfer":
                                    System.out.print("Введите имя пользователя получателя: ");
                                    String recipientUsername = scanner.nextLine();
                                    User recipient = financeManager.getCurrentUser(); // Здесь нужно найти пользователя по имени
                                    if (recipient != null) {
                                        System.out.print("Введите сумму перевода: ");
                                        double transferAmount = scanner.nextDouble();
                                        scanner.nextLine(); // consume newline
                                        user.getWallet().transfer(recipient.getWallet(), transferAmount);
                                    } else {
                                        System.out.println("Получатель не найден.");
                                    }
                                    break;

                                case "save":
                                    financeManager.saveData();
                                    break;

                                case "load":
                                    financeManager.loadData();
                                    break;

                                case "logout":
                                    financeManager.getCurrentUser(); // сброс текущего пользователя
                                    break;

                                default:
                                    System.out.println("Неизвестная команда.");
                            }
                        }
                    }
                    break;

                case "exit":
                    System.out.println("Выход из программы.");
                    return;

                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }
}