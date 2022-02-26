package com.fandyadam.dailyupdate;

import org.springframework.stereotype.Component;

@Component
public class DailyUpdateImpl {

    private KalendarApi kalendarApi;

    public DailyUpdateImpl(KalendarApi kalendarApi) {
        this.kalendarApi = kalendarApi;
    }

    public void run() {
        System.out.println("DailyUpdate run");
        try {
            kalendarApi.getHoliday(2022, 2);
        }catch (Exception e) {
            System.err.println(e);
        }
    }
}
