package hibernate_example.singletable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * An author is a person where the name is the ID. Beside this it contains the publisher whom he/she writes to (one author can have only one
 * publisher) and the books this author has written.
 *
 *
 */
@Entity
@DiscriminatorValue(value = "AUTHOR")
public class Author extends Person {

    @ManyToOne
    @JoinColumn(name = "PUBLISHER_ID")
    private Publisher publisher;

    @ManyToMany(mappedBy = "authors")
    private final List<Book> books = new ArrayList<Book>();

    private String royalty;
    
    public Author(String name) {
        setName(name);
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} has written {1} book(s).", this.getName(), this.books.size());
    }
}
