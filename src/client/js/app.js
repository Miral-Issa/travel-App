import { save_button } from "./save_trip";
import { remove_button } from "./remove_trip";

const pixabyURL = "https://pixabay.com/api/?";
const pixabyAPIkey = "key=49094447-2f71e092d72ef2623f34ade69";

const button_save_trip = document.getElementById("save");
button_save_trip.addEventListener('click', () => {
    save_button().then(function(forcast){
        updateUI(forcast);
    }).then(function(){
        getImage(pixabyURL,pixabyAPIkey);
    });
});

const button_remove_trip = document.getElementById("remove");
button_remove_trip.addEventListener('click', remove_button);

const updateUI = (weatherForcast) => {
    console.log(weatherForcast);
    const tempMin = weatherForcast.app_min_temp;
    const tempMax = weatherForcast.app_max_temp;
    const description = weatherForcast.weather.description;

    const date =new Date(weatherForcast.datetime);
    const currentDate = new Date();
    console.log(currentDate);
    console.log(date);

    let Difference_In_Time = date.getTime() - currentDate.getTime();
    let Difference_In_Days = Math.round(Difference_In_Time / (1000 * 3600 * 24))+2;

    const city = document.getElementById("cityName").value;

    document.getElementById("daysAway").innerHTML = city + " is " +Difference_In_Days+" days away";
    document.getElementById("temp").innerHTML = "high= "+tempMax+" , low= "+tempMin;
    document.getElementById("desc").innerHTML = description;
}

async function getImage(baseURL, key){

    const city = document.getElementById("cityName").value;

    const APIURL = baseURL + key + "&q="+city;
    console.log(APIURL);


    const res = await fetch(APIURL);

    try {
        const imagesReturned = await res.json();
        console.log(imagesReturned.hits[0].largeImageURL);
        document.getElementById("tripImage").src=imagesReturned.hits[0].largeImageURL;
      }  catch(error) {
        console.log("error", error);
      }
}

export {getImage};