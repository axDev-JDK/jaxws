/*
 * Copyright 2005-2006 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package com.sun.tools.internal.ws.wsdl.parser;

import com.sun.tools.internal.ws.api.wsdl.TWSDLExtensible;
import com.sun.tools.internal.ws.api.wsdl.TWSDLExtensionHandler;
import com.sun.tools.internal.ws.api.wsdl.TWSDLParserContext;
import com.sun.tools.internal.ws.util.xml.XmlUtil;
import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken;
import org.w3c.dom.Element;

/**
 * 
 * @author Jakub Podlesak (jakub.podlesak at sun.com)
 */

public class PolicyExtensionHandler extends TWSDLExtensionHandler {
    
    
    @Override
    public String getNamespaceURI() {
        return NamespaceVersion.v1_2.toString();
    }
    
    /*  we need default constructor, so that our service provider could be looked up and instantiated  */
    public PolicyExtensionHandler() {
    }

    /* only skip the element if it is either <wsp:Policy/> or <wsp:PolicyReference/> element */
    private boolean handleExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return XmlUtil.matchesTagNS(e, NamespaceVersion.v1_2.asQName(XmlToken.Policy)) 
                    || XmlUtil.matchesTagNS(e,NamespaceVersion.v1_2.asQName(XmlToken.PolicyReference));
    }
    
    @Override
    public boolean handlePortTypeExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return handleExtension(context, parent, e);
    }
    
    @Override
    public boolean handleDefinitionsExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return handleExtension(context, parent, e);
    }

    @Override
    public boolean handleBindingExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return handleExtension(context, parent, e);
    }
    
    @Override
    public boolean handleOperationExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return handleExtension(context, parent, e);
    }    

    @Override
    public boolean handleInputExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return handleExtension(context, parent, e);
    }    

    @Override
    public boolean handleOutputExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return handleExtension(context, parent, e);
    }    

    @Override
    public boolean handleFaultExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return handleExtension(context, parent, e);
    }    

    @Override
    public boolean handleServiceExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return handleExtension(context, parent, e);
    }    
    
    @Override
    public boolean handlePortExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return handleExtension(context, parent, e);
    }    
}
