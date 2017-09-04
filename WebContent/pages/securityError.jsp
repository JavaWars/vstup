<%@ include file="/pages/jspf/directive/page.jspf"%>
<html>

<c:set var="title" value="SeccurityError" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>


<script src="lib/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {

				var images = [ "res/security.jpg", "res/access.jpg",
						"res/denied.jpg" ];
				var currentImage = 0;
				var timerId = setInterval(func, 2000);
				show();
				$("#back").click(function() {
					currentImage--;
					show();
				})

				$("#forward").click(function() {
					currentImage++;
					show();
				})

				function func() {
					currentImage++;
					show();
				}
				function show() {
					if (currentImage < 0)
						currentImage = 2;
					if (currentImage >= 3)
						currentImage = 0;

					$("#img").fadeToggle(200);//200ms slow=600ms
					setTimeout(function() {
						$("#img").attr("src", images[currentImage]);
					}, 200);
					$("#img").fadeToggle(200);

				}
			})
</script>

<body>
	<div class="jumbotron slider row">
		<div class="col-md-4 col-md-offset-4">
			<img id="img" src="" height="400" width="600" /> <br> <a
				href="/vstup/home">back to home page</a>
		</div>
	</div>
</body>
</html>