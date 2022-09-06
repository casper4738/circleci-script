package com.fandyadam.dailyupdate;

import com.fandyadam.dailyupdate.calendar.CalendarIndonesiaApi;
import com.fandyadam.dailyupdate.calendar.CalendarVercelApi;
import com.fandyadam.dailyupdate.calendar.CustomHolidayApi;
import com.fandyadam.dailyupdate.katabijak.KataBijakApi;
import com.fandyadam.dailyupdate.office.Office365Api;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DailyUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(DailyUpdateService.class);

    private Office365Api office365Api;
    private CalendarIndonesiaApi calendarIndonesiaApi;
    private CalendarVercelApi calendarVercelApi;
    private CustomHolidayApi customHolidayApi;
    private KataBijakApi kataBijakApi;

    public DailyUpdateService(
        Office365Api office365Api,
        CalendarIndonesiaApi calendarIndonesiaApi,
        CalendarVercelApi calendarVercelApi,
        CustomHolidayApi customHolidayApi,
        KataBijakApi kataBijakApi
    ) {
        this.office365Api = office365Api;
        this.calendarIndonesiaApi = calendarIndonesiaApi;
        this.calendarVercelApi = calendarVercelApi;
        this.customHolidayApi = customHolidayApi;
        this.kataBijakApi = kataBijakApi;
    }

    public void run(boolean testMode) {
        logger.info("DailyUpdate run default");

        run(LocalDate.now().getYear(),
            LocalDate.now().getMonthValue(),
            LocalDate.now().getDayOfMonth(),
            testMode
        );
    }

    public void run(int year, int month, int day, boolean testMode) {

        boolean holiday = false;
        boolean webhookSuccess = false;

        logger.info("==== DailyUpdate start ====");
        logger.info("DailyUpdate run mode test : " + testMode);
        logger.info("DailyUpdate param year    : " + year);
        logger.info("DailyUpdate param month   : " + month);
        logger.info("DailyUpdate param day     : " + day);

        logger.info("DailyUpdate default year  : " + LocalDate.now().getYear());
        logger.info("DailyUpdate default month : " + LocalDate.now().getMonthValue());
        logger.info("DailyUpdate default day   : " + LocalDate.now().getDayOfMonth());

        try {
            logger.info("call service holiday");
            try {
                holiday = customHolidayApi.isHoliday(year, month, day);
            } catch (java.net.UnknownHostException | Exception ex) {
                logger.error("customHolidayApi error", ex);
            }

            if (!holiday) {
                try {
                    holiday = calendarIndonesiaApi.isHoliday(year, month, day);
                } catch (Exception exCalIndo) {
                    logger.error("calendarIndonesiaApi error", exCalIndo);
                    try {
                        holiday = calendarVercelApi.isHoliday(year, month, day);
                    } catch (Exception exVelcel) {
                        logger.error("calendarVercelApi error", exVelcel);
                    }
                }
            }

            if (!holiday) {
                logger.info("call service kata bijak");
                String kataBijak = kataBijakApi.getRandomKataBijak().trim();

                logger.info("call service webhookDaily");
                String monthFormat = Strings.padStart(String.valueOf(month), 2, '0');
                String dayFormat = Strings.padStart(String.valueOf(day), 2, '0');

                String title = "DAILY UPDATE  |  " + dayFormat + "-" + monthFormat + "-" + year;
                String subtitle = kataBijak.equals("") ? "Finish What You Start" : kataBijak;

                webhookSuccess = office365Api.webhookDaily(
                    title,
                    subtitle,
                    testMode);
            }
        } catch (Exception e) {
            logger.error("error", e);
        }

        logger.info("isHoliday " + holiday);
        logger.info("webhookSuccess " + webhookSuccess);
        logger.info("==== DailyUpdate finish ====");

    }
}
