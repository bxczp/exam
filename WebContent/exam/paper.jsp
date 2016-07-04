<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

<script type="text/javascript">
	// 以秒为单位
	var examTime = 20 * 60;
	var useTime = 0;
	var remainTime = examTime;

	function showTime() {

		if (remainTime == 0) {
			document.getElementById("myForm").submit();
		}

		useTime += 1;
		remainTime -= 1;
		//Math.floor 只取整数部分
		var hourU = Math.floor(useTime / 3600);
		var minU = Math.floor((useTime - hourU * 3600) / 60);
		var secU = Math.floor(useTime - hourU * 3600 - minU * 60);
	

		var hourR = Math.floor(remainTime / 3600);
		var minR = Math.floor((remainTime - hourR * 3600) / 60);
		var secR = Math.floor(remainTime - hourR * 3600 - minR * 60);

		document.getElementById("useTime").innerHTML = format(hourU) + ":"
				+ format(minU) + ":" + format(secU);
		document.getElementById("remainTime").innerHTML = format(hourR) + ":"
				+ format(minR) + ":" + format(secR);
	}

	//格式化时间
	function format(timeNum) {
		if (timeNum < 10) {
			return "0" + timeNum;
		} else {
			return timeNum;
		}
	}

	//窗口每个一秒调用
	window.setInterval("showTime()", 1000);
</script>



</head>
<body>
	<div class="data_list">

		<div class="data_info">
			<p>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;考试时间：<strong>20分钟</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				计时：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font id="useTime"
					style="font-weight: bold;"></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				剩余时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font id="remainTime"
					style="font-weight: bold;"></font>
			</p>
			<hr />
			<p class="examTitle">${paper.paperName}&nbsp;&nbsp;考试卷</p>
			<p class="examScoreInfo">(&nbsp;满分120&nbsp;&nbsp;单选题60分&nbsp;&nbsp;多选题60分&nbsp;)</p>
		</div>


		<div class="data_exam_content">
			<form action="exam!add" id="myForm" method="post">

				<strong><big>一，单选题</big></strong>(每题20分，答错不得分)<br /> <br />
				<c:forEach var="s" items="${squestionList}" varStatus="status">
					<strong>[&nbsp;${status.index+1 }&nbsp;]&nbsp;${s.subject }</strong>
					<br />
					<br />
					<!-- radio单选框 -->
					<label class="radio"> <input type="radio"
						name="id-r-${s.id }" value="A" /> ${s.optionA }
					</label>
					<label class="radio"> <input type="radio"
						name="id-r-${s.id }" value="B" /> ${s.optionB }
					</label>
					<label class="radio"> <input type="radio"
						name="id-r-${s.id }" value="C" /> ${s.optionC }
					</label>
					<label class="radio"> <input type="radio"
						name="id-r-${s.id }" value="D" /> ${s.optionD }
					</label>
					<br />
				</c:forEach>
				<br /> <strong><big>一，多选题</big></strong>(每题30分，答错不得分)<br /> <br />
				<c:forEach var="m" items="${mquestionList}" varStatus="status">
					<strong>[&nbsp;${status.index+1 }&nbsp;]&nbsp;${m.subject }</strong>
					<br />
					<br />
					<!-- checkBox复选框 -->
					<label class="checkbox"> <input type="checkbox"
						name="id-c-${m.id }" value="A" /> ${m.optionA }
					</label>
					<label class="checkbox"> <input type="checkbox"
						name="id-c-${m.id }" value="B" /> ${m.optionB }
					</label>
					<label class="checkbox"> <input type="checkbox"
						name="id-c-${m.id }" value="C" /> ${m.optionC }
					</label>
					<label class="checkbox"> <input type="checkbox"
						name="id-c-${m.id }" value="D" /> ${m.optionD }
					</label>
					<br />
				</c:forEach>
				<input type="hidden"  name="exam.student.id" id="student.id" value="${currentStudent.id }"/>
				<input type="hidden"  name="exam.paper.id"  id="paper.id" value="${paper.id }" />
				<button class="btn btn-primary"  type="submit">交卷</button>
			</form>
		</div>
	</div>
</body>
</html>