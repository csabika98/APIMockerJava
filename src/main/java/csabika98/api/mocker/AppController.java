package csabika98.api.mocker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AppController {

    @RequestMapping("/")
    String home() {
        return "index2";
    }
}
