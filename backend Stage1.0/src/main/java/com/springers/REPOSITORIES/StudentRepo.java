package com.springers.REPOSITORIES;

import com.springers.ENTITIES.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends CrudRepository<Student, Long>{

}
