package cz.moravec;

import cz.moravec.data.Country;
import cz.moravec.data.Town;
import cz.moravec.provisioning.Provisioner;
import cz.moravec.repository.CountryRepository;
import cz.moravec.repository.TownRepository;
import cz.moravec.service.CountryService;
import cz.moravec.service.TownService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EntityScan("cz.moravec.data")
public class Main {

    @Profile({"test"})
    @Bean(initMethod = "doProvision")
    public Provisioner provisioner() {
        return new Provisioner();
    }


    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        CountryService countryService = new CountryService(ctx.getBean(CountryRepository.class));
        Iterable<Country> countries = countryService.getAll();

        TownService townService = new TownService(ctx.getBean(TownRepository.class));
        Iterable<Town> towns = townService.getAll();
        System.out.println(towns);

    }

}