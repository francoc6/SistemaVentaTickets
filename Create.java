import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Crear
{
        public static void main(String[] args) {
        //parametros de lugar
        String lugar,direccion;
        int capacidad;
        String[] seccion;
        //parametros de evento
        String tema,fecha;
        float[] costo;
        //parametros de usuario
        String nombre, usuario, pass;
        System.out.println("Ingrese la opcion a realizar 1.Crear Usuario 2.Crear Lugar 3.Crear Evento");
        Scanner sc=new Scanner(System.in);
        int op=Integer.parseInt(sc.nextLine());
        switch(op){
                case 1: Usuario();
                        break;
                case 2: Venue();
                        break;
                case 3: Evento();
                        break;
        }
        }

        public static void Usuario(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Ingrese su nombre: ");
        String nom=sc.nextLine();
        System.out.println("Ingrese su usuario: ");
        String usr=sc.nextLine();
        System.out.println("Ingrese su password: ");
        String contra=sc.nextLine();
        String orden="insert into Usuario(Nombre,Usuario,Pass) values ('"+nom+"','"+usr+"','"+contra+"')";
        //conexion
        try{
        String dbClassName = "com.mysql.jdbc.Driver";
        String CONNECTION ="jdbc:mysql://52.14.132.218:3306/proyecto?autoReconnect=true&useSSL=false";
        Class.forName(dbClassName);
        // Properties for user and password. Here the user and password are both 'paulr'
        Properties p = new Properties();
        p.put("user","invitado");
        p.put("password","password");
        // Now try to connect
        Connection c = DriverManager.getConnection(CONNECTION,p);
        //crear objeto statement
        Statement myStatement=c.createStatement();
        //excecute query
        myStatement.executeUpdate(orden);
        System.out.println("Exito");
        c.close();
        }catch(Exception e){
         e.printStackTrace();
        }
        }
        public static void Venue(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Ingrese el nombre del local: ");
        String nom=sc.nextLine();
        System.out.println("Ingrese la direccion: ");
        String dir=sc.nextLine();
        System.out.println("Ingrese el tipo: ");
        String tipo=sc.nextLine();
        System.out.println("Ingrese la capacidad: ");
        int cap=Integer.parseInt(sc.nextLine());
        System.out.println("Cuantos tipos de localidades existen: ");
        int nl=Integer.parseInt(sc.nextLine());
        String res="";
        for(int i=0;i<nl;i++){
                System.out.println("Ingrese el nombre de la localidad"+(i+1)+": ");
                String nombloc=sc.nextLine();
                System.out.println("Ingrese la capacidad de  la  localidad: ");
                String capacidad=sc.nextLine();
                res+=nombloc+"-"+capacidad+" ";
        }
        String orden="insert into Lugar(Nombre,Direccion,Tipo,Capacidad,Localidad_cap) values ('"+nom+"','"+dir+"','"+tipo+"',"+cap+",'"+res+"')";
        //conexion
        try{
                String dbClassName = "com.mysql.jdbc.Driver";
                String CONNECTION ="jdbc:mysql://52.14.132.218:3306/proyecto?autoReconnect=true&useSSL=false";
                Class.forName(dbClassName);
                // Properties for user and password. Here the user and password are both 'paulr'
                Properties p = new Properties();
                p.put("user","invitado");
                p.put("password","password");
                // Now try to connect
                Connection c = DriverManager.getConnection(CONNECTION,p);
                //crear objeto statement
                Statement myStatement=c.createStatement();
                //excecute query
                myStatement.executeUpdate(orden);
                System.out.println("Exito");
                c.close();
        }catch(Exception e){
                e.printStackTrace();
        }
        }



        public static void Evento(){
             Scanner sc= new Scanner(System.in);
             System.out.println("Ingrese el nombre de evento: ");
             String ne=sc.nextLine();
             System.out.println("Ingrese el id del lugar: ");
             String lugar=sc.nextLine();
             System.out.println("Ingrese la fecha dd/mm/aa: ");
             String f=sc.nextLine();
             System.out.println("Ingrese la hora hh:mm: ");
             String h=sc.nextLine();
             String orden="select Localidad_cap from Lugar where Id="+lugar; //conexion
                   try{
                String dbClassName = "com.mysql.jdbc.Driver";
                String CONNECTION ="jdbc:mysql://52.14.132.218:3306/proyecto?autoReconnect=true&useSSL=false";
                Class.forName(dbClassName);
                // Properties for user and password. Here the user and password are both 'paulr'
                Properties p = new Properties();
                p.put("user","invitado");
                p.put("password","password");
                // Now try to connect
                Connection c = DriverManager.getConnection(CONNECTION,p);
                //crear objeto statement
                Statement myStatement=c.createStatement();
                //excecute query
                ResultSet myResultSet=myStatement.executeQuery(orden);
                String localidad="";
                while (myResultSet.next()){
                        localidad=myResultSet.getString("Localidad_cap");
                }
                String[] partes;
                partes= localidad.split(" ");
                String Costo="";
                for (int i=0; i<partes.length;i++){
                        String texto=partes[i];
                        String [] ubicacion=texto.split("-");
                        System.out.println("El precio para "+ubicacion[0]+" es: ");
                        String precio=sc.nextLine();
                        Costo+=precio+" ";
               }
               String orden2="insert into Evento(Nombre,Lugar,Fecha,Hora,Costos) values ('"+ne+"','"+lugar+"','"+f+"','"+h+"','"+Costo+"')";
               myStatement.executeUpdate(orden2);
               String orden3="select * from Lugar where Id="+lugar;
               myResultSet=myStatement.executeQuery(orden3);
               String capac;
               myResultSet.next();
               capac=myResultSet.getString("Localidad_cap");
               String[] parte;
               parte= localidad.split(" ");
               String Tot="";
               for (int i=0; i<parte.length;i++){
                        String texto=parte[i];
                        String [] num=texto.split("-");
                        Tot+=num[1]+" ";
               }

			   String orden4="insert into Venta(Disponibilidad) values ('"+Tot+"')";
               myStatement.executeUpdate(orden4);
               System.out.println("Exito");
               c.close();
               }catch(Exception e){
                        e.printStackTrace();
               }
        }

}

