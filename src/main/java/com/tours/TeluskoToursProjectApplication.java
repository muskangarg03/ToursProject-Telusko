package com.tours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;

@SpringBootApplication
public class TeluskoToursProjectApplication {

	public static void main(String[] args) {
		clearLogFile("C://Users//DELL//Downloads//TeluskoToursProject//TeluskoToursProject//src//main//java//com//tours//Logging//app.log");
//		clearLogFile("E://New Tour//ToursProject-Telusko//src//main//java//com//tours//Logging//app.log");
		SpringApplication.run(TeluskoToursProjectApplication.class, args);
		System.out.println("Hello....git ..");
	}

	private static void clearLogFile(String logFilePath) {
		try (FileWriter fileWriter = new FileWriter(logFilePath, false)) {
			fileWriter.write("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}





