package com.springers.SERVICES;

import java.util.List;

import com.springers.ENTITIES.Student;

public interface I_Service_Student {
	public void ajouter_Student(Student std);
	public void supprimer_Student(Long id);
	public List<Student> afficher_Students();
}
