package com.springers.REPOSITORIES;

import com.springers.ENTITIES.Professor;
import com.springers.ENTITIES.Student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepo extends CrudRepository<Professor, Long>{

}
