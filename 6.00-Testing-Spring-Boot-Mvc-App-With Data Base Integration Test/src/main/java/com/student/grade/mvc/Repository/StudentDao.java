package com.student.grade.mvc.Repository;

import com.student.grade.mvc.model.CollegeStudent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentDao extends CrudRepository<CollegeStudent, Integer> {
    public CollegeStudent findByEmailAddress(String emailAddress) ;
}
