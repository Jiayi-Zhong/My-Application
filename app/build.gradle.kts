import java.util.Properties

plugins {
    id("com.android.application")
}

// Load credentials from local.properties (not committed to git).
val localProps = Properties()
val localPropsFile = rootProject.file("local.properties")
if (localPropsFile.exists()) {
    localProps.load(localPropsFile.inputStream())
}

android {
    namespace = "edu.illinois.cs.cs124.ay2026.project"
    compileSdk = 35

    defaultConfig {
        applicationId = "edu.illinois.cs.cs124.ay2026.project"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Expose Supabase credentials to Java code via BuildConfig.
        buildConfigField("String", "SUPABASE_URL", "\"${localProps["supabase.url"]}\"")
        buildConfigField("String", "SUPABASE_ANON_KEY", "\"${localProps["supabase.anonKey"]}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.fragment:fragment:1.8.5")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Retrofit for making HTTP requests to Supabase's REST API.
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // Gson converter so Retrofit automatically converts JSON to Java objects.
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
