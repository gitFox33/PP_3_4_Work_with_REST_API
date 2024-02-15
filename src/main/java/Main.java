
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring.boot_security.User;


public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://94.198.50.185:7081/api/users";

        /*GET*/
        /*Получение списка всех пользователей и session id в виде cookies */
        ResponseEntity<String> response1 = restTemplate.getForEntity(baseUrl, String.class);
        HttpHeaders headers1 = response1.getHeaders();
        String sessionID = headers1.getFirst("Set-Cookie");
        System.out.println(sessionID);


        /*post*/
        /*Сохранение пользователя с использованием cookies из GET метода*/
        User newUser = new User(3, "James", "Brown", (byte) 30);
        HttpEntity<User> requestEntity = new HttpEntity<>(newUser, createHeadersWithSessionID(sessionID));
        ResponseEntity<String> response2 = restTemplate.postForEntity(baseUrl, requestEntity, String.class);


        /*put*/
        /*Изменение пользователя с использованием cookies из GET метода*/
        User updatedUser = new User(3, "Thomas", "Shelby", (byte) 30);
        HttpEntity<User> requestEntity2 = new HttpEntity<>(updatedUser, createHeadersWithSessionID(sessionID));
        restTemplate.put(baseUrl, requestEntity2);
        System.out.println(updatedUser.toString());

        /*delete*/
        /*удаление пользователя с использованием cookies из GET метода*/
        User deletedUser = new User(3, "Thomas", "Shelby", (byte) 30);
        HttpEntity<User> requestEntity3 = new HttpEntity<>(deletedUser, createHeadersWithSessionID(sessionID));
        restTemplate.delete("http://94.198.50.185:7081/api/users/3", requestEntity3);

    }

    private static HttpHeaders createHeadersWithSessionID(String sessionID) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionID);
        return headers;

    }
}
