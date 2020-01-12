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
import rest_api.mysql_project.model.Delete;
import rest_api.mysql_project.model.StudentEntity;
import rest_api.mysql_project.okhttp.OkHttpClientPost;
import rest_api.mysql_project.repository.StudentRepository;
import rest_api.mysql_project.services.DeleteService;
import rest_api.mysql_project.services.DeleteServiceImpl;
import rest_api.mysql_project.services.StudentService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static java.lang.System.exit;


@Controller
public class ApiTestController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    StudentRepository repository;
    @Autowired
    DeleteServiceImpl deleteService;

    @Autowired
    StudentService studentService;


    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") String id) {

        System.out.println(id +"debug xx");
        deleteService.delete(Long.parseLong(id));
        return "allStudentPage";
    }

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
    public String getStudents(@ModelAttribute("student") StudentEntity student, Model model) {

        OkHttpClientPost example = new OkHttpClientPost();
        String json = "{\r\n" +
                    " \"firstName\" :\""+student.getFirstName()+"\",\r\n" +
                    " \"lastName\" : \""+student.getLastName()+"\",\r\n" +
                    " \"branch\" : \""+student.getBranch()+"\",\r\n" +
                    " \"emailId\" : \""+student.getEmailId()+"\"\r\n" +
                "}";
        System.out.println(json);
        String response = null;
        try {
            response = example.post("http://localhost:8080/student", json);
            model.addAttribute("response",response);

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("response",e);
        }
        System.out.println(response);

        return "result";
    }
    @GetMapping(value="/deleteStudent")
    public String delStudent( Model model) {

      model.addAttribute("objectt", new Delete());
//        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class).getBody();
        return "deleteStudent";
    }

//    @DeleteMapping(value="/deleteStudentApi")
    @RequestMapping(value="/deleteStudentApi", method = RequestMethod.DELETE)
    public String deleteStudent( @RequestParam("objectt") Long objectt, Model model) {
//        System.out.println(objectt.id);
        OkHttpClientPost example = new OkHttpClientPost();
        String response = null;
        try {
            response = example.delete("http://localhost:8080/student/"+objectt);
            model.addAttribute(response);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute(e);
        }
        System.out.println(response);
        return "result";
    }


}

