package com.mediscreen.historic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.ResourceUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mediscreen.historic.dao.HistoricDao;
import com.mediscreen.historic.domain.Historic;


@SpringBootApplication
public class HistoricApplication implements CommandLineRunner {

	@Autowired
	private HistoricDao historicDao;
	
	public static void main(String[] args) {
		SpringApplication.run(HistoricApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		historicDao.deleteAll();
		
		List<Historic> dataSome = new ArrayList<>();
		dataSome.add(new Historic("61937e698bcc2a1d678ae0b6", 1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam ullamcorper erat sit amet nisl vulputate eleifend. Quisque ac erat in leo congue vehicula et vel tellus. Nulla sem metus, scelerisque vitae tellus vel, venenatis bibendum felis. Ut volutpat fringilla odio, congue vehicula magna dictum a. Quisque scelerisque feugiat vestibulum. Sed vitae lectus odio. Integer sed auctor purus. Praesent vulputate leo a fermentum eleifend."));
		dataSome.add(new Historic("61937e698bcc2a1d678ae0b7", 1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam a aliquet mi. Donec vel orci sit amet ante dapibus sollicitudin. Pellentesque ultricies facilisis nunc, at elementum justo finibus vitae. Nam varius sapien metus, imperdiet semper ipsum porttitor nec. Quisque lorem urna, auctor a tortor eu, molestie iaculis lacus. Praesent imperdiet quam nulla, sit amet blandit risus tempor sed. Aenean commodo nibh vitae semper mattis. Nam ut consectetur sapien."));
		dataSome.add(new Historic("61937e698bcc2a1d678ae0b8", 2, "Note note note"));
		dataSome.add(new Historic("61937e698bcc2a1d678ae0b9", 2, "Note note note"));
		historicDao.saveAll(dataSome);
		
		
		BufferedReader br = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:HistoricData.json")));
		Type type = new TypeToken<List<Historic>>() {
        }.getType();
        List<Historic> inpList = new Gson().fromJson(br, type);
        historicDao.saveAll(inpList);
        
	}
	
}
