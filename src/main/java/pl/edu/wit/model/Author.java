package pl.edu.wit.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa encji autora z mapowaniem jpa
 */
// oznaczenie encji
@Entity
// oznaczenie tabeli
@Table(name = "authors")
// zapisana nazwana lista query
@NamedQueries({
        // zapisana nazwana query
        @NamedQuery(
                name = Author.QUERY_FIND_ALL,
                query = "select a from Author a")
})
@XmlAccessorType(XmlAccessType.FIELD)
public class Author {

    // statyczna definicja nazwy query
    public static final String QUERY_FIND_ALL = "authors.findAll"; // musi być unikatowe dla całego entity managera

    // konstruktor
    public Author() {
        books = new HashSet<>();
    }

    // adnotacja id
    @Id
    // adnotacja generowania wartosci - oczywiscie tylko w przypadku bazy danych
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute(name = "id")
    private Integer id;

    // definicja kolumny w bazie danych
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column(name = "last_name", nullable = false, columnDefinition = "varchar(50)")
    private String lastName;

    // definicja zlaczenia wiele do wielu
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Book> books;

    // gettery i settery
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
