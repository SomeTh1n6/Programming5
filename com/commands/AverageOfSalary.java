package com.commands;

import com.worker.Worker;

import java.util.LinkedList;

public class AverageOfSalary {
    /**Выводится среднее значение по зарплате
     * @param workers коллекция с которой ведется работа
     * */
    public void execute(LinkedList<Worker> workers){
        double result = 0;
        for (Worker worker: workers) {
            result += worker.getSalary();
        }
        result = result/(double)(workers.size());
        if (result == 0){
            System.out.println("Не добавлено ни одного сотрудника");
        }
        else{
            System.out.println("Среднее значение по зарплате среди всех сотрудников: " + String.format("%.2f",result));
        }
    }
}
