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

package com.sun.xml.internal.ws.policy.sourcemodel;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Marek Potociar, Jakub Podlesak
 */
public final class PolicySourceModelContext {
    
    Map<URI,PolicySourceModel> policyModels;
    
    /** 
     * Private constructor prevents instantiation of the instance from outside of the class
     */
    private PolicySourceModelContext() {
        // nothing to initialize
    }
    
    private Map<URI,PolicySourceModel> getModels() {
        if (null==policyModels) {
            policyModels = new HashMap<URI,PolicySourceModel>();
        }
        return policyModels;
    }
    
    public void addModel(final URI modelUri, final PolicySourceModel model) {
        getModels().put(modelUri,model);
    }
    
    public static PolicySourceModelContext createContext() {
        return new PolicySourceModelContext();
    }
    
    public boolean containsModel(final URI modelUri) {
        return getModels().containsKey(modelUri);
    }
    
    PolicySourceModel retrieveModel(final URI modelUri) {
        return getModels().get(modelUri);
    }
    
    PolicySourceModel retrieveModel(final URI modelUri, final URI digestAlgorithm, final String digest) {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return policyModels.toString();
    }
}
