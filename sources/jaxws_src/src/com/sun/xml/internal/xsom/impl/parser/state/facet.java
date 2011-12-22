/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/* this file is generated by RelaxNGCC */
package com.sun.xml.internal.xsom.impl.parser.state;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import com.sun.xml.internal.xsom.impl.parser.NGCCRuntimeEx;

    import com.sun.xml.internal.xsom.*;
    import com.sun.xml.internal.xsom.parser.*;
    import com.sun.xml.internal.xsom.impl.*;
    import com.sun.xml.internal.xsom.impl.parser.*;
    import org.xml.sax.Locator;
    import org.xml.sax.ContentHandler;
    import org.xml.sax.helpers.*;
    import java.util.*;
    import java.math.BigInteger;



class facet extends NGCCHandler {
    private AnnotationImpl annotation;
    private String fixed;
    private String value;
    private ForeignAttributesImpl fa;
    protected final NGCCRuntimeEx $runtime;
    private int $_ngcc_current_state;
    protected String $uri;
    protected String $localName;
    protected String $qname;

    public final NGCCRuntime getRuntime() {
        return($runtime);
    }

    public facet(NGCCHandler parent, NGCCEventSource source, NGCCRuntimeEx runtime, int cookie) {
        super(source, parent, cookie);
        $runtime = runtime;
        $_ngcc_current_state = 12;
    }

    public facet(NGCCRuntimeEx runtime) {
        this(null, runtime, runtime, -1);
    }

    private void action0()throws SAXException {

        result = new FacetImpl( $runtime.document,
                annotation, locator, fa, $localName/*name of the facet*/,
                $runtime.createXmlString(value), $runtime.parseBoolean(fixed) );

}

    private void action1()throws SAXException {
        locator=$runtime.copyLocator();
}

    public void enterElement(String $__uri, String $__local, String $__qname, Attributes $attrs) throws SAXException {
        int $ai;
        $uri = $__uri;
        $localName = $__local;
        $qname = $__qname;
        switch($_ngcc_current_state) {
        case 5:
            {
                if(($ai = $runtime.getAttributeIndex("","fixed"))>=0) {
                    $runtime.consumeAttribute($ai);
                    $runtime.sendEnterElement(super._cookie, $__uri, $__local, $__qname, $attrs);
                }
                else {
                    $_ngcc_current_state = 4;
                    $runtime.sendEnterElement(super._cookie, $__uri, $__local, $__qname, $attrs);
                }
            }
            break;
        case 12:
            {
                if((((((((((((($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("minExclusive")) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("maxExclusive"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("minInclusive"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("maxInclusive"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("totalDigits"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("fractionDigits"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("length"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("maxLength"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("minLength"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("enumeration"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("whiteSpace"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("pattern")))) {
                    $runtime.onEnterElementConsumed($__uri, $__local, $__qname, $attrs);
                    action1();
                    $_ngcc_current_state = 11;
                }
                else {
                    unexpectedEnterElement($__qname);
                }
            }
            break;
        case 0:
            {
                revertToParentFromEnterElement(result, super._cookie, $__uri, $__local, $__qname, $attrs);
            }
            break;
        case 11:
            {
                if(($ai = $runtime.getAttributeIndex("","value"))>=0) {
                    $runtime.consumeAttribute($ai);
                    $runtime.sendEnterElement(super._cookie, $__uri, $__local, $__qname, $attrs);
                }
                else {
                    unexpectedEnterElement($__qname);
                }
            }
            break;
        case 4:
            {
                if(($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("annotation"))) {
                    NGCCHandler h = new foreignAttributes(this, super._source, $runtime, 192, fa);
                    spawnChildFromEnterElement(h, $__uri, $__local, $__qname, $attrs);
                }
                else {
                    unexpectedEnterElement($__qname);
                }
            }
            break;
        case 2:
            {
                if(($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("annotation"))) {
                    NGCCHandler h = new annotation(this, super._source, $runtime, 190, null,AnnotationContext.SIMPLETYPE_DECL);
                    spawnChildFromEnterElement(h, $__uri, $__local, $__qname, $attrs);
                }
                else {
                    $_ngcc_current_state = 1;
                    $runtime.sendEnterElement(super._cookie, $__uri, $__local, $__qname, $attrs);
                }
            }
            break;
        default:
            {
                unexpectedEnterElement($__qname);
            }
            break;
        }
    }

    public void leaveElement(String $__uri, String $__local, String $__qname) throws SAXException {
        int $ai;
        $uri = $__uri;
        $localName = $__local;
        $qname = $__qname;
        switch($_ngcc_current_state) {
        case 5:
            {
                if(($ai = $runtime.getAttributeIndex("","fixed"))>=0) {
                    $runtime.consumeAttribute($ai);
                    $runtime.sendLeaveElement(super._cookie, $__uri, $__local, $__qname);
                }
                else {
                    $_ngcc_current_state = 4;
                    $runtime.sendLeaveElement(super._cookie, $__uri, $__local, $__qname);
                }
            }
            break;
        case 1:
            {
                if((((((((((((($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("minExclusive")) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("maxExclusive"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("minInclusive"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("maxInclusive"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("totalDigits"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("fractionDigits"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("length"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("maxLength"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("minLength"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("enumeration"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("whiteSpace"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("pattern")))) {
                    $runtime.onLeaveElementConsumed($__uri, $__local, $__qname);
                    $_ngcc_current_state = 0;
                    action0();
                }
                else {
                    unexpectedLeaveElement($__qname);
                }
            }
            break;
        case 0:
            {
                revertToParentFromLeaveElement(result, super._cookie, $__uri, $__local, $__qname);
            }
            break;
        case 11:
            {
                if(($ai = $runtime.getAttributeIndex("","value"))>=0) {
                    $runtime.consumeAttribute($ai);
                    $runtime.sendLeaveElement(super._cookie, $__uri, $__local, $__qname);
                }
                else {
                    unexpectedLeaveElement($__qname);
                }
            }
            break;
        case 4:
            {
                if((((((((((((($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("minExclusive")) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("maxExclusive"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("minInclusive"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("maxInclusive"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("totalDigits"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("fractionDigits"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("length"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("maxLength"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("minLength"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("enumeration"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("whiteSpace"))) || ($__uri.equals("http://www.w3.org/2001/XMLSchema") && $__local.equals("pattern")))) {
                    NGCCHandler h = new foreignAttributes(this, super._source, $runtime, 192, fa);
                    spawnChildFromLeaveElement(h, $__uri, $__local, $__qname);
                }
                else {
                    unexpectedLeaveElement($__qname);
                }
            }
            break;
        case 2:
            {
                $_ngcc_current_state = 1;
                $runtime.sendLeaveElement(super._cookie, $__uri, $__local, $__qname);
            }
            break;
        default:
            {
                unexpectedLeaveElement($__qname);
            }
            break;
        }
    }

    public void enterAttribute(String $__uri, String $__local, String $__qname) throws SAXException {
        int $ai;
        $uri = $__uri;
        $localName = $__local;
        $qname = $__qname;
        switch($_ngcc_current_state) {
        case 5:
            {
                if(($__uri.equals("") && $__local.equals("fixed"))) {
                    $_ngcc_current_state = 7;
                }
                else {
                    $_ngcc_current_state = 4;
                    $runtime.sendEnterAttribute(super._cookie, $__uri, $__local, $__qname);
                }
            }
            break;
        case 0:
            {
                revertToParentFromEnterAttribute(result, super._cookie, $__uri, $__local, $__qname);
            }
            break;
        case 11:
            {
                if(($__uri.equals("") && $__local.equals("value"))) {
                    $_ngcc_current_state = 10;
                }
                else {
                    unexpectedEnterAttribute($__qname);
                }
            }
            break;
        case 2:
            {
                $_ngcc_current_state = 1;
                $runtime.sendEnterAttribute(super._cookie, $__uri, $__local, $__qname);
            }
            break;
        default:
            {
                unexpectedEnterAttribute($__qname);
            }
            break;
        }
    }

    public void leaveAttribute(String $__uri, String $__local, String $__qname) throws SAXException {
        int $ai;
        $uri = $__uri;
        $localName = $__local;
        $qname = $__qname;
        switch($_ngcc_current_state) {
        case 5:
            {
                $_ngcc_current_state = 4;
                $runtime.sendLeaveAttribute(super._cookie, $__uri, $__local, $__qname);
            }
            break;
        case 9:
            {
                if(($__uri.equals("") && $__local.equals("value"))) {
                    $_ngcc_current_state = 5;
                }
                else {
                    unexpectedLeaveAttribute($__qname);
                }
            }
            break;
        case 0:
            {
                revertToParentFromLeaveAttribute(result, super._cookie, $__uri, $__local, $__qname);
            }
            break;
        case 2:
            {
                $_ngcc_current_state = 1;
                $runtime.sendLeaveAttribute(super._cookie, $__uri, $__local, $__qname);
            }
            break;
        case 6:
            {
                if(($__uri.equals("") && $__local.equals("fixed"))) {
                    $_ngcc_current_state = 4;
                }
                else {
                    unexpectedLeaveAttribute($__qname);
                }
            }
            break;
        default:
            {
                unexpectedLeaveAttribute($__qname);
            }
            break;
        }
    }

    public void text(String $value) throws SAXException {
        int $ai;
        switch($_ngcc_current_state) {
        case 10:
            {
                value = $value;
                $_ngcc_current_state = 9;
            }
            break;
        case 5:
            {
                if(($ai = $runtime.getAttributeIndex("","fixed"))>=0) {
                    $runtime.consumeAttribute($ai);
                    $runtime.sendText(super._cookie, $value);
                }
                else {
                    $_ngcc_current_state = 4;
                    $runtime.sendText(super._cookie, $value);
                }
            }
            break;
        case 0:
            {
                revertToParentFromText(result, super._cookie, $value);
            }
            break;
        case 11:
            {
                if(($ai = $runtime.getAttributeIndex("","value"))>=0) {
                    $runtime.consumeAttribute($ai);
                    $runtime.sendText(super._cookie, $value);
                }
            }
            break;
        case 7:
            {
                fixed = $value;
                $_ngcc_current_state = 6;
            }
            break;
        case 2:
            {
                $_ngcc_current_state = 1;
                $runtime.sendText(super._cookie, $value);
            }
            break;
        }
    }

    public void onChildCompleted(Object $__result__, int $__cookie__, boolean $__needAttCheck__)throws SAXException {
        switch($__cookie__) {
        case 192:
            {
                fa = ((ForeignAttributesImpl)$__result__);
                $_ngcc_current_state = 2;
            }
            break;
        case 190:
            {
                annotation = ((AnnotationImpl)$__result__);
                $_ngcc_current_state = 1;
            }
            break;
        }
    }

    public boolean accepted() {
        return(($_ngcc_current_state == 0));
    }


                private FacetImpl result;
                private Locator locator;

}
