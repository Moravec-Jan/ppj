package cz.moravec.data;

import javax.persistence.*;

@Entity
@Table(name = "town")
public class Town {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;
    @ManyToOne
//    Tato anotace zajišťuje, že před vymazáním všech uživatelů není nutné vymazávat také všechny objednávky
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "username")
    private Country country;

    public Town(int id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
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
