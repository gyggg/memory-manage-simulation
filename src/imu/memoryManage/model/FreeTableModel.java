package imu.memoryManage.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by guyu on 2016/11/23.
 */
public class FreeTableModel {
    SimpleStringProperty id = new SimpleStringProperty();
    SimpleStringProperty initialAddress = new SimpleStringProperty();
    SimpleStringProperty length = new SimpleStringProperty();
    SimpleStringProperty status = new SimpleStringProperty();

    public FreeTableModel() {

    }
    public FreeTableModel(String id, String initialAddress, String length, String status) {
        this.id = new SimpleStringProperty(id);
        this.initialAddress = new SimpleStringProperty(initialAddress);
        this.length = new SimpleStringProperty(length);
        this.status = new SimpleStringProperty(status);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getInitialAddress() {
        return initialAddress.get();
    }

    public SimpleStringProperty initialAddressProperty() {
        return initialAddress;
    }

    public void setInitialAddress(String initialAddress) {
        this.initialAddress.set(initialAddress);
    }

    public String getLength() {
        return length.get();
    }

    public SimpleStringProperty lengthProperty() {
        return length;
    }

    public void setLength(String length) {
        this.length.set(length);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
