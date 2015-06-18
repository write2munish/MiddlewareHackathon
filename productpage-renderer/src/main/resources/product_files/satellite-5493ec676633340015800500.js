_satellite.pushAsyncScript(function(event, target, $variables){
  //Define script links
switch (_satellite.getVar('dlCountry')) {
	case 'ar':
		var ubDesk = 'fb564eaf638b';
		var ubMob = 'eff45a5e7942';
		break;
	case 'at':
		var ubDesk = '6f03d7b5b27b';
		var ubMob = '7ec9498bfc58';
		break;
	case 'au':
		var ubDesk = 'c2a8a22f5593';
		var ubMob = '38b9709a8320';
		break;
	case 'be':
		switch (_satellite.getVar('dlLanguage')) {
			case 'nl':
			    var ubDesk = '0fc41f49ee84';
				var ubMob = '69cc8e0910ef';
				break;
			case 'fr':
				var ubDesk = '8e8f36ee6543';
				var ubMob = '876b043eb4a2';
				break;
		}
		break;
	case 'bg':
		var ubDesk = 'b378fc364b48';
		var ubMob = '6938e3f0bde5';
		break;
	case 'br':
		var ubDesk = 'a248ce107761';
		var ubMob = 'd1b1365cb30c';
		break;
	case 'ca':
		switch (_satellite.getVar('dlLanguage')) {
			case 'en':
				var ubDesk = '0aacf47c5d5b';
				var ubMob = '617fa9b03e72';
				break;
			case 'fr':
				var ubDesk = '9283caf8cd3a';
				var ubMob = '950ee705a225';
				break;
		}
		break;
	case 'ch':
		switch (_satellite.getVar('dlLanguage')) {
			case 'de':
				var ubDesk = '381d400b00f8';
				var ubMob = 'ca787f8a1943';
				break;
			case 'fr':
				var ubDesk = '04d28412a58f';
				var ubMob = '3973e246f86e';
				break;
		}
		break;
	case 'cn':
		var ubDesk = '87f618d73944';
		var ubMob = '8671e185010f';
		break;
	case 'de':
	    var ubDesk = '322f577072f4';
		var ubMob = 'f4f85fc5e032';
		break;
	case 'dk':
		var ubDesk = 'e34cb3f33e3d';
		var ubMob = '69a5dde4a496';
		break;
	case 'es':
	    var ubDesk = '6b2f3e827c4d';
		var ubMob = '45473bd0b60d';
		break;
	case 'fi':
		var ubDesk = 'b62bdf608547';
		var ubMob = 'fa2244fb99ce';
		break;
	case 'fr':
	    var ubDesk = 'abb04b8f3899';
		var ubMob = 'f2280bed648a';
		break;
	case 'global':
	    var ubDesk = 'fe6327a0e2ba';
		var ubMob = 'a3f010d6fe8f';
		break;
	case 'gr':
	    var ubDesk = '1441aa61ab45';
		var ubMob = '696ee1d0f882';
		break;
	case 'hk':
	    switch (_satellite.getVar('dlLanguage')) {
			case 'en':
				var ubDesk = '7afd836e3180';
				var ubMob = 'b384ac063d41';
				break;
			case 'zh':
				var ubDesk = 'f0b86cd055c6';
				var ubMob = '1b47895e7348';
				break;
		}
		break;
	case 'hr':
	    var ubDesk = '2484c19b20b5';
		var ubMob = 'ebe9668439ed';
		break;
	case 'hu':
	    var ubDesk = '9a5f52b21a2f';
		var ubMob = '1da7c4c668b5';
		break;
	case 'id':
	    var ubDesk = 'ae08337dc99c';
		var ubMob = 'd56ead547f93';
		break;
	case 'in':
	    var ubDesk = 'afbcb6b5e982';
		var ubMob = 'c6cfc5a073ee';
		break;
	case 'it':
	    var ubDesk = '41e1e739e342';
		var ubMob = 'eb07863045ec';
		break;
	case 'jp':
	    var ubDesk = 'b3045213d29c';
		var ubMob = '6fde4f5390c6';
		break;
	case 'kr':
	    var ubDesk = '62a9eddfdc65';
		var ubMob = '68820633ca11';
		break;
	case 'me':
		var ubDesk = '8c6629ddd0de';
		var ubMob = '0a113c7c9290';
		break;
	case 'mx':
		var ubDesk = '6d59b02fc570';
		var ubMob = '738bd24b9252';
		break;
	case 'my':
		var ubDesk = '71d09c538f29';
		var ubMob = '05aa27221e46';
		break;
	case 'no':
		var ubDesk = 'b52cdbf3a612';
		var ubMob = 'a9bea644eff8';
		break;
	case 'nz':
		var ubDesk = '194dcaa090db';
		var ubMob = '227b4a80b3bf';
		break;
	case 'ph':
	    var ubDesk = '386ed8bd618d';
		var ubMob = '0ae480964ee6';
		break;
	case 'pl':
	    var ubDesk = 'e432b11d8199';
		var ubMob = '8389febfb8e5';
		break;
	case 'pt':
		var ubDesk = '0dd6e85aad0f';
		var ubMob = '61be2aab719c';
		break;
	case 'ru':
	    var ubDesk = '88f9df7b0c39';
		var ubMob = '414d20c50882';
		break;
	case 'ro':
		var ubDesk = 'ad5387cc9f22';
		var ubMob = 'd2e54d8462ec';
		break;
	case 'se':
	    var ubDesk = '315836e4a2f1';
		var ubMob = '979beab1b03e';
		break;
	case 'sg':
	    var ubDesk = '0cd82791af71';
		var ubMob = '82a988196f15';
		break;
	case 'si':
		var ubDesk = '308af44a2f98';
		var ubMob = '2b364706e4e7';
		break;
	case 'th':
	    var ubDesk = '013293022477';
		var ubMob = '98914d59129d';
		break;
	case 'tr':
	    var ubDesk = '2282c17bebd2';
		var ubMob = 'db519f4fc5db';
		break;
	case 'tw':
	    var ubDesk = 'b0e553e0a464';
		var ubMob = '8a724f62c47e';
		break;
	case 'vn':
	    var ubDesk = '527791e32571';
		var ubMob = '9ced9d3e9bd1';
		break;
	case 'za':
	    var ubDesk = '89d0b533710b';
		var ubMob = '1373970d489a';
		break;
	case 'gb':
	    var ubDesk = 'c37dad75a9d9';
		var ubMob = '02004f42180b';
		break;
	case 'us':
		var ubDesk = '4c9a9447a0c0';
		var ubMob = 'aa8a4f784eab';
		break;
	case 'nl':
	    var ubDesk = '23f41479f86d';
		var ubMob = '86ad1221609d';
		break;
	case 'pe':
	    var ubDesk = 'e996128e6ea2';
		var ubMob = 'd9b257278f3c';
		break;
	case 'pa':
	    var ubDesk = '88d373de1240';
		var ubMob = '107e1c27ae55';
		break;
	case 'co':
	    var ubDesk = 'a0b34b1b7db8';
		var ubMob = '78a82bdd1fe3';
		break;
	case 'cl':
	    var ubDesk = 'de4428114acc';
		var ubMob = 'a9118b63bba6';
		break;
	case 'sk':
	    var ubDesk = '1608fa040716';
		var ubMob = '4fae8205a22c';
		break;
	case 'cz':
	    var ubDesk = '861725827c05';
		var ubMob = '921d8f48cb9c';
		break;	
	case 've':
	    var ubDesk = 'd6e430db39c6';
		var ubMob = 'e74217cee35a';
		break;
	default:
		var ubDesk = 'missing';
		var ubMob ='missing';
		break;
}

//Check for ML survey group
if (!_satellite.readCookie('Elapsedp13467')) {
	//Divide non ML group in two groups for Usabilla
		if(!_satellite.readCookie('_sdsat_Ub')) {
			var groupFlag = RegExp(/true/).test(String(Math.random()<0.5));
			if (groupFlag == true) {
	    		_satellite.setCookie('_sdsat_Ub', 'b_a');
	  		} else {
	   			_satellite.setCookie('_sdsat_Ub', 'b_b');
	  		}
		} else {
			//Or prolong cookie (not usefull with session cookie, might change to longer)
			var groupCode = _satellite.readCookie('_sdsat_Ub');
			_satellite.setCookie('_sdsat_Ub', groupCode);
		}
} else {
	_satellite.setCookie('_sdsat_Ub', 'a_a');
}

//Add Usabilla default script if script ids are available
if(ubDesk != 'missing' && ubMob != 'missing') {
	window.lightningjs||function(c){function g(b,d){d&&(d+=(/\?/.test(d)?"&":"?")+"lv=1");c[b]||function(){var i=window,h=document,j=b,g=h.location.protocol,l="load",k=0;(function(){function b(){a.P(l);a.w=1;c[j]("_load")}c[j]=function(){function m(){m.id=e;return c[j].apply(m,arguments)}var b,e=++k;b=this&&this!=i?this.id||0:0;(a.s=a.s||[]).push([e,b,arguments]);m.then=function(b,c,h){var d=a.fh[e]=a.fh[e]||[],j=a.eh[e]=a.eh[e]||[],f=a.ph[e]=a.ph[e]||[];b&&d.push(b);c&&j.push(c);h&&f.push(h);return m};return m};var a=c[j]._={};a.fh={};a.eh={};a.ph={};a.l=d?d.replace(/^\/\//,(g=="https:"?g:"http:")+"//"):d;a.p={0:+new Date};a.P=function(b){a.p[b]=new Date-a.p[0]};a.w&&b();i.addEventListener?i.addEventListener(l,b,!1):i.attachEvent("on"+l,b);var q=function(){function b(){return["<head></head><",c,' onload="var d=',n,";d.getElementsByTagName('head')[0].",d,"(d.",g,"('script')).",i,"='",a.l,"'\"></",c,">"].join("")}var c="body",e=h[c];if(!e)return setTimeout(q,100);a.P(1);var d="appendChild",g="createElement",i="src",k=h[g]("div"),l=k[d](h[g]("div")),f=h[g]("iframe"),n="document",p;k.style.display="none";e.insertBefore(k,e.firstChild).id=o+"-"+j;f.frameBorder="0";f.id=o+"-frame-"+j;/MSIE[ ]+6/.test(navigator.userAgent)&&(f[i]="javascript:false");f.allowTransparency="true";l[d](f);try{f.contentWindow[n].open()}catch(s){a.domain=h.domain,p="javascript:var d="+n+".open();d.domain='"+h.domain+"';",f[i]=p+"void(0);"}try{var r=f.contentWindow[n];r.write(b());r.close()}catch(t){f[i]=p+'d.write("'+b().replace(/"/g,String.fromCharCode(92)+'"')+'");d.close();'}a.P(2)};a.l&&setTimeout(q,0)})()}();c[b].lv="1";return c[b]}var o="lightningjs",k=window[o]=g(o);k.require=g;k.modules=c}({});
    if(!navigator.userAgent.match(/Android|BlackBerry|BB10|iPhone|iPad|iPod|Opera Mini|IEMobile/i)) {window.usabilla_live = lightningjs.require("usabilla_live", "//w.usabilla.com/" + ubDesk + ".js"); } else {window.usabilla_live = lightningjs.require("usabilla_live", "//w.usabilla.com/" + ubMob + ".js"); }
}

//Check for groups to hide button and/or trigger surveys
if(_satellite.readCookie('_sdsat_Ub') === 'a_a') {
	window.usabilla_live('hide');
	window.usabilla_live("setCampaign", {}, [], true);
} else if(_satellite.readCookie('_sdsat_Ub') === 'b_a') {
	if(_satellite.getVar('dlSection') !== 'saeco_page') {
		usabilla_live('hide');
		window.usabilla_live('trigger', 'short-survey');
	} else {
		window.usabilla_live("setCampaign", {}, [], true);
	}
} else if(_satellite.readCookie('_sdsat_Ub') === 'b_b') {
	if(_satellite.getVar('dlSection') !== 'saeco_page') {
		usabilla_live('hide');
		window.usabilla_live('trigger', 'second-survey');
	} else {
		window.usabilla_live("setCampaign", {}, [], true);
	}
}

//Process data for Usabilla object
//survey_vendor
if(_satellite.readCookie('_sdsat_Ub') === 'a_a') {
	var ubVendor = 'MetrixLab';
} else {
	var ubVendor = 'Usabilla';
}

//catalog variables
if(philips.context.productCatalog !== undefined) {
	var ubCatalogtype = philips.context.productCatalog.catalogType;
	var ubProductGroup = philips.context.productCatalog.productGroup;
	var ubProductCategory = philips.context.productCatalog.productCategory;
	var ubProductSubCategory = philips.context.productCatalog.productSubCategory;
	var ubCTN = philips.context.productCatalog.ctn != undefined ? philips.context.productCatalog.ctn : '';
} else {
	var ubCatalogtype = '';
	var ubProductGroup = '';
	var ubProductCategory = '';
	var ubProductSubCategory = '';
	var ubCTN = '';
}

if($('img#dot-indicator').parent().attr('title') || window.usabilla_live) {
    var testId = $('img#dot-indicator').parent().attr('title');
} else {
  	var testId = '';
}

//Make data available for Usabilla
window.usabilla_live("data",{
    "custom":{
        "survey_vendor":ubVendor,
        "country":_satellite.getVar('dlCountry') != undefined ? _satellite.getVar('dlCountry') : '',
        "language":_satellite.getVar('dlLanguage') != undefined ? _satellite.getVar('dlLanguage') : '',
        "pageName":s.pageName,
        "pageType":_satellite.getVar('dlPageType') != undefined ? _satellite.getVar('dlPageType') : '',	
        "section":_satellite.getVar('dlSection') != undefined ? _satellite.getVar('dlSection') : '',
        "catalogType":ubCatalogtype,
        "productGroup":ubProductGroup,
        "productCategory":ubProductCategory,
        "productSubCategory":ubProductSubCategory,
        "CTN":ubCTN,
        "sector":_satellite.getVar('dlSector') != undefined ? _satellite.getVar('dlSector') : '',
      	 "test_id": testId
    }
})

//Define eVar66
usabilla_live("configureAdobeAnalytics", { 
    preTrackCallback: function(s, type,event,data) { 
        if (type == 'feedback' && data.button_id) {
            s.eVar66 = 'button.' + data.button_id;
        } else if (type == 'campaign' && data._campaign_id) {
            s.eVar66 = 'campaign.' + data._campaign_id;
        }
    }
});

$(document).trigger('dtm:usabilla_ready');
});
