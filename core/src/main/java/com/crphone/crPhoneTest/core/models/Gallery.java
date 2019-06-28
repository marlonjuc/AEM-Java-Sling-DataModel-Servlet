package com.crphone.crPhoneTest.core.models;

import com.crphone.crPhoneTest.core.models.Phone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.query.Query;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Gallery {
    @Inject
    private ResourceResolver resourceResolver;
    
    private List<Phone> phones = new ArrayList<Phone>();
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
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

