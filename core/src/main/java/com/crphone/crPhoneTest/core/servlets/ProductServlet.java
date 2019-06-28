package com.crphone.crPhoneTest.core.servlets;

import com.crphone.crPhoneTest.core.models.Phone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.query.Query;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductServlet {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Self
    private SlingHttpServletRequest request;
    
    @Inject
    private ResourceResolver resourceResolver;
    
    private List<Phone> phones = new ArrayList<Phone>();
    
    @PostConstruct
    public void initModel() {
        Resource productResource;
        
        try {
            String sqlStatement = "SELECT * FROM [nt:unstructured] WHERE CONTAINS(desc, 'phone')";
            
            Iterator<Resource> result = resourceResolver.findResources(sqlStatement, Query.JCR_SQL2);
        
            while (result.hasNext()) {
                
                productResource = result.next();
                
                if (productResource != null) {
                    
                    ValueMap valueMap = productResource.adaptTo(ValueMap.class);
                    
                    if (valueMap != null) {
                        
                        Phone phone = new Phone();
                        
                        phone.setName(valueMap.get("name").toString());
                        phone.setProcessor(valueMap.get("processor").toString());
                        phone.setRam(valueMap.get("ram").toString());
                        phone.setSd(valueMap.get("sd").toString());
                        phone.setCamera(valueMap.get("camera").toString());
                        phone.setNetworks(valueMap.get("networks", String[].class));
                        phone.setImages(valueMap.get("images", String[].class));
                        
                        phones.add(phone);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally {
            //if (resourceResolver.isLive())
                //resourceResolver.close();
        }
    }
    
    public List<Phone> getPhones() {
        return phones;
    }
    
   
}
