package imu.memoryManage.model;

/**
 * Created by Administrator on 2016/11/24.
 */
public class AllocatedMemory implements Comparable{

    private int length;
    private int startAddress;
    private String processName;

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

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Override
        public int compareTo(Object o) {
            AllocatedMemory allocatedMemory = (AllocatedMemory)o;
            if(allocatedMemory.startAddress == this.startAddress)
                return 0;
            else if(allocatedMemory.startAddress>this.startAddress)
                return -1;
            else
                return 1;
    }

    public BusyTableModel getBusyTalbeModel() {
        BusyTableModel busyTableModel = new BusyTableModel();
        busyTableModel.setInitialAddress(String.valueOf(startAddress));
        busyTableModel.setProgressName(processName);
        busyTableModel.setLength(String.valueOf(length));
        return busyTableModel;
    }

}
