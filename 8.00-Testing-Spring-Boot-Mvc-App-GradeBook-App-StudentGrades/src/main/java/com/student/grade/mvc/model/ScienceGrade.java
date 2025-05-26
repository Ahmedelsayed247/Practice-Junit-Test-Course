package com.student.grade.mvc.model;

import jakarta.persistence.*;

@Entity
@Table(name = "science_grade")
public class ScienceGrade implements Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "student_id")
    private int studentId;
    @Column(name = "grade")
    private double grade;

    public ScienceGrade() {

    }
    public ScienceGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getStudentId() {
        return studentId;
    }

    @Override
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public double getGrade() {
        return grade;
    }

    @Override
    public void setGrade(double grade) {
        this.grade = grade;
    }
}
