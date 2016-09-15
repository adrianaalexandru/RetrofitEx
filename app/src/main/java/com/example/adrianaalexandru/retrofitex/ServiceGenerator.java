package com.example.adrianaalexandru.retrofitex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adriana on 14/09/2016.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "http://localhost:8181/TaskManagement/rest/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Gson gson = new GsonBuilder()
            .setDateFormat(Constants.DATE_FORMAT)
            .create();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }


    /**
     * Creates a service with authentication in requests
     * @param serviceClass
     * @param authToken
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if (authToken != null) {
            // to set the authorization header value for any HTTP request executed with this OkHttp client
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", authToken)
                            // diff between header(override preexisting headers)/addHeader(add the header)
                            .addHeader("Cache-Control", "no-cache")
                            .addHeader("Cache-Control", "no-store")
                            //receive the server response in a specific format
                             //.header("Accept", "application/json")
                            .method(original.method(), original.body());



                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
