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
    private int limit = 10;
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
                if(oldUnAllocatedMemory.getLength()-length>limit) {
                    UnAllocatedMemory newUnAllocatedMeMory = new UnAllocatedMemory();
                    AllocatedMemory allocatedMemory = new AllocatedMemory();
                    unAllocatedMemories.remove(i);
                    newUnAllocatedMeMory.setLength((oldUnAllocatedMemory.getLength() - length));
                    newUnAllocatedMeMory.setStartAddress(oldUnAllocatedMemory.getStartAddress() + length);
                    unAllocatedMemories.add(i,newUnAllocatedMeMory);
                    allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                    allocatedMemory.setLength(length);
                    allocatedMemory.setProcessName(processName);
                    allocatedMemories.add(allocatedMemory);
                    sortAllocatedMemoriesByStartAddress();
                    return true;
                }else {
                    AllocatedMemory allocatedMemory = new AllocatedMemory();
                    unAllocatedMemories.remove(i);
                    allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                    allocatedMemory.setLength(oldUnAllocatedMemory.getLength());
                    allocatedMemory.setProcessName(processName);
                    allocatedMemories.add(allocatedMemory);
                    sortAllocatedMemoriesByStartAddress();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean bestFitAlgorithm(int length,String processName){
        for(int i = 0;i<unAllocatedMemories.size();i++) {
            if (unAllocatedMemories.get(i).getLength() >= length) {
                UnAllocatedMemory oldUnAllocatedMemory = unAllocatedMemories.get(i);
                AllocatedMemory allocatedMemory = new AllocatedMemory();
                if(unAllocatedMemories.get(i).getLength()>=length) {
                    UnAllocatedMemory newUnAllocatedMeMory = new UnAllocatedMemory();
                    newUnAllocatedMeMory.setLength((oldUnAllocatedMemory.getLength() - length));
                    newUnAllocatedMeMory.setStartAddress(oldUnAllocatedMemory.getStartAddress() + length);
                    for(int j = 0 ;j < i;j++){
                        if(unAllocatedMemories.get(j).getLength()>newUnAllocatedMeMory.getLength()){
                            unAllocatedMemories.add(j,newUnAllocatedMeMory);
                            break;
                        }
                    }
                    unAllocatedMemories.add(newUnAllocatedMeMory);
                    allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                    allocatedMemory.setLength(length);
                    allocatedMemory.setProcessName(processName);
                    allocatedMemories.add(allocatedMemory);
                    sortAllocatedMemoriesByStartAddress();
                    return true;
                }else {
                    unAllocatedMemories.remove(i);
                    allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                    allocatedMemory.setLength(length);
                    allocatedMemory.setProcessName(processName);
                    allocatedMemories.add(allocatedMemory);
                    sortAllocatedMemoriesByStartAddress();
                }
            }
        }
        return false;
    }

    public boolean worstFitAlgorithm(int length,String processName){
        for(int i = 0;i<unAllocatedMemories.size();i++) {
            UnAllocatedMemory oldUnAllocatedMemory = unAllocatedMemories.get(i);
            UnAllocatedMemory newUnAllocatedMeMory = new UnAllocatedMemory();
            AllocatedMemory allocatedMemory = new AllocatedMemory();
            if (unAllocatedMemories.get(i).getLength() >= length) {
                unAllocatedMemories.remove(i);
                newUnAllocatedMeMory.setLength((oldUnAllocatedMemory.getLength() - length));
                newUnAllocatedMeMory.setStartAddress(oldUnAllocatedMemory.getStartAddress() + length);
                for(int j=unAllocatedMemories.size()-1;j>i;j--) {
                    if(unAllocatedMemories.get(j).getLength()>newUnAllocatedMeMory.getLength())
                    {
                        unAllocatedMemories.add(j+1,newUnAllocatedMeMory);
                    }
                }
                allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                allocatedMemory.setLength(length);
                allocatedMemory.setProcessName(processName);
                allocatedMemories.add(allocatedMemory);
                sortAllocatedMemoriesByStartAddress();
                return true;
            }else {
                unAllocatedMemories.remove(i);
                allocatedMemory.setStartAddress(oldUnAllocatedMemory.getStartAddress());
                allocatedMemory.setLength(length);
                allocatedMemory.setProcessName(processName);
                allocatedMemories.add(allocatedMemory);
                sortAllocatedMemoriesByStartAddress();
             }
        }
        return false;
    }

    private int getPrecursor(AllocatedMemory allocatedMemory){
        for (int i = 0;i<unAllocatedMemories.size();i++){
            if(unAllocatedMemories.get(i).getStartAddress()+unAllocatedMemories.get(i).getLength()==allocatedMemory.getStartAddress()){
                return i;
            }
        }
        return -1;
    }

    private int getNext(AllocatedMemory allocatedMemory){
        for (int i = 0;i<unAllocatedMemories.size();i++){
            if(allocatedMemory.getStartAddress()+allocatedMemory.getLength()==unAllocatedMemories.get(i).getStartAddress()){
                return i;
            }
        }
        return -1;
    }

    public boolean bestRecycle(String procressName){
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
        if(allocatedMemory.getLength()==0){
            return false;
        }
        int front=getPrecursor(allocatedMemory);
        int behind=getNext(allocatedMemory);
        if(front!=-1&&behind!=-1){
            UnAllocatedMemory unAllocatedMemory1;
            UnAllocatedMemory unAllocatedMemory2;
            unAllocatedMemory1=unAllocatedMemories.get(front);
            unAllocatedMemory2=unAllocatedMemories.get(behind);
            unAllocatedMemory.setLength(allocatedMemory.getLength()+unAllocatedMemory1.getLength()+unAllocatedMemory2.getLength());
            unAllocatedMemory.setStartAddress(unAllocatedMemory1.getStartAddress());
            unAllocatedMemories.remove(front);
            unAllocatedMemories.remove(behind);
        }
        else if(front!=-1&&behind==-1){
            UnAllocatedMemory unAllocatedMemory1;
            unAllocatedMemory1=unAllocatedMemories.get(front);
            unAllocatedMemory.setLength(allocatedMemory.getLength()+unAllocatedMemory1.getLength());
            unAllocatedMemory.setStartAddress(unAllocatedMemory1.getStartAddress());
            unAllocatedMemories.remove(front);
        }
        else if(front==-1&&behind!=-1){
            UnAllocatedMemory unAllocatedMemory2;
            unAllocatedMemory2=unAllocatedMemories.get(behind);
            unAllocatedMemory.setStartAddress(allocatedMemory.getStartAddress());
            unAllocatedMemory.setLength(allocatedMemory.getLength()+unAllocatedMemory2.getLength());
            unAllocatedMemories.remove(behind);
        }
        else {
            unAllocatedMemory.setLength(allocatedMemory.getLength());
            unAllocatedMemory.setStartAddress(allocatedMemory.getStartAddress());
        }
        for(int i=0;i<unAllocatedMemories.size();i++){
            if(unAllocatedMemory.getLength()<=unAllocatedMemories.get(i).getLength()){
                unAllocatedMemories.add(i,unAllocatedMemory);
                break;
            }
        }
        return true;
    }

    public boolean worstRecycle(String procressName, int arithmeticName){
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
        if(allocatedMemory.getLength()==0){
            return false;
        }
        int front=getPrecursor(allocatedMemory);
        int behind=getNext(allocatedMemory);
        if(front!=-1&&behind!=-1){
            UnAllocatedMemory unAllocatedMemory1;
            UnAllocatedMemory unAllocatedMemory2;
            unAllocatedMemory1=unAllocatedMemories.get(front);
            unAllocatedMemory2=unAllocatedMemories.get(behind);
            unAllocatedMemory.setLength(allocatedMemory.getLength()+unAllocatedMemory1.getLength()+unAllocatedMemory2.getLength());
            unAllocatedMemory.setStartAddress(unAllocatedMemory1.getStartAddress());
            unAllocatedMemories.remove(front);
            unAllocatedMemories.remove(behind);
        }
        else if(front!=-1&&behind==-1){
            UnAllocatedMemory unAllocatedMemory1;
            unAllocatedMemory1=unAllocatedMemories.get(front);
            unAllocatedMemory.setLength(allocatedMemory.getLength()+unAllocatedMemory1.getLength());
            unAllocatedMemory.setStartAddress(unAllocatedMemory1.getStartAddress());
            unAllocatedMemories.remove(front);
        }
        else if(front==-1&&behind!=-1){
            UnAllocatedMemory unAllocatedMemory2;
            unAllocatedMemory2=unAllocatedMemories.get(behind);
            unAllocatedMemory.setStartAddress(allocatedMemory.getStartAddress());
            unAllocatedMemory.setLength(allocatedMemory.getLength()+unAllocatedMemory2.getLength());
            unAllocatedMemories.remove(behind);
        }
        else {
            unAllocatedMemory.setLength(allocatedMemory.getLength());
            unAllocatedMemory.setStartAddress(allocatedMemory.getStartAddress());
        }
        for(int i=0;i<unAllocatedMemories.size();i++) {
            if (unAllocatedMemory.getLength() >= unAllocatedMemories.get(i).getLength()) {
                unAllocatedMemories.add(i, unAllocatedMemory);
                break;
            }
        }
        return true;
    }


    public boolean firstRecycle(String procressName){
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
        if(allocatedMemory.getLength()==0){
            return false;
        }
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
            for (int i=0;i<unAllocatedMemories.size();i++){
                if(unAllocatedMemories.get(i).getStartAddress()>unAllocatedMemory.getStartAddress()){
                    unAllocatedMemories.add(i,unAllocatedMemory);
                    break;
                }
            }
        }
        return true;
    }
    public boolean recycle(String procressName,int alogorithm){
        switch (alogorithm){
            case FIRST_FIT : return firstRecycle(procressName);
            case BEST_FIT : return bestRecycle(procressName);
            case WORST_FIT : return firstRecycle(procressName);
        }
        return false;
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
