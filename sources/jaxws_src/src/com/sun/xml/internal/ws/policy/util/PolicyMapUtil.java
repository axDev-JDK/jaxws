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

package com.sun.xml.internal.ws.policy.util;

import com.sun.xml.internal.ws.policy.Policy;
import com.sun.xml.internal.ws.policy.PolicyException;
import com.sun.xml.internal.ws.policy.PolicyMap;
import com.sun.xml.internal.ws.policy.privateutil.LocalizationMessages;
import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;

/**
 * Utility methods for PolicyMap
 */
public final class PolicyMapUtil {
    private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyMapUtil.class);
    
    /**
     * Throw an exception if the policy map contains any policy with at least two
     * policy alternatives.
     *
     * Optional assertions are not considered (unless they have been normalized into
     * two policy alternatives).
     *
     * @param map policy map to be processed
     * @throws PolicyException Thrown if the policy map contains at least one policy
     * with more than one policy alternative
     */
    public static void rejectAlternatives(final PolicyMap map) throws PolicyException {
        for (Policy policy : map) {
            if (policy.getNumberOfAssertionSets() > 1) {
                throw LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0035_RECONFIGURE_ALTERNATIVES(policy.getIdOrName())));
            }            
        }
                
//        Collection<PolicyMapKey> keys = map.getAllServiceScopeKeys();
//        for (PolicyMapKey key : keys) {
//            final Policy policy = map.getServiceEffectivePolicy(key);
//            if (policy.getNumberOfAssertionSets() > 1) {
//                throw LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0035_RECONFIGURE_ALTERNATIVES(policy.getIdOrName())));
//            }
//        }
//        keys = map.getAllEndpointScopeKeys();
//        for (PolicyMapKey key : keys) {
//            final Policy policy = map.getEndpointEffectivePolicy(key);
//            if (policy.getNumberOfAssertionSets() > 1) {
//                throw LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0035_RECONFIGURE_ALTERNATIVES(policy.getIdOrName())));
//            }
//        }
//        keys = map.getAllOperationScopeKeys();
//        for (PolicyMapKey key : keys) {
//            final Policy policy = map.getOperationEffectivePolicy(key);
//            if (policy.getNumberOfAssertionSets() > 1) {
//                throw LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0035_RECONFIGURE_ALTERNATIVES(policy.getIdOrName())));
//            }
//        }
//        keys = map.getAllInputMessageScopeKeys();
//        for (PolicyMapKey key : keys) {
//            final Policy policy = map.getInputMessageEffectivePolicy(key);
//            if (policy.getNumberOfAssertionSets() > 1) {
//                throw LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0035_RECONFIGURE_ALTERNATIVES(policy.getIdOrName())));
//            }
//        }
//        keys = map.getAllOutputMessageScopeKeys();
//        for (PolicyMapKey key : keys) {
//            final Policy policy = map.getOutputMessageEffectivePolicy(key);
//            if (policy.getNumberOfAssertionSets() > 1) {
//                throw LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0035_RECONFIGURE_ALTERNATIVES(policy.getIdOrName())));
//            }
//        }
//        keys = map.getAllFaultMessageScopeKeys();
//        for (PolicyMapKey key : keys) {
//            final Policy policy = map.getFaultMessageEffectivePolicy(key);
//            if (policy.getNumberOfAssertionSets() > 1) {
//                throw LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0035_RECONFIGURE_ALTERNATIVES(policy.getIdOrName())));
//            }
//        }
    }
}
