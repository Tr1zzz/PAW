package com.jsfcourse.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.jsfcourse.entities.Service;

@Stateless
public class ServiceDAO {
    private final static String UNIT_NAME = "my_persistence_unit";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Service service) {
        em.persist(service);
    }

    public Service merge(Service service) {
        return em.merge(service);
    }

    public void remove(Service service) {
        em.remove(em.merge(service));
    }

    public Service find(Object id) {
        return em.find(Service.class, id);
    }

    public List<Service> getFullList() {
        List<Service> list = null;

        Query query = em.createQuery("SELECT s FROM Service s");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Service> getList(Map<String, Object> searchParams) {
        List<Service> list = null;

        String select = "SELECT s ";
        String from = "FROM Service s ";
        String where = "";
        String orderby = "ORDER BY s.name ASC";

        
        String name = (String) searchParams.get("name");
        if (name != null && !name.isEmpty()) {
            where += (where.isEmpty() ? "WHERE " : "AND ") + "s.name LIKE :name ";
        }

        

        Query query = em.createQuery(select + from + where + orderby);

        if (name != null && !name.isEmpty()) {
            query.setParameter("name", name + "%");
        }

        

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
