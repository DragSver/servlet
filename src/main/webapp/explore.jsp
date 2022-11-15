<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<p>${date}</p>
<h1>${path}</h1>
<hr>
<p><a href="${contextPath}/files?path=${path.replace('\\','/').substring(0, path.lastIndexOf('\\'))}">Вверх</a></p>
<table>
    <tr>
        <td>

        </td>
        <td>
            <b>Файл</b>
        </td>
        <td>
            <b>Размер</b>
        </td>
        <td>
            <b>Дата</b>
        </td>
    </tr>
    <c:forEach var="el" items="${elements}">
        <tr>
            <td>
                    ${el.isDirectory()?"(D)":"(F)"}
            </td>
            <td>
                    <a href=${contextPath}/files?path=${el.getAbsolutePath().replace(' ',"%20").replace('\\','/')}>${el.getName()}</a>
            </td>
            <td>
                    ${el.length()/8==0?"":el.length()/8} ${el.length()/8==0?"":" B"}
            </td>
            <td>
                    ${format.format(el.lastModified())}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
