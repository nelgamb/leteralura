package com.alura.literalura.repository;

import com.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Derived Queries
    Optional<Book> findByTitleContainsIgnoreCase(String title); // find by name of Book

    @Query("SELECT b FROM Book b WHERE :language IN (b.languages)")
    List<Book> findByLanguagesContainsIgnoreCase(@Param("language") String language); // find by language of Book


} // end BookRepository interface
