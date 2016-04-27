/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.dao;

import java.util.List;
import br.com.feapps.model.Usuario;
import br.com.feapps.util.CONST;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author F.Einstein
 */
public class UsuarioDAO extends DAO {
    private List<Usuario> usuarios;
    
    public UsuarioDAO () {
        usuarios = new ArrayList<>();
    }
    
    public void inserir(Usuario u) {
        try {
            this.getSessao().save(u);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UsuarioDAO > não inserido");
        } finally {
            //this.closeSession();
        }
    }
    
    public void remover(Usuario c) {
        try {
            this.getSessao().delete(c);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
    
    public List<Usuario> getListaUsuarios() {
        List<Usuario> result = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(Usuario.class);
            
            result = crit.list();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return result;
        }
    }
    
    public List<Usuario> getBuscaContatos(String busca, String tipoBusca){
        List result = new ArrayList();
        
        Criteria crit2 = this.getSessao().createCriteria(Usuario.class); //sessao.createCriteria(Contato.class);
       
        String busca2 = "%" + busca + "%";
        crit2.add(Restrictions.ilike(tipoBusca, busca2));
        result = crit2.list();
        
        //this.closeSession();
        
        return result;
    }
    
    public Usuario getUsuario(String login, String senha) {
        Usuario userRet = new Usuario();
        
        Criteria crit = this.getSessao().createCriteria(Usuario.class);
        
        /*if (crit == null)
            System.out.println("criteria nulo");
        else
            System.out.println("criteria OK");*/
        
        crit.add(Restrictions.eq("login", login));
        crit.add(Restrictions.eq("senha", senha));

        userRet = (Usuario) crit.uniqueResult();
        
        /*if (userRet == null)
            userRet = new Usuario();
        else
            userRet = lRet.get(0);*/
        
        //this.closeSession();
        
        return userRet;
    }
    
    public Usuario getUsuarioById(int id) {
        Usuario userRet = new Usuario();
        
        Criteria crit = this.getSessao().createCriteria(Usuario.class);
        
        crit.add(Restrictions.eq("id", id));

        userRet = (Usuario) crit.uniqueResult();
        
        //this.closeSession();
        
        return userRet;
    }
    
    public List<Integer> getIdsUsuariosBySalaId(int idSala) {
        List<Usuario> userListRet = new ArrayList<>();
        List<Integer> listIdUsers = new ArrayList<>();
        
        Criteria crit = this.getSessao().createCriteria(Usuario.class);
        
        crit.add(Restrictions.eq("sala.id", idSala));

        userListRet = crit.list();
        
        for (Usuario u : userListRet) {
            listIdUsers.add(u.getId());
        }
        
        //this.closeSession();
        
        return listIdUsers;
    }
    
    public boolean ItIsInTheDatabase (String key, String value) {
        Usuario userRet = new Usuario();
        
        Criteria crit = this.getSessao().createCriteria(Usuario.class);
        
        crit.add(Restrictions.eq(key, value));

        userRet = (Usuario) crit.uniqueResult();
        
        //this.closeSession();
        
        if (userRet != null)
            return true;
        else
            return false;
    }

    public void atualizar(Usuario c) {
        try {
            this.getSessao().update(c);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }

}
