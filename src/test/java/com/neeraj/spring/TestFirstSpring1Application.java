package com.neeraj.spring;

import org.springframework.boot.SpringApplication;

public class TestFirstSpring1Application {

	public static void main(String[] args) {
		SpringApplication.from(FirstSpring1Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
