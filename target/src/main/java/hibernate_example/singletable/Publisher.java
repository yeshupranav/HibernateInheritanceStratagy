package hibernate_example.singletable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * A publisher is a person too because it has a name -- however a publisher has a Tax-ID and the list of their authors.
 *
 *
 */
@Entity
@DiscriminatorValue(value = "PUBLISHER")
public class Publisher extends Person {

    private String taxId;
    
   // private String abc;

    @OneToMany(mappedBy = "publisher")
    private final List<Author> authors = new ArrayList<Author>();

    public String getTaxId() {
        return this.taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public List<Author> getAuthors() {
        return this.authors;
    }
}
