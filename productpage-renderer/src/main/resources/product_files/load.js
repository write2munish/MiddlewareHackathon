/** Includes Eloqua and ignitionone * */
$(document).ready(
    function() {
        if ((philips.context.tracking.eloqua.load || philips.context.tracking.eloqua.load === 'default')
            && (philips.context.tracking.eloqua.siteid > 0)) {
            philips.utilities.loadScript("/b-etc/philips/clientlibs/foundation-base/clientlibs/js/philips/tracking/eloqua.js");
        }
        if ((philips.context.tracking.ignitionone.load || philips.context.tracking.ignitionone.load === 'default')
            && (philips.context.tracking.ignitionone.aid > 0)) {
            philips.utilities.loadScript("/b-etc/philips/clientlibs/foundation-base/clientlibs/js/philips/tracking/ignitionOne.js");
        }
        if ((philips.context.tracking.usabilla.load || philips.context.tracking.usabilla.load === 'default')
            && (philips.context.tracking.usabilla.mvalue) && (philips.context.tracking.usabilla.dvalue)) {
            philips.utilities.loadScript("/b-etc/philips/clientlibs/foundation-base/clientlibs/js/philips/tracking/usabilla.js");
        }
    });