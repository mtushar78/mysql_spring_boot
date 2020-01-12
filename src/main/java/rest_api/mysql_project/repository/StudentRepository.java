package rest_api.mysql_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rest_api.mysql_project.model.StudentEntity;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    @Query(
            value = "SELECT * FROM student_details",
            nativeQuery = true)
    List<StudentEntity> fin();

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM student_details WHERE id =?1 ",
            nativeQuery = true)
    void del(Long id);
}
