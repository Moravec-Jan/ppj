package cz.moravec.data;

import javax.persistence.*;

@Entity
@Table(name = "town")
public class Town {
    public static final String TABLE_NAME = "Town";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String COUNTRY_ATTRIBUTE = "country";

    @Id
    private int id;

    @Column(name = "name")
    private String name;
    @ManyToOne
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "country_id")
    private Country country;

    public Town(int id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Town() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
