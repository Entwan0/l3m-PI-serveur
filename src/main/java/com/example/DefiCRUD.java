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
                defi.login_Auteur = rs.getString("login_fk");
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
                System.err.println("Erreur HTTP 404"); 
            }else{
                rs.first();
                defi.id = rs.getString("id");
                defi.titre = rs.getString("titre");
                defi.dateDeCreation = rs.getDate("dateDeCreation");
                defi.description = rs.getString("description");
                defi.login_Auteur = rs.getString("login_fk");
            }
            return defi;
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
    public Defi create(@PathVariable(value="defiID") String id, @RequestBody Defi defi, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis WHERE id='"+ id +"'");
            if(rs.next()){
                System.err.println("Erreur HTTP 403");
                
            }else{
                Defi s = new Defi();
                s.id = rs.getString("id");
                s.titre = rs.getString("titre");
                s.dateDeCreation = rs.getDate("dateDeCreation");
                s.description = rs.getString("description");
                s.login_Auteur = rs.getString("login_fk");
                if(id == defi.getId()){
                    int create = stmt.executeUpdate("Insert into defis values('"+ defi.getId() +"','"+defi.getTitre() +"','"+defi.getDateDeCreation()+"','"+defi.getDescription()+"','"+defi.getLoginAuteur()+"')"); 
                }else
                    System.err.println("Erreur HTTP 412");
            }
            return defi;
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
                    System.err.println("Erreur HTTP 403");
                    
                }else{
                    Defi defi = new Defi();
                    defi.id = rs.getString("id");
                    defi.titre = rs.getString("titre");
                    defi.dateDeCreation = rs.getDate("dateDeCreation");
                    defi.description = rs.getString("description");
                    defi.login_Auteur = rs.getString("login_fk");
                    if(id == u.getId()){
                        int create = stmt.executeUpdate("Insert into defis values('"+ defi.getId() +"','"+defi.getTitre() +"','"+defi.getDateDeCreation()+"','"+defi.getDescription()+"','"+defi.getLoginAuteur()+"')"); 

                    }else
                        System.err.println("Erreur HTTP 412");
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
            return null;
            } 
    }
}