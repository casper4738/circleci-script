package com.fandy;

import com.fandy.circleci.DailyUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.fandy"})
public class Application implements CommandLineRunner {

    private DailyUpdate dailyUpdate;

    @Autowired
    public Application(
        DailyUpdate dailyUpdate
    ) {
        this.dailyUpdate = dailyUpdate;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).headless(false).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        dailyUpdate.run();
    }


}
