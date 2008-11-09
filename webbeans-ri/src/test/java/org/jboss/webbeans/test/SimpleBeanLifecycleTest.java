package org.jboss.webbeans.test;

import static org.jboss.webbeans.test.util.Util.createSimpleBean;

import javax.webbeans.manager.Bean;

import org.jboss.webbeans.bean.SimpleBean;
import org.jboss.webbeans.test.beans.Farm;
import org.jboss.webbeans.test.beans.FarmOffice;
import org.jboss.webbeans.test.beans.FishPond;
import org.jboss.webbeans.test.beans.Goldfish;
import org.jboss.webbeans.test.beans.RedSnapper;
import org.jboss.webbeans.test.beans.Tuna;
import org.jboss.webbeans.test.beans.TunaFarm;
import org.testng.annotations.Test;

@SpecVersion("PDR")
public class SimpleBeanLifecycleTest extends AbstractTest
{
	
	@Test(groups="beanConstruction") @SpecAssertion(section="3.1.3")
	public void testInjectionOfParametersIntoBeanConstructor()
	{
	   Bean<FishPond> goldfishPondBean = createSimpleBean(FishPond.class, manager);
	   Bean<Goldfish> goldfishBean = createSimpleBean(Goldfish.class, manager);
	   manager.addBean(goldfishBean);
	   manager.addBean(goldfishPondBean);
	   FishPond fishPond = goldfishPondBean.create();
	   assert fishPond.goldfish != null;
	}
	
	@Test(groups="specialization") @SpecAssertion(section="3.1.4")
   public void testSpecializedBeanAlwaysUsed()
   {
      // TODO Placeholder
      assert false;
   }
	
   @Test(groups="beanLifecycle") @SpecAssertion(section="5.3")
   public void testCreateReturnsInstanceOfBean()
   {
      Bean<RedSnapper> bean = createSimpleBean(RedSnapper.class, manager);
      assert bean.create() instanceof RedSnapper;
   }
   
   @Test(groups={"beanLifecycle", "interceptors"}) @SpecAssertion(section="5.3")
   public void testCreateBindsInterceptorStack()
   {
      assert false;
   }
   
   @Test(groups={"beanLifecycle", "decorators"}) @SpecAssertion(section="5.3")
   public void testCreateBindsDecoratorStack()
   {
      assert false;
   }
   
   @Test(groups={"beanLifecycle", "commonAnnotations"}) @SpecAssertion(section="5.3")
   public void testCreateInjectsEjb()
   {
      assert false;
   }
   
   @Test(groups={"beanLifecycle", "commonAnnotations"}) @SpecAssertion(section="5.3")
   public void testCreateInjectsPersistenceContext()
   {
      assert false;
   }
   
   @Test(groups={"beanLifecycle", "commonAnnotations"}) @SpecAssertion(section="5.3")
   public void testCreateInjectsResource()
   {
      assert false;
   }
   
   @Test(groups={"beanLifecycle", "lifecycleCallbacks"}) @SpecAssertion(section="5.3")
   public void testPostConstructPreDestroy() throws Exception
   {
      Bean<FarmOffice> farmOfficeBean = createSimpleBean(FarmOffice.class, manager);
      Bean<Farm> farmBean = createSimpleBean(Farm.class, manager);
      manager.addBean(farmOfficeBean);
      manager.addBean(farmBean);
      Farm farm = farmBean.create();
      assert farm.founded!=null;
      assert farm.initialStaff==20;
      assert farm.closed==null;
      farmBean.destroy(farm);
      assert farm.closed!=null;
   }
   
   @Test(groups="injection") @SpecAssertion(section="5.3")
   public void testCreateInjectsFieldsDeclaredInJava()
   {
      SimpleBean<TunaFarm> tunaFarmBean = createSimpleBean(TunaFarm.class, manager);
      Bean<Tuna> tunaBean = createSimpleBean(Tuna.class, manager);
      manager.addBean(tunaBean);
      TunaFarm tunaFarm = tunaFarmBean.create();
      assert tunaFarm.tuna != null;
   }
   
   @Test(groups="injection") 
   public void testFieldMissingBindingAnnotationsAreNotInjected()
   {
      SimpleBean<TunaFarm> tunaFarmBean = createSimpleBean(TunaFarm.class, manager);
      Bean<Tuna> tunaBean = createSimpleBean(Tuna.class, manager);
      manager.addBean(tunaBean);
      TunaFarm tunaFarm = tunaFarmBean.create();
      assert tunaFarm.notInjectedTuna != manager.getInstance(tunaBean);
   }
	
}
