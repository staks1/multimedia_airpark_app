package park;

import java.util.ArrayList;

public class Airpark{
    public String parkid;    //the unique id of the parking (based maybe on hashtag of object)
    public String parkstate;  //the state of the parking (empty or reserved)
    public Integer category; //category of the parking---!8a ginei integer mallon telika
    public long maxtime;    //max parking time
    public float cost;        //cost of usage


    public ArrayList<Integer> fl_type;    //flight type the parking can support,according to category
    public ArrayList<Integer> plane_type; //plane types the parking can support,according to category
    public ArrayList<String>  pservices; //services type the parking can support,according to category


    public void setParkstate(String parkstate) {
        this.parkstate = parkstate;
    }

    public String getParkstate() {
        return parkstate;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setParkid(String parkid) {
        this.parkid = parkid;
    }

    public String getParkid() {
        return parkid;
    }


    /*set the remaining parameters of each  parking according to category */
    // services : refill , clean , transporting , load ,
    public void categorySetter(){

        //gate//
        if(this.category.equals(1)){
            this.fl_type.add(1);
            this.plane_type.add(2);
            this.plane_type.add(3);
            this.pservices.add("refill");
            this.pservices.add("clean");
            this.pservices.add("transporting");
            this.pservices.add("load");
            this.maxtime=45;
        }
        //commercial gate//
        if(this.category.equals(2)) {
            this.fl_type.add(2);
            this.plane_type.add(2);
            this.plane_type.add(3);
            this.pservices.add("refill");
            this.pservices.add("clean");
            this.pservices.add("transporting");
            this.pservices.add("load");
            this.maxtime = 90;
        }
        //zone A
        if(this.category.equals(3)) {
            this.fl_type.add(1);
            this.plane_type.add(2);
            this.plane_type.add(3);
            this.pservices.add("refill");
            this.pservices.add("clean");
            this.pservices.add("transporting");
            this.pservices.add("load");
            this.maxtime = 90;
        }
        //zone B
        if(this.category.equals(4)) {
            this.fl_type.add(1);
            this.fl_type.add(2);
            this.fl_type.add(3);
            this.plane_type.add(2);
            this.plane_type.add(3);
            this.pservices.add("refill");
            this.pservices.add("clean");
            this.pservices.add("transporting");
            this.pservices.add("load");
            this.maxtime = 120;
        }
        //zone C
        if(this.category.equals(5)) {
            this.fl_type.add(1);
            this.fl_type.add(2);
            this.fl_type.add(3);
            this.plane_type.add(1);
            this.pservices.add("refill");
            this.pservices.add("clean");
            this.pservices.add("transporting");
            //pservices.add("load"); !!!doesn't support load/unload for monojets
            this.maxtime = 180;
        }
        //general park
        if(this.category.equals(6)) {
            this.fl_type.add(1);
            this.fl_type.add(2);
            this.fl_type.add(3);
            this.plane_type.add(1);
            this.plane_type.add(3);
            this.plane_type.add(2);
            this.pservices.add("refill");
            this.pservices.add("clean");
            this.maxtime = 240;

        }
        //big length
        if(this.category.equals(7)) {
            this.fl_type.add(2);
            this.fl_type.add(3);
            this.plane_type.add(1);
            this.plane_type.add(3);
            this.plane_type.add(2);
            this.pservices.add("refill");
            this.pservices.add("clean");
            this.pservices.add("transporting");
            this.pservices.add("load");
            this.maxtime = 600;
        }

    }

    public Integer getCategory() {
        return category;
    }

    public Airpark(Integer category){
        //initialize the attributes
        this.category=category;
        this.fl_type=new ArrayList<Integer>();
        this.plane_type=new ArrayList<Integer>();
        this.pservices=new ArrayList<String>();
    }



}
