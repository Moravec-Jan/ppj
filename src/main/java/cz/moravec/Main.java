package cz.moravec;

import cz.moravec.data.Country;
import cz.moravec.data.CountryDao;
import cz.moravec.data.Town;
import cz.moravec.data.TownDao;
import cz.moravec.provisioning.Provisioner;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@SpringBootApplication
@EntityScan("cz.moravec.data")
public class Main {

    @Bean
    public CountryDao countryDao() {
        return new CountryDao();
    }

    @Bean
    public TownDao townDao() {
        return new TownDao();
    }


    @Autowired
    @Bean
    public SessionFactory sessionFactory(ObjectProvider<EntityManagerFactory> factory) {
        return factory.getIfAvailable().unwrap(SessionFactory.class);
    }

    @Profile({"test"})
    @Bean(initMethod = "doProvision")
    public Provisioner provisioner() {
        return new Provisioner();
    }


    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        CountryDao countryDao = ctx.getBean(CountryDao.class);
        List<Country> countries = countryDao.getCountries();

        TownDao townDao = ctx.getBean(TownDao.class);
        List<Town> towns = townDao.getAll();
        System.out.println(towns);

    }

}