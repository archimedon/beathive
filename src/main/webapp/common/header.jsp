<%@ include file="/common/taglibs.jsp"%>

<%--     <div id="switchLocale"><a href="<c:url value='/?locale=es'/>">Espanol</a> - <a href="<c:url value='/?locale=pt'/>">Portuguese</a> -<c:if test="${pageContext.request.locale.language != 'en'}"><a href="<c:url value='/?locale=en'/>">English</a></c:if></div>
--%>

<div id="branding">
  <a href="<c:url value="/"/>"><%-- <html:img page="/images/BeatHive_logo_medium.gif" styleId="top_logo" alt="logo"/>--%> </a>
</div>
<hr />
<%-- Put constants into request scope 
<beathive2:constants scope="request"/>
--%>