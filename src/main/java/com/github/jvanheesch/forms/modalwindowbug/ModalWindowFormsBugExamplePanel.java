package com.github.jvanheesch.forms.modalwindowbug;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.Arrays;

public class ModalWindowFormsBugExamplePanel extends Panel {
    private static final long serialVersionUID = -365557371654493298L;

    private static final Logger LOG = LogManager.getLogger();

    public ModalWindowFormsBugExamplePanel(String id) {
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
        form.add(new TextField<>("textField", Model.of("InitModelObject")));

        DropDownChoice<String> dropDownChoice = new DropDownChoice<>("dropDownChoice", Model.of("initValueDropdown"), Model.ofList(Arrays.asList("noValueIsSelectedCheckHtml", "initValueDropdown")));
        form.add(dropDownChoice);


        form.add(new AjaxSubmitLink("submitOuterForm") {
            private static final long serialVersionUID = 5188134882521490454L;

            {
                this.setBody(Model.of("submitOuterForm"));
            }
        });

        ModalWindow modalWindow = new ModalWindow("modalWindow");
        form.add(modalWindow);
        modalWindow.setContent(new ModalWindowContentPanel(modalWindow.getContentId()));

        form.add(new AjaxLink<Serializable>("showModalWindow") {
            private static final long serialVersionUID = 7534589342960848879L;

            {
                this.setBody(Model.of("showModalWindow"));
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                modalWindow.show(target);
            }
        });

        this.setOutputMarkupId(true);
    }

    private static final class ModalWindowContentPanel extends Panel {
        private static final long serialVersionUID = 3919584400271895240L;

        ModalWindowContentPanel(String id) {
            super(id);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            Form<?> form = new Form<Void>("form") {
                private static final long serialVersionUID = -6270065321817391546L;

                @Override
                protected void onSubmit() {
                    super.onSubmit();

                    LOG.info("Submitted form in ModalWindowContentPanel");
                }
            };
            this.add(form);

            form.add(new AjaxSubmitLink("submitForm") {
                private static final long serialVersionUID = 5188134882521490454L;

                {
                    this.setBody(Model.of("Submit form, close modalwindow and refresh ModalWindowFormsBugExamplePanel."));
                }

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    super.onSubmit(target, form);

                    this.findParent(ModalWindow.class).close(target);

                    target.add(this.findParent(ModalWindowFormsBugExamplePanel.class));
                }
            });
        }
    }
}
