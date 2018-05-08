package cz.moravec.web;

import cz.moravec.model.Country;
import cz.moravec.model.Measurement;
import cz.moravec.model.Town;
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

    @GET(MEASUREMENTS_PATH)
    Call<List<Measurement>> getMeasurements();

    @POST(MEASUREMENTS_PATH)
    Call<Measurement> createMeasurement(@Body Measurement Measurement);

    @POST(MEASUREMENT_PATH)
    Call<Measurement> updateMeasurement(@Body Measurement Measurement);

    @GET(MEASUREMENT_PATH)
    Call<Measurement> getMeasurement(@Path("id") String id);

    @DELETE(MEASUREMENT_PATH)
    Call<Void> deleteMeasurement(@Path("id") String id);

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


}
