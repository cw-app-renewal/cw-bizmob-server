package adapter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.adapter.loaders.IAdapterLoader;

public class CowayAnnotationBeanAdapterLoader implements IAdapterLoader, ApplicationContextAware  {

    private static final Logger logger = LoggerFactory.getLogger(CowayAnnotationBeanAdapterLoader.class);
    private Map<String, String> beanContext = new ConcurrentHashMap<String, String>();
    private ApplicationContext appContext;
    private boolean init = false;
    
    /**
     * 생성자
     */
    public CowayAnnotationBeanAdapterLoader() { } 
 
    
    private void register() {
        logger.debug("register() > init : " + init);
        
        if(init) {
            return;
        }
        
        else {
            init = true;
        }
        
        logger.info("Start::register()");
        
        Map<String, Object> beanMap = appContext.getBeansWithAnnotation(Adapter.class);
        
        for (Entry<String, Object> entry : beanMap.entrySet()) {
            try {
                String beanName = entry.getKey();
                Adapter adp = entry.getValue().getClass().getAnnotation(Adapter.class);
                String[] trcodes = adp.trcode();
    
                for (String trcode : trcodes) {
                    if (trcode != null && trcode.length() > 0) {
                        if(!beanContext.containsKey(trcode)) {
                            beanContext.put(trcode, beanName);
                            logger.info(String.format("Register Adapter: The trcode %s was found and registered by bean %s!", trcode, beanName));
                        }
                        
                        else {
                            logger.error(String.format("Register Adapter: The trcode %s was duplicated and skipped!", trcode));
                        }
                    }
                }
            } catch(Exception e) {
                logger.debug(e.getLocalizedMessage());
                continue;
            }
        }
        
        logger.info("End::register");
    }    
 
    /* (non-Javadoc)
     */
    @SuppressWarnings("unused")
    private void testBean() {
        String[] names = appContext.getBeanDefinitionNames();
        
        for(String name : names) {
            logger.debug(name);
        }
    }
    
    
	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
        register();
    }

	@Override
    public IAdapterJob getAdapter(String adapterGroupId, String adapterId) {
        logger.debug("Start::getAdapter()");
        logger.debug("  > adapterGroupId : " + adapterGroupId);
        logger.debug("  > adapterId : " + adapterId);
        logger.debug("  > beanContext" + beanContext.toString());
        IAdapterJob adapterJob = null;
        
        if(beanContext.containsKey(adapterId)) {
            String beanName = beanContext.get(adapterId);
            
            try {
                adapterJob = (IAdapterJob) appContext.getBean(beanName);
            } catch (Exception e) {
                logger.error("Cannot find the adapter > adapterId : " + adapterId);
                adapterJob = null;
            }
        }

        logger.debug("  > RV(job) : " + adapterJob);
        logger.debug("End::getAdapter()");

        return adapterJob;
    }

}
