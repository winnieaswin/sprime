<html>
  <head>
    <title>VIEW DATA (${type})</title>
    <meta name="layout" content="main" />
  </head>
  <body>

        <g:displaySideBar />

        <div class="span-18 last">

          <g:if test='${flash.message}'>
            <div class='success'>${flash.message}</div>
	  </g:if>

          <h3 class="loud"><span style="text-transform:capitalize">${type}</span> Power Usage</h3>

          <p>Here is a break down of your ${type} power usage:</p>

          <form >
        </div>

  </body>
</html>