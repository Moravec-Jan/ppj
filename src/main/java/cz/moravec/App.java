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
import cz.moravec.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;


@SpringBootApplication
public class App {

    @Profile({"test", "devel"})
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

//        SpringApplication.run(App.class, args);


        SpringApplication app = new SpringApplication(App.class);
        ApplicationContext ctx = app.run(App.class,args);

        WeatherService weatherService = ctx.getBean(WeatherService.class);
        try {
            Measurement measurement = weatherService.downloadActualDataForTown(14256);
        }catch (Exception e){
            e.printStackTrace();
        }

//        CountryService countryService = new CountryService(ctx.getBean(CountryRepository.class));
//        Iterable<Country> countries = countryService.getAll();
//
//        TownService townService = new TownService(ctx.getBean(TownRepository.class));
//        Iterable<Town> towns = townService.getAll(PageRequest.of(0,10));
//
//
//        MeasurementService measurementService = ctx.getBean(MeasurementService.class);
//        Measurement measurement = new Measurement(10f, 15f, 30f);
//        measurementService.save(measurement);
//        Iterable<Measurement> measurements = measurementService.getAll();
//        System.out.println(measurements.iterator().next());
    }

}