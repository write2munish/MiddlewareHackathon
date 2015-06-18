_satellite.pushAsyncScript(function(event, target, $variables){
  if($('aside.p-st03-support-contact-details .p-info .p-feedback').length){
	if(window.usabilla_live) {
		$('aside.p-st03-support-contact-details .p-info .p-feedback').click(function(){window.usabilla_live('click')});
	} else {
		$('aside.p-st03-support-contact-details .p-info .p-feedback').css({'display':'none'});
    $(document).on('dtm:usabilla_ready', function(){
      $('aside.p-st03-support-contact-details .p-info .p-feedback').click(function(){window.usabilla_live('click')});
			$('aside.p-st03-support-contact-details .p-info .p-feedback').css({'display':'block'});
		});
	}
}
});
