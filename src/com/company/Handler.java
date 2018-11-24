package com.company;

import java.util.ArrayList;

public class Handler {
    private ValuesGenerator valuesGenerator = new ValuesGenerator();
    private ArrayList<Thread> threadArrayList = new ArrayList<>();
    private ArrayList<ArrayList<Request>> requestsListinThread = new ArrayList<>();
    private double currentTime = 0;
    private double timeProstoya = 0;
    private double timeOjidaniya = 0;

    public double getTimeProstoya() {
        return timeProstoya;
    }

    public void setTimeProstoya(double timeProstoya) {
        this.timeProstoya = timeProstoya;
    }

    public double getTimeOjidaniya() {
        return timeOjidaniya;
    }

    public void setTimeOjidaniya(double timeOjidaniya) {
        this.timeOjidaniya = timeOjidaniya;
    }

    public Handler(){
        threadArrayList.add(new Thread());
        threadArrayList.add(new Thread());
        threadArrayList.add(new Thread());
        threadArrayList.add(new Thread());

        System.out.println(threadArrayList.size());

        requestsListinThread.add(new ArrayList<>());
        requestsListinThread.add(new ArrayList<>());
        requestsListinThread.add(new ArrayList<>());
        requestsListinThread.add(new ArrayList<>());
        requestsListinThread.add(new ArrayList<>());

    }

    public void addRequests(Request request){
        double timeService = request.getTime();
        int threadNum = valuesGenerator.discoverFate(4);
        double timeIncoming = valuesGenerator.getUniformY();

        doStaff(timeIncoming);

        currentTime += timeIncoming;
        requestsListinThread.get(threadNum).add(request);

        System.out.printf("\n[%.2f] Поступила " + request.getName() + ". Выбран поток №" + threadNum
                + " время поступления: " + timeIncoming + " вемя обслуживания: " + request.getTime() +"\n", currentTime);

        if(threadArrayList.get(threadNum).isFree()){
            threadArrayList.get(threadNum).serviceRequest(requestsListinThread.get(threadNum).get(0), currentTime + timeService);
            requestsListinThread.get(threadNum).remove(0);
        }else{
            System.out.println("------заявка " + request.getName() + " осталась ожидать");
            timeOjidaniya += threadArrayList.get(threadNum).getTime() - currentTime;
        }

        //System.out.println("\n--------------------------------------------\n");
    }

    public void doStaff(double timeIncoming){
        for(int i = 0; i < 4; i++){

            if(currentTime + timeIncoming >= threadArrayList.get(i).getTime() && threadArrayList.get(i).getTime() >= currentTime
                    && !threadArrayList.get(i).isFree()){
                //currentTime = ;
                System.out.printf("\n[%.2f] Поток № %d освободился\n", threadArrayList.get(i).getTime(), i);
                threadArrayList.get(i).setFree(true);
                if(!requestsListinThread.get(i).isEmpty()){
                    System.out.printf("[%.2f] ", threadArrayList.get(i).getTime());

                    timeProstoya += currentTime + timeIncoming - threadArrayList.get(i).getTime();

                    threadArrayList.get(i).serviceRequest(requestsListinThread.get(i).get(0),
                            currentTime + timeIncoming + requestsListinThread.get(i).get(0).getTime());
                    System.out.println(" потоком №" + i);
                    requestsListinThread.get(i).remove(0);
                    //System.out.println("//Заявка удалена из очереди");
                }
            }
        }
    }

    public void doRemaining(){
        double t = 0;
        while(!requestsListinThread.get(0).isEmpty() || !requestsListinThread.get(1).isEmpty() ||
                !requestsListinThread.get(2).isEmpty() || !requestsListinThread.get(3).isEmpty()) {
            doStaff(t++);
        }
    }

    public void printFreeThreads(){
        System.out.println("Потоки: ");
        for(Thread thread: threadArrayList){
            System.out.println("Поток " + thread.isFree());
        }
    }

    public void printRemaining(){
        System.out.println("Remaining:");
        for(ArrayList<Request> requests:requestsListinThread){
            for(Request request: requests){
                System.out.println(request.getName());
            }
        }
    }

    public void addThread(Thread thread){
        threadArrayList.add(thread);
    }


}
