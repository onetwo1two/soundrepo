<%@include file="header.jsp"%>

<div class="col-md-4 col-md-offset-4">
	<div class="panel panel-default"
		style="margin-top: 100px; text-align: left;">

		<div class="panel-heading">
			<h3 class="panel-title">Upload</h3>
		</div>

		<%@include file="errors.jsp"%>

		<div class="panel-body">
			<form method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label for="titleInput">
						Title
					</label>
					<input id="titleInput" type="text" name="title" placeholder="Title" class="form-control">
				</div>
				
				<div class="form-group">
					<label for="producerInput">
						Producer
					</label>
					<input id="producerInput" type="text" name="producer" placeholder="Producer" class="form-control">
				</div>
				
				<div class="form-group">
					<label for="mp3Input">
						Track
					</label>
					<input id="mp3Input" class="btn btn-default" type="file" name="mp3">
					<div class="row">
						<div class="col-md-8 col-md-offset-4">
							only mp3, max 15mb
						</div>
					</div>
				</div>
				
				<button type="submit" class="btn btn-primary">Submit</button>
				<a href="/explore.do" class="btn btn-default">Cancel</a>
			</form>
	
		</div>
	</div>
</div>
</body>
</html>