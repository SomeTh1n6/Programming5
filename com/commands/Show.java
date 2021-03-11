package com.commands;

import com.worker.Worker;

import java.util.LinkedList;
import java.util.WeakHashMap;

public class Show {
    /** Выводит на печать все элементы коллекции
     * @param workers коллекция, с которой ведется работа
     */
    public void execute(LinkedList<Worker> workers){
        if (!workers.isEmpty())
            workers.forEach(p -> System.out.println(p.toString()));
        else
            System.out.println("Коллекция пуста");
    }
}
