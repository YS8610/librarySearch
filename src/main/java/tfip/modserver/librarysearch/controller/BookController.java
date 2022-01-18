package tfip.modserver.librarysearch.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tfip.modserver.librarysearch.service.BookService;

@Controller
@RequestMapping(path = "/book/{id}")
public class BookController {

    @Autowired
    BookService bookService;
    
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String resultpage(@PathVariable(name="id",required = true) String id, Model model) {

        HashMap<String,String> bookDetail = bookService.getBookDetail(id);
        String a = bookDetail.get("description");


        model.addAttribute("title", bookDetail.get("title"));
        model.addAttribute("Description", bookDetail.get("description"));

        return "detail";
    }
}
