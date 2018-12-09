package com.company;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Handler {
    private ValuesGenerator valuesGenerator = new ValuesGenerator();
    private ArrayList<Thread> threadArrayList = new ArrayList<>();
    private ArrayList<Request> requestArrayList = new ArrayList<>();
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

        requestsListinThread.add(new ArrayList<>());
        requestsListinThread.add(new ArrayList<>());
        requestsListinThread.add(new ArrayList<>());
        requestsListinThread.add(new ArrayList<>());
        requestsListinThread.add(new ArrayList<>());

    }

    public void addRequests(Request request){
        double timeService = request.getTime();
        double timeIncoming = valuesGenerator.getUniformY();

        doStaff(timeIncoming);

        currentTime += timeIncoming;

        ArrayList<Integer> freeThreads = getFreeThreds();
        System.out.printf("\n[%.2f] Поступила " + request.getName() + ". время поступления: " + timeIncoming
                + " время обслуживания: " + request.getTime() +"\n", currentTime);

        if (freeThreads.isEmpty()){
            int min = getMinTime();
            System.out.println("нет свободных каналов. " + request.getName() + " остается ожидать.");
            requestArrayList.add(request);
            timeOjidaniya += threadArrayList.get(min).getTime() - currentTime;

        }
        else {
            int threadNum = valuesGenerator.discoverFate(freeThreads.size());
           // System.out.println(" sda " + freeThreads.size() + " " + threadNum + " " + freeThreads.get(threadNum));

            threadArrayList.get(freeThreads.get(threadNum)).serviceRequest(request, currentTime + timeService);
            System.out.println(" потоком №" + freeThreads.get(threadNum));
        }


        //System.out.println("\n--------------------------------------------\n");
    }

    public void addReqtmp(Request request){
        requestArrayList.add(request);
    }

    public int getMinTime(){
        double min = threadArrayList.get(0).getTime();
        int num = 0;
        for(int i =1; i < 4; i++){
            if(threadArrayList.get(i).getTime() < min){
                min = threadArrayList.get(i).getTime();
                num = i;
            }
        }
        return num;
    }

    public ArrayList<Integer> getFreeThreds(){
       // Stream<Boolean> booleanStream = threadArrayList.stream().filter(Thread::isFree).map(Thread::isFree);
        ArrayList<Integer> free = new ArrayList<>();

        for(int i =0; i < 4; i++){
            if(threadArrayList.get(i).isFree()){
               free.add(i);
            }
        }
        return free;
    }

    public void doStaff(double timeIncoming){
        for(int i = 0; i < 4; i++){

            if(currentTime + timeIncoming >= threadArrayList.get(i).getTime() && threadArrayList.get(i).getTime() >= currentTime
                    && !threadArrayList.get(i).isFree()){
                //currentTime = ;
                System.out.printf("\n[%.2f] Поток № %d освободился\n", threadArrayList.get(i).getTime(), i);
                threadArrayList.get(i).setFree(true);
                if(!requestArrayList.isEmpty()){
                    System.out.printf("[%.2f] ", threadArrayList.get(i).getTime());

                    timeProstoya += currentTime + timeIncoming - threadArrayList.get(i).getTime();

                    threadArrayList.get(i).serviceRequest(requestArrayList.get(0),
                            currentTime + timeIncoming + requestArrayList.get(0).getTime());
                    System.out.println(" потоком №" + i);
                    requestArrayList.remove(0);
                    //System.out.println("//Заявка удалена из очереди");
                }
            }
        }
    }


    public void doRemaining(){
        double t = 0;
        while(!threadArrayList.get(0).isFree() || !threadArrayList.get(1).isFree()
                || !threadArrayList.get(2).isFree() || !threadArrayList.get(3).isFree()) {
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
