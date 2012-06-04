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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

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

    final SuggestBox textBox0 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel00;
    
    final SuggestBox textBox1 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel11;
    
    final SuggestBox textBox2 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel22;
    
    final SuggestBox textBox3 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel33;
    
    final SuggestBox textBox4 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel44;
    
    final SuggestBox textBox5 = new SuggestBox(suggestOracle);
    private HorizontalPanel horizontalPanel55;

    

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

	FlowPanel flowPanel = new FlowPanel();
	dockLayoutPanel.addNorth(flowPanel, 8);

	VerticalPanel verticalPanel_2 = new VerticalPanel();
	flowPanel.add(verticalPanel_2);
	// verticalPanel_2.setHeight("53px");

	HTML htmlNewHtml = new HTML(
		"<h2>SSU Student Schedule Generator v.004<h2>", true);
	htmlNewHtml.setStyleName("none");
	htmlNewHtml.setDirectionEstimator(true);
	verticalPanel_2.add(htmlNewHtml);

	Button btnNewButton = new Button("Help");
	btnNewButton.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		Window.open("/help/help.html", "_blank", "");
	    }
	});
	verticalPanel_2.add(btnNewButton);

	VerticalPanel verticalPanel = new VerticalPanel();
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

	FlexTable flexTable = new FlexTable();
	decoratorPanel_1.setWidget(flexTable);
	flexTable.setBorderWidth(0);
	flexTable.setSize("445px", "116px");

	Label lblNewLabel_1 = new Label("Course Numbers");
	flexTable.setWidget(0, 0, lblNewLabel_1);
	
	
	//Suggest box 0.
	textBox0.setText("ETCO1120");
	textBox0.setLimit(10);
	HorizontalPanel horizontalPanel0 = new HorizontalPanel();
	horizontalPanel0.add(textBox0);
	ToggleButton pushButton0 = new ToggleButton("Sections");
	pushButton0.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		
		ToggleButton button = (ToggleButton) event.getSource();
		
		if (button.isDown() == true)
		{
		    
		
		studentScheduleService.getSections(":" + textBox0.getValue(),
			new AsyncCallback<String>() {
			    public void onFailure(Throwable caught) {
				if (caught instanceof IllegalArgumentException) {
				    Window.alert(caught.getMessage());
				} else {
				    Window.alert("Error :"
					    + caught.getMessage());
				}
			    }

			    public void onSuccess(String result) {

				String[] sections = result.split(",");
				for (String section : sections) {
				    CheckBox checkBox = new CheckBox(section);
				    checkBox.setValue(true);
				    checkBox.setWordWrap(false);
				    horizontalPanel00.add(checkBox);
				}

			    }
			});
		}
		else {
		    horizontalPanel00.clear();
		}
	    }
	});
	horizontalPanel0.add(pushButton0);
	horizontalPanel0.setCellVerticalAlignment(pushButton0,
		HasVerticalAlignment.ALIGN_MIDDLE);
	flexTable.setWidget(1, 0, horizontalPanel0);
	textBox0.setWidth("90px");
	coursesList.add(textBox0);

	horizontalPanel00 = new HorizontalPanel();
	horizontalPanel0.add(horizontalPanel00);
	horizontalPanel0.setCellVerticalAlignment(horizontalPanel00,
		HasVerticalAlignment.ALIGN_MIDDLE);

	
	
	
	
	
	//Suggest box 1.
	textBox1.setText("ETEM1110");
	textBox1.setLimit(10);
	HorizontalPanel horizontalPanel1 = new HorizontalPanel();
	horizontalPanel1.add(textBox1);
	ToggleButton pushButton1 = new ToggleButton("Sections");
	pushButton1.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		
		ToggleButton button = (ToggleButton) event.getSource();
		
		if (button.isDown() == true)
		{
		    
		
		studentScheduleService.getSections(":" + textBox1.getValue(),
			new AsyncCallback<String>() {
			    public void onFailure(Throwable caught) {
				if (caught instanceof IllegalArgumentException) {
				    Window.alert(caught.getMessage());
				} else {
				    Window.alert("Error :"
					    + caught.getMessage());
				}
			    }

			    public void onSuccess(String result) {

				String[] sections = result.split(",");
				for (String section : sections) {
				    CheckBox checkBox = new CheckBox(section);
				    checkBox.setValue(true);
				    checkBox.setWordWrap(false);
				    horizontalPanel11.add(checkBox);
				}

			    }
			});
		}
		else {
		    horizontalPanel11.clear();
		}
	    }
	});
	horizontalPanel1.add(pushButton1);
	horizontalPanel1.setCellVerticalAlignment(pushButton1,
		HasVerticalAlignment.ALIGN_MIDDLE);
	flexTable.setWidget(2, 0, horizontalPanel1);
	textBox1.setWidth("90px");
	coursesList.add(textBox1);

	horizontalPanel11 = new HorizontalPanel();
	horizontalPanel1.add(horizontalPanel11);
	horizontalPanel1.setCellVerticalAlignment(horizontalPanel11,
	HasVerticalAlignment.ALIGN_MIDDLE);


	//Suggest box 2.
	textBox2.setText("MATH1300");
	textBox2.setLimit(10);
	HorizontalPanel horizontalPanel2 = new HorizontalPanel();
	horizontalPanel2.add(textBox2);
	ToggleButton pushButton2 = new ToggleButton("Sections");
	pushButton2.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		
		ToggleButton button = (ToggleButton) event.getSource();
		
		if (button.isDown() == true)
		{
		    
		
		studentScheduleService.getSections(":" + textBox2.getValue(),
			new AsyncCallback<String>() {
			    public void onFailure(Throwable caught) {
				if (caught instanceof IllegalArgumentException) {
				    Window.alert(caught.getMessage());
				} else {
				    Window.alert("Error :"
					    + caught.getMessage());
				}
			    }

			    public void onSuccess(String result) {

				String[] sections = result.split(",");
				for (String section : sections) {
				    CheckBox checkBox = new CheckBox(section);
				    checkBox.setValue(true);
				    checkBox.setWordWrap(false);
				    horizontalPanel22.add(checkBox);
				}

			    }
			});
		}
		else {
		    horizontalPanel22.clear();
		}
	    }
	});
	horizontalPanel2.add(pushButton2);
	horizontalPanel2.setCellVerticalAlignment(pushButton2,
		HasVerticalAlignment.ALIGN_MIDDLE);
	flexTable.setWidget(3, 0, horizontalPanel2);
	textBox2.setWidth("90px");
	coursesList.add(textBox2);

	horizontalPanel22 = new HorizontalPanel();
	horizontalPanel2.add(horizontalPanel22);
	horizontalPanel2.setCellVerticalAlignment(horizontalPanel22,
	HasVerticalAlignment.ALIGN_MIDDLE);


	//Suggest box 3.
	textBox3.setText("ENGL1101");
	textBox3.setLimit(10);
	HorizontalPanel horizontalPanel3 = new HorizontalPanel();
	horizontalPanel3.add(textBox3);
	ToggleButton pushButton3 = new ToggleButton("Sections");
	pushButton3.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		
		ToggleButton button = (ToggleButton) event.getSource();
		
		if (button.isDown() == true)
		{
		    
		
		studentScheduleService.getSections(":" + textBox3.getValue(),
			new AsyncCallback<String>() {
			    public void onFailure(Throwable caught) {
				if (caught instanceof IllegalArgumentException) {
				    Window.alert(caught.getMessage());
				} else {
				    Window.alert("Error :"
					    + caught.getMessage());
				}
			    }

			    public void onSuccess(String result) {

				String[] sections = result.split(",");
				for (String section : sections) {
				    CheckBox checkBox = new CheckBox(section);
				    checkBox.setValue(true);
				    checkBox.setWordWrap(false);
				    horizontalPanel33.add(checkBox);
				}

			    }
			});
		}
		else {
		    horizontalPanel33.clear();
		}
	    }
	});
	horizontalPanel3.add(pushButton3);
	horizontalPanel3.setCellVerticalAlignment(pushButton3,
		HasVerticalAlignment.ALIGN_MIDDLE);
	flexTable.setWidget(4, 0, horizontalPanel3);
	textBox3.setWidth("90px");
	coursesList.add(textBox3);

	horizontalPanel33 = new HorizontalPanel();
	horizontalPanel3.add(horizontalPanel33);
	horizontalPanel3.setCellVerticalAlignment(horizontalPanel33,
	HasVerticalAlignment.ALIGN_MIDDLE);


	//Suggest box 4.
	//textBox0.setText("ETCO1120");
	textBox4.setLimit(10);
	HorizontalPanel horizontalPanel4 = new HorizontalPanel();
	horizontalPanel4.add(textBox4);
	ToggleButton pushButton4 = new ToggleButton("Sections");
	pushButton4.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		
		ToggleButton button = (ToggleButton) event.getSource();
		
		if (button.isDown() == true)
		{
		    
		
		studentScheduleService.getSections(":" + textBox4.getValue(),
			new AsyncCallback<String>() {
			    public void onFailure(Throwable caught) {
				if (caught instanceof IllegalArgumentException) {
				    Window.alert(caught.getMessage());
				} else {
				    Window.alert("Error :"
					    + caught.getMessage());
				}
			    }

			    public void onSuccess(String result) {

				String[] sections = result.split(",");
				for (String section : sections) {
				    CheckBox checkBox = new CheckBox(section);
				    checkBox.setValue(true);
				    checkBox.setWordWrap(false);
				    horizontalPanel44.add(checkBox);
				}

			    }
			});
		}
		else {
		    horizontalPanel44.clear();
		}
	    }
	});
	horizontalPanel4.add(pushButton4);
	horizontalPanel4.setCellVerticalAlignment(pushButton4,
		HasVerticalAlignment.ALIGN_MIDDLE);
	flexTable.setWidget(5, 0, horizontalPanel4);
	textBox4.setWidth("90px");
	coursesList.add(textBox4);

	horizontalPanel44 = new HorizontalPanel();
	horizontalPanel4.add(horizontalPanel44);
	horizontalPanel4.setCellVerticalAlignment(horizontalPanel44,
	HasVerticalAlignment.ALIGN_MIDDLE);


	//Suggest box 5.
	//textBox0.setText("ETCO1120");
	textBox5.setLimit(10);
	HorizontalPanel horizontalPanel5 = new HorizontalPanel();
	horizontalPanel5.add(textBox5);
	ToggleButton pushButton5 = new ToggleButton("Sections");
	pushButton5.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		
		ToggleButton button = (ToggleButton) event.getSource();
		
		if (button.isDown() == true)
		{
		    
		
		studentScheduleService.getSections(":" + textBox5.getValue(),
			new AsyncCallback<String>() {
			    public void onFailure(Throwable caught) {
				if (caught instanceof IllegalArgumentException) {
				    Window.alert(caught.getMessage());
				} else {
				    Window.alert("Error :"
					    + caught.getMessage());
				}
			    }

			    public void onSuccess(String result) {

				String[] sections = result.split(",");
				for (String section : sections) {
				    CheckBox checkBox = new CheckBox(section);
				    checkBox.setValue(true);
				    checkBox.setWordWrap(false);
				    horizontalPanel55.add(checkBox);
				}

			    }
			});
		}
		else {
		    horizontalPanel55.clear();
		}
	    }
	});
	horizontalPanel5.add(pushButton5);
	horizontalPanel5.setCellVerticalAlignment(pushButton5,
		HasVerticalAlignment.ALIGN_MIDDLE);
	flexTable.setWidget(6, 0, horizontalPanel5);
	textBox5.setWidth("90px");
	coursesList.add(textBox5);

	horizontalPanel55 = new HorizontalPanel();
	horizontalPanel5.add(horizontalPanel55);
	horizontalPanel5.setCellVerticalAlignment(horizontalPanel55,
		HasVerticalAlignment.ALIGN_MIDDLE);


	DecoratorPanel decoratorPanel = new DecoratorPanel();
	// decoratorPanel.setStyleName("border");
	decoratorPanel.addStyleName("border");
	verticalPanel.add(decoratorPanel);

	FlexTable flexTable_1 = new FlexTable();
	decoratorPanel.setWidget(flexTable_1);
	flexTable_1.setBorderWidth(0);

	Label lblNumberOfSchedules = new Label("Number Of Schedules");
	flexTable_1.setWidget(0, 0, lblNumberOfSchedules);

	numberReturned = new ListBox();
	numberReturned.addItem("1");
	numberReturned.addItem("2");
	numberReturned.addItem("3");
	numberReturned.addItem("4");
	numberReturned.addItem("5");
	numberReturned.addItem("10");
	numberReturned.addItem("20");
	flexTable_1.setWidget(0, 1, numberReturned);

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

	timeComboBox = new ListBox();
	timeComboBox.addItem("None");
	timeComboBox.addItem("Morning");
	timeComboBox.addItem("Afternoon");
	timeComboBox.addItem("Evening");
	flexTable_2.setWidget(0, 1, timeComboBox);

	horizontalPanel_1 = new HorizontalPanel();
	verticalPanel.add(horizontalPanel_1);

	Button btnReset = new Button("Reset");
	btnReset.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		numberReturned.setItemSelected(0, true);
		timeComboBox.setItemSelected(0, true);
		tabNumber = 1;

		for (ScrollPanel html : tabList) {
		    tabPanel.remove(html);
		}

		tabList.clear();

	    }
	});
	horizontalPanel_1.add(btnReset);

	final Button btnSubmit = new Button("Submit");
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

				if (caught instanceof IllegalArgumentException) {
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
				simplePanel.remove(processingImage);
				btnSubmit.setEnabled(true);
			    }
			});

	    }
	});

	tabPanel.selectTab(0);

    }// end method

    private String toClojure() {
	StringBuilder sb = new StringBuilder();

	sb.append("{");

	// =========== :course-lists.

	sb.append(":course-lists ");

	sb.append("[");

	for (SuggestBox textBox : coursesList) {
	    String courseNumber = textBox.getText().trim();

	    if (!courseNumber.equals("")) {

		sb.append(" [:");

		sb.append(courseNumber);

		sb.append("]");
	    }
	}// end for.

	sb.append("] ");

	sb.append(":return-number ");
	sb.append(numberReturned.getValue(numberReturned.getSelectedIndex()));

	sb.append(" :quality-fn-and-vals [");
	String dayTimePreference = timeComboBox.getValue(
		timeComboBox.getSelectedIndex()).toLowerCase();
	if (!dayTimePreference.equals("none")) {
	    sb.append("[\"time-of-day-ratio-corrected\" [:");
	    sb.append(dayTimePreference);
	    sb.append(" 1]]");
	}
	sb.append("]");

	// :quality-fn-and-vals [[time-of-day-ratio-corrected [:afternoon 1]]]

	sb.append(" :custom-courses {}");

	// =============== :selected-courses.

	sb.append(" :selected-courses");

	sb.append("[");
	
	
	//Course 0.
	if(horizontalPanel00.getWidgetCount() > 0)
	{
	    sb.append("{");
	    sb.append(":course-number :" + textBox0.getValue());
	    sb.append(" :section-numbers [");
	    
	    int widgetCount = horizontalPanel00.getWidgetCount();
	    for(int index = 0; index < widgetCount; index++)
	    {
		CheckBox checkBox = (CheckBox) horizontalPanel00.getWidget(index);
		if(checkBox.getValue() == true)
		{
    			sb.append(" :");
    			sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");
	    
	}
	
	
	//Course 1.
	if(horizontalPanel11.getWidgetCount() > 0)
	{
	    sb.append("{");
	    sb.append(":course-number :" + textBox1.getValue());
	    sb.append(" :section-numbers [");
	    
	    int widgetCount = horizontalPanel11.getWidgetCount();
	    for(int index = 0; index < widgetCount; index++)
	    {
		CheckBox checkBox = (CheckBox) horizontalPanel11.getWidget(index);
		if(checkBox.getValue() == true)
		{
    			sb.append(" :");
    			sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");
	    
	}
	
	
	
	//Course 2.
	if(horizontalPanel22.getWidgetCount() > 0)
	{
	    sb.append("{");
	    sb.append(":course-number :" + textBox2.getValue());
	    sb.append(" :section-numbers [");
	    
	    int widgetCount = horizontalPanel22.getWidgetCount();
	    for(int index = 0; index < widgetCount; index++)
	    {
		CheckBox checkBox = (CheckBox) horizontalPanel22.getWidget(index);
		if(checkBox.getValue() == true)
		{
    			sb.append(" :");
    			sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");
	    
	}
	
	
	
	//Course 3.
	if(horizontalPanel33.getWidgetCount() > 0)
	{
	    sb.append("{");
	    sb.append(":course-number :" + textBox3.getValue());
	    sb.append(" :section-numbers [");
	    
	    int widgetCount = horizontalPanel33.getWidgetCount();
	    for(int index = 0; index < widgetCount; index++)
	    {
		CheckBox checkBox = (CheckBox) horizontalPanel33.getWidget(index);
		if(checkBox.getValue() == true)
		{
    			sb.append(" :");
    			sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");
	    
	}
	
	
	
	//Course 4.
	if(horizontalPanel44.getWidgetCount() > 0)
	{
	    sb.append("{");
	    sb.append(":course-number :" + textBox4.getValue());
	    sb.append(" :section-numbers [");
	    
	    int widgetCount = horizontalPanel44.getWidgetCount();
	    for(int index = 0; index < widgetCount; index++)
	    {
		CheckBox checkBox = (CheckBox) horizontalPanel44.getWidget(index);
		if(checkBox.getValue() == true)
		{
    			sb.append(" :");
    			sb.append(checkBox.getText());
		}
	    }
	    sb.append("]");
	    sb.append("}");
	    
	}
	
	
	
	//Course 5.
	if(horizontalPanel55.getWidgetCount() > 0)
	{
	    sb.append("{");
	    sb.append(":course-number :" + textBox5.getValue());
	    sb.append(" :section-numbers [");
	    
	    int widgetCount = horizontalPanel55.getWidgetCount();
	    for(int index = 0; index < widgetCount; index++)
	    {
		CheckBox checkBox = (CheckBox) horizontalPanel55.getWidget(index);
		if(checkBox.getValue() == true)
		{
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
}
