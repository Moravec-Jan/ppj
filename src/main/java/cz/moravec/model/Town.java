package cz.moravec.model;

import javax.persistence.*;

@Entity
@Table(name = Town.TABLE_NAME)
public class Town {
    static final String TABLE_NAME = "town";

    @Id
    private long id;

    @Column(name = "name")
    private String name;
    @ManyToOne
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "country_id")
    private Country country;

    public Town(long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Town() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Town [" + "id=" + id + ", name=" + name + ", country=" + country + "]";
    }
}
