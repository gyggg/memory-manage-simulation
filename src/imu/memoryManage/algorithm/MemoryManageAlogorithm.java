package imu.memoryManage.algorithm;

import imu.memoryManage.model.AllocatedMemory;
import imu.memoryManage.model.UnAllocatedMemory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by guyu on 2016/11/25.
 */
public class MemoryManageAlogorithm {
    int [] Memory;
    private List<UnAllocatedMemory> unAllocatedMemories = new ArrayList<>();
    private List<AllocatedMemory> allocatedMemories = new ArrayList<>();

    public int[] getMemory() {
        return Memory;
    }

    public void setMemory(int[] memory) {
        Memory = memory;
    }

    public List<UnAllocatedMemory> getUnAllocatedMemories() {
        return unAllocatedMemories;
    }

    public void setUnAllocatedMemories(List<UnAllocatedMemory> unAllocatedMemories) {
        this.unAllocatedMemories = unAllocatedMemories;
    }

    public List<AllocatedMemory> getAllocatedMemories() {
        return allocatedMemories;
    }

    public void setAllocatedMemories(List<AllocatedMemory> allocatedMemories) {
        this.allocatedMemories = allocatedMemories;
    }

    public void initMemory(int length){
        Memory = new int [length];
        UnAllocatedMemory unAllocatedMemory = new UnAllocatedMemory();
        unAllocatedMemory.setLength(length);
        unAllocatedMemory.setStartAddress(0);
        unAllocatedMemories.add(unAllocatedMemory);
    }


    public void firstFitAlgorithm(int length,String processName){
        for(int i = 0;i<unAllocatedMemories.size();i++){
            if(unAllocatedMemories.get(i).getLength()>length){
                UnAllocatedMemory oldUnAllocatedMemory = unAllocatedMemories.get(i);
                UnAllocatedMemory newUnAllocatedMeMory = new UnAllocatedMemory();
                AllocatedMemory allocatedMemory = new AllocatedMemory();
                unAllocatedMemories.remove(i);
                newUnAllocatedMeMory.setLength((oldUnAllocatedMemory.getLength()-length));
                newUnAllocatedMeMory.setStartAddress(oldUnAllocatedMemory.getStartAddress()+length);
                unAllocatedMemories.set(i,newUnAllocatedMeMory);
                allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                allocatedMemory.setLength(length);
                allocatedMemory.setProcessName(processName);
                allocatedMemories.add(allocatedMemory);
                Collections.sort(allocatedMemories);
            }
        }
    }

    public void bestFitAlgorithm(int length,String processName){
        for(int i = 0;i<unAllocatedMemories.size();i++) {
            if (unAllocatedMemories.get(i).getLength() > length) {
                UnAllocatedMemory oldUnAllocatedMemory = unAllocatedMemories.get(i);
                UnAllocatedMemory newUnAllocatedMeMory = new UnAllocatedMemory();
                AllocatedMemory allocatedMemory = new AllocatedMemory();
                unAllocatedMemories.remove(i);
                newUnAllocatedMeMory.setLength((oldUnAllocatedMemory.getLength()-length));
                newUnAllocatedMeMory.setStartAddress(oldUnAllocatedMemory.getStartAddress()+length);
                unAllocatedMemories.set(i,newUnAllocatedMeMory);
                allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                allocatedMemory.setLength(length);
                allocatedMemory.setProcessName(processName);
                allocatedMemories.add(allocatedMemory);
                Collections.sort(allocatedMemories);
                Collections.sort(unAllocatedMemories);
            }
        }
    }



    public void compact(int lenget){

    }

    public void Recycle(String procressName,String arithmeticName){
        AllocatedMemory allocatedMemory = new AllocatedMemory();
        for (int i=0;i<allocatedMemories.size();i++) {
            if (allocatedMemories.get(i).getProcessName() == procressName) {
                allocatedMemory = allocatedMemories.get(i);
                allocatedMemories.remove(i);
            }
            break;
        }
        for (int j = 0; j < unAllocatedMemories.size(); j++) {
            if ((unAllocatedMemories.get(j).getStartAddress() + unAllocatedMemories.get(j).getLength() == allocatedMemory.getStartAddress()) || (allocatedMemory.getStartAddress() + allocatedMemory.getLength() == unAllocatedMemories.get(j).getStartAddress())) {
                if (unAllocatedMemories.get(j).getStartAddress() + unAllocatedMemories.get(j).getLength() == allocatedMemory.getStartAddress()) {
                    unAllocatedMemories.get(j).setLength(unAllocatedMemories.get(j).getLength() + allocatedMemory.getLength());
                        }
                if (allocatedMemory.getStartAddress() + allocatedMemory.getLength() == unAllocatedMemories.get(j).getStartAddress()) {
                    unAllocatedMemories.get(j).setLength(unAllocatedMemories.get(j).getLength() + allocatedMemory.getLength());
                    unAllocatedMemories.get(j).setStartAddress(allocatedMemory.getStartAddress());
                        }
                    }
            break;
        }
        if(arithmeticName=="最好"){
            Collections.sort(unAllocatedMemories);
        }
        if(arithmeticName=="最坏"){
            Collections.reverse(unAllocatedMemories);
        }



}



}
