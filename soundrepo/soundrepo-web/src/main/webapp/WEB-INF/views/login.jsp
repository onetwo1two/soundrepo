<%@include file="header.jsp"%>
<div class="col-md-4 col-md-offset-4">
<div class="panel panel-default" style="margin-top: 100px;">
	<div class="panel-heading">
		Login
	</div>
	<%@include file="errors.jsp"%>
	<div class="panel-body">
		<form method="post">
			<div class="form-group">
				<label for="emailInput">Email address</label> <input type="email"
					class="form-control" name="email"
					placeholder="Enter email" value="admin@admin">
			</div>
			<div class="form-group">
				<label for="passwordInput">Password</label> <input type="password"
					class="form-control" id="passwordInput" name="password"
					placeholder="Enter password" value="admin">
			</div>
			<button type="submit" class="btn btn-primary">Login</button>
			<a href="register.do" class="btn btn-default">Register</a>
			<a href="reset_password.do" class="btn btn-default">Reset Password</a>
		</form>
	</div>
	</div>
	</div>
</body>
</html>