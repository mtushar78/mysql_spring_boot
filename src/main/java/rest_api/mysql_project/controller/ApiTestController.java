package rest_api.mysql_project.controller;

import okhttp3.*;
import okhttp3.RequestBody;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rest_api.mysql_project.model.Student;
import rest_api.mysql_project.model.StudentEntity;
import rest_api.mysql_project.okhttp.OkHttpClientPost;
import rest_api.mysql_project.repository.StudentRepository;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Controller
public class ApiTestController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    StudentRepository repository;

    @GetMapping
    public String homePage(){
        return "home_view";
    }
    @GetMapping("/getAllStudents")
    public String home_Page(Model model){
        String url = "http://localhost:8080/student";
        List<StudentEntity> studentEntityList = restTemplate.getForObject(url,List.class);
        model.addAttribute("students", studentEntityList);
        return "allStudentPage";
    }

    @GetMapping("/greeting")
    public String getView(Model model) {

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        StudentEntity studentEntity = restTemplate.getForObject("http://localhost:8080/student/1", StudentEntity.class);
        String name = studentEntity.getFirstName();
        model.addAttribute("name", name);
        return "greetings";
    }

    @GetMapping(value="/addStudent")
    public String getStudents( Model model) {

        model.addAttribute("student",new StudentEntity());
//        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class).getBody();
        return "creatStudent";
    }
    @RequestMapping(value = "/addStudentPost", method = RequestMethod.POST)
    public String getStudents(@ModelAttribute("student") Student student) {

        OkHttpClientPost example = new OkHttpClientPost();
        String json = "{\r\n" +
                    " \"firstName\" :\""+student.getFname()+"\",\r\n" +
                    " \"lastName\" : \""+student.getLname()+"\",\r\n" +
                    " \"email\" : \""+student.getEmail()+"\"\r\n" +
                    " \"semester\" : \""+student.getSemesteer()+"\"\r\n" +
                "}";
        System.out.println(json);
        String response = null;
        try {
            response = example.post("http://localhost:8080/student/api/new", json);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);

        return "result";
    }
    @GetMapping(value="/deleteStudent")
    public String delStudent( Model model) {

        model.addAttribute("student", new String());
//        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class).getBody();
        return "deleteStudent";
    }

    @RequestMapping(value="/deleteStudentApi" , method = RequestMethod.DELETE)
    public String deleteStudent( @ModelAttribute("delStudent") String delStudent, Model model) {
        System.out.println(delStudent);
        OkHttpClientPost example = new OkHttpClientPost();
        String response = null;
        try {
            response = example.delete("http://localhost:8080/student/"+delStudent);
            model.addAttribute(response);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute(e);
        }
        System.out.println(response);
        return "result";
    }


}

