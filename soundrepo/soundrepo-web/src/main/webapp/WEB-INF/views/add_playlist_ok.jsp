<%@include file="header.jsp"%>
		<div class="col-md-4 col-md-offset-4">	
			<div class="panel panel-default"
					style="width: 350px; margin-top: 50px; text-align: left;">
				<div class="panel-heading">
					<h3 class="panel-title">Playlist ${playlist}</h3>
				</div>
				<div class="panel-body">
					<div class="alert alert-success">${user}, your playlist "${playlist}" were added successfully.
					You can fill your playlist from EXPLORE page or upload your own tracks </div>
					<a href="/explore.do" class="btn btn-primary">I want EXPLORE</a>
					<a href="/upload.do" class="btn btn-default">I want UPLOAD</a>
				</div>
			</div>
		</div>
	</body>
</html>