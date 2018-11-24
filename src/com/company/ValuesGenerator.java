package com.company;

import java.util.Random;

public class ValuesGenerator {
    private Random r = new Random();
    private final int MX_NORMAL = 20;
    private final int SKO_NORMAL = 5;
    private final int MX_UNIFORM = 5;
    private final double SKO_UNIFORM = 0.5;
    private double a;
    private double b;

    public ValuesGenerator() {
        a = MX_NORMAL /12.0 - Math.sqrt(3.0)* SKO_NORMAL / 12.0;
        b = MX_NORMAL /12.0 + Math.sqrt(3.0)* SKO_NORMAL / 12.0;
    }

    public int discoverFate(int max){
        return r.nextInt(max);
    }

    public double getKsi(){
        return r.nextDouble();
    }

    public double getUniformY(){
        return (MX_UNIFORM - SKO_UNIFORM) + ((MX_UNIFORM + SKO_UNIFORM) - (MX_UNIFORM - SKO_UNIFORM)) * getKsi();
    }

    public double getNormalY(){
        double y = 0.0;
        for (int i = 0; i < 12; i++) {
            y += (a + ((b-a) * r.nextDouble()));
        }
        return y;
    }



}
