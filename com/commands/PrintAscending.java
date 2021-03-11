package com.commands;

import com.worker.Worker;

import java.util.*;

public class PrintAscending {

    Show show = new Show();
    /** Сортирует коллекцию по заданному параметру
     * @param workers коллекция, с которой ведется работа
     * @param condition параметр по которому сортируем
     * */
    public void execute(LinkedList workers, String condition){
        if (!workers.isEmpty()){
            switch (condition) {
                case "id":
                    Collections.sort(workers, Worker::compareTo);
                    System.out.println("Коллекция отсортирована по id\n");
                    show.execute(workers);
                    break;
                case "name":
                    Collections.sort(workers,nameComparator);
                    System.out.println("Коллекция отсортирована по имени , в алфавитном порядке\n");
                    show.execute(workers);
                    break;
                case "salary":
                    Collections.sort(workers,salaryComparator);
                    System.out.println("Коллекция отсортирована по заработной плате\n");
                    show.execute(workers);
                    break;
                case "creation_date":
                    Collections.sort(workers,dateComparator);
                    System.out.println("Коллекция отсортирована по дате создания\n");
                    show.execute(workers);
                    break;
                case "organization_annual_turnover":
                    Collections.sort(workers,annualTurnoverComparator);
                    System.out.println("Коллекция отсортирована по годовому обороту организаций\n");
                    show.execute(workers);
                    break;
                case "organization_adress":
                    Collections.sort(workers,annualTurnoverComparator);
                    System.out.println("Коллекция отсортирована по адресу организаций\n");
                    show.execute(workers);
                    break;
                default:
                    System.out.println("Неопознанный аргумент. Доступные параметры для сортировки коллекции:\nid - сортировка по id" +
                            "\nname - сортировка по имени\nsalary - сортировка по зарплате\ncreation_date - сортировка по дате создания\n" +
                            "organization_annual_turnover - сортировка по годовому обороту организации\norganization_adress - сортировка по адресу организации");
                    break;
            }
        }
        else
            System.out.println("Коллекция пуста");
    }

    public static Comparator<Worker> nameComparator = new Comparator<Worker>() {
        @Override
        public int compare(Worker w1, Worker w2) {
            return w1.getName().compareTo(w2.getName());
        }
    };

    public static Comparator<Worker> salaryComparator = new Comparator<Worker>() {
        @Override
        public int compare(Worker w1, Worker w2) {
            return (int) (w1.getSalary() - w2.getSalary());
        }
    };

    public static Comparator<Worker> dateComparator = new Comparator<Worker>() {
        @Override
        public int compare(Worker w1, Worker w2) {
            String creationDate1 = w1.getCreationDate().toString();
            String creationDate2 = w2.getCreationDate().toString();
            return creationDate1.compareTo(creationDate2);
        }
    };

    public static Comparator<Worker> annualTurnoverComparator = new Comparator<Worker>() {
        @Override
        public int compare(Worker w1, Worker w2) {
            return (int) (w1.getOrganizationAnnualTurnover() - w2.getOrganizationAnnualTurnover());
        }
    };

    public static Comparator<Worker> adressComparator = new Comparator<Worker>() {
        @Override
        public int compare(Worker w1, Worker w2) {
            return w1.getOrganizationAdress().compareTo(w2.getOrganizationAdress());
        }
    };
}
