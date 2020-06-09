package pl.edu.wit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wit.controller.dto.*;
import pl.edu.wit.model.Author;
import pl.edu.wit.model.Book;
import pl.edu.wit.model.Category;
import pl.edu.wit.model.Publisher;
import pl.edu.wit.repository.BookDao;
import pl.edu.wit.repository.db.BookDaoDB;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/books")
public class BookController {

    private final BookDao bookDao;

    public BookController(final BookDao repository) {
        this.bookDao = repository;
    }


//    @GetMapping
//    public List<BookDto> getAllBooks(){
//        final Collection<Book> books = repository.findAll();
//        List<BookDto> bookDtos = new ArrayList<>();
//        for (Book b: books){
//            bookDtos.add(new BookDto(b));
//        }
//
//        return bookDtos;
//    }


    @GetMapping
    public ResponseEntity<Collection<BookDto>> getAllBooks(){
        final Collection<Book> books = bookDao.findAll();

        final List<BookDto> collect = books.stream().map(BookDto::new)
        .collect(Collectors.toList());

        return ResponseEntity.ok(collect);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<BookDto> getBookById( @PathVariable("id") Integer id){
        final Book byId = bookDao.findById(id);
        final BookDto dto = new BookDto(byId);
        return ResponseEntity.ok(dto);
    }
    // GET filtr


    // create
    @PostMapping
    public ResponseEntity<Void> createBook( @RequestBody BookDto dto){
        final Book book = new Book();
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setTitle(dto.getTitle());
        book.setPages(dto.getPages());
        // category
        final CategoryDto categoryDto = dto.getCategory();
        final Category category = new Category();
        category.setName(categoryDto.getName());
        category.setId(categoryDto.getId());
        book.setCategory(category);

        //publisher
        final PublisherDto publisherDto = dto.getPublisher();
        final Publisher publisher = new Publisher();
        publisher.setId(publisherDto.getId());
        publisher.setName(publisherDto.getName());
        book.setPublisher(publisher);

        final Set<AuthorDto> authorsDto = dto.getAuthors();
        //authors 1 sposob
        for(AuthorDto authorDto: authorsDto){
            final Author author = new Author();
            author.setId(authorDto.getId());
            author.setName(authorDto.getName());
            author.setLastName(authorDto.getLastName());
            book.addAuthor(author);
        }
//        // authors 2 sposob
//
//        authorsDto.forEach(authorDto -> {
//            final Author author = new Author();
//            author.setId(authorDto.getId());
//            author.setName(authorDto.getName());
//            author.setLastName(authorDto.getLastName());
//            book.addAuthor(author);
//        });
        bookDao.add(book);
        return ResponseEntity.ok().build();
    }
    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook( @PathVariable("id") Integer id){
        bookDao.delete(id);
        return ResponseEntity.ok().build();
    }
    //update

    @PatchMapping("/patch/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable("id") Integer id,@RequestBody BookUpdateDto dto){
        bookDao.update(id, dto.getTitle(),dto.getIsbn());
        return ResponseEntity.ok().build();
    }
}
