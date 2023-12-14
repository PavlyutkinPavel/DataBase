package com.db_lab.db_lab6;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "DB Lab6 Project",
                description = "This is Java Spring Boot REST project for Football Forum with many features",
                contact = @Contact(
                        name = "Pavel Pavlyutkin",
                        email = "pashanpmrp200431@gmail.com",
                        url = "@pashap"
                )

        )
)
@SpringBootApplication
public class DbLab6Application {

    public static void main(String[] args) {
        SpringApplication.run(DbLab6Application.class, args);
    }

}
