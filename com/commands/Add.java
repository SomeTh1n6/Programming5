package com.commands;

import com.worker.Worker;
import com.worker.properties.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;

public class Add {

    IsNumber checkNumber = new IsNumber();
    RandomLocalDateTime randomLocalDateTime = new RandomLocalDateTime();
    Status status = null;

    /** Добавление нового сотрудника в коллекцию
     * @param workers коллекция, куда мы добавляем работника
     * */
    public void execute(LinkedList<Worker> workers, Scanner scanner) {

        String line = null;
        String name = "NoName";
        Integer id = null;
        int x = 0;
        float y = 0;
        double salary = 1;
        Position position = null;

        // Выдаем уникальный id
        TreeSet<Integer> usedId = new TreeSet<>();
        if (!workers.isEmpty()) {
            for (Worker worker :
                    workers) {
                usedId.add(worker.getId());
            }
            if (usedId.size() == 2147483647) {
                System.out.println("Невозможно добавить нового работника, чтобы продолжить добаление , требуется удалить кого то из сотрудников\n" +
                        "Превышено максимальное количество работников , находящихся в базе");
                System.exit(1);
            }
            int originalSize = usedId.size();
            for (int i = 0; i < 2147483647; i++) {
                usedId.add(i + 1);
                if (usedId.size() > originalSize) {
                    id = i + 1;
                    break;
                }
            }
        } else id = 1;

        //Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя");
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] finalUserCommand = line.trim().split(" ");
            while (line.trim().equals("") || finalUserCommand.length > 1) {
                System.out.println("Имя - непустое значение и одно слово");
                if(scanner.hasNextLine())
                    line = scanner.nextLine();
                else {
                    System.out.println("Непредвиденная ошибка");
                    System.exit(1);
                }
            }
            name = line;
        } else {
            System.out.println("Неверное имя");
            System.exit(1);
        }

        System.out.println("Введите координату x (целое число):");
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            // проверка на число int
            while (!checkNumber.isNumberInteger(line)) {
                System.out.println("Введенная строка не соответствует требованиям" +
                        " (целое числовое значение большее -2147483649 и меньшее 2147483647");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                }
                else {
                    System.out.println("Неверное значение координаты");
                    System.exit(1);
                }
            }
            x = Integer.parseInt(line);
        } else {
            System.out.println("Неверное имя");
            System.exit(1);
        }


        System.out.println("Введите координату y (с точкой):");
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            // проверка на число float
            while (!checkNumber.isNumberFloat(line)) {
                System.out.println("Введенная строка не соответствует требованиям" +
                        " (число от 1.4*10^-45 до 3.4028235*10^38(число с точкой))");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else {
                    System.out.println("Неверное значение координаты");
                    System.exit(1);
                }
            }
            y = Float.parseFloat(line);
        } else {
            System.out.println("Неверное значение координаты");
            System.exit(1);
        }

        Coordinates coordinates = new Coordinates(x, y);

        LocalDateTime localDateTime = randomLocalDateTime.randomLocalDateTime();

        System.out.println("Введите назначенную зарплату");
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            while (!checkNumber.isNumberDouble(line) || Double.parseDouble(line) <= 0) {
                System.out.println("Введенная строка не соответствует требованиям" +
                        " (число от 4.9*10^−324 до 1.7976931348623157^308(число с точкой))\nИли вводится неположительное число для зарплаты");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else {
                    System.out.println("Неверное значение зарплаты");
                    System.exit(1);
                }

            }
            salary = Double.parseDouble(line);
            System.out.println(salary);
        } else {
            System.out.println("Неверное значение зарплаты");
            System.exit(1);
        }
        System.out.println("Выберите позицию , которую занимает сотрудник в кампании (введите число):\n" +
                " 1 - Инженер\n" + " 2 - Начальник отдела\n 3 - Ведущий разработчик\n 4 - Пекарь\n Любое другое число - нет позиции");
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            switch (line.trim()) {
                case "1":
                    position = Position.ENGINEER;
                    break;
                case "2":
                    position = Position.HEAD_OF_DIVISION;
                    break;
                case "3":
                    position = Position.LEAD_DEVELOPER;
                    break;
                case "4":
                    position = Position.BAKER;
                    break;
                default:
                    position = Position.UNDEFINED;
            }
            System.out.println(position.getPosition());
        } else {
            System.out.println("Неверное значение");
            System.exit(1);
        }

        System.out.println("Выберите его нынешний статус:\n" +
                " 1 - Уволен\n" + " 2 - Нанят\n 3 - Рекомендуется для продвижения по службе" +
                "\n 4 - Основной\n 5 - На испытательном сроке\n Любое другое число - нет статуса");
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            switch (line.trim()) {
                case "1":
                    status = Status.FIRED;
                    break;
                case "2":
                    status = Status.HIRED;
                    break;
                case "3":
                    status = Status.RECOMMENDED_FOR_PROMOTION;
                    break;
                case "4":
                    status = Status.REGULAR;
                    break;
                case "5":
                    status = Status.PROBATION;
                    break;
                default:
                    status = Status.UNDEFINED;
                    break;
            }
        } else {
            System.out.println("Неверное значение");
            System.exit(1);
        }
        Organization organization;
        OrganizationType organizationType = null;
        Address address = new Address("");
        double annualTurnover = 0;
        System.out.println("Введите информацию об организации\n\nВведите годовой оборот (ввод числа с точкой)");
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            while (!checkNumber.isNumberDouble(line) || Double.parseDouble(line) <= 0) {
                System.out.println("Введенная строка не соответствует требованиям" +
                        " (число от 4.9*10^−324 до 1.7976931348623157^308 (число с точкой))\nГодовой оборот - число больше 0");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else {
                    System.out.println("Неверное значение");
                    System.exit(1);
                }
            }
            annualTurnover = Double.parseDouble(line);
        } else {
            System.out.println("Неверное значение");
            System.exit(1);
        }

        System.out.println("Введите число, соответствующее типу организации:\n 1 - Коммерческая\n" +
                " 2 - Частная компания с ограниченной ответственностью\n" +
                " 3 - Открытое акционерное общество\n Любое другое - нет типа");
        if (scanner.hasNextLine()) {
                line = scanner.nextLine();
            switch (line.trim()) {
                case "1":
                    organizationType = OrganizationType.COMMERCIAL;
                    break;
                case "2":
                    organizationType = OrganizationType.PRIVATE_LIMITED_COMPANY;
                    break;
                case "3":
                    organizationType = OrganizationType.OPEN_JOINT_STOCK_COMPANY;
                    break;
                default:
                    organizationType = OrganizationType.UNDEFINED;
                    break;
            }
        }
        else {
            System.out.println("Неверное значение");
            System.exit(1);
        }

        System.out.println("Введите адрес организации (и после нажать Enter):");

        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            while (line.trim().equals("")){
                if (scanner.hasNextLine()) {
                    System.out.println("Введите корректные данные");
                    line = scanner.nextLine();
                }
                else {
                    System.out.println("Неверное значение");
                    System.exit(1);
                }
            }
            address.setStreet(line);
        }
        else{
            System.out.println("Неверное значение");
            System.exit(1);
        }
        organization = new Organization(annualTurnover,organizationType,address);

        Worker worker = new Worker(id,name,coordinates,localDateTime,salary,position,status,organization);
        workers.add(worker); // добавляем в коллекцию только что созданный id
        System.out.println("Элемент добавлен");
    }
}
