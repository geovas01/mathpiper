package org.mathpiper.ui.gui.andriod;

import org.mathpiper.interpreters.EvaluationResponse;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class PreferencesActivity extends PreferenceActivity {


    private MathPiperInterpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setPreferenceScreen(createPreferenceHierarchy());
    }

    private PreferenceScreen createPreferenceHierarchy() {
	// Root
	PreferenceScreen root = getPreferenceManager().createPreferenceScreen(
		this);

	interpreter = MathPiperInterpreter.getInterpreter();



	try {
	    return initialize(root);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	return root;
    }

    private PreferenceScreen initialize(PreferenceScreen root) throws Exception {


	// =======================================================================

	EvaluationResponse response = interpreter.evaluate("ConfigurationsGet();");
	
	String result;

	if (response.isExceptionThrown()) {
	    throw response.getException();
	} else {
	    result = response.getResult();
	    
	    result = result.replace("\"", "");

	    String[] configurations = result.split(";");

	    for (String configuration : configurations) {
		String[] values = configuration.split(",");

		String name = values[0];

		String type = values[1];

		String value = values[2];
 
		// Edit text preference
		EditTextPreference editTextPref = new EditTextPreference(this);
		editTextPref.setDialogTitle(name);
		editTextPref.setKey(name + "_preference");
		editTextPref.setTitle(name);
		editTextPref.setSummary("test summary");
		editTextPref.setDefaultValue(value);
		root.addPreference(editTextPref);
	    }

	    /*
	     * String numberOneLow = "2"; String numberOneHigh = "9"; String
	     * numberTwoLow = "2"; String numberTwoHigh = "9";
	     * 
	     * Editor edit = preferences.edit(); edit.putString("numberOneLow",
	     * numberOneLow); edit.putString("numberOneHigh", numberOneHigh);
	     * edit.putString("numberTwoLow", numberTwoLow);
	     * edit.putString("numberTwoHigh", numberTwoHigh); edit.commit();
	     * 
	     * String initializeCode = "operation := \"+\"; numberOneLowSet(" +
	     * numberOneLow + ");numberOneHighSet(" + numberOneHigh +
	     * ");numberTwoLowSet(" + numberTwoLow + ");numberTwoHighSet(" +
	     * numberTwoHigh + ");";
	     */

	    /*String initializeCode2 = "operation := \"+\"; numberOneLowSet(2);numberOneHighSet(9999);numberTwoLowSet(2);numberTwoHighSet(9999);";

	    response = interpreter.evaluate(initializeCode2);

	    if (response.isExceptionThrown()) {
		throw response.getException();
	    } else {
		result = response.getResult();
	    }
*/

	}//end else.
	
	return root;
    }// end method.
}