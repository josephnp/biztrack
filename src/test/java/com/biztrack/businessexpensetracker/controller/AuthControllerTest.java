package com.biztrack.businessexpensetracker.controller;

import com.biztrack.businessexpensetracker.model.Department;
import com.biztrack.businessexpensetracker.model.Role;
import com.biztrack.businessexpensetracker.utils.DataGenerator;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerTest extends AbstractTestNGSpringContextTests {

    public static final String AUTH_HEADER = "Authorization";
    private DataGenerator dataGenerator;
    private JSONObject req;

    private Boolean isOk = false;// untuk menjaga step estafet

    @BeforeClass
    void init() {

        dataGenerator = new DataGenerator();
        req = new JSONObject();
        System.out.println("Data Populated");
    }


    @Test()
    public void loginTest() {
        Response response;
        req.clear();
        if (!isOk) {
            try {
                req.put("email", "admin@example.com");
                req.put("password", "P@sword1!");

                response = given().
                        header("Content-Type", "application/json").
                        header("accept", "*/*").
                        body(req).
                        request(Method.POST, "auth/login");

                int intResponse = response.getStatusCode();
                JsonPath jsonPath = response.jsonPath();
                System.out.println(response.getBody().asPrettyString());

                if (intResponse == 200) {
                    isOk = true;
                }
                Assert.assertEquals(intResponse, 200);
                Assert.assertEquals(jsonPath.getString("message"), "Login Berhasil !!");
                Assert.assertNotNull(jsonPath.getString("data.menu"));
                Assert.assertNotNull(jsonPath.getString("data.token"));
                Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
                Assert.assertNotNull(jsonPath.getString("timestamp"));

            } catch (Exception e) {
                isOk = false;
            }
        } else {
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }


}
