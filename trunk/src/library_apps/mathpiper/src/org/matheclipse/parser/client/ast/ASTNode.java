/*
 * Copyright 2005-2008 Axel Kramer (axellclk@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.matheclipse.parser.client.ast;

/**
 * The basic node for a parsed expression string
 * 
 */
public abstract class ASTNode {

	protected final String fStringValue;

	protected ASTNode(final String value) {
		fStringValue = value;
	}

	/**
	 * Returns the parsed string of this node.
	 * 
	 * @return <code>null</code> if there's another representation in the
	 *         derived class
	 */
	public String getString() { 
		return fStringValue;
	}

	public String toString() {
		return fStringValue;
	}

	public boolean dependsOn(String variableName) {
		return false;
	}

//	public ASTNode derivative(String variableName) {
//		return new IntegerNode("0");
//	}

	public boolean equals(Object obj) {
		if (obj instanceof ASTNode) {
			return fStringValue.equals(((ASTNode) obj).fStringValue);
		}
		return false;
	}

	public int hashCode() {
		return fStringValue.hashCode();
	}
}
