package be.rds.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Author.
 */
@Entity
@Table(name = "author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birht_date")
    private LocalDate birhtDate;

    @OneToMany(mappedBy = "book2author")
    @JsonIgnore
    private Set<Book> author2books = new HashSet<>();

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

    public LocalDate getBirhtDate() {
        return birhtDate;
    }

    public void setBirhtDate(LocalDate birhtDate) {
        this.birhtDate = birhtDate;
    }

    public Set<Book> getAuthor2books() {
        return author2books;
    }

    public void setAuthor2books(Set<Book> books) {
        this.author2books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Author{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", birhtDate='" + birhtDate + "'" +
            '}';
    }
}
