package imu.memoryManage.unite;

import imu.memoryManage.algorithm.MemoryManageAlogorithm;

/**
 * Created by cyy on 2016/11/25.
 */
public class test {
    public static void main(String[] args) {
        MemoryManageAlogorithm memoryManageAlogorithm = new MemoryManageAlogorithm();
        memoryManageAlogorithm.initMemory(200);
        memoryManageAlogorithm.bestFitAlgorithm(30,"aa");
        memoryManageAlogorithm.bestFitAlgorithm(30,"bb");
        memoryManageAlogorithm.bestFitAlgorithm(30,"cc");
        memoryManageAlogorithm.bestFitAlgorithm(30,"dd");
        memoryManageAlogorithm.bestFitAlgorithm(30,"ee");
        memoryManageAlogorithm.Recycle("aa","最好");
        memoryManageAlogorithm.Recycle("cc","最好");
        memoryManageAlogorithm.Recycle("ee","最好");

        memoryManageAlogorithm.compact(120);
    }
}
