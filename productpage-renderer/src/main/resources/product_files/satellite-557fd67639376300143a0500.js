_satellite.pushAsyncScript(function(event, target, $variables){
  //content pages
if(_satellite.getVar('dlPageType') == 'content'){
	var pageName = ['garden-wall-and-post-lights','flexible-lighting-solutions','ambience-lighting','basement-lighting','lighting-for-elderly','warm-white-led-bulbs','energy-saving-led-bulbs','led-daylight-bulbs','Philips-Choose_a_lamp','led-lights','warm-led-light','eco-friendly-led-light','quality-of-light-led-lighting','dimmable-led']
	if(_satellite.getVar('dlPageName').indexOf(pageName)){
		//click on product div except buy widgets
		$(document).on('click','div.p-card',function(e) {
			if($(e.target).is('a.p-button')){
				e.preventDefault();
				return;
			}	
			var productId = $(this).find('div.p-product-id').text().trim();
			philips.analytics.trackConversion ({ 
			name:"interaction",
			description:"productbox_" + productId	
			});
		});
		//click on view product link
		$(document).on('click','span.p-link-more.p-view-product',function(e){
			
			var productId = $(this).siblings('div.p-product-id').text().trim();
			philips.analytics.trackConversion ({ 
			name:"interaction",
			description:"productbox_" + productId	
			});
		
		});
		//click on compare check-box
		$(document).on('change','div.p-checkbox', function(){
			var productId = $(this).parent().parent().parent().find('div.p-product-id').text().trim();
			philips.analytics.trackConversion ({ 
			name:"interaction",
			description:"compare_checked_" + productId	
			});
		});
	}	
}

//category pages
else if(_satellite.getVar('dlPageType') == 'category'){
	var productCategory = ['led_energy_saving_lamps','energy_saving_lamps','halogen_light_bulbs','choose_your_luminaire'];
	if(_satellite.getVar('dlProductCategory').indexOf(productCategory)){
		//click on product div except buy widgets
		$(document).on('click','div.p-card',function(e) {
			if($(e.target).is('a.p-button')){
				e.preventDefault();
				return;
			}	
			var productId = $(this).find('div.p-product-id').text().trim();
			philips.analytics.trackConversion ({ 
			name:"interaction",
			description:"view_product_" + productId	
			});
		});
		//click on view product link
		$(document).on('click','span.p-link-more.p-view-product',function(e) {
		
			var productId = $(this).siblings('div.p-product-id').text().trim();
			philips.analytics.trackConversion ({ 
			name:"interaction",
			description:"view_product_" + productId	
			});
		
		});
	}	
}
});
