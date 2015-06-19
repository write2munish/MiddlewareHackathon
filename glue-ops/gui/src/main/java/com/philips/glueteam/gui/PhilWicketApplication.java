package com.philips.glueteam.gui;

import com.philips.glueteam.gui.pages.FrontPage;
import de.agilecoders.wicket.core.Bootstrap;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Created by mikke on 17-06-2015.
 */
public class PhilWicketApplication extends WebApplication {
    public PhilWicketApplication() {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class getHomePage() {
        return FrontPage.class;
    }

    @Override
    protected void init() {
        Bootstrap.install(this);
        super.init();
    }
}
