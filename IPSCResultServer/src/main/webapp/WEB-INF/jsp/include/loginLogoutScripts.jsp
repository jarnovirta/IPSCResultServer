<script>
	function login() {
		window.location.href = "${baseUrl}login";
	}
	function logout() {
		document.getElementById("logoutForm").submit();
	}
</script>