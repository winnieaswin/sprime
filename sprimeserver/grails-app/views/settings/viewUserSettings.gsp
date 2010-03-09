<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EDIT USER SETTINGS</title>
    <meta name="layout" content="main" />
  </head>
  <body>

        <g:displaySideBar />

        <div class="span-18 last">

          <g:if test='${flash.message}'>
            <div class='success'>${flash.message}</div>
	  </g:if>

          <g:if test='${flash.errorMsg}'>
            <div class='error'>${flash.errorMsg}</div>
	  </g:if>

          <h3 class="loud">Edit User Settings</h3>

          <p>Feel free to edit your user information and settings.</p>

          <div id="userSettingsForm" class="span-10 append-8 last">

          <g:form action="editUserSettings">
            <input type="hidden" name="id" value="${person.id}" />
	    <input type="hidden" name="version" value="${person.version}" />

            <fieldset>
               <legend>User Information</legend>
               <p><label for="fullname">Full name:</label><br />
               <g:textField name="fullname" class="text" value="${person.userRealName?.encodeAsHTML()}" /></p>

               <p><label for="email">E-mail:</label><br />
               <g:textField name="email" class="text" value="${person?.email?.encodeAsHTML()}"/></p>
            </fieldset>

            <fieldset>
              <legend>Change Password</legend>
              <p><label for="oldpassword">Old Password:</label><br/>
              <input type="password" name="oldpassword" class="text"/></p>

              <p><label for="newpassword">New Password:</label><br/>
              <input type="password" name="newpassword" class="text"/></p>

              <p><label for="confirmpassword">Confirm New Password:</label><br/>
              <input type="password" name="confirmpassword" class="text"/></p>
            </fieldset>


            <g:submitButton name="update" value="Update" />
          </g:form>
          </div>
        </div>
</html>
