<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="ua.devfactory.sounds.dto.Track"%>

<%@include file="header.jsp"%>
	
	<div class="col-md-8 col-md-offset-2">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">${nick}</h3>
			</div>
			<div class="panel-body">
				<ul class="nav nav-tabs">
					<li class="hard-print active"><a href="/${nick}/sets.do">Playlists</a></li>
					<li><a class="hard-print" href="/${nick}/tracks.do">All ${nick}'s tracks</a></li>
				</ul>
		<%Map<String, List<Track>> usersPlaylists = (Map<String, List<Track>>)request.getAttribute("usersPlaylists"); 
		if (usersPlaylists != null && usersPlaylists.size() > 0) {
		Set<String> keys = usersPlaylists.keySet();
		%>
	
		<ul class="play-btn">
			<%
			for(String playlist : keys){
				
			%>
			
			<li class="playlist">
				<div class="row">
					<div class="col-md-12">					
						<button class="round-button js-playlist glyphicon glyphicon-play"></button> <span class="hard-print"><%=playlist%></span>
					</div>
				</div>
					<ul class="play-btn">
						<%for (Track track : usersPlaylists.get(playlist)){ %>
						<li class="track">
							<div class="row">
								<div class="col-md-11"><button class="btn btn-default track-block btn-block play-button"><%=track.getProducer()%>-<%= track.getTitle() %></button></div>
							</div>
						</li>
						<%} %>
					</ul>
			</li>
			
			
			<% }%>
		</ul>
		<%
				} else {
		%>
				<div class="row" style="margin-top: 15px">
					<div class="col-md-6 col-md-offset-3">
						<a href="/${nick}/sets/add_playlist.do">You haven't any playlists, click to add new</a>				
					</div>
				</div>
		<%}%>
				</div>
			</div>
		</div>
		<div align="center">
			<audio id="player" style="display:none" controls>
	 	 		<source id="sourceMp3" src="" type="audio/mpeg">
			</audio>
		</div>
		
		<script src="/js/myjs.js"></script>
	</body>
</html>