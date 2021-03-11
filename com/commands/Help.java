package com.commands;

import java.util.HashMap;
import java.util.Map;

public class Help {
    /** Выводит на печать информацию о доступных командах
     * @param manual информация о доступных командах с пояснениями к ним
     */
    public void execute(HashMap<String,String> manual) {
        for (Map.Entry<String, String> pair : manual.entrySet())
        {
            String key = pair.getKey();
            String value = pair.getValue();
            System.out.println(key + " : " + value);
        }
    }
}
