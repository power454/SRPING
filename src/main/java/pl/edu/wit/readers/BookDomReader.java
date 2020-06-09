package pl.edu.wit.readers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.edu.wit.Application;
import pl.edu.wit.model.Author;
import pl.edu.wit.model.Book;
import pl.edu.wit.model.Category;
import pl.edu.wit.model.Publisher;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class BookDomReader {
    public List<Book> readFile() throws ParserConfigurationException, IOException, SAXException {

        // utworzenie fabryki
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // utworzenie builder'a dokumentu
        final DocumentBuilder builder = factory.newDocumentBuilder();

        // pobranie classLoader - https://codecouple.pl/2019/01/18/class-loader-w-javie/
        final ClassLoader classLoader = Application.class.getClassLoader();
        // pobranie ścieżki do pliku zapisanej jako wskaźnik (Uniform Resource Locator) do zasobu - w tym przypadku XML z ksiązkami
        final URL booksURL = classLoader.getResource("books.xml");
        // sprawdzenie czy pobrany obiekt nie jest null'em . Gdy będzie dostaniemy błąd NullPointerException
        final URL notNullBooksURL = Objects.requireNonNull(booksURL);
        // pobranie ścieżki do pliki jako text ( String )
        final String file = notNullBooksURL.getFile();

        // tworzy reprezentacje obiektu jako Dokument
        final Document document = builder.parse(file);

        // pobierany główny element dokumentu
        final Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

        // pobierany node'y o nazwie book
        final NodeList nodes = document.getElementsByTagName("book");
        System.out.println("============================");

        // tworzymy pustą listę książek
        List<Book> books = new ArrayList<>();

        // przechodzimy przez wektor node'ów book
        for (int i = 0; i < nodes.getLength(); i++) {

            // pobieramy element node
            final Node node = nodes.item(i);

            // czytamy node i tworzymy obiekt książki
            final Book book = readBook(node);

            // dodamy książkę do listy jeżeli udało się stworzyć książkę.
            if (book != null) {
                books.add(book);
            }
        }

        return books;
    }

    private Book readBook(Node bookNode) {
        if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
            // zmiana Node na Element - możliwa ze względu na to że Element dziedziczy po interfasie Node
            Element bookElement = (Element) bookNode;
            // utworzenie obiektu Book
            Book book = new Book();
            // pobranie atrybutu id
            // ze względu na to że pobieramy wartości jako text ( String ) trzeba zamienić ją na wartość Integer
            final String idAsString = bookElement.getAttribute("id");
            // zmiana na Integer przy wykorzystaniu parseInt
            final Integer id = Integer.parseInt(idAsString);
            // ustawienie id książki z wartości zmiennej id pobranej z XML
            book.setId(id);

            // pobranie node'ow title z elementu book
            final NodeList title = bookElement.getElementsByTagName("title");
            // przy założeniu że tytuł w książce jest jeden - pobieramy pierwszy element wektora
            final Node titleNode = title.item(0);
            // pobieramy text elementu
            final String titleText = titleNode.getTextContent();
            // ustawiamy text tytułu do tytułu książki
            book.setTitle(titleText);

            // pobranie textu isbn z elementu książki ( to samo co w przypadku tytułu zapisane w jednej lini )
            book.setIsbn(bookElement.getElementsByTagName("isbn").item(0).getTextContent());

            // pobieramy pozostałe elementy pojedyncze
            book.setPages(Integer.parseInt(bookElement.getElementsByTagName("pages").item(0).getTextContent()));
            book.setDescription(bookElement.getElementsByTagName("description").item(0).getTextContent());
            book.setPrice(new BigDecimal(bookElement.getElementsByTagName("price").item(0).getTextContent()));

            // pobieranie złożonych elementów
            readCategory(book, bookElement.getElementsByTagName("category").item(0));
            readPublisher(book, bookElement.getElementsByTagName("publisher").item(0));
            readAuthors(book, bookElement.getElementsByTagName("author"));

            return book;
        }

        return null;
    }

    private void readAuthors(Book book, NodeList authors) {
        for (int i = 0; i < authors.getLength(); i++) {
            final Node authorNode = authors.item(i);
            if (authorNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element authorElement = (Element) authorNode;

                final Author author = new Author();
                author.setId(Integer.parseInt(authorElement.getAttribute("id")));
                author.setName(authorElement.getElementsByTagName("name").item(0).getTextContent());
                author.setLastName(authorElement.getElementsByTagName("lastName").item(0).getTextContent());
                author.setBooks(new HashSet<>());
                author.getBooks().add(book);

                book.addAuthor(author);
            }
        }
    }

    private void readPublisher(Book book, Node publisherNode) {
        if (publisherNode.getNodeType() == Node.ELEMENT_NODE) {
            final Element publisherElement = (Element) publisherNode;
            final Publisher publisher = new Publisher();

            publisher.setId(Integer.parseInt(publisherElement.getAttribute("id")));
            publisher.setName(publisherElement.getElementsByTagName("name").item(0).getTextContent());
            publisher.setBooks(new HashSet<>());
            publisher.getBooks().add(book);

            book.setPublisher(publisher);
        }
    }

    private void readCategory(Book book, Node categoryNode) {
        if (categoryNode.getNodeType() == Node.ELEMENT_NODE) {
            final Element categoryElement = (Element) categoryNode;
            final Category category = new Category();

            category.setName(categoryElement.getTextContent());
            category.setId(Integer.parseInt(categoryElement.getAttribute("id")));

            book.setCategory(category);
        }
    }
}
