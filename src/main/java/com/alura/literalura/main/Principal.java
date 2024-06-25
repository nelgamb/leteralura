package com.alura.literalura.main;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.service.ConsultAPI;
import com.alura.literalura.service.ConvertData;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    Scanner scanner = new Scanner(System.in);
    private ConsultAPI consultAPI = new ConsultAPI();
    private ConvertData convertData = new ConvertData();
    private final String BASE_URL_API = "https://gutendex.com/books/?search=";
    private String questionAPI = null;
    private List<Book> books;
    private List<Author> authors;
    List<Book> bookSearch;

    // Repositorys to BD
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    // repository to work with DB
    public Principal(BookRepository bookRepository, AuthorRepository authorRepository ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    } // end costructor


    // Menu console for app
    public void menuApp() {
        Integer option = -1;

        String menu = """
                ********************
                **** LiterAlura ****
                ********************
                
                1 - Buscar Libro
                2 - Listar libros registrados
                3 - Listar autores registrados 
                4 - Listar libros por idioma
                5 - Listar Autores por fecha en los que vivian
                6 - Listar Autor por nombre
                
                0 - Salir                              
                """;

        while (option != 0) {

            System.out.println(menu);
            System.out.println("Ingrese la opción que desea hacer");

            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea después de nextInt()
                switch (option) {
                    case 1:
                        searchBookInformation();
                        break;
                    case 2:
                        showSearchHistoryBook();
                        break;
                    case 3:
                        showAuthorHistory();
                        break;
                    case 4:
                        showBooksByLanguage();
                        break;
                    case 5:
                        listAuthorLiveforYear();
                        break;
                    case 6:
                        showAuthorForName();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...!");
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                        break;
                } // end switch case
            } else {
                System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
                scanner.next(); // Consumir la entrada no válida
            }
        } // end while
    } // end menuApp()


    // function to get data from the consult to API
    private String consultDataFromUserToAPI(String BASE_URL_API, String questionAPI) {
        String json = consultAPI.getDataFromAPI(BASE_URL_API + questionAPI);
        return json;
    } // end consultDataFromAPI()

    // Function to get de consulto from user and make de call to api and transform de json to DataJsonAPI.class
    private DataJsonAPI getConsultFromUser() {
        System.out.println("\n\nIngrese el TITULO del LIBRO que desea buscar: \n");
        String questionToUser = scanner.nextLine();
        questionAPI = questionToUser.toLowerCase().replace(" ", "%20");
        String json = consultDataFromUserToAPI(BASE_URL_API, questionAPI);
        return convertData.getDataToClass(json, DataJsonAPI.class);
    } // end getConsultFromUser()


    private void searchBookInformation() {
        DataJsonAPI dataJsonAPI = getConsultFromUser();
        DataBook dataBook = dataJsonAPI.results().get(0);
        DataAuthor dataAuthor = dataBook.authors().get(0);

        // Use a repository queries Check the Author and Book if exist
        Optional<Author> optionalAuthor = authorRepository.findByName(dataAuthor.name());
        Optional<Book> optionalBook = bookRepository.findByTitleContainsIgnoreCase(dataBook.title());

        // Var to save the autor information
        Author author;

        // Check Author
        if (optionalAuthor.isPresent()) {
            System.out.println("Autor Existente");
            author = optionalAuthor.get();
        } else {
            author = new Author(dataAuthor);
            authorRepository.save(author);
        }

        if (optionalBook.isPresent()) {
            System.out.println("Libro Existente");
        } else {
            Book book = new Book( dataBook, author );
            bookRepository.save(book);
            System.out.println(book);
        }

        System.out.println("""
                _________________________
                      Boook Buscado
                Titulo: %s
                Idioma: %s
                Autor: %s
                Descargas: %s
                _________________________
                """.formatted(dataBook.title(), dataBook.languages(), dataAuthor.name(), dataBook.download_count()));
    } // end searchBookInformation()

    private void showSearchHistoryBook() {
        books = bookRepository.findAll();
        books.forEach(System.out::println);
    } // end showSearchHistoryBook()

    private void showAuthorHistory() {
        authors = authorRepository.findAll();
        authors.forEach(System.out::println);
    } // end showAuthorHistory()

    private void showBooksByLanguage() {
        System.out.println("Ingrese el lenguaje que desea listar: ");
        var lang = scanner.nextLine().trim().toLowerCase();
        bookSearch = bookRepository.findByLanguagesContainsIgnoreCase(lang);

        if (bookSearch.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado.");
        } else {
            System.out.println("\nLibros por idioma -> " + bookSearch.get(0).getLanguages());
            bookSearch.forEach(System.out::println);
        }
    } // end showBooksByLanguage()

    public void listAuthorLiveforYear() {
        System.out.println("Digite el año de busqueda");
        Integer year = scanner.nextInt();

        authors = authorRepository.listAuthorLiveforYear(year);

        if (authors.isEmpty()) {
            System.out.println("No se encontro autor vivo por la fecha");
        } else {
            System.out.println("Autores vivos por fecha");
            authors.forEach(System.out::println);
        }
    } // end listAuthorLiveforYear()

    public void showAuthorForName() {
        System.out.println("Digite el author que desea buscar: ");
        String authhorToSearch = scanner.nextLine();

        Optional<Author> optionalAuthor = authorRepository.findByNameContainsIgnoreCase(authhorToSearch);

        if (optionalAuthor.isPresent()) {
            System.out.println(optionalAuthor);
        } else {
            System.out.println("Autor no encontrado");
        }
    } // end showAuthorForName


} // end Principal Class
