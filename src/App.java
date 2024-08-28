import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        var apiKey = System.getenv("OPENAI_API_KEY");
        

        // Create a Scanner object to read input from the user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your message:");
        String userInput = scanner.nextLine();

        var body = String.format("""
                {
                    "model": "gpt-4",

                    "messages": [
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ]
                }""", userInput);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        // Close the scanner
        scanner.close();
    }
}
