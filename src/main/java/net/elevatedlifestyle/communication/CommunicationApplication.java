package net.elevatedlifestyle.communication;

import net.elevatedlifestyle.communication.dto.UserDto;
import net.elevatedlifestyle.communication.model.User;
import net.elevatedlifestyle.communication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommunicationApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommunicationApplication.class, args);
	}
}
