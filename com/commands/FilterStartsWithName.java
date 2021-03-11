package com.commands;

import com.worker.Worker;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterStartsWithName {
    /** Выводит на печать только те элементы коллекци в которых поле name начинается с заданной подстроки
     * Игнорирует верхний и нижний регистр
     * Если ничего не найдено, выводится сообщение об этом
     * @param name параметр по которому проводится поиск
     * @param workers коллекция, с которой ведется работа
     */
    public void execute(LinkedList<Worker> workers,String name){
        boolean flag = false;
        for (Worker worker: workers) {
            Pattern pattern = Pattern.compile("^" + name,Pattern.CASE_INSENSITIVE); // почему то не игнорируется регистр у русского алфавита. С правильным регистром поиск работает
            Matcher matcher = pattern.matcher(worker.getName());
            if (matcher.find()){
                System.out.println(worker.toString());
                flag = true;
            }
        }
        if (!flag)
            System.out.println("Ничего не найдено");
    }
}
