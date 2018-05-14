package cz.moravec.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static cz.moravec.model.Country.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class Country {

    static final String TABLE_NAME = "country";

    @Id
    @GeneratedValue()
    private long id;
    private String name;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Country [" + "id=" + id + ", name=" + name + "]";
    }
}
