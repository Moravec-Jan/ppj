package cz.moravec.web.mvc;

import cz.moravec.model.Country;
import cz.moravec.model.Measurement;
import cz.moravec.model.Town;
import cz.moravec.model.projections.MeasurementData;
import cz.moravec.service.CountryService;
import cz.moravec.service.MeasurementService;
import cz.moravec.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ActualDataController {
    private final CountryService countryService;
    private final MeasurementService measurementService;

    public ActualDataController(CountryService countryService, MeasurementService measurementService) {
        this.countryService = countryService;

        this.measurementService = measurementService;
    }

    @GetMapping({"/actual", "/", "/index"})
    public String index(Model model) {
        List<Country> countries = countryService.getAll(PageRequest.of(0, 1000));
        model.addAttribute("countries", countries);
        return "index";
    }

    @GetMapping({"/actual/{id}"})
    public String getDataForCountry(@PathVariable("id") long id, Model model) {
        List<MeasurementData> measurements = measurementService.findAllByCountry(id);
        model.addAttribute("measurements", measurements);
        return "actual :: dataTable";
    }
}
