'use strict';

import Lang from './utils/lang.js';
import Net from './utils/net.js';
import Widget from './ui/widget.js';
import Accounts from './widgets/accounts.js';
import Workspaces from './widgets/workspaces.js';	
import Services from './widgets/services.js';
import Frameworks from './widgets/frameworks.js';	

export default class Main extends Widget { 

	constructor(node) {		

		super(node);
		
		
		this.Node("button1").addEventListener("click", this.onButton1Handler.bind(this));
		this.Node("button2").addEventListener("click", this.onButton2Handler.bind(this));
		this.Node("button3").addEventListener("click", this.onButton3Handler.bind(this));
		this.Node("button4").addEventListener("click", this.onButton4Handler.bind(this));

	}


	onError_Handler(ev) {
		alert(ev.error.ToString())
	}
	
	onButton1Handler(ev) {
		var target = ev.target.attributes[3].value;

		$('.divs').not(target).slideUp();
		$(target).slideDown();  }

		onButton2Handler(ev) {
			var target = ev.target.attributes[3].value;

			$('.divs').not(target).slideUp();
			$(target).slideDown(); 
		}

		onButton3Handler(ev) {
			var target = ev.target.attributes[3].value;

			$('.divs').not(target).slideUp();
			$(target).slideDown(); 
		}

		onButton4Handler(ev) {
			var target = ev.target.attributes[3].value;

			$('.divs').not(target).slideUp();
			$(target).slideDown(); 
		}


		Template() {
			return	"<div class='main'>" +

			"<div class='topnav'>" +
			"<a href='#'  handle= 'button1' class='b1 buttons' data-target='.div1'>Accounts</a>" +
			"<a href='#'  handle= 'button2'  class='b2 buttons' data-target='.div2'>Workspaces</a>" +
			"<a href='#'  handle= 'button3'  class='b3 buttons' data-target='.div3'>Services</a>" +
			" <a href='#'  handle= 'button4'  class='b4 buttons' data-target='.div4'>Frameworks</a>" +

			"</div>" +
			"<div handle='div1' class='div1 divs'><h1>Accounts</h1>"+
			"<div widget='Widget.Accounts'></div>" +
			"</div>"+			
			"<div handle='div2' class='div2 divs'><h1>Workspaces</h1>"+
			"<div widget='Widget.Workspaces'></div>" +
			"</div>"+		
			"<div handle='div3' class='div3 divs'><h1>Services</h1>"+
			"<div  widget='Widget.Services'></div>" +
			"</div>"+			
			"<div handle='div4' class='div4 divs'><h1>Frameworks</h1>"+
			"<div  widget='Widget.Frameworks'></div>" +
			"</div>"+				
			"</div>"
			;
		}
	}