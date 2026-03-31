# Animal Tracker - Project Plan

## App Overview
An educational map-based web app where users can report sightings of wild animals and stray animals in their area. Each sighting includes the animal's location, photo, species information, and tips on how to protect them. The goal is to raise awareness about local wildlife and help communities understand and care for animals in their neighborhoods.

## Key Screens/Views

### 1. Map View (Main Screen)
- **What users see:** An interactive map showing pins/markers for all reported animal sightings
- **What users can do:**
    - Browse the map and click on any pin to see animal details
    - Filter animals by type (wild animals vs. stray animals)
    - Click "Add Sighting" button to report a new animal

### 2. Animal Details View
- **What users see:** A popup or side panel showing information about a specific animal sighting
- **What users can do:**
    - View the animal's photo
    - Read species name, date spotted, and location
    - Learn background information about the species
    - Read tips on how to protect or help this type of animal

### 3. Add Sighting Form
- **What users see:** A form to submit a new animal sighting
- **What users can do:**
    - Upload a photo of the animal
    - Select location on map (or use current GPS location)
    - Enter species/type of animal
    - Add date of sighting
    - Write background information about the animal
    - Add protection/safety tips
    - Submit the sighting to the database

### 4. Resources Screen
- **What users see:** A list of local wildlife protection organizations and upcoming events
- **What users can do:**
    - Browse wildlife rescue centers and protection organizations
    - View contact information (phone, email, website)
    - See their location on a map
    - Filter by distance or type of organization
    - View upcoming events (rescue training, awareness campaigns, volunteer opportunities)
    - Get directions to organizations or events

## Core Features

### Must-Have (Version 1.0)
1. **Interactive map display** - Show all animal sightings as pins on a map
2. **Add new sightings** - Form to submit animal information with photo and location
3. **View sighting details** - Click any pin to see full information
4. **Photo upload** - Attach images to animal sightings
5. **Simple filtering** - Toggle between showing wild animals, stray animals, or all
6. **Resources directory** - List of local wildlife protection organizations with contact info
7. **Emergency contact access** - Quick way to find and contact rescue organizations
8. **Mobile-responsive design** - Works well on phones and computers

### Nice-to-Have (Future versions)
- User accounts and authentication
- Edit/delete your own sightings
- Search by species name or location
- Comments or updates on existing sightings
- Share individual sightings via link
- **Events calendar** - Upcoming wildlife protection events and volunteer opportunities
- **Event reminders** - Get notified about events you're interested in
- **Add new organizations** - User-submitted directory entries (with moderation)

## Data

### What we store for each animal sighting:
- **ID** - Unique identifier for each sighting (auto-generated)
- **Animal type** - Wild animal or stray animal (dropdown selection)
- **Species/name** - What kind of animal it is (text)
- **Photo URL** - Link to the uploaded image (string)
- **Latitude** - GPS coordinate (number)
- **Longitude** - GPS coordinate (number)
- **Date spotted** - When the animal was seen (date)
- **Background info** - Description/facts about the animal (text)
- **Protection tips** - How to help or protect this species (text)
- **Timestamp** - When the sighting was added to the app (auto-generated)

### What we store for each protection organization:
- **ID** - Unique identifier (auto-generated)
- **Name** - Organization name (text)
- **Type** - Wildlife rescue, rehabilitation center, advocacy group, etc. (dropdown)
- **Phone** - Contact phone number (text)
- **Email** - Contact email (text)
- **Website** - Organization website URL (text)
- **Address** - Physical address (text)
- **Latitude** - GPS coordinate (number)
- **Longitude** - GPS coordinate (number)
- **Description** - What the organization does (text)
- **Operating hours** - When they're available (text)
- **Emergency contact** - Whether they handle emergencies 24/7 (boolean)

### What we store for each event:
- **ID** - Unique identifier (auto-generated)
- **Title** - Event name (text)
- **Organization** - Who's hosting it (text)
- **Date** - When it happens (date)
- **Time** - Start time (text)
- **Location** - Where it takes place (text)
- **Latitude** - GPS coordinate (number)
- **Longitude** - GPS coordinate (number)
- **Description** - Event details (text)
- **Registration link** - URL to sign up if needed (text, optional)

### Where we store it:
**Firebase Firestore** - Cloud database (free tier available)
- Collection: "sightings" - All animal sighting reports
- Collection: "organizations" - Wildlife protection organizations and rescue centers
- Collection: "events" - Upcoming protection events and volunteer opportunities
- Each document represents one sighting/organization/event
- Automatically syncs to all users viewing the app

**Firebase Storage** - For storing uploaded photos
- Photos stored in organized folders
- URLs saved in the Firestore database

## External Dependencies

### Required Services:
1. **Firebase** (Google) - FREE tier available
    - Firestore Database - stores animal sighting data
    - Firebase Storage - stores uploaded photos
    - Firebase Hosting - makes the app accessible on the web

2. **OpenStreetMap + Leaflet.js** - FREE, no limits
    - Provides the interactive map display
    - No API key required
    - Open-source mapping library

### Development Tools:
- **React** - JavaScript framework for building the app
- **Node.js & npm** - For installing packages and running the development server
- **Git & GitHub** - Version control and code backup (optional but recommended)

### Browser APIs (built into browsers, no account needed):
- **Geolocation API** - Gets user's current location for easy sighting submission
- **File API** - Handles photo uploads from phone camera or gallery

## Technical Stack Summary
- **Frontend:** React + Leaflet.js
- **Backend/Database:** Firebase (Firestore + Storage)
- **Hosting:** Firebase Hosting or Vercel (both free)
- **No server needed** - Firebase handles everything backend-related

## Estimated Timeline (for a beginner with AI help)

- **Week 1-2:** Learn React basics, set up project, get a basic map showing
- **Week 3-4:** Add pin-dropping feature and connect to Firebase
- **Week 5:** Build the add sighting form and photo upload
- **Week 6:** Add resources screen with organizations directory
- **Week 7:** Add events listing and contact information
- **Week 8-9:** Polish UI, add filtering, test on mobile, deploy live

**Note:** You can launch with just animal sightings first (weeks 1-5), then add the resources/events feature in a second update. This lets you get something working sooner!

---

**Next Steps:**
1. Set up development environment (install Node.js, code editor)
2. Create Firebase account and project
3. Initialize React project with Vite or Create React App
4. Start with displaying a basic map using Leaflet.js