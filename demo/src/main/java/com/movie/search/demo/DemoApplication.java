package com.movie.search.demo;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @implNote Generally I would not make the main class to be a RestController
 *           but for demoing purposes this makes it easier to verify
 *           installation steps
 */
@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping("/")
    public Map<String, String> home() {
        return Map.of("message", "Hello Docker World");
    }

}
