'use strict';

import Lang from '../utils/lang.js';

import Widget from '../ui/widget.js';
import Net from '../utils/net.js';

export default Lang.Templatable('Widget.Workspaces', class Workspaces extends Widget { 

	constructor(id) {
		super(id);
      this.Node("getAllWorkspaces").addEventListener("click", this.getAllWorkspaces.bind(this));
      this.Node("getUserWorkspace").addEventListener("click", this.getUserWorkspace.bind(this));
      this.Node("createWorkspace").addEventListener("click", this.createWorkspace.bind(this));
      this.Node("deleteWorkspace").addEventListener("click", this.deleteWorkspace.bind(this));
      this.Node("getUserService").addEventListener("click", this.getUserService.bind(this));
      this.Node("createService").addEventListener("click", this.createService.bind(this));
      this.Node("deleteService").addEventListener("click", this.deleteService.bind(this));
	}
	
 getAllWorkspaces()
{
  
  
  const url='http://localhost:8080/RISE_Replica/workspaces';
  var p1 = Net.GetRequest(url);
   
   var c = Promise.all([p1]).then((data) => {
     $('#workspace_div').html(data[0].result);
   });  
}

 getUserWorkspace()
{
  
  
 
  var url='http://localhost:8080/RISE_Replica/workspaces/'+document.getElementById("getUserWorkspace_uname").value;
   var p1 = Net.GetRequest(url);
   
   var c = Promise.all([p1]).then((data) => {
     $('#user_workspace_div').html(data[0].result);
   });  
}

 createWorkspace()
{
  
  var data = {
      
    
          "ConfigWorkspace": 
        { "Restricted":  document.getElementById("workspaceRestricted").value
          
        }
  

      };

      var json = JSON.stringify(data);
      var url='http://localhost:8080/RISE_Replica/workspaces/'+document.getElementById("create_workspace_uname").value
            +"/"+document.getElementById("create_workspace_password").value;

      
      var p1 = Net.PutRequest(url,json);
       console.log(p1);
     document.getElementById("createWorkspaceForm").reset();
}



 deleteWorkspace()
{ 
    
      var url='http://localhost:8080/RISE_Replica/workspaces/'+document.getElementById("delete_workspace_uname").value
            +"/"+document.getElementById("delete_workspace_password").value;

      
       var p1 = Net.DeleteRequest(url);
       console.log(p1);
      document.getElementById("deleteWorkspaceForm").reset();
}
getUserService()
{
   
   
  
   var url='http://localhost:8080/RISE_Replica/workspaces/'+document.getElementById("user_service_uname").value
         + '/' +document.getElementById("user_service_name").value;
   var p1 = Net.GetRequest(url);
   
   var c = Promise.all([p1]).then((data) => {
      console.log(data[0].result);
        $('#getServices_div').html(data[0].result);
   });   
}

 createService()
{
   
   var data = {
         
      
              "ConfigWorkspace": 
            { "Restricted":  document.getElementById("serviceRestricted").value
               
            }
   

       };

       var json = JSON.stringify(data);
       var url='http://localhost:8080/RISE_Replica/workspaces/'+document.getElementById("create_service_uname").value
            + '/' +document.getElementById("create_service_name").value 
            +"/"+document.getElementById("create_service_password").value;

       
       
       var p1 = Net.PutRequest(url,json);
       console.log(p1);
       document.getElementById("createServiceForm").reset();
}



 deleteService()
{  
     
       var url='http://localhost:8080/RISE_Replica/workspaces/'+document.getElementById("delete_service_uname").value
            + '/' +document.getElementById("delete_service_name").value 
            +"/"+document.getElementById("delete_service_oldpassword").value;

       
      var p1 = Net.DeleteRequest(url);
       console.log(p1);
       document.getElementById("deleteServiceForm").reset();
}


	Template() {
		return	"<h3  style = 'margin-left: 400px;'>Get Workspaces</h3>"+

"<div class='container'>"+
  "<form >"+
   " <input class ='b1' type='button' value='Get Workspaces' handle='getAllWorkspaces'/>  "+
 
  "</form>"+
  "<p id = 'workspace_div' > </p>"+
"</div>"+
       
"<h3  style = 'margin-left: 400px;'>Get User Workspace</h3>"+

"<div class='container'>"+
  "<form id ='getUserWorkspaceForm' >"+
    "<label for='getUserWorkspace_uname'>User Name</label>"+
    "<input type='text' id='getUserWorkspace_uname' name='username' placeholder=''>"+
   

   " <input class ='b1' type='button' value='Submit' handle ='getUserWorkspace'/>"+
  "</form>"+
 " <p id = 'user_workspace_div' > </p>"+
"</div> "+


"<h3  style = 'margin-left: 400px;'>Create/Update Workspace</h3>"+

"<div class='container'>"+
  "<form id ='createWorkspaceForm'>"+
    "<label for='uname'>User Name</label>"+
    "<input type='text' id='create_workspace_uname' name='username' placeholder=''>"+

    "<label for='password'>User Password</label>"+
    "<input type='password' id='create_workspace_password' name='password' placeholder=''>"+

    "<label for='Restricted'>Restricted</label>"+
    "<select id='workspaceRestricted' name='Restricted'>"+
    "  <option value='false'>False</option>"+
    "  <option value='true'>True</option>"+
      
  
   " </select>"+

  "  <input class ='b1' type='button' value='Submit' handle ='createWorkspace'/>"+
 " </form>"+
"</div> "+
    
     
 
"<h3  style = 'margin-left: 400px;'>Delete Workspace</h3>"+

"<div class='container'>"+
"  <form id ='deleteWorkspaceForm' >"+
 "   <label for='delete_workspace_uname'>User Name</label>"+
"    <input type='text' id='delete_workspace_uname' name='username' placeholder=''>"+
    
   "  <label for='password'>User Password</label>"+
  "  <input type='password' id='delete_workspace_password' name='password' placeholder=''>"+

 "   <input class ='b1' type='button' value='Submit' handle ='deleteWorkspace'/>"+
"  </form>"+
"</div>"+

   "<h3  style = 'margin-left: 400px;'>Get user Service</h3>"+

   "<div class='container'>"+
   "<form id ='getUserServiceForm' >"+
   " <label for='service_uname'>User Name</label>"+
   "  <input type='text' id='user_service_uname' name='username' placeholder=''>"+
   
   "<label for='service_name'>Service Name</label>"+
   "  <input type='text' id='user_service_name' name='serviceName' placeholder=''>"+
   
   "   <input class ='b1' type='button' value='Submit' handle ='getUserService'/>"+
   "  </form>"+

   "<p id = 'getServices_div' > </p>"+
   "</div> "+

   "<h3  style = 'margin-left: 400px;'>Create Service</h3>"+

   "<div class='container'>"+
   "<form id ='createServiceForm'>"+
   " <label for='uname'>User Name</label>"+
   "  <input type='text' id='create_service_uname' name='username' placeholder=''>"+

   "<label for='service_name'>Service Name</label>"+
   "   <input type='text' id='create_service_name' name='serviceName' placeholder=''>"+

   "    <label for='password'>Password</label>"+
   " <input type='password' id='create_service_password' name='password' placeholder=''>"+

   "<label for='Restricted'>Restricted</label>"+
   "<select id='serviceRestricted' name='Restricted'>"+
   " <option value='false'>False</option>"+
   "  <option value='true'>True</option>"+
   

   " </select>"+

   "  <input class ='b1' type='button' value='Submit' handle ='createService'/>"+
   " </form>"+
   "</div> "+
   

   "<h3  style = 'margin-left: 400px;'>Delete Service</h3>"+

   "<div class='container'>"+
   "<form id ='deleteServiceForm' >"+
   " <label for='delete_uname'>User Name</label>"+
   "  <input type='text' id='delete_service_uname' name='username' placeholder=''>"+
   "<label for='service_name'>Service Name</label>"+
   "<input type='text' id='delete_service_name' name='serviceName' placeholder=''>"+
   
   "<label for='oldpassword'>Password</label>"+
   " <input type='password' id='delete_service_oldpassword' name='oldpassword' placeholder=''>"+


   "  <input class ='b1' type='button' value='Submit' handle ='deleteService'/>"+
   " </form>"+
   "</div>"+
 
"<h3  style = 'margin-left: 400px;'>Get User Framework</h3>"+

"<div class='container'>"+
  "<form id ='userFrameworkForm' >"+
    "<label for='framework_uname'>User Name</label>"+
   " <input type='text' id='user_framework_uname' name='username' placeholder=''>"+
   

  "  <input class ='b1' type='button' value='Submit' onclick ='getUserFramework()'/>"+
 " </form>"+
"</div>"+
 
"<h3  style = 'margin-left: 400px;'>Create Framework</h3>"+

"<div class='container'>"+
  "<form id ='createFrameworkForm'>"+
    "<label for='uname'>User Name</label>"+
    "<input type='text' id='create_framework_uname' name='username' placeholder=''>"+

    "<label for='password'>Password</label>"+
    "<input type='password' id='create_framework_password' name='password' placeholder=''>"+

    "<label for='admin'>Admin</label>"+
   " <select id='create_framework_admin' name='admin'>"+
  "    <option value='true'>True</option>"+
 "     <option value='false'>False</option>"+
  
"    </select>"+

  "  <input class ='b1' type='button' value='Submit' onclick ='createFramework()'/>"+
 " </form>"+
"</div> "+
    
 
"<h3  style = 'margin-left: 400px;'>Update Framework</h3>"+

"<div class='container'>"+
  "<form id ='updateFrameworkForm'>"+
    "<label for='update_uname'>User Name</label>"+
    "<input type='text' id='update_framework_uname' name='username' placeholder=''>"+
   
   "<label for='oldpassword'>Old Password</label>"+
    "<input type='password' id='update_framework_oldpassword' name='oldpassword' placeholder=''>"+


    "<label for='update_password'>New Password</label>"+
    "<input type='password' id='update_framework_password' name='password' placeholder=''>"+

   " <label for='update_admin'>Admin</label>"+
   " <select id='update_framework_admin' name='admin'>"+
  "    <option value='true'>True</option>"+
 "     <option value='false'>False</option>"+
  
"    </select>"+

  "  <input class ='b1' type='button' value='Submit' onclick ='updateFramework()'/>"+
 " </form>"+
"</div>"+


"<h3  style = 'margin-left: 400px;'>Delete Framework</h3>"+

"<div class='container'>"+
  "<form id ='deleteFrameworkForm' >"+
   " <label for='delete_uname'>User Name</label>"+
  "  <input type='text' id='delete_framework_uname' name='username' placeholder=''>"+
   

 "   <input class ='b1' type='button' value='Submit' onclick ='deleteFramework()'/>"+
"  </form>"+
"</div>";
	}
});