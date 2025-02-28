# Travel App

This project allows users to input a city name and a specific date to get the weather forecast for that city on the given date. The application uses the following APIs:

- GeoNames API: To retrieve the latitude and longitude of the city.
- WeatherBit API: To fetch the weather forecast for the city on the specified date.
- Pixabay API: To display an image of the city.

The project was built using HTML, SCSS, and JavaScript.

## Features

- City Search: Enter the name of a city.
- Date Selection: Enter a specific date for the weather forecast.
- Weather Forecast: Display the weather forecast for the selected city and date.
- City Image: Show an image of the city fetched from Pixabay.

## Technologies Used

- HTML: To structure the website.
- SCSS: To style the website with a clean and responsive design.
- JavaScript: To fetch data from APIs and handle dynamic content updates.
- APIs:
  - GeoNames API: For geolocation (latitude and longitude).
  - WeatherBit API: For weather forecast data.
  - Pixabay API: For fetching images of the city.

## Setup

### Prerequisites

no special prerequisites are needed, it's enough to have a modern browser to display the page content

## How to Run

1. Clone the repository:

`git clone https://github.com/your-username/travel-App.git`

2. Navigate into the project directory:

`cd travel-App`

Open the index.html file in your browser to view the project

### API Keys

You need to register for API keys to access the GeoNames, WeatherBit, and Pixabay APIs:

1. GeoNames API: Sign up for an API key and follow the instructions.
2. WeatherBit API: Create an account and get your API key.
3. Pixabay API: Get your free API key.

Once you have your API keys, replace the ones in the app.js and save_trip.js files with your keys.

## How it Works

1. User Input: The user enters the name of a city and a date in the provided input fields.
2. Geolocation: The app uses the GeoNames API to cget the latitude and longitude for the city.
3. Weather Forecast: With the retrieved coordinates, the app fetches the weather forecast for the specified date using the WeatherBit API.
4. City Image: The app uses the Pixabay API to fetch an image of the city and displays it on the page.
