package com.student.grade.mvc.Repository;

import com.student.grade.mvc.model.HistoryGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryGradeDao extends CrudRepository<HistoryGrade,Integer> {
    public Iterable<HistoryGrade> findGradeByStudentId(int id) ;
    public void deleteByStudentId(int id);

}
