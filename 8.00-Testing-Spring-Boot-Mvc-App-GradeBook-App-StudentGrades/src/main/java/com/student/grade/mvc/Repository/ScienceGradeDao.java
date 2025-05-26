package com.student.grade.mvc.Repository;

import com.student.grade.mvc.model.ScienceGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScienceGradeDao extends CrudRepository<ScienceGrade,Integer> {
    public Iterable<ScienceGrade> findGradeByStudentId(int id);
    public void deleteByStudentId(int id);

}
