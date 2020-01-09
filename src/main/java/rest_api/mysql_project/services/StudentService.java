package rest_api.mysql_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest_api.mysql_project.exceptions.RecordNotFoundException;
import rest_api.mysql_project.model.StudentEntity;
import rest_api.mysql_project.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public List<StudentEntity> getAllStudents() {
        List<StudentEntity> studentList = studentRepository.findAll();
        if (studentList.size() > 0) {
            return studentList;
        } else {
            return new ArrayList<StudentEntity>();
        }
    }

    public StudentEntity getStudentByID(Long id) throws RecordNotFoundException {
        Optional<StudentEntity> student = studentRepository.findById(id);
        //OPTIONAL validates the null pointer exception
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new RecordNotFoundException("Record Not Found ! ", id);
        }
    }

    public StudentEntity createOrUpdateStudent(StudentEntity entity) throws RecordNotFoundException {

        if (entity.getId() != null) {
            Optional<StudentEntity> student = studentRepository.findById(entity.getId());

            if (student.isPresent()) {
                StudentEntity newEntity = student.get();
                newEntity.setEmailId(entity.getEmailId());
                newEntity.setFirstName(entity.getFirstName());
                newEntity.setLastName(entity.getLastName());
                newEntity.setBranch(entity.getBranch());

                newEntity = studentRepository.save(newEntity);

                return newEntity;
            } else {
                entity = studentRepository.save(entity);
                return entity;
            }
        } else {
            entity = studentRepository.save(entity);
            return entity;
        }
    }
    public void deleteStudentById(Long id) throws RecordNotFoundException{
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        if(studentEntity.isPresent()){
            studentRepository.deleteById(id);
        }else{
            throw new RecordNotFoundException("Record Not Found with the id", id);
        }
    }
}
