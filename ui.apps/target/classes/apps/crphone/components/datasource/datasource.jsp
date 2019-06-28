<%@page session="false" import="
                  org.apache.sling.api.resource.Resource,
                  com.adobe.granite.ui.components.ds.DataSource,
                  com.adobe.granite.ui.components.ds.EmptyDataSource,
                  com.crphone.crPhoneTest.core.services.PhonesDataSource"%><%
%><%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %><%
%><cq:defineObjects/>
<%
    request.setAttribute(DataSource.class.getName(), EmptyDataSource.instance());
    PhonesDataSource dss = new PhonesDataSource(resource);
    DataSource ds = dss.getDataSource();
    request.setAttribute(DataSource.class.getName(), ds);
%>