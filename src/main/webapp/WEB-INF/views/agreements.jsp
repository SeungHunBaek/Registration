<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% HttpSession s = request.getSession(false);
   String session_info = (String)s.getAttribute("login");
%>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
	<meta name="description" content="Admin, Dashboard, Bootstrap" />
	<title>RegistrationAdminWeb</title>
	<link rel="shortcut icon" sizes="196x196" href="${pageContext.request.contextPath}/resources/assets/images/round1.PNG">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/libs/bower/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/libs/bower/material-design-iconic-font/dist/css/material-design-iconic-font.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/core.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/app.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/libs/misc/datatables/datatables.min.css">
	<script src="${pageContext.request.contextPath}/resources/libs/bower/breakpoints.js/dist/breakpoints.min.js"></script>
	<!--Text Font  -->
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:400,500,600,700,800,900,300">
	<script>
		Breakpoints();
	</script>
</head>

<body class="menubar-left menubar-unfold menubar-light theme-primary menubar-in">
<!--============= start main area -->

<!-- APP NAVBAR ==========-->
<nav id="app-navbar" class="navbar navbar-inverse navbar-fixed-top danger">

  <!-- navbar header -->
  <div class="navbar-header">

    <img style="max-height:60px; margin-left:34px;" src="${pageContext.request.contextPath}/resources/assets/images/round1red.jpg" alt="">
  </div><!-- .navbar-header -->

  <div class="navbar-container container-fluid bg-danger">
    <div class="collapse navbar-collapse" id="app-navbar-collapse">
      	<ul class="nav navbar-toolbar navbar-toolbar-right navbar-right">
      	<li class="dropdown">
      	<a href="logout">
            <i class="fa fa-sign-out"></i>
        </a>
        </li>
      </ul>

    </div>
  </div><!-- navbar-container -->
</nav>
<!--========== END app navbar -->

<!-- APP ASIDE ==========-->
<aside id="menubar" class="menubar light">

  <div class="menubar-scroll">
    <div class="menubar-scroll-inner">
      <ul class="app-menu">

      <li class="active">
          <a href="agreements">
            <i class="menu-icon zmdi zmdi-file-text zmdi-hc-lg"></i>
            <span class="menu-text">Agreement</span>
          </a>
        </li>
        <%if(session_info.equals("admin")){ %>
        <li>
          <a href="store">
            <i class="menu-icon zmdi zmdi-apps zmdi-hc-lg"></i>
            <span class="menu-text">Store Master</span>
          </a>
        </li>
        <% } %>



       <!--  <li class="menu-separator"><hr></li> -->

      </ul><!-- .app-menu -->
    </div><!-- .menubar-scroll-inner -->
  </div><!-- .menubar-scroll -->
</aside>
<!--========== END app aside -->

<!-- navbar search -->
<div id="navbar-search" class="navbar-search collapse">
  <div class="navbar-search-inner">
    <form action="#">
      <span class="search-icon"><i class="fa fa-search"></i></span>
      <input class="search-field" type="search" placeholder="search${pageContext.request.contextPath}/resources."/>
    </form>
    <button type="button" class="search-close" data-toggle="collapse" data-target="#navbar-search" aria-expanded="false">
      <i class="fa fa-close"></i>
    </button>
  </div>
  <div class="navbar-search-backdrop" data-toggle="collapse" data-target="#navbar-search" aria-expanded="false"></div>
</div><!-- .navbar-search -->

<!-- APP MAIN ==========-->
<main id="app-main" class="app-main">
  <div class="wrap">
	<section class="app-content">
		<div class="row">
			<!-- DOM dataTable -->
			<div class="col-md-12">
				<div class="widget">
					<header class="widget-header">
						<h4 class="widget-title">Agreement List</h4>
					</header><!-- .widget-header -->
					<hr class="widget-separator">
					<div class="widget-body">
						<div class="table-responsive">
							<table id="default-datatable" class="table table-striped" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th>Date</th>
										<th>Store</th>
										<th>Name</th>
										<th>Telephone</th>
										<th>Etc</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th>Date</th>
										<th>Store</th>
										<th>Name</th>
										<th>Telephone</th>
										<th>Etc</th>
									</tr>
								</tfoot>

							</table>
						</div>
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- END column -->

		</div><!-- .row -->
	</section><!-- .app-content -->
</div><!-- .wrap -->
</main>
<!--========== END app main -->


	<!-- build:js ../assets/js/core.min.js -->
	<script src="${pageContext.request.contextPath}/resources/libs/bower/jquery/dist/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/resources/libs/bower/jQuery-Storage-API/jquery.storageapi.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/libs/bower/bootstrap-sass/assets/javascripts/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/resources/libs/bower/jquery-slimscroll/jquery.slimscroll.js"></script>
	<script src="${pageContext.request.contextPath}/resources/libs/bower/perfect-scrollbar/js/perfect-scrollbar.jquery.js"></script>

	<script src="${pageContext.request.contextPath}/resources/libs/misc/datatables/datatables.min.js"></script>
	<!-- endbuild -->

	<!-- build:js ../assets/js/app.min.js -->
	<script src="${pageContext.request.contextPath}/resources/assets/js/library.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/js/plugins.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/js/app.js"></script>


	<script type="text/javascript">

	$(document).ready(function(){

		$('.submenu').css({"display":"block"});

	    	 $('#default-datatable').DataTable( {
	    		"lengthMenu": [[25], [25]],
	 	        "processing": true,
	 	        "serverSide": true,
	 	        "ajax": "agreements/data.json",
	 	        "ordering": false
	 	    } );

	    	$("#default-datatable_filter").css({"text-align":"left"});
	    	$("#default-datatable_length").parent(".col-sm-6").hide();

			$("#default-datatable_filter").parent(".col-sm-6").addClass("col4_addClass");
			$("#default-datatable_filter").parent(".col-sm-6").removeClass("col-sm-6");

    		var storeListTemplate = $('#store_list_template').html();
    		$("#default-datatable_filter").parent().after(storeListTemplate);

	} );

	function filterColumn ( i ) {
	    $('#default-datatable').DataTable().column( i ).search(
	        $('#col'+i+'_filter').val()
	    ).draw();
	}
	</script>

	<script id="store_list_template" type="text/html">
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<%if(session_info.equals("admin")||session_info.equals("9999")){ %>
		<div class='col4_addClass'><div id='store_list' class='dataTables_length'>
			<label>Store:
			<select class="column_filter form-control input-sm" id="col1_filter" onchange="filterColumn(1)">
					<option value="">All</option>
					<option value="Undefined">Undefined</option>
					<c:forEach var="dto" items="${list}">
						<option value="${dto.store_name}">${dto.store_name}</option>
					</c:forEach>
			</select></label>
		</div></div>
		<% }else{ %>
		<% }%>
	</script>

</body>
</html>