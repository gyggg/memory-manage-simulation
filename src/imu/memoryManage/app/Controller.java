package imu.memoryManage.app;

import imu.memoryManage.algorithm.MemoryManageAlogorithm;
import imu.memoryManage.model.AllocatedMemory;
import imu.memoryManage.model.BusyTableModel;
import imu.memoryManage.model.FreeTableModel;
import imu.memoryManage.model.ProcessMove;
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
import java.util.*;

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
    ObservableList<Node> colorSpaces = FXCollections.observableArrayList();

    MemoryManageAlogorithm memoryManageAlogorithm = new MemoryManageAlogorithm();

    int algName = 0;
    int memorySize = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstAdapt.setToggleGroup(algorithmGroup);
        bestAdapt.setToggleGroup(algorithmGroup);
        baddestAdapt.setToggleGroup(algorithmGroup);
        overButton.setDisable(true);
        onOverMemory();
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
        busyTableColumns.get(0).setCellValueFactory(new PropertyValueFactory("id"));
        busyTableColumns.get(1).setCellValueFactory(new PropertyValueFactory("initialAddress"));
        busyTableColumns.get(2).setCellValueFactory(new PropertyValueFactory("length"));
        busyTableColumns.get(3).setCellValueFactory(new PropertyValueFactory("progressName"));
        busyTable.setItems(busyTableModels);
        busyTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        firstAdapt.setSelected(true);
        colorSpaces = FXCollections.observableArrayList();
    }

    public void onStartProgress() {
        String proName = progressNameText.getText();
        int needMemory;
        try {
            needMemory = Integer.parseInt(needMemoryText.getText());
        } catch(Exception e) {
            showErrorInfo("内存必须是整数");
            return;
        }
        boolean result = memoryManageAlogorithm.selectAlgorithm(needMemory, proName, algName);
        if(result == true) {
            refreshTalbe();
        }
        else {
            result = memoryManageAlogorithm.compact(needMemory);
            if(result == true) {
                List<ProcessMove> processMoveList = memoryManageAlogorithm.getProcessMoves();
                Animation.moveColorSpace(processMoveList,colorSpaces, memoryPane, memorySize, new Runnable() {
                    @Override
                    public void run() {
                        memoryManageAlogorithm.selectAlgorithm(needMemory, proName, algName);
                        refreshTalbe();
                    }
                });
            }
            else {
                showErrorInfo("剩余内存不足，无法分配");
            }
        }
    }

    public void onReleaseProgress() {
        String proName = progressNameText.getText();
        int needMemory;
        boolean result;
        try {
            needMemory = Integer.parseInt(needMemoryText.getText());
        } catch(Exception e) {
            showErrorInfo("内存必须是整数");
            return;
        }
        result = memoryManageAlogorithm.recycle(proName, algName);
        if(result){
            refreshTalbe();
        }else {
            showErrorInfo("进程名输入错误，无法回收");
        }
    }

    public void refreshTalbe() {
        freeTableModels.clear();
        for(int i = 0; i < memoryManageAlogorithm.getUnAllocatedMemories().size(); i++){
            FreeTableModel freeTableModel = memoryManageAlogorithm.getUnAllocatedMemories().get(i).getFreeTalbeModel();
            freeTableModel.setId(String.valueOf(i + 1));
            freeTableModels.add(freeTableModel);
        }
        busyTableModels.clear();
        for(int i = 0; i < memoryManageAlogorithm.getAllocatedMemories().size(); i++) {
            BusyTableModel busyTableModel = memoryManageAlogorithm.getAllocatedMemories().get(i).getBusyTalbeModel();
            busyTableModel.setId(String.valueOf(i + 1));
            busyTableModels.add(busyTableModel);
        }
        memoryPane.getChildren().clear();
        colorSpaces = FXCollections.observableArrayList();
        for(int i = 0; i < memoryManageAlogorithm.getAllocatedMemories().size(); i++) {
            AllocatedMemory allocatedMemory = memoryManageAlogorithm.getAllocatedMemories().get(i);
            Node node = addColorSpace(allocatedMemory.getStartAddress(), allocatedMemory.getLength(), allocatedMemory.getProcessName() + ":", allocatedMemory.getLength() + "K");
            colorSpaces.add(node);
        }
    }

    public void onStartMemory() {
        try {
            memoryManageAlogorithm.initMemory(Integer.parseInt(initialMemoryText.getText()));
            memorySize = memoryManageAlogorithm.getMaxLength();
            refreshTalbe();
        } catch (Exception e) {
            showErrorInfo("初始内存必须是整数");
            return;
        }
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
    }

    public void showErrorInfo(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(Main.stage);
        alert.setContentText(text);
        alert.setTitle("error!");
        alert.show();
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
        memoryManageAlogorithm = new MemoryManageAlogorithm();
        refreshTalbe();
    }

    public void onAlgorithmSelect() {
        if(firstAdapt.isSelected())
            algName = MemoryManageAlogorithm.FIRST_FIT;
        else if(bestAdapt.isSelected())
            algName = MemoryManageAlogorithm.BEST_FIT;
        else
            algName = MemoryManageAlogorithm.WORST_FIT;
    }

    public Node addColorSpace(int start, int length, String ...texts) {
        Label startAddress = new Label();
        Label endAddress = new Label();
        startAddress.setText(start + " K");
        startAddress.setLayoutX(-100);
        startAddress.setPrefWidth(95);
        startAddress.setLayoutY(memoryPane.getPrefHeight() * start / memorySize -10);
        startAddress.setAlignment(Pos.TOP_RIGHT);
        endAddress.setText(start + length + " K");
        endAddress.setLayoutX(-100);
        endAddress.setPrefWidth(95);
        endAddress.setLayoutY((start + length) * memoryPane.getPrefHeight() / memorySize -10);
        endAddress.setAlignment(Pos.TOP_RIGHT);
        memoryPane.getChildren().add(startAddress);
        memoryPane.getChildren().add(endAddress);
        return addColorSpace((double)start / memorySize, (double)length / memorySize, texts);
    }

    public Node addColorSpace(double yPercent, double lengthPercent, String ...texts) {
        Pane colorSpace = new Pane();
        colorSpace.setPrefSize(memoryPane.getPrefWidth(), memoryPane.getPrefHeight() * lengthPercent - 1);
        colorSpace.setLayoutX(0);
        colorSpace.setLayoutY(memoryPane.getPrefHeight() * yPercent);
        colorSpace.setOpacity(0.8);
        Label label = new Label();
        String lableText = "";
        for(String text : texts) {
            lableText = lableText + text + "\r\n";
        }
        label.setText(lableText);
        label.setPrefSize(memoryPane.getPrefWidth(), memoryPane.getPrefHeight() * lengthPercent);
        label.setLayoutX(0);
        label.setLayoutY(0);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font(15));
        label.setTextFill(Paint.valueOf("#000000"));
        colorSpace.getChildren().add(label);
        Color color = Color.CADETBLUE;
//        System.out.println(getColorString(color));
        colorSpace.setStyle("-fx-background-color:" + getColorString(color));
        memoryPane.getChildren().add(colorSpace);
        return colorSpace;
    }

    public void onTest() {
    }

    public static String getColorString(Color color) {
        String colorString = color.toString();
        colorString = colorString.replaceFirst("0x", "#");
        colorString = colorString.substring(0, 7);
        return colorString;
    }
}
