<%@page import="java.util.List"%>
<%@include file="header.jsp"%>
			<div class="col-md-4 col-md-offset-4">
				<div class="panel panel-default" style="margin-top: 100px;">
					<div class="panel-heading">
						Add track to playlist
					</div>
					<div class="panel-body">
  					<%List<String> listOfPlaylists = (List<String>)request.getAttribute("list");
  					int trackId = ((Integer)request.getAttribute("id")).intValue();
					if(listOfPlaylists.size() > 0){ %>
						<form method="post">
							<div class="form-group">
							<select name="playlist" class="form-control">
								<%for (String playlistTitle : listOfPlaylists){%>
			  					<option value="<%=playlistTitle%>"><%=playlistTitle%></option>
								<%} %>
							</select>
							</div>
							<button class="btn btn-primary" type="submit" name="trackId" value="<%=trackId%>">Ok</button>
							<a class="btn btn-default" href="/explore.do">Cancel</a>
							
						</form> 
					<%} else { %>
						<div class="row" style="margin-top: 15px">
							<div class="col-md-6 col-md-offset-3">
								<a href="/${sessionScope.user.nick}/sets/add_playlist.do">You haven't any playlists, click to add new</a>				
							</div>
						</div>
					<%}%>
				</div>
			</div>
		</div>
	</body>
</html>