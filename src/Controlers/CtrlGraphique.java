package Controlers;

import Entities.DatasGraph;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlGraphique
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlGraphique() {
        cnx = ConnexionBDD.getCnx();
    }

    public HashMap<Integer,Double> getDataGraphique1() throws SQLException {
        HashMap<Integer,Double> data = new HashMap<>();
                ps = cnx.prepareStatement("SELECT ageEmp , SUM( employe.salaireEmp) / COUNT(employe.numEmp)\n" +
                "FROM employe\n" +
                "GROUP BY ageEmp");
        rs = ps.executeQuery();
        while (rs.next()){
            data.put(rs.getInt(1),rs.getDouble(2));
        }
        return data;
    }

    public HashMap<String,Double> getDataGraphique2() throws SQLException {
        HashMap<String,Double> data = new HashMap<>();
        ps = cnx.prepareStatement("SELECT sexe,COUNT(numEmp) / total * 100\n" +
                "FROM employe , (\n" +
                "    SELECT COUNT(numEmp) as total\n" +
                "    FROM employe\n" +
                ") as temp\n" +
                "WHERE sexe LIKE \"Homme\"\n" +
                "UNION\n" +
                "SELECT sexe,COUNT(numEmp) / total * 100\n" +
                "FROM employe , (\n" +
                "    SELECT COUNT(numEmp) as total\n" +
                "    FROM employe\n" +
                ") as temp\n" +
                "WHERE sexe LIKE \"Femme\"");
        rs = ps.executeQuery();
        while (rs.next()){
            data.put(rs.getString(1),rs.getDouble(2));
        }
        return data;
    }

    public HashMap<Integer ,ArrayList<String>> getDataGraphique3() throws SQLException {
    HashMap<Integer ,ArrayList<String>> data = new HashMap<>();
    int i = 0;
    int tranche = 10;
    while(i < 4){
        ps = cnx.prepareStatement("SELECT COUNT(sexe) , sexe\n" +
                "FROM employe\n" +
                "WHERE ageEmp BETWEEN ? AND ?\n" +
                "GROUP BY sexe\n" +
                "ORDER BY sexe DESC");
        ps.setInt(1,tranche);
        ps.setInt(2,tranche+9);
        rs = ps.executeQuery();
        while (rs.next()){
            if(rs.getString(2).compareTo("Homme") == 0){
                ArrayList<String> population = new ArrayList<>();
                population.add("-" + rs.getString(1));
                population.add(rs.getString(2));
                population.add(String.valueOf(tranche) + "-" + String.valueOf(tranche+9));
                data.put(i,population);
            }
            else {

                data.get(i).add(rs.getString(1));
                data.get(i).add(rs.getString(2));
                data.get(i).add(String.valueOf(tranche) + "-" + String.valueOf(tranche+9));
            }
        }
        i++;
        tranche= tranche + 10;
    }
    ps = cnx.prepareStatement("SELECT COUNT(sexe) , sexe , \"50+\"\n" +
            "FROM employe\n" +
            "WHERE ageEmp >= 50\n" +
            "GROUP BY sexe\n" +
            "ORDER BY sexe DESC");
    rs = ps.executeQuery();
    while (rs.next()){
        if(rs.getString(2).compareTo("Homme") == 0){

            ArrayList<String> population = new ArrayList<>();
            population.add("-" + rs.getString(1));
            population.add(rs.getString(2));
            population.add(rs.getString(3));
            data.put(i,population);
        }
        else {
            data.get(i).add(rs.getString(1));
            data.get(i).add(rs.getString(2));
            data.get(i).add(rs.getString(3));
            }
    }
    return data;
    }
    public HashMap<Integer, String[]> getDataGraphique4() throws SQLException {
        HashMap<Integer, String[]> data = new HashMap<>();
        ps = cnx.prepareStatement("SELECT montant , nomSemestre, nomMagasin\n" +
                "FROM vente\n" +
                "GROUP BY  nomSemestre, nomMagasin");
        rs = ps.executeQuery();
        int i = 0;
        while (rs.next()){
            data.put(i,new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});
            i++;
        }
        return data;
    }

}
