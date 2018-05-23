<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Liste des Professeurs existants</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"></c:url>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" ></c:import>
        <div id="corps">
        <c:choose>
            <%-- Si aucun Professeur n'existe en session, affichage d'un message par défaut. --%>
            <c:when test="${ empty sessionScope.Professeurs }">
                <p class="erreur">Aucun Professeur enregistré.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>MDP</th>
                    <th>Filiere</th>
                    <th>Adresse</th>
                    <th>Téléphone</th>
                    <th>Email</th>
                    <th>Image</th>
                    <th class="action">Action</th>                    
                </tr>
                <%-- Parcours de la Map des Professeurs en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ sessionScope.Professeurs }" var="mapProfesseurs" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propriétés du bean Professeur, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                    <td><c:out value="${ mapProfesseurs.value.nom }"></c:out></td>
                    <td><c:out value="${ mapProfesseurs.value.prenom }"></c:out></td>
                    <td><c:out value="${ mapProfesseurs.value.mdp }"></c:out></td>
                    <td><c:out value="${ mapProfesseurs.value.filiere }"></c:out></td>
                    <td><c:out value="${ mapProfesseurs.value.adresse }"></c:out></td>
                    <td><c:out value="${ mapProfesseurs.value.telephone }"></c:out></td>
                    <td><c:out value="${ mapProfesseurs.value.email }"></c:out></td>
                    <td>
                        <%-- On ne construit et affiche un lien vers l'image que si elle existe. --%>
                        <c:if test="${ !empty mapProfesseurs.value.image }">
                            <c:set var="image"><c:out value="${ mapProfesseurs.value.image }"></c:out></c:set>
                            <a href="<c:url value="/images/${ image }"></c:url>">Voir</a>
                        </c:if>
                    </td>
                    <%-- Lien vers la servlet de suppression, avec passage du nom du Professeur - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param></c:param>. --%>
                    <td class="action">
                        <a href="<c:url value="/suppressionProfesseur"><c:param name="idProfesseur" value="${ mapProfesseurs.key }" ></c:param></c:url>">
                            <img src="<c:url value="/inc/supprimer.png"></c:url>" alt="Supprimer" />
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
        </div>
    </body>
</html>