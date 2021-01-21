<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="scripts/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body onkeydown="tutkiKey(event)">
<form id="tiedot">
<table>
	<thead>
		<tr>
			<th colspan="5" class="oikealle"><a href="listaa.jsp" id="takaisin">Takaisin listaukseen</a></th>
		</tr>
		<tr>
			<th>Tuotteen nimi</th>
			<th>Tuotteen hinta</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="text" name="nimi" id="nimi"></td>
			<td><input type="text" name="hinta" id="hinta"></td>
			<td><input type="button" name="nappi" id="tallenna" value="Lis‰‰" onclick="lisaaTiedot()"></td>
		</tr>
	</tbody>
</table>
</form>
<span id="ilmo"></span>
<script>

function tutkiKey(event){
	if(event.keyCode==13){//Enter
		lisaaTiedot();
	}		
}
document.getElementById("nimi").focus();//vied‰‰n kursori etunimi-kentt‰‰n sivun latauksen yhteydess‰

//funktio tietojen lis‰‰mist‰ varten. Kutsutaan backin POST-metodia ja v‰litet‰‰n kutsun mukana uudet tiedot json-stringin‰.
//POST /asiakkaat/
function lisaaTiedot(){	
	var nimi = document.getElementById("nimi").value;
	var hinta = document.getElementById("hinta").value;
	
	if(nimi.length<2||hinta.length<1){
		document.getElementById("ilmo").innerHTML = "Antamasi arvot eiv‰t kelpaa!"
		return;
	}
	var formJsonStr=formDataToJSON(document.getElementById("tiedot")); //muutetaan lomakkeen tiedot json-stringiksi
	//L‰het‰‰n uudet tiedot backendiin
	fetch("Beauties",{
	      method: 'POST',
	      body:formJsonStr
	    })
	.then( function (response) {
		return response.json()
	})
	.then( function (responseJson) {
		var vastaus = responseJson.response;		
		if(vastaus==0){
			document.getElementById("ilmo").innerHTML= "Asiakkaan lis‰‰minen ep‰onnistui";
        }else if(vastaus==1){	        	
        	document.getElementById("ilmo").innerHTML= "Asiakkaan lis‰‰minen onnistui";			      	
		}	
	});		
}
</script>
</body>
</html>