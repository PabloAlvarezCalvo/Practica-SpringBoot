package com.teachers.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.teachers.model.Teacher;
import com.teachers.service.IServiceTeacher;

@RequestMapping("/")
@RestController
public class TeachersRestController {

	@Autowired
	private IServiceTeacher serviceTeacher;


	@GetMapping("/")
	public ResponseEntity<Object> hello(){
		return ResponseEntity.status(HttpStatus.FOUND)
		        .location(URI.create("/teachers"))
		        .build();
	}
	
	@GetMapping("/teachers")
	public List<Teacher> listar(){
		return serviceTeacher.findAll();
		
	}

	@DeleteMapping("/teachers/{id}")
	public String delete(@PathVariable Long id){
		try {
			serviceTeacher.deleteById(id);
			return "OK";
		}catch(Exception e){
			return "ERROR: " + e;
		}
	}

	@PostMapping("/teachers")
	public String create(@RequestBody String body) {
		try {
			Map < String, Object > map = ParseJsonToMap(body);
			Teacher teacher = new Teacher(map.get("firstName").toString(), map.get("lastName").toString(), map.get("course").toString());
			serviceTeacher.save(teacher);
			return "OK";
		}catch(Exception e){
			return "ERROR: " + e;
		}
	}
	
	@PutMapping("/teachers/{id}")
	public String update(@PathVariable Long id, @RequestBody String body) {
		try {
			Map < String, Object > map = ParseJsonToMap(body);
			Teacher teacher = new Teacher(id, map.get("firstName").toString(), map.get("lastName").toString(), map.get("course").toString());
			serviceTeacher.save(teacher);
			return "OK";
		}catch(Exception e){
			return "ERROR: " + e;
		}
	}
	

	public Map < String, Object > ParseJsonToMap(String json) {
		JsonParser springParser = JsonParserFactory.getJsonParser();
		Map < String, Object > map = springParser.parseMap(json);
		return map;
	}	

}
