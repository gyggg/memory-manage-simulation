package imu.memoryManage.algorithm;

import imu.memoryManage.model.AllocatedMemory;
import imu.memoryManage.model.ProcessMove;
import imu.memoryManage.model.UnAllocatedMemory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by guyu on 2016/11/25.
 */
public class MemoryManageAlogorithm {
    int [] Memory;
    int maxLength;
    private List<UnAllocatedMemory> unAllocatedMemories = new ArrayList<>();
    private List<AllocatedMemory> allocatedMemories = new ArrayList<>();
    private List<ProcessMove> processMoves = new ArrayList<>();
    final static public int FIRST_FIT = 0;
    final static public int BEST_FIT = 1;
    final static public int WORST_FIT = 2;


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

    public List<ProcessMove> getProcessMoves() {
        return processMoves;
    }

    public void setProcessMoves(List<ProcessMove> processMoves) {
        this.processMoves = processMoves;
    }

    public void initMemory(int length){
        Memory = new int [length];
        UnAllocatedMemory unAllocatedMemory = new UnAllocatedMemory();
        unAllocatedMemory.setLength(length);
        unAllocatedMemory.setStartAddress(0);
        unAllocatedMemories.add(unAllocatedMemory);
        maxLength = length-1;
    }

    public boolean selectAlgorithm(int length, String processName, int algName) {
        switch (algName) {
            case FIRST_FIT:return firstFitAlgorithm(length, processName);
            case BEST_FIT:return bestFitAlgorithm(length, processName);
            case WORST_FIT:return worstFitAlgorithm(length, processName);
            default:return false;
        }
    }

    public boolean firstFitAlgorithm(int length,String processName){
        for(int i = 0;i<unAllocatedMemories.size();i++){
            if(unAllocatedMemories.get(i).getLength()>=length){
                UnAllocatedMemory oldUnAllocatedMemory = unAllocatedMemories.get(i);
                UnAllocatedMemory newUnAllocatedMeMory = new UnAllocatedMemory();
                AllocatedMemory allocatedMemory = new AllocatedMemory();
                unAllocatedMemories.remove(i);
                newUnAllocatedMeMory.setLength((oldUnAllocatedMemory.getLength()-length));
                newUnAllocatedMeMory.setStartAddress(oldUnAllocatedMemory.getStartAddress()+length);
                unAllocatedMemories.add(newUnAllocatedMeMory);
                allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                allocatedMemory.setLength(length);
                allocatedMemory.setProcessName(processName);
                allocatedMemories.add(allocatedMemory);
                sortUnAllocatedMemoriesByStartAddress();
                sortAllocatedMemoriesByStartAddress();
                return true;
            }
        }
        return false;
    }

    public boolean bestFitAlgorithm(int length,String processName){
        for(int i = 0;i<unAllocatedMemories.size();i++) {
            if (unAllocatedMemories.get(i).getLength() >= length) {
                UnAllocatedMemory oldUnAllocatedMemory = unAllocatedMemories.get(i);
                UnAllocatedMemory newUnAllocatedMeMory = new UnAllocatedMemory();
                AllocatedMemory allocatedMemory = new AllocatedMemory();
                unAllocatedMemories.remove(i);
                newUnAllocatedMeMory.setLength((oldUnAllocatedMemory.getLength()-length));
                newUnAllocatedMeMory.setStartAddress(oldUnAllocatedMemory.getStartAddress()+length);
                unAllocatedMemories.add(newUnAllocatedMeMory);
                allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                allocatedMemory.setLength(length);
                allocatedMemory.setProcessName(processName);
                allocatedMemories.add(allocatedMemory);
                sortAllocatedMemoriesByStartAddress();
                Collections.sort(unAllocatedMemories, new Comparator<UnAllocatedMemory>() {
                    @Override
                    public int compare(UnAllocatedMemory o1, UnAllocatedMemory o2) {
                        return Memorycompare(o1,o2);
                    }
                });
                return  true;
            }
        }
        return false;
    }

    public boolean worstFitAlgorithm(int length,String processName){
        for(int i = 0;i<unAllocatedMemories.size();i++) {
            if (unAllocatedMemories.get(i).getLength() >= length) {
                UnAllocatedMemory oldUnAllocatedMemory = unAllocatedMemories.get(i);
                UnAllocatedMemory newUnAllocatedMeMory = new UnAllocatedMemory();
                AllocatedMemory allocatedMemory = new AllocatedMemory();
                unAllocatedMemories.remove(i);
                newUnAllocatedMeMory.setLength((oldUnAllocatedMemory.getLength()-length));
                newUnAllocatedMeMory.setStartAddress(oldUnAllocatedMemory.getStartAddress()+length);
                unAllocatedMemories.add(newUnAllocatedMeMory);
                allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                allocatedMemory.setLength(length);
                allocatedMemory.setProcessName(processName);
                allocatedMemories.add(allocatedMemory);
                sortAllocatedMemoriesByStartAddress();
                Collections.sort(unAllocatedMemories, new Comparator<UnAllocatedMemory>() {
                    @Override
                    public int compare(UnAllocatedMemory o1, UnAllocatedMemory o2) {
                        return Memorycompare(o1,o2);
                    }
                });
                Collections.reverse(unAllocatedMemories);
                return  true;
            }
        }
        return false;
    }

    public void recycle(String procressName, int arithmeticName){
        int flag=0;
        AllocatedMemory allocatedMemory = new AllocatedMemory();
        UnAllocatedMemory unAllocatedMemory=new UnAllocatedMemory();
        for (int i=0;i<allocatedMemories.size();i++) {
            if (allocatedMemories.get(i).getProcessName().equals(procressName)) {
                allocatedMemory = allocatedMemories.get(i);
                allocatedMemories.remove(i);
                break;
            }
        }
        sortUnAllocatedMemoriesByStartAddress();
        for (int j = 0; j < unAllocatedMemories.size(); j++) {
            UnAllocatedMemory unAllocatedMemory1 = unAllocatedMemories.get(j);
            if(unAllocatedMemories.size()-1!=j){
                UnAllocatedMemory unAllocatedMemory2 = unAllocatedMemories.get(j+1);
                if(unAllocatedMemory1.getStartAddress() +unAllocatedMemory1.getLength() == allocatedMemory.getStartAddress()&&allocatedMemory.getStartAddress() + allocatedMemory.getLength() == unAllocatedMemory2.getStartAddress()){
                    flag=2;
                    unAllocatedMemories.remove(j+1);
                    unAllocatedMemories.get(j).setLength(unAllocatedMemory1.getLength() + allocatedMemory.getLength()+unAllocatedMemory2.getLength());
                }
            }
            if (flag==0&&unAllocatedMemory1.getStartAddress() +unAllocatedMemory1.getLength() == allocatedMemory.getStartAddress()) {
                flag=1;
                unAllocatedMemories.get(j).setLength(unAllocatedMemory1.getLength() + allocatedMemory.getLength());
                break;
            }
            else if (flag==0&&allocatedMemory.getStartAddress() + allocatedMemory.getLength() == unAllocatedMemory1.getStartAddress()) {
                flag=1;
                unAllocatedMemories.get(j).setLength(unAllocatedMemory1.getLength() + allocatedMemory.getLength());
                unAllocatedMemories.get(j).setStartAddress(allocatedMemory.getStartAddress());
                break;
            }
        }

        if(flag==0){
            unAllocatedMemory.setStartAddress(allocatedMemory.getStartAddress());
            unAllocatedMemory.setLength(allocatedMemory.getLength());
            unAllocatedMemories.add(unAllocatedMemory);
            sortUnAllocatedMemoriesByStartAddress();
        }

        if(arithmeticName==BEST_FIT){
            Collections.sort(unAllocatedMemories, new Comparator<UnAllocatedMemory>() {
                @Override
                public int compare(UnAllocatedMemory o1, UnAllocatedMemory o2) {
                   return Memorycompare(o1,o2);
                }
            });
        }
        if(arithmeticName==WORST_FIT){
            Collections.sort(unAllocatedMemories, new Comparator<UnAllocatedMemory>() {
                @Override
                public int compare(UnAllocatedMemory o1, UnAllocatedMemory o2) {
                    return Memorycompare(o1,o2);
                }
            });
            Collections.reverse(unAllocatedMemories);
        }
    }

    private int Memorycompare(UnAllocatedMemory o1, UnAllocatedMemory o2)
    {
        if(o1.getLength() == o2.getLength()) {
            if(o1.getStartAddress()==o2.getStartAddress())
                return 0;
            else if(o1.getStartAddress()>o2.getStartAddress())
                return 1;
            else
                return -1;
        }
        else if(o1.getLength()>o2.getLength())
            return 1;
        else
            return -1;
    }

    private void sortUnAllocatedMemoriesByStartAddress(){
        Collections.sort(unAllocatedMemories, new Comparator<UnAllocatedMemory>() {
            @Override
            public int compare(UnAllocatedMemory o1, UnAllocatedMemory o2) {
                if(o1.getStartAddress()==o2.getStartAddress())
                    return 0;
                else if(o1.getStartAddress()>o2.getStartAddress())
                    return 1;
                else
                    return -1;
            }
        });
    }

    private void sortAllocatedMemoriesByStartAddress(){
        Collections.sort(allocatedMemories);
    }

    private int getLastIndex(UnAllocatedMemory unAllocatedMemory){
        for(int i=allocatedMemories.size()-1;i>0;i--){
            if(allocatedMemories.get(i).getLength()+allocatedMemories.get(i).getStartAddress()==
                    unAllocatedMemory.getStartAddress()){
                return i;
            }
        }
        return 0;
    }

    public boolean compact(int length){
        processMoves.clear();
        int number = 0;
        for(int i = 0;i<unAllocatedMemories.size();i++) {
            number = number + unAllocatedMemories.get(i).getLength();
        }
        if(number<length)
            return false;
        else {
            sortUnAllocatedMemoriesByStartAddress();
            while (unAllocatedMemories.get(unAllocatedMemories.size()-1).getLength()<length) {
                UnAllocatedMemory unAllocatedMemory = unAllocatedMemories.get(unAllocatedMemories.size()-1);
                int index = getLastIndex(unAllocatedMemory);
                AllocatedMemory allocatedMemory = allocatedMemories.get(index);
                if(unAllocatedMemories.get(unAllocatedMemories.size()-2).getStartAddress()+
                        unAllocatedMemories.get(unAllocatedMemories.size()-2).getLength()== allocatedMemory.getStartAddress()) {

                    ProcessMove processMove = new ProcessMove();
                    processMove.setPosition(index);
                    processMove.setLength(unAllocatedMemories.get(unAllocatedMemories.size() - 1).getLength());
                    processMoves.add(processMove);

                    unAllocatedMemories.get(unAllocatedMemories.size() - 2).setLength(unAllocatedMemories.get(unAllocatedMemories.size() - 2).getLength() + unAllocatedMemory.getLength());
                    unAllocatedMemories.remove(unAllocatedMemories.size() - 1);
                    allocatedMemory.setStartAddress(unAllocatedMemories.get(unAllocatedMemories.size() - 1).getStartAddress() + unAllocatedMemories.get(unAllocatedMemories.size() - 1).getLength());
                    allocatedMemories.remove(index);
                    allocatedMemories.add(allocatedMemory);
                    sortAllocatedMemoriesByStartAddress();
                }else {
                    ProcessMove processMove = new ProcessMove();
                    processMove.setPosition(index);
                    processMove.setLength(unAllocatedMemories.get(unAllocatedMemories.size() - 1).getLength());
                    processMoves.add(processMove);

                    unAllocatedMemories.get(unAllocatedMemories.size() - 1).setStartAddress(allocatedMemory.getStartAddress());
                    allocatedMemory.setStartAddress(allocatedMemory.getStartAddress()+unAllocatedMemories.get(unAllocatedMemories.size() - 1).getLength());
                    allocatedMemories.remove(index);
                    allocatedMemories.add(allocatedMemory);
                    sortAllocatedMemoriesByStartAddress();

                }
            }
            return true;
        }
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
