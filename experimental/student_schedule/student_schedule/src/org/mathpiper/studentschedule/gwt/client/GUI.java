package org.mathpiper.studentschedule.gwt.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.NotificationMole;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
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
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class GUI implements EntryPoint {

    private final StudentScheduleServiceAsync studentScheduleService = GWT
	    .create(StudentScheduleService.class);

    private List<SuggestBox> coursesList = new ArrayList<SuggestBox>();

    private List<CheckBox> fineArtsGEPList = new ArrayList<CheckBox>();

    private ListBox numberReturned;

    private ListBox timeComboBox;

    private int tabNumber = 1;

    final TabLayoutPanel tabPanel = new TabLayoutPanel(2.3, Unit.EM);

    private List<ScrollPanel> tabList = new ArrayList<ScrollPanel>();

    private MultiWordSuggestOracle suggestOracle = new MultiWordSuggestOracle();

    private RootLayoutPanel rootPanel;
    
    private Timer blinkTimer;
    
    private Image processingImage = new Image("processing.gif");
    
    private HorizontalPanel horizontalPanel_1;
    
    private SimplePanel simplePanel;


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
	
	/*NotificationMole notificationMole = new NotificationMole();
	notificationMole.setAnimationDuration(2000);
        notificationMole.setTitle("Title");
        notificationMole.setHeight("100px");
        notificationMole.setWidth("200px");
        notificationMole.setMessage("Test message to be shown in mole");
	notificationMole.hide();
	verticalPanel_2.add(notificationMole);*/
	

	VerticalPanel verticalPanel = new VerticalPanel();
	dockLayoutPanel.add(tabPanel);

	tabPanel.add(verticalPanel, "Enter Courses", false);

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

	SuggestBox textBox0 = new SuggestBox(suggestOracle);
	textBox0.setText("ETCO1120");
	textBox0.setLimit(10);
	flexTable.setWidget(1, 0, textBox0);
	coursesList.add(textBox0);

	SuggestBox textBox1 = new SuggestBox(suggestOracle);
	textBox1.setText("ETEM1110");
	textBox1.setLimit(10);
	flexTable.setWidget(2, 0, textBox1);
	coursesList.add(textBox1);

	SuggestBox textBox2 = new SuggestBox(suggestOracle);
	textBox2.setText("MATH1300");
	textBox2.setLimit(10);
	flexTable.setWidget(3, 0, textBox2);
	coursesList.add(textBox2);

	SuggestBox textBox3 = new SuggestBox(suggestOracle);
	textBox3.setText("ENGL1101");
	textBox3.setLimit(10);
	flexTable.setWidget(4, 0, textBox3);
	coursesList.add(textBox3);

	SuggestBox textBox4 = new SuggestBox(suggestOracle);
	textBox4.setLimit(10);
	flexTable.setWidget(5, 0, textBox4);
	coursesList.add(textBox4);

	SuggestBox textBox5 = new SuggestBox(suggestOracle);
	textBox5.setLimit(10);
	flexTable.setWidget(6, 0, textBox5);
	coursesList.add(textBox5);

	DecoratorPanel gep_decorator_panel = new DecoratorPanel();
	horizontalPanel.add(gep_decorator_panel);

	FlexTable flexTable_3 = new FlexTable();
	gep_decorator_panel.setWidget(flexTable_3);

	Label lblFineArts = new Label("Fine Arts");
	flexTable_3.setWidget(0, 0, lblFineArts);

	CheckBox chckbxNewCheckBox = new CheckBox("ARTH1101");
	chckbxNewCheckBox
		.setTitle("ARTH 1101 - Introduction to Art - Credits: 3 - The course is an introduction to the visual arts. It encompasses the world of western and non-western art. It deals with the principles of art, formal and contextual elements and the basic vocabulary necessary in order to articulate opinions about the arts. The course has a studio component that will allow the student hands on experience to encourage visual communication through the visual arts.");
	flexTable_3.setWidget(1, 0, chckbxNewCheckBox);
	fineArtsGEPList.add(chckbxNewCheckBox);

	CheckBox chckbxEngl = new CheckBox("ENGL2275");
	chckbxEngl
		.setTitle("ENGL 2275 - American Film History - Credits: 3 - Chronological study of the influence of American history upon American film, and vice versa. Students become acquainted with the work and themes of some of America’s significant film directors and major genres of American popular film. Prereq: ENGL 1105 or ENGL 1107.");
	flexTable_3.setWidget(2, 0, chckbxEngl);
	fineArtsGEPList.add(chckbxEngl);

	CheckBox chckbxMusi = new CheckBox("MUSI1201");
	chckbxMusi
		.setTitle("MUSI 1201 - Music Appreciation - Credits: 3 - A survey of musical highlights throughout history including pieces, composers, forms, styles, and performance media from the Fall of the Roman Empire to the emergence of the music video.");
	flexTable_3.setWidget(3, 0, chckbxMusi);
	fineArtsGEPList.add(chckbxMusi);

	CheckBox chckbxMusi_1 = new CheckBox("MUSI2211");
	chckbxMusi_1
		.setTitle("MUSI 2211 - Music History 1 -Credits: 3 - A detailed survey of music including pieces, composers, forms, styles, and performance media from the Fall of the Roman Empire through the Classical Period.");
	flexTable_3.setWidget(4, 0, chckbxMusi_1);
	fineArtsGEPList.add(chckbxMusi_1);

	CheckBox chckbxPhil = new CheckBox("PHIL3300");
	chckbxPhil
		.setTitle("PHIL 3300 - Philosophy and Film - Credits: 3 - Viewing, analysis, and interpretation of international and domestic films and their philosophical, aesthetic, and moral dimensions.");
	flexTable_3.setWidget(5, 0, chckbxPhil);
	fineArtsGEPList.add(chckbxPhil);

	CheckBox chckbxThar = new CheckBox("THAR1000");
	chckbxThar
		.setTitle("THAR 1000 - Introduction to Theater- Credits: 3 - Survey of development of theater from classical to modern times, emphasizing the artists and craftspersons of the theater and their contributions to its development.");
	flexTable_3.setWidget(6, 0, chckbxThar);
	fineArtsGEPList.add(chckbxThar);

	Button btnAll = new Button("All");
	btnAll.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		for (CheckBox checkBox : fineArtsGEPList) {
		    checkBox.setValue(true);
		}
	    }
	});
	btnAll.setText("Check All");
	flexTable_3.setWidget(7, 0, btnAll);

	DecoratorPanel decoratorPanel = new DecoratorPanel();
	decoratorPanel.setStyleName("border");
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

		for (CheckBox checkBox : fineArtsGEPList) {
		    checkBox.setValue(false);
		}
	    }
	});
	horizontalPanel_1.add(btnReset);
	
	final Button btnSubmit = new Button("Submit");
	horizontalPanel_1.add(btnSubmit);
	
	simplePanel = new SimplePanel();
	horizontalPanel_1.add(simplePanel);
	simplePanel.setSize("49px", "29px");
	
	btnSubmit.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		//notificationMole.show();
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
				final Label tabLabel = new Label("" + tabNumber++);
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
				
				
				   blinkTimer = new Timer()
                                    {
				       
				       private boolean toggle = true;
				       
				       private int count = 6;
                                        @Override
                                        public void run()
                                        {
                                            if(toggle)
                                            {
                                        	tabLabel.setStyleName("newTab");
                                        	toggle = false;
                                            }
                                            else
                                            {
                                        	tabLabel.setStyleName("normalTab");
                                        	toggle = true;
                                            }
                                            
                                            count--;
                                            
                                            if(count == 0)
                                            {
                                        	blinkTimer.cancel();
                                            }
                                            
                                        }
                                    };
                                
                                    blinkTimer.scheduleRepeating(600);
                                    //notificationMole.hide();
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

	sb.append("{:course-lists ");

	sb.append("[");

	for (SuggestBox textBox : coursesList) {
	    String courseNumber = textBox.getText().trim();

	    if (!courseNumber.equals("")) {

		sb.append(" [:");

		sb.append(courseNumber);

		sb.append("]");
	    }
	}// end for.

	// Fine arts GEP.
	final StringBuilder fineArtsStringBuilder = new StringBuilder();
	boolean empty = true;
	for (CheckBox checkBox : fineArtsGEPList) {
	    if (checkBox.getValue() == true) {
		empty = false;
		fineArtsStringBuilder.append(" :");
		fineArtsStringBuilder.append(checkBox.getText().toString());
		// sb.append("]");
	    }
	}
	if (!empty) {
	    sb.append(" [");
	    sb.append(fineArtsStringBuilder.toString());
	    sb.append("]");
	}

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

	sb.append("}");

	return sb.toString();

    }
}
