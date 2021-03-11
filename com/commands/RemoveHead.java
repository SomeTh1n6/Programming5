package com.commands;

import com.worker.Worker;

import java.util.LinkedList;

public class RemoveHead {
    /** Удаляет первый элемент в коллекции, если она не пуста
     * @param workers коллекция, с которой ведется работа
     */
    public void execute(LinkedList<Worker> workers){
        if (!workers.isEmpty()){
            System.out.println();
            workers.remove(workers.getFirst());
            System.out.println("Сотрудник успешно удален");
        }
        else
            System.out.println("Коллекция пуста!");
    }
}
