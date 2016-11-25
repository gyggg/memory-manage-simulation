package imu.memoryManage.model;

/**
 * Created by Administrator on 2016/11/24.
 */
public class UnAllocatedMemory implements Comparable{
   private int length;
   private int startAddress;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    @Override
    public int compareTo(Object o) {
        UnAllocatedMemory unAllocatedMemory  = (UnAllocatedMemory) o;
        if(unAllocatedMemory.length == this.length)
            return 0;
        else if(unAllocatedMemory.length>this.length)
            return -1;
        else
            return 1;
    }
}
