package com.gobinda.Random.Quote.controller;


import com.gobinda.Random.Quote.model.Quote;
import com.gobinda.Random.Quote.service.ServiceQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ControllerQoute {

    @Autowired
    ServiceQuote serviceQuote;

    @PostMapping("upload")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        if(serviceQuote.hasCsvFormat(file)){
            serviceQuote.processAndSaveData(file);
            return "Uploaded the file Successfully";
        }
        return "Please upload csv file";
    }

    @GetMapping("all")
    public List<Quote> getAll(){
        return serviceQuote.getAlls();
    }

    @GetMapping("author/{author}")
    public Quote getByAuthor(@PathVariable String author){
        return serviceQuote.getAuthor(author);
    }
}
