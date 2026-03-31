package edu.illinois.cs.cs124.ay2026.project;

public class Organization {

    public String name;
    public String type;
    public String phone;
    public String email;
    public String website;
    public String address;
    public String description;
    public String hours;
    public boolean emergencyContact;

    public Organization(String name, String type, String phone, String email,
                        String website, String address, String description,
                        String hours, boolean emergencyContact) {
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.address = address;
        this.description = description;
        this.hours = hours;
        this.emergencyContact = emergencyContact;
    }
}
