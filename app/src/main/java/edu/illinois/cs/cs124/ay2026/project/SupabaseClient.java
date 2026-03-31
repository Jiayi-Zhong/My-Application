package edu.illinois.cs.cs124.ay2026.project;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides a single shared Retrofit instance configured to talk to Supabase.
 *
 * Every HTTP request to Supabase needs two headers:
 *   - apikey: identifies the project (uses the anon/public key)
 *   - Authorization: Bearer <key> — required by Supabase's Row Level Security
 *
 * We attach these automatically to every request using an OkHttp interceptor,
 * so the rest of the app never has to think about them.
 */
public class SupabaseClient {

    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    okhttp3.Request request = chain.request().newBuilder()
                        .addHeader("apikey", BuildConfig.SUPABASE_ANON_KEY)
                        .addHeader("Authorization", "Bearer " + BuildConfig.SUPABASE_ANON_KEY)
                        .addHeader("Content-Type", "application/json")
                        .build();
                    return chain.proceed(request);
                })
                .build();

            instance = new Retrofit.Builder()
                .baseUrl(BuildConfig.SUPABASE_URL + "/rest/v1/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return instance;
    }
}
