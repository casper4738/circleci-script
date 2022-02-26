package com.fandyadam.circleci;

import org.springframework.stereotype.Component;

@Component
public class DailyUpdate {

    public void run() {
        System.out.println("DailyUpdate run");
    }
}
