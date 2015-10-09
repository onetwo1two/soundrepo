function loadSong(title){
		    
		    var player=document.getElementById('player');
		    var sourceMp3=document.getElementById('player');
		    
		    sourceMp3.src="/resources/sounds/" + title + ".mp3";
		    
		   player.load(); //just start buffering (preload)
		   player.play(); //start playing
}

function play(event){
	var activeTrack = document.getElementsByClassName("active-track");		
	resetActiveTrack(activeTrack);
	var songTitle = event.target.innerText;
	//.split("-")[1]
	toggleVisibility();
	var track = event.target;
	track.className += " active-track";
	loadSong(songTitle);
}

function playPlaylist(event) {
	var activeTrack = document.getElementsByClassName("active-track");		
	resetActiveTrack(activeTrack);
	var tracks = event.target.parentNode.parentNode.parentNode.getElementsByClassName("track-block");
	tracks[0].className += " active-track";
	var songTitle = tracks[0].innerText;
	//.split("-")[1]
	toggleVisibility();
	loadSong(songTitle);	
}

function resetActiveTrack(activeTrack) {		
	for(var i=0; i<activeTrack.length; i++) {
		var pos = activeTrack[i].className.indexOf("active-track");		
		activeTrack[i].className = activeTrack[i].className.substr(0, pos-1);		
	}
}

function toggleVisibility(){
	  var player = document.getElementById("player");
	  if (player.style.display == 'none'){
		  player.style.display = 'block';
	  }
}

window.onload = function() {
	var playButtons = document.getElementsByClassName("play-button");
	for(var i=0; i<playButtons.length; i++) {
		//playButtons[i]['onclick'] = play;
		playButtons[i].addEventListener("click", play);
	};
	
	var player = document.getElementById("player");
	player.addEventListener("ended", function() {
		var activeTrack = document.getElementsByClassName("active-track");	
		var el = activeTrack[0].parentNode.parentNode.parentNode;
		el = el.nextSibling;
		while(el) {
			if(el.nodeName === "LI") {
				break;
			}
			el = el.nextSibling;
		}		
		resetActiveTrack(activeTrack);
		if(el != null) {
			el.getElementsByClassName("play-button")[0].className += " active-track";
			var songTitle = el.getElementsByClassName("play-button")[0].innerText;
			//.split("-")[1]
			loadSong(songTitle);
		}
	});	
	
	var playlists = document.getElementsByClassName("js-playlist");	
	for(var i = 0; i < playlists.length; i++) {
		playlists[i].addEventListener("click", playPlaylist);
	}	
};
