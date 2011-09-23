/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.xml.internal.ws.developer;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.xml.ws.spi.WebServiceFeatureAnnotation;

/**
 * This feature represents the use of WS-Addressing with either
 * the SOAP 1.1/HTTP or SOAP 1.2/HTTP binding.  Using this feature
 * with any other binding is NOT required.
 * <p>
 * Enabling this feature will result in the
 * <code>wsaw:UsingAddressing</code> element being added to the
 * <code>wsdl:Binding</code> for
 * the endpoint and in the runtime being capable of responding to
 * WS-Addressing headers.
 * <p>
 * The following describes the affects of this feature with respect
 * to be enabled or disabled:
 * <ul>
 *  <li> ENABLED: In this Mode, Addressing will be enabled.
 *       If there is not a WSDL associated with the Endpoint and
 *       a WSDL is to be generated, it MUST be generated with the
 *       wsaw:UsingAddressing element. At runtime, Addressing headers
 *       MUST be consumed by the receiver and generated by the
 *       sender even if the WSDL declares otherwise. The
 *       mustUnderstand="0" attribute MUST be used on the Addressing
 *       headers.
 *  <li> DISABLED: In this Mode, Addressing will be disabled
 *       even if an associated WSDL specifies otherwise. At runtime,
 *       Addressing headers MUST NOT be used.
 * </ul>
 * <p>
 * The {@link #required} property can be used to
 * specify if the <code>required</code> attribute on the
 * <code>wsaw:UsingAddressing</code> element should
 * be <code>true</code> or <code>false</code>.  By default the
 * <code>wsdl:required</code> parameter is <code>false</code>.
 *
 * See <a href="http://www.w3.org/TR/2006/REC-ws-addr-core-20060509/">WS-Addressing</a>
 * for more information on WS-Addressing.
 * See <a href="http://www.w3.org/TR/2006/CR-ws-addr-wsdl-20060529/">WS-Addressing - WSDL 1.0
 * </a> for more information on <code>wsaw:UsingAddressing</code>.
 *
 * @since JAX-WS 2.1
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WebServiceFeatureAnnotation(id= MemberSubmissionAddressingFeature.ID,bean=MemberSubmissionAddressingFeature.class)
public @interface MemberSubmissionAddressing {
    /**
     * Specifies if this feature is enabled or disabled.
     */
    boolean enabled() default true;

    /**
     * Property to determine the value of the
     * <code>wsdl:required</code> attribute on
     * <code>wsaw:UsingAddressing</code> element in the WSDL.
     */
    boolean required() default false;

    /**
     * Property to determine if the incoming messsages should be checked for conformance
     * with MemberSubmission version of WS-Addressing.
     *
     * If Validation.LAX, then some WS-Adressing headers are not strictly checked.
     */
    public enum Validation { LAX, STRICT }

    Validation validation() default Validation.LAX;                   

}
