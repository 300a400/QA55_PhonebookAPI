package com.phonebook.tests.restassured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {
    public static final  String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYXBpdGVzdDU1QHRlc3QuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NDk0NTY5NzEsImlhdCI6MTc0ODg1Njk3MX0.QBQhNIxdQkrHNkWfFhp1i5DKFv4Fk3GKv_6NJR8epyU";
    public static final String AUTHORIZATION = "Authorization";

    @BeforeMethod
    public void init() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "/v1";
    }
}
