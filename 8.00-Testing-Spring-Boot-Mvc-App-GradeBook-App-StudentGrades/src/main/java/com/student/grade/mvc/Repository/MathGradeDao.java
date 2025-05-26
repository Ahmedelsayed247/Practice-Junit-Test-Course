package com.student.grade.mvc.Repository;

import com.student.grade.mvc.model.MathGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathGradeDao extends CrudRepository<MathGrade,Integer> {

    Iterable<MathGrade> findGradeByStudentId(int id);

    public void deleteByStudentId(int id);
}
