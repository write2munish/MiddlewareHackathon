_satellite.pushAsyncScript(function(event, target, $variables){
  (function($) {
  
  if (_satellite.getVar('dlCountry') === 'nl') {
  
	//Write cookie if referrer on same domain
	/* will be done from second rule, top of page, to account for hardcoded cookie bar
	if(!_satellite.readCookie('p-cookie-msg')) {
		if(document.referrer.match(document.domain)) {
			_satellite.setCookie('p-cookie-msg', 'true', 365);
		}
	}
	*/
	
	//Create function for attaching events
	function barAttach() {
		$(".p-cookiebar .p-content a").click(function(){
			//Open consent manager
			a=truste.eu.bindMap;truste.eu.prefview(a,"prefmgr");
			//Close the bar
			$('.p-infobar').hide();
			//Set the cookie
			_satellite.setCookie('p-cookie-msg', 'true', 365);
			//Show the button
			$("div#truste-button").css("visibility", "visible");
			//Don't go to URL
			return false;
		});
		$(".p-cookiebar .p-close").click(function(){
			//Show the button
			$("div#truste-button").css("visibility", "visible");
		});
		if(navigator.userAgent.match(/Android|BlackBerry|BB10|iPhone|iPad|iPod|Opera Mini|IEMobile/i)) {
			$('div.p-container a.p-close').text('');
			$('div.p-container a.p-close').css({'height':'18px'});
			$('.p-infobar .p-container').css({'padding-right':'30px','padding-left':'30px','max-width':'100%'});
		}
	}
	
	$.cachedScript = function( url, options ) {
		// Allow user to set any option except for dataType, cache, and url
		options = $.extend( options || {}, {
			dataType: "script",
			cache: true,
			url: url
		});
		// Use $.ajax() since it is more flexible than $.getScript
		// Return the jqXHR object so we can chain callbacks
		return $.ajax( options );
	};
	
	//Add the cookie bar if the cookie is not there
	if(!_satellite.readCookie('p-cookie-msg') && typeof _page.cookieBar !== 'object') {
		//Mark this action for hiding the button (button can come first due to deferring the scripts for the bar)
		var barLoading = true;
		
		//Add css for the cookie bar to body
		$('body').append('<link type="text/css" rel="stylesheet" href="//www.crsc.philips.com/crsc/styles/screen.cookiebar.css" />');
		
		//Add RTL css if necessary
		if(_satellite.getVar('dlLanguage') === 'ar' || _satellite.getVar('dlLanguage') === 'iw') {
			$('body').append('<link type="text/css" rel="stylesheet" href="//www.crsc.philips.com/crsc/styles/rtl.cookiebar.css" />');
		}
	
		//Create function for overwriting text (must be automated!)
		function barText() {
				_page.text.cookie_message = "Philips respecteert uw privacy. We maken gebruik van cookies voor verschillende doeleinden, zoals: websitefunctionaliteit, verbeteren van de gebruikerservaring, integratie met sociale media en weergave van (gerichte) advertenties. Door onze website te bezoeken, stemt u in met ons gebruik van cookies. U kunt echter altijd uw {0}";
				_page.text.here = 'cookie-instellingen wijzigen.';
		}
	
		//Load cookie bar resources in order
		$.browser= {};
		var defer = $.Deferred();
		defer.then(function(){return $.cachedScript('//www.crsc.philips.com/crsc/scripts/lib_global.internet.js');})
			.then(function(){return $.cachedScript(_satellite.getVar("linkLocale"));})
			.then(function(){return barText();})
			.then(function(){return _page.createCookieBar();})
			.then(function(){return barAttach();});
		defer.resolve();
	}
	
	if(!_satellite.readCookie('p-cookie-msg') && typeof _page.cookieBar === 'object') {
		$('.p-infobar .p-content').html("Philips respecteert uw privacy. We maken gebruik van cookies voor verschillende doeleinden, zoals: websitefunctionaliteit, verbeteren van de gebruikerservaring, integratie met sociale media en weergave van (gerichte) advertenties. Door onze website te bezoeken, stemt u in met ons gebruik van cookies. U kunt echter altijd uw <a href=\"http://www.philips.nl/cookienotice/index.page\" target=\"_blank\">cookie-instellingen wijzigen.</a>");
		barAttach();
	}
	
	var tProperty = 'philips.nl';
	
	//Add the Truste button to the page
	$('body').append('<div id="truste-button"><div id="teconsent"></div></div>');
	$.cachedScript('//consent-st.truste.com/get?name=notice.js&domain=' + tProperty + '&c=teconsent&text=true&country=nl&language=nl');
	
	//If the cookie bar is there hide the button
	if(!_satellite.readCookie('p-cookie-msg') && (barLoading || typeof _page.cookieBar === 'object')) {
		$('div#truste-button').css({
			'visibility':'hidden'
		});
	}
	
	//Position it in the bottom right corner
	$('div#truste-button').css({
		'position':'fixed',
		'bottom':'0px',
		'right':'0px'
	});
	
	//Style it
	$('div#teconsent').css({
	    'background':'#0089c4',
	    'border-radius':'2px',
	    'display':'inline-block',
	    'min-width':'38px',
	    'padding':'11px 24px 10px'
	});
	
	//Style text (comes in later)
	$("<style>#truste-button a { color: #fff; font:14px/14px centrale_sans,Tahoma,Helvetica,Arial,Verdana,sans-serif;}</style>").appendTo(document.documentElement);
	
	//Add hover styling in and out
	$('div#teconsent').hover(
		function(){
			$(this).css({
		    	'background':'#0073b4',
	
			});
		},
		function(){
			$(this).css({
		    	'background':'#0089c4',
	
			});
		}
	);

  }
})(jQuery);
});
