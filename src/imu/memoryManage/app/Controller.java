package imu.memoryManage.app;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javax.swing.text.TableView;

public class Controller {
    @FXML
    TableView freeTable;
    @FXML
    TableView busyTable;
    @FXML
    TextField progressNameText;
    @FXML
    TextField needMemoryText;
    @FXML
    TextField initialMemoryText;
    @FXML
    Button startProgressButton;
    @FXML
    Button releaseProgressButton;
    @FXML
    Button startButton;
    @FXML
    Button overButton;
    @FXML
    Pane memoryPane;
    @FXML
    RadioButton firstAdapt;
    @FXML
    RadioButton bestAdapt;
    @FXML
    RadioButton baddestAdapt;

    ToggleGroup algorithmGroup = new ToggleGroup();
}
