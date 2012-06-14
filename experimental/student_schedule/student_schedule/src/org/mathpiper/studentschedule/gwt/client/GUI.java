package org.mathpiper.studentschedule.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

import org.mathpiper.studentschedule.gwt.shared.ArgumentException;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;

public class GUI implements EntryPoint {

    private final StudentScheduleServiceAsync studentScheduleService = GWT
	    .create(StudentScheduleService.class);

    private List<SuggestBox> coursesList = new ArrayList<SuggestBox>();

    private ListBox numberReturned;

    private ListBox timeComboBox;

    private int tabNumber = 1;

    final TabLayoutPanel tabPanel = new TabLayoutPanel(2.3, Unit.EM);

    private List<ScrollPanel> tabList = new ArrayList<ScrollPanel>();

    private MultiWordSuggestOracle suggestOracle = new MultiWordSuggestOracle();

    private RootLayoutPanel rootPanel;

    private Image processingImage = new Image("processing.gif");

    private HorizontalPanel horizontalPanel_1;

    private SimplePanel simplePanel;

    private final SuggestBox textBox0 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel00;

    private final SuggestBox textBox1 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel11;

    private final SuggestBox textBox2 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel22;

    private final SuggestBox textBox3 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel33;

    private final SuggestBox textBox4 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel44;

    private final SuggestBox textBox5 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel55;

    private ToggleButton pushButton0;
    private ToggleButton pushButton1;
    private ToggleButton pushButton2;
    private ToggleButton pushButton3;
    private ToggleButton pushButton4;
    private ToggleButton pushButton5;

    private CheckBox minimizeDaysCheckBox;

    private CheckBox chckbxM;
    private CheckBox chckbxT;
    private CheckBox chckbxW;
    private CheckBox chckbxR;
    private CheckBox chckbxF;

    private ListBox comboBox;
    private ListBox comboBox_2;

    @Override
    public void onModuleLoad() {

	studentScheduleService.courseList(new AsyncCallback<String>() {
	    public void onFailure(Throwable caught) {
		Window.alert("Failure: " + caught.getMessage());
	    }

	    public void onSuccess(String result) {
		String[] courses = result.split(",");
		for (String course : courses) {
		    suggestOracle.add(course);
		}

	    }
	});

	rootPanel = RootLayoutPanel.get();

	DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
	rootPanel.add(dockLayoutPanel);

	VerticalPanel verticalPanel = new VerticalPanel();
	verticalPanel.setSpacing(5);
	
	HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
	dockLayoutPanel.addNorth(horizontalPanel_3, 3.7);
	
		Button btnNewButton = new Button("Help");
		horizontalPanel_3.add(btnNewButton);
		horizontalPanel_3.setCellVerticalAlignment(btnNewButton, HasVerticalAlignment.ALIGN_MIDDLE);
		// verticalPanel_2.setHeight("53px");

		HTML htmlNewHtml = new HTML(
			"<h2>&nbsp;&nbsp;&nbsp;SSU Student Schedule Generator v.006 beta<h2>", false);
		horizontalPanel_3.add(htmlNewHtml);
		htmlNewHtml.setStyleName("none");
		htmlNewHtml.setDirectionEstimator(true);
		btnNewButton.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
			Window.open(GWT.getHostPageBaseURL() + "help/help.html",
				"_blank", "");
		    }
		});
	// tabPanel.addStyleName("gwt-TabLayoutPanel .gwt-TabLayoutPanelTabs");
	dockLayoutPanel.add(tabPanel);

	tabPanel.add(new ScrollPanel(verticalPanel), "Enter Courses", false);
	verticalPanel.setWidth("477px");

	HorizontalPanel horizontalPanel = new HorizontalPanel();
	verticalPanel.add(horizontalPanel);

	DecoratorPanel decoratorPanel_1 = new DecoratorPanel();
	horizontalPanel.add(decoratorPanel_1);
	decoratorPanel_1.setWidth("452px");

	decoratorPanel_1.setStyleName("border");

	FlexTable courseNumbersFlexTable = new FlexTable();
	// flexTable.setStyleName("FlexTable");

	decoratorPanel_1.setWidget(courseNumbersFlexTable);
	courseNumbersFlexTable.setBorderWidth(0);
	courseNumbersFlexTable.setSize("445px", "116px");

	Label lblNewLabel_1 = new Label("Course Numbers");
	lblNewLabel_1.addStyleName("labelBold");
	courseNumbersFlexTable.setWidget(0, 0, lblNewLabel_1);

	// Suggest box 0.
	textBox0.setText("ETCO1120");
	textBox0.setLimit(10);
	HorizontalPanel horizontalPanel0 = new HorizontalPanel();
	horizontalPanel0.add(textBox0);
	pushButton0 = new ToggleButton("Sections");
	pushButton0
		.setTitle("Show the open sections that are being offered for this course.");
	pushButton0.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {

		if (textBox0.getValue().equals("")) {
		    return;
		}

		ToggleButton button = (ToggleButton) event.getSource();

		if (button.isDown() == true) {

		    studentScheduleService.getSections(":"
			    + cleanCourseText(textBox0.getValue()),
			    new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
				    if (caught instanceof ArgumentException) {
					Window.alert(caught.getMessage());
				    } else {
					Window.alert("Error :"
						+ caught.getMessage());
				    }
				    pushButton0.setDown(false);
				}

				public void onSuccess(String result) {

				    String[] sections = result.split(",");
				    for (String section : sections) {
					CheckBox checkBox = new CheckBox(
						section);
					checkBox.setValue(true);
					checkBox.setWordWrap(false);
					horizontalPanel00.add(checkBox);
				    }

				}
			    });
		} else {
		    horizontalPanel00.clear();
		}
	    }
	});
	horizontalPanel0.add(pushButton0);
	horizontalPanel0.setCellVerticalAlignment(pushButton0,
		HasVerticalAlignment.ALIGN_MIDDLE);
	courseNumbersFlexTable.setWidget(1, 0, horizontalPanel0);
	textBox0.setWidth("90px");
	coursesList.add(textBox0);

	horizontalPanel00 = new HorizontalPanel();
	horizontalPanel0.add(horizontalPanel00);
	horizontalPanel0.setCellVerticalAlignment(horizontalPanel00,
		HasVerticalAlignment.ALIGN_MIDDLE);
	textBox0.getTextBox().addFocusHandler(new FocusHandler() {
	    @Override
	    public void onFocus(FocusEvent event) {
		horizontalPanel00.clear();
		pushButton0.setDown(false);
	    }
	});
	/*
	 * textBox0.addKeyPressHandler(new KeyPressHandler() { public void
	 * onKeyPress(KeyPressEvent event) {
	 * 
	 * if(event.getCharCode()== 8 || event.getCharCode()== 127) { return; }
	 * 
	 * if (textBox0.getText().length() >= 8) {
	 * textBox0.getTextBox().cancelKey(); } } });
	 */

	// Suggest box 1.
	textBox1.setText("ETEM1110");
	textBox1.setLimit(10);
	HorizontalPanel horizontalPanel1 = new HorizontalPanel();
	horizontalPanel1.add(textBox1);
	pushButton1 = new ToggleButton("Sections");
	pushButton1
		.setTitle("Show the open sections that are being offered for this course.");
	pushButton1.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {

		if (textBox1.getValue().equals("")) {
		    return;
		}

		ToggleButton button = (ToggleButton) event.getSource();

		if (button.isDown() == true) {

		    studentScheduleService.getSections(":"
			    + cleanCourseText(textBox1.getValue()),
			    new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
				    if (caught instanceof ArgumentException) {
					Window.alert(caught.getMessage());
				    } else {
					Window.alert("Error :"
						+ caught.getMessage());
				    }
				    pushButton1.setDown(false);
				}

				public void onSuccess(String result) {

				    String[] sections = result.split(",");
				    for (String section : sections) {
					CheckBox checkBox = new CheckBox(
						section);
					checkBox.setValue(true);
					checkBox.setWordWrap(false);
					horizontalPanel11.add(checkBox);
				    }

				}
			    });
		} else {
		    horizontalPanel11.clear();
		}
	    }
	});
	horizontalPanel1.add(pushButton1);
	horizontalPanel1.setCellVerticalAlignment(pushButton1,
		HasVerticalAlignment.ALIGN_MIDDLE);
	courseNumbersFlexTable.setWidget(2, 0, horizontalPanel1);
	textBox1.setWidth("90px");
	coursesList.add(textBox1);

	horizontalPanel11 = new HorizontalPanel();
	horizontalPanel1.add(horizontalPanel11);
	horizontalPanel1.setCellVerticalAlignment(horizontalPanel11,
		HasVerticalAlignment.ALIGN_MIDDLE);
	textBox1.getTextBox().addFocusHandler(new FocusHandler() {
	    @Override
	    public void onFocus(FocusEvent event) {
		horizontalPanel11.clear();
		pushButton1.setDown(false);
	    }
	});

	// Suggest box 2.
	textBox2.setText("MATH1300");
	textBox2.setLimit(10);
	HorizontalPanel horizontalPanel2 = new HorizontalPanel();
	horizontalPanel2.add(textBox2);
	pushButton2 = new ToggleButton("Sections");
	pushButton2
		.setTitle("Show the open sections that are being offered for this course.");
	pushButton2.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {

		if (textBox2.getValue().equals("")) {
		    return;
		}

		ToggleButton button = (ToggleButton) event.getSource();

		if (button.isDown() == true) {

		    studentScheduleService.getSections(":"
			    + cleanCourseText(textBox2.getValue()),
			    new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
				    if (caught instanceof ArgumentException) {
					Window.alert(caught.getMessage());
				    } else {
					Window.alert("Error :"
						+ caught.getMessage());
				    }
				    pushButton2.setDown(false);
				}

				public void onSuccess(String result) {

				    String[] sections = result.split(",");
				    for (String section : sections) {
					CheckBox checkBox = new CheckBox(
						section);
					checkBox.setValue(true);
					checkBox.setWordWrap(false);
					horizontalPanel22.add(checkBox);
				    }

				}
			    });
		} else {
		    horizontalPanel22.clear();
		}
	    }
	});
	horizontalPanel2.add(pushButton2);
	horizontalPanel2.setCellVerticalAlignment(pushButton2,
		HasVerticalAlignment.ALIGN_MIDDLE);
	courseNumbersFlexTable.setWidget(3, 0, horizontalPanel2);
	textBox2.setWidth("90px");
	coursesList.add(textBox2);

	horizontalPanel22 = new HorizontalPanel();
	horizontalPanel2.add(horizontalPanel22);
	horizontalPanel2.setCellVerticalAlignment(horizontalPanel22,
		HasVerticalAlignment.ALIGN_MIDDLE);
	textBox2.getTextBox().addFocusHandler(new FocusHandler() {
	    @Override
	    public void onFocus(FocusEvent event) {
		horizontalPanel22.clear();
		pushButton2.setDown(false);
	    }
	});

	// Suggest box 3.
	textBox3.setText("ENGL1101");
	textBox3.setLimit(10);
	HorizontalPanel horizontalPanel3 = new HorizontalPanel();
	horizontalPanel3.add(textBox3);
	pushButton3 = new ToggleButton("Sections");
	pushButton3
		.setTitle("Show the open sections that are being offered for this course.");
	pushButton3.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {

		if (textBox3.getValue().equals("")) {
		    return;
		}

		ToggleButton button = (ToggleButton) event.getSource();

		if (button.isDown() == true) {

		    studentScheduleService.getSections(":"
			    + cleanCourseText(textBox3.getValue()),
			    new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
				    if (caught instanceof ArgumentException) {
					Window.alert(caught.getMessage());
				    } else {
					Window.alert("Error :"
						+ caught.getMessage());
				    }
				    pushButton3.setDown(false);
				}

				public void onSuccess(String result) {

				    String[] sections = result.split(",");
				    for (String section : sections) {
					CheckBox checkBox = new CheckBox(
						section);
					checkBox.setValue(true);
					checkBox.setWordWrap(false);
					horizontalPanel33.add(checkBox);
				    }

				}
			    });
		} else {
		    horizontalPanel33.clear();
		}
	    }
	});
	horizontalPanel3.add(pushButton3);
	horizontalPanel3.setCellVerticalAlignment(pushButton3,
		HasVerticalAlignment.ALIGN_MIDDLE);
	courseNumbersFlexTable.setWidget(4, 0, horizontalPanel3);
	textBox3.setWidth("90px");
	coursesList.add(textBox3);

	horizontalPanel33 = new HorizontalPanel();
	horizontalPanel3.add(horizontalPanel33);
	horizontalPanel3.setCellVerticalAlignment(horizontalPanel33,
		HasVerticalAlignment.ALIGN_MIDDLE);
	textBox3.getTextBox().addFocusHandler(new FocusHandler() {
	    @Override
	    public void onFocus(FocusEvent event) {
		horizontalPanel33.clear();
		pushButton3.setDown(false);
	    }
	});

	// Suggest box 4.
	// textBox0.setText("ETCO1120");
	textBox4.setLimit(10);
	HorizontalPanel horizontalPanel4 = new HorizontalPanel();
	horizontalPanel4.add(textBox4);
	pushButton4 = new ToggleButton("Sections");
	pushButton4
		.setTitle("Show the open sections that are being offered for this course.");
	pushButton4.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {

		if (textBox4.getValue().equals("")) {
		    return;
		}

		ToggleButton button = (ToggleButton) event.getSource();

		if (button.isDown() == true) {

		    studentScheduleService.getSections(":"
			    + cleanCourseText(textBox4.getValue()),
			    new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
				    if (caught instanceof ArgumentException) {
					Window.alert(caught.getMessage());
				    } else {
					Window.alert("Error :"
						+ caught.getMessage());
				    }
				    pushButton4.setDown(false);
				}

				public void onSuccess(String result) {

				    String[] sections = result.split(",");
				    for (String section : sections) {
					CheckBox checkBox = new CheckBox(
						section);
					checkBox.setValue(true);
					checkBox.setWordWrap(false);
					horizontalPanel44.add(checkBox);
				    }

				}
			    });
		} else {
		    horizontalPanel44.clear();
		}
	    }
	});
	horizontalPanel4.add(pushButton4);
	horizontalPanel4.setCellVerticalAlignment(pushButton4,
		HasVerticalAlignment.ALIGN_MIDDLE);
	courseNumbersFlexTable.setWidget(5, 0, horizontalPanel4);
	textBox4.setWidth("90px");
	coursesList.add(textBox4);

	horizontalPanel44 = new HorizontalPanel();
	horizontalPanel4.add(horizontalPanel44);
	horizontalPanel4.setCellVerticalAlignment(horizontalPanel44,
		HasVerticalAlignment.ALIGN_MIDDLE);
	textBox4.getTextBox().addFocusHandler(new FocusHandler() {
	    @Override
	    public void onFocus(FocusEvent event) {
		horizontalPanel44.clear();
		pushButton4.setDown(false);
	    }
	});

	// Suggest box 5.
	// textBox0.setText("ETCO1120");
	textBox5.setLimit(10);
	HorizontalPanel horizontalPanel5 = new HorizontalPanel();
	horizontalPanel5.add(textBox5);
	pushButton5 = new ToggleButton("Sections");
	pushButton5
		.setTitle("Show the open sections that are being offered for this course.");
	pushButton5.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {

		if (textBox5.getValue().equals("")) {
		    return;
		}

		ToggleButton button = (ToggleButton) event.getSource();

		if (button.isDown() == true) {

		    studentScheduleService.getSections(":"
			    + cleanCourseText(textBox5.getValue()),
			    new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
				    if (caught instanceof ArgumentException) {
					Window.alert(caught.getMessage());
				    } else {
					Window.alert("Error :"
						+ caught.getMessage());
				    }
				    pushButton5.setDown(false);
				}

				public void onSuccess(String result) {

				    String[] sections = result.split(",");
				    for (String section : sections) {
					CheckBox checkBox = new CheckBox(
						section);
					checkBox.setValue(true);
					checkBox.setWordWrap(false);
					horizontalPanel55.add(checkBox);
				    }

				}
			    });
		} else {
		    horizontalPanel55.clear();
		}
	    }
	});
	horizontalPanel5.add(pushButton5);
	horizontalPanel5.setCellVerticalAlignment(pushButton5,
		HasVerticalAlignment.ALIGN_MIDDLE);
	courseNumbersFlexTable.setWidget(6, 0, horizontalPanel5);
	textBox5.setWidth("90px");
	coursesList.add(textBox5);

	horizontalPanel55 = new HorizontalPanel();
	horizontalPanel5.add(horizontalPanel55);
	horizontalPanel5.setCellVerticalAlignment(horizontalPanel55,
		HasVerticalAlignment.ALIGN_MIDDLE);
	textBox5.getTextBox().addFocusHandler(new FocusHandler() {
	    @Override
	    public void onFocus(FocusEvent event) {
		horizontalPanel55.clear();
		pushButton5.setDown(false);
	    }
	});

	DecoratorPanel decoratorPanel = new DecoratorPanel();
	decoratorPanel.setStyleName("border");
	// decoratorPanel.addStyleName("border");
	verticalPanel.add(decoratorPanel);

	FlexTable configurationFlexTable = new FlexTable();
	decoratorPanel.setWidget(configurationFlexTable);
	configurationFlexTable.setBorderWidth(0);

	Label lblNumberOfSchedules = new Label(
		"Maximum Number Of Schedules To Generate");
	configurationFlexTable.setWidget(0, 0, lblNumberOfSchedules);

	numberReturned = new ListBox();
	numberReturned.addItem("1");
	numberReturned.addItem("2");
	numberReturned.addItem("3");
	numberReturned.addItem("4");
	numberReturned.addItem("5");
	numberReturned.addItem("10");
	numberReturned.addItem("20");
	configurationFlexTable.setWidget(0, 1, numberReturned);

	DecoratorPanel decoratorPanel_2 = new DecoratorPanel();
	decoratorPanel_2.setStyleName("border");
	verticalPanel.add(decoratorPanel_2);
	decoratorPanel_2.setWidth("390px");

	VerticalPanel verticalPanel_1 = new VerticalPanel();
	decoratorPanel_2.setWidget(verticalPanel_1);
	verticalPanel_1.setWidth("213px");

	FlexTable preferencesFlexTable = new FlexTable();
	// flexTable_2.addStyleName("FlexTable");
	// HTMLTable.RowFormatter rf = preferencesFlexTable.getRowFormatter();

	// for (int row = 1; row < courseNumbersFlexTable.getRowCount(); ++row)
	// {
	// rf.addStyleName(row, "FlexTable-Cell");
	// }
	verticalPanel_1.add(preferencesFlexTable);
	preferencesFlexTable.setSize("387px", "");

	Label lblPreference = new Label("Preference");
	preferencesFlexTable.setWidget(0, 0, lblPreference);

	Label lblValue = new Label("Value");
	preferencesFlexTable.setWidget(0, 1, lblValue);

	Label lblPriority = new Label("Priority");
	preferencesFlexTable.setWidget(0, 2, lblPriority);

	Label lblTime = new Label("Time");
	lblTime.addStyleName("labelBold");
	preferencesFlexTable.setWidget(1, 0, lblTime);

	timeComboBox = new ListBox();
	timeComboBox.addItem("None");
	timeComboBox.addItem("Morning");
	timeComboBox.addItem("Afternoon");
	timeComboBox.addItem("Evening");
	preferencesFlexTable.setWidget(1, 1, timeComboBox);

	comboBox = new ListBox();
	comboBox.addChangeHandler(new ChangeHandler() {
	    public void onChange(ChangeEvent event) {

		ListBox listBox = (ListBox) event.getSource();

		if (listBox.getValue(listBox.getSelectedIndex()).equals("1")) {
		    comboBox_2.setItemSelected(1, true);
		} else {
		    comboBox_2.setItemSelected(0, true);
		}
	    }
	});
	comboBox.addItem("1");
	comboBox.addItem("2");
	preferencesFlexTable.setWidget(1, 2, comboBox);
	preferencesFlexTable.getCellFormatter().setHorizontalAlignment(0, 1,
		HasHorizontalAlignment.ALIGN_CENTER);
	preferencesFlexTable.getCellFormatter().setHorizontalAlignment(0, 2,
		HasHorizontalAlignment.ALIGN_CENTER);
	preferencesFlexTable.getCellFormatter().setHorizontalAlignment(0, 0,
		HasHorizontalAlignment.ALIGN_CENTER);
	preferencesFlexTable.getCellFormatter().setHorizontalAlignment(1, 2,
		HasHorizontalAlignment.ALIGN_CENTER);
	preferencesFlexTable.getCellFormatter().setHorizontalAlignment(1, 1,
		HasHorizontalAlignment.ALIGN_CENTER);

	Label lblSelectDays = new Label("Select Days");
	lblSelectDays.addStyleName("labelBold");
	lblSelectDays.setWordWrap(false);
	preferencesFlexTable.setWidget(2, 0, lblSelectDays);

	HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
	preferencesFlexTable.setWidget(2, 1, horizontalPanel_2);

	chckbxM = new CheckBox("M");
	chckbxM.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		minimizeDaysCheckBox.setValue(false);
	    }
	});
	chckbxM.setHTML("M,");
	chckbxM.setValue(true);
	chckbxM.setWordWrap(false);
	horizontalPanel_2.add(chckbxM);

	chckbxT = new CheckBox("T");
	chckbxT.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		minimizeDaysCheckBox.setValue(false);
	    }
	});
	chckbxT.setHTML("T,");
	chckbxT.setValue(true);
	chckbxT.setWordWrap(false);
	horizontalPanel_2.add(chckbxT);

	chckbxW = new CheckBox("W");
	chckbxW.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		minimizeDaysCheckBox.setValue(false);
	    }
	});
	chckbxW.setHTML("W,");
	chckbxW.setValue(true);
	chckbxW.setWordWrap(false);
	horizontalPanel_2.add(chckbxW);

	chckbxR = new CheckBox("R");
	chckbxR.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		minimizeDaysCheckBox.setValue(false);
	    }
	});
	chckbxR.setHTML("R,");
	chckbxR.setValue(true);
	chckbxR.setWordWrap(false);
	horizontalPanel_2.add(chckbxR);

	chckbxF = new CheckBox("F");
	chckbxF.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		minimizeDaysCheckBox.setValue(false);
	    }
	});
	chckbxF.setHTML("F;");
	chckbxF.setValue(true);
	chckbxF.setWordWrap(false);
	horizontalPanel_2.add(chckbxF);

	minimizeDaysCheckBox = new CheckBox("New check box");
	minimizeDaysCheckBox.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {

		CheckBox checkBox = (CheckBox) event.getSource();

		if (checkBox.getValue() == true) {
		    clearDays();
		} else {
		    setDays();
		}
	    }
	});
	minimizeDaysCheckBox.setWordWrap(false);
	horizontalPanel_2.add(minimizeDaysCheckBox);
	minimizeDaysCheckBox.setHTML("Minimize");

	comboBox_2 = new ListBox();
	comboBox_2.addChangeHandler(new ChangeHandler() {
	    public void onChange(ChangeEvent event) {
		ListBox listBox = (ListBox) event.getSource();
		if (listBox.getValue(listBox.getSelectedIndex()).equals("1")) {
		    comboBox.setItemSelected(1, true);
		} else {
		    comboBox.setItemSelected(0, true);
		}
	    }
	});
	comboBox_2.addItem("1");
	comboBox_2.addItem("2");
	comboBox_2.setItemSelected(1, true);
	preferencesFlexTable.setWidget(2, 2, comboBox_2);
	preferencesFlexTable.getCellFormatter().setHorizontalAlignment(2, 2,
		HasHorizontalAlignment.ALIGN_CENTER);
	preferencesFlexTable.getCellFormatter().setHorizontalAlignment(2, 1,
		HasHorizontalAlignment.ALIGN_CENTER);
	preferencesFlexTable.getCellFormatter().setHorizontalAlignment(2, 2,
		HasHorizontalAlignment.ALIGN_CENTER);

	horizontalPanel_1 = new HorizontalPanel();
	horizontalPanel_1.setSpacing(2);
	verticalPanel.add(horizontalPanel_1);

	Button btnReset = new Button("Reset");
	btnReset.setTitle("Reset the application, but do not clear the course numbers.");
	btnReset.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		reset();
	    }
	});
	horizontalPanel_1.add(btnReset);

	Button btnResetAll = new Button("Reset All");
	btnResetAll.setTitle("Reset the application.");
	btnResetAll.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		resetAll();
	    }
	});
	horizontalPanel_1.add(btnResetAll);

	final Button btnCourseInfo = new Button("Sections Info");
	btnCourseInfo.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {

		btnCourseInfo.setEnabled(false);

		studentScheduleService.sectionsInformation(toClojure(),
			new AsyncCallback<String>() {
			    public void onFailure(Throwable caught) {

				btnCourseInfo.setEnabled(true);

				if (caught instanceof ArgumentException) {
				    Window.alert(caught.getMessage());
				} else {
				    Window.alert("Error :"
					    + caught.getMessage());
				}

			    }

			    public void onSuccess(String result) {
				// Window.alert("Success!: " + result);
				HTML html = new HTML(result);
				final Label tabLabel = new Label(""
					+ tabNumber++);
				tabLabel.setStyleName("newTab");
				tabLabel.setWidth("10px");

				ScrollPanel scrollPanel = new ScrollPanel(html);
				tabPanel.add(scrollPanel, tabLabel); // "" +
								     // tabNumber++);

				// tabPanel.add(html,tabLabel);

				tabList.add(scrollPanel);

				/*
				 * Scheduler.get().scheduleDeferred(new
				 * Scheduler.ScheduledCommand() { public void
				 * execute() { tabPanel.fireEvent(GwtEvent.) }
				 * });
				 */

				Timer blinkTimer = new Timer() {

				    private boolean toggle = true;

				    private int count = 6;

				    @Override
				    public void run() {
					if (toggle) {
					    tabLabel.setStyleName("newTab");
					    toggle = false;
					} else {
					    tabLabel.setStyleName("normalTab");
					    toggle = true;
					}

					count--;

					if (count == 0) {
					    cancel();
					}

				    }
				};

				blinkTimer.scheduleRepeating(600);
				// notificationMole.hide();
				btnCourseInfo.setEnabled(true);
			    }
			});

	    }
	});
	horizontalPanel_1.add(btnCourseInfo);

	final Button btnSubmit = new Button("Generate Schedule");
	btnSubmit.setTitle("Generate a schedule.");
	horizontalPanel_1.add(btnSubmit);

	simplePanel = new SimplePanel();
	horizontalPanel_1.add(simplePanel);
	horizontalPanel_1.setCellVerticalAlignment(simplePanel,
		HasVerticalAlignment.ALIGN_MIDDLE);
	simplePanel.setSize("49px", "29px");

	btnSubmit.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		// notificationMole.show();
		simplePanel.add(processingImage);

		btnSubmit.setEnabled(false);

		studentScheduleService.findSchedules(toClojure(),
			new AsyncCallback<String>() {
			    public void onFailure(Throwable caught) {

				simplePanel.remove(processingImage);

				btnSubmit.setEnabled(true);

				if (caught instanceof ArgumentException) {
				    Window.alert(caught.getMessage());
				} else {
				    Window.alert("Error :"
					    + caught.getMessage());
				}

			    }

			    public void onSuccess(String result) {
				// Window.alert("Success!: " + result);

				final HTML allTablesHtml = new HTML(
					"<iframe id=\"__printingFrame\" style=\"width:0;height:0;border:0\"></iframe>"
						+ result.replace("|", ""));
				final Label tabLabel = new Label(""
					+ tabNumber++);
				tabLabel.setStyleName("newTab");
				tabLabel.setWidth("10px");

				Button printAllButton = new Button("Print All");
				printAllButton
					.addClickHandler(new ClickHandler() {
					    public void onClick(ClickEvent event) {
						Print.it(allTablesHtml);
					    }
					});
				VerticalPanel verticalPanel = new VerticalPanel();
				verticalPanel.add(printAllButton);
				verticalPanel.add(new HTML("<br />"));

				String[] tables = result.split("\\|");

				for (String table : tables) {
				    final HTML tableHtml = new HTML(
					    "<iframe id=\"__printingFrame\" style=\"width:0;height:0;border:0\"></iframe>"
						    + table);
				    
				    
				    Button printButton = new Button("Print");
				    printButton
					    .addClickHandler(new ClickHandler() {
						public void onClick(
							ClickEvent event) {
						    Print.it(tableHtml);
						}
					    });
				    verticalPanel.add(new HTML("<hr /> "));
				    verticalPanel.add(printButton);
				    verticalPanel.add(tableHtml);
				    verticalPanel.add(new HTML("<br /> <br /> <br />"));
				}

				ScrollPanel scrollPanel = new ScrollPanel(
					verticalPanel);

				tabPanel.add(scrollPanel, tabLabel); // "" +
								     // tabNumber++);

				// tabPanel.add(html,tabLabel);

				tabList.add(scrollPanel);

				/*
				 * Scheduler.get().scheduleDeferred(new
				 * Scheduler.ScheduledCommand() { public void
				 * execute() { tabPanel.fireEvent(GwtEvent.) }
				 * });
				 */

				Timer blinkTimer = new Timer() {

				    private boolean toggle = true;

				    private int count = 6;

				    @Override
				    public void run() {
					if (toggle) {
					    tabLabel.setStyleName("newTab");
					    toggle = false;
					} else {
					    tabLabel.setStyleName("normalTab");
					    toggle = true;
					}

					count--;

					if (count == 0) {
					    cancel();
					}

				    }
				};

				blinkTimer.scheduleRepeating(600);
				// notificationMole.hide();
				simplePanel.remove(processingImage);
				btnSubmit.setEnabled(true);
			    }
			});

	    }
	});

	tabPanel.selectTab(0);

	// preferencesFlexTable.getRowFormatter().setStyleName(1,"watchList");

	preferencesFlexTable.getRowFormatter().addStyleName(0,
		"watchListHeader");

	for (int row = 0; row < preferencesFlexTable.getRowCount(); row++) {

	    for (int column = 0; column < preferencesFlexTable
		    .getCellCount(row); column++) {

		preferencesFlexTable.getCellFormatter().addStyleName(row,
			column, "border");

	    }
	}

	preferencesFlexTable.addStyleName("collapse");
	preferencesFlexTable.getCellFormatter().setHorizontalAlignment(1, 0,
		HasHorizontalAlignment.ALIGN_CENTER);
	preferencesFlexTable.getCellFormatter().setHorizontalAlignment(2, 0,
		HasHorizontalAlignment.ALIGN_CENTER);
	
	
	tabPanel.add(new ScrollPanel(makeGEPTree()), "GEP", false);

    }// end method

    private String toClojure() {
	StringBuilder sb = new StringBuilder();

	sb.append("{");

	// =========== :course-lists.

	sb.append(":course-lists ");

	sb.append("[");

	for (SuggestBox textBox : coursesList) {
	    String courseNumber = cleanCourseText(textBox.getText());

	    if (!courseNumber.equals("")) {

		sb.append(" [:");

		sb.append(courseNumber);

		sb.append("]");
	    }
	}// end for.

	sb.append("] ");

	sb.append(":return-number ");
	sb.append(numberReturned.getValue(numberReturned.getSelectedIndex()));

	// ===============================================================
	sb.append(" :quality-fn-and-vals [");

	StringBuilder dayTimeStringBuilder = null;
	String dayTimePreference = timeComboBox.getValue(
		timeComboBox.getSelectedIndex()).toLowerCase();
	if (!dayTimePreference.equals("none")) {
	    dayTimeStringBuilder = new StringBuilder();
	    dayTimeStringBuilder.append("[\"time-of-day-ratio-corrected\" [:");
	    dayTimeStringBuilder.append(dayTimePreference);
	    dayTimeStringBuilder.append(" 1]]");
	}

	// -----------------------------

	StringBuilder daysSB = null;

	if (minimizeDaysCheckBox.getValue() == true) {
	    daysSB = new StringBuilder();
	    daysSB.append("[\"minimize-days\" [] ]");
	} else if (!(chckbxM.getValue() == true && chckbxT.getValue() == true
		&& chckbxW.getValue() == true && chckbxR.getValue() == true && chckbxF
		    .getValue() == true)) {
	    // [\"choose-days\" [2r0101100]]
	    daysSB = new StringBuilder();

	    daysSB.append("[\"choose-days\" [2r");

	    if (chckbxM.getValue() == true) {
		daysSB.append("1");
	    } else {
		daysSB.append("0");
	    }
	    if (chckbxT.getValue() == true) {
		daysSB.append("1");
	    } else {
		daysSB.append("0");
	    }
	    if (chckbxW.getValue() == true) {
		daysSB.append("1");
	    } else {
		daysSB.append("0");
	    }
	    if (chckbxR.getValue() == true) {
		daysSB.append("1");
	    } else {
		daysSB.append("0");
	    }
	    if (chckbxF.getValue() == true) {
		daysSB.append("1");
	    } else {
		daysSB.append("0");
	    }

	    daysSB.append("00]]");
	}

	// Encode priority of the dayTime preference and the days preference.
	if (dayTimeStringBuilder != null && daysSB == null) {
	    sb.append(dayTimeStringBuilder.toString());
	} else if (dayTimeStringBuilder == null && daysSB != null) {
	    sb.append(daysSB.toString());
	} else if (dayTimeStringBuilder != null && daysSB != null) {
	    if (comboBox.getValue(comboBox.getSelectedIndex()).equals("1")) {
		sb.append(dayTimeStringBuilder.toString());
		sb.append(daysSB.toString());
	    } else {
		sb.append(daysSB.toString());
		sb.append(dayTimeStringBuilder.toString());
	    }
	}

	// ------------------------------

	sb.append("]"); // end quality-fn-and-vals
	// =============================

	// :quality-fn-and-vals [[time-of-day-ratio-corrected [:afternoon 1]]]

	sb.append(" :custom-courses {}");

	// =============== :selected-courses.

	sb.append(" :selected-courses");

	sb.append("[");

	// Course 0.
	if (horizontalPanel00.getWidgetCount() > 0) {
	    sb.append("{");
	    sb.append(":course-number :" + cleanCourseText(textBox0.getValue()));
	    sb.append(" :section-numbers [");

	    int widgetCount = horizontalPanel00.getWidgetCount();
	    for (int index = 0; index < widgetCount; index++) {
		CheckBox checkBox = (CheckBox) horizontalPanel00
			.getWidget(index);
		if (checkBox.getValue() == true) {
		    sb.append(" :");
		    sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");

	}

	// Course 1.
	if (horizontalPanel11.getWidgetCount() > 0) {
	    sb.append("{");
	    sb.append(":course-number :" + cleanCourseText(textBox1.getValue()));
	    sb.append(" :section-numbers [");

	    int widgetCount = horizontalPanel11.getWidgetCount();
	    for (int index = 0; index < widgetCount; index++) {
		CheckBox checkBox = (CheckBox) horizontalPanel11
			.getWidget(index);
		if (checkBox.getValue() == true) {
		    sb.append(" :");
		    sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");

	}

	// Course 2.
	if (horizontalPanel22.getWidgetCount() > 0) {
	    sb.append("{");
	    sb.append(":course-number :" + cleanCourseText(textBox2.getValue()));
	    sb.append(" :section-numbers [");

	    int widgetCount = horizontalPanel22.getWidgetCount();
	    for (int index = 0; index < widgetCount; index++) {
		CheckBox checkBox = (CheckBox) horizontalPanel22
			.getWidget(index);
		if (checkBox.getValue() == true) {
		    sb.append(" :");
		    sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");

	}

	// Course 3.
	if (horizontalPanel33.getWidgetCount() > 0) {
	    sb.append("{");
	    sb.append(":course-number :" + cleanCourseText(textBox3.getValue()));
	    sb.append(" :section-numbers [");

	    int widgetCount = horizontalPanel33.getWidgetCount();
	    for (int index = 0; index < widgetCount; index++) {
		CheckBox checkBox = (CheckBox) horizontalPanel33
			.getWidget(index);
		if (checkBox.getValue() == true) {
		    sb.append(" :");
		    sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");

	}

	// Course 4.
	if (horizontalPanel44.getWidgetCount() > 0) {
	    sb.append("{");
	    sb.append(":course-number :" + cleanCourseText(textBox4.getValue()));
	    sb.append(" :section-numbers [");

	    int widgetCount = horizontalPanel44.getWidgetCount();
	    for (int index = 0; index < widgetCount; index++) {
		CheckBox checkBox = (CheckBox) horizontalPanel44
			.getWidget(index);
		if (checkBox.getValue() == true) {
		    sb.append(" :");
		    sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");

	}

	// Course 5.
	if (horizontalPanel55.getWidgetCount() > 0) {
	    sb.append("{");
	    sb.append(":course-number :" + cleanCourseText(textBox5.getValue()));
	    sb.append(" :section-numbers [");

	    int widgetCount = horizontalPanel55.getWidgetCount();
	    for (int index = 0; index < widgetCount; index++) {
		CheckBox checkBox = (CheckBox) horizontalPanel55
			.getWidget(index);
		if (checkBox.getValue() == true) {
		    sb.append(" :");
		    sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");

	}

	sb.append("]");

	sb.append("}");

	return sb.toString();

    }

    private String cleanCourseText(String text) {
	text = text.trim();

	text = text.toUpperCase();

	if (text.length() > 8) {
	    text = text.substring(0, 8);
	}

	text = text.replace(" ", "");

	text = text.replace("(", ".");
	text = text.replace(")", ".");
	text = text.replace("[", ".");
	text = text.replace("]", ".");
	text = text.replace("{", ".");
	text = text.replace("}", ".");

	return text;
    }

    private void reset() {
	numberReturned.setItemSelected(0, true);
	timeComboBox.setItemSelected(0, true);
	tabNumber = 1;

	for (ScrollPanel html : tabList) {
	    tabPanel.remove(html);
	}

	tabList.clear();

	horizontalPanel00.clear();
	horizontalPanel11.clear();
	horizontalPanel22.clear();
	horizontalPanel33.clear();
	horizontalPanel44.clear();
	horizontalPanel55.clear();

	pushButton0.setDown(false);
	pushButton1.setDown(false);
	pushButton2.setDown(false);
	pushButton3.setDown(false);
	pushButton4.setDown(false);
	pushButton5.setDown(false);

	minimizeDaysCheckBox.setValue(false);

	comboBox.setItemSelected(0, true);
	comboBox_2.setItemSelected(1, true);

	setDays();

    }

    private void resetAll() {
	reset();

	textBox0.setText("");
	textBox1.setText("");
	textBox2.setText("");
	textBox3.setText("");
	textBox4.setText("");
	textBox5.setText("");

    }

    private void clearDays() {
	chckbxM.setValue(false);
	chckbxT.setValue(false);
	chckbxW.setValue(false);
	chckbxR.setValue(false);
	chckbxF.setValue(false);
    }

    private void setDays() {
	chckbxM.setValue(true);
	chckbxT.setValue(true);
	chckbxW.setValue(true);
	chckbxR.setValue(true);
	chckbxF.setValue(true);
    }
    
    
    
    private Tree makeGEPTree()
    {
	/*Tree tree = new Tree();
       /// initWidget(tree);
        TreeItem outerRoot = new TreeItem("Item 1");
        outerRoot.addItem("Item 1-1");
        outerRoot.addItem("Item 1-2");
        outerRoot.addItem("Item 1-3");
        outerRoot.addItem(new CheckBox("Item 1-4"));
        tree.addItem(outerRoot);

        TreeItem innerRoot = new TreeItem("Item 1-5");
        innerRoot.addItem("Item 1-5-1");
        innerRoot.addItem("Item 1-5-2");
        innerRoot.addItem("Item 1-5-3");
        innerRoot.addItem("Item 1-5-4");
        innerRoot.addItem(new CheckBox("Item 1-5-5"));
        outerRoot.addItem(innerRoot);*/
        
        Tree categories = new Tree();
        
        String descriptionWidth = "400px";
        int charactersPerLine = 38;
        
        
        
        
        //==================== Fine Arts.
        TreeItem fineArtsCategory = new TreeItem("Fine And Performing Arts (Select 3 Hours).");
        categories.addItem(fineArtsCategory);
        
        //ARTH1101.
        TreeItem ARTH1101 = new TreeItem("ARTH1101 - Introduction to Art.");
        TextArea textArea = new TextArea();
        textArea.setText("The course is an introduction to the visual arts. It encompasses the world of western and non-western art. It deals with the principles of art, formal and contextual elements and the basic vocabulary necessary in order to articulate opinions about the arts. The course has a studio component that will allow the student hands on experience to encourage visual communication through the visual arts. Credits: 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        ARTH1101.addItem(textArea);
        fineArtsCategory.addItem(ARTH1101);

        
        
        
        //ENGL2275.
        TreeItem ENGL2275 = new TreeItem("ENGL2275 - American Film History (Prereq: ENGL 1105 or ENGL 1107).");
        textArea = new TextArea();
        textArea.setText("Chronological study of the influence of American history upon American film, and vice versa. Students become acquainted with the work and themes of some of America’s significant film directors and major genres of American popular film. Credits: 3.");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        ENGL2275.addItem(textArea);
        fineArtsCategory.addItem(ENGL2275);

        

        
        //MUSI1201.
        TreeItem MUSI1201 = new TreeItem("MUSI1201 - Music Appreciation.");
        textArea = new TextArea();
        textArea.setText("A survey of musical highlights throughout history including pieces, composers, forms, styles, and performance media from the Fall of the Roman Empire to the emergence of the music video. Credits: 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        MUSI1201.addItem(textArea);
        fineArtsCategory.addItem(MUSI1201);
        
        
        
        
        //MUSI2211.
        TreeItem MUSI2211 = new TreeItem("MUSI2211 - Music History 1.");
        textArea = new TextArea();
        textArea.setText("A detailed survey of music including pieces, composers, forms, styles, and performance media from the Fall of the Roman Empire through the Classical Period. Credits: 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        MUSI2211.addItem(textArea);
        fineArtsCategory.addItem(MUSI2211);
        
        
        

        //PHIL3300.
        TreeItem PHIL3300 = new TreeItem("PHIL3300 - Philosophy and Film.");
        textArea = new TextArea();
        textArea.setText("Viewing, analysis, and interpretation of international and domestic films and their philosophical, aesthetic, and moral dimensions. Credits: 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        PHIL3300.addItem(textArea);
        fineArtsCategory.addItem(PHIL3300);
        
        
        
        
        //THAR1000.
        TreeItem THAR1000 = new TreeItem("THAR1000 - Introduction to Theater.");
        textArea = new TextArea();
        textArea.setText("Survey of development of theater from classical to modern times, emphasizing the artists and craftspersons of the theater and their contributions to its development. Credits 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        THAR1000.addItem(textArea);
        fineArtsCategory.addItem(THAR1000);
        
        
        
        
        
        
        //==================== Social Sciences.
        TreeItem socialSciencesCategory = new TreeItem("Social Sciences (Select 3 Hours).");
        categories.addItem(socialSciencesCategory);
        
        //ANTH3350.
        TreeItem ANTH3350 = new TreeItem("ANTH3350 - Biological Anthropology.");
        textArea = new TextArea();
        textArea.setText("This course provides an introduction to the basic concepts and principles of Biological Anthropology.  Topics to be covered include evolution in general, primate and human evolution, and genetics of human populations. 3 lecture hours 1 lab hours. Credits: 4");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        ANTH3350.addItem(textArea);
        socialSciencesCategory.addItem(ANTH3350);

        
        
        
        //GOVT2250.
        TreeItem GOVT2250 = new TreeItem("GOVT2250 - Intro to Political Science.");
        textArea = new TextArea();
        textArea.setText("This course, required for all Social Science majors, explains the fundamentals of the field of political science and offers introductory treatments on the four subfields of the discipline (i.e. political theory, comparative politics, international relations, and American government). Credits: 3.");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        GOVT2250.addItem(textArea);
        socialSciencesCategory.addItem(GOVT2250);

        

        
        //HIST2430.
        TreeItem HIST2430 = new TreeItem("HIST2430 - World History I.");
        textArea = new TextArea();
        textArea.setText("Introduction to the development of human civilizations from their Paleolithic origins through the formation of ancient empires and modern nation states in the Middle East, Africa, Asia, Europe, Oceania, and the Americas by 1600 CE. Credits: 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        HIST2430.addItem(textArea);
        socialSciencesCategory.addItem(HIST2430);
        
        
        
        
        //HIST2440.
        TreeItem HIST2440 = new TreeItem("HIST2440 - World History II.");
        textArea = new TextArea();
        textArea.setText("Introduction to the development of human civilizations from approximately the year 1600 CE to the present, focusing on the growing interaction of cultures and civilizations of the Middle East, Africa, Asia, Europe, Oceania, and the Americas. Credits: 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        HIST2440.addItem(textArea);
        socialSciencesCategory.addItem(HIST2440);
        
        
        

        //HIST2530.
        TreeItem HIST2530 = new TreeItem("HIST2530 - World Prehistory and Archaeology: Origins and the Development of Human Societies.");
        textArea = new TextArea();
        textArea.setText("Survey of world prehistory from human origins to the rise of complex societies and an introduction to the methods archaeologists use to study these past human achievements and solve some the world’s oldest mysteries. 3 lecture hours. Credits: 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        HIST2530.addItem(textArea);
        socialSciencesCategory.addItem(HIST2530);
        
        
        
        
        //HIST4110.
        TreeItem HIST4110 = new TreeItem("HIST4110 - Intellectual History.");
        textArea = new TextArea();
        textArea.setText("An examination of humanity’s ideas about our cosmos, our earth, and our species from pre-history to the modern era. Credits 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        HIST4110.addItem(textArea);
        socialSciencesCategory.addItem(HIST4110);
        
        
        
        
        //PHIL3360 - Social and Political Philosophy.
        TreeItem PHIL3360 = new TreeItem("PHIL3360 - Social and Political Philosophy.");
        textArea = new TextArea();
        textArea.setText("An examination of theories of society and the state that have significantly influenced Western thought from Plato to Aquinas to Rawls. A comparison of religious and secular understandings of liberal democracy and the state more generally is emphasized. HONORS SECTION: Designed to enable Honors students to explore the perennial questions of social and political thought through the most significant thinkers, both traditional and contemporary, of Western civilization. Credits 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        PHIL3360.addItem(textArea);
        socialSciencesCategory.addItem(PHIL3360);
        
        
        
        
        //PSYC1101.
        TreeItem PSYC1101 = new TreeItem("PSYC1101 - Introduction to Psychology.");
        textArea = new TextArea();
        textArea.setText("Survey of topics in experimental and clinical psychology, including physiological bases of behavior, sensation, perception, learning, memory, human development, social processes, personality and abnormal. 3 lecture hours. Credits 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        PSYC1101.addItem(textArea);
        socialSciencesCategory.addItem(PSYC1101);
        
        
        
        
        //SOCI1101.
        TreeItem SOCI1101 = new TreeItem("SOCI1101 - Introduction to Sociology.");
        textArea = new TextArea();
        textArea.setText("Studies the nature of human society and factors affecting its development, including concepts of culture, groups, organizations, collective behavior, and institutions. Required course for all social science majors. TAG course. 3 lecture hours. Credits 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        SOCI1101.addItem(textArea);
        socialSciencesCategory.addItem(SOCI1101);
        
        
        
        
        //SOSC1110.
        TreeItem SOSC1110 = new TreeItem("SOSC1110 - Foundations of Social Science.");
        textArea = new TextArea();
        textArea.setText("Introduction to the methods and concerns of social science. Studies perspectives of anthropology, economics, history, geography, political science, psychology, and sociology as related to specific themes or topics. HONORS SECTION: Explores a specific interdisciplinary theme in the social sciences. Examples include an examination of the causes of the 9/11 terrorist attacks on the U.S. or of Jared Diamond’s Guns, Germs and Steel. Credits 3");
        textArea.setReadOnly(true);
        textArea.setWidth(descriptionWidth);
        textArea.setVisibleLines(textArea.getText().length()/charactersPerLine);
        SOSC1110.addItem(textArea);
        socialSciencesCategory.addItem(SOSC1110);
        
        
        
        //===============================
        return categories;
        
        
    }

}// end class.








