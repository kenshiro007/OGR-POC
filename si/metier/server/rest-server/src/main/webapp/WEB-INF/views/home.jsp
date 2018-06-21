<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Accueil</title>
</head>
<body>
<h1>
	Bienvenue sur le serveur de service de l'APEC
</h1>
<P>	API disponible : <a href="${url}">${url}</a> </P>
<P> Heure : ${serverTime}. </P>
</body>
</html>
