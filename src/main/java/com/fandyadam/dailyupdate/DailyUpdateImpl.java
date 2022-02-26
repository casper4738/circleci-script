package com.fandyadam.dailyupdate;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DailyUpdateImpl {

    private KalendarApi kalendarApi;

    public DailyUpdateImpl(KalendarApi kalendarApi) {
        this.kalendarApi = kalendarApi;
    }

    public void run() {
        System.out.println("DailyUpdate run");
        try {
            kalendarApi.getHoliday(
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth());
        }catch (Exception e) {
            System.err.println(e);
        }
    }
}
