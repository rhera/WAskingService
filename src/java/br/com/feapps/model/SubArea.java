/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author F.Einstein
 */
@Entity
@Table(name = "subarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubArea.findAll", query = "SELECT u FROM SubArea u"),
    @NamedQuery(name = "SubArea.findById", query = "SELECT u FROM SubArea u WHERE u.id = :id"),
    @NamedQuery(name = "SubArea.findByNome", query = "SELECT u FROM SubArea u WHERE u.nome = :nome")})
public class SubArea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nome")
    private String nome;
    
    /*@ManyToOne
    Area area;*/

    public SubArea() {
        this.id = 0;
        this.nome = "";
    }

    public SubArea(Integer id) {
        this.id = id;
        this.nome = "vazio";
    }
    
    public SubArea(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
        //this.area = area;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /*public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        SubArea other = (SubArea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String buffer = "";
        buffer += this.getId();
        buffer += "$$";
        buffer += this.getNome();
        return buffer;
    }

    public void toSubArea(String str) {
        this.toSubArea(str.split("$$"));
    }

    public void toSubArea(String[] vectorStr) {
        this.setId(Integer.parseInt(vectorStr[0]));
        this.setNome(vectorStr[1]);
    }
    
}
