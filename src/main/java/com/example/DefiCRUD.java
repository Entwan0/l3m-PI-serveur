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
@RequestMapping("api/defis")
public class DefiCRUD{

    @Autowired
    private DataSource dataSource;

    @GetMapping("/")
    public ArrayList<Defi> allDefis(HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis");
            
            ArrayList<Defi> L = new ArrayList<Defi>();
            while (rs.next()) { 
                Defi defi = new Defi(); 
                defi.id = rs.getString("id");
                defi.titre = rs.getString("titre");
                defi.dateDeCreation = rs.getDate("dateDeCreation");
                defi.description = rs.getString("description");
                defi.loginAuteur = rs.getString("login_fk");
                defi.latitude = rs.getString("latitude");
                defi.longitude = rs.getString("longitude");
                L.add(defi);
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

    @GetMapping("/{defiID}")
    public Defi read(@PathVariable(value="defiID") String id, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis WHERE id='"+ id +"'");

            Defi defi = new Defi();
            if(!rs.next()){
                response.setStatus(404);
                response.getOutputStream().print("erreur 404");
                return null;
            }else{
                defi.id = rs.getString("id");
                defi.titre = rs.getString("titre");
                defi.dateDeCreation = rs.getDate("dateDeCreation");
                defi.description = rs.getString("description");
                defi.loginAuteur = rs.getString("login_fk");
                return defi;
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

    @PostMapping("/{defiID}")
    public Defi create(@PathVariable(value="defiID") String id, @RequestBody Defi u, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis WHERE id ='"+ id +"'");
            if (rs.next()) {
                response.setStatus(403);
                response.getOutputStream().print("Erreur HTTP 403");
                return null;
            } else {
                if(id.equals(u.getId())) {
                    stmt.executeUpdate("Insert into defis values('"+ u.getId() +"','"+ u.getTitre() +"','"+ u.getDateDeCreation() + "','" + u.getDescription() + "','" + u.getLoginAuteur() + "')"); 
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

    @PutMapping("/{defiID}")
    public Defi update(@PathVariable(value="defiID") String id, @RequestBody Defi u, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis WHERE id='"+ id +"'");
            if(rs.next()){
                if(rs.getString("id").equals(u.getId())){
                    stmt.executeUpdate("UPDATE defis set id ='"+ u.getId() +"',titre ='"+ u.getTitre() +"',datedecreation= '"+ u.getDateDeCreation()+"', description ='"+ u.getDescription()+"',login_fk ='"+ u.getLoginAuteur()+"' where id ='"+ u.getId() + "'"); 
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

    @DeleteMapping("/{userId}")
    void delete(@PathVariable(value="userId") String id, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis WHERE id='"+ id +"'");
            if(rs.next()){
                stmt.executeUpdate("DELETE FROM defis WHERE id = '" + id + "'"); 
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