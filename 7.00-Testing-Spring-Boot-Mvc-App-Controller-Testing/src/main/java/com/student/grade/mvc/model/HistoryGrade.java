package com.student.grade.mvc.model;

import jakarta.persistence.*;

@Entity
@Table(name = "history_grade")
public class HistoryGrade implements Grade{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name="student_id")
    private int studentId;
    @Column(name="grade")
    private double grade;

    public HistoryGrade() {

    }
    public HistoryGrade(double grade) {
        this.grade = grade;
    }


    @Override
    public double getGrade() {
        return grade;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id ;
    }

    @Override
    public int getStudentId() {
        return studentId;
    }

    @Override
    public void setStudentId(int studentId) {
        this.studentId = studentId ;
    }

    @Override
    public void setGrade(double grade) {
        this.grade = grade ;
    }
}
