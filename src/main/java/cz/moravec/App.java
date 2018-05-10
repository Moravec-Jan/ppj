package cz.moravec;

import cz.moravec.model.projections.MeasurementAverage;
import cz.moravec.model.projections.MeasurementData;
import cz.moravec.service.MeasurementService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class App {


    public static void main(String[] args) {

//        SpringApplication.run(App.class, args);
        ApplicationContext ctx = SpringApplication.run(App.class,args);

        MeasurementService measurementService = ctx.getBean(MeasurementService.class);
        try {
            MeasurementAverage measurementDay = measurementService.getAverageForDay(1);
            MeasurementAverage measurementWeek = measurementService.getAverageForWeek(1);
            MeasurementAverage measurementTwoWeeks = measurementService.getAverageForTwoWeeks(1);
            MeasurementData byTownId = measurementService.findActualWeatherDataForTown(1);
            List<MeasurementData> allByCountry = measurementService.findAllByCountry(1);

            System.out.println();
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