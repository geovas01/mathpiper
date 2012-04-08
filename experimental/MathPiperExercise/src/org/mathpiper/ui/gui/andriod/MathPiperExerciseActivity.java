package org.mathpiper.ui.gui.andriod;

//import org.mathpiper.android.R;
import java.io.*;
import java.util.Map;

import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.interpreters.ResponseListener;
import org.mathpiper.test.Fold;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MathPiperExerciseActivity extends Activity implements
	OnInitListener {
    private TextToSpeech tts;
    static final int TTS_CHECK_CODE = 0;

    private EditText displayText;
    private EditText inputTextField;

    private MathPiperInterpreter interpreter;

    private String currentQuestion;

    private SharedPreferences preferences;

    private String resultText;
    
    private Map<String, Fold> foldsMap;

    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

	preferences = PreferenceManager.getDefaultSharedPreferences(this);

	interpreter = MathPiperInterpreter.getInterpreter();



	    

	    // =================================================================
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mathpiperexercise);

	    inputTextField = (EditText) findViewById(R.id.inputText);
	    
	    inputTextField.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {

		        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
		         
		            enter();
		            
		          return true;
		        }
		        return false;
		    }
		});
	    
	    
	    
	    

	    displayText = (EditText) findViewById(R.id.displayText);

	    tts = new TextToSpeech(this, this);

	    // Add event listeners to buttons.
	    final Button button0 = (Button) findViewById(R.id.button_0);
	    button0.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    inputTextField.append(button0.getText());
		}
	    });

	    final Button button1 = (Button) findViewById(R.id.button_1);
	    button1.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    inputTextField.append(button1.getText());
		}
	    });

	    final Button button2 = (Button) findViewById(R.id.button_2);
	    button2.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    inputTextField.append(button2.getText());
		}
	    });

	    final Button button3 = (Button) findViewById(R.id.button_3);
	    button3.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    inputTextField.append(button3.getText());
		}
	    });

	    final Button button4 = (Button) findViewById(R.id.button_4);
	    button4.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    inputTextField.append(button4.getText());
		}
	    });

	    final Button button5 = (Button) findViewById(R.id.button_5);
	    button5.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    inputTextField.append(button5.getText());
		}
	    });

	    final Button button6 = (Button) findViewById(R.id.button_6);
	    button6.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    inputTextField.append(button6.getText());
		}
	    });

	    final Button button7 = (Button) findViewById(R.id.button_7);
	    button7.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    inputTextField.append(button7.getText());
		}
	    });

	    final Button button8 = (Button) findViewById(R.id.button_8);
	    button8.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    inputTextField.append(button8.getText());
		}
	    });

	    final Button button9 = (Button) findViewById(R.id.button_9);
	    button9.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    inputTextField.append(button9.getText());
		}
	    });

	    final Button button_backspace = (Button) findViewById(R.id.button_backspace);
	    button_backspace.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {

		    String currentText = inputTextField.getText().toString();

		    if (currentText.length() > 0) {
			currentText = currentText.substring(0,
				currentText.length() - 1);

			inputTextField.setText(currentText);
		    }

		}
	    });

	    final Button button_enter = (Button) findViewById(R.id.button_enter);
	    button_enter.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {

		    enter();

		}
	    });

	    final Button button_configure = (Button) findViewById(R.id.button_configure);
	    button_configure.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {

		    Intent settingsActivity = new Intent(getBaseContext(),
			    PreferencesActivity.class);
		    startActivity(settingsActivity);

		}
	    });

	    final Button button_start = (Button) findViewById(R.id.button_start);
	    button_start.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {

		    questionAsk();

		}
	    });

	    final Button button_evaluate = (Button) findViewById(R.id.button_evaluate);
	    button_evaluate.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {

		    String input = inputTextField.getText().toString();

		    EvaluationResponse response = interpreter.evaluate(input
			    + ";");

		    String result;

		    if (response.isExceptionThrown()) {
			result = response.getException().getMessage();
		    } else {
			result = response.getResult();
		    }

		    displayText.setText(result);

		}
	    });



    }

    @Override
    public void onInit(int initStatus) {
	if (initStatus == TextToSpeech.SUCCESS) {
	    speak("initializing");
	    displayText.setText("initializing");

	    try {
		initialize();
		    speak("ready");
		    displayText.setText("ready");
	    } catch (Exception e) {
		speak("Initialization error.");
		
		displayText.setText(e.getMessage());
	    }



	}
    }
    
    
    
    private void initialize()
    {
	InputStream raw;
	try {
	    raw = getAssets().open("arithmetic.mpw");

	    foldsMap = org.mathpiper.test.MPWSFile.getFoldsMap(raw);

	} catch (Exception e) {
	    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
	    e.printStackTrace();
	}
	
	Fold configurationFold = foldsMap.get("configuration");

	String codeText = configurationFold.getContents();

	EvaluationResponse response = interpreter.evaluate(codeText);
	String result;

	if (response.isExceptionThrown()) {
	    result = response.getException().getMessage();

	    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
	    return;
	} else {
	    result = response.getResult();
	}
	
	
//====================================	
	    configurationFold = foldsMap.get("ExerciseEngine");

	    codeText = configurationFold.getContents();

	    response = interpreter.evaluate(codeText);

	    if (response.isExceptionThrown()) {
		result = response.getException().getMessage();

		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		
		return;
		
	    } else {
		result = response.getResult();
	    }
    }

    private void questionAsk() {
	EvaluationResponse response = interpreter.evaluate("QuestionAsk();");

	String result;

	if (response.isExceptionThrown()) {
	    result = response.getException().getMessage();
	} else {
	    result = response.getResult();
	}

	result = result.replace("\"", "");

	currentQuestion = result;

	displayText.setText(currentQuestion);

	speak(currentQuestion);

    }

    private void speak(String text) {

	while (tts.isSpeaking()) {
	    try {
		Thread.sleep(100);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
	super.onConfigurationChanged(newConfig);

    }

    private void enter() {
	String input = inputTextField.getText().toString();

	if (input.equals("")) {
	speak(currentQuestion);

	return;
	}

	EvaluationResponse response = interpreter
	    .evaluate("QuestionCheck(" + input + ");");

	String result;

	if (response.isExceptionThrown()) {
	result = response.getException().getMessage();
	} else {
	result = response.getResult();
	}

	inputTextField.setText("");

	speak(input);

	if (result.equals("True")) {
	displayText.setText("Correct");

	speak("correct");

	questionAsk();
	} else {
	displayText.setText("Incorrect");

	speak("incorrect");

	speak(currentQuestion);
	}
    }

    // ==============================================

}