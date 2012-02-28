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
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MathPiperExerciseActivity extends Activity implements
		OnInitListener {
	private TextToSpeech tts;
	static final int TTS_CHECK_CODE = 0;


	private EditText displayText;
	private EditText inputTextField;

	private MathPiperInterpreter interpreter;





	private String resultText;

	private Map<String, Fold> foldsMap;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		interpreter = new MathPiperInterpreter();

		InputStream raw;
		try {
			raw = getAssets().open("arithmetic.mpw");

			foldsMap = org.mathpiper.test.MPWSFile.getFoldsMap(raw);

			// =================================================================
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);

			inputTextField = (EditText) findViewById(R.id.inputText);

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
					
					String input = inputTextField.getText().toString();
					
					EvaluationResponse response = interpreter.evaluate("QuestionCheck(" + input + ");");
					
					String result;
					
					if(response.isExceptionThrown())
					{
						result = response.getException().getMessage();
					}
					else
					{
						result = response.getResult();
					}
					
					displayText.setText("D: " + result);

				}
			});

			final Button button_initialize = (Button) findViewById(R.id.button_initialize);
			button_initialize.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {

					Fold configurationFold = foldsMap.get("configuration");

					String codeText = configurationFold.getContents();

					codeText = codeText.replace("\\", "\\\\");
					codeText = codeText.replace("\"", "\\\"");

					EvaluationResponse response = interpreter.evaluate(codeText);
					String result;
					
					if(response.isExceptionThrown())
					{
						result = response.getException().getMessage();
						
						displayText.setText("A: " + result);
						return;
					}
					else
					{
						result = response.getResult();
					}
					


					response = interpreter.evaluate("operation := \"+\"; numberOneLowSet(2);numberOneHighSet(9);numberTwoLowSet(2);numberTwoHighSet(9);".replace("\"", "\\\""));
					if(response.isExceptionThrown())
					{
						result = response.getException().getMessage();
						
						displayText.setText("B: " + result);
						return;
					}
					else
					{
						result = response.getResult();
					}
					
					
					
					configurationFold = foldsMap.get("ExerciseEngine");

					codeText = configurationFold.getContents();

					codeText = codeText.replace("\\", "\\\\");
					codeText = codeText.replace("\"", "\\\"");

					response = interpreter.evaluate(codeText);
					
					if(response.isExceptionThrown())
					{
						result = response.getException().getMessage();
						
						displayText.setText("C: " + result);
						return;
					}
					else
					{
						result = response.getResult();
					}
					
					
				}
			});

			final Button button_start = (Button) findViewById(R.id.button_start);
			button_start.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					
					EvaluationResponse response = interpreter.evaluate("QuestionAsk();");
					
					String result;
					
					
					if(response.isExceptionThrown())
					{
						result = response.getException().getMessage();
					}
					else
					{
						result = response.getResult();
					}
					
					
					result = result.replace("\"", "");
					
					displayText.setText("Y: " + result);
					
					
					tts.speak(result, TextToSpeech.QUEUE_FLUSH, null);
					
				}
			});
			
			
			
			final Button button_evaluate = (Button) findViewById(R.id.button_evaluate);
			button_evaluate.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					
					String input = inputTextField.getText().toString();
					
					EvaluationResponse response = interpreter.evaluate(input + ";");
					
					String result;
					
					if(response.isExceptionThrown())
					{
						result = response.getException().getMessage();
					}
					else
					{
						result = response.getResult();
					}
					
					displayText.setText("X: " + result);

				}
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onInit(int initStatus) {
		if (initStatus == TextToSpeech.SUCCESS) {
			tts.speak("Working", TextToSpeech.QUEUE_FLUSH, null);
		}
	}

	// ==============================================
	private class MathPiperInterpreter implements Runnable {
		private Thread casThread;
		private EvaluationResponse evaluationResponse;
		private Interpreter interpreter;
		private String inputText = null;

		public MathPiperInterpreter() {

			casThread = new Thread(new ThreadGroup("mathiper"), this,
					"mathpiper", 50000);
			casThread.start();
			interpreter = Interpreters.getSynchronousInterpreter();
		}

		public EvaluationResponse evaluate(String input) {
			inputText = "LoadScript(\"" + input + "\");";


			while (inputText != null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return evaluationResponse;
		}

		public void run() {
			while (true) {
				if (inputText == null) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {

					evaluationResponse = interpreter.evaluate(inputText);

					/*
					 * runOnUiThread(new Runnable() { public void run() {
					 * 
					 * String response;
					 * 
					 * if(evaluationResponse.isExceptionThrown()) { response =
					 * evaluationResponse.getException().getMessage(); } else {
					 * response = evaluationResponse.getResult(); }
					 * 
					 * } });
					 */

					inputText = null;
				}

			}
		}// end method
	}// end class.
}