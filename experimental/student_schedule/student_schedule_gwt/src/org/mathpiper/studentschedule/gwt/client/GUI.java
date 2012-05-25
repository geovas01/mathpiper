package org.mathpiper.studentschedule.gwt.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.DecoratorPanel;

public class GUI implements EntryPoint {

    private final StudentScheduleServiceAsync studentScheduleService = GWT
	    .create(StudentScheduleService.class);

    private List<TextBox> coursesList = new ArrayList<TextBox>();

    private ListBox comboBox_1;

    @Override
    public void onModuleLoad() {
	// TODO Auto-generated method stub

	RootPanel rootPanel = RootPanel.get();

	final DecoratedTabPanel tabPanel = new DecoratedTabPanel();
	rootPanel.add(tabPanel, 10, 10);
	tabPanel.setSize("700", "537px");

	VerticalPanel verticalPanel = new VerticalPanel();
	tabPanel.add(verticalPanel, "Enter Courses", false);
	verticalPanel.setSize("490px", "328px");

	DecoratorPanel decoratorPanel_1 = new DecoratorPanel();
	decoratorPanel_1.setStyleName("border");
	verticalPanel.add(decoratorPanel_1);

	FlexTable flexTable = new FlexTable();
	decoratorPanel_1.setWidget(flexTable);
	flexTable.setBorderWidth(0);
	flexTable.setSize("181px", "116px");

	Label lblNewLabel_1 = new Label("Course Numbers");
	flexTable.setWidget(0, 0, lblNewLabel_1);

	Label lblGep = new Label("GEP");
	flexTable.setWidget(0, 1, lblGep);

	TextBox textBox0 = new TextBox();
	textBox0.setText("ETCO1120");
	flexTable.setWidget(1, 0, textBox0);
	coursesList.add(textBox0);

	ListBox comboBox = new ListBox();
	comboBox.addItem("None");
	comboBox.addItem("Fine Arts");
	flexTable.setWidget(1, 1, comboBox);

	TextBox textBox1 = new TextBox();
	textBox1.setText("ETEM1110");
	flexTable.setWidget(2, 0, textBox1);
	coursesList.add(textBox1);

	TextBox textBox2 = new TextBox();
	textBox2.setText("MATH1300");
	flexTable.setWidget(3, 0, textBox2);
	coursesList.add(textBox2);

	TextBox textBox3 = new TextBox();
	textBox3.setText("ENGL1101");
	flexTable.setWidget(4, 0, textBox3);
	coursesList.add(textBox3);

	TextBox textBox4 = new TextBox();
	textBox4.setText("ARTH1101");
	flexTable.setWidget(5, 0, textBox4);
	coursesList.add(textBox4);

	TextBox textBox5 = new TextBox();
	flexTable.setWidget(6, 0, textBox5);
	coursesList.add(textBox5);

	DecoratorPanel decoratorPanel = new DecoratorPanel();
	decoratorPanel.setStyleName("border");
	decoratorPanel.addStyleName("border");
	verticalPanel.add(decoratorPanel);

	FlexTable flexTable_1 = new FlexTable();
	decoratorPanel.setWidget(flexTable_1);
	flexTable_1.setBorderWidth(0);

	Label lblNumberOfSchedules = new Label("Number Of Schedules");
	flexTable_1.setWidget(0, 0, lblNumberOfSchedules);

	comboBox_1 = new ListBox();
	comboBox_1.addItem("1");
	comboBox_1.addItem("2");
	comboBox_1.addItem("3");
	comboBox_1.addItem("4");
	comboBox_1.addItem("5");
	comboBox_1.addItem("10");
	comboBox_1.addItem("20");
	flexTable_1.setWidget(0, 1, comboBox_1);

	DecoratorPanel decoratorPanel_2 = new DecoratorPanel();
	decoratorPanel_2.setStyleName("border");
	verticalPanel.add(decoratorPanel_2);
	decoratorPanel_2.setWidth("223px");

	VerticalPanel verticalPanel_1 = new VerticalPanel();
	decoratorPanel_2.setWidget(verticalPanel_1);
	verticalPanel_1.setWidth("213px");

	Label lblPreferences = new Label("Preferences");
	verticalPanel_1.add(lblPreferences);

	FlexTable flexTable_2 = new FlexTable();
	verticalPanel_1.add(flexTable_2);

	Label lblTime = new Label("Time");
	flexTable_2.setWidget(0, 0, lblTime);

	ListBox comboBox_2 = new ListBox();
	comboBox_2.addItem("None");
	comboBox_2.addItem("Morning");
	comboBox_2.addItem("Afternoon");
	comboBox_2.addItem("Evening");
	flexTable_2.setWidget(0, 1, comboBox_2);

	Button btnSubmit = new Button("Submit");
	verticalPanel.add(btnSubmit);
	btnSubmit.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		studentScheduleService.findSchedules(toClojure(),
			new AsyncCallback<String>() {
			    public void onFailure(Throwable caught) {
				Window.alert("Failure: " + caught.getMessage());
			    }

			    public void onSuccess(String result) {
				// Window.alert("Success!: " + result);
				tabPanel.add(new HTML(result), "foo");

			    }
			});

	    }
	});

	tabPanel.selectTab(0);

    }// end method

    private String toClojure() {
	StringBuilder sb = new StringBuilder();

	sb.append("{:course-lists ");

	sb.append("[");

	for (TextBox textBox : coursesList) {
	    String courseNumber = textBox.getText().trim();

	    if (!courseNumber.equals("")) {

		sb.append(" [:");

		sb.append(courseNumber);

		sb.append("]");
	    }
	}

	sb.append("] ");

	sb.append(":return-number ");
	sb.append(comboBox_1.getValue(comboBox_1.getSelectedIndex()));

	sb.append("}");

	return sb.toString();

    }
}
