/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.dao;

import br.com.feapps.model.Area;
import br.com.feapps.model.ConjuntoQuestoes;
import br.com.feapps.model.Questao;
import br.com.feapps.model.Ranking;
import br.com.feapps.model.RankingTemp;
import br.com.feapps.model.Resposta;
import br.com.feapps.model.Sala;
import br.com.feapps.model.SubArea;
import br.com.feapps.util.CONST;
import br.com.feapps.util.Cript;
import br.com.feapps.model.Usuario;
import br.com.feapps.util.STR;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author F.Einstein
 */
@WebService(serviceName = "WebServiceAskingDB")
public class WebServiceAskingDB {

    /*UsuarioDAO userDAO = new UsuarioDAO();
    AreaDAO areaDAO = new AreaDAO();
    SubAreaDAO subAreaDAO = new SubAreaDAO();
    SalaDAO salaDAO = new SalaDAO();
    ConjuntoQuestoesDAO cqDAO = new ConjuntoQuestoesDAO();
    RankingDAO rDAO = new RankingDAO();*/
    
    //boolean teste = false;
    
    //String key = "";
    //Cript cript;
    boolean acessoBD; // = false;

    public WebServiceAskingDB() {
        acessoBD = false;
        //cript = new Cript("");
    }
    
    //@WebMethod(operationName = "ObterAcessoBD")
    /*public void ObterAcessoBD () {
        while (acessoBD) {
            //System.out.println("Esperando obeter acesso.");
        }
        acessoBD = true;
        //return "Obteve acesso.";
    }
    
    //@WebMethod(operationName = "LiberarAcessoBD")
    public void LiberarAcessoBD() {
        acessoBD = false;
        //return "Liberou acesso.";
    }*/
    
    /*@WebMethod(operationName = "TestePegaChave")
    public String TestePegaChave (){
        if (teste)
            return "Alguém está com a chave!";
        else {
            teste = true;
            return "Peguei a chave!";
        }
    }
    
    @WebMethod(operationName = "TesteLiberaChave")
    public String TesteLiberaChave (){
        if (teste) {
            teste = false;
            return "Eu estava com a chave mas liberei!";
        } else {
            return "A chave não está com ninguém.";
        }
    }*/
    
    //ApagarMetodoDeCriação
    /*@WebMethod(operationName = "atualizarTabelas")
    public String atualizarTabelas() {
        Usuario u = new Usuario();
        Area a = new Area();
        SubArea sa = new SubArea();
        Sala s = new Sala();
        Questao q = new Questao();
        Resposta r = new Resposta();
        ConjuntoQuestoes cq = new ConjuntoQuestoes();
        Ranking ran = new Ranking();
        RankingTemp ranTem = new RankingTemp();
        
        return "OK";
    }*/
    
    @WebMethod(operationName = "hello")
    public String hello(
            @WebParam(name = "name") String txt,
            @WebParam(name = "key") String key) {
        //System.out.println(txt + " - " + key);
        Cript cript = new Cript("");
        if (cript.getKey().equals(key))
            return "Hello " + txt + "!";
        else
            return "Chave incorreta!";
    }
    
    @WebMethod(operationName = "listarUsuarios")
    public String listarUsuarios(
            @WebParam(name = "key") String key) {
        //Compara chaves
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            List<Usuario> list = new ArrayList();
            String retorno = "";
            
            UsuarioDAO userDAO = new UsuarioDAO();
            //this.ObterAcessoBD();
            list = userDAO.getListaUsuarios();
            
            int i = 0;
            for (Usuario u : list) {
                retorno += u.toString();
                if (i < list.size())
                    retorno += "@@";
                i++;
            }
            userDAO.closeSession();
            //this.LiberarAcessoBD();
            
            return retorno;
        } else
            return "";
    }
    
    /*@WebMethod(operationName = "addUser")
    public boolean addUser(
            @WebParam(name = "listUser") String strUser,
            @WebParam(name = "key") String key) {
        if (cript.getKey().equals(key)) {
            String listUser[] = strUser.split("@@");
            Usuario user = new Usuario();
            //user.setId(Integer.parseInt(listUser[0]));
            user.setNome(listUser[0]);
            user.setTelefone(listUser[1]);
            user.setEmail(listUser[2]);
            user.setNascimento(listUser[3]);
            user.setLogin(listUser[4]);
            user.setSenha(listUser[5]);
            user.setTipo(Integer.parseInt(listUser[6]));
            user.setFoto(listUser[7].getBytes());
            userDAO.inserir(user);
            return true;
        } else
            return false;
    }*/
    
    @WebMethod(operationName = "addUser")
    public int addUser(
            @WebParam(name = "listUser") String strUser,
            @WebParam(name = "key") String key) {
        String[] retorno = new String[1];
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            Usuario user = new Usuario();
            boolean cancel = false;
            UsuarioDAO userDAO = new UsuarioDAO();
            user.toUsuario(strUser);
            //user.setId(Integer.parseInt(listUser[0]));
            /*user.setNome(listUser[0]);
            user.setTelefone(listUser[1]);
            user.setNascimento(listUser[2]);
            user.setEmail(listUser[3]);
            user.setLogin(listUser[4]);
            user.setSenha(listUser[5]);
            user.setTipo(Integer.parseInt(listUser[6]));*/
            //user.setFoto(listUser[7].getBytes());
            
            if (userDAO.ItIsInTheDatabase(CONST.USERTABLOGIN, user.getLogin())) {
                //retorno[0] = Integer.toString(CONST.USERLOGINEXIST);
                return CONST.USERLOGINEXIST;
            }
            
            if (userDAO.ItIsInTheDatabase(CONST.USERTABEMAIL, user.getEmail())) {
                //retorno[0] = Integer.toString(CONST.USEREMAILEXIST);
                return CONST.USEREMAILEXIST;
            }
            //this.ObterAcessoBD();
            userDAO.inserir(user);
            userDAO.closeSession();
            //this.LiberarAcessoBD();
            //retorno[0] = Integer.toString(CONST.USERSUCESS);
            return CONST.SUCESS;
        } else {
            //retorno[0] = Integer.toString(CONST.KEYERROR);
            return CONST.KEYERROR;
        }
    }
    
    @WebMethod(operationName = "login")
    public String login(
            @WebParam(name = "login") String login,
            @WebParam(name = "senha") String senha,
            @WebParam(name = "key") String key) {
        String retorno = "";
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            Usuario user = null;
            UsuarioDAO userDAO = new UsuarioDAO();
            
            user = userDAO.getUsuario(login, senha);
            //System.out.println(user.getNome());
            if (user != null) {
                retorno = user.toString();
                userDAO.closeSession();
                return retorno;
            } else {
                userDAO.closeSession();
                return null;
            }
        } else {
            retorno = "errohorario";
            return retorno;
        }
    }
    
    @WebMethod(operationName = "saveUser")
    public String saveUser(
            @WebParam(name = "listUser") String strUser,
            @WebParam(name = "key") String key) {
        Usuario user = null;
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            //String listUser[] = strUser.split("@@");
            user = new Usuario();
            UsuarioDAO userDAO = new UsuarioDAO();
            user.toUsuario(strUser);
            
            user.setSala(null);
            
            userDAO.atualizar(user);
            userDAO.closeSession();
        }
        return user.toString();
    }
    
    @WebMethod(operationName = "getUserbyId")
    public String getUserbyId(
            @WebParam(name = "userId") int userId,
            @WebParam(name = "key") String key) {
        Usuario user = null;
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            UsuarioDAO userDAO = new UsuarioDAO();
            user = userDAO.getUsuarioById(userId);
            userDAO.closeSession();
        }
        return user.toString();
    }
    
    @WebMethod(operationName = "resetarSala")
    public String resetarSala(
            @WebParam(name = "idUser") int idUser,
            @WebParam(name = "key") String key) {
        Usuario user = new Usuario();
        Sala sala = new Sala();
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            //String listUser[] = strUser.split("@@");
            UsuarioDAO userDAO = new UsuarioDAO();
            user = userDAO.getUsuarioById(idUser);
            userDAO.closeSession();
            if (user != null) {
                if (user.getSala() != null) {
                    SalaDAO salaDAO = new SalaDAO();
                    sala = salaDAO.getSala(user.getSala().getId());
                    if (sala != null) {
                        sala.getListUser().remove(user);
                        salaDAO.atualizar(sala);
                    }
                    salaDAO.closeSession();
                }
                user.setSala(null);
                userDAO = new UsuarioDAO();
                userDAO.atualizar(user);
                userDAO.closeSession();
            } else
                return "UsuarioNaoIdentificado";
        }
        return user.toString();
    }
    
    //Funções referentes à sala
    
    @WebMethod(operationName = "listarAreas")
    public List<Area> listarAreas(
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            List<Area> listArea = new ArrayList();
            List<SubArea> listSubArea = new ArrayList();
            
            AreaDAO areaDAO = new AreaDAO();
                    
            listArea = areaDAO.getLista();
            
            for (Area a : listArea) {
                a.setSubArea(new HashSet<>(areaDAO.getSubAreas(a.getId())));
            }
            
            //System.out.printf("Área adicionada: " + listArea.get(0).getNome());
            //System.out.printf("SubÁrea adicionada: " + listArea.get(0).getSubArea().get(0).getNome());
            areaDAO.closeSession();
            return listArea;
        } else
            return new ArrayList();
    }
    
    /*@WebMethod(operationName = "listarSubAreas")
    public List<SubArea> listarSubAreas(
            @WebParam(name = "idArea") int idArea,
            @WebParam(name = "key") String key) {
        System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        System.out.println("IDAREAWEBSERVICE = " + idArea);
        if (cript.getKey().equals(key)) {
            List<SubArea> list = new ArrayList();
            
            list = subAreaDAO.getSubAreasForIdArea(idArea);
            return list;
        } else
            return new ArrayList();
    }*/
    
    /*@WebMethod(operationName = "gerarConjunto")
    public ConjuntoQuestoes gerarConjunto (
            @WebParam(name = "numQuestoes") int numQuestoes,
            @WebParam(name = "idArea") int idArea,
            @WebParam(name = "idSubArea") int idSubArea,
            @WebParam(name = "key") String key) {
        ConjuntoQuestoes qQuestoes = new ConjuntoQuestoes();
        if (cript.getKey().equals(key)) {
            qQuestoes = cqDAO.getConjuntoQuestoes(numQuestoes, idArea, idSubArea);
        }
        return qQuestoes;
    }*/
    
    @WebMethod(operationName = "getConjuntoQuestoes")
    public String[] getConjuntoQuestoes (
            @WebParam(name = "idSala") int idSala,
            @WebParam(name = "key") String key) {
        //ConjuntoQuestoes cq = new ConjuntoQuestoes();
        String[] response = null;
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
             ConjuntoQuestoesDAO cqDAO = new ConjuntoQuestoesDAO();
             response = cqDAO.getVectorConjuntoById(idSala);
             cqDAO.closeSession();
             //System.out.printf("CONJUNTO DE QUESTÕES NÚMERO: " + cq.getId());
         }
         return response;
    }
    
    @WebMethod(operationName = "deletarConjunto")
    public void deletarConjunto (
            @WebParam(name = "idConjQuest") int idConjQuest,
            @WebParam(name = "key") String key) {
        ConjuntoQuestoes qQuestoes = new ConjuntoQuestoes();
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            //Verificar relações com outras tabelas
            ConjuntoQuestoesDAO cqDAO = new ConjuntoQuestoesDAO();
            cqDAO.remover(cqDAO.getConjuntoById(idConjQuest));
            cqDAO.closeSession();
        }
    }
    
    //Apagar esta função
    /*public void imprimeTabelaSubAreas(String str) {
        List<Area> lArea = areaDAO.getLista();
        List<SubArea> lSubArea = new ArrayList<>();
        for (Area a : lArea) {
            for (SubArea sa : a.getSubArea()) {
                lSubArea.add(sa);
            }
        }
        System.out.println("Listando informações da tabela de subárea ("+ str + "):\n");
        for (SubArea sa : lSubArea) {
            System.out.println("\tSubÁrea:");
            System.out.println("\t\tId:" + sa.getId() + "\n");
            System.out.println("\t\tId:" + sa.getNome()+ "\n");
        }
    }*/
    
    @WebMethod(operationName = "criarSala")
    public String[] criarSala (
            @WebParam(name = "numQuestoes") int numQuestoes,
            @WebParam(name = "listSala") String strSalas,
            @WebParam(name = "key") String key) {
        //String[] retorno = new String[1];
        Sala sala = new Sala();
        //ConjuntoQuestoes qQuestoes = new ConjuntoQuestoes();
        String[] response = null;
        //imprimeTabelaSubAreas("Antes do cript");
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            /*String listSalas[] = strSalas.split("@@");
            boolean cancel = false;
            //System.out.println(listSalas[3]);
            
            //user.setId(Integer.parseInt(listUser[0]));
            sala.setNome(listSalas[0]);
            if (salaDAO.ItIsInTheDatabase(CONST.SALATABNOME, sala.getNome())) {
                sala.setId(0);
                return sala.toVectorString();
            }
            
            sala.setTipo(listSalas[1]);
            sala.setSenha(listSalas[2]);
            
            Area a = new Area(Integer.parseInt(listSalas[3]), listSalas[4]);
            List<SubArea> lsa = subAreaDAO.getSubAreasForIdArea(a.getId());
            //for (SubArea sa : lsa)
                //System.out.println(sa.getId() + " - " + sa.getNome() + "\n");
            a.setSubArea(new HashSet<>(lsa));
            sala.setArea(a);
            //imprimeTabelaSubAreas("Depois de definir área");
            sala.setSubArea(new SubArea(Integer.parseInt(listSalas[5]), listSalas[6]));
            //imprimeTabelaSubAreas("Depois de definir subárea");
            
            sala.setMaxUserSala(sala.getTipoInt()*2);
            
            Usuario u = userDAO.getUsuarioById(Integer.parseInt(listSalas[7]));
            
            sala.getListUser().add(u);
            
            //salaDAO.inserir(sala);
            //System.out.println("ID DA SALA CRIADA: " + sala.getId());
            //imprimeTabelaSubAreas("Depois de imprimir id da sala criada");
            
            qQuestoes = cqDAO.novoConjuntoQuestoes(numQuestoes, sala.getArea().getId(), sala.getSubArea().getId());
            //imprimeTabelaSubAreas("Depois de setarconjunto de questões");
            
            sala.setConjuntoQuestoes(qQuestoes);
            salaDAO.inserir(sala);
            
            u.setSala(sala);
            userDAO.atualizar(u);*/
            
            SalaDAO salaDAO = new SalaDAO();
            //this.ObterAcessoBD();
            response = salaDAO.CriarSala(numQuestoes, strSalas);//salaDAO.getSalaVectorStr(sala.getId());
            salaDAO.closeSession();
            //this.LiberarAcessoBD();
            
            //imprimeTabelaSubAreas("Final da função");
        } else {
            sala = new Sala(-1);
            response = sala.toVectorString();
        }
        
        /*for(String s : response) {
            System.out.println(s);
        }*/
        
        return response;
    }
    
    @WebMethod(operationName = "listarSalas")
    public String[] listarSalas(
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Retornar apenas id, nome, tipo, area, subarea. Posteriormente senha
        //Compara chaves
        String[] retorno = null;
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            SalaDAO salaDAO = new SalaDAO();
            retorno = salaDAO.getListVectStr();
            salaDAO.closeSession();
            return retorno;
        } else {
            retorno = new String[1];
            retorno[0] = "Erro";
            return retorno;
        }
    }
    
    /*@WebMethod(operationName = "atualizaSalaIdConjQuestoes")
    public void atualizaSalaIdConjQuestoes(
            @WebParam(name = "idSala") int idSala,
            @WebParam(name = "idConjuntoQuestoes") int idConjuntoQuestoes,
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        if (cript.getKey().equals(key)) {
            Sala s = salaDAO.getSala(idSala);
            s.setIdConjuntoQuestoes(idConjuntoQuestoes);
            salaDAO.atualizar(s);
        }
    }*/
    
    
    @WebMethod(operationName = "adcUsuario")
    public String[] adcUsuario(
            @WebParam(name = "salaId") int salaId,
            @WebParam(name = "strUser") String strUser,
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        //Sala sala = new Sala();
        //Usuario user = new Usuario();
        String[] response = null;
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            
            /*sala = salaDAO.getSala(salaId);
            
            if (sala != null) {
                user.toUsuario(strUser);

                user = userDAO.getUsuarioById(user.getId());

                if (sala.getListUser().size() < sala.getMaxUserSala()) {
                    sala.getListUser().add(user);
                    if (sala.getListUser().size() == sala.getMaxUserSala()) {
                        sala.setVisivel(false);
                    }
                    DAO.update(sala);

                    response = salaDAO.getSalaVectorStr(salaId);
                } else {
                    //sala.setVisivel(false);
                    //salaDAO.update(sala);
                    //salaDAO.closeSession();
                    
                    sala = new Sala(0);
                    response = sala.toVectorString();
                }
            } else {
                sala = new Sala(0);
                response = sala.toVectorString();
            }*/
            SalaDAO salaDAO = new SalaDAO();
            //this.ObterAcessoBD();
            response = salaDAO.AdicionaUsuario(salaId, strUser);
            salaDAO.closeSession();
            //this.LiberarAcessoBD();
        }
        return response;
    }
    
    @WebMethod(operationName = "removeUserSala")
    public String[] removeUserSala(
            @WebParam(name = "userId") int userId,
            @WebParam(name = "salaId") int salaId,
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        Sala sala = new Sala();
        Usuario user = new Usuario();
        String[] response = sala.toVectorString();
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            
            /*user = userDAO.getUsuarioById(userId);
            
            sala = salaDAO.getSala(salaId);
            
            sala.getListUser().remove(user);
            
            if (sala.getListUser().size() < sala.getMaxUserSala())
                sala.setVisivel(true);
                
            
            salaDAO.atualizar(sala);*/
            SalaDAO salaDAO = new SalaDAO();
            response = salaDAO.RemoveUsuario(userId, salaId);//salaDAO.getSalaVectorStr(salaId);
            salaDAO.closeSession();
        }
        return response;
    }
    
    @WebMethod(operationName = "atualizaSala")
    public String[] atualizaSala(
            @WebParam(name = "idSala") int idSala,
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        Sala sala = new Sala();
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            SalaDAO salaDAO = new SalaDAO();
            sala = salaDAO.getSala(idSala);
            salaDAO.closeSession();
        }
        return sala.toVectorString();
    }
    
    @WebMethod(operationName = "getNumSalasAtivas")
    public int getNumSalasAtivas(
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        int num = 0;
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            SalaDAO salaDAO = new SalaDAO();
            num = salaDAO.getNumSalasAtivas();
            salaDAO.closeSession();
        }
        return num;
    }
    
    @WebMethod(operationName = "getSalaForId")
    public String[] getSalaForId(
            @WebParam(name = "idSala") int idSala,
            @WebParam(name = "key") String key) {
        //System.out.println("getSalaForId: " + "idSala: " + idSala + " - " + key + " - " + cript.getKey());
        //Compara chaves
        Sala sala = new Sala();
        String[] response = sala.toVectorString();
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            //System.out.println("Sala id: " + idSala);
            SalaDAO salaDAO = new SalaDAO();
            response = salaDAO.getSalaVectorStr(idSala);
            salaDAO.closeSession();
        }
        return response;
    }
    
    @WebMethod(operationName = "deletarSala")
    public int deletarSala(
            @WebParam(name = "idSala") int idSala,
            @WebParam(name = "key") String key) {
        //System.out.println("DELETAR SALA: " + key + " - " + cript.getKey());
        //Compara chaves
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            
            //Pegando Conjunto e Sala
            /*Sala s = salaDAO.getSala(idSala);
            //salaDAO.closeSession();
            
            ConjuntoQuestoes c = s.getConjuntoQuestoes();
            
            //Removendo conjunto de questões
            c.setRespostas(new HashSet<Resposta>());
            DAO.update(c);
            c.setQuestoes(new HashSet<Questao>());
            DAO.update(c);
            s.setConjuntoQuestoes(null);
            DAO.update(s);
            DAO.remove(c);
            
            //Apagando area e subárea
            //s.setConjuntoQuestoes(null);
            s.setArea(null);
            s.setSubArea(null);
            DAO.update(s);
            
            //Apagando usuários da sala
            List<Usuario> listUsers = new ArrayList<>(s.getListUser());
            for (Usuario u : listUsers) {
                u.setSala(null);
                DAO.update(u);
            }
            s.setListUser(new HashSet<Usuario>());
            DAO.update(s);
            
            DAO.remove(s);*/
            
            SalaDAO salaDAO = new SalaDAO();
            //this.ObterAcessoBD();
            salaDAO.DeletarSala(idSala);
            salaDAO.closeSession();
            //this.LiberarAcessoBD();
            
            return CONST.SUCESS;
        } else
            return CONST.ERROR;
    }
    
    /*
    ------------------
    Funções de ranking
    ------------------
    */
    
    @WebMethod(operationName = "salvaRankingTemp")
    public String[] salvaRankingTemp(
            @WebParam(name = "str") String str,
            @WebParam(name = "salaId") int salaId,
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        RankingTemp ramTem = new RankingTemp();
        String[] resultado = null;
        //System.out.println(str);
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            RankingDAO rDAO = new RankingDAO();
            
            ramTem.toRanking(str);
            ramTem.setIdSala(salaId);
            
            //this.ObterAcessoBD();
            
            rDAO.inserirRankingTemp(ramTem);
            
            resultado = rDAO.getListRamTempsForSala(salaId);
            
            rDAO.closeSession();
            
            //this.LiberarAcessoBD();
        }
        return resultado;
    }
    
    @WebMethod(operationName = "retornaRankingsTemps")
    public String[] retornaRankingsTemps(
            @WebParam(name = "idSala") int idSala,
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        //String[] ids = str.split("##");
        RankingTemp ramTem = new RankingTemp();
        String[] retorno;
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            RankingDAO rDAO = new RankingDAO();
            retorno = rDAO.getListRamTempsForSala(idSala);
            rDAO.closeSession();
        } else {
            return null;
        }
        return retorno;
    }
    
    @WebMethod(operationName = "salvaRankings")
    public void salvaRankings(
            @WebParam(name = "salaId") int salaId,
            @WebParam(name = "userId") int userId,
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        /*RankingTemp ramTem = new RankingTemp();
        Ranking ram = new Ranking();*/
        //String[] ids = str.split("##");
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            //Cria lista vazia de rankisngs a ser salva
            //List<Ranking> listRank = new ArrayList<>();
            
            //Pegar lista de rankings temporários, sala e usuários da sala
            /*List<RankingTemp> listRankTemp = rDAO.getListRamTemps(salaId);
            Sala sala = salaDAO.getSala(salaId);
            Usuario user = userDAO.getUsuarioById(userId);
            Set<Usuario> listUsers = new HashSet<Usuario>();
            if (sala != null)
                listUsers = sala.getListUser();
            
            //Salva ranking temporário do usuário
            RankingTemp rTemp = rDAO.getRankingTempForIdUser(user.getId());
            //System.out.println("Acertos: " + rTemp.getMedia5acertos());
            Ranking r = new Ranking();
            if (rDAO.ItIsInTheDatabase("idUser", user.getId(), CONST.RANKING)) {
                r = rDAO.getRankingForIdUser(user.getId());
                float media = (r.getMedia5acertos() + rTemp.getMedia5acertos())/2;
                r.setMedia5acertos(media);
                media = (r.getMediaTempo() + rTemp.getMediaTempo())/2;
                r.setMediaTempo(media);
                rDAO.atualizarRanking(r);
            } else {
                r = new Ranking(rTemp);
                rDAO.inserirRanking(r);
            }
            
            //Retirando usuário da sala e excluindo rabkings temporários apenas se não há mais usuários na sala
            sala.getListUser().remove(user);
            salaDAO.update(sala);
            if (sala.getListUser().isEmpty()) {
                for (RankingTemp rt : listRankTemp)
                    rDAO.removerRankingTemp(rt);
            }
            
            if (sala.getListUser().isEmpty())
                this.deletarSala(salaId, key);*/
            
            //Caso o ranking teporario do usuário não exista, penalizar definindo como 0
            /*boolean achou = false;
            if (ids.length != listRankTemp.size()) {
                for (String s : ids) {
                    int id = 0;
                    for (RankingTemp rt : listRankTemp) {
                        if (rt.getIdUser().equals(Integer.parseInt(s))) {
                            achou = true;
                            listRank.add(new Ranking(rt));
                            break;
                        } else {
                            id = rt.getIdUser();
                        }
                    }
                    if (!achou) {
                        listRank.add(new Ranking(id));
                    }
                }
            }*/
            
            /*boolean achou = false;
            boolean listaRTVazia = false;
            for (Usuario u : listUsers) {
                if (listRankTemp.isEmpty()) {
                    listaRTVazia = true;
                    break;
                } else {
                    int id = 0;
                    for (RankingTemp rt : listRankTemp) {
                        if (rt.getIdUser().equals(u.getId())) {
                            achou = true;
                            listRank.add(new Ranking(rt));
                            break;
                        } else {
                            id = rt.getIdUser();
                        }

                    }
                    if (!achou) {
                        listRank.add(new Ranking(id));
                    }
                }
            }
            //Apagar rankings temporários com as ids especificadas
            if (!listaRTVazia) {
                for (RankingTemp rt : listRankTemp) {
                    rDAO.removerRankingTemp(rt);
                }
                //Verificar se existe ranking salvo com as ids especificadas
                //Pegar as que existem, fazer uma média e fazer um update
                //inserir as que não existirem
                for (Ranking r : listRank) {
                    if (rDAO.ItIsInTheDatabase("idUser", r.getIdUser(), CONST.RANKING)) {
                        Ranking rTemp = rDAO.getRankingForIdUser(r.getIdUser());
                        float media = (rTemp.getMedia5acertos() + r.getMedia5acertos())/2;
                        rTemp.setMedia5acertos(media);
                        media = (rTemp.getMediaTempo() + r.getMediaTempo())/2;
                        rTemp.setMediaTempo(media);
                        rDAO.atualizarRanking(rTemp);
                    } else {
                        rDAO.inserirRanking(r);
                    }
                }
            }*/
            
            
            
            /*ramTem.toRanking(str);
            if (rDAO.ItIsInTheDatabase("id", ramTem.getIdUser().toString(), CONST.RANKING)) {
                ram = rDAO.getRanking(ramTem.getIdUser().toString());
                float media = ram.getMedia5acertos()+ramTem.getMedia5acertos();
                media /= 2;
                ram.setMedia5acertos(media);
                media = ram.getMediaTempo()+ramTem.getMediaTempo();
                media /= 2;
                ram.setMediaTempo(media);
                rDAO.atualizarRanking(ram);
            } else {
                ram = new Ranking(
                        ramTem.getIdUser(),
                        ramTem.getMedia5acertos(),
                        ramTem.getMediaTempo());
                rDAO.inserirRanking(ram);
            }
            rDAO.removerRankingTemp(ramTem);*/
            
            RankingDAO rDAO = new RankingDAO();
            //this.ObterAcessoBD();
            rDAO.SalvarRankings(salaId, userId);
            rDAO.closeSession();
            //this.LiberarAcessoBD();
            
        } else {
            //ramTem.setIdUser(0);
        }
        //return ramTem.toString();
    }
    
    @WebMethod(operationName = "listarRankings")
    public String[] listarRankings(
            @WebParam(name = "key") String key) {
        //System.out.println(key + " - " + cript.getKey());
        //Compara chaves
        String[] retorno = null;
        Cript cript = new Cript("");
        if (cript.getKey().equals(key)) {
            /*List<Ranking> list = new ArrayList();
            
            list = rDAO.getListaRanking();
            
            retorno = new String[list.size()];
            
            int i = 0;
            for (Ranking r : list) {
                Usuario u = new Usuario();
                u = userDAO.getUsuarioById(r.getIdUser());
                retorno[i++] = r.toString(u);
                //System.out.println(retorno[i-1]);
            }*/
            RankingDAO rDAO = new RankingDAO();
            retorno = rDAO.ListarRankings();
            rDAO.closeSession();
            
            return retorno;
        } else
            return null;
    }
}
