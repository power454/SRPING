package pl.edu.wit;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.wit.model.Book;
import pl.edu.wit.readers.BookDomReader;
import pl.edu.wit.repository.BookDao;

import java.util.List;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);
    }

    @Bean
    InitializingBean initDatabase(BookDao bookDao){
        return () -> {
            BookDomReader reader =  new BookDomReader();

            reader.readFile().forEach(bookDao::add); // sposob 1

//            reader.readFile().forEach(book -> bookDao.add(book)); // sposob 2

//            final List<Book> books = reader.readFile(); // sposob 3
//            for(Book book:books){
//                bookDao.add(book);
//            }

        };

    }

}
