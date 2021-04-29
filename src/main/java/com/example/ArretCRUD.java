package com.example;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/arrets")
public class ArretCRUD{
    @Autowired
    private DataSource dataSource;

    @GetMapping("/")
    public ArrayList<Arret> allArrets(HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM arret");

            ArrayList<Arret> L = new ArrayList<Arret>();
            while (rs.next()) {
                Arret u = new Arret();
                u.nomArret = rs.getString("nomArret");
                u.code = rs.getString("code");
                u.streetMap = rs.getString("streetMap");
                u.googleMap = rs.getString("googleMap");
                u.nomVille = rs.getString("nomVille");
                L.add(u);
            }
            return L;
        } catch(Exception e){
            response.setStatus(500);
        try{
            response.getOutputStream().print(e.getMessage());
        } catch (Exception e2) {
            System.err.println(e2.getMessage()); 
        }
        System.err.println(e.getMessage());
        return null;
        }
    }

    @GetMapping("/{nomArret}")
    public Arret read(@PathVariable(value="nomArret") String id, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM arret WHERE nomArret='"+ id +"'");

            Arret u = new Arret();
            if(!rs.next()){
                response.setStatus(404);
                response.getOutputStream().print("erreur 404");
                return null;
            }else{
                u.nomArret = rs.getString("nomArret");
                u.code = rs.getString("code");
                u.streetMap = rs.getString("streetMap");
                u.googleMap = rs.getString("googleMap");
                u.nomVille = rs.getString("nomVille");
                return u;
            }
        } catch(Exception e){
            response.setStatus(500);
            try{
                response.getOutputStream().print(e.getMessage());
            } catch (Exception e2) {
                System.err.println(e2.getMessage()); 
            }
            System.err.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/{nomArret}")
    public Arret create(@PathVariable(value="nomArret") String id, @RequestBody Arret u, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM arret WHERE nomArret='"+ id +"'");
            if (rs.next()) {
                response.setStatus(403);
                response.getOutputStream().print("Erreur HTTP 403");
                return null;
            } else {
                if(id.equals(u.getNomArret())) {
                    stmt.executeUpdate("Insert into arret values('"+ u.getNomArret() +"','"+u.getCod() +"','"+ u.getStreetMap() +"','"+ u.getGoogleMap() +"','"+ u.getNomVille() +"')"); 
                    return u;
                } else {
                    response.setStatus(412);
                    response.getOutputStream().print("Erreur HTTP 412");
                    return null;
                }
            }
        } catch(Exception e){
            response.setStatus(500);
        try{
            response.getOutputStream().print(e.getMessage());
        } catch (Exception e2) {
            System.err.println(e2.getMessage()); 
        }
        System.err.println(e.getMessage());
        return null;
        } 
    }

    @PutMapping("/{nomArret}")
        public Arret update(@PathVariable(value="nomArret") String id, @RequestBody Arret u, HttpServletResponse response){
            try (Connection connection = dataSource.getConnection()){
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM arret WHERE nomArret='"+ id +"'");
                if(rs.next()){
                    if(rs.getString("nomArret").equals(u.getNomArret())){
                        stmt.executeUpdate("UPDATE Arret set nomArret = '"+ u.getNomArret() + "', code = '" + u.getCod() + "', steetMap = '" + u.getStreetMap() + "', googleMap = '" + u.getGoogleMap() + "', nomVille = '" + u.getNomVille() + "' WHERE nomArret = '" + u.nomArret + "'"); 
                    }else{
                        response.setStatus(412);
                        response.getOutputStream().print("Erreur HTTP 412");
                    }
                }else{
                    response.setStatus(404);
                    response.getOutputStream().print("Erreur HTTP 404");
                }
                return u;
            } catch(Exception e){
                response.setStatus(500);
                try{
                    response.getOutputStream().print(e.getMessage());
                } catch (Exception e2) {
                    System.err.println(e2.getMessage()); 
                }
                System.err.println(e.getMessage());
            }
        return null;
    }

    @DeleteMapping("/{nomArret}")
    void delete(@PathVariable(value="nomArret") String id, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM arret WHERE nomArret='"+ id +"'");
            if(rs.next()){
                stmt.executeUpdate("DELETE FROM arret WHERE nomArret = '" + id + "'"); 
            }else{
                response.setStatus(404);
                response.getOutputStream().print("Erreur HTTP 404");
            }
        } catch(Exception e){
            response.setStatus(500);
            try{
                response.getOutputStream().print(e.getMessage());
            } catch (Exception e2) {
                System.err.println(e2.getMessage()); 
            }
            System.err.println(e.getMessage());
        }
    }
 }