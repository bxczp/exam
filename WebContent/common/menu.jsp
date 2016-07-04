<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function logout ()
    {
	    if (confirm ("确认退出？"))
	    {
		    window.location.href = "student!logout";
	    }
    }

    function logout2 ()
    {
	    if (confirm ("确认退出？"))
	    {
		    window.location.href = "manager!logout";
	    }
    }
</script>

<!-- Bootstrap3 的导航条样式 -->
<div class="navbar">
	<div class="navbar-inner">
		<a class="brand" href="main.jsp">首页</a>
		<ul class="nav">
			<c:choose>
				<c:when test="${currentStudent.flag == 1}">
					<li>
						<a href="${pageContext.request.contextPath}/student!list">考生信息管理</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath }/exam!list">考生成绩查询</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath }/paper!paperList">试卷管理</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath }/question!list">题目管理</a>
					</li>
					<li>
						<a href="javascript:logout2()">退出系统</a>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<a href="${pageContext.request.contextPath}/paper!list">在线考试</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/exam!getExams?s_exam.student.id=${currentStudent.id}">成绩查询</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/student!preUpdate">修改密码</a>
					</li>
					<li>
						<a href="javascript:logout()">退出系统</a>
					</li>
				</c:otherwise>
			</c:choose>

		</ul>
	</div>





</div>