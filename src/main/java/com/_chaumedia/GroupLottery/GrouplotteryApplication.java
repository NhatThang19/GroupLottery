package com._chaumedia.GroupLottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class GrouplotteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrouplotteryApplication.class, args);
	}

}
