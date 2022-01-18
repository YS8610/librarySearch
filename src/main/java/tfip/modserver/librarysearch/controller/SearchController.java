package tfip.modserver.librarysearch.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tfip.modserver.librarysearch.service.BookService;

@Controller
@RequestMapping(path = "/book")
public class SearchController {

    @Autowired
    BookService bookService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String searchBook(@RequestParam(required = true) String search, Model model) {

        Map<String, String> results = bookService.search(search);

        // results.forEach((k,v) -> System.out.println(k +" " + v));
        
        System.out.println();
        


        return "result";
        
    }
}
