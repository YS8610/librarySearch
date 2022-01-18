package tfip.modserver.librarysearch.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/book")
public class SearchController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String searchBook(@RequestParam(required = true) String book, Model model) {




        return "result";
        
    }
}
