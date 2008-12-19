/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.webbeans.servlet;

import java.util.Enumeration;

import javax.servlet.ServletContext;

import org.jboss.webbeans.contexts.AbstractBeanMapAdaptor;
import org.jboss.webbeans.contexts.ApplicationContext;

/**
 * A BeanMap that uses a servlet context as backing map
 * 
 * @author Nicklas Karlsson
 * 
 * @see org.jboss.webbeans.contexts.ApplicationContext
 */
public class ApplicationBeanMap extends AbstractBeanMapAdaptor
{
   // The servlet context to use as backing map
   private ServletContext context;

   /**
    * Constructor 
    * 
    * @param context The servlet context instance
    */
   public ApplicationBeanMap(ServletContext context)
   {
      super();
      this.context = context;
   }

   /**
    * @see org.jboss.webbeans.contexts.AbstractBeanMapAdaptor#getKeyPrefix()
    */
   @Override
   protected String getKeyPrefix()
   {
      return ApplicationContext.class.getName();
   }

   /**
    * @see org.jboss.webbeans.contexts.AbstractBeanMapAdaptor#getAttribute()
    */
   @Override
   protected Object getAttribute(String key)
   {
      return context.getAttribute(key);
   }

   /**
    * @see org.jboss.webbeans.contexts.AbstractBeanMapAdaptor#getAttributeNames()
    */
   @SuppressWarnings("unchecked")
   @Override
   protected Enumeration<String> getAttributeNames()
   {
      return context.getAttributeNames();
   }

   /**
    * @see org.jboss.webbeans.contexts.AbstractBeanMapAdaptor#removeAttributes()
    */
   @Override
   protected void removeAttribute(String key)
   {
      context.removeAttribute(key);
   }

   /**
    * @see org.jboss.webbeans.contexts.AbstractBeanMapAdaptor#setAttribute()
    */
   @Override
   protected void setAttribute(String key, Object instance)
   {
      context.setAttribute(key, instance);
   }

}
