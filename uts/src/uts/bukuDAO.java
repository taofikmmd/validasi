/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uts;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Taofik Dimas
 */
public class bukuDAO {
    public static ObservableList<Buku> getBuku() {
         ObservableList<Buku> bukuList = FXCollections.observableArrayList();
        String query = "SELECT * FROM buku";
        
        try (
            Connection koneksi = DBConnection.getConnection();
            Statement stmt = koneksi.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            
            while (rs.next()) {
                String kode_buku = rs.getString("kode_buku");
                String judul = rs.getString("judul");
                 String pengarang = rs.getString("pengarang");
                  String penerbit = rs.getString("penerbit");
                   String tahun_terbit = rs.getString("tahun_terbit");
                   String edisi = rs.getString("edisi");
                
                bukuList.add(new Buku(kode_buku, judul, pengarang, penerbit, tahun_terbit, edisi));
            }
                rs.close();
                stmt.close();
                koneksi.close();
            }catch (Exception e){
                    e.printStackTrace();
                    }
        return bukuList;   
    }
    public static void addBuku(Buku buku) {
        String query = "INSERT INTO buku (kode_buku, judul, pengarang, penerbit, tahun_terbit, edisi) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection koneksi = DBConnection.getConnection();
             PreparedStatement smt = koneksi.prepareStatement(query)) {
            smt.setString(1, buku.getKode_buku());
            smt.setString(2, buku.getJudul());
            smt.setString(3, buku.getPengarang());
            smt.setString(4, buku.getPenerbit());
            smt.setString(5, buku.getTahun_terbit());
            smt.setString(6, buku.getEdisi());

            smt.executeUpdate();
                
           smt.close();
                koneksi.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public static void updateBuku(Buku buku){
            String query = "UPDATE buku SET judul = ?, pengarang = ?, penerbit = ?, tahun_terbit = ?, edisi = ? WHERE kode_buku = ?";
            
             try (
                 Connection koneksi = DBConnection.getConnection();
                 PreparedStatement smt = koneksi.prepareStatement(query)){
                 
                
                smt.setString(1, buku.getJudul());
                smt.setString(2, buku.getPengarang());
                smt.setString(3, buku.getPenerbit());
                 smt.setString(4, buku.getTahun_terbit());
                smt.setString(5, buku.getEdisi());
                smt.setString(6, buku.getKode_buku());
                
                smt.executeUpdate();
                
                smt.close();
                koneksi.close();
                
             }catch (SQLException e) {
                e.printStackTrace();     
                }
        }
        public static void deleteBuku(String nama) {
            String query = "DELETE FROM buku WHERE kode_buku = ?";
            
              try (
                 Connection koneksi = DBConnection.getConnection();
                 PreparedStatement smt = koneksi.prepareStatement(query)){
                 
                smt.setString(1, nama);
                
                smt.executeUpdate();
                
                smt.close();
                koneksi.close();
             }catch (SQLException e) {
              e.printStackTrace();     
        }
        }
}
