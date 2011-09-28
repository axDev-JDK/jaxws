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

/* Generated By:JavaCC: Do not edit this line. SCDParserConstants.java */
package com.sun.xml.internal.xsom.impl.scd;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface SCDParserConstants {

  int EOF = 0;
  int Letter = 6;
  int BaseChar = 7;
  int Ideographic = 8;
  int CombiningChar = 9;
  int UnicodeDigit = 10;
  int Extender = 11;
  int NCNAME = 12;
  int NUMBER = 13;
  int FACETNAME = 14;

  int DEFAULT = 0;

static final List<String> tokenImage = Collections.unmodifiableList(Arrays.asList(
        new String[] {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "<Letter>",
    "<BaseChar>",
    "<Ideographic>",
    "<CombiningChar>",
    "<UnicodeDigit>",
    "<Extender>",
    "<NCNAME>",
    "<NUMBER>",
    "<FACETNAME>",
    "\":\"",
    "\"/\"",
    "\"//\"",
    "\"attribute::\"",
    "\"@\"",
    "\"element::\"",
    "\"substitutionGroup::\"",
    "\"type::\"",
    "\"~\"",
    "\"baseType::\"",
    "\"primitiveType::\"",
    "\"itemType::\"",
    "\"memberType::\"",
    "\"scope::\"",
    "\"attributeGroup::\"",
    "\"group::\"",
    "\"identityContraint::\"",
    "\"key::\"",
    "\"notation::\"",
    "\"model::sequence\"",
    "\"model::choice\"",
    "\"model::all\"",
    "\"model::*\"",
    "\"any::*\"",
    "\"anyAttribute::*\"",
    "\"facet::*\"",
    "\"facet::\"",
    "\"component::*\"",
    "\"x-schema::\"",
    "\"x-schema::*\"",
    "\"*\"",
    "\"0\"",
  }));
}
