package com.commands;

import com.CollectionManager;
import com.google.gson.JsonArray;
import com.worker.Worker;

import java.io.*;
import java.util.*;

public class ExecuteScript {

    /** Выполнение скрипта из файла. Команды считываются так же как бы они считывались в интерактивном режиме
     * @param filePath абсолютный путь к файлу с командами
     * @param data абсолютный путь к файлу с данными
     * @param history очередь с историей команд
     * @exception FileNotFoundException - Файл не обнаружен
     * @exception StackOverflowError - Рекурсия
     * */
    public void execute(CollectionManager manager,String filePath, String data, Queue<String> history, LinkedList es) throws IOException {
        //userCommand = scanner.nextLine();
        try {
            String line;
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] finalUserCommand;
            LinkedList<String> scriptInformation = new LinkedList<>();
            while ((line = bufferedReader.readLine()) != null) {
                scriptInformation.add(line);
            }
            for (int i = 0; i < scriptInformation.size(); i++){
                finalUserCommand = scriptInformation.get(i).trim().split(" ", 2);
                if (finalUserCommand[0].equals("execute_script") && finalUserCommand[1].equals(filePath)) {
                    throw new StackOverflowError();
                } else {
                    try {
                        switch (finalUserCommand[0]) {
                            case "":
                                break;
                            case "help":
                                manager.help();
                                break;
                            case "execute_script":
                                es.add(finalUserCommand[1]);
                                Iterator iterator = es.iterator();
                                int count = 0;
                                while (iterator.hasNext()){
                                    if (iterator.next().equals(finalUserCommand[1])){
                                        count++;
                                    }
                                }
                                if (count == 2){
                                    System.out.println("Рекурсия");
                                    break;
                                }
                                manager.execute_script(manager,finalUserCommand[1],data,history,es);
                                break;
                            case "info":
                                System.out.println(manager.toString());
                                break;
                            case "show":
                                manager.show();
                                break;
                            case "add":
                                File tmpDir = new File("tmpDir").getAbsoluteFile();
                                tmpDir.mkdir();
                                File tmp = new File(tmpDir + "\\tmp.txt");
                                try {
                                    tmp.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                PrintWriter writer = new PrintWriter(new File(tmpDir + "\\tmp.txt"));
                                for (int j = 1; j <= 9; j++ ){
                                    // запись всей строки
                                    String text = scriptInformation.get(i+j);
                                    writer.write(text+"\n");
                                }
                                writer.flush();
                                writer.close();
                                i+=9;
                                Scanner scanner = new Scanner(new FileInputStream(tmpDir + "\\tmp.txt"));
                                System.out.println(tmpDir + "\\tmp.txt");
                                manager.add(scanner);
                                tmp.deleteOnExit();
                                break;
                            case "update":
                                File tmpDir1 = new File("tmpDir").getAbsoluteFile();
                                tmpDir1.mkdir();
                                File tmp1 = new File(tmpDir1 + "\\tmp.txt");
                                try {
                                    tmp1.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                PrintWriter writer1 = new PrintWriter(new File(tmpDir1 + "\\tmp.txt"));
                                for (int j = 1; j <= 9; j++ ){
                                    // запись всей строки
                                    String text = scriptInformation.get(i+j);
                                    writer1.write(text+"\n");
                                }
                                writer1.flush();
                                writer1.close();
                                i+=9;
                                Scanner scanner1 = new Scanner(new FileInputStream(tmpDir1 + "\\tmp.txt"));
                                System.out.println(tmpDir1 + "\\tmp.txt");
                                manager.update(Integer.parseInt(finalUserCommand[1]),scanner1);
                                tmp1.deleteOnExit();
                                break;
                            case "save":
                                manager.save();
                                break;
                            case "history":
                                if (history.size() == 0)
                                    System.out.println("История команд пуста!");
                                else {
                                    System.out.println("История последних 6 команд:");
                                    history.forEach(p -> System.out.println(p));
                                }
                                break;
                            case "clear":
                                manager.clear();
                                break;
                            case "remove_by_id":
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
                            case "exit":
                                System.exit(1);
                            default:
                                System.out.println("Неопознанная команда. Введите 'help' для справки.");

                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Отсутствует аргумент.");
                    }
                    if (manager.checkCommand(finalUserCommand[0])){
                        history.add(finalUserCommand[0]);
                        if (history.size() > 6)
                            history.remove();
                    }
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("По заданному пути не найдено файла");
        } catch (StackOverflowError stackOverflowError) {
            System.out.println("Рекурсия");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
