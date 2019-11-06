package rest_api.mysql_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import rest_api.mysql_project.model.StudentEntity;

@SpringBootApplication

public class MysqlProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MysqlProjectApplication.class, args);
    }


}
