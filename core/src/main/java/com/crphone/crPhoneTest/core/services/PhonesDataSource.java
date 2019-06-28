package com.crphone.crPhoneTest.core.services;

import com.crphone.crPhoneTest.core.models.Phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.query.Query;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhonesDataSource {
    
    private List<Phone> phones = new ArrayList<Phone>();
    private Resource resource;
    private ResourceResolver resolver;
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public PhonesDataSource(Resource resource) {
        this.resource = resource;
        this.resolver = resource.getResourceResolver();
    }
    
    public DataSource getDataSource() {
        Resource productResource;
        List<Resource> options = new ArrayList<>();
        DataSource ds = null;
        
        try {
            String sqlStatement = "SELECT * FROM [nt:unstructured] WHERE CONTAINS(desc, 'phone')";
            
            Iterator<Resource> result = resolver.findResources(sqlStatement, Query.JCR_SQL2);
            
            while (result.hasNext()) {
                
                productResource = result.next();
                
                if (productResource != null) {
                    
                    ValueMap valueMap = productResource.adaptTo(ValueMap.class);
                    
                    if (valueMap != null) {
                        Phone phone = new Phone();
                        
                        phone.setName(valueMap.get("name").toString());
                        
                        phones.add(phone);
                    }
                }
            }
            for (int i = 0; i < phones.size(); i++) {
                ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());
                //String value = "" + i;
                String value = phones.get(i).getName();
                String text = phones.get(i).getName();
                vm.put("value", value);
                vm.put("text", text);

                options.add(new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm));
            }
            
            ds = new SimpleDataSource(options.iterator());
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally {
            //if (resourceResolver.isLive())
                //resourceResolver.close();
        }
        
        return ds;
    }

}