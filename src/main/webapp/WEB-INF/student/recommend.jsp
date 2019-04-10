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
<title>参加考试</title>
<meta charset="UTF-8">
<base href="<%=basePath%>">
<link rel="SHORTCUT ICON" href="images/icon.ico">
<link rel="BOOKMARK" href="images/icon.ico">
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/head.css">
<link rel="stylesheet" type="text/css" href="css/student/exam_take.css">
</head>
<body >
	<!--头部-->
	<jsp:include page="share/head.jsp"></jsp:include>
		<form method="post" action="student/exam/submit-one">
			<!--中间主体部分-->
			<div class="main">
				<c:if test="${question.type == 'SINGLE'}">
				<hr>
				<div id="single-container">
					<!-- 题型标识 -->
					<div class="title">
						本次为你推荐的是单选题
					</div>
					<div class="question">
						<!-- 保存题目id -->
						<input type="hidden" name="id" value="${question.id}" />
						<div class="question-title">
							&nbsp;&nbsp;${question.title}
						</div>
						<ul class="question-option">
							<li>A.&nbsp;&nbsp;${question.optionA}</li>
							<li>B.&nbsp;&nbsp;${question.optionB}</li>
							<li>C.&nbsp;&nbsp;${question.optionC}</li>
							<li>D.&nbsp;&nbsp;${question.optionD}</li>
						</ul>
						<div class="question-answer">
							答案:
							<input type="radio" name="anwser" value="0" />A
							<input type="radio" name="anwser" value="1" />B
							<input type="radio" name="anwser" value="2" />C
							<input type="radio" name="anwser" value="3" />D
						</div>
					</div>
				</div>
				<hr>
				</c:if>
				<c:if test="${question.type == 'MULTI'}">
				<hr>
				<!-- 多选题 -->
				<div id="multi-container">
					<div class="title">
						本次为你推荐的是多选题
					</div>
					<div class="question">
						<input type="hidden" name="id" value="${question.id}" />
						<div class="question-title">${question.title}
						</div>
						<ul class="question-option">
							<li>A.&nbsp;&nbsp;${question.optionA}</li>
							<li>B.&nbsp;&nbsp;${question.optionB}</li>
							<li>C.&nbsp;&nbsp;${question.optionC}</li>
							<li>D.&nbsp;&nbsp;${question.optionD}</li>
						</ul>
						<div class="question-answer">
							答案:
							<input name="anwser" type="checkbox" value="0" />A
							<input name="anwser" type="checkbox" value="1" />B
							<input name="anwser" type="checkbox" value="2" />C
							<input name="anwser" type="checkbox" value="3" />D
						</div>
					</div>
				</div>
				<hr>
				</c:if>
				<c:if test="${question.type == 'JUDGE'}">
				<hr>
				<!-- 判断题 -->
				<div id="judge-container">
					<div class="title">
						本次为你推荐的是判断题
					</div>
					<div class="question">
						<input type="hidden" name="id" value="${question.id}" />
						<div class="question-title">
							&nbsp;&nbsp;${question.title}
						</div>
						<div class="question-answer">
							答案:
							<input type="radio" name="anwser" value="0" />对
							<input type="radio" name="anwser" value="1" />错
						</div>
					</div>
				</div>
				<hr />
				</c:if>
				<div style="text-align: center;margin-bottom: 20px;">
					<input name="提交" type="submit" />
				</div>
		</form>
</body>
<script type="text/javascript" src="script/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/tips.js"></script>
</html>