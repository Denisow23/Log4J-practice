import java.util.Arrays;
import java.util.Scanner;
import org.apache.logging.log4j.*;

public class Main{

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final Marker INPUT_HISTORY_MARKER = MarkerManager.getMarker("INPUT_HISTORY");
        private static final Marker ERROR_HISTORY_MARKER = MarkerManager.getMarker("ERROR_HISTORY");
    private static final String ADD_COMMAND = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static final String COMMAND_EXAMPLES = "\t" + ADD_COMMAND + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров";
    private static final String COMMAND_ERROR = "Wrong command! Available command examples: \n" +
            COMMAND_EXAMPLES;
    private static final String helpText = "Command examples:\n" + COMMAND_EXAMPLES;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();

        while (true) {
            try {
                String command = scanner.nextLine();
                LOGGER.info(INPUT_HISTORY_MARKER, "Пользователь ввел следующий запрос: {}", command);
                String[] tokens = command.split("\\s+", 2);
                if (tokens[0].equals("add")) {
                    try {
                        executor.addCustomer(tokens[1]);
                    } catch (IllegalArgumentException ex){
                        LOGGER.info(ERROR_HISTORY_MARKER, ex.getMessage());
                    }
                } else if (tokens[0].equals("list")) {
                    executor.listCustomers();
                } else if (tokens[0].equals("remove")) {
                    executor.removeCustomer(tokens[1]);
                } else if (tokens[0].equals("count")) {
                    System.out.println("There are " + executor.getCount() + " customers");
                } else if (tokens[0].equals("help")) {
                    System.out.println(helpText);
                } else {
                    System.out.println(COMMAND_ERROR);
                }
            }catch (ArrayIndexOutOfBoundsException ex){
                LOGGER.error(ERROR_HISTORY_MARKER, "Введена команда add без параметров");
            }
            catch (Exception ex){
                LOGGER.error(ERROR_HISTORY_MARKER, ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
                return;
            }
        }
    }
}
