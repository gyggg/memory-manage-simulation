package imu.memoryManage.unite;

import imu.memoryManage.model.UnAllocatedMemory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cyy on 2016/11/25.
 */
public class test {
    public static void main(String[] args) {
        List<UnAllocatedMemory> unAllocatedMemories = new ArrayList<UnAllocatedMemory>();
        UnAllocatedMemory allocatedMemory1 = new UnAllocatedMemory();
        UnAllocatedMemory allocatedMemory2 = new UnAllocatedMemory();
        UnAllocatedMemory allocatedMemory3 = new UnAllocatedMemory();
        allocatedMemory1.setLength(5);
        allocatedMemory2.setLength(1);
        allocatedMemory3.setLength(7);
        allocatedMemory1.setStartAddress(7);
        allocatedMemory2.setStartAddress(15);
        allocatedMemory3.setStartAddress(25);
        unAllocatedMemories.add(allocatedMemory1);
        unAllocatedMemories.add(allocatedMemory2);
        unAllocatedMemories.add(allocatedMemory3);
        Collections.sort(unAllocatedMemories);
        for(int i=0;i<3;i++){
            System.out.print(unAllocatedMemories.get(i).getLength());
        }
        Collections.reverse(unAllocatedMemories);
        for(int i=0;i<3;i++){
            System.out.print(unAllocatedMemories.get(i).getLength());
        }
    }
}
