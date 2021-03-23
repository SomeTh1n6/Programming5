package com;
import java.io.IOException;
import java.util.*;

public class CommandSelector {

    /** Запуск интерактивного режима программы
     * @param data - абсолютный путь до файла с информацией о коллекции
     * @param history - очередь с историей команд
     * @exception ArrayIndexOutOfBoundsException - отсутстиве аргумента
     * @throws IOException - для CollectionManager
     **/
    public void start(String data, Queue<String> history) throws IOException {

        CollectionManager manager = new CollectionManager(data);
        String userCommand = null;
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            userCommand = scanner.nextLine(); // строка , введенная пользователем
        }
        else{
            System.out.println("Ошибка ввода");
            System.exit(1);
        }
        String[] finalUserCommand = userCommand.trim().split(" ", 2); // разбиение этой строки на 2 подстроки (команда и агрумент к ней)
        while (!finalUserCommand[0].equals("exit")) {
            try {
                switch (finalUserCommand[0]) {
                    case "":
                        break;
                    case "help":
                        manager.help();
                        break;
                    case "info":
                        System.out.println(manager.toString());
                        break;
                    case "show":
                        manager.show();
                        break;
                    case "add":
                        Scanner scanner1 = new Scanner(System.in);
                        manager.add(scanner1);
                        break;
                    case "update":
                        Scanner scanner2 = new Scanner(System.in);
                        manager.update(Integer.parseInt(finalUserCommand[1]),scanner2);
                        break;
                    case "execute_script":
                        LinkedList <String> es = new LinkedList<>();
                        es.add(finalUserCommand[1]);
                        history.add(finalUserCommand[0]); // сделано, чтобы работа history была корректной
                        if (history.size() > 6)
                            history.remove();
                        manager.execute_script(manager,finalUserCommand[1],data,history,es);
                        break;
                    case "save":
                        manager.save();
                        break;
                    case "history":
                        if (history.size() == 0)
                            System.out.println("История команд пуста!");
                        history.forEach(p -> System.out.println(p));
                        break;
                    case "clear":
                        manager.clear();
                        break;
                    case "remove_by_id":
                        while (!manager.isNumberInteger(finalUserCommand[1])){
                            System.out.println("Введите корректные значения (id - целое число)");
                            finalUserCommand = scanner.nextLine().trim().split(" ", 2);
                        }
                        manager.remove_by_id(Integer.parseInt(finalUserCommand[1]));
                        break;
                    case "head":
                        manager.head();
                        break;
                    case "remove_head":
                        manager.head();
                        manager.remove_head();
                        break;
                    case "average_of_salary":
                        manager.average_of_salary();
                        break;
                    case "filter_starts_with_name":
                        manager.filter_starts_with_name(finalUserCommand[1]);
                        break;
                    case "print_ascending":
                        manager.print_ascending(finalUserCommand[1]);
                        break;
                    default:
                        System.out.println("Неопознанная команда. Введите 'help' для справки.");
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Отсутствует аргумент.");
            }
            if (manager.checkCommand(finalUserCommand[0]) && !finalUserCommand[0].equals("execute_script")){
                history.add(finalUserCommand[0]);
                if (history.size() > 6)
                    history.remove();
            }
            if (scanner.hasNextLine()) {
                userCommand = scanner.nextLine(); // строка , введенная пользователем
            }
            else{
                System.out.println("Ошибка ввода");
                System.exit(1);
            }
            finalUserCommand = userCommand.trim().split(" ", 2);
        }
    }
}

