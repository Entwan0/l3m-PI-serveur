package com.example;
import java.sql.Date;
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
@RequestMapping("api/visites")
public class VisitesCRUD{
    @Autowired
    private DataSource dataSource;

    @GetMapping("/")
    public ArrayList<Visites> allVisites(HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM visites");
            
            ArrayList<Visites> L = new ArrayList<Visites>();
            while (rs.next()) { 
                Visites visites = new Visites(); 
                visites.idVisite = rs.getString("idvisite");
                visites.idDefis = rs.getString("iddefi");
                visites.nomVisiteur = rs.getString("nomvisiteur");
                visites.dateVisite = rs.getDate("datevisite");
                visites.mode = rs.getString("mode");
                visites.score = rs.getString("score");
                visites.temps = rs.getString("temps");
                visites.status = rs.getString("status");
                L.add(visites);
            }
            return L;
        }catch (Exception e){
            response.setStatus(500);
            try{
                response.getOutputStream().print(e.getMessage());
            } catch(Exception e2) {
                System.err.println(e2.getMessage());
            }
            System.err.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/{visiteID}")
    public Visites read(@PathVariable(value="visitesID") String id, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM visites WHERE idvisite='"+ id +"'");

            Visites visites = new Visites();
            if(!rs.next()){
                response.setStatus(404);
                response.getOutputStream().print("erreur 404");
                return null;
            }else{
                visites.idVisite = rs.getString("idvisite");
                visites.idDefis = rs.getString("iddefi");
                visites.nomVisiteur = rs.getString("nomvisiteur");
                visites.dateVisite = rs.getDate("datevisite");
                visites.mode = rs.getString("mode");
                visites.score = rs.getString("score");
                visites.temps = rs.getString("temps");
                visites.status = rs.getString("status");
                return visites;
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

    @PostMapping("/{visitesID}")
    public Visites create(@PathVariable(value="visitesID") String id, @RequestBody Visites u, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM visites WHERE idvisite ='"+ id +"'");
            if (rs.next()) {
                response.setStatus(403);
                response.getOutputStream().print("Erreur HTTP 403");
                return null;
            } else {
                if(id.equals(u.getIdVisite())) {
                    stmt.executeUpdate("Insert into visites values('"+ u.getIdVisite() +"','"+ u.getIdDefis() +"','"+ u.getNomVisiteur() + "','" + u.getDateVisite() + "','" + u.getMode() + "','" + u.getScore() + "','" + u.getTemps()+ "','" + u.getStatus()+"')"); 
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

    @PutMapping("/{visitesID}")
    public Visites update(@PathVariable(value="visitesID") String id, @RequestBody Visites u, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM visites WHERE idvisite='"+ id +"'");
            if(rs.next()){
                if(rs.getString("idVisite").equals(u.getIdVisite())){
                    stmt.executeUpdate("UPDATE visites set idvisite = '"+ u.getIdVisite() +"', iddefi = '"+ u.getIdDefis() +"', nomvisiteur = '"+ u.getNomVisiteur() + "', datevisite = '" + u.getDateVisite() + "', mode = '" + u.getMode() + "', score = '" + u.getScore() + "', temps = '" + u.getTemps()+ "', status = '" + u.getStatus()+"' WHERE idvisite = '" + u.getIdVisite() + "'"); 
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

    @DeleteMapping("/{visitesId}")
    void delete(@PathVariable(value="visitesId") String id, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM visites WHERE idvisite='"+ id +"'");
            if(rs.next()){
                stmt.executeUpdate("DELETE FROM visites WHERE idvisite = '" + id + "'"); 
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