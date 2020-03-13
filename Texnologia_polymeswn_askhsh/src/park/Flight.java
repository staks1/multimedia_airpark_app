package park;
import java.time.LocalTime;
import java.util.ArrayList;

public class Flight {
    public static int flcount;
    public String flid;   //unique flight id (maybe based on object hashcode)
    public String city;     //city of departure
    public Integer fltype;    //flight type
    public Integer planetype;  //airplane type
    public String flstate;     //state of flight (parked/holding/landing)
    public long deptime;      //programmed departure time
    public ArrayList<String> services; //bonus services asked
    public LocalTime switchStateTime;  // time of landing +  /the time the flight needs to switch states(landing-->parked) based on airplane types
    public int coefficient; //the factor bonus/penalty if the flight departs earlier or later than the programmed departure time
    public int flpriority; //use this to remember which flight is first in priority queue later
    public String park_flight_id; //use this to remember which park corresponds to the flight
    public double flight_park_price=0; //the price for the parking,services that the flight reserved !
    public int index;//the index of the parking corresponding to this flight parked
    public LocalTime progDepTime; //programmed departure time -->it is calculated as : landing time(initial switchStateTime) + deptime minutes
    public LocalTime firstContact=null;



    public Flight(String flid,Integer planetype,String city,Integer fltype,long deptime,String flstate){
        this.planetype=planetype;
        this.city=city;
        this.fltype=fltype;
        this.flstate=flstate;
        this.deptime=deptime;
        this.flid=flid;
        flcount=flcount+1; //use this static counter to set each flight priority according to creation
        services=new ArrayList<String>();

    }


    public void setPark_flight_id(String park_flight_id) {
        this.park_flight_id = park_flight_id;
    }

    public void setFlpriority(int flpriority) {
        this.flpriority = flpriority;
    }

    public int getFlpriority() {
        return flpriority;
    }

    public int getPlanetype() {
        return  this.planetype;
    }

    /*not sure yet -- a function to check if a jet is monojet ,returns boolean to check for ****load/unload**** service later*/
    public boolean checkJet(){
        if(planetype.equals(1))
           return  true;
        else
            return false;
    }


    public Integer getFltype() {
        return fltype;
    }

    public String getPark_flight_id() {
        return park_flight_id;
    }

    public int setSwitchStateTime(Integer planetype) {
        if(this.planetype.equals(3))
            return 2;
        else if(this.planetype.equals(2))
            return 4;
        else if(this.planetype.equals(1))
            return 6;
        else return 0;
    }

    public void setFlstate(String flstate) {
        this.flstate = flstate;
    }

    public String getFlstate() {
        return flstate;
    }


    // compute the  bonus sale/penalty of the flight due to early/late departure (realDepartime is set random from main programm )!!
    public void setCoefficient(long realDepartime) {
        if(realDepartime > this.deptime)
           this.flight_park_price=2*this.flight_park_price;
        else if(this.deptime-realDepartime >= 25)
            this.flight_park_price=0.8*this.flight_park_price;
        else if(this.deptime-realDepartime >= 10 & this.deptime-realDepartime <=20)
            this.flight_park_price=0.9*this.flight_park_price;
    }







}
