package com.fandyadam.dailyupdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Arrays;

public class DailyUpdate implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DailyUpdate.class);

    @Autowired
    private DailyUpdateService dailyUpdate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("args=[{}] " + Arrays.toString(args.getSourceArgs()));

        boolean testMode = false;

        if(args.getOptionNames().contains("test")) {
            testMode = args.getOptionValues("test").size() > 0 ?
                Boolean.valueOf(args.getOptionValues("test").get(0)) : false;
        }

        dailyUpdate.run(testMode);
    }

}
