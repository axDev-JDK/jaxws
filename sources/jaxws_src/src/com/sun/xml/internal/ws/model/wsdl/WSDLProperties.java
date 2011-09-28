/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.xml.internal.ws.model.wsdl;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.PropertySet;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;

/**
 * Properties exposed from {@link WSDLPort} for {@link MessageContext}.
 * Donot add this satellite if {@link WSDLPort} is null.
 *
 * @author Jitendra Kotamraju
 */
public final class WSDLProperties extends PropertySet {

    private static final PropertyMap model;
    static {
        model = parse(WSDLProperties.class);
    }

    private final @NotNull WSDLPort port;

    public WSDLProperties(@NotNull WSDLPort port) {
        this.port = port;
    }

    @Property(MessageContext.WSDL_SERVICE)
    public QName getWSDLService() {
        return port.getOwner().getName();
    }

    @Property(MessageContext.WSDL_PORT)
    public QName getWSDLPort() {
        return port.getName();
    }

    @Property(MessageContext.WSDL_INTERFACE)
    public QName getWSDLPortType() {
        return port.getBinding().getPortTypeName();
    }

    @Override
    protected PropertyMap getPropertyMap() {
        return model;
    }

}
