package com.phonebook.tests.okhttp;

import com.google.gson.Gson;
import com.phonebook.dto.AuthRequestDto;
import com.phonebook.dto.AuthResponceDto;
import com.phonebook.dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginOkhttpTest {

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("apitest55@test.com")
                .password("Aa12345!")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        AuthResponceDto responceDto = gson.fromJson(response.body().string(), AuthResponceDto.class);
        System.out.println(responceDto.getToken());
    } //eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYXBpdGVzdDU1QHRlc3QuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NDk0NTY5NzEsImlhdCI6MTc0ODg1Njk3MX0.QBQhNIxdQkrHNkWfFhp1i5DKFv4Fk3GKv_6NJR8epyU


    @Test
    public void loginFail() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("apitest55test.com")
                .password("Aa12345!")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 401);
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(), "Login or Password incorrect");
    }
}
