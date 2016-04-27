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
import java.util.regex.Pattern;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "sala")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sala.findAll", query = "SELECT u FROM Sala u"),
    @NamedQuery(name = "Sala.findById", query = "SELECT u FROM Sala u WHERE u.id = :id"),
    @NamedQuery(name = "Sala.findByNome", query = "SELECT u FROM Sala u WHERE u.nome = :nome")})
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nome")
    private String nome;
    
    @Size(max = 2147483647)
    @Column(name = "tipo")
    private String tipo;
    
    @Size(max = 2147483647)
    @Column(name = "senha")
    private String senha;
    
    @ManyToOne (fetch = FetchType.EAGER)
    Area area;
    
    @ManyToOne (fetch = FetchType.EAGER)
    SubArea subArea;
    
    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Usuario> listUser;
    
    @Column(name = "max_user_sala")
    Integer maxUserSala;
    
    @OneToOne (cascade = CascadeType.ALL)
    ConjuntoQuestoes conjuntoQuestoes;
    
    boolean visivel;

    public Sala() {
        this.id = 0;
        this.nome = "";
        this.tipo = "";
        this.senha = "";
        this.area = new Area();
        this.area.setSubArea(new HashSet<>());
        this.subArea = new SubArea();
        this.listUser = new HashSet<>();
        this.maxUserSala = 0;
        this.conjuntoQuestoes = new ConjuntoQuestoes();
        this.visivel = true;
    }

    public Sala(Integer id) {
        this.id = id;
        this.nome = "vazio";
        this.tipo = "vazio";
        this.senha = "vazio";
        this.area = new Area(0);
        this.subArea = new SubArea(0);
        this.listUser = new HashSet<>();
        this.maxUserSala = 0;
        this.conjuntoQuestoes = new ConjuntoQuestoes(0);
        this.visivel = true;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public int getTipoInt() {
        switch(this.tipo) {
            case "1 Vs 1":
                return 1;
            case "2 Vs 2":
                return 1;
            case "3 Vs 3":
                return 1;
            default:
                return 0;
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public SubArea getSubArea() {
        return subArea;
    }

    public void setSubArea(SubArea subArea) {
        this.subArea = subArea;
    }

    public Set<Usuario> getListUser() {
        return listUser;
    }

    public void setListUser(Set<Usuario> listUser) {
        this.listUser = listUser;
    }

    public Integer getMaxUserSala() {
        return maxUserSala;
    }

    public void setMaxUserSala(Integer maxUserSala) {
        this.maxUserSala = maxUserSala;
    }

    public ConjuntoQuestoes getConjuntoQuestoes() {
        return conjuntoQuestoes;
    }

    public void setConjuntoQuestoes(ConjuntoQuestoes conjuntoQuestoes) {
        this.conjuntoQuestoes = conjuntoQuestoes;
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    /*public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public int getIdSubArea() {
        return idSubArea;
    }

    public void setIdSubArea(int idSubArea) {
        this.idSubArea = idSubArea;
    }
    
    public int getUserCriadorId() {
        return userCriadorId;
    }

    public void setUserCriadorId(int userCriadorId) {
        this.userCriadorId = userCriadorId;
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
        Sala other = (Sala) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public String[] toVectorString() {
        String[] vectorBuffer = new String[14 + this.getArea().getSubArea().size()*2 + (listUser.size()*9)+this.getConjuntoQuestoes().getQuestoes().size()+this.getConjuntoQuestoes().getRespostas().size()];
        
        int j = 0;
        
        vectorBuffer[j++] = String.valueOf(this.isVisivel());
        vectorBuffer[j++] = String.valueOf(this.getId());
        vectorBuffer[j++] = this.getNome();
        vectorBuffer[j++] = this.getTipo();
        vectorBuffer[j++] = this.getSenha();
        
        //Pegando Area
        vectorBuffer[j++] = String.valueOf(this.getArea().getId());
        vectorBuffer[j++] = this.getArea().getNome();
        
        //Pegando subAreas de Area
        List<SubArea> lsa = new ArrayList<>(this.getArea().getSubArea());
        
        for (SubArea sa : lsa) {
            vectorBuffer[j++] = sa.getId().toString();
            vectorBuffer[j++] = sa.getNome();
        }
        
        vectorBuffer[j++] = "$flag";

        vectorBuffer[j++] = String.valueOf(this.getSubArea().getId());
        vectorBuffer[j++] = this.getSubArea().getNome();
        
        for (Usuario u : this.getListUser()) {
            vectorBuffer[j++] = String.valueOf(u.getId());
            vectorBuffer[j++] = u.getNome();
            vectorBuffer[j++] = u.getTelefone();
            vectorBuffer[j++] = u.getEmail();
            vectorBuffer[j++] = u.getNascimento();
            vectorBuffer[j++] = u.getLogin();
            vectorBuffer[j++] = u.getSenha();
            vectorBuffer[j++] = String.valueOf(u.getTipo());
            vectorBuffer[j++] = String.valueOf(u.getFoto());
        }
        
        vectorBuffer[j++] = "$flag";

        vectorBuffer[j++] = String.valueOf(this.getMaxUserSala());
        
        String[] tempBuffer = this.getConjuntoQuestoes().toVectorString();
        
        for (int i = 0; i < tempBuffer.length; i++) {
            vectorBuffer[j++] = tempBuffer[i];
        }
        
        return vectorBuffer;
    }

    @Override
    public String toString() {
        //List<Usuario> usuarios = this.usuarios;
        String[] vectorBuffer = toVectorString();

        String buffer="";
        for(int i = 0; i < vectorBuffer.length; i++) {
            buffer += vectorBuffer[i];
            if (i < vectorBuffer.length)
                buffer += "##";
        }
        //System.out.println(buffer);
        return buffer;
    }
    
    public void toSala (String[] vectorBuffer) {
        ConjuntoQuestoes cq = new ConjuntoQuestoes();
        List<Usuario> usuarios = new ArrayList<>();

        int j = 0;

        this.setVisivel(Boolean.valueOf(vectorBuffer[j++]));
        this.setId(Integer.parseInt(vectorBuffer[j++]));
        this.setNome(vectorBuffer[j++]);
        this.setTipo(vectorBuffer[j++]);
        this.setSenha(vectorBuffer[j++]);

        Area a = new Area(Integer.parseInt(vectorBuffer[j++]), vectorBuffer[j++]);

        //Setando subareas de area
        while (!vectorBuffer[j].equals("$flag")) {
            SubArea sa = new SubArea();
            sa.setId(Integer.parseInt(vectorBuffer[j++]));
            sa.setNome(vectorBuffer[j++]);
            a.getSubArea().add(sa);
        }
        this.setArea(a);
        j++;

        SubArea sa = new SubArea(Integer.parseInt(vectorBuffer[j++]), vectorBuffer[j++]);
        this.setSubArea(sa);

        while (!vectorBuffer[j].equals("$flag")) {
            Usuario u = new Usuario(
                    Integer.parseInt(vectorBuffer[j++]),
                    vectorBuffer[j++],
                    vectorBuffer[j++],
                    vectorBuffer[j++],
                    vectorBuffer[j++],
                    vectorBuffer[j++],
                    vectorBuffer[j++],
                    Integer.parseInt(vectorBuffer[j++]),
                    vectorBuffer[j++].getBytes());
            usuarios.add(u);
        }
        j++;

        this.setMaxUserSala(Integer.parseInt(vectorBuffer[j++]));

        String[] tempVecStr = new String[vectorBuffer.length-j];

        int k = 0;
        for (int i = j; i < vectorBuffer.length; i++)
            tempVecStr[k++] = vectorBuffer[i];

        cq.toConjunto(tempVecStr);

        this.setConjuntoQuestoes(cq);

        this.setListUser(new HashSet<>(usuarios));
    }

    /*public void toSala(String str) {
        String[] vectorBuffer = str.split("##");
        toSala(vectorBuffer);
    }*/
    
}
