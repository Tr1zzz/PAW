/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jsfcourse.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author david
 */
@MappedSuperclass
@Table(name = "memberships")
public class Memberships implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_memberships")
    private Integer idMemberships;
    @JoinTable(name = "service_membership", joinColumns = {
        @JoinColumn(name = "id_membership", referencedColumnName = "id_memberships")}, inverseJoinColumns = {
        @JoinColumn(name = "id_service", referencedColumnName = "id_service")})
    @ManyToMany
    private Collection<Service> serviceCollection;
    @JoinColumn(name = "id_account", referencedColumnName = "id_account")
    @ManyToOne(optional = false)
    private Account idAccount;
    @JoinColumn(name = "id_type", referencedColumnName = "id_type")
    @ManyToOne(optional = false)
    private Type idType;

    public Memberships() {
    }

    public Memberships(Integer idMemberships) {
        this.idMemberships = idMemberships;
    }

    public Integer getIdMemberships() {
        return idMemberships;
    }

    public void setIdMemberships(Integer idMemberships) {
        this.idMemberships = idMemberships;
    }

    public Collection<Service> getServiceCollection() {
        return serviceCollection;
    }

    public void setServiceCollection(Collection<Service> serviceCollection) {
        this.serviceCollection = serviceCollection;
    }

    public Account getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Account idAccount) {
        this.idAccount = idAccount;
    }

    public Type getIdType() {
        return idType;
    }

    public void setIdType(Type idType) {
        this.idType = idType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMemberships != null ? idMemberships.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memberships)) {
            return false;
        }
        Memberships other = (Memberships) object;
        if ((this.idMemberships == null && other.idMemberships != null) || (this.idMemberships != null && !this.idMemberships.equals(other.idMemberships))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jsfcourse.entities.Memberships[ idMemberships=" + idMemberships + " ]";
    }
    
}
