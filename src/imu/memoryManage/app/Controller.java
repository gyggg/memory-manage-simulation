package imu.memoryManage.app;

import imu.memoryManage.model.BusyTableModel;
import imu.memoryManage.model.FreeTableModel;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable{
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
    ObservableList<BusyTableModel> busyTableModels = FXCollections.observableArrayList();
    ObservableList<FreeTableModel> freeTableModels = FXCollections.observableArrayList();
    ObservableList<Node> colorSpaces;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstAdapt.setToggleGroup(algorithmGroup);
        bestAdapt.setToggleGroup(algorithmGroup);
        baddestAdapt.setToggleGroup(algorithmGroup);
        overButton.setDisable(true);
        //onOverMemory();
        //初始化空闲分区表
        ObservableList<TableColumn<FreeTableModel, String>> observableList = freeTable.getColumns();
        observableList.get(0).setCellValueFactory(new PropertyValueFactory("id"));
        observableList.get(1).setCellValueFactory(new PropertyValueFactory("initialAddress"));
        observableList.get(2).setCellValueFactory(new PropertyValueFactory("length"));
        observableList.get(3).setCellValueFactory(new PropertyValueFactory("status"));
        freeTable.setItems(freeTableModels);
        freeTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //初始化已分配分区表
        ObservableList<TableColumn<BusyTableModel, String>> busyTableColumns = busyTable.getColumns();
        observableList.get(0).setCellValueFactory(new PropertyValueFactory("id"));
        observableList.get(1).setCellValueFactory(new PropertyValueFactory("initialAddress"));
        observableList.get(2).setCellValueFactory(new PropertyValueFactory("length"));
        observableList.get(3).setCellValueFactory(new PropertyValueFactory("progressName"));
        busyTable.setItems(busyTableModels);
        busyTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        firstAdapt.setSelected(true);
    }

    public void onStartProgress() {

    }

    public void onReleaseProgress() {

    }

    public void onStartMemory() {
        initialMemoryText.setDisable(true);
        firstAdapt.setDisable(true);
        bestAdapt.setDisable(true);
        baddestAdapt.setDisable(true);
        startButton.setDisable(true);
        overButton.setDisable(false);
        progressNameText.setDisable(false);
        needMemoryText.setDisable(false);
        startProgressButton.setDisable(false);
        releaseProgressButton.setDisable(false);
        colorSpaces = memoryPane.getChildren();
    }

    public void onOverMemory() {
        initialMemoryText.setDisable(!true);
        firstAdapt.setDisable(!true);
        bestAdapt.setDisable(!true);
        baddestAdapt.setDisable(!true);
        startButton.setDisable(!true);
        overButton.setDisable(!false);
        progressNameText.setDisable(!false);
        needMemoryText.setDisable(!false);
        startProgressButton.setDisable(!false);
        releaseProgressButton.setDisable(!false);
        memoryPane.getChildren().clear();
    }

    public void onAlgorithmSelect() {

    }

    public Node addColorSpace(int y, double percent, String ...texts) {
        if(colorSpaces == null)
            colorSpaces = memoryPane.getChildren();
        Pane colorSpace = new Pane();
        colorSpace.setPrefSize(memoryPane.getPrefWidth(), memoryPane.getPrefHeight() * percent);
        colorSpace.setLayoutX(0);
        colorSpace.setLayoutY(y);
        colorSpace.setOpacity(0.8);
        Label label = new Label();
        String lableText = "";
        for(String text : texts) {
            lableText = lableText + text + "\r\n";
        }
        label.setText(lableText);
        label.setPrefSize(memoryPane.getPrefWidth(), memoryPane.getPrefHeight() * percent);
        label.setLayoutX(0);
        label.setLayoutY(0);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font(15));
        label.setTextFill(Paint.valueOf("#000000"));
        colorSpace.getChildren().add(label);
        Color color = new Color(percent * 3, 0, 1 - percent * 3, 1);
        System.out.println(getColorString(color));
        colorSpace.setStyle("-fx-background-color:" + getColorString(color));
        colorSpaces.add(colorSpace);
        return colorSpace;
    }




    public void onTest() {
        Animation.removeColorSpace(200,
                addColorSpace(100, 0.2, "123\r\n456"));
    }

    public static String getColorString(Color color) {
        String colorString = color.toString();
        colorString = colorString.replaceFirst("0x", "#");
        colorString = colorString.substring(0, 7);
        return colorString;
    }
}
