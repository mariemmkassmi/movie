package tn.cs.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tn.cs.movie.domain.enumeration.Langue;

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Duration duration;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Langue language;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "publish_date")
    private ZonedDateTime publishDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_movie__membre_staff",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "membre_staff_id")
    )
    @JsonIgnoreProperties(value = { "movies" }, allowSetters = true)
    private Set<Staff> membreStaffs = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_movie__category",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties(value = { "movies" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Movie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Movie name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public Movie duration(Duration duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return this.description;
    }

    public Movie description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Langue getLanguage() {
        return this.language;
    }

    public Movie language(Langue language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Langue language) {
        this.language = language;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Movie imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ZonedDateTime getPublishDate() {
        return this.publishDate;
    }

    public Movie publishDate(ZonedDateTime publishDate) {
        this.setPublishDate(publishDate);
        return this;
    }

    public void setPublishDate(ZonedDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public Set<Staff> getMembreStaffs() {
        return this.membreStaffs;
    }

    public void setMembreStaffs(Set<Staff> staff) {
        this.membreStaffs = staff;
    }

    public Movie membreStaffs(Set<Staff> staff) {
        this.setMembreStaffs(staff);
        return this;
    }

    public Movie addMembreStaff(Staff staff) {
        this.membreStaffs.add(staff);
        staff.getMovies().add(this);
        return this;
    }

    public Movie removeMembreStaff(Staff staff) {
        this.membreStaffs.remove(staff);
        staff.getMovies().remove(this);
        return this;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Movie categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public Movie addCategory(Category category) {
        this.categories.add(category);
        category.getMovies().add(this);
        return this;
    }

    public Movie removeCategory(Category category) {
        this.categories.remove(category);
        category.getMovies().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        return id != null && id.equals(((Movie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", duration='" + getDuration() + "'" +
            ", description='" + getDescription() + "'" +
            ", language='" + getLanguage() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            "}";
    }
}
