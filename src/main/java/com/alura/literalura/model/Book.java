package com.alura.literalura.model;


import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private List<String> languages;
    private Integer download_count;

    // relationship to Author
    @ManyToOne // Foreign Key to Author
    private Author author;

    // Default Constructor for prevent the errors from JPA to BD
    public Book(){}

    // Cosntructor with the information from consult of API
    public Book( DataBook dataBook, Author author ) {
        this.title = dataBook.title();
        this.author = author;
        this.languages = dataBook.languages();
        this.download_count = dataBook.download_count();
    } // end Constructor from Json


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }


    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

    @Override
    public String toString() {
        return """
                _________________________
                         Boook
                Titulo: %s
                Idioma: %s
                Autor: %s
                Descargas: %s
                _________________________
                """.formatted(title, languages, author.getName(), download_count);


    }
} // end Book Class
