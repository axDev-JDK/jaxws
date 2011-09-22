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
 

package com.sun.org.glassfish.gmbal;

import java.util.Map;
import com.sun.org.glassfish.external.amx.AMX;


/** Base interface supported by all AMXMBeanInterface MBeans.  All MBeans generated by
 * gmbal comply with this interface, which means that the attributes and
 * operations defined in this Java interface all appear in each
 * MBean generated by calling ManagedObjectManager.register.
 *
 * @author LLoyd Chambers
 * @author Ken Cavanaugh
 *
 */
@ManagedObject
@Description( "Base interface for any MBean that works in the AMX framework" )
public interface AMXMBeanInterface {
    /** Get all metadata about this MBean.
     * @return The descriptor, which will be a ModelMBeanInfoSupport instance.
     */
    public Map<String,?> getMeta();

    /** Usually the same as the ObjectName 'name' property, but can differ
        if the actual name contains characters that must be escaped for an ObjectName and/or
        if the MBean has a mutable name attribute.
       The type property can be obtained from the ObjectName */
    @ManagedAttribute( id=AMX.ATTR_NAME )
    @Description( "Return the name of this MBean.")
    public String getName();

    /** "go up one level": the MBean containing this one, can be null for root
     * @return The container of this MBean (null if already at root).
     */
    @ManagedAttribute( id=AMX.ATTR_PARENT )
    @Description( "The container that contains this MBean" )
    public AMXMBeanInterface getParent();

    /** Containment hierarchy:
        Get all AMXMBeanInterface contained by this one, in no particular order.
        Valid only if isContainer().
     * Note that using an array sidesteps Map/Set/OpenType issues
     * @return All children of this AMXMBeanInterface MBean.
     */
    @ManagedAttribute( id=AMX.ATTR_CHILDREN )
    @Description( "All children of this AMX MBean")
    public AMXMBeanInterface[] getChildren();
}
