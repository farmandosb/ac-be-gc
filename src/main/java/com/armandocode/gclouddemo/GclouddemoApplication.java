package com.armandocode.gclouddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GclouddemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GclouddemoApplication.class, args);
    }

}

/*
@SpringBootApplication
public class GclouddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GclouddemoApplication.class, args);
	}

}
// Add the controller.
@RestController
class HelloWorldController {
	@GetMapping("/")
	public String hello() {
		return "hello world!";
	}
}*/
