package com.saadmeddiche.dailyhelper;

import com.saadmeddiche.dailyhelper.excel_files.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DailyHelperApplication {

	public static void main(String[] args) {
		Producer.produce();
		SpringApplication.run(DailyHelperApplication.class, args);
	}

}
