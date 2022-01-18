package tfip.modserver.librarysearch.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/book/{id}")
public class BookController {
    
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String resultpage(@PathVariable(name="id",required = true) String id, Model model) {

        return "detail";
        
    }
}
