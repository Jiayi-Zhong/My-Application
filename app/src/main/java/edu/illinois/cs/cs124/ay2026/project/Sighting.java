package edu.illinois.cs.cs124.ay2026.project;

import com.google.gson.annotations.SerializedName;

/**
 * Represents one animal sighting, matching the columns in the Supabase
 * "sightings" table. Gson uses the @SerializedName annotations to map
 * the snake_case JSON field names to our Java field names.
 */
public class Sighting {

    public long id;

    @SerializedName("animal_type")
    public String animalType;

    public String species;

    @SerializedName("photo_url")
    public String photoUrl;

    public double latitude;
    public double longitude;

    @SerializedName("date_spotted")
    public String dateSpotted;

    @SerializedName("background_info")
    public String backgroundInfo;

    @SerializedName("protection_tips")
    public String protectionTips;

    @SerializedName("created_at")
    public String createdAt;
}
