<%@page import="java.util.List"%>
<%@page import="ua.devfactory.sounds.dto.Track"%>
<%@include file="header.jsp"%>
	
	<div class="col-md-8 col-md-offset-2">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">${nick}</h3>
			</div>
			<div class="panel-body">
				<ul class="nav nav-tabs">
					<li><a class="hard-print" href="/${nick}/sets.do">Playlists</a></li>
					<li class="hard-print active"><a href="/${nick}/tracks.do">All ${nick}'s tracks</a></li>
				</ul>
		<%List<Track> usersTracks = (List<Track>)request.getAttribute("usersTracks"); 
		if (usersTracks != null && usersTracks.size() > 0) {
		%>
				<div class="track-list">
					<ul class="play-btn">
			<%
			for(Track track : usersTracks){
			%>
						<li class="track">
							<div class="row">
								<div class="col-md-11"><button class="btn btn-default track-block btn-block play-button"><%=track.getProducer()%>-<%= track.getTitle() %></button></div>
							</div>
						</li>
						<%} %>
					</ul>
				</div>
			
		<%
				} else {
		%>
				<div class="row" style="margin-top: 15px">
					<div class="col-md-6 col-md-offset-3">
						<a href="/upload.do">You haven't any tracks, click to upload</a>				
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