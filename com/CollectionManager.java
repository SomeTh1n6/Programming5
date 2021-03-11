package com;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.worker.Worker;
import com.worker.properties.*;
import java.io.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.*;
import com.commands.*;

public class CollectionManager {
    /** Файл с данными*/
    private File jsonFile;

    /** Тут хранятся все данные о работниках */
    public LinkedList<Worker> workers = new LinkedList<>();

    /** Список доступных команд (ключ - команда, значение - информация о ней) */
    public static HashMap<String, String> manual;

    /** Дата инициализации коллекции */
    private final Date initDate;

    /** Парсинг JSON в Коллекцию/класс и обратно */
    private final Gson gson;

    String data;

    {
        manual = new HashMap<>();
        gson = new Gson();
        manual.put("help","вывести справку по доступным командам");
        manual.put("info","вывести в стандартный поток вывода информацию о " +
                "коллекции (тип, дата инициализации, количество элементов и т.д.)");
        manual.put("show","вывести в стандартный поток вывода все элементы коллекции " +
                "в строковом представлении");
        manual.put("add","добавить новый элемент в коллекцию");
        manual.put("update id","обновить значение элемента коллекции, id " +
                "которого равен заданному");
        manual.put("remove_by_id id","удалить элемент из коллекции по его id");
        manual.put("save","сохранить коллекцию в файл");
        manual.put("execute_script file_name","считать и исполнить скрипт из указанного файла. Указать абсолютный путь к файлу " +
                "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        manual.put("exit","завершить программу (без сохранения в файл)");
        manual.put("head","вывести первый элемент коллекции");
        manual.put("remove_head","вывести первый элемент коллекции и удалить его");
        manual.put("history","вывести последние 6 команд (без их аргументов)");
        manual.put("average_of_salary","вывести среднее значение поля salary для всех элементов коллекции");
        manual.put("filter_starts_with_name name","вывести элементы, значение поля name которых " +
                "начинается с заданной подстроки");
        manual.put("print_ascending","вывести элементы коллекции в порядке возрастания (сортировка по заданным параметрам)");
    }

    /** Загрузка коллекции из файла
     * @param collectionPath - абсолютный путь к файлу , котором лежит все информация о коллекции
     * @exception FileNotFoundException - файл по заданному пути не найден
     */
    public CollectionManager(String collectionPath) throws FileNotFoundException {
        data = collectionPath;
        try {
            if (collectionPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Путь до json файла нужно передать через переменную окружения collectionPath.");
            System.exit(1);
        }
        File file = new File(collectionPath);
        try {
            if (file.exists()) this.jsonFile = new File(collectionPath);
            else throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Файл по указанному пути не существует.");
            System.exit(1);
        }
        this.initDate = new Date();
        this.load();
    }

    /** Проверка строки, введенной пользователем, является ли она командой
     * @param userCommand - строка на проверку
     * @return значение boolean
     */
    public boolean checkCommand(String userCommand) {
        for (Map.Entry<String, String> pair : manual.entrySet()) {
            String[] finalUserCommand = pair.getKey().trim().split(" ", 2);
            if (userCommand.equals(finalUserCommand[0])) {
                return true;
            }
        }
        return false;
    }

    /**Сортировка коллекции по заданному параметру
     * @param condition параметр по которому сортируется
     * */
    public void print_ascending(String condition){
        PrintAscending printAscending = new PrintAscending();
        printAscending.execute(workers,condition);
    }

    /** Удаление из коллекции по id
     * @param id id по которому будет производиться удаление
     */
    public void remove_by_id(int id){
        RemoveById removeById = new RemoveById();
        removeById.execute(workers,id);
    }

    /** Сохранение в файл в формате JSON
     * @exception FileNotFoundException файл , в который записывается информация о коллекции
     * */
    public void save() throws FileNotFoundException {
        Save save = new Save();
        save.execute(gson,workers,data);
    }


    /** Подсчет среднего значения по зарплате среди всех работников. 2 знака после запятой
     * **/
    public void average_of_salary(){
        AverageOfSalary averageOfSalary = new AverageOfSalary();
        averageOfSalary.execute(workers);
    }


    /** Выводит на печать первый элемент в коллекции
     */
    public void head() {
        Head head = new Head();
        head.execute(workers);
    }


    /** Удаляет первый элемент в коллекции, если она не пуста
     */
    public void remove_head(){
        RemoveHead removeHead = new RemoveHead();
        removeHead.execute(workers);
    }


    /** Очищает коллекцию
     */
    public void clear(){
        Clear clear = new Clear();
        clear.execute(workers);
    }


    /** Выводит на печать информацию о доступных командах
     */
    public void help() {
        Help help = new Help();
        help.execute(manual);
    }


    /** Выводит на печать все элементы коллекции
     */
    public void show(){
        Show show = new Show();
        show.execute(workers);
    }


    /** Выводит на печать только те элементы коллекци в которых поле name начинается с заданной подстроки
     * Игнорирует верхний и нижний регистр
     * Если ничего не найдено, выводится сообщение об этом
     * @param name - параметр по которому проводится поиск
     */
    public void filter_starts_with_name(String name){
        FilterStartsWithName filterStartsWithName = new FilterStartsWithName();
        filterStartsWithName.execute(workers,name);
    }

    /** Загружает информацию из файла в коллекцию
     * @exception JsonSyntaxException ошибка синтаксиса , считываемого файла
     * @exception FileNotFoundException файл не найден
     * @exception SecurityException ошибка прав доступа
     * */
    public void load() throws JsonSyntaxException, FileNotFoundException, SecurityException {
        Load load = new Load();
        load.execute(jsonFile,workers,gson);
    }


    /** Изменение данных работника по заданному id
     * Удаляем по id и создаем новый объект с тем же id и LocalDateTime,
     * но уже с новыми данными, которые вводятся вручную
     * @param id id , по которому будут вноситься изменения
     * */
    public void update(int id,Scanner scanner){
        Update update = new Update();
        update.execute(workers,id,scanner);
    }

    /** Выполнение скрипта из файла. Команды считываются так же как бы они считывались в интерактивном режиме
     * @param filePath абсолютный путь к файлу с командами
     * @param data абсолютныйпуть к файлу с данными
     * @param history очередь с историей команд
     * @exception FileNotFoundException - Файл не обнаружен
     * @exception StackOverflowError - Рекурсия
     * */
    public void execute_script(CollectionManager manager,String filePath, String data, Queue<String> history) throws IOException {
        ExecuteScript executeScript = new ExecuteScript();
        executeScript.execute(manager,filePath,data,history);
    }


    /** Добавление нового сотрудника в коллекцию
     * */
    public void add(Scanner scanner){
        Add add = new Add();
        add.execute(workers, scanner);
    }

    /** Проверка строки , является ли она float
     * @param s строка которую проверяем
     * @return true или false
     * */
    public boolean isNumberFloat(String s) throws NumberFormatException {
        try {
            Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    /** Проверка строки , является ли она double
     * @param s строка которую
     * @return true или false
     * */
    public boolean isNumberDouble(String s){
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    /** Проверка строки , является ли она int
     * @param s строка которую проверяем
     * @return true или false
     **/
    public boolean isNumberInteger(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    /** Генерация рандомного значения LocalDateTime
     * Каждый параметр генерируется рандомно и отдельно
     * @return LocalDateTime
     */
    private LocalDateTime randomLocalDateTime() {
        int year = (int) (Math.random() * (2021.0 - 1970.0) + 1970.0);
        int month = (int) (Math.random() * (13.0 - 1.0) + 1.0);
        int day;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            day = (int) (Math.random() * (32.0 - 1.0) + 1.0);
        }
        else if(month == 2){
            if ((year%4 == 0 && year%100 != 0) || year%400==0 ){
                day = (int) (Math.random() * (30.0 - 1.0) + 1.0);
            }
            else{
                day = (int) (Math.random() * (29.0 - 1.0) + 1.0);
            }
        }
        else{
            day = (int) (Math.random() * (31.0 - 1.0) + 1.0);
        }
        int hour = (int) (Math.random() * (24.0 - 1.0) + 1.0);
        int minute = (int) (Math.random() * (60.0 - 1.0) + 1.0);
        int second = (int) (Math.random() * (60.0 - 1.0) + 1.0);
        LocalDateTime localDateTime = LocalDateTime.of(year,month,day,hour,minute,second); //создается объект LocalDateTime

        return  localDateTime;
    }


    /** Возвращает строку с информацией об объекте
     **/
    @Override
    public String toString() {
        return "Тип коллекции: " + workers.getClass() +
                "\nДата инициализации: " + initDate +
                "\nКоличество элементов: " + workers.size();
    }
}
