package cz.moravec.web;

import cz.moravec.model.Country;
import cz.moravec.model.Measurement;
import cz.moravec.model.Town;
import cz.moravec.model.projections.MeasurementAverage;
import cz.moravec.model.projections.MeasurementData;
import jdk.nashorn.internal.codegen.CompilerConstants;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RestApi {
    String TOWNS_PATH = "/towns";
    String TOWN_PATH = TOWNS_PATH + "/{id}";
    String COUNTRIES_PATH = "/countries";
    String COUNTRY_PATH = COUNTRIES_PATH + "/{id}";
    String MEASUREMENTS_PATH = "/measurements";
    String MEASUREMENT_PATH = MEASUREMENTS_PATH + "/{id}";
    String AVG_MEASUREMENT_PATH = "/avg-measurements" + "/{town_id}";
    String ACTUAL_MEASUREMENT_PATH = "/actual-measurements" + "/{town_id}";

    //region country
    @GET(COUNTRIES_PATH)
    Call<List<Country>> getCountries();

    @POST(COUNTRIES_PATH)
    Call<Country> createCountry(@Body Country country);

    @POST(COUNTRY_PATH)
    Call<Country> updateCountry(@Body Country country);

    @GET(COUNTRY_PATH)
    Call<Country> getCountry(@Path("id") long id);

    @DELETE(COUNTRY_PATH)
    Call<Void> deleteCountry(@Path("id") long id);

    //endregion
    //region  town
    @GET(TOWNS_PATH)
    Call<List<Town>> getTowns();

    @POST(TOWNS_PATH)
    Call<Town> createTown(@Body Town Town);

    @POST(TOWN_PATH)
    Call<Town> updateTown(@Body Town Town);

    @GET(TOWN_PATH)
    Call<Town> getTown(@Path("id") long id);

    @DELETE(TOWN_PATH)
    Call<Void> deleteTown(@Path("id") long id);

    //endregion
    //region measurement

    class Interval {
        public static final String DAY = "day";
        public static final String DEFAULT = DAY;
        public static final String WEEK = "week";
        public static final String TWO_WEEKS = "two_weeks";
    }

    @GET(MEASUREMENTS_PATH)
    Call<List<Measurement>> getMeasurements();

    @GET(ACTUAL_MEASUREMENT_PATH)
    Call<MeasurementData> getActualMeasurement(@Path("town_id") long id);

    @GET(AVG_MEASUREMENT_PATH)
    Call<MeasurementAverage> getAvgMeasurement(@Path("town_id") long id);

    @GET(AVG_MEASUREMENT_PATH)
    Call<MeasurementAverage> getAvgMeasurement(@Path("town_id") long id, @Query("from") String from);

    @POST(MEASUREMENTS_PATH)
    Call<Measurement> createMeasurement(@Body Measurement Measurement);

    @POST(MEASUREMENT_PATH)
    Call<Measurement> updateMeasurement(@Body Measurement Measurement);

    @GET(MEASUREMENT_PATH)
    Call<Measurement> getMeasurement(@Path("id") String id);

    @DELETE(MEASUREMENT_PATH)
    Call<Void> deleteMeasurement(@Path("id") String id);
    //endregion

}
