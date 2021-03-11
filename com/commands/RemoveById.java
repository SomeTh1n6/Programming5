package com.commands;

import com.worker.Worker;

import java.util.LinkedList;

public class RemoveById {
    /**Удаление по id
     * @param workers коллекция с которой проводится работа
     * @param id id по которому производится удаление
     * */
    public void execute(LinkedList<Worker> workers,int id){
        boolean flag = false;
        for (Worker worker :workers) {
            if (worker.getId().equals(id)){
                flag = true;
            }
        }
        if (!flag)
            System.out.println("По заданому id сотрудника не найдено");
        else {
            workers.removeIf(p -> p.getId().equals(id));
            System.out.println("Сотрудник с id " + id + " успешно удален");
        }
    }
}
