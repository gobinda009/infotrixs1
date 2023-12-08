package com.gobinda.Random.Quote.repo;


import com.gobinda.Random.Quote.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepoQuotes extends JpaRepository<Quote,Integer> {

    Quote findFirstByAuthor(String author);
}
