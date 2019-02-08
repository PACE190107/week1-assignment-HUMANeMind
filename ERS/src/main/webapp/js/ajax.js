let button = document.getElementById("displayBtn").addEventListener("click", getInfo)

function getInfo(){
	var x = new XMLHttpRequest();	
	
	x.onreadystatechange = function(){
		if((x.readyState == 4) && (x.status == 200)){
			let rt = document.getElementById("responseTxt");
			let rh = document.getElementById("responseHdr");
			let hs = document.getElementById("responseSts");
			let hst = document.getElementById("responseStsTxt");
			let rx = document.getElementById("responseXml");
			rt.innerHTML = x.responseText;
			rh.innerHTML = x.getAllResponseHeaders();
			hs.innerHTML = x.status
			hst.innerHTML = x.statusText;
			rx.innerHTML = x.responseXML;
			
			console.log("Rt: " + x.responseText + "Rs: " + x.readyState + "S: " + x.status);	
		}
	};		
	
	x.open("get", "https://api.myjson.com/bins/ly8t0");
	x.send();

	
//	let j1 = [{name:"Inigo Montoya", age: 43}, {name:"Wesley", age: 25}];
//	console.log(j1);
//	
//	let j2 = JSON.stringify(j1);
//	console.log(j2);
//	
//	let j3 = JSON.parse(j2);	
//	console.log(j3);
}