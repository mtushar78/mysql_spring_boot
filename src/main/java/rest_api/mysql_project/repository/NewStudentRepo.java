package rest_api.mysql_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest_api.mysql_project.model.Student;

public interface NewStudentRepo extends JpaRepository<Student,Integer> {
}
