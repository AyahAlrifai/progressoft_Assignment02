package com.ayah.progress.server.main;

import java.util.ResourceBundle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ayah.progress.fun.DataBaseConnection;
import com.ayah.progress.fun.ProgressLogger;

@SpringBootApplication(scanBasePackages = {"com.ayah"})
public class ProgressoftApiApplication {

	public static void main(String[] args) {
		initApplication();
		SpringApplication.run(ProgressoftApiApplication.class, args);
	}

	private static void initApplication() {
        ResourceBundle props = ResourceBundle.getBundle("properties.Progressoft");
        String logPath = props.getString("log-path");
        ProgressLogger.initProgressLogger(logPath);
		
        String dbAccount = props.getString("db-account");
        String dbPassword = props.getString("db-password");
        String dbHost = props.getString("db-host");
        String dbPort = props.getString("db-port");
        String dbDatabase = props.getString("db-database");

        DataBaseConnection.setConnectionData(dbAccount, dbPassword, dbHost, dbPort, dbDatabase);
    }
}

