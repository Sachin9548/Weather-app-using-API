
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {

    private static final String API_KEY = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
    private static final String BASE_URL = "https://api.weatherapi.com/v1/current.json";

    public static void main(String[] args) {
    System.out.println("enter your choice");
    System.out.println("1.Get weather\n 2.get wind speed \n 3.get temperature \n4.Exit");
    
    Scanner sc=new Scanner (System.in);
    System.out.println("Enter the Location");
        String location = sc.nextLine();

        try {
            URL url = new URL(BASE_URL + "?key=" + API_KEY + "&q=" + location);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject data = new JSONObject(response.toString());
                JSONObject current = data.getJSONObject("current");

                double tempC = current.getDouble("temp_c");
                double feelsLikeC = current.getDouble("feelslike_c");
                String condition = current.getJSONObject("condition").getString("text");

                System.out.println("Location: " + location);
                System.out.println("Temperature: " + tempC + "°C");
                System.out.println("Feels Like: " + feelsLikeC + "°C");
                System.out.println("Condition: " + condition);

            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
