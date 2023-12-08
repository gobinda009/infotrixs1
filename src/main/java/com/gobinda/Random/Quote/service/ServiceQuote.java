package com.gobinda.Random.Quote.service;


import com.gobinda.Random.Quote.model.Quote;
import com.gobinda.Random.Quote.repo.IRepoQuotes;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceQuote {

    @Autowired
    IRepoQuotes iRepoQuotes;


    public boolean hasCsvFormat(MultipartFile file) {
        String type = "text/csv";
        if (!type.equals(file.getContentType()))
            return false;
        return true;
    }

    public void processAndSaveData(MultipartFile file) {
        try {
            List<Quote> dataList = csvToDataList(file.getInputStream());
            iRepoQuotes.saveAll(dataList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Quote> csvToDataList(InputStream inputStream) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Quote> quotes = new ArrayList<>();
            List<CSVRecord> records = csvParser.getRecords();
            for (CSVRecord csvRecord : records) {
                Quote data = new Quote( csvRecord.get("quote"),
                        csvRecord.get("authors"));
                quotes.add(data);
            }
            return quotes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public List<Quote> getAlls() {

       return iRepoQuotes.findAll();
    }

    public Quote getAuthor(String author) {
        Quote quote = iRepoQuotes.findFirstByAuthor(author);
        if(quote==null){
            System.out.println("no author found!!!");
            return null;
        }
        return quote;
    }
}
