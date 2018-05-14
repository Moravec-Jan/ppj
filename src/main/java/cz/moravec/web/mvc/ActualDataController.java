package cz.moravec.web.mvc;

import cz.moravec.model.Country;
import cz.moravec.model.projections.MeasurementData;
import cz.moravec.service.CountryService;
import cz.moravec.service.MeasurementService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * MVC controller displaying current weather data.
 */
@Controller
public class ActualDataController {
    private static final int MAX_RECORDS =1000;
    private final CountryService countryService;
    private final MeasurementService measurementService;

    public ActualDataController(CountryService countryService, MeasurementService measurementService) {
        this.countryService = countryService;

        this.measurementService = measurementService;
    }

    @GetMapping({MvcApi.INDEX,MvcApi.ROOT,MvcApi.ACTUAL})
    public String index(Model model) {
        List<Country> countries = countryService.getAll(PageRequest.of(0, MAX_RECORDS));
        model.addAttribute("countries", countries);
        return "index";
    }

    @GetMapping(MvcApi.ACTUAL_SINGLE)
    public String getDataForCountry(@PathVariable("id") long id, Model model) {
        List<MeasurementData> measurements = measurementService.findAllByCountry(id);
        model.addAttribute("measurements", measurements);
        return "actual :: dataTable"; // thymeleaf fragment used
    }
}
