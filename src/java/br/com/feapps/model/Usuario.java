/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.model;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u WHERE u.nome = :nome"),
    @NamedQuery(name = "Usuario.findByTelefone", query = "SELECT u FROM Usuario u WHERE u.telefone = :telefone"),
    @NamedQuery(name = "Usuario.findByNascimento", query = "SELECT u FROM Usuario u WHERE u.nascimento = :nascimento"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByLogin", query = "SELECT u FROM Usuario u WHERE u.login = :login"),
    @NamedQuery(name = "Usuario.findBySenha", query = "SELECT u FROM Usuario u WHERE u.senha = :senha"),
    @NamedQuery(name = "Usuario.findByTipo", query = "SELECT u FROM Usuario u WHERE u.tipo = :tipo")})
public class Usuario implements Serializable {

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
    @Column(name = "telefone")
    private String telefone;
    
    @Column(name = "nascimento")
    private String nascimento;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    
    @Size(max = 2147483647)
    @Column(name = "login")
    private String login;
    
    @Size(max = 2147483647)
    @Column(name = "senha")
    private String senha;
    
    @Column(name = "tipo")
    private Integer tipo;
    
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    
    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Sala sala;
    
    /*@OneToMany (cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Usuario> amigos;*/

    public Usuario() {
        this.id = 0;
        this.nome = "";
        this.telefone = "";
        this.nascimento = "";
        this.email = "";
        this.login = "";
        this.senha = "";
        this.tipo = 0;
        this.foto = new byte[1];
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    Usuario(int id,
            String nome,
            String tel,
            String nasc,
            String email,
            String login,
            String senha,
            int tipo,
            byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.telefone = tel;
        this.nascimento = nasc;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
        this.foto = foto;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    /*public List<Usuario> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Usuario> amigos) {
        this.amigos = amigos;
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
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String buffer = "";
        buffer += getId();
        buffer += "##";
        buffer += getNome();
        buffer += "##";
        buffer += getTelefone();
        buffer += "##";
        buffer += getNascimento();
        buffer += "##";
        buffer += getEmail();
        buffer += "##";
        buffer += getLogin();
        buffer += "##";
        buffer += getSenha();
        buffer += "##";
        buffer += getTipo();
        buffer += "##";
        buffer += getFoto();
        return buffer;
    }
    
    public void toUsuario(String buffer) {
        String[] vectorBuffer = buffer.split("##");
        this.setId(Integer.parseInt(vectorBuffer[0]));
        this.setNome(vectorBuffer[1]);
        this.setTelefone(vectorBuffer[2]);
        this.setNascimento(vectorBuffer[3]);
        this.setEmail(vectorBuffer[4]);
        this.setLogin(vectorBuffer[5]);
        this.setSenha(vectorBuffer[6]);
        this.setTipo(Integer.parseInt(vectorBuffer[7]));
        this.setFoto(vectorBuffer[8].getBytes());
    }
    
}
