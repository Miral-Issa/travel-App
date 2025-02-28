const serverURL = 'http://localhost:8000/save_trip'

// Function to send city to the server
const sendCity = async (url = '', data = {}) =>{
    const response = await fetch(url, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data), // body data type must match "Content-Type" header        
      });

      try {
        const newData = await response.json();
        return newData;
      }catch(error) {
        console.log("error", error);
        // appropriately handle the error
      }
}

async function save_button(){
    const city = document.getElementById("cityName").value;
    console.log("travel to "+ city);

    const date = document.getElementById("departingDate").value;
    console.log("date of the trip: "+ date);
    const weatherForcast = await sendCity(serverURL, {"city": city, "date": date});
    return weatherForcast;
}

export {save_button};