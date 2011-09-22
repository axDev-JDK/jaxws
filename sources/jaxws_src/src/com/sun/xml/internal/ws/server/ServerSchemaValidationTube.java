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

package com.sun.xml.internal.ws.server;

import com.sun.xml.internal.ws.api.WSBinding;
import com.sun.xml.internal.ws.api.SOAPVersion;
import com.sun.xml.internal.ws.api.model.SEIModel;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.internal.ws.api.message.Packet;
import com.sun.xml.internal.ws.api.message.Message;
import com.sun.xml.internal.ws.api.pipe.Tube;
import com.sun.xml.internal.ws.api.pipe.TubeCloner;
import com.sun.xml.internal.ws.api.pipe.NextAction;
import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
import com.sun.xml.internal.ws.api.server.SDDocument;
import com.sun.xml.internal.ws.api.server.SDDocumentSource;
import com.sun.xml.internal.ws.api.server.ServiceDefinition;
import com.sun.xml.internal.ws.api.server.WSEndpoint;
import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import com.sun.xml.internal.ws.util.MetadataUtil;
import com.sun.xml.internal.ws.util.pipe.AbstractSchemaValidationTube;
import com.sun.xml.internal.ws.util.xml.MetadataDocument;
import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Tube} that does the schema validation on the server side.
 *
 * @author Jitendra Kotamraju
 */
public class ServerSchemaValidationTube extends AbstractSchemaValidationTube {

    private static final Logger LOGGER = Logger.getLogger(ServerSchemaValidationTube.class.getName());

    //private final ServiceDefinition docs;
    private final Schema schema;
    private final Validator validator;
    
    private final boolean noValidation;
    private final SEIModel seiModel;
    private final WSDLPort wsdlPort;

    public ServerSchemaValidationTube(WSEndpoint endpoint, WSBinding binding,
            SEIModel seiModel, WSDLPort wsdlPort, Tube next) {
        super(binding, next);
        this.seiModel = seiModel;
        this.wsdlPort = wsdlPort;
        //docs = endpoint.getServiceDefinition();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            sf.setFeature(HONOUR_ALL_SCHEMA_LOCATIONS_ID, true);
       } catch(Exception e) {
            // xerces 2.7 supports this feature. So just ignore the exception.
        }
        Source[] sources = getSchemaSources(endpoint.getServiceDefinition());
        for(Source source : sources) {
            LOGGER.fine("Constructing validation Schema from = "+source.getSystemId());
            //printDOM((DOMSource)source);
        }
        if (sources.length != 0) {
            noValidation = false;
            sf.setResourceResolver(new MetadataResolverImpl(endpoint.getServiceDefinition()));
            try {
                schema = sf.newSchema(sources);
            } catch(SAXException e) {
                throw new WebServiceException(e);
            }
            validator = schema.newValidator();
            try {
                validator.setFeature(HONOUR_ALL_SCHEMA_LOCATIONS_ID, true);
            } catch(Exception e) {
                // xerces 2.7 supports this feature. So just ignore the exception.
            }            
        } else {
            noValidation = true;
            schema = null;
            validator = null;
        }
    }

    /**
     * Constructs list of schema documents as follows:
     *   - all <xsd:schema> fragements from all WSDL documents.
     *   - all schema documents in the application(from WAR etc)
     *
     * @param sd wsdl/schema document
     * @return list of root schema documents
     */
    private Source[] getSchemaSources(ServiceDefinition sd) {
        String primary = sd.getPrimary().getURL().toExternalForm();
        MetadataUtil.MetadataResolver mdresolver = new MetadataResolverImpl(sd);
        return super.getSchemaSources(primary, mdresolver);
    }

    private class MetadataResolverImpl implements MetadataUtil.MetadataResolver, LSResourceResolver {

        Map<String, SDDocument> docs = new HashMap<String, SDDocument>();

        MetadataResolverImpl(ServiceDefinition sd) {
            for(SDDocument doc : sd) {
                docs.put(doc.getURL().toExternalForm(), doc);
            }
        }

        public SDDocument resolveEntity(String systemId) {
            SDDocument sdi = docs.get(systemId);
            if (sdi == null) {
                SDDocumentSource sds;
                try {
                    sds = SDDocumentSource.create(new URL(systemId));
                } catch(MalformedURLException e) {
                    throw new WebServiceException(e);
                }
                sdi = MetadataDocument.create(sds, new QName(""), new QName(""));
                docs.put(systemId, sdi);
            }
            return sdi;
        }

        public LSInput resolveResource(String type, String namespaceURI, String publicId, final String systemId, final String baseURI) {
            LOGGER.fine("type="+type+ " namespaceURI="+namespaceURI+" publicId="+publicId+" systemId="+systemId+" baseURI="+baseURI);
            try {
                URL base = baseURI == null ? null : new URL(baseURI);
                final URL rel = new URL(base, systemId);
                final SDDocument doc = docs.get(rel.toExternalForm());
                if (doc != null) {
                    return new LSInput() {

                        public Reader getCharacterStream() {
                            return null;
                        }

                        public void setCharacterStream(Reader characterStream) {
                            throw new UnsupportedOperationException();
                        }

                        public InputStream getByteStream() {
                            ByteArrayBuffer bab = new ByteArrayBuffer();
                            try {
                                doc.writeTo(null, resolver, bab);
                            } catch (IOException ioe) {
                                throw new WebServiceException(ioe);
                            }
                            return bab.newInputStream();
                        }

                        public void setByteStream(InputStream byteStream) {
                            throw new UnsupportedOperationException();
                        }

                        public String getStringData() {
                            return null;
                        }

                        public void setStringData(String stringData) {
                            throw new UnsupportedOperationException();
                        }

                        public String getSystemId() {
                            return rel.toExternalForm();
                        }

                        public void setSystemId(String systemId) {
                            throw new UnsupportedOperationException();
                        }

                        public String getPublicId() {
                            return null;
                        }

                        public void setPublicId(String publicId) {
                            throw new UnsupportedOperationException();
                        }

                        public String getBaseURI() {
                            return rel.toExternalForm();
                        }

                        public void setBaseURI(String baseURI) {
                            throw new UnsupportedOperationException();
                        }

                        public String getEncoding() {
                            return null;
                        }

                        public void setEncoding(String encoding) {
                            throw new UnsupportedOperationException();
                        }

                        public boolean getCertifiedText() {
                            return false;
                        }

                        public void setCertifiedText(boolean certifiedText) {
                            throw new UnsupportedOperationException();
                        }
                    };
                }
            } catch(Exception e) {
                LOGGER.log(Level.WARNING, "Exception in LSResourceResolver impl", e);
            }
            LOGGER.fine("Don't know about systemId="+systemId+" baseURI="+baseURI);
            return null;
        }

    }


    protected Validator getValidator() {
        return validator;
    }

    protected boolean isNoValidation() {
        return noValidation;
    }

    @Override
    public NextAction processRequest(Packet request) {
        if (isNoValidation() || !request.getMessage().hasPayload() || request.getMessage().isFault()) {
            return super.processRequest(request);
        }
        try {
            doProcess(request);
        } catch(SAXException se) {
            LOGGER.log(Level.WARNING, "Client Request doesn't pass Service's Schema Validation", se);
            // Client request is invalid. So sending specific fault code
            // Also converting this to fault message so that handlers may get
            // to see the message.
            SOAPVersion soapVersion = binding.getSOAPVersion();
            Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(
                    soapVersion, null, se, soapVersion.faultCodeClient);
            return doReturnWith(request.createServerResponse(faultMsg,
                    wsdlPort, seiModel, binding));
        }
        return super.processRequest(request);
    }

    @Override
    public NextAction processResponse(Packet response) {
        if (isNoValidation() || response.getMessage() == null || !response.getMessage().hasPayload() || response.getMessage().isFault()) {
            return super.processResponse(response);
        }
        try {
            doProcess(response);
        } catch(SAXException se) {
            // TODO: Should we convert this to fault Message ??
            throw new WebServiceException(se);
        }
        return super.processResponse(response);
    }

    protected ServerSchemaValidationTube(ServerSchemaValidationTube that, TubeCloner cloner) {
        super(that,cloner);
        //this.docs = that.docs;
        this.schema = that.schema;      // Schema is thread-safe
        this.validator = schema.newValidator();
        this.noValidation = that.noValidation;
        this.seiModel = that.seiModel;
        this.wsdlPort = that.wsdlPort;
    }

    public AbstractTubeImpl copy(TubeCloner cloner) {
        return new ServerSchemaValidationTube(this,cloner);
    }

}
