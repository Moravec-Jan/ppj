package cz.moravec.data;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cz.moravec.data.Town.ID_ATTRIBUTE;
import static cz.moravec.data.Town.TABLE_NAME;

@Transactional
public class TownDao {


    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public List<Town> getAll() {
        Criteria criteria = session().createCriteria(Town.class);
        return criteria.list();
    }

    public void update(Town town) {
        session().saveOrUpdate(town);
    }

    public int create(Town town) {
        return (Integer) session().save(town);
    }


    public void create(List<Town> towns) {
        for (Town town : towns) {
            session().save(town);
        }
    }

    public boolean deleteAll(int id) {

        Query query = session().createQuery("DELETE FROM " + TABLE_NAME + " WHERE " + ID_ATTRIBUTE + "=:id");
        query.setLong(ID_ATTRIBUTE, id);
        return query.executeUpdate() == 1;
    }


    public Town get(int id) {
        Criteria criteria = session().createCriteria(Town.class)
                .add(Restrictions.idEq(id));

        return (Town) criteria.uniqueResult();
    }


    public int deleteAll() {
        return session().createQuery("DELETE FROM " + TABLE_NAME).executeUpdate();
    }
}
