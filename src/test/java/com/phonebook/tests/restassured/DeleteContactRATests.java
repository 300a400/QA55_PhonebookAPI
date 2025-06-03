package com.phonebook.tests.restassured;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactRATests extends TestBase {
    String id;
    @BeforeMethod
    public void precondition() {
        ContactDto contactDto = ContactDto.builder()
                .name("Tom")
                .lastName("Thecat")
                .email("meow@meow.de")
                .phone("98765432100")
                .address("Gatos")
                .description("Der Kater")
                .build();

        String message = given()
                .header(AUTHORIZATION, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");
        //System.out.println(message); //Contact was added! ID: 6f3b7483-6833-4a66-b17e-e4d384d4cd6c
        String[] split = message.split(": ");
        id = split[1];
    }
    @Test
    public void deleteContactSuccessTest() {
//        String message =
                given()
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
                //.extract().path("message");
        //System.out.println(message); Contact was deleted!
    }

    @Test
    public void deleteContactByWrongId() {
        given()
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete("contacts/6f3b7483-6833-4a66-b17e-e4d384d4cd69")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", containsString("not found in your contacts!"));
    }
}
