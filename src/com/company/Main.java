package com.company;

public class Main {

    public static void main(String[] args) {

        Handler handler = new Handler();
        ValuesGenerator valuesGenerator = new ValuesGenerator();

        int num = 10;

        for(int i = 0; i < num; i++) {
            handler.addRequests(new Request("заявка " + i, valuesGenerator.getNormalY()));
            //handler.addReqtmp(new Request("заявка " + i, valuesGenerator.getNormalY()));
        }

        handler.doRemaining();
       // handler.doServicetmp();

        System.out.println("Среднее время ожидания: " + handler.getTimeOjidaniya()/num + "\nСреднее время простоя " + handler.getTimeProstoya()/4);

    }


}
