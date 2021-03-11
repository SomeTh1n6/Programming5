package com.worker.properties;

public enum Status {
    FIRED("Уволен"),
    HIRED("Нанят"),
    RECOMMENDED_FOR_PROMOTION("Рекомендуется для продвижения по службе"),
    REGULAR("Основной"),
    PROBATION("На испытательном сроке"),
    UNDEFINED("-");

    private String status;

    Status(String status) {
        this.status = status;
    }

    /**@return статус работника в организации
     * */
    public String getStatus(){
        return status;
    }
}
