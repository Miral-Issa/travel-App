const geonameURL = "http://api.geonames.org/searchJSON?formatted=true&maxRows=10&lang=es&username=MiralIssa&style=full&q=";
//weather api
const apiKey = "fd06286be25635d8593384e4fc66bd97";
const baseURL = "http://api.openweathermap.org/data/2.5/forecast?";

const weatherbitAPIkey = "537f3b2c8c0e44768f073f7ffe9fe3d2";
const weatherbitBaseURL = "https://api.weatherbit.io/v2.0/forecast/daily?lang=en&";


// Import required dependencies
const express = require('express');
const bodyParser = require('body-parser');
const axios = require('axios');
const cheerio = require('cheerio');
const cors = require('cors');

// Initialize the Express application
const app = express();

// Apply middleware
app.use(cors()); // Enable Cross-Origin Resource Sharing
app.use(bodyParser.json()); // Parse JSON request bodies

// Default route
app.get('/', (req, res) => {
    res.send("This is the server API page. You may access its services via the client app.");
});

// Start the server
app.listen(8000, () => {
    console.log('Server running on port 8000');
});

//get city name
app.post('/save_trip', async(req, res) =>
{
    console.log("got a POST request")
    const dataGot = req.body;

    //console.log("a trip to: " + dataGot.city);
    const position = await getLatlng(dataGot.city);

    //const forcast = await getWeatherData(baseURL, apiKey, position, dataGot.date);

    const forcast = await weatherbitAPIcall(weatherbitBaseURL, weatherbitAPIkey, position, dataGot.date);

    res.send(forcast);
    //return res.json(response.data);
})

const getLatlng = async(city) =>
{
    const response = await axios.get(geonameURL+city);
    const position ={"lat":response.data.geonames[0].lat, "long":response.data.geonames[0].lng}
    return position;
}

//practised on an API I used before
const getWeatherData = async (baseURL, apiKey, position, date) => {
    const weatherURL = baseURL + "lat=" +position.lat+"&lon="+position.long+"&appid="+apiKey;

    const res = await fetch(weatherURL);
    try {
        const data = await res.json();

        for(let i = 0; i < data.list.length; i++){
            const date1 = data.list[i].dt_txt;
            if(date1.includes(date))
            {
                //console.log(date1);
                return data.list[i];
            }
        }

        return data.list[39]; //if the date is not in the 30 day window return the last entry (the furthest from today)
      }  catch(error) {
        console.log("error", error);
      }
}

async function weatherbitAPIcall (baseURL, key, position, date){

    console.log(position);

    const wantedDate =new Date(date);
    const currentDate = new Date();

    let Difference_In_Time = wantedDate.getTime() - currentDate.getTime();
    let Difference_In_Days = Math.round(Difference_In_Time / (1000 * 3600 * 24)) +2;

    const APIURL = baseURL + "lat=" +position.lat+"&lon="+position.long+"&days="+Difference_In_Days+"&key="+key;
    console.log(APIURL);


    const res = await fetch(APIURL);

    try {
        const forcast = await res.json();
        return forcast.data[Difference_In_Days-1];
      }  catch(error) {
        console.log("error", error);
      }
}
