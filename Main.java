import java.util.*;
import com.*;
import com.worker.Worker;

import java.io.*;


/**
 * @author Alexander Miller
 * @version 2.3
 */

public class Main {
    public static void main(String[] args) throws IOException {
        Queue<String> history = new ArrayDeque<>();  //Создаем очередь для поддержания истории последних 6 команд (не обязательно завершенных успешно)
        CommandSelector selector = new CommandSelector();
        LinkedList<Worker> workers = new LinkedList<>();
        String data = null;
        //String data = "I:\\ВСЯ УЧЕБА\\Лабораторные\\Лаба5\\src\\files_information\\data.json";

        if (args.length == 0 || args == null){
            System.out.println("Путь к файлу с данными не задан, задайте путь (прописать путь после вызова программы):");
            System.exit(1);
        }
        else{
           data = args[0];
        }
        System.out.println("Введите \"help\", чтобы ознакомиться с возможностями программы");
        selector.start(data, history); // запуск программы
    }
}