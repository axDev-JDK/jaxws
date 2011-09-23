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

import com.sun.xml.internal.ws.api.FeatureConstructor;

import javax.jws.WebService;
import javax.xml.ws.WebServiceFeature;

import com.sun.org.glassfish.gmbal.ManagedAttribute;
import com.sun.org.glassfish.gmbal.ManagedData;


/**
 * Designates a stateful {@link WebService}.
 * A service class that has this feature on will behave as a stateful web service.
 *
 * @since 2.1
 * @see StatefulWebServiceManager
 */
@ManagedData
public class StatefulFeature extends WebServiceFeature {
    /**
     * Constant value identifying the StatefulFeature
     */
    public static final String ID = "http://jax-ws.dev.java.net/features/stateful";

    /**
     * Create an <code>StatefulFeature</code>.
     * The instance created will be enabled.
     */
    @FeatureConstructor
    public StatefulFeature() {
        this.enabled = true;
    }

    @ManagedAttribute
    public String getID() {
        return ID;
    }
}
