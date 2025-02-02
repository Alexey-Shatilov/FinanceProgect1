import java.util.*;
import java.io.*;

class FinanceManager {
    private Map<String, User> users;
    private User currentUser;

    public FinanceManager() {
        users = new HashMap<>();
    }

    public void registerUser(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Ошибка: пользователь с таким именем уже существует.");
            return;
        }
        users.put(username, new User(username, password));
        System.out.println("Пользователь зарегистрирован.");
    }

    public boolean loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Успешный вход в систему.");
            return true;
        }
        System.out.println("Ошибка: неверное имя пользователя или пароль.");
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.dat"))) {
            oos.writeObject(users);
            System.out.println("Данные успешно сохранены.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.dat"))) {
            users = (Map<String, User>) ois.readObject();
            System.out.println("Данные успешно загружены.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке данных: " + e.getMessage());
        }
    }
}