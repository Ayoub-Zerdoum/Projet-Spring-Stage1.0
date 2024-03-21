package com.springers.SERVICES;

import com.springers.ENTITIES.Student;
import com.springers.REPOSITORIES.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service_Student implements I_Service_Student{
	@Autowired
	StudentRepo StdRepo;

	@Override
	public void ajouter_Student(Student std){
		Student std2 = StdRepo.save(std);
	}

	@Override
	public void supprimer_Student(Long id){
		StdRepo.deleteById(id);
	}

	@Override
	public List<Student> afficher_Students(){
		List<Student> students = (List<Student>) StdRepo.findAll();
		return students;
	}
}
