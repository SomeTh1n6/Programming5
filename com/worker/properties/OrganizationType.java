package com.worker.properties;

public enum OrganizationType {
    COMMERCIAL("Коммерческая"),
    PRIVATE_LIMITED_COMPANY("Частная компания с ограниченной ответственностью"),
    OPEN_JOINT_STOCK_COMPANY("Открытое акционерное общество"),
    UNDEFINED("-");

    private String organizationType;

    OrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    /**@return тип организации
     * */
    public String getOrganizationType(){
        return organizationType;
    }
}
