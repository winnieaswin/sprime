<html>
    <head>
        <title>SPRIME >> <g:layoutTitle default="Grails" /></title>
        
        <link rel="stylesheet" href="${resource(dir:'css/blueprint', file:'screen.css')}" type="text/css" media="screen, projection" />
        <link rel="stylesheet" href="${resource(dir:'css/blueprint', file:'print.css')}" type="text/css" media="print" />
        <!--[if lt IE 8]><link rel="stylesheet" href="${resource(dir:'css/blueprint', file:'ie.css')}" type="text/css" media="screen, projection"><![endif]-->

        <link rel="stylesheet" href="${resource(dir:'css/blueprint/plugins/fancy-type', file:'screen.css')}" type="text/css" media="screen, projection" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />				
    </head>
    <body>

        <div class="container">
          <div id="header" class="span-24 last">

            
            <div id="titleWithLogo" class="<g:isLoggedIn>span-21</g:isLoggedIn><g:isNotLoggedIn>span-24 last</g:isNotLoggedIn>">
                <img id="logo" src="${resource(dir:'images', file:'sprimelogo.png')}" class="left" alt="Sprime Logo" />
                <h1 id="sprime_header" >SPRIME</h1>
              </div>
            <g:isLoggedIn>
              <div class="span-3 prepend-top last">
                <a href="${resource(file:'/logout')}" id="logoutLink" class="quiet">Logout</a>
              </div>
            </g:isLoggedIn>
          </div>
          <hr />
          <div id="subheader" class="span-24 last">
            <h3 id="sprime_subheader" class="alt">Simple Power Remote Interactive Monitor and Enforcer</h3>
          </div>
          <hr />
          
          <div id="body" class="span-24 append-bottom last">
            <g:layoutBody />
          </div>

          <hr />
          <div id="footer" class="prepend-11 span-13 last">
            <p class="quiet small">SPRIME 2010</p>
          </div>
        </div>		
    </body>	
</html>