package cz.moravec;

import cz.moravec.data.Country;
import cz.moravec.data.CountryDao;
import cz.moravec.data.TownDao;
import cz.moravec.provisioning.Provisioner;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootApplication
@EntityScan("cz.moravec.data")
@EnableTransactionManagement
public class Main {

//    @Bean
//    public CountryDao countryDao() {
//        return new CountryDao();
//    }
//
//    @Bean
//    public TownDao townDao() {
//        return new TownDao();
//    }
    @Autowired
    private EntityManager entityManagerFactory;

//    @Autowired
//    public void setEntityManagerFactory(EntityManagerFactory manager) {
//        entityManagerFactory = manager;
//    }

    @Bean
    public SessionFactory sessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new HibernateTransactionManager(entityManagerFactory.unwrap(SessionFactory.class));
    }

    @Profile({"devel", "test"})
    @Bean(initMethod = "doProvision")
    public Provisioner provisioner() {
        return new Provisioner();
    }

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        CountryDao countryDao = ctx.getBean(CountryDao.class);

        List<Country> countries = countryDao.getCountries();

//        UsersDao usersDao = ctx.getBean(UsersDao.class);
//
//        List<User> users = usersDao.getAllUsers();
//        System.out.println(users);

    }

}