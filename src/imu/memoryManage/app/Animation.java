package imu.memoryManage.app;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by guyu on 2016/11/23.
 */
public class Animation{

    final static int ANIMATION_STEP = 1;
    static int memorySize = 0;
    static int paneHeight = 0;
    static ArrayList<Node> nodes;
    static boolean canDo = true;

    public static void moveColorSpace(int moveLength, int pos) {
        Node node = nodes.get(pos);
        Timer timer = new Timer();
        timer.schedule(new AnimationTimerTask(node, moveLength * paneHeight / memorySize, timer), 0, 5);
        canDo = false;
    }

    static class AnimationTimerTask extends TimerTask {
        Node node;
        int discount;
        int max;
        Timer timer;
        public AnimationTimerTask(Node node, int max, Timer timer) {
            this.node = node;
            this.max = max;
            if(max < 0)
                this.discount = -ANIMATION_STEP;
            else
                this.discount = ANIMATION_STEP;
            this.timer = timer;
        }

        @Override
        public void run() {
            if(max == 0) {
                timer.cancel();
                canDo = true;
            }
            else {
                node.setLayoutY(node.getLayoutY() + discount);
            }
            max -= discount;
        }
    }

    public static int getMemorySize() {
        return memorySize;
    }

    public static void setMemorySize(int memorySize) {
        Animation.memorySize = memorySize;
    }

    public static ArrayList<Node> getNodes() {
        return nodes;
    }

    public static void setNodes(ArrayList<Node> nodes) {
        Animation.nodes = nodes;
    }

    public static int getPaneHeight() {
        return (int) paneHeight;
    }

    public static void setPaneHeight(int paneHeight) {
        Animation.paneHeight = paneHeight;
    }

    public static boolean isCanDo() {
        return canDo;
    }

    public static void setCanDo(boolean canDo) {
        Animation.canDo = canDo;
    }
}
