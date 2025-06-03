package com.phonebook.tests.restassured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.UpdateContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PutContactsRATests extends TestBase {

    private String id;

    @BeforeMethod
    public void precondition() {
        ContactDto contactDto = ContactDto.builder()
                .name("Johnny")
                .lastName("Good")
                .email("jo5rt@test.com")
                .phone("0234567890")
                .address("London")
                .description("Original Description")
                .build();

        String message = given()
                .header(AUTHORIZATION, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");
        String[] split = message.split(": ");
        id = split[1];
    }

    @Test
    public void putContactSuccessTest() {
        UpdateContactDto updatedContact = UpdateContactDto.builder()
                .id(id)
                .name("NewName")
                .lastName("NewContact")
                .email("updated@test.com")
                .phone("0987654321")
                .address("Stuttgart")
                .description("etwas")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .contentType(ContentType.JSON)
                .body(updatedContact)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was updated"));
    }

    @Test
    public void putContactWrongPhoneNegativeTest() {
        UpdateContactDto updatedContact = UpdateContactDto.builder()
                .id(id)
                .name("NameFail")
                .lastName("Donthave")
                .email("fail@test.com")
                .phone("12345")
                .address("Bonn")
                .description("4failtest")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .contentType(ContentType.JSON)
                .body(updatedContact)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.phone", containsString("Phone number must contain only digits!"));
    }

    @Test
    public void putContactWrongIDNegativeTest() {
        UpdateContactDto updatedContact = UpdateContactDto.builder()
                .id("80455565-0072-aa43-7745-a4f798555000")
                .name("Mister")
                .lastName("Twister")
                .email("twister@test.com")
                .phone("9876543210")
                .address("London")
                .description("etwas")
                .build();

        given().header(AUTHORIZATION, TOKEN)
                .body(updatedContact)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", containsString("not found in your contacts!"));
    }

    @AfterMethod
    public void deleteContact() {
        given()
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
    }
}