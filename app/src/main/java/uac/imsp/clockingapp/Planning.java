package uac.imsp.clockingapp;

public class Planning {
    private int Id;
    private String StartTime;
    private String EndTime;

    public Planning(String startTime,String endTime){
        StartTime=startTime;
        EndTime=endTime;
    }
    public Planning(int id){
        Id=id;
    }

    public  int getId(){
        return Id;
   }
    public String getStartTime(){
        return StartTime;
    }
    public String getEndTime(){
        return EndTime;
    }

    public void setStartTime(String startTime){
        StartTime = startTime;
    }
    public void setEndTime(String endTime){
        EndTime = endTime;
    }



}