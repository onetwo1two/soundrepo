<%@include file="header.jsp"%>
<%@page import="ua.devfactory.sounds.dto.Track"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
	
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Explore</h3>
					</div>
					<div class="panel-body">
						<%	List<Track> explorersList = (List<Track>) request.getAttribute("tracks");
							if (explorersList != null){%>
								<ul class="play-btn">
								<%for (Track t : explorersList){%>
											<li class="track">
												<div class="row">
													<div class="col-md-9 col-md-offset-1">
														<button class="btn btn-default track-block btn-block play-button">
																<%=t.getProducer()%>-<%=t.getTitle()%>
														</button>														
													</div>
												</div>	
												<div class="row">
													<div class="col-md-1 col-md-offset-1">
														<a href="/<%=t.getUser().getNick()%>/sets.do"><%=t.getUser().getNick()%></a>
													</div>
													<div class="col-md-offset-8">
														<a href="/add_to_playlist.do?trackId=<%=t.getId()%>">
															<span class="glyphicon glyphicon-plus" title="Add to playlist"></span>
															add to playlist
														</a>
													</div>
												</div>																																													
											</li>
								<%}%>
								</ul>
		<%}else{%>
			too bad
		<%}%>
					</div>
				</div>
			</div>
			<div align="center">
				<audio id="player" style="display:none" controls>
 	 				<source id="sourceMp3" src="" type="audio/mpeg">
				</audio>
			</div>
		<script src="js/myjs.js"></script>
	</body>
</html>