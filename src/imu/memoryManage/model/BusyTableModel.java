package imu.memoryManage.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by guyu on 2016/11/23.
 */
public class BusyTableModel {
    SimpleStringProperty id = new SimpleStringProperty();
    SimpleStringProperty initialAddress = new SimpleStringProperty();
    SimpleStringProperty length = new SimpleStringProperty();
    SimpleStringProperty progressName = new SimpleStringProperty();

    public BusyTableModel() {

    }
    public BusyTableModel(String id, String initialAddress, String length, String progressName) {
        this.id = new SimpleStringProperty(id);
        this.initialAddress = new SimpleStringProperty(initialAddress);
        this.length = new SimpleStringProperty(length);
        this.progressName = new SimpleStringProperty(progressName);
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

    public String getProgressName() {
        return progressName.get();
    }

    public SimpleStringProperty progressNameProperty() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName.set(progressName);
    }
}
