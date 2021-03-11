package com.worker.properties;

public enum Position {
    ENGINEER("Инженер"),
    HEAD_OF_DIVISION("Начальник отдела"),
    LEAD_DEVELOPER("Ведущий разработчик"),
    BAKER("Пекарь"),
    UNDEFINED("-");

    private String position;

    Position(String position) {
        this.position = position;
    }

    /**@return должность, занимаемая в организации
     * */
    public String getPosition(){
        return position;
    }
}
