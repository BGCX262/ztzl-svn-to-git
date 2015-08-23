<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	if (request.getSession().getAttribute("username") == null)
		response.sendRedirect("login.jsp");
%>
<html>
	<head>
		<title>中天公司质量管理系统</title>
		<link rel="stylesheet" type="text/css"
			href="ext/resources/css/ext-all.css" />

		<!-- GC -->
		<!-- LIBS -->
		<script type="text/javascript" src="ext/adapter/ext/ext-base.js"></script>
		<!-- ENDLIBS -->

		<script type="text/javascript" src="ext/ext-all.js"></script>
		<script type="text/javascript"
			src="ext/sources/locale/ext-lang-zh_CN.js"></script>

		<script type="text/javascript" src="js/mootools.js"></script>
		<script type="text/javascript" src="js/exp2excel.js"></script>
		<!--<script language="javascript" src="../grid/PropsGrid.js"></script>-->
		<style type="text/css">
.settings {
	background-image: url(./ext/examples/shared/icons/fam/folder_wrench.png)
		;
}

.nav {
	background-image: url(./ext/examples/shared/icons/fam/folder_go.png);
}

.add {
	background-image: url(./img/add.gif) !important;
}

.delete {
	background-image: url(./img/delete.png) !important;
}

.edit {
	background-image: url(./img/edit.png) !important;
}

.save {
	background-image: url(./img/save.png) !important;
}

.find {
	background-image: url(./img/find.png) !important;
}

.filter {
	background-image: url(./img/filter.png) !important;
}

.xls {
	background-image: url(./img/excel.png) !important;
}

</style>

		<script type="text/javascript" src="index.js">    
	</script>
	</head>
	<body>
		<!-- <script type="text/javascript" src="./ext/examples/shared/examples.js"></script>-->
		<!-- EXAMPLES -->

		<div id="north">
			<center>
				<table 　width="100%" height="100">
					<tr>
					<td></td>
						<td width="1024" background="img/logo.jpg" valign="bottom" align="left">
							当前用户: ${sessionScope.username }    &nbsp;&nbsp;&nbsp; ${sessionScope.userarea }
							<a href='logout.jsp'>退出登录</a>
						</td>
						<td></td>
					</tr>
				</table>

			</center>

		</div>

		<div id="center1">

		</div>
		
		<div id="south" align="center">
			<p>
				中天公司产品质量管理系统－版权所有 当前用户: <s:property value="#session.username" />
			</p>
		</div>

	</body>
</html>
