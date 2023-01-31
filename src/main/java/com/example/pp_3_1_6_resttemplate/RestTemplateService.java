package com.example.pp_3_1_6_resttemplate;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestTemplateService {
    private static final String URL_USERS = "http://94.198.50.185:7081/api/users";
    private static final String URL_USER_DELETE = "http://94.198.50.185:7081/api/users/3";

    private static final RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    String result = "";


    public static void main(String[] args) {
        RestTemplateService RestTemplateService = new RestTemplateService();

        RestTemplateService.getEmployees();
        RestTemplateService.createEmployee();
        RestTemplateService.updateEmployee();
        RestTemplateService.deleteEmployee();

    }

    private void getEmployees() {  //https://codeflex.co/java-rest-client-post-with-cookie/ полезная ссылочка
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(URL_USERS, HttpMethod.GET, entity,
                String.class);

        System.out.println(result);

        HttpHeaders header = result.getHeaders();

        String cookieArray = header.getFirst("Set-Cookie");
        String cookie = Arrays.stream(cookieArray.split(";"))
                .findFirst()
                .map(Object::toString)
                .orElse("");

        System.out.println("cookie: " + cookie);
        headers.set("Content-Type", "application/json");
        headers.set("Cookie", cookie);
    }

    private void createEmployee() {
        User newUser = new User(3L, "James", "Brown", (byte) 23);

        HttpEntity<User> requestBody = new HttpEntity<>(newUser, headers);
        String result1 = restTemplate.postForObject(URL_USERS, requestBody, String.class);
        System.out.println(result1);
        result = result1;
    }

    private void updateEmployee() {
        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 23);

        HttpEntity<User> requestBody = new HttpEntity<>(updatedUser, headers);
        ResponseEntity<String> userResponseEntity = restTemplate.exchange(URL_USERS, HttpMethod.PUT, requestBody, String.class);
        System.out.println("result 2: " + userResponseEntity.getBody());
        result += userResponseEntity.getBody();

    }

    private void deleteEmployee() {
        HttpEntity<Long> requestBody = new HttpEntity<>(headers);
        ResponseEntity<String> userResponseEntity = restTemplate.exchange(URL_USER_DELETE, HttpMethod.DELETE, requestBody, String.class);
        System.out.println("result 2: " + userResponseEntity.getBody());
        result += userResponseEntity.getBody();
        System.out.println("result: " + result);
    }
}
