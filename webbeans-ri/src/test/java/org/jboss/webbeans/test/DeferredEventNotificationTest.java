package org.jboss.webbeans.test;

import static org.jboss.webbeans.test.util.Util.createSimpleBean;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.webbeans.Observes;
import javax.webbeans.Standard;

import org.jboss.webbeans.bean.SimpleBean;
import org.jboss.webbeans.introspector.AnnotatedClass;
import org.jboss.webbeans.introspector.impl.InjectableMethod;
import org.jboss.webbeans.introspector.impl.SimpleAnnotatedClass;
import org.jboss.webbeans.test.annotations.AnotherDeploymentType;
import org.jboss.webbeans.test.annotations.Asynchronous;
import org.jboss.webbeans.test.beans.Tuna;
import org.jboss.webbeans.test.bindings.AsynchronousAnnotationLiteral;
import org.testng.annotations.Test;

/**
 * Unit tests for the deferred event notification object used to delay
 * notification till the end of a transaction.
 * 
 * @author David Allen
 * 
 */
public class DeferredEventNotificationTest extends AbstractTest
{

   public class Event
   {
      // Simple class used for testing
   }
   
   @Override
   protected void addEnabledDeploymentTypes()
   {
      List<Class<? extends Annotation>> enabledDeploymentTypes = new ArrayList<Class<? extends Annotation>>();
      enabledDeploymentTypes.add(Standard.class);
      enabledDeploymentTypes.add(AnotherDeploymentType.class);
      manager.setEnabledDeploymentTypes(enabledDeploymentTypes);
   }

   public class AnObserver
   {
      protected boolean notified = false;

      public void observe(@Observes @Asynchronous Event e)
      {
         // An observer method
         this.notified = true;
      }
   }

   /**
    * Test method for
    * {@link org.jboss.webbeans.event.DeferredEventNotification#beforeCompletion()}
    * .
    */
   @Test(groups="deferredEvent")
   public final void testBeforeCompletion() throws Exception
   {
      // When the transaction is committed, the beforeCompletion() method is
      // invoked which in turn invokes the observer. Here the mock observer
      // is used to keep track of the event being fired.
      SimpleBean<Tuna> tuna;
      InjectableMethod<Object> om;
      

      // Create an observer with known binding types
      Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<Class<? extends Annotation>, Annotation>();
      annotations.put(Asynchronous.class, new AsynchronousAnnotationLiteral());
      AnnotatedClass<Tuna> annotatedItem = new SimpleAnnotatedClass<Tuna>(Tuna.class, annotations);
      tuna = createSimpleBean(Tuna.class, manager);
      om = new InjectableMethod<Object>(AnObserver.class.getMethod("observe", new Class[] { Event.class }));

      AnObserver observerInstance = new AnObserver();
      // TODO Fix this Observer<Event> observer = new MockObserverImpl<Event>(tuna, om, Event.class);
      //((MockObserverImpl<Event>) observer).setInstance(observerInstance);
      Event event = new Event();
      //DeferredEventNotification<Event> deferredNotification = new DeferredEventNotification<Event>(event, observer);
      //deferredNotification.beforeCompletion();
      assert observerInstance.notified;
   }

}
