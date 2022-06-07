const CARTYPE = {
    "CAR" : {
        "liquidQuantity" : { 
            "min" : 1,
            "max" : 10
        },

        "fuel" : {
            "min" : 1,
            "max" : 50,
        },

        "crewMember" : {
            "min" : 1,
            "max" : 2
        }
    },

    "FIRE_ENGINE" : {
        "liquidQuantity" : { 
            "min" : 1,
            "max" : 50
        },

        "fuel" : {
            "min" : 1,
            "max" : 60,
        },

        "crewMember" : {
            "min" : 1,
            "max" : 4
        }
    }, 

    "PUMPER_TRUCK" : {
        "liquidQuantity" : { 
            "min" : 1,
            "max" : 1000
        },

        "fuel" : {
            "min" : 1,
            "max" : 500
        },

        "crewMember" : {
            "min" : 1,
            "max" : 6
        }
    }, 

    "WATER_TENDERS" : {
        "liquidQuantity" : { 
            "min" : 1,
            "max" : 1000
        },

        "fuel" : {
            "min" : 1,
            "max" : 500
        },

        "crewMember" : {
            "min" : 1,
            "max" : 3
        }
    }, 

    "TURNTABLE_LADDER_TRUCK" : {
        "liquidQuantity" : { 
            "min" : 1,
            "max" : 1000
        },

        "fuel" : {
            "min" : 1,
            "max" : 500
        },

        "crewMember" : {
            "min" : 1,
            "max" : 6
        }
    },

    "TRUCK" : {
        "liquidQuantity" : { 
            "min" : 1,
            "max" : 2000
        },

        "fuel" : {
            "min" : 1,
            "max" : 500
        },

        "crewMember" : {
            "min" : 1,
            "max" : 8
        }
    }
}

function filter()
{
    let carType = document.getElementById("Type").value;
    document.getElementById("liquidQuantityRange").setAttribute("max", CARTYPE[carType].liquidQuantity.max)
    document.getElementById("fuelRange").setAttribute("max", CARTYPE[carType].fuel.max)
    document.getElementById("crewMemberRange").setAttribute("max", CARTYPE[carType].crewMember.max)

}

function sliderValue()
{
    let sliderFuel = document.getElementById("fuelRange");
    let outputFuel = document.getElementById("outputFuelRange");
    outputFuel.innerHTML = sliderFuel.value; // Display the default slider value

    let sliderLiquid = document.getElementById("liquidQuantityRange");
    let outputLiquid = document.getElementById("outputLiquidQuantityRange");
    outputLiquid.innerHTML = sliderLiquid.value; // Display the default slider value
}

document.onreadystatechange = function(){
    filter()
    sliderValue()
}

function createTruk(){
    // create a json from form

    const form = document.getElementById('addTrukForm')
    console.log(form)
    var Truk = new Object();
    Truk.id = 0;
    

    Truk.liquidType = form.elements[0].value;
    Truk.type = form.elements[1].value;
    
    Truk.liquidQuantity= parseInt(form.elements[3].value);
    Truk.fuel = parseInt(form.elements[4].value);
    Truk.crewMember= parseInt(form.elements[5].value);
    Truk.facilityRefID= parseInt(form.elements[6].value);

    var jsonString = JSON.stringify(Truk); //all the card infos
    console.log(jsonString)

    fetch("http://vps.cpe-sn.fr:8081/facility/" + Truk.facilityRefID )
        .then(response => response.json())
            .then (data => {
                    Truk.lon = data.lon;
                    Truk.lat = data.lat;
                    console.log(Truk);

             });
             


    // Fetch, POST, UrL/Card, headers : Application/json

    const element = jsonString; //what we want to post
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: jsonString
    };
    /*
    fetch("http://vps.cpe-sn.fr:8081/vehicle/0eb29fc1-d666-4dd6-9a6e-933f29f87689", requestOptions)
        .then(response => response.json())
            .then (response => callback(response))
            .catch(error => err_callback(error));
        */
}

function err_callback(error)
{
    alert(error);
}
function callback(reponse)
{
    console.log(reponse)
}