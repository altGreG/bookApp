package com.bookApp.bookApp.controllers;

import com.bookApp.bookApp.Domain.Book;
import com.bookApp.bookApp.services.impl.ScraperServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/scraper")
public class ScraperController {

    protected static final Logger logger = LogManager.getLogger();

    private ScraperServiceImpl accessScraper;

    public ScraperController(ScraperServiceImpl accessScraper) {
        this.accessScraper = accessScraper;
    }

    @GetMapping(path="/getBook")
    public String getBook(@RequestParam(name="bookUrl", defaultValue="not_provided") String bookUrl,
                          @CookieValue(value = "username", defaultValue = "Unknown") String username,
                          Model model){

        logger.info(">>> SCRAPING DATA FROM LUBIMYCZYRAC.PL MODULE <<<");

        model.addAttribute("username", username);

        logger.info("Get request to scrap data from: " + "\n    - "+ bookUrl);

        Book scrapedBook = accessScraper.getFromLubimyCzytac(bookUrl);
        if(scrapedBook == null){
            Book book = new Book();

            book.setReleaseDate("00/00/0000");

            model.addAttribute("book", book);
            logger.info("Data can't be sraped");
            logger.info("Sent user to: Can't Srape Data Page");
            return "add-book-not-scraped";
        }else{
            model.addAttribute("book", scrapedBook);
            logger.info("Sent to user: Scraped Data About Book");
            return "add-book-scraped";
        }





    }
}
