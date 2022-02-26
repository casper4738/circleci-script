package com.fandyadam.dailyupdate;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DailyUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(DailyUpdateService.class);

    private Office365Api office365Api;
    private KalendarApi kalendarApi;
    private GithubApi githubApi;

    public DailyUpdateService(
        Office365Api office365Api,
        KalendarApi kalendarApi,
        GithubApi githubApi
    ) {
        this.office365Api = office365Api;
        this.kalendarApi = kalendarApi;
        this.githubApi = githubApi;
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
            holiday = kalendarApi.isHoliday(year, month, day);

            logger.info("call service webhookDaily");
            String monthFormat = Strings.padStart(String.valueOf(month), 2, '0');
            String dayFormat = Strings.padStart(String.valueOf(day), 2, '0');

            String title = "DAILY UPDATE - " + dayFormat + "-" + monthFormat + "-" + year;

            String kataBijak = githubApi.getRandomKataBijak().trim();
            String subtitle = kataBijak.equals("") ? "Finish What You Start" : kataBijak;

            webhookSuccess = office365Api.webhookDaily(
                title,
                subtitle,
                testMode);
        } catch (Exception e) {
            logger.error("error", e);
        }

        logger.info("isHoliday " + holiday);
        logger.info("webhookSuccess " + webhookSuccess);
        logger.info("==== DailyUpdate finish ====");

    }
}
