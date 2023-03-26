package com.example.SpringBootDiaryApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringBootDiaryAppApplicationTests {

	@Test
	void contextLoads() {
		DriverManagerDataSource dataSource =
				new DriverManagerDataSource("jdbc:mysql://127.0.0.1:3306");
		try{
			Connection connection = dataSource.getConnection("root", "3284368");
			System.out.println(connection);
			assertThat(connection).isNotNull();
		}catch (SQLException exception){
			throw new RuntimeException(exception);
		}
	}
}
