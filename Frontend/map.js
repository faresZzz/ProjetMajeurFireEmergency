var map = L.map('map').setView([45.762014, 4.849434], 12);
L.tileLayer('	https://cartodb-basemaps-{s}.global.ssl.fastly.net/light_all/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);

var Facility = L.layerGroup().addTo(map);
var Fire = L.layerGroup().addTo(map);
var Truck = L.layerGroup().addTo(map);

var B_Gasoline = L.layerGroup().addTo(map);
var C_Flammable_Gases = L.layerGroup().addTo(map);
var A = L.layerGroup().addTo(map);
var B_Alcohol = L.layerGroup().addTo(map);
var B_Plastics = L.layerGroup().addTo(map);
var D_Metals = L.layerGroup().addTo(map);
var E_Electrics = L.layerGroup().addTo(map);

var FireIcon = L.icon({ iconUrl: 'Fire.png', iconSize: [35, 45] });
var FacilityIcon = L.icon({ iconUrl: 'Facility.png', iconSize: [50, 50] });
var TruckIcon = L.icon({ iconUrl: 'teuck.png', iconSize: [35, 25] });



//----------------------------------------------------------------
// CheckBox management
var facilityCheckbox = document.querySelector('input[value="facility"]');
var fireCheckbox = document.querySelector('input[value="fire"]');
var truckCheckbox = document.querySelector('input[value="truck"]');
var b_gasoline = document.querySelector('input[value="b_gasoline"]');
var c_flammable_gases = document.querySelector('input[value="c_flammable_gases"]')
var a = document.querySelector('input[value="a"]')
var b_alcohol = document.querySelector('input[value="b_alcohol"]');
var b_plastics = document.querySelector('input[value="b_plastics"]');
var d_metals = document.querySelector('input[value="d_metals"]');
var e_electrics = document.querySelector('input[value="e_electrics"]');
var range = document.querySelector('input[value="range"]');
var intensity = document.querySelector('input[value="intensity"]');

var checkeboxList = [[facilityCheckbox,Facility],[truckCheckbox,Truck],[fireCheckbox,Fire],[b_gasoline, B_Gasoline],
    [c_flammable_gases, C_Flammable_Gases],[a, A],[b_alcohol, B_Alcohol],[b_plastics, B_Plastics],[d_metals, D_Metals],[e_electrics, E_Electrics]];



// for (let index = 0; index < fireTypeArray.length; index++) {
//     const element = fireTypeArray[index];
//     const associatedElement = associatedFireTypeArray[index];
//     console.log(element);
//     element.onchange = function () {
//         console.log("changement");
//         if (element.checked) {
//             map.addLayer(associatedElement);
//         }
//         else {
//             map.removeLayer(associatedElement);
//         }
//     }
// }

checkeboxList.forEach(checkbox => {
    checkbox[0].onchange = function () {
    if (checkbox[0].checked) {
        map.addLayer(checkbox[1]);
    }
    else {
        map.removeLayer(checkbox[1]);
    }
}
});
//----------------------------------------------------------------


function getFire() {
    const url = 'http://vps.cpe-sn.fr:8081/fire'
    var fireType = {
        'B_Gasoline': B_Gasoline,
        'C_Flammable_Gases': C_Flammable_Gases,
        'A': A,
        'B_Alcohol': B_Alcohol,
        "B_Plastics": B_Plastics,
        'D_Metals': D_Metals,
        'E_Electrics': E_Electrics,
    }
    //const element = document.querySelector('#post-request .article-id');
    // const requestOptions = {
    // method: 'GET',
    // headers: { 'Content-Type': 'application/json' },
    // body: JSON.stringify({ title: 'Fetch POST Request Example' })
    // };
    fetch(url)
        .then(response => response.json())
        .then(data => {
            data.forEach(element => {
                console .log(fireType[element.type])
                
                L.marker([element.lat, element.lon], { icon: FireIcon }).addTo(fireType[element.type]).addTo(Fire).bindPopup("<h2> Feu n°" + element.id + "</h2>" + "<ul>" +
                    "<li> Type : " + element.type + "</li>" + "<li> Intensity : " + element.intensity + "</li>" +
                    "<li> Range : " + element.range + "</li>" + "</ul>");
            }
            )


        })

}
function getFacility() {
    const url = 'http://vps.cpe-sn.fr:8081/facility'

    const element = document.querySelector('#post-request .article-id');
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: 'Fetch POST Request Example' })
    };
    fetch(url)
        .then(response => response.json())
        .then(data => {
            data.forEach(element => {

                L.marker([element.lat, element.lon], { icon: FacilityIcon }).addTo(Facility).bindPopup("<h2> Caserne n°" + element.id + "</h2>" + "<ul>" +
                    "<li> Name : " + element.name + "</li>" +
                    "<li> Max Vehicle Space : " + element.maxVehicleSpace + "</li>" +
                    "<li> PeopleCapacity : " + element.peopleCapacity + "</li>" + "</ul>");
            }
            )


        });

}
function getTruck() {
    const url = 'http://vps.cpe-sn.fr:8081/vehicle'

    const element = document.querySelector('#post-request .article-id');
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: 'Fetch POST Request Example' })
    };
    fetch(url)
        .then(response => response.json())
        .then(data => {
            data.forEach(element => {

                L.marker([element.lat, element.lon], { icon: TruckIcon }).addTo(Truck).bindPopup("<h2> Camion n°" + element.id + "</h2>" + "<ul>" +
                    "<li> Liquid Type : " + element.liquidType + "</li>" + "<li> Liquid Quantity : " + element.liquidQuantity + "</li>" +
                    "<li> Fuel : " + element.fuel + "</li>" + "<li> CrewMember : " + element.crewMember + "</li>" +
                    "<li> facilityRefID : " + element.facilityRefID + "</li>" +
                    "</ul>");
            }
            )


        })
}

function main() {
    L.marker.remove;
    getFacility();
    getFire();
    getTruck();
    console.log('Done')
    setTimeout(main, 10000); // try again in 10 seconds
}

main();
