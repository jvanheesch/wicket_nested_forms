package com.github.jvanheesch.forms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.UUID;

public class SimpleNestedFormsRawInputExamplePanel extends Panel {
    private static final long serialVersionUID = -6135921423666072529L;

    private static final Logger LOG = LogManager.getLogger();

    public SimpleNestedFormsRawInputExamplePanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<?> form = new Form<Void>("outerForm") {
            private static final long serialVersionUID = -6270065321817391546L;

            @Override
            protected void onSubmit() {
                super.onSubmit();

                LOG.info("Submitted outerForm");
            }
        };

        this.add(form);
        form.add(new AjaxLink<Serializable>("clearOuterFormInput") {
            private static final long serialVersionUID = 5188134882521490454L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                form.clearInput();
                target.add(form);
            }
        });
        form.add(new AjaxSubmitLink("submitOuterForm") {
            private static final long serialVersionUID = 5188134882521490454L;
            {
                this.setBody(Model.of("submitOuterForm"));
            }
        });

        form.add(new TextField<>("textField", Model.of(UUID.randomUUID().toString())));

        Form<?> nestedForm = new Form<Void>("nestedForm") {
            private static final long serialVersionUID = 4754278116243135912L;

            @Override
            protected void onSubmit() {
                super.onSubmit();

                LOG.info("Submitted nestedForm");
            }
        };
        form.add(nestedForm);

        nestedForm.add(new AjaxSubmitLink("submitNestedForm") {
            private static final long serialVersionUID = 5188134882521490454L;
            {
                this.setBody(Model.of("submitNestedForm"));
            }
        });
        nestedForm.add(new TextField<>("nestedTextField", Model.of(UUID.randomUUID().toString())));
    }
}
