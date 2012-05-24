package org.mathpiper.studentschedule.gwt.client;

import java.io.IOException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.HTML;


public class GUI implements EntryPoint {
    
    private final StudentScheduleServiceAsync studentScheduleService = GWT
	    .create(StudentScheduleService.class);

    @Override
    public void onModuleLoad() {
	// TODO Auto-generated method stub
	

	
	RootPanel rootPanel = RootPanel.get();
	
	final TabPanel tabPanel = new TabPanel();
	rootPanel.add(tabPanel, 10, 10);
	tabPanel.setSize("555px", "380px");
	
	VerticalPanel verticalPanel = new VerticalPanel();
	tabPanel.add(verticalPanel, "Enter Courses", false);
	verticalPanel.setSize("490px", "328px");
	
	Label lblNewLabel = new Label("New label");
	verticalPanel.add(lblNewLabel);
	
	FlexTable flexTable = new FlexTable();
	verticalPanel.add(flexTable);
	flexTable.setSize("181px", "116px");
	
	Label lblNewLabel_1 = new Label("Course Numbers");
	flexTable.setWidget(0, 0, lblNewLabel_1);
	
	Label lblGep = new Label("GEP");
	flexTable.setWidget(0, 1, lblGep);
	
	TextBox textBox = new TextBox();
	flexTable.setWidget(1, 0, textBox);
	
	ListBox comboBox = new ListBox();
	comboBox.addItem("None");
	comboBox.addItem("Fine Arts");
	flexTable.setWidget(1, 1, comboBox);
	
	TextBox textBox_1 = new TextBox();
	flexTable.setWidget(2, 0, textBox_1);
	
	TextBox textBox_2 = new TextBox();
	flexTable.setWidget(3, 0, textBox_2);
	
	TextBox textBox_3 = new TextBox();
	flexTable.setWidget(4, 0, textBox_3);
	
	TextBox textBox_4 = new TextBox();
	flexTable.setWidget(5, 0, textBox_4);
	
	TextBox textBox_5 = new TextBox();
	flexTable.setWidget(6, 0, textBox_5);
	
	Button btnSubmit = new Button("Submit");
	btnSubmit.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
		studentScheduleService.findSchedules("",
			new AsyncCallback<String>() {
			    public void onFailure(Throwable caught) {
				Window.alert("Failure: " + caught.getMessage());
			    }

			    public void onSuccess(String result) {
				Window.alert("Success!: " + result);
				
				tabPanel.add(new HTML(result), "foo");
				
				
			    }
			});
		
		    Window.alert("Hello");
		}
	});
	flexTable.setWidget(7, 0, btnSubmit);
	
	HTML htmlNewHtml = new HTML("New HTML", true);
	tabPanel.add(htmlNewHtml, "New tab", false);
	htmlNewHtml.setSize("537px", "336px");
	
	
	tabPanel.selectTab(0);
	
	
    }
}
