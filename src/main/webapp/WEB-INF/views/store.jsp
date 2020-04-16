<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
	<meta name="description" content="Admin, Dashboard, Bootstrap" />

	<link rel="shortcut icon" sizes="196x196" href="${pageContext.request.contextPath}/resources/assets/images/round1.PNG">
	<title>RegistrationAdminWeb</title>
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

       <li>
          <a href="agreements">
            <i class="menu-icon zmdi zmdi-file-text zmdi-hc-lg"></i>
            <span class="menu-text">Agreement</span>
          </a>
        </li>
        <li class="active">
          <a href="store">
            <i class="menu-icon zmdi zmdi-apps zmdi-hc-lg"></i>
            <span class="menu-text">Store Master</span>
          </a>
        </li>


       <!--  <li class="menu-separator"><hr></li> -->

      </ul><!-- .app-menu -->
    </div><!-- .menubar-scroll-inner -->
  </div><!-- .menubar-scroll -->
</aside>
<!--========== END app aside -->

<!-- navbar search -->
<div id="navbar-search" class="navbar-search collapse">
  <div class="navbar-search-inner">
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
							<div class="row">
								<div class="col-md-6">
									<h4 class="widget-title registration-title">Store Master</h4>
								</div>
								<div class="col-md-6">
									<div class="dataTables_wrapper">
										<div class="dataTables_paginate">
											<a href="#" class="btn btn-primary registration-plus" data-toggle="modal" data-target="#categoryModal">
											<i class="fa fa-plus m-r-sm"></i>Add New Store</a>
										</div>
									</div>
								</div>
							</div>
						</header>
						<!-- .widget-header -->
						<hr class="widget-separator">
					<div class="widget-body">
						<div class="table-responsive">
							<table id="default-datatable" class="table table-striped" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th>Store ID</th>
										<th>Store Name</th>
										<th>Etc</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th>Store ID</th>
										<th>Store Name</th>
										<th>Etc</th>
									</tr>
								</tfoot>
								<tbody id="make_tbody">
									<c:forEach var="dto" items="${list }">
										<tr>
											<td>${dto.store_id }</td><td>${dto.store_name }</td>
											<td>
												<a href="javascript:void(0)" class="btn btn-success" data-toggle="modal" data-target="#updateModal"
													data-store_id="${dto.store_id }"
													data-store_name="${dto.store_name }"
													data-store_password="${dto.store_password}">
												<i class="fa fa-pencil"></i></a>
												<a href="javascript:void(0)" class="btn btn-danger" data-toggle="modal" data-target="#deleteItemModal"
													data-store_id="${dto.store_id }"
													data-store_name="${dto.store_name }"
													data-store_password="${dto.store_password}">
												<i class="fa fa-trash"></i></a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- END column -->

		</div><!-- .row -->
	</section><!-- .app-content -->
</div><!-- .wrap -->

<!-- update Modal -->
<div id="updateModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">Update Store</h4>
			</div>
			<form action="updateStore" id="updateContactForm" method="post">
				<div class="modal-body">
					<div class="form-group">
						<input type="hidden" id="org_store_id" name="org_store_id">
					</div>
					<div class="form-group">
						<input type="text" id="update_store_id" name="update_store_id" class="form-control" placeholder="Store ID" maxlength="4">
					</div>
					<div class="form-group">
						<input type="text" id="update_store_name" name="update_store_name" class="form-control" placeholder="Store Name" maxlength="30">
					</div>
					<div class="form-group">
						<input type="text" id="update_store_password" name="update_store_password" class="form-control" placeholder="Store Password" maxlength="30">
					</div>
				</div><!-- .modal-body -->
				<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-success" data-dismiss="modal" onclick="submit()">Save</button>
				</div><!-- .modal-footer -->
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- delete item Modal -->
<div id="deleteItemModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">Delete Store</h4>
			</div>
			<form action="deleteStore" id="deleteContactForm" method="post">
				<div class="form-group">
					<input type="hidden" id="store_id" name="store_id">
				</div>
				<div class="modal-body">
					<h6 class="selectedStoreId"></h6>
					<h6 class="selectedStoreName"></h6>
					<br/>
					<h5>Do you really want to delete this Store ?</h5>
				</div><!-- .modal-body -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="submit()">Delete</button>
				</div><!-- .modal-footer -->
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- new category Modal -->
<div id="categoryModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">Create Store</h4>
			</div>
			<form action="insertStore" id="newCategoryForm" method="post">
				<div class="modal-body">
					<div class="form-group">
						<input type="text" id="insert_store_id" name="insert_store_id" class="form-control" placeholder="Store ID" maxlength="4">
					</div>
					<div class="form-group">
						<input type="text" id="insert_store_name" name="insert_store_name" class="form-control" placeholder="Store Name" maxlength="30">
					</div>
					<div class="form-group">
						<input type="text" id="insert_store_password" name="insert_store_password" class="form-control" placeholder="Store Password" maxlength="30">
					</div>
				</div><!-- .modal-body -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-success" data-dismiss="modal" onclick="submit()">Create</button>
				</div><!-- .modal-footer -->
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

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


	<!-- jQuery -->
	<script type="text/javascript">


	$(document).ready(function(){


		 $('#default-datatable').DataTable( {
	    		"lengthMenu": [[25], [25]]
	 	    } );

	$("#default-datatable_filter").css({"text-align":"left"})
   	$("#default-datatable_length").parent(".col-sm-6").hide();

	$("#default-datatable_filter").parent(".col-sm-6").addClass("col4_addClass");
	$("#default-datatable_filter").parent(".col-sm-6").removeClass("col-sm-6");


} );





		$('#updateModal').on('show.bs.modal', function (event) {
		  var button = $(event.relatedTarget) // Button that triggered the modal
		  var storeId = button.data('store_id') // Extract info from data-* attributes
		  var storeName = button.data('store_name')
		  var storePassword = button.data('store_password');
		  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
		  var modal = $(this)
		  modal.find('#org_store_id').val(storeId);
		  modal.find('#update_store_id').val(storeId);
		  modal.find('#update_store_name').val(storeName);
		  modal.find('#update_store_password').val(storePassword);
		})

		$('#deleteItemModal').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget) // Button that triggered the modal
			  var storeId = button.data('store_id') // Extract info from data-* attributes
			  var storeName = button.data('store_name')
			  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
			  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
			  var modal = $(this)
			  modal.find('#store_id').val(storeId)
			  modal.find('.selectedStoreId').text('Store ID : ' + storeId)
			  modal.find('.selectedStoreName').text('Store Name : ' + storeName)
			})





	</script>
</body>
</html>