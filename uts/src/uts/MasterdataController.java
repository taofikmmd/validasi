package uts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;

public class MasterdataController {
    @FXML
    private TableView<Buku> tblBuku;
    @FXML
    private TableColumn<Buku, String> collKode;
    @FXML
    private TableColumn<Buku, String> collJdl;
    @FXML
    private TableColumn<Buku, String> collPengarang;
    @FXML
    private TableColumn<Buku, String> collPenerbit;
    @FXML
    private TableColumn<Buku, String> collThn;
    @FXML
    private TableColumn<Buku, String> collEdisi;
@FXML
    private Button btnAdd;
    
     @FXML
    private Button btnUp;
     
     @FXML
    private Button btnDel;
    @FXML
    private TextField kdBuk;
    @FXML
    private TextField jdlBuk;
    @FXML
    private TextField pengarangBuk;
    @FXML
    private TextField penerbitBuk;
    @FXML
    private TextField thnBuk;
    @FXML
    private TextField edisiBuk;

    private ObservableList<Buku> bukuList = FXCollections.observableArrayList();
    
     private void loadDataBuku(){
        
        ObservableList<Buku> bukuList = bukuDAO.getBuku();
        
        tblBuku.setItems(bukuList);
    }
  
       private void clearFields(){
        kdBuk.clear();
        selectedBuku = null;
    }
   
        private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
  
      private void selectBuku(Buku buku){
        if (buku != null) {
            selectedBuku = buku;
            kdBuk.setText(buku.getKode_buku());
        }
    }
    private Buku selectedBuku;
   

    @FXML
    private void addBuku() {
        String kode_buku = kdBuk.getText();
        String judul = jdlBuk.getText();
        String pengarang = pengarangBuk.getText();
        String penerbit = penerbitBuk.getText();
        String tahun_terbit = thnBuk.getText();
        String edisi = edisiBuk.getText();

    if (kode_buku.isEmpty() || judul.isEmpty() || pengarang.isEmpty() ||
        penerbit.isEmpty() || tahun_terbit.isEmpty() || edisi.isEmpty()) {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Gagal");
        alert.setHeaderText(null);
        alert.setContentText("Semua field harus diisi!");
        alert.showAndWait();
        return;
    }

    try {
        Integer.parseInt(tahun_terbit);
        Integer.parseInt(edisi);

        Buku buku = new Buku(kode_buku, judul, pengarang, penerbit, tahun_terbit, edisi);
        bukuDAO.addBuku(buku);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText("Data buku berhasil disimpan.");
        alert.showAndWait();

        loadDataBuku();
        clearFields();

    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gagal!!");
        alert.setHeaderText(null);
        alert.setContentText("Tahun terbit dan edisi harus angka.");
        alert.showAndWait();
    } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText("Terjadi kesalahan saat menyimpan data");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
    }
    @FXML
    private void updateBuku() {
        
        String kode_buku = kdBuk.getText();
        String judul = jdlBuk.getText();
        String pengarang = pengarangBuk.getText();
        String penerbit = penerbitBuk.getText();
        String tahun_terbit = thnBuk.getText();
        String edisi = edisiBuk.getText();
        
        if (judul.isEmpty()) { 
            showAlert("input error", "Field Tidak boleh kosong!");
            return;
        }
        
        selectedBuku.setJudul(judul);
        selectedBuku.setPengarang(pengarang);
        selectedBuku.setPenerbit(penerbit);
        selectedBuku.setTahun_terbit(tahun_terbit);
        selectedBuku.setEdisi(edisi);
        
        bukuDAO.updateBuku(selectedBuku);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);  
        alert.setContentText("Data buku berhasil diperbarui.");
        alert.showAndWait();
        loadDataBuku();
        clearFields();
        
    }
    
@FXML
private void deleteBuku() {
    if (selectedBuku == null) {
        showAlert("Input Error", "Pilih data buku yang akan dihapus!");
        return;
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Konfirmasi Hapus");
    alert.setHeaderText("Hapus Data?");
    alert.setContentText("Data akan dihapus jika Anda klik OK.");

    ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

    if (result == ButtonType.OK) {
        bukuDAO.deleteBuku(selectedBuku.getKode_buku());

        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Informasi");
        alert2.setHeaderText("Hapus Berhasil");
        alert2.setContentText("Data berhasil dihapus.");
        alert2.showAndWait();

        loadDataBuku();
        clearFields();
    }
}


 @FXML
    public void initialize() {
        collKode.setCellValueFactory(new PropertyValueFactory<>("kode_buku"));
        collJdl.setCellValueFactory(new PropertyValueFactory<>("judul"));
        collPengarang.setCellValueFactory(new PropertyValueFactory<>("pengarang"));
        collPenerbit.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        collThn.setCellValueFactory(new PropertyValueFactory<>("tahun_terbit"));
        collEdisi.setCellValueFactory(new PropertyValueFactory<>("edisi"));
 loadDataBuku();
    
    tblBuku.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectBuku(newValue)
    );
    }

}