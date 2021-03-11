package com.commands;

import com.worker.Worker;

import java.nio.file.Watchable;
import java.util.LinkedList;

public class Clear {

    /** Очищает коллекцию
     * @param workers коллекция, оторая очищается
     */
    public void execute(LinkedList<Worker> workers){
        workers.clear();
        System.out.println("Коллекция успешно очищена");
    }

}
