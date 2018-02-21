package com.github.jvanheesch;

import com.github.jvanheesch.forms.SimpleNestedFormsRawInputExamplePanel;
import com.github.jvanheesch.forms.modalwindowbug.ModalWindowFormsBugExamplePanel;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
    private static final long serialVersionUID = -4628714388032191363L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new SimpleNestedFormsRawInputExamplePanel("panel"));

        this.add(new ModalWindowFormsBugExamplePanel("modalWindowFormsBugExamplePanel"));
    }
}
