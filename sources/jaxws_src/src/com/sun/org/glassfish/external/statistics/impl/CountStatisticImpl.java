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
import com.sun.org.glassfish.external.statistics.CountStatistic;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;
import java.lang.reflect.*;

/** 
 * @author Sreenivas Munnangi
 */
public final class CountStatisticImpl extends StatisticImpl
    implements CountStatistic, InvocationHandler {
    
    private long count = 0L;
    private final long initCount;

    private final CountStatistic cs = 
            (CountStatistic) Proxy.newProxyInstance(
            CountStatistic.class.getClassLoader(),
            new Class[] { CountStatistic.class },
            this);

    public CountStatisticImpl(long countVal, String name, String unit, 
                              String desc, long sampleTime, long startTime) {
        super(name, unit, desc, startTime, sampleTime);
        count = countVal;
        initCount = countVal;
    }
    
    public CountStatisticImpl(String name, String unit, String desc) {
        this(0L, name, unit, desc, -1L, System.currentTimeMillis());
    }

    public synchronized CountStatistic getStatistic() {
        return cs;
    }

    public synchronized Map getStaticAsMap() {
        Map m = super.getStaticAsMap();
        m.put("count", getCount());
        return m;
    }

    public synchronized String toString() {
        return super.toString() + NEWLINE + "Count: " + getCount();
    }

    public synchronized long getCount() {
        return count;
    }

    public synchronized void setCount(long countVal) {
        count = countVal;
        sampleTime = System.currentTimeMillis();
    }

    public synchronized void increment() {
        count++;
        sampleTime = System.currentTimeMillis();
    }

    public synchronized void increment(long delta) {
        count = count + delta;
        sampleTime = System.currentTimeMillis();
    }

    public synchronized void decrement() {
        count--;
        sampleTime = System.currentTimeMillis();
    }

    @Override
    public synchronized void reset() {
        super.reset();
        count = initCount;
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
