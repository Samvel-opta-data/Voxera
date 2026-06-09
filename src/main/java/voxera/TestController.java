package voxera;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello")
    public String sayHello() {
        return "<h1>Hallo! Das ist meine erste SpringBoot Website!</h1>";
    }

}