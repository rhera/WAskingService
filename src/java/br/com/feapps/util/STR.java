package br.com.feapps.util;

import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author F.Einstein
 */
public class STR {
    public static String[] Concatenar(String[] str1, String[] str2) {
        String[] retorno = new String[str1.length+str2.length+1];
        int j = 0;
        for (int i = 0; i < str1.length; i++)
            retorno[j++] = str1[i];
        retorno[j++] = "$concat";
        for (int i = 0; i < str2.length; i++)
            retorno[j++] = str2[i];
        return retorno;
    }
    
    public static String toString(String[] strings) {
        String buffer="";
        for(int i=0;i<strings.length;i++) {
            buffer += strings[i];
            if(i < strings.length)
                buffer += "@@";
        }
        return buffer;
    }

    public static String[] toVectorString (String str) {
        return str.split(Pattern.quote("@@"));
    }
}
