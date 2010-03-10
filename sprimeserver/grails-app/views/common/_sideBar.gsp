       <div id="leftSideBar" class="span-5 colborder">

          <h3 class="caps">View Data</h3>

          <div class="box">
            <ul class="list" id="dataViews">
             <li><g:link elementId="current" class="quiet" action="viewData" controller="application" params="[id:'current']">Current</g:link></li>
             <li><g:link elementId="hourly" class="quiet" action="viewData" controller="application" params="[id:'hourly']">Hourly</g:link></li>
             <li><g:link elementId="daily" class="quiet" action="viewData" controller="application" params="[id:'daily']">Daily</g:link></li>
             <li><g:link elementId="weekly" class="quiet" action="viewData" controller="application" params="[id:'weekly']">Weekly</g:link></li>
             <li><g:link elementId="monthly" class="quiet" action="viewData" controller="application" params="[id:'monthly']">Monthly</g:link></li>
             <li><g:link elementId="yearly" class="quiet" action="viewData" controller="application" params="[id:'yearly']">Yearly</g:link></li>
            </ul>
          </div>

          <h3 class="caps">Control Power</h3>

          <div class="box">
            <ul class="list" id="controlFunctions">
             <li><g:link elementId="on" class="quiet" action="controlPower" controller="application" params="[id: type, control: 'on']">ON</g:link></li>
             <li><g:link elementId="off" class="quiet" action="controlPower" controller="application" params="[id: type, control: 'off']">OFF</g:link></li>
            </ul>
          </div>

          <h3 class="caps">Settings</h3>
          <div class="box">
            <ul class="list">
             <li><g:link action="viewUserSettings" controller="settings" class="quiet">User Profile</g:link></li>
             <li><a href="#" class="quiet">Graph Setting</a></li>
            </ul>
          </div>


        </div>
