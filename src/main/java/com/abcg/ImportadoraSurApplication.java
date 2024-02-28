package com.abcg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //Linea para evitar la conexion a bases de datos cuando aún no se ha hecho
@SpringBootApplication
public class ImportadoraSurApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImportadoraSurApplication.class, args);
	}

}
