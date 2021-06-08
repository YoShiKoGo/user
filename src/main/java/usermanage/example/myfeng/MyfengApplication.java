package usermanage.example.myfeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class MyfengApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyfengApplication.class, args);
    }

}
