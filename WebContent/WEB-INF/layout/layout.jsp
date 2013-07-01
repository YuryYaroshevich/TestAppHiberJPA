<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/struts-html.tld" prefix="html"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/divBorder.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/layout/layout.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/layout/header.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/layout/menu.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/layout/footer.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/layout/content.css" type="text/css"
	media="screen" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/contentMinHeight.js"></script>
<title>
    <tiles:useAttribute name="title" />
    <bean:message key="${title}" />
</title>
</head>
<body>
	<div id="header">
		<tiles:insert attribute="header" />
	</div>

	<div id="menu">
		<tiles:insert attribute="menu" />
	</div>

	<div id="content">
		<tiles:insert attribute="content" />
	</div>

	<div id="footer">
		<tiles:insert attribute="footer" />
	</div>
</body>
</html>