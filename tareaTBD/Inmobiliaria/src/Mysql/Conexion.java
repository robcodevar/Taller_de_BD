/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author bobvo
 */
public class Conexion {

    private Connection conectar = null;
    private final String usuario = "usertbd";
    private final String contrasenia = "root";

    //String ip = "DESKTOP-0SDU8T2" ;
    String ip = "localhost";
    String puerto = "1433";
    private final String bd = "Taller";
    //private final String cadena = "jdbc:sqlserver://"+ip+":"+puerto+"/"+bd;

    public Conexion() throws SQLException{
        try {
            conectar();
            transaccionCliente(51, 4 , 731234, "Rodrio" , "c. Avaroa , ladislao cabrera","correo@gmail.com");
        } finally{
            
        }
    }
    
    /*public Connection establecerConexion() throws SQLException {
    }*/
    public void conectar() throws SQLException{
        
        try {
            String connectionUrl = "jdbc:sqlserver://" + ip + ":" + puerto + ";databaseName=" + bd + ";user=" + usuario + ";password=" + contrasenia + ";encrypt=false;";
            //jdbc:sqlserver://localhost:1433;databaseName=AdventureWorks;
            //String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=Taller;user=usertbd;password=root;encrypt=false;";
            /*
            Connection con = DriverManager.getConnection(connectionUrl);
             */
            conectar = DriverManager.getConnection(connectionUrl);
            JOptionPane.showMessageDialog(null, ">Estoy dentro, conexion establecida ");
            conectar.setAutoCommit(false);
        } catch (SQLException sql) {
            JOptionPane.showMessageDialog(null, ">Error con el intento de conexion a la BD , revisa el output de la consola");
            sql.printStackTrace();
        }
    }
    
    public final void cerrar() throws SQLException{
        if (conectar != null) {
                try {
                    conectar.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }
    
    public void consulta() throws SQLException{
        Statement statement = conectar.createStatement();
        ResultSet set = statement.executeQuery("select * from Sucursal");
        while(set.next()){
            int idSucursal = set.getInt("ID_SUCURSAL");
            String direccion_sucursal = set.getString("DIRECCION_SUCURSAL");
            String descripcionSucursal = set.getString("DESCRIPCIONSUCURSAL");
            System.out.println("Id sucursal: "+idSucursal+"\n direccion SUCURSAL : "+
                    direccion_sucursal+"\n descripcion Sucursal : "+descripcionSucursal);
        }
        set.close();
        statement.close();
    }
    
    public void transaccionCliente(int idCli, int idSuc , int ciCliente , String nomCom , String direc ,String correo ) throws SQLException {
        final String Cliente = "insert into CLIENTE(ID_CLIENTE ,ID_SUCURSAL ,CI_CLIENTE ,NOMBRE_COMPLETO ,DIRECCION_CLIENTE ,CORREO_CLIENTE) VALUES (? , ? , ? , ? , ? , ?)";
        PreparedStatement cliente = null;
        try {
            cliente = conectar.prepareStatement(Cliente);
            cliente.setInt(1, idCli);
            cliente.setInt(2, idSuc);
            cliente.setInt(3, ciCliente);
            cliente.setString(4, nomCom );
            cliente.setString(5, direc);
            cliente.setString(6, correo);
            cliente.executeUpdate();
            
            conectar.commit();            
        } catch (SQLException ex) {
            //En caso de ocurrir alguna falla al realizar la transaccion anterior 
            //Deshacemos  la transaccion oon un rollback y devolvemos el error;
            conectar.rollback();
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }finally {
            if (cliente != null){
                // al terminar la transaccion o el informe de error se cierra el proceso
                //de la declaracion preparada (PreparedStatement) de Cliente
                cliente.close();
            }
        }
        
    }
    
    public void transaccionInteres(int idInte , int idCli , int idEsp , String tipo , String zone, int prespt , String tOferta , int idContrat ) throws SQLException {
        final String Interes = "insert into INTERES(ID_INTERES ,ID_CLIENTE ,IDESPECIFICOS, TIPO_INMUEBLE ,__ZONA_,PRESUPUESTO, TIPO_OFERTA , ID_CONTRATO) VALUES (? , ? , ? , ? , ? , ? , ? , ?)";
        PreparedStatement interes = null;
         try {
            interes = conectar.prepareStatement(Interes);
            interes.setInt(1, idInte);
            interes.setInt(2, idCli);
            interes.setInt(3, idEsp);
            interes.setString(4, tipo );
            interes.setString(5, zone);
            interes.setInt(6, prespt);
            interes.setString(7, tOferta);
            interes.setInt(8 , idContrat);
            interes.executeUpdate();
            
            conectar.commit();
        } catch (SQLException ex) {
            //En caso de ocurrir alguna falla al realizar la transaccion anterior 
            //Deshacemos la transaccion oon un rollback y devolvemos el error;
            conectar.rollback();
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }finally {
            if (interes != null){
                // al terminar la transaccion o el informe de error se cierra el proceso
                //de la declaracion preparada (PreparedStatement) de interes
                interes.close();
            }
        }
    }
    
    public void transaccionInteresEspecificos(int idEsp , int idInt , short numDorm , short numbanios, boolean Garage , boolean Jardin, String serBas , byte ascensor , byte estacionamiento ) throws SQLException {
        final String InteresEsp = "insert into INTERESES_ESPECIFICOS(IDESPECIFICOS , ID_INTERES ,NUMDORMITORIOS , NUMBANOS , GARAJE , JARDIN , SERVICIOS_BASICOS , ASCENSOR, ESTACIONAMIENTO) VALUES (? , ? , ? , ? , ? , ? , ? , ? , ?)";
        PreparedStatement Interesp = null;
         try {
            Interesp = conectar.prepareStatement(InteresEsp);
            Interesp.setInt(1, idEsp);
            Interesp.setInt(2, idInt);
            Interesp.setShort(3, numDorm);
            Interesp.setShort(4, numbanios );
            Interesp.setBoolean(5, Garage);
            Interesp.setBoolean(6, Jardin);
            Interesp.setString(7 , serBas);
            Interesp.setByte(8 , ascensor);
            Interesp.setByte(9 , estacionamiento);
            Interesp.executeUpdate();
            
            conectar.commit();
        } catch (SQLException ex) {
            //En caso de ocurrir alguna falla al realizar la transaccion anterior 
            //Deshacemos la transaccion oon un rollback y devolvemos el error;
            conectar.rollback();
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }finally {
            if (Interesp != null){
                // al terminar la transaccion o el informe de error se cierra el proceso
                //de la declaracion preparada (PreparedStatement) de interes Especificos
                Interesp.close();
            }
        }
    }
    
}