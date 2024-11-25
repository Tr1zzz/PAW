package com.jsfcourse.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;

import com.jsfcourse.dao.ServiceDAO;
import com.jsfcourse.entities.Service;

@Named
@RequestScoped
public class ServiceListBB {
    private static final String PAGE_SERVICE_EDIT = "serviceEdit?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    private String name;
        
    @Inject
    ExternalContext extcontext;
    
    @Inject
    Flash flash;
    
    @EJB
    ServiceDAO serviceDAO;
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public List<Service> getFullList(){
        return serviceDAO.getFullList();
    }

    public List<Service> getList(){
        List<Service> list = null;
        
        
        Map<String,Object> searchParams = new HashMap<String, Object>();
        
        if (name != null && !name.isEmpty()){
            searchParams.put("name", name);
        }
        
        
        list = serviceDAO.getList(searchParams);
        
        return list;
    }

    public String newService(){
        Service service = new Service();
        
        
        flash.put("service", service);
        
        return PAGE_SERVICE_EDIT;
    }

    public String editService(Service service){
        
        flash.put("service", service);
        
        return PAGE_SERVICE_EDIT;
    }

    public String deleteService(Service service){
        serviceDAO.remove(service);
        return PAGE_STAY_AT_THE_SAME;
    }
}
