package com.fandyadam.dailyupdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.fandyadam"})
public class DailyUpdateApplication implements CommandLineRunner {

    private DailyUpdateImpl dailyUpdate;

    @Autowired
    public DailyUpdateApplication(DailyUpdateImpl dailyUpdate) {
        this.dailyUpdate = dailyUpdate;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DailyUpdateApplication.class).headless(false).run(args);
    }

    @Override
    public void run(String... args) {
        dailyUpdate.run();
    }


}
