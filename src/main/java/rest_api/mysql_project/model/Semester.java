package rest_api.mysql_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "semester")
public class Semester  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String semester_no;

    public Semester() {
    }

    public Semester(String semester_no, List<Course> courses, List<Student> student) {
        this.semester_no = semester_no;
        this.courses = courses;
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSemester_no() {
        return semester_no;
    }

    public void setSemester_no(String semester_no) {
        this.semester_no = semester_no;
    }
    @JsonManagedReference
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    @JsonBackReference
    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    @OneToMany(mappedBy = "semester")
    private List<Course> courses;

    @OneToMany(mappedBy = "semesteer")
    private List<Student> student;
}
