package com.fandyadam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.fandyadam"})
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).headless(false).run(args);
    }

    @Override
    public void run(String... args) {
        System.out.println("java -jar build/libs/circleci.jar {MAIN_CLASS}");
    }


}
