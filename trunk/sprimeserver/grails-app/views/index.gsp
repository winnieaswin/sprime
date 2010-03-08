<html>
    <head>
        <title>SPRIME</title>
        <meta name="layout" content="main" />
    </head>
    <body>

        <div class="span-11 colborder" id="loginBox">
          <h3 class="loud">Login</h3>
          
          <g:if test='${flash.message}'>
            <div class='error'>${flash.message}</div>
	  </g:if>

          <p>Enter your user name and password to begin using SPRIME.</p>

          <form action="${resource(file:'j_spring_security_check')}" method="POST">
            <p><label for="j_username">Login name:</label><br/>
            <g:textField name="j_username" class="text" /></p>

            <p><label for="j_password">Password: </label><br />
            <input type="password" name="j_password" class="text" /></p>
            
            <p><label for="_spring_security_remember_me">Remember Me:</label>
            <input type="checkbox" name="_spring_security_remember_me" /></p>
            
            <g:submitButton name="login" value="Log In" />
          </form>

          
        </div>

        <div class="span-12 last">
          <h3 class="loud">Welcome to SPRIME</h3>

          <p>Welcome to SPRIME. Sprime is a system for monitoring and controlling your power usage in your
          house. SPRIME connects with a special device attached to your outlet and receives usage information
          from it. SPRIME can show you how much power you use in a variety of different ways. You can even
          shut down the power to that outlet remotely. Login and try!</p>
        </div>
    </body>
</html>