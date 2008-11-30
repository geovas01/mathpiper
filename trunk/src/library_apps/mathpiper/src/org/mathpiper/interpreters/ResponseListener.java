/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.interpreters;

import org.mathpiper.interpreters.EvaluationResponse;

public interface ResponseListener
{
    void response(EvaluationResponse response);
    boolean remove();
}// end interface.
