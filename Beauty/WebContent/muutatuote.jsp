<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="scripts/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Tuotteen tietojen muutos</title>
</head>
<body onkeydown="tutkiKey(event)">
<form id="tiedot">
	<table class="table">
		<thead>
			<tr>
				<th colspan="5" class="oikealle"><a href="listaa.jsp" id="takaisin">Takaisin listaukseen</a></th>
			</tr>
			<tr>
				<th>Nimi</th>
				<th>Hinta</th>			
				<th>Hallinta</th>
			</tr>
		</thead>
			<tr>
				<td><input type="text" name="nimi" id="nimi"></td>
				<td><input type="text" name="hinta" id="hinta"></td>		
				<td><input type="button" name="nappi" value="Tallenna" id="tallenna" onclick="vieTiedot()"></td>
			</tr>
		<tbody>
		</tbody>
	</table>
	<input type="hidden" name="tuoteid" id="tuoteid">
</form>
<span id="ilmo"></span>
<script>

function tutkiKey(event){
	if(event.keyCode==13){//Enter
		vieTiedot();
	}		
}

document.getElementById("nimi").focus();//vied‰‰n kursori etunimi-kentt‰‰n sivun latauksen yhteydess‰
document.getElementById("tuoteid").value = requestURLParam("tuoteid");
console.log(document.getElementById("tuoteid").value);
//Haetaan muutettavan asiakkaan tiedot. Kutsutaan backin GET-metodia ja v‰litet‰‰n kutsun mukana muutettavan tiedon id
//GET /asiakkaat/haeyksi/id
var tuoteid =  requestURLParam("tuoteid"); //Funktio lˆytyy scripts/main.js 



//Funktio tietojen muuttamista varten. Kutsutaan backin PUT-metodia ja v‰litet‰‰n kutsun mukana muutetut tiedot json-stringin‰.
//PUT /asiakkaat/
function vieTiedot(){
	var id = tuoteid;
	console.log(id);
	var nimi = document.getElementById("nimi").value;
	var hinta = document.getElementById("hinta").value;	
	if(nimi.length<2||hinta.length<1){
		document.getElementById("ilmo").innerHTML = "Antamasi arvot eiv‰t kelpaa!"
		return;
	}
	var formJsonStr=formDataToJSON(document.getElementById("tiedot"));
	console.log(formJsonStr);
	
	fetch("Beauties" ,{
	      method: 'PUT',
	     body:formJsonStr
	    })
	.then( function (response) {
		return response.json();
	})
	.then( function (responseJson) {
		var vastaus = responseJson.response;		
		if(vastaus==0){
			document.getElementById("ilmo").innerHTML= "Tietojen p‰ivitys ep‰onnistui";
      }else if(vastaus==1){	        	
      	document.getElementById("ilmo").innerHTML= "Tietojen p‰ivitys onnistui";			      	
		}	
	});	
}

</script>
</body>
</html>