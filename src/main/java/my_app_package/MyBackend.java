package my_app_package;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class MyBackend
 */
public class MyBackend extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyBackend() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String apiKey = "7d8b91bfec9e76cb9ac20ca86da46d30";                
		String city = request.getParameter("city");

		String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

		try {
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			InputStream inputStream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);

			Scanner scanner = new Scanner(reader);
			StringBuilder responseContent = new StringBuilder();

			while (scanner.hasNext()) {
				responseContent.append(scanner.nextLine());
			}
			scanner.close();

			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);

			long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000;
			int timezoneOffset = jsonObject.get("timezone").getAsInt();
			String date = formatDate(dateTimestamp, timezoneOffset);

			double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
			float temperatureCelsius = (float) (temperatureKelvin - 273.15);

			double feelsLikeKelvin = jsonObject.getAsJsonObject("main").get("feels_like").getAsDouble();
			float feelsLikeCelcius = (float) (feelsLikeKelvin - 273.15);

			double minTemperatureKelvin = jsonObject.getAsJsonObject("main").get("temp_min").getAsDouble();
			float minTemperatureCelsius = (float) (minTemperatureKelvin - 273.15);

			double maxTemperatureKelvin = jsonObject.getAsJsonObject("main").get("temp_max").getAsDouble();
			float maxTemperatureCelsius = (float) (maxTemperatureKelvin - 273.15);

			int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
			double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
			String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();

			String iconCode = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
			String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";

			long sunRiseTimeStamp = jsonObject.getAsJsonObject("sys").get("sunrise").getAsLong();
			long sunSetTimeStamp = jsonObject.getAsJsonObject("sys").get("sunset").getAsLong();
			String sunRiseTime = convertUnixTimestampToLocalTime(sunRiseTimeStamp, timezoneOffset);
			String sunSetTime = convertUnixTimestampToLocalTime(sunSetTimeStamp, timezoneOffset);

			String countryCode = jsonObject.getAsJsonObject("sys").get("country").getAsString();
			city = capitalize(city);
			String locationName=city + ", "  + countryCode;
			
			request.setAttribute("date", date);
			request.setAttribute("city", locationName);
			request.setAttribute("temperature", temperatureCelsius);
			request.setAttribute("realFeel", feelsLikeCelcius);
			request.setAttribute("lowTemp", minTemperatureCelsius);
			request.setAttribute("highTemp", maxTemperatureCelsius);
			request.setAttribute("weatherCondition", weatherCondition); 
			request.setAttribute("humidity", humidity);    
			request.setAttribute("windSpeed", windSpeed);
			request.setAttribute("sunRiseTime", sunRiseTime);
			request.setAttribute("sunSetTime", sunSetTime);
			request.setAttribute("iconUrl", iconUrl);

			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	private String convertUnixTimestampToLocalTime(long timestamp, int timezoneOffset) {
	    Date date = new Date((timestamp + timezoneOffset) * 1000L);
	    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
	    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    return sdf.format(date).toUpperCase();
	}

	private String formatDate(long timestamp, int timezoneOffset) {
		Date date = new Date(timestamp + timezoneOffset * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM yyyy | 'Local Time:' hh:mm a", Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}

	private String capitalize(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
}
