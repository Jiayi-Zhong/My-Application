package edu.illinois.cs.cs124.ay2026.project;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Declares the HTTP requests we can make against the Supabase "sightings" table.
 * Retrofit reads these annotations at runtime and generates the actual network code.
 *
 * Supabase exposes every table as a REST endpoint at /rest/v1/<table-name>.
 * The "select=*" query parameter tells Supabase to return all columns.
 */
public interface SightingApi {

    /** Fetch all sightings, newest first. */
    @GET("sightings")
    Call<List<Sighting>> getAllSightings(
        @Query("select") String select,
        @Query("order") String order
    );

    /** Insert a new sighting. Supabase returns the created row. */
    @Headers("Prefer: return=representation")
    @POST("sightings")
    Call<List<Sighting>> addSighting(@Body Sighting sighting);
}
