<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("basePath", basePath);
%>
<html>
<head>
	<title>知识点管理</title>
	<meta charset="UTF-8">
	<base href="<%=basePath%>">
	<link rel="SHORTCUT ICON" href="images/icon.ico">
	<link rel="BOOKMARK" href="images/icon.ico">
	<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/head.css">
	<link rel="stylesheet" type="text/css" href="css/list_main.css">
	<link rel="stylesheet" type="text/css" href="css/modal.css">
</head>
<body>
	<!--头部-->
	<jsp:include page="share/head.jsp"></jsp:include>

	<!--中间主体部分-->
	<div class="main">
		<!--学生-->
		<div class="list" id="student_list">
			<!--搜索框-->
			<div class="search form-inline">
				<form action="/teacher/knowledge/list" method="post" onsubmit="return searchKnowledge(this);">
					<input type="text" class="form-control" name="search" style="width: 300px;">
					<%--&nbsp;&nbsp;--%>
					<button class="btn btn-default" type="submit">搜索</button>
				</form>
			</div>
			<!--操作按钮-->
			<div class="operation_btn">
				<button class="btn btn-success btn-xs" onclick="toggleKnowledgeAdd(true);">添加知识点</button>
			</div>
			<table class="table table-hover">
				<thead>
				<tr>
					<th width="15%">知识点编号</th>
					<th width="30">知识点名称</th>
					<th width="40%" >知识点父编号</th>
					<th width="15%">操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${pageBean.records}" var="knowledge">
					<tr>
						<td>${knowledge.id}</td>
						<td>${knowledge.knowledgeName}</td>
						<td>${knowledge.pid}</td>
						<td>
							<button class="btn btn-default btn-xs" onclick="toggleKnowledgeEdit(true, this);">编辑</button>
							<button class="btn btn-danger btn-xs" onclick="deleteKnowledge(this);">删除</button>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<!--分页-->
		<div class="page">
			<!-- 用于javascript提交，搜索内容 -->
			<input type="hidden" id="search_content" value="${search}">
			<script type="text/javascript">
				function page(pageCode) {
					var search = document.getElementById("search_content").value;
					window.location.href = "${pageContext.request.contextPath}/teacher/knowledge/list?pn=" + pageCode + "&search=" + search;
				}
			</script>
			<jsp:include page="../share/page.jsp"></jsp:include>
		</div>
	</div>
	<div class="modal_window student_window form-control" id="knowledge_add">
		<!--标题-->
		<div class="modal_window_title">
			添加知识点: <img src="images/error.png" onclick="toggleKnowledgeAdd(false);">
		</div>
		<form action="" method="post" onsubmit="return addKnowledge(this);">
			<table>
				<tr>
					<td>
						父知识点编号:
					</td>
					<td>
						<select id="pid_select_add" name="pid">
							<option value="-1">无</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>知识点名称:</td>
					<td><input type="text" name="knowledgeName"></td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="2"><span class="error" id="knowledge_add_error">&nbsp;</span>
					</td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="2"><input type="submit" value="提交"></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="modal_window student_window form-control" id="knowledge_edit">
		<!--标题-->
		<div class="modal_window_title">
			编辑知识点: <img src="images/error.png" onclick="toggleKnowledgeEdit(false);">
		</div>
		<form action="" id="knowledge_edit_form" method="post" onsubmit="return editKnowledge(this);">
			<!--提交记录id-->
			<input type="hidden" name="id" id="knowledge_edit_id">
			<table>
				<tr>
					<td>
						父知识点编号:
					</td>
					<td>
						<select id="pid_select_edit" name="pid">
							<option value="-1">无</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>学生姓名:</td>
					<td><input type="text" name="knowledgeName" id="knowledge_edit_name"></td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="2"><span class="error" id="knowledge_edit_error">&nbsp;</span>
					</td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="2"><input type="submit" value="提交"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript" src="script/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/teacher/knowledge.js"></script>
<script src="script/time.js"></script>
<script src="script/tips.js"></script>
</html>>