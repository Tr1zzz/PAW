package com.jsfcourse.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.jsfcourse.dao.ServiceDAO;
import com.jsfcourse.entities.Service;

@Named
@ViewScoped
public class ServiceEditGETBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_SERVICE_LIST = "serviceList?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    private Service service = new Service();
    private Service loaded = null;

    @Inject
    FacesContext context;

    @EJB
    ServiceDAO serviceDAO;

    public Service getService() {
        return service;
    }

    public void onLoad() throws IOException {
        if (!context.isPostback()) {
            if (!context.isValidationFailed() && service.getIdService() != null) {
                loaded = serviceDAO.find(service.getIdService());
            }
            if (loaded != null) {
                service = loaded;
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", null));
            }
        }
    }

    public String saveData() {
        if (loaded == null) {
            return PAGE_STAY_AT_THE_SAME;
        }

        try {
            if (service.getIdService() == null) {
                serviceDAO.create(service);
            } else {
                serviceDAO.merge(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", null));
            return PAGE_STAY_AT_THE_SAME;
        }

        return PAGE_SERVICE_LIST;
    }
}
