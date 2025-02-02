import java.util.*;

class Wallet {
    private double balance;
    private List<Operation> operations;
    private Map<String, Double> budgets;

    public Wallet() {
        this.balance = 0.0;
        this.operations = new ArrayList<>();
        this.budgets = new HashMap<>();
    }

    public void addIncome(String category, double amount) {
        if (amount < 0) {
            System.out.println("Ошибка: сумма дохода не может быть отрицательной.");
            return;
        }
        balance += amount;
        operations.add(new Operation("Income", category, amount));
    }

    public void addExpense(String category, double amount) {
        if (amount < 0) {
            System.out.println("Ошибка: сумма расхода не может быть отрицательной.");
            return;
        }
        if (checkBudget(category, amount)) {
            balance -= amount;
            operations.add(new Operation("Expense", category, amount));
            checkBudgetAlert(category);
        }
    }

    public void setBudget(String category, double amount) {
        if (amount < 0) {
            System.out.println("Ошибка: бюджет не может быть отрицательным.");
            return;
        }
        budgets.put(category, amount);
    }

    public boolean checkBudget(String category, double amount) {
        double currentBudget = budgets.getOrDefault(category, 0.0);
        if (currentBudget - amount < 0) {
            System.out.println("Предупреждение: превышен бюджет по категории " + category);
            return false;
        }
        return true;
    }

    public void checkBudgetAlert(String category) {
        double currentBudget = budgets.getOrDefault(category, 0.0);
        double spent = operations.stream()
                .filter(op -> op.getCategory().equals(category) && op.getType().equals("Expense"))
                .mapToDouble(Operation::getAmount)
                .sum();
        if (spent > currentBudget) {
            System.out.println("Оповещение: превышен лимит бюджета по категории " + category);
        }
    }

    public void transfer(Wallet recipient, double amount) {
        if (amount < 0) {
            System.out.println("Ошибка: сумма перевода не может быть отрицательной.");
            return;
        }
        if (this.balance >= amount) {
            this.balance -= amount;
            recipient.balance += amount;
            operations.add(new Operation("Transfer Out", "Transfer", amount));
            recipient.operations.add(new Operation("Transfer In", "Transfer", amount));
        } else {
            System.out.println("Ошибка: недостаточно средств для перевода.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public Map<String, Double> getBudgets() {
        return budgets;
    }
}

class Operation {
    private String type;
    private String category;
    private double amount;

    public Operation(String type, String category, double amount) {
        this.type = type;
        this.category = category;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}
