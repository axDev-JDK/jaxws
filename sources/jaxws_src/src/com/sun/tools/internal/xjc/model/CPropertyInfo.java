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

package com.sun.tools.internal.xjc.model;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

import com.sun.codemodel.internal.JClass;
import com.sun.codemodel.internal.JJavaName;
import com.sun.codemodel.internal.JType;
import com.sun.tools.internal.xjc.Plugin;
import com.sun.tools.internal.xjc.generator.bean.field.FieldRenderer;
import com.sun.tools.internal.xjc.model.nav.NClass;
import com.sun.tools.internal.xjc.model.nav.NType;
import com.sun.xml.internal.bind.api.impl.NameConverter;
import com.sun.xml.internal.bind.v2.WellKnownNamespace;
import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;
import com.sun.xml.internal.xsom.XSComponent;

import org.xml.sax.Locator;

/**
 * Model of a property to be generated.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class CPropertyInfo implements PropertyInfo<NType,NClass>, CCustomizable {

    @XmlTransient
    private CClassInfo parent;
    private String privateName;
    private String publicName;
    private final boolean isCollection;

    @XmlTransient
    public final Locator locator;

    /**
     * @see #getSchemaComponent()
     */
    private final XSComponent source;

    /**
     * If the base type of the property is overriden,
     * this field is set to non-null.
     */
    public JType baseType;

    /**
     * Javadoc for this property. Must not be null.
     */
    public String javadoc="";

    /**
     * Property with {@link @XmlInlineBinaryData}.
     */
    public boolean inlineBinaryData;

    /**
     * Specifies how the field is generated by the backend.
     */
    @XmlJavaTypeAdapter(RuntimeUtil.ToStringAdapter.class)
    public FieldRenderer realization;

    /**
     * If non-null, keeps the default value in Java representation.
     *
     * If {@link #isCollection} is true, this field is always null,
     * for we don't handle default values for a list.
     */
    public CDefaultValue defaultValue;

    private final CCustomizations customizations;

    protected CPropertyInfo(String name, boolean collection, XSComponent source,
                            CCustomizations customizations, Locator locator) {
        this.publicName = name;
        String n = NameConverter.standard.toVariableName(name);
        if(!JJavaName.isJavaIdentifier(n))
            n = '_'+n;  // avoid colliding with the reserved names like 'abstract'.
        this.privateName = n;

        this.isCollection = collection;
        this.locator = locator;
        if(customizations==null)
            this.customizations = CCustomizations.EMPTY;
        else
            this.customizations = customizations;
        this.source = source;
    }

    // called from CClassInfo when added
    final void setParent( CClassInfo parent ) {
        assert this.parent==null;
        assert parent!=null;
        this.parent = parent;
        customizations.setParent(parent.model,this);
    }

    public CTypeInfo parent() {
        return parent;
    }

    public Locator getLocator() {
        return locator;
    }

    /**
     * If this model object is built from XML Schema,
     * this property returns a schema component from which the model is built.
     *
     * @return
     *      null if the model is built from sources other than XML Schema
     *      (such as DTD.)
     */
    public final XSComponent getSchemaComponent() {
        return source;
    }

    public abstract CAdapter getAdapter();

    /**
     * Name of the property.
     *
     * <p>
     * This method is implemented to follow the contract of
     * {@link PropertyInfo#getName()}, and therefore it always
     * returns the name of the annotated field.
     * <p>
     * This name is normally not useful for the rest of XJC,
     * which usually wants to access the "public name" of the property.
     * A "public name" of the property is a name like "FooBar" which
     * is used as a seed for generating the accessor methods.
     * This is the name controlled by the schema customization via users.
     *
     * <p>
     * If the caller is calling this method statically, it's usually
     * the sign of a mistake. Use {@link #getName(boolean)} method instead,
     * which forces you to think about which name you want to get.
     *
     * @deprecated
     *      marked as deprecated so that we can spot the use of this method.
     * 
     * @see #getName(boolean)
     */
    public String getName() {
        return getName(false);
    }

    /**
     * Gets the name of the property.
     *
     * @param isPublic
     *      if true, this method returns a name like "FooBar", which
     *      should be used as a seed for generating user-visible names
     *      (such as accessors like "getFooBar".)
     *
     *      <p>
     *      if false, this method returns the "name of the property"
     *      as defined in the j2s side of the spec. This name is usually
     *      something like "fooBar", which often corresponds to the XML
     *      element/attribute name of this property (for taking advantage
     *      of annotation defaulting as much as possible)
     */
    public String getName(boolean isPublic) {
        return isPublic?publicName:privateName;
    }

    /**
     * Overrides the name of the property.
     *
     * This method can be used from {@link Plugin#postProcessModel(Model, ErrorHandler)}.
     * But the caller should do so with the understanding that this is inherently
     * dangerous method.
     */
    public void setName(boolean isPublic, String newName) {
        if(isPublic)
            publicName = newName;
        else
            privateName = newName;
    }

    public String displayName() {
        return parent.toString()+'#'+getName(false);
    }

    public boolean isCollection() {
        return isCollection;
    }


    public abstract Collection<? extends CTypeInfo> ref();

    /**
     * Returns true if this property is "unboxable".
     *
     * <p>
     * In general, a property often has to be capable of representing null
     * to indicate the absence of the value. This requires properties
     * to be generated as <tt>@XmlElement Float f</tt>, not as
     * <tt>@XmlElement float f;</tt>. But this is slow.
     *
     * <p>
     * Fortunately, there are cases where we know that the property can
     * never legally be absent. When this condition holds we can generate
     * the optimized "unboxed form".
     *
     * <p>
     * The exact such conditions depend
     * on the kind of properties, so refer to the implementation code
     * for the details.
     *
     * <p>
     * This method returns true when the property can be generated
     * as "unboxed form", false otherwise.
     *
     * <p>
     * When this property is a collection, this method returns true
     * if items in the collection is unboxable. Obviously, the collection
     * itself is always a reference type.
     */
    public boolean isUnboxable() {
        Collection<? extends CTypeInfo> ts = ref();
        if(ts.size()!=1)
            // if the property is heterogeneous, no way.
            // ts.size()==0 is a special case that can happen for wildcards.
            return false;

        if(baseType!=null && (baseType instanceof JClass))
            return false;

        CTypeInfo t = ts.iterator().next();
        // only a primitive type is eligible.
        return t.getType().isBoxedType();
    }

    /**
     * Returns true if this property needs to represent null
     * just for the purpose of representing an absence of the property.
     */
    public boolean isOptionalPrimitive() {
        return false;
    }

    public CCustomizations getCustomizations() {
        return customizations;
    }

    public boolean inlineBinaryData() {
        return inlineBinaryData;
    }

    public abstract <V> V accept( CPropertyVisitor<V> visitor );

    /**
     * Checks if the given {@link TypeUse} would need an explicit {@link XmlSchemaType}
     * annotation with the given type name.
     */
    protected static boolean needsExplicitTypeName(TypeUse type, QName typeName) {
        if(typeName==null)
            // this is anonymous type. can't have @XmlSchemaType
            return false;

        if(!typeName.getNamespaceURI().equals(WellKnownNamespace.XML_SCHEMA))
            // if we put application-defined type name, it will be undefined
            // by the time we generate a schema.
            return false;

        if(type.isCollection())
            // there's no built-in binding for a list simple type,
            // so any collection type always need @XmlSchemaType
            return true;

        QName itemType = type.getInfo().getTypeName();
        if(itemType==null)
            // this is somewhat strange case, as it means the bound type is anonymous
            // but it's eventually derived by a named type and used.
            // but we can certainly use typeName as @XmlSchemaType value here
            return true;

        // if it's the default type name for this item, then no need
        return !itemType.equals(typeName);
    }

    /**
     * Puts the element names that this property possesses to the map,
     * so that we can find two properties that own the same element name,
     * which is an error.
     *
     * @return
     *      null if no conflict was found. Otherwise return the QName that has the collision.
     */
    public QName collectElementNames(Map<QName,CPropertyInfo> table) {
        return null;
    }

    public final <A extends Annotation> A readAnnotation(Class<A> annotationType) {
        throw new UnsupportedOperationException();
    }

    public final boolean hasAnnotation(Class<? extends Annotation> annotationType) {
        throw new UnsupportedOperationException();
    }
}
