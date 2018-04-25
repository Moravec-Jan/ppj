package cz.moravec.model;


import javax.persistence.*;

import static cz.moravec.model.Country.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class Country {

    public static final String TABLE_NAME = "Country";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";


    @Id
    @GeneratedValue
    private int id;

    @Column(name = NAME_ATTRIBUTE)
    private String name;


    public Country() {
    }

    public Country(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Country [" + "id=" + id + ", name=" + name + "]";
    }
}
