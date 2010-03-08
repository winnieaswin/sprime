<html>
  <head>
    <title>VIEW DATA (${type})</title>
    <meta name="layout" content="main" />
  </head>
  <body>

        <div id="leftSideBar" class="span-5 colborder">

          <h3 class="caps">View Data</h3>

          <div class="box">
            <ul class="list">
             <li><a class="quiet" href="${resource(file:'/viewData')}?id=daily">Daily</a></li>
             <li><a href="${resource(file:'/viewData')}?id=weekly" class="quiet">Weekly</a></li>
             <li><a href="${resource(file:'/viewData')}?id=monthly" class="quiet">Monthly</a></li>
             <li><a href="${resource(file:'/viewData')}?id=yearly" class="quiet">Yearly</a></li>
            </ul>
          </div>

          <h3 class="caps">Control Power</h3>

          <div class="box">
            <ul class="list">
             <li><a href="#" class="quiet">ON</a></li>
             <li><a href="#" class="quiet">OFF</a></li>
            </ul>
          </div>


        </div>

        
        <div class="span-18 last">
          <h3 class="loud">${type} Power Usage</h3>

          <p>Here is a break down of your <span style="text-transform:lowercase">${type}</span> power usage:</p>
        </div>

  </body>
</html>