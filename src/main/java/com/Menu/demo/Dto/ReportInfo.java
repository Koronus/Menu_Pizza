package com.Menu.demo.Dto;

public class ReportInfo {
    private String code;
    private String name;
    // конструктор, геттеры

    public ReportInfo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
