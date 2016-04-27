/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Area.findAll", query = "SELECT u FROM Area u"),
    @NamedQuery(name = "Area.findById", query = "SELECT u FROM Area u WHERE u.id = :id"),
    @NamedQuery(name = "Area.findByNome", query = "SELECT u FROM Area u WHERE u.nome = :nome")})
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nome")
    private String nome;
    
    @OneToMany (cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    Set<SubArea> subArea;

    public Area() {
        id = 0;
        nome = "";
        subArea = new HashSet<>();
    }

    public Area(Integer id) {
        this.id = id;
        nome = "vazio";
        this.subArea = new HashSet<>();
    }
    
    public Area(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
        this.subArea = new HashSet<>();
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

    public Set<SubArea> getSubArea() {
        return subArea;
    }

    public void setSubArea(Set<SubArea> subArea) {
        this.subArea = subArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        List<SubArea> subAreas = new ArrayList<>(this.getSubArea());
        String[] vectorBuffer = new String[2+(subAreas.size()*2)];

        vectorBuffer[0] = String.valueOf(this.getId());
        vectorBuffer[1] = this.getNome();

        int i = 2;
        for (SubArea sa : subAreas) {
            vectorBuffer[i++] = String.valueOf(sa.getId());
            vectorBuffer[i++] = sa.getNome();
        }

        String buffer="";
        for(i = 0; i < vectorBuffer.length; i++) {
            buffer += vectorBuffer[i];
            if(i < vectorBuffer.length)
                buffer += "##";
        }

        return buffer;
    }

    public void toArea(String str) {
        String[] vectorBuffer = str.split("##");
        List<SubArea> subAreas = new ArrayList<>();

        this.setId(Integer.parseInt(vectorBuffer[0]));
        this.setNome(vectorBuffer[1]);

        for (int i = 2; i < vectorBuffer.length; i+=2) {
            int id = Integer.parseInt(vectorBuffer[i]);
            String nome = vectorBuffer[i+1];
            SubArea sa = new SubArea(id, nome);
            subAreas.add(sa);
        }
        this.setSubArea(new HashSet<>(subAreas));
    }
    
}
