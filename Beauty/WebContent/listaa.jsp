	<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Tuotteiden listaus</title>
</head>
<body onkeydown="tutkiKey(event)">
	<span id="ilmo"></span>
	<table id="listaus">
		<thead>	
			<tr>
				<th colspan="5" class="oikealle"><a id="uusiAsiakas" href="lisaaasiakas.jsp">Lis‰‰ uusi tuote</a></th>
			</tr>
			<tr>
				
				<th colspan="3" class="oikealle">Hakusana:</th>

				<th><input type="text" id="hakusana"></th>
				<th><input type="button" id="hae" value="Hae" onclick="haeTiedot()"></th>
				
			</tr>		
			<tr>
				<th>TuoteID</th>
				<th>Nimi</th>
				<th>Hinta</th>
					
				<th></th>			
			</tr>
		</thead>
		<tbody id="tbody">
		</tbody>
	</table>
<script>
	

document.getElementById("hakusana").focus();//vied‰‰n kursori hakusana-kentt‰‰n sivun latauksen yhteydess‰

function tutkiKey(event){
	if(event.keyCode==13){//Enter
		haeTiedot();
	}		
}
//Funktio tietojen hakemista varten
//GET   /asiakkaat/{hakusana}
function haeTiedot(){	
	
	document.getElementById("tbody").innerHTML = "";
	fetch("Beauties/" + document.getElementById("hakusana").value,{
	      method: 'GET'	      
	    })
	.then( function (response) {
		console.log("kurssit then 1");
		return response.json()
	})
	.then( function (responseJson) {
		var kosmetiikka = responseJson.kosmetiikka;	
		console.log(kosmetiikka);
		var htmlStr="";
		for(var i=0;i<kosmetiikka.length;i++){			
        	htmlStr+="<tr>";
        	htmlStr+="<td>"+kosmetiikka[i].tuoteID+"</td>";
        	htmlStr+="<td>"+kosmetiikka[i].nimi+"</td>";
        	htmlStr+="<td>"+kosmetiikka[i].hinta+"</td>";
        	htmlStr+="<td><a href='muutatuote.jsp?tuoteid="+kosmetiikka[i].tuoteID+"'>Muuta</a>&nbsp;";
        	htmlStr+="<td><span class='poista' onclick=poista("+kosmetiikka[i].tuoteID+",'"+kosmetiikka[i].nimi+"')>Poista</span></td>";
        	htmlStr+="</tr>"; 
        	
		}
		document.getElementById("tbody").innerHTML = htmlStr;	
			
		
				})		
			}	
function poista(tuoteID, nimi){
	if(confirm("Poista asiakas " + nimi +" "+ nimi +"?")){	
		fetch("Beauties/" + tuoteID,{
		      method: 'DELETE'	      
		    })
		.then( function (response) {
			return response.json()
		})
		.then( function (responseJson) {
			var vastaus = responseJson.response;		
			if(vastaus==0){
				document.getElementById("ilmo").innerHTML= "Asiakkaan poisto ep‰onnistui.";
	        }else if(vastaus==1){	        	
	        	alert("Asiakkaan " + nimi +" "+ tuoteID +" poisto onnistui.");
				haeTiedot();        	
			}	
		})
		
	}	
	
	
	
}


</script>
</body>
</html>
