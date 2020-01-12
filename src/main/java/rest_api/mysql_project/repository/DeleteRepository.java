package rest_api.mysql_project.repository;

import org.springframework.data.repository.CrudRepository;
import rest_api.mysql_project.model.StudentEntity;

public interface DeleteRepository extends CrudRepository<StudentEntity, Long> {
}
