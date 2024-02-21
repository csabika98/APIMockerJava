package csabika98.api.mocker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model) {
        // Add model attributes if needed
        return "index"; // This refers to index.html in the /resources/templates directory
    }
}
