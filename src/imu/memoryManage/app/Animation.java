package imu.memoryManage.app;

import javafx.scene.Node;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by guyu on 2016/11/23.
 */
public class Animation{

    final static int ANIMATION_STEP = 1;

    public static  void removeColorSpace(int removeLength, Node node) {
        Timer timer = new Timer();
        timer.schedule(new AnimationTimerTask(node, removeLength, timer), 0, 5);
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
            if(max == 0)
                timer.cancel();
            else {
                node.setLayoutY(node.getLayoutY() + discount);
            }
            max -= discount;
        }
    }

}
