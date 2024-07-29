<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Java Weather App</title>
    <link rel="stylesheet" href="styles.css"> 
</head>
<body>
    <header>
        <div>Weather Dashboard</div>
    </header>
    
    <main>
        <section class="container">
            <form action="MyBackend" method="post">
                <div class="search-bar">
                    <input type="text" id="searchInput" placeholder="Search for city..." name="city" value=""/>
                    <button>
                        <img width="30" src="https://img.icons8.com/ios/50/search--v3.png" alt="search"/>
                    </button>
                    <button type="button" id="getLocationBtn">
                        <img width="30" src="https://img.icons8.com/ios-filled/50/marker.png" alt="marker"/>
                    </button>
                </div>
            </form>
            <div class="unit-selector">
                <button id="celcius-btn">°C</button>
                <p>|</p>
                <button id="fahrenheit-btn">°F</button>
            </div>
        </section>
        
        <section class="container">
            <div>
                <div class="timeDetails">
                    <p><%= request.getAttribute("date") != null ? request.getAttribute("date") : "Friday, 12 Jul 2024 | Local Time: 04:30 PM" %></p>
                </div>
                <div class="locationDetails">
                    <p class="location"><%= request.getAttribute("city") != null ? request.getAttribute("city") : "Gurgaon, IN" %></p>
                </div>
            </div>
        </section>
    
        <section class="container">
            <div>
                <div class="currentWeather">
                    <p><%= request.getAttribute("weatherCondition") != null ? request.getAttribute("weatherCondition") : "Haze" %></p>
                </div>
            
                <div class="tempDetails">
                	<img src="<%= request.getAttribute("iconUrl") != null ? request.getAttribute("iconUrl") : "https://openweathermap.org/img/wn/50d@2x.png" %>" alt="weather-icon"/>
                    <p class="temp-number" id="temperature"><%= request.getAttribute("temperature") != null ? request.getAttribute("temperature") + "°" : "33°" %></p>
                    
                    <div class="subTempDetails">
                        <div class="details">
                            Real Feel: 
                            <span id="real-feel"><%= request.getAttribute("realFeel") != null ? request.getAttribute("realFeel") + "°" : "38°" %></span>
                        </div>
                    
                        <div class="details">
                            Humidity: 
                            <span><%= request.getAttribute("humidity") != null ? request.getAttribute("humidity") + "%" : "55%" %></span>
                        </div>
                    
                        <div class="details">
                            Wind: 
                            <span><%= request.getAttribute("windSpeed") != null ? request.getAttribute("windSpeed") + " km/h" : "3 km/h" %></span>
                        </div>
                    </div>
                </div>
            
                <div class="sunDetails">
                    <p class="sunPara">Rise: <span class="sunSpan"><%= request.getAttribute("sunRiseTime") != null ? request.getAttribute("sunRiseTime") : "05:32 AM" %></span></p>
                    <p>|</p>
                    <p class="sunPara">Set: <span class="sunSpan"><%= request.getAttribute("sunSetTime") != null ? request.getAttribute("sunSetTime") : "07:21 PM" %></span></p>
                    <p>|</p>
                    <p class="sunPara">High: <span class="sunSpan" id="highTemp"><%= request.getAttribute("highTemp") != null ? request.getAttribute("highTemp") + "°" : "33°" %></span></p>
                    <p>|</p>
                    <p class="sunPara">Low: <span class="sunSpan" id="lowTemp"><%= request.getAttribute("lowTemp") != null ? request.getAttribute("lowTemp") + "°" : "33°" %></span></p>
                </div>
            </div>
        </section>
    </main>
    <script src="./script.js"></script>
</body>
</html>
