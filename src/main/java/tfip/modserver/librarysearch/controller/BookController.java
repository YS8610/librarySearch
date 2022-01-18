package tfip.modserver.librarysearch.controller;

import java.util.HashMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tfip.modserver.librarysearch.repository.BookRepository;
import tfip.modserver.librarysearch.service.BookService;

@Controller
@RequestMapping(path = "/book/{id}")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepo;

    Logger logger = Logger.getLogger(BookController.class.getName());
    
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String resultpage(@PathVariable(name="id",required = true) String id, Model model) {

        HashMap<String,String> bookDetail;
        boolean cached = false;

        try {
            if (bookRepo.hasID(id)){
                bookDetail = bookRepo.getID(id);
                cached = true;
            }
            else{
                bookDetail = bookService.getBookDetail(id);
                bookRepo.save(id, bookDetail);
            }
        
        String a = bookDetail.get("description");
        model.addAttribute("title", bookDetail.get("title"));
        model.addAttribute("Description", bookDetail.get("description"));
        model.addAttribute("cache", cached? "Yes":"no");


        } catch (Exception e) {
            logger.info("fail to get info for book detail in bookcontrollers");
        }
        return "detail";
    }
}
