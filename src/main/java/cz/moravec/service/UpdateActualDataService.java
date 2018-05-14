package cz.moravec.service;

import cz.moravec.App;
import cz.moravec.config.Conditions;
import cz.moravec.config.WeatherProperties;
import cz.moravec.model.Measurement;
import cz.moravec.model.Town;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Service for scheduling download and persisting data.
 */
@Profile("!test")
@Conditional(Conditions.ReadOnlyModeDisabled.class)
@Service
public class UpdateActualDataService {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static final int batch = 1000;

    private final TownService townService;
    private final MeasurementService measurementService;
    private final WeatherService weatherService;
    private final long updateInterval;


    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    public UpdateActualDataService(TownService townService, MeasurementService measurementService, WeatherService weatherService, WeatherProperties properties, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.townService = townService;
        this.measurementService = measurementService;
        this.weatherService = weatherService;
        updateInterval = properties.getUpdateAfterSeconds() * 1000;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        downloadDataPeriodically();
    }

    // invoked after bean created
    private void downloadDataPeriodically() {
        threadPoolTaskScheduler.scheduleAtFixedRate(this::addNewMeasurementsForAllTowns, updateInterval);
    }

    private void addNewMeasurementsForAllTowns() {
        int page = 0;
        while (true) {
            List<Town> towns = townService.getAll(PageRequest.of(page++, batch));
            if (CollectionUtils.isEmpty(towns))
                return;
            addMeasurements(towns);
        }
    }

    private void addMeasurements(List<Town> towns) {
        for (Town town : towns) {
            Measurement measurement = downloadNewActualData(town);
            if (measurement != null) {
                measurementService.save(measurement);
            }
        }
    }

    private Measurement downloadNewActualData(Town town) {
        try {
            return weatherService.downloadActualDataForTown(town.getId());
        } catch (Exception e) {
            log.error("UpdateActualDataService: download failed", e);
            return null;
        }
    }
}
