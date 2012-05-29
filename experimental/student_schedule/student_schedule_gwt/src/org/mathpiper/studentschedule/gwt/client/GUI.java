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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.CheckBox;

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

	HorizontalPanel horizontalPanel = new HorizontalPanel();
	verticalPanel.add(horizontalPanel);

	DecoratorPanel decoratorPanel_1 = new DecoratorPanel();
	horizontalPanel.add(decoratorPanel_1);
	decoratorPanel_1.setStyleName("border");

	FlexTable flexTable = new FlexTable();
	decoratorPanel_1.setWidget(flexTable);
	flexTable.setBorderWidth(0);
	flexTable.setSize("181px", "116px");

	Label lblNewLabel_1 = new Label("Course Numbers");
	flexTable.setWidget(0, 0, lblNewLabel_1);

	TextBox textBox0 = new TextBox();
	textBox0.setText("ETCO1120");
	flexTable.setWidget(1, 0, textBox0);
	coursesList.add(textBox0);

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
	flexTable.setWidget(5, 0, textBox4);
	coursesList.add(textBox4);

	TextBox textBox5 = new TextBox();
	flexTable.setWidget(6, 0, textBox5);
	coursesList.add(textBox5);

	DecoratorPanel gep_decorator_panel = new DecoratorPanel();
	horizontalPanel.add(gep_decorator_panel);

	FlexTable flexTable_3 = new FlexTable();
	gep_decorator_panel.setWidget(flexTable_3);

	Label lblFineArts = new Label("Fine Arts");
	flexTable_3.setWidget(0, 0, lblFineArts);

	Button btnAll = new Button("All");
	btnAll.setText("Check All");
	flexTable_3.setWidget(1, 0, btnAll);

	CheckBox chckbxNewCheckBox = new CheckBox("ARTH1101");
	chckbxNewCheckBox
		.setTitle("ARTH 1101 - Introduction to Art - Credits: 3 - The course is an introduction to the visual arts. It encompasses the world of western and non-western art. It deals with the principles of art, formal and contextual elements and the basic vocabulary necessary in order to articulate opinions about the arts. The course has a studio component that will allow the student hands on experience to encourage visual communication through the visual arts.");
	flexTable_3.setWidget(2, 0, chckbxNewCheckBox);

	CheckBox chckbxEngl = new CheckBox("ENGL2275");
	chckbxEngl
		.setTitle("ENGL 2275 - American Film History - Credits: 3 - Chronological study of the influence of American history upon American film, and vice versa. Students become acquainted with the work and themes of some of America’s significant film directors and major genres of American popular film. Prereq: ENGL 1105 or ENGL 1107.");
	flexTable_3.setWidget(3, 0, chckbxEngl);

	CheckBox chckbxMusi = new CheckBox("MUSI1201");
	chckbxMusi
		.setTitle("MUSI 1201 - Music Appreciation - Credits: 3 - A survey of musical highlights throughout history including pieces, composers, forms, styles, and performance media from the Fall of the Roman Empire to the emergence of the music video.");
	flexTable_3.setWidget(4, 0, chckbxMusi);

	CheckBox chckbxMusi_1 = new CheckBox("MUSI2211");
	chckbxMusi_1
		.setTitle("MUSI 2211 - Music History 1 -Credits: 3 - A detailed survey of music including pieces, composers, forms, styles, and performance media from the Fall of the Roman Empire through the Classical Period.");
	flexTable_3.setWidget(5, 0, chckbxMusi_1);

	CheckBox chckbxPhil = new CheckBox("PHIL3300");
	chckbxPhil
		.setTitle("PHIL 3300 - Philosophy and Film - Credits: 3 - Viewing, analysis, and interpretation of international and domestic films and their philosophical, aesthetic, and moral dimensions.");
	flexTable_3.setWidget(6, 0, chckbxPhil);

	CheckBox chckbxThar = new CheckBox("THAR1000");
	chckbxThar
		.setTitle("THAR 1000 - Introduction to Theater- Credits: 3 - Survey of development of theater from classical to modern times, emphasizing the artists and craftspersons of the theater and their contributions to its development.");
	flexTable_3.setWidget(7, 0, chckbxThar);

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
