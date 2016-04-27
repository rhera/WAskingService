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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author F.Einstein
 */
public class SubAreaDAO extends DAO {
    private List<SubArea> listSubArea;
    
    public SubAreaDAO () {
        listSubArea = new ArrayList<>();
    }
    
    public void inserir(SubArea a) {
        try {
            this.getSessao().save(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SubAreaDAO > não inserido");
        } finally {
            //this.closeSession();
        }
    }
    
    public void remover(SubArea a) {
        try {
            this.getSessao().delete(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
    
    public List<SubArea> getLista() {
        List<SubArea> result = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(SubArea.class);
            
            crit.add(Restrictions.ne("id", 0));
            
            result = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return result;
        }
    }
    
    public List<SubArea> getSubAreasForIdArea(int idArea) {
        //String hql = "select id,nome from subarea where area_id=" + idArea;
        //Query query = (Query) DAO.getSessao().createQuery(hql);
        //List results = query.list();
        Area area = new Area();
        List<Area> lAreas = new ArrayList();
        listSubArea = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(Area.class);
            
            lAreas = crit.list();
            
            for (Area a : lAreas) {
                if (a.getId().equals(idArea))
                    area = a;
            }
            
            listSubArea = new ArrayList<>(area.getSubArea());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return listSubArea;
        }
    }
    
    public List<String> getListaStr() {
        List<SubArea> result = new ArrayList();
        List<String> resultFinal = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(SubArea.class);
            
            result = crit.list();
            
            for (SubArea sa : result) {
                resultFinal.add(sa.getNome());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return resultFinal;
        }
    }
    
    public List<SubArea> getBuscaSubAreas(String busca, String tipoBusca){
        List result = new ArrayList();
        
        Criteria crit2 = this.getSessao().createCriteria(SubArea.class); //sessao.createCriteria(Contato.class);
       
        String busca2 = "%" + busca + "%";
        crit2.add(Restrictions.ilike(tipoBusca, busca2));
        result = crit2.list();
        
        //this.closeSession();
        
        return result;
    }
    
    public SubArea getSubArea(int id) {
        SubArea userRet = new SubArea();
        Criteria crit = this.getSessao().createCriteria(SubArea.class);
        
        crit.add(Restrictions.eq("id", id));

        userRet = (SubArea) crit.uniqueResult();
        
        //this.closeSession();
        
        return userRet;
    }
    
    public boolean ItIsInTheDatabase (String key, String value) {
        SubArea userRet = new SubArea();
        Criteria crit = this.getSessao().createCriteria(SubArea.class);
        
        crit.add(Restrictions.eq(key, value));

        userRet = (SubArea) crit.uniqueResult();
        
        //this.closeSession();
        
        return (userRet != null);
    }

    public void atualizar(SubArea a) {
        try {
            this.getSessao().update(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
    
    /* 
    
    
    Conexão direta com o banco de dados
    
   
    */
    /*private Connection conn = DAO.getConnection(); //testando a conexao
    
    public void inserir(SubArea a){
        String sql = "INSERT INTO subarea ("+
                CONST.SUBAREATABNOME + "," +
                CONST.SUBAREATABIDAREA + ") VALUES (?,?)";
        try {
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setString(1, a.getNome());
            preparador.setInt(2, a.getAreaId());
            
            preparador.execute();
            preparador.close();
            
            System.out.println("SubArea inserida com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro - SubArea>inserir - Conexao: "+e.getMessage());
        }
    }
    
    public boolean ItIsInTheDatabase (String key, String value) {
        //Usuario userRet = new Usuario();
        String sql = "SELECT * FROM subarea WHERE ?=?";
        boolean retorno = false;
        try {
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setString(1, key);
            preparador.setString(2, value);
            ResultSet resSet = preparador.executeQuery();
            retorno = resSet.next();
            preparador.close();
        } catch (SQLException e) {
            System.out.println("Erro - Area>ItIsInTheDatabase - Conexao: " + e.getMessage());
        }
        return retorno;
    }
    
    public void alterar(SubArea a){
        String sql = "UPDATE subarea SET " +
                CONST.SUBAREATABNOME + " = ? " +
                CONST.SUBAREATABIDAREA + " = ? " +
                "WHERE " +
                CONST.SUBAREATABID + " = ?";
        try {
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setString(1, a.getNome());
            preparador.setInt(2, a.getAreaId());
            preparador.setInt(3, a.getId());
            
            preparador.execute();
            preparador.close();
            
            System.out.println("SubArea alterada com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro - SubArea>alterar - Conexao: "+e.getMessage());
        }
             
    }
    
    public void deletar(Area a){
        String sql = "DELETE FROM subarea WHERE id = ?";
        try {
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setInt(1, a.getId());
            
            preparador.execute();
            preparador.close();
            
            System.out.println("SubArea deletada com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro - SubArea>deletar - Conexao: "+e.getMessage());
        }    
    }
    
    public List<SubArea> getListaSubAreas(){
    
        String sql = "SELECT * FROM subarea";
        List<SubArea> lista = new ArrayList<SubArea>();
        try {
            PreparedStatement preparador = conn.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();
            while(resultados.next()){
                SubArea a = new SubArea();
                a.setId(resultados.getInt(CONST.SUBAREATABID));
                a.setNome(resultados.getString(CONST.SUBAREATABNOME));
                a.setAreaId(resultados.getInt(CONST.SUBAREATABIDAREA));
                lista.add(a);
                System.out.println("SubArea inserida na lista para retorno");
            }
        } catch (SQLException e) {
            System.out.println("Erro - SubArea>getListaSubArea: "+e.getMessage());
        }finally{
            return lista;  
        }
    }
    
    public List<SubArea> getListaSubAreasByAreaId(int areaId){
    
        String sql = "SELECT * FROM subarea WHERE " + CONST.SUBAREATABIDAREA + " = ?";
        List<SubArea> lista = new ArrayList<SubArea>();
        try {
            PreparedStatement preparador = conn.prepareStatement(sql);
            preparador.setInt(1, areaId);
            
            ResultSet resultados = preparador.executeQuery();
            while(resultados.next()){
                SubArea a = new SubArea();
                a.setId(resultados.getInt(CONST.SUBAREATABID));
                a.setNome(resultados.getString(CONST.SUBAREATABNOME));
                a.setAreaId(resultados.getInt(CONST.SUBAREATABIDAREA));
                lista.add(a);
                System.out.println("SubArea por id de área inserida na lista para retorno");
            }
        } catch (SQLException e) {
            System.out.println("Erro - SubArea>getListaSubAreasByAreaId: "+e.getMessage());
        }finally{
            return lista;  
        }
    }*/
    
}
