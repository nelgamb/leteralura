package com.alura.literalura.repository;

import com.alura.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    Optional<Author> findByNameContainsIgnoreCase(String name);

    @Query("SELECT a FROM Author a WHERE :year BETWEEN a.birth_year AND a.death_year")
    List<Author>  listAuthorLiveforYear( Integer year );
} // end AuthorRepository interface
