package imu.memoryManage.app;

import imu.memoryManage.model.ProcessMove;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by guyu on 2016/11/23.
 */
public class Animation{

    final static double ANIMATION_STEP = 0.5;
    static int size = 0;
    static int maxSize = 0;
    static Runnable onEnd;
    static Timer timer;

    public static void moveColorSpace(List<ProcessMove> processMoveList, ObservableList<Node> nodes, Pane pane, int memorySize, Runnable onEnd) {
        timer = new Timer();
        Animation.size = Animation.maxSize = processMoveList.size();
        Animation.onEnd = onEnd;
        for(int i = 0; i < size; i++) {
            ProcessMove processMove = processMoveList.get(i);
            Node node = nodes.get(processMove.getPosition());
            timer.schedule(new AnimationTimerTask(node, processMove.getLength() * (int)pane.getPrefHeight() / memorySize, i), 0, 25);
        }
    }

    public static void onEnd() {
        if(onEnd != null) {
            Platform.runLater(onEnd);
        }
        timer.purge();
        timer.cancel();
    }

    static class AnimationTimerTask extends TimerTask {
        Node node;
        double discount;
        double max;
        int i = 0;
        List<ProcessMove> processMoveList;
        Runnable onEnd;
        public AnimationTimerTask(Node node, int max, int i) {
            this.node = node;
            this.max = max;
            this.i = i;
            if(max < 0)
                this.discount = -ANIMATION_STEP;
            else
                this.discount = ANIMATION_STEP;
        }

        @Override
        public void run() {
            if(maxSize - size != i)
                return;
            if(max == 0) {
                this.cancel();
                size--;
                if(size == 0)
                    onEnd();
            }
            else {
                //System.out.println("第" + i + "块" + ": y " + node.getLayoutY());
                node.setLayoutY(node.getLayoutY() + discount);
            }
            max -= discount;
        }
    }

}
