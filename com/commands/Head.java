package com.commands;

import com.worker.Worker;

import java.util.LinkedList;
import java.util.WeakHashMap;

public class Head {
    /** Выводит на печать первый элемент в коллекции
     * @param workers коллекция, с которой ведется работа
     */
    public void execute(LinkedList<Worker> workers) {
        if (workers.isEmpty())
            System.out.println("Ошибка. Коллекция пуста");
        else
            System.out.println("Первый элемент - " + workers.getFirst());
    }
}
