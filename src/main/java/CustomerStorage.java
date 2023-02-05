import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {

    private static final String regexPhoneNumber1 = "[8][-,\\s]?[0-9]{3}[-,\\s]?[0-9]{3}[-,\\s]?[0-9]{2}[-,\\s]?[0-9]{2}";
    private static final String regexPhoneNumber2 = "[+][7][-,\\s]?[0-9]{3}[-,\\s]?[0-9]{3}[-,\\s]?[0-9]{2}[-,\\s]?[0-9]{2}";
    private static final String regexEmail = "([A-Za-z0-9]{1,}[\\\\-]{0,1}[A-Za-z0-9]{1,}[\\\\.]{0,1}[A-Za-z0-9]{1,})+@([A-Za-z0-9]{1,}[\\\\-]{0,1}[A-Za-z0-9]{1,}[\\\\.]{0,1}[A-Za-z0-9]{1,})+[\\\\.]{1}[a-z]{2,4}";
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer (String data) {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;
            String[] components = data.split("\\s+");
            if (components.length != 4) {
                System.out.println("Неверное количество параметров передано в метод add. Необходимое количество элементов - 4.");
                throw new UncorrectCountOfComponents("Неверное количество параметров передано в метод add. Переданное количество", components.length);
            }
            if(!components[INDEX_EMAIL].matches(regexEmail)){
               System.out.println("Введен неверный e-mail. Повторите!");
                throw new UncorrectEmailException("Введен неверный e-mail", components[INDEX_EMAIL]);
            }
            if (!components[INDEX_PHONE].matches(regexPhoneNumber1) && !components[INDEX_PHONE].matches(regexPhoneNumber2)) {
                System.out.println("Введен неверный номер телефон. Повторите!");
                throw new UncorrectNumberException("Неверный номер телефона", components[INDEX_PHONE]);
            }
            String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
            storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));

    }


    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}

class UncorrectCountOfComponents extends IllegalArgumentException{
    public UncorrectCountOfComponents(String message, int lenght){
        super(message + ": " + lenght);
    }

    public UncorrectCountOfComponents(){};
}

class UncorrectEmailException extends IllegalArgumentException{
    public UncorrectEmailException(String message, String email){
        super(message + ": " + email);
    }
    public UncorrectEmailException(){};
}

class UncorrectNumberException extends IllegalArgumentException{
    public UncorrectNumberException(String message, String number ){
        super(message + ": " + number);
    }
    public UncorrectNumberException(){};
}