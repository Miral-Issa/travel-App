import "./js/app.js";
import "./js/save_trip.js";

import "./styles/base.scss";
import "./styles/header.scss";
import "./styles/main.scss";
import "./styles/footer.scss";

import "./media/girl_in_a_boat.jpg";

import { save_button } from "./js/save_trip.js";
import { updateUI } from "./js/app.js";
import { getImage } from "./js/app.js";

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