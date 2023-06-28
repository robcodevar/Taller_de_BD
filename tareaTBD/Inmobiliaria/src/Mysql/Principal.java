/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mysql;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;

/**
 *
 * @author bobvo
 */
public class Principal {
    private static final Logger LOG = LoggerFactory.getLogger(Principal.class);
    
    public static void main(String args []) throws SQLException{
        try{
            Conexion c = new Conexion();
        }
        catch(SQLException e){
            LOG.error("Error durante el uso del JDBC");
        }
    }
    
    /*public Principal() throws SQLException{
        try {
            conectar();
            consulta();
        } finally{
            
        }
    }
    */
    
    
}
