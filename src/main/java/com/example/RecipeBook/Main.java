package com.example.RecipeBook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


//@SpringBootApplication//(exclude = {JacksonAutoConfiguration.class})
@SpringBootApplication (exclude={DataSourceAutoConfiguration.class})

public class Main {
//    @Autowired
//    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);


     // Recipe recipe = gson.fromJson(content, new TypeToken<Employee>(){}.getType());
    }

//    @Override
//    public void run (String[] args)  {
//        System.out.println("Hello World");
//    }

}
