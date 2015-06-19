package com.philips.glueteam.gui.pages;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;

/**
 * Created by mikke on 17-06-2015.
 */
public class FrontPage extends WebPage {
    public FrontPage() {
        Form form = new Form("form");
        add( form );
        form.add(new AjaxButton("dashboard", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                // repaint the feedback panel so that it is hidden
                System.out.println("Tada");
            }

        });
        form.add(new AjaxButton("scheduler", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                // repaint the feedback panel so that it is hidden
                System.out.println("Tada 222");
            }

        });
    }
}
