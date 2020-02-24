package rest_api.mysql_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest_api.mysql_project.exceptions.RecordNotFoundException;
import rest_api.mysql_project.model.Student;
import rest_api.mysql_project.model.StudentEntity;
import rest_api.mysql_project.repository.NewStudentRepo;
import rest_api.mysql_project.services.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    NewStudentRepo newStudentRepo;


    @GetMapping
    public ResponseEntity<List<StudentEntity>> getAllStudents() {
        List<StudentEntity> studentEntityList = studentService.getAllStudents();
        return new ResponseEntity<List<StudentEntity>>(studentEntityList, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentEntity> getStudentById(@PathVariable("id") Long id) throws RuntimeException {
        StudentEntity studentEntity = studentService.getStudentByID(id);
        return new ResponseEntity<StudentEntity>(studentEntity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentEntity> createOrUpdateStudent(@Valid @RequestBody StudentEntity student)
            throws RecordNotFoundException {
        StudentEntity updated = studentService.createOrUpdateStudent(student);
        return new ResponseEntity<StudentEntity>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteStudentById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        studentService.deleteStudentById(id);
        return HttpStatus.FORBIDDEN;
    }

    @GetMapping(value = "/api/new")
    public ResponseEntity<List<Student>> getAllNewStudents() {
        List<Student> student = newStudentRepo.findAll();
        return new ResponseEntity<List<Student>>(student, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/api/new" , consumes = "application/json", produces = "application/json")
    public ResponseEntity<Student> createNewStudent(@Valid @RequestBody Student student) {
        /*HttpStatus newStudent = studentService.createNewStudent(student);
        if (newStudent == HttpStatus.CREATED) {
            return new ResponseEntity<Student>(student, new HttpHeaders(), newStudent);
        }
        return new ResponseEntity<Student>(new Student(), new HttpHeaders(), newStudent);*/
        Student newStudent =  newStudentRepo.save(student);
        return new ResponseEntity<Student>(newStudent,new HttpHeaders(),HttpStatus.CREATED);
    }

}
