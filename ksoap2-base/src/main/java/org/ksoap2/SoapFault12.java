/*
 * Copyright (c) 2003,2004, Stefan Haustein, Oberhausen, Rhld., Germany
 * 
 * Copyright (c) 2011, Petter Uvesten, Everichon AB, Sweden
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.ksoap2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.ksoap2.transport.ServiceConnectionFixture;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/**
 * Exception class encapsulating SOAP 1.2 Faults
 * 
 * see http://www.w3.org/TR/soap12-part1/#soapfault for explanation of fields
 */

public class SoapFault12 extends IOException
{

	private static final long serialVersionUID = 1001L;
	
	/** Top-level nodes */
	public Node Code;
	public Node Reason;
	public Node Node;
	public Node Role;
	public Node Detail;
	
	/** used for holding the env namespace */
	public String env;

	public SoapFault12(String env) {
		super();
		this.env = env;
	}

	/** Fills the fault details from the given XML stream */
	public void parse(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, this.env, "Fault");
		
		while (parser.nextTag() == XmlPullParser.START_TAG)
		{
			String name = parser.getName();
			parser.nextTag();
			if (name.equals("Code"))
			{
				
				this.Code = new Node();
				this.Code.parse(parser);
				
				
			}
			else if (name.equals("Reason"))
			{
				this.Reason = new Node();
				this.Reason.parse(parser);
				
				
				
			} else if (name.equals("Node"))
			{
				this.Node = new Node();
				this.Node.parse(parser);
				
				
			}
			else if (name.equals("Role"))
			{
				this.Role = new Node();
				this.Role.parse(parser);
				
				
			}
			else if (name.equals("Detail"))
			{
				this.Detail = new Node();
				this.Detail.parse(parser);
				
				
			}
			else
				throw new RuntimeException("unexpected tag:" + name);
			
			parser.require(XmlPullParser.END_TAG, this.env, name);
			
		}
		parser.require(XmlPullParser.END_TAG, this.env, "Fault");
		parser.nextTag();
	}

	/** Writes the fault to the given XML stream */
	public void write(XmlSerializer xw) throws IOException
	{
		
		
		xw.startTag(this.env, "Fault");
		//this.Code.write(xw);
		
		
		xw.startTag(this.env, "Code");
		this.Code.write(xw);
		xw.endTag(this.env, "Code");
		xw.startTag(this.env, "Reason");
		this.Reason.write(xw);
		xw.endTag(this.env, "Reason");
		
		if (this.Node != null) {
			xw.startTag(this.env, "Node");
			this.Node.write(xw);
			xw.endTag(this.env, "Node");
		}
		if (this.Role != null) {
			xw.startTag(this.env, "Role");
			this.Role.write(xw);
			xw.endTag(this.env, "Role");
		}
		
		if (this.Detail != null) {
			xw.startTag(this.env, "Detail");
			this.Detail.write(xw);
			xw.endTag(this.env, "Detail");
		}
		
		
		
		
		xw.endTag(this.env, "Fault");
	}

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage()
	{
		return Reason.getElement(this.env, "Text").getText(0);
	}

	/** Returns an XML representation of the fault */
	public String toString()
	{
		
        
        String reason = Reason.getElement(this.env, "Text").getText(0);
        
        String code = Code.getElement(this.env, "Value").getText(0);
        
       
		return "Code: " + code + ", Reason: " + reason;
	}
}
