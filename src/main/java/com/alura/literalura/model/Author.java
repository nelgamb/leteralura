package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birth_year;
    private Integer death_year;

    @OneToMany( mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Book> books;

    // Default Constructor for prevent the errors from JPA to BD
    public Author(){}

    // Cosntructor with the information from consult of API
    public Author ( DataAuthor dataAuthor ) {
        this.name = dataAuthor.name();
        this.birth_year = dataAuthor.birth_year();
        this.death_year = dataAuthor.death_year();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return """
                _________________________
                        Author
                Nombre: %s
                Fecha Nacimiento: %s
                Fecha de muerte: %s        
                _________________________                
                """.formatted( name, birth_year, death_year );
    }
} // end Author class
