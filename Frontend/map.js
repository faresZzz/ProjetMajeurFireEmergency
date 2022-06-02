var map = L.map('map').setView([45.762014, 4.849434], 12);
    L.tileLayer('	https://cartodb-basemaps-{s}.global.ssl.fastly.net/light_all/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);
   
    var Facility = L.layerGroup().addTo(map);
    var Fire =L.layerGroup().addTo(map);
    var Trunck =L.layerGroup().addTo(map);

    var FireIcon = L.icon({iconUrl: 'Fire.png',iconSize: [35, 45] });
    var FacilityIcon = L.icon({iconUrl: 'Facility.png',iconSize: [50, 50] });
    var TrunckIcon = L.icon({iconUrl: 'teuck.png',iconSize: [30, 30] });


    var facilityCheckbox = document.querySelector('input[value="facility"]');

	facilityCheckbox.onchange = function() {
		if(facilityCheckbox.checked) {
						map.addLayer(Facility);
	    
	  				  } 
	 	else			  {
						map.removeLayer(Facility);
	    
	  				  }
	};

	var fireCheckbox = document.querySelector('input[value="fire"]');

	fireCheckbox.onchange = function() {
	      if(fireCheckbox.checked) {
	                    			map.addLayer(Fire);
	    
	  				   } 
	     else {
					       map.removeLayer(Fire);
		    
	  				  }
	};

    var trunckCheckbox = document.querySelector('input[value="trunck"]');

	trunckCheckbox.onchange = function() {
	      if(trunckCheckbox.checked) {
	                    			map.addLayer(Trunck);
	    
	  				   } 
	     else {
					       map.removeLayer(Trunck);
		    
	  				  }
	};


function getFire() {
    const url = 'http://vps.cpe-sn.fr:8081/fire'

    const element = document.querySelector('#post-request .article-id');
    // const requestOptions = {
    // method: 'GET',
    // headers: { 'Content-Type': 'application/json' },
    // body: JSON.stringify({ title: 'Fetch POST Request Example' })
    // };
    fetch(url)
        .then(response => response.json())
        .then(data =>  {
            data.forEach(element => {

                L.marker([element.lat,element.lon],{icon: FireIcon }).addTo(Fire).bindPopup("<h2> Feu n°"+element.id + "</h2>"+ "<ul>"+
                "<li> Type : "+ element.type + "</li>" +   "<li> Intensity : "+ element.intensity + "</li>" + 
                "<li> Range : "+ element.range + "</li>" + "</ul>");}
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
        .then(data =>  { 
            data.forEach(element => {

                L.marker([element.lat,element.lon],{icon: FacilityIcon }).addTo(Facility).bindPopup( "<h2> Caserne n°"+element.id + "</h2>"+ "<ul>"+
                "<li> Name : "+ element.name + "</li>" + 
                "<li> Max Vehicle Space : "+ element.maxVehicleSpace + "</li>" + 
                "<li> PeopleCapacity : "+ element.peopleCapacity + "</li>" + "</ul>");}
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
        .then(data =>  {
            data.forEach(element => {

                L.marker([element.lat,element.lon],{icon: TrunckIcon }).addTo(Trunck).bindPopup("<h2> Camion n°"+element.id + "</h2>"+ "<ul>"+
                "<li> Liquid Type : "+ element.liquidType + "</li>" +   "<li> Liquid Quantity : "+ element.liquidQuantity + "</li>" + 
                "<li> Fuel : "+ element.fuel + "</li>" + "<li> CrewMember : "+ element.crewMember + "</li>" +
                "<li> facilityRefID : "+ element.facilityRefID + "</li>" +
                "</ul>");} 
                )
            

     })}

function main () { 
    L.marker.remove;
    getFacility();
    getFire();
    getTruck();
    console.log('Done')
    setTimeout(main, 10000); // try again in 10 seconds
  }

main();
        