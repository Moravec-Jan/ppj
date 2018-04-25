package cz.moravec;

import cz.moravec.model.Country;
import cz.moravec.model.Measurement;
import cz.moravec.model.Town;
import cz.moravec.provisioning.MongoProvisioner;
import cz.moravec.provisioning.MySqlProvisioner;
import cz.moravec.repository.CountryRepository;
import cz.moravec.repository.TownRepository;
import cz.moravec.service.CountryService;
import cz.moravec.service.MeasurementService;
import cz.moravec.service.TownService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;


@SpringBootApplication
public class App {

    @Profile({"test","devel"})
    @Bean
    public MySqlProvisioner sqlProvisioner() {
        return new MySqlProvisioner();
    }

    @Profile("devel")
    @Bean
    public MongoProvisioner mongoProvisioner(MongoTemplate mongo) {
        return new MongoProvisioner(mongo);
    }

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(App.class);
        ApplicationContext ctx = app.run(args);

        CountryService countryService = new CountryService(ctx.getBean(CountryRepository.class));
        Iterable<Country> countries = countryService.getAll();

        TownService townService = new TownService(ctx.getBean(TownRepository.class));
        Iterable<Town> towns = townService.getAll();

        MeasurementService measurementService = ctx.getBean(MeasurementService.class);
        Iterable<Measurement> measurements = measurementService.getAll();
        System.out.println(measurements.iterator().next());
    }

}