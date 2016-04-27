/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.dao;

import br.com.feapps.model.Area;
import br.com.feapps.model.SubArea;
import br.com.feapps.model.Usuario;
import br.com.feapps.util.CONST;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author F.Einstein
 */
public class AreaDAO extends DAO {
    private List<Area> areas;
    
    public AreaDAO () {
        areas = new ArrayList<>();
    }
    
    public void inserir(Area a) {
        try {
            this.getSessao().save(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AreaDAO > não inserido");
        } finally {
            //this.closeSession();
        }
    }
    
    public void remover(Area a) {
        try {
            this.getSessao().delete(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            //this.closeSession();
        }
    }
    
    public List<Area> getLista() {
        List<Area> result = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(Area.class);
            result = crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return result;
        }
    }
    
    public List<Area> getBuscaAreas(String busca, String tipoBusca){
        List result = new ArrayList();
        
        Criteria crit2 = this.getSessao().createCriteria(Area.class); //sessao.createCriteria(Contato.class);
       
        String busca2 = "%" + busca + "%";
        crit2.add(Restrictions.ilike(tipoBusca, busca2));
        result = crit2.list();
        
        //this.closeSession();
        
        return result;
    }
    
    public Area getArea(String id) {
        Area userRet = new Area();
        
        Criteria crit = this.getSessao().createCriteria(Area.class);
        
        crit.add(Restrictions.eq("id", id));

        userRet = (Area) crit.uniqueResult();
        
        //this.closeSession();
        
        return userRet;
    }
    
    public List<SubArea> getSubAreas(int idArea) {
        List<SubArea> ret = new ArrayList<>();
        Area area = new Area();
        
        Criteria crit = this.getSessao().createCriteria(Area.class);
        
        crit.add(Restrictions.eq("id", idArea));

        area = (Area) crit.uniqueResult();
        
        ret = new ArrayList<>(area.getSubArea());
        
        //this.closeSession();
        
        return ret;
    }
    
    public boolean ItIsInTheDatabase (String key, String value) {
        Area userRet = new Area();
        
        Criteria crit = this.getSessao().createCriteria(Area.class);
        
        crit.add(Restrictions.eq(key, value));

        userRet = (Area) crit.uniqueResult();
        
        //this.closeSession();
        
        if (userRet != null)
            return true;
        else
            return false;
    }

    public void atualizar(Area a) {
        try {
            this.getSessao().update(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
    
}
