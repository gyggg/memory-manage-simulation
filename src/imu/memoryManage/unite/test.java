package imu.memoryManage.unite;

import imu.memoryManage.algorithm.MemoryManageAlogorithm;

/**
 * Created by cyy on 2016/11/25.
 */
public class test {
    public static void main(String[] args) {
        MemoryManageAlogorithm memoryManageAlogorithm = new MemoryManageAlogorithm();
        memoryManageAlogorithm.initMemory(300);
        memoryManageAlogorithm.bestFitAlgorithm(30,"aa");
        memoryManageAlogorithm.bestFitAlgorithm(30,"bb");
        memoryManageAlogorithm.bestFitAlgorithm(30,"cc");
        memoryManageAlogorithm.bestFitAlgorithm(30,"dd");
        memoryManageAlogorithm.bestFitAlgorithm(30,"ee");
        memoryManageAlogorithm.bestFitAlgorithm(50,"ff");
        memoryManageAlogorithm.bestFitAlgorithm(30,"gg");
        memoryManageAlogorithm.bestFitAlgorithm(50,"zz");
        memoryManageAlogorithm.recycle("ff",100);
        memoryManageAlogorithm.recycle("cc",100);

        memoryManageAlogorithm.compact(90);

    }
}
