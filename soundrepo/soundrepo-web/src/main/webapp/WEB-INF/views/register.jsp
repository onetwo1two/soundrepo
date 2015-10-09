<%@include file="header.jsp"%>


<div class="col-md-4 col-md-offset-4">
<div class="panel panel-default"
	style="width: 350px; margin-top: 100px;">

	<div class="panel-heading">
		<h3 class="panel-title">Registration</h3>
	</div>

	<%@include file="errors.jsp"%>

		<div class="panel-body">
			<form method="post" role="form">
				<div class="form-group">
					<label for="emailInput">Email address</label> <input type="email"
					class="form-control" name="email"
					placeholder="Enter email">
				</div>
				<div class="form-group">
					<label for="nickInput">Nick</label> <input type="text"
					class="form-control" name="nick"
					placeholder="Enter your nick">
				</div>
				<button type="submit" class="btn btn-primary">Register</button>
				<a href="/explore.do" class="btn btn-default" role="button">Cancel</a>
			</form>

		</div>
	</div>
	</div>
</body>
</html>