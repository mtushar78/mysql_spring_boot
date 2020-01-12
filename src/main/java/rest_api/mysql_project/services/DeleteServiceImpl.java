package rest_api.mysql_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import rest_api.mysql_project.model.StudentEntity;
import rest_api.mysql_project.repository.DeleteRepository;
import rest_api.mysql_project.repository.StudentRepository;

import java.util.List;

@Service
public class DeleteServiceImpl implements DeleteService {
    @Autowired
    public StudentRepository studentRepository;


    @Override
    public void delete(Long id) {
        studentRepository.del(id);
    }

    @Override
    public List<StudentEntity> findAllStudent() {
        return studentRepository.fin();
    }
}
