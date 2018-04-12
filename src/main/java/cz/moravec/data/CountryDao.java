package cz.moravec.data;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static cz.moravec.data.Country.ID_ATTRIBUTE;
import static cz.moravec.data.Country.TABLE_NAME;

@Transactional
public class CountryDao {


    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public List<Country> getCountries() {
        Criteria criteria = session().createCriteria(Country.class);
        return criteria.list();
    }

    public void update(Country country) {
        session().saveOrUpdate(country);
    }

    public int create(Country country) {
        return (Integer) session().save(country);
    }


    public void create(List<Country> countries) {
        for (Country country : countries) {
            session().save(country);
        }
    }

    public boolean delete(int id) {

        Query query = session().createQuery("DELETE FROM " + TABLE_NAME + " WHERE " + ID_ATTRIBUTE + "=:id");
        query.setLong(ID_ATTRIBUTE, id);
        return query.executeUpdate() == 1;
    }


    public Country getCountry(int id) {
        Criteria criteria = session().createCriteria(Country.class)
                .add(Restrictions.idEq(id));

        return (Country) criteria.uniqueResult();
    }


    public int deleteCountries() {
        return session().createQuery("DELETE FROM " + TABLE_NAME).executeUpdate();
    }

}
