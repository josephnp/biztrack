package com.biztrack.businessexpensetracker.controller;

import com.biztrack.businessexpensetracker.model.Department;
import com.biztrack.businessexpensetracker.model.Role;
import com.biztrack.businessexpensetracker.utils.DataGenerator;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
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
public class UserControllerTest {

    private static final AtomicInteger employeeNumberCounter = new AtomicInteger(1);
    private static final Map<Long, Department> departments = new HashMap<>();
    private static final Map<Long, Role> roles = new HashMap<>();

    private JSONObject req;

    private DataGenerator dataGenerator;
    private String username;
    private String email;
    private String password;
    private Boolean isOk = false;


    @BeforeClass
    void init() {

        dataGenerator = new DataGenerator();
        req = new JSONObject();

        email = dataGenerator.dataEmail();
        password = dataGenerator.dataPassword();
        username = dataGenerator.dataUsername();

        departments.put(1L, new Department(1L, "Human Resources", "Manages HR activities"));
        departments.put(2L, new Department(2L, "Finance", "Handles financial operations"));
        departments.put(3L, new Department(3L, "IT", "Manages information technology"));

        // Pre-populate roles (IDs 1 to 4)
        roles.put(1L, new Role(1L, "Administrator", "Full system access"));
        roles.put(2L, new Role(2L, "Manager", "Departmental management"));
        roles.put(3L, new Role(3L, "Staff", "General employee role"));
        roles.put(4L, new Role(4L, "Intern", "Temporary learning role"));

        System.out.println("Data Populated");
    }

    @Test(priority = 20)
    public void addUser() {
        Response response;
        try {
            isOk = false;
            String currentEmployeeNumber = String.format("EMP%04d", employeeNumberCounter.getAndIncrement());
            Long departmentID = (long) new Random().nextInt(1, 4); // 1 (inclusive) to 4 (exclusive)
            Long roleID = (long) new Random().nextInt(1, 5); // 1 (inclusive) to 5 (exclusive)

            // Get a random Department ID from the pre-populated data
            Long[] departmentKeys = departments.keySet().toArray(new Long[0]);
            Long randomDepartmentId = departmentKeys[ThreadLocalRandom.current().nextInt(departmentKeys.length)];
            Department selectedDepartment = departments.get(randomDepartmentId);

            // Get a random Role ID from the pre-populated data
            Long[] roleKeys = roles.keySet().toArray(new Long[0]);
            Long randomRoleId = roleKeys[ThreadLocalRandom.current().nextInt(roleKeys.length)];
            Role selectedRole = roles.get(randomRoleId);

            req.put("fullname", username);
            req.put("email", email);
            req.put("employeeNumber", currentEmployeeNumber);
            req.put("password", password);
            req.put("deparment", selectedDepartment);
            req.put("role", selectedRole);

            response = given()
                    .header("Context-Type", "application/json")
                    .header("accept", "(*/*")
                    .body(req)
                    .request(Method.POST, "/api/user");
            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();

            System.out.println("Isi Response Body : " + response.getBody().asPrettyString());

            Assert.assertEquals(intResponse, 200);
            Assert.assertEquals(jsonPath.getString("data.email"), email);
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));


        } catch (Exception e) {
            isOk = false;
        }
    }
}
