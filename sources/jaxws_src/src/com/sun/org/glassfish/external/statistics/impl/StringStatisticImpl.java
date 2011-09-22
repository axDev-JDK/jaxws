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

package com.sun.org.glassfish.external.statistics.impl;
import com.sun.org.glassfish.external.statistics.StringStatistic;
import java.util.Map;
import java.lang.reflect.*;

/** 
 * @author Sreenivas Munnangi
 */
public final class StringStatisticImpl extends StatisticImpl
    implements StringStatistic, InvocationHandler {
    
    private volatile String str = null;
    private final String initStr;

    private final StringStatistic ss = 
            (StringStatistic) Proxy.newProxyInstance(
            StringStatistic.class.getClassLoader(),
            new Class[] { StringStatistic.class },
            this);

    public StringStatisticImpl(String str, String name, String unit, 
                              String desc, long sampleTime, long startTime) {
        super(name, unit, desc, startTime, sampleTime);
        this.str = str;
        initStr = str;
    }
    
    public StringStatisticImpl(String name, String unit, String desc) {
        this("", name, unit, desc, System.currentTimeMillis(), System.currentTimeMillis());
    }
    
    public synchronized StringStatistic getStatistic() {
        return ss;
    }

    public synchronized Map getStaticAsMap() {
        Map m = super.getStaticAsMap();
        if (getCurrent() != null) {
            m.put("current", getCurrent());
        }
        return m;
    }

    public synchronized String toString() {
        return super.toString() + NEWLINE + "Current-value: " + getCurrent();
    }

    public String getCurrent() {
        return str;
    }

    public void setCurrent(String str) {
        this.str = str;
        sampleTime = System.currentTimeMillis();
    }

    @Override
    public synchronized void reset() {
        super.reset();
        this.str = initStr;
        sampleTime = -1L;
    }

    // todo: equals implementation
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        Object result;
        try {
            result = m.invoke(this, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " +
                       e.getMessage());
        } finally {
        }
        return result;
    }
}
