// Getting the geolocation of the user
const getLatLong = () => {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition((position) => {
            let lat = position.coords.latitude;
            let lon = position.coords.longitude;

            fetch(`https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${lat}&longitude=${lon}&localityLanguage=en`)
                .then(response => response.json())
                .then(data => {
                    let city = data.city;
                    document.getElementById("searchInput").value = city;
                    document.querySelector("form").submit();
                })
                .catch(error => console.error('Error fetching city name:', error));
        });
    }
}

const getLocationBtn = document.getElementById("getLocationBtn");
getLocationBtn.addEventListener('click', (event) => {
    event.preventDefault();
    getLatLong();
});

// Toggling between Celcius and Farenheit
const fahrenheitBtn = document.getElementById("fahrenheit-btn");
const celciusBtn = document.getElementById("celcius-btn");
let selectedUnit = 'celcius';

let temperatureCelsius = parseFloat(document.getElementById("temperature").innerText);
let temperatureFahrenheit = (temperatureCelsius * 9/5) + 32;

celciusBtn.addEventListener('click', () => {
    if (selectedUnit !== 'celcius') {
        selectedUnit = 'celcius';
        updateTemperatureDisplay();
    }
	celciusBtn.style.fontSize = "2rem";
	fahrenheitBtn.style.fontSize = "1.5rem";
});

fahrenheitBtn.addEventListener('click', () => {
    if (selectedUnit !== 'fahrenheit') {
        selectedUnit = 'fahrenheit';
        updateTemperatureDisplay();
    }
	celciusBtn.style.fontSize = "1.5rem";
	fahrenheitBtn.style.fontSize = "2rem";
});

function updateTemperatureDisplay() {
    const temperatureElement = document.getElementById("temperature");
    const realFeelElement = document.getElementById("real-feel");
	const highTemp = document.getElementById("highTemp");
	const lowTemp = document.getElementById("lowTemp");
    if (selectedUnit === 'celcius') {
        temperatureElement.innerText = temperatureCelsius.toFixed(2) + "°";
        realFeelElement.innerText = temperatureCelsius.toFixed(2) + "°";
		highTemp.innerText = temperatureCelsius.toFixed(1) + "°";
		lowTemp.innerText = temperatureCelsius.toFixed(1) + "°";
    } else {
        temperatureElement.innerText = temperatureFahrenheit.toFixed(2) + "°";
        realFeelElement.innerText = temperatureFahrenheit.toFixed(2) + "°";
        highTemp.innerText = temperatureFahrenheit.toFixed(1) + "°";
        lowTemp.innerText = temperatureFahrenheit.toFixed(1) + "°";
    }
}
celciusBtn.style.fontSize = "2rem";
updateTemperatureDisplay();
