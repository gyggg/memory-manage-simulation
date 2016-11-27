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
                return true;
            }
        }
        return false;
    }

    public boolean bestFitAlgorithm(int length,String processName){
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
                return  true;
            }
        }
        return false;
    }



    public void compact(int lenget){

    }

    public void Recycle(String procressName,String arithmeticName){
        int flag=0;
        AllocatedMemory allocatedMemory = new AllocatedMemory();
        UnAllocatedMemory unAllocatedMemory=new UnAllocatedMemory();
        for (int i=0;i<allocatedMemories.size();i++) {
            if (allocatedMemories.get(i).getProcessName() == procressName) {
                allocatedMemory = allocatedMemories.get(i);
                allocatedMemories.remove(i);
            }
            break;

        }
        for (int j = 0; j < unAllocatedMemories.size(); j++) {
            if ((unAllocatedMemories.get(j).getStartAddress() + unAllocatedMemories.get(j).getLength() == allocatedMemory.getStartAddress()) || (allocatedMemory.getStartAddress() + allocatedMemory.getLength() == unAllocatedMemories.get(j).getStartAddress())) {
                flag=1;
                if (unAllocatedMemories.get(j).getStartAddress() + unAllocatedMemories.get(j).getLength() == allocatedMemory.getStartAddress()) {
                    unAllocatedMemories.get(j).setLength(unAllocatedMemories.get(j).getLength() + allocatedMemory.getLength());
                        }
                if (allocatedMemory.getStartAddress() + allocatedMemory.getLength() == unAllocatedMemories.get(j).getStartAddress()) {
                    unAllocatedMemories.get(j).setLength(unAllocatedMemories.get(j).getLength() + allocatedMemory.getLength());
                    unAllocatedMemories.get(j).setStartAddress(allocatedMemory.getStartAddress());
                        }
                break;
                    }
        }
        if(flag==0){
            unAllocatedMemory.setStartAddress(allocatedMemory.getStartAddress());
            unAllocatedMemory.setLength(allocatedMemory.getLength());
            unAllocatedMemories.add(unAllocatedMemory);
        }
        if(arithmeticName=="最好"){
            Collections.sort(unAllocatedMemories);
        }
        if(arithmeticName=="最坏"){
            Collections.reverse(unAllocatedMemories);
        }
        return false;
    }

    public boolean compact(int length){
        int number = 0;
        for(int i = 0;i<unAllocatedMemories.size();i++) {
            number = number + unAllocatedMemories.get(i).getLength();
        }
        if(number<length)
            return false;
        else {
            for(int i = unAllocatedMemories.size();i > 0;i--) {
                UnAllocatedMemory unAllocatedMemory = unAllocatedMemories.get(i);







            }
            return true;
        }
    }





}
