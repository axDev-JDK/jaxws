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

package com.sun.xml.internal.ws.api.policy;

import com.sun.xml.internal.ws.util.ServiceFinder;
import com.sun.xml.internal.ws.policy.DefaultPolicyResolver;

/**
 * PolicyResolverFactory provides a way to override Effective Policy Resolution for a Service or Client.
 * JAX-WS provides DEFAULT_POLICY_RESOLVER implementation that
 *      on server-side validates that Policy has single alternative in the scope of each subject
 *      on client-side updates with the effective policy by doing alternative selection.
 *
 * Extensions can override this to consult other forms of configuration to give the effective PolicyMap.
 * 
 * @author Rama Pulavarthi
 */
public abstract class PolicyResolverFactory {

    public abstract PolicyResolver doCreate();

    public static PolicyResolver create(){
        for (PolicyResolverFactory factory : ServiceFinder.find(PolicyResolverFactory.class)) {
            PolicyResolver policyResolver = factory.doCreate();
            if (policyResolver != null) {
                return policyResolver;
            }
        }
         // return default policy resolver.
        return DEFAULT_POLICY_RESOLVER;
    }

    /**
     * JAX-WS provided DEFAULT_POLICY_RESOLVER implementation that
     *      on server-side validates that Policy has single alternative in the scope of each subject
     *      on client-side updates with the effective policy by doing alternative selection.
     */
    public static final PolicyResolver DEFAULT_POLICY_RESOLVER =  new DefaultPolicyResolver();

    
}
