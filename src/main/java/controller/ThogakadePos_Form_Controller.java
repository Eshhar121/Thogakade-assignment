package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import model.Customer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ThogakadePos_Form_Controller implements Initializable {

    public static ArrayList<Customer> customerArrayList= new ArrayList<>();

    @FXML
    private JFXTextField txtaddress;

    @FXML
    private JFXTextField txtname;

    @FXML
    private JFXComboBox<String> cmbtitle;

    @FXML
    private JFXTextField txtcontact;

    @FXML
    private DatePicker datedob;

    @FXML
    private JFXTextField txtid;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (customerArrayList.isEmpty() || checkid()){
            addcustomer();
        }else{
            txtid.setText(null);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ID");
            alert.setContentText("The id you entered exists.Do you want to change it?");
            alert.show();
        }
    }

    private boolean checkid() {
        for (int i = 0; i < customerArrayList.size(); i++){
            if (customerArrayList.get(i).getId().equals(txtid.getText()))return false;
        }
        return true;
    }

    private void addcustomer() {
        customerArrayList.add(new Customer(
                txtid.getText(),
                cmbtitle.getValue(),
                txtname.getText(),
                txtaddress.getText(),
                txtcontact.getText(),
                datedob.getValue()
        ));
        clearvalues();
        customerArrayList.forEach(System.out::println);
    }

    private void clearvalues() {
        txtid.setText(null);
        cmbtitle.setValue(null);
        txtname.setText(null);
        txtaddress.setText(null);
        txtcontact.setText(null);
        datedob.setValue(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (!checkid()) customerArrayList.remove(searchindex());
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        if (!checkid()) searchcustomer();
    }

    private void searchcustomer() {
        customerArrayList.forEach(customer -> {
            if (customer.getId().equals(txtid.getText())){
                cmbtitle.setValue(customer.getTitle());
                txtname.setText(customer.getName());
                txtaddress.setText(customer.getAddress());
                txtcontact.setText(customer.getContact());
                datedob.setValue(customer.getDob());
            }
        });
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        customerArrayList.add(searchindex(), new Customer(
                txtid.getText(),
                cmbtitle.getValue(),
                txtname.getText(),
                txtaddress.getText(),
                txtcontact.getText(),
                datedob.getValue()
        ));
    }

    private int searchindex() {
        int i;
        for (i = 0; i < customerArrayList.size(); i++){
            if (customerArrayList.get(i).getId().equals(txtid.getText())) break;
        }
        return i;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("MR.");
        titles.add("Miss");

        cmbtitle.setItems(titles);
    }
}
