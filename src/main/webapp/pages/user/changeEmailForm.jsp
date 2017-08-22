<%@ include file="/common/var_contentType.jsp"%>
<form id="validateEmail" name="validateEmail" method="post" action="/user/changeEmail.html">
<ul>
  <li><div id="messageOutput"></div></li>
  
  <li id="f1">
   <label for="newemail">New Email address:</label>
   <input name="email" type="text" id="newemail" size="25" maxlength="25" />
  </li>
  
  <li id="f2" style="display:none;">
   <label for="vericode">Enter verification code:</label>
   <input name="vericode" type="text" id="vericode" size="25" maxlength="25" disabled="true"/>
  </li>
  
  <li>
   <input name="method.confirm" type="submit" id="validateButton" value="validate"/>
   
  </li>
</ul></form>
