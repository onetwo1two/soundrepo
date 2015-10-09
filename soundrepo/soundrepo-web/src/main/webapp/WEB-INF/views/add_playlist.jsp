<%@include file="header.jsp"%>
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default" style="margin-top: 100px;">
				<div class="panel-heading">
				Add playlist
				</div>
				<%@include file="errors.jsp"%>
				<div class="panel-body">
					<form method="POST">
						<div class="form-group">
							<label>Playlist title</label>
							<input type="text" name="playlist_title" class="form-control" placeholder="Enter title">
						</div>	
						<button type="submit" class="btn btn-primary">Submit</button>
						<a href="/explore.do" class="btn btn-default">Cancel</a>
					</form>
				</div>	
			</div>			
		</div>					
	</body>
</html>