<%@include file="header.jsp"%>

<div class="col-md-4 col-md-offset-4">
	<div class="panel panel-default"
		style="margin-top: 100px; text-align: left;">

		<div class="panel-heading">
			<h3 class="panel-title">Reset Password</h3>
		</div>

		<%@include file="errors.jsp"%>

		<div class="panel-body">
			<form method="post" role="form">
				<div class="form-group">
					<label for="emailInput">Your Email address</label> <input type="email"
						class="form-control" id="emailInput" name="email"
						placeholder="Enter email">
				</div>
				<button type="submit" class="btn btn-primary">Reset Password</button>
				<a href="login.do" class="btn btn-default" role="button">Cancel</a>
			</form>

		</div>
	</div>
</div>
</html>