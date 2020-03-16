
package user_interface;

import java.awt.*;
import java.awt.TextArea;
import java.io.*;
import java.net.URL;
import java.security.AllPermission;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import park.Airpark;
import park.Flight;


public class Controller
{

    //initializing all the AnchorPanes
    @FXML
    private AnchorPane topPane;
    @FXML
    private Label appTitle;
    @FXML
    private AnchorPane upperPane;
    @FXML
    private MenuButton menu;
    @FXML
    private MenuButton deTails;
    @FXML
    private AnchorPane middlePane;
    @FXML
    private GridPane statsPane;
    @FXML
    private TitledPane arrivalPane;
    @FXML
    private TitledPane spotPane;
    @FXML
    private TitledPane departPane;
    @FXML
    private TitledPane earnPane;
    @FXML
    private TitledPane timePane;
    @FXML
    private AnchorPane lowerPane;
    @FXML
    private GridPane splitPane;
    @FXML
    private GridPane formPane;
    @FXML
    private AnchorPane bottomPane;


    //initializing the parks optimization grid ( that shows the parks and if they are free or used)
    //all accordions
    @FXML
    private GridPane parksPane;
    @FXML
    private Accordion accord0;
    @FXML
    private Accordion accord1;
    @FXML
    private Accordion accord2;
    @FXML
    private Accordion accord3;
    @FXML
    private Accordion accord4;
    @FXML
    private Accordion accord5;
    @FXML
    private Accordion accord6;
    //all titled panes
    @FXML
    private TitledPane pane0;
    @FXML
    private TitledPane pane1;
    @FXML
    private TitledPane pane2;
    @FXML
    private TitledPane pane3;
    @FXML
    private TitledPane pane4;
    @FXML
    private TitledPane pane5;
    @FXML
    private TitledPane pane6;
    //all scroll panes
    @FXML
    private ScrollPane scroll0;
    @FXML
    private ScrollPane scroll1;
    @FXML
    private ScrollPane scroll2;
    @FXML
    private ScrollPane scroll3;
    @FXML
    private ScrollPane scroll4;
    @FXML
    private ScrollPane scroll5;
    @FXML
    private ScrollPane scroll6;

    //initializing  The references of the mainMenu )
    //1st menu
    @FXML
    private MenuItem startSel;
    @FXML
    private MenuItem loadConf;
    @FXML
    private MenuItem exitSel;
    //@FXML
    //private MenuItem detailMenu=null;
    //details submenu
    @FXML
    private MenuItem gateSel;
    @FXML
    private MenuItem flightSel;
    @FXML
    private MenuItem delaySel;
    @FXML
    private MenuItem holdSel;
    @FXML
    private MenuItem departSel;
  /*---------------------------------*/

    // The references of the statPane (flight arriving,spots available.......)
    @FXML
    private Label arriveLab;
    @FXML
    private Label spotLab;
    @FXML
    private Label departLab;
    @FXML
    private Label earnLab;
    @FXML
    private Label timeLab;
  /* -------------------------------*/

  // The references of the formPane (new flight ,type,.......)
    //input forms
    @FXML
    private Label formLab;
    @FXML
    private TextField idInput;
    @FXML
    private TextField cityInput;
    @FXML
    private TextField fltypeInput;
    @FXML
    private TextField pltypeInput;
    @FXML
    private TextField prodepInput;
    //checkboxes
    @FXML
    private CheckBox fuelCheck;
    @FXML
    private CheckBox cleanCheck;
    @FXML
    private CheckBox passCheck;
    @FXML
    private CheckBox loadCheck;
    //submit button
    @FXML
    private Button submitButt;
  /* ---------------------------- */
 //The references of the Popups label at the bottom(popups,fill...)
    @FXML
    private Label popLab;
    @FXML
    private Label fillLab;
    @FXML
    private ScrollPane scrollast;
    @FXML
    private AnchorPane warnPane;


    /*******************--------------inner class that adds 1 minute every 5 secs and handles the tasks-----------*************************/
    class timeCount extends TimerTask {

        public void run() {
            //use platform.runLater to allow thread to change fx-text from outside//
            Platform.runLater(()-> {
                        DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm");
                        LocalTime ltime = LocalTime.parse(timeLab.getText());
                        ltime = ltime.plusMinutes(1);
                        timeLab.setText(ltime.toString());
                        Random random = new Random();



                        //CHECK FOR FLIGHTS TO LAND //
                        for (int i = 0; i < flightsLanding.size(); i++) {
                            if (flightsLanding.get(i).switchStateTime != null && flightsLanding.get(i).switchStateTime.equals(LocalTime.parse(timeLab.getText())) && flightsLanding.get(i).getFlstate().equals("Landing") && !flightsLanding.get(i).getFlstate().equals("Parked") && !flightsLanding.get(i).getFlstate().equals("Departed")) {

                                //turn the flight parked
                                flightsLanding.get(i).setFlstate("Parked");
                                //add to parked set
                                flightsParked.add(flightsLanding.get(i));


                                //UPDATE LANDING ANCHORPANE TO DISPLAY THE LANDING FLIGHTS TO THE USER //
                                ++flightsCount;
                                arriveLab.setText(String.valueOf(flightsCount));


                                //set the actual random departure time//
                                //set the switchstatetime =time of landing + nxt minutes!
                                int nxt = random.nextInt(9) + 10;
                                System.out.println("old time to land" + flightsLanding.get(i).switchStateTime.toString());

                                //set the programmed departure time //
                                flightsLanding.get(i).progDepTime=flightsLanding.get(i).switchStateTime.plusMinutes(flightsLanding.get(i).deptime);



                                //set the new departure time
                                //switchStateTime is changed to the actual departure time now
                                flightsLanding.get(i).switchStateTime = flightsLanding.get(i).switchStateTime.plusMinutes(nxt);
                                System.out.println("new time to depart" + flightsLanding.get(i).switchStateTime.toString());



                                //check if the flight is landing on time /delayed/sooner and update price accordingly
                                flightsLanding.get(i).setCoefficient(nxt);


                                //SET THE PARKING that corresponds to the flight landing  TO RED --RESERVED STATE
                                Rectangle rec = (Rectangle) parksPane.getScene().lookup("#" + "rec" + flightsLanding.get(i).park_flight_id);
                                rec.setFill(Color.CRIMSON);


                                scrollast.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                                Glow gl2 = new Glow();
                                popLab.setEffect(gl2);
                                scrollast.setFitToHeight(true);
                                popLab.setText(popLab.getText() + "\nThe flight with id: " + flightsLanding.get(i).flid + " was  parked\n");


                                //details menu update //
                                 flightext=flightext+"\nflight: "+ flightsLanding.get(i).flid + " city "+ flightsLanding.get(i).city + " fltype "+ flightsLanding.get(i).getFltype() + " state "+ flightsLanding.get(i).getFlstate()+"parked in "+ allParks.get(flightsLanding.get(i).index).parkid + "leaves in :"+ flightsLanding.get(i).deptime;
                                //delayed flights updates
                                if(nxt>flightsLanding.get(i).deptime) {
                                    delayText = delayText + "\n" + "Delayed flight with id: " + flightsLanding.get(i).flid + " parked in :" + allParks.get(flightsLanding.get(i).index).getParkid() + " with plane type :" + flightsLanding.get(i).planetype + " with flight type " + flightsLanding.get(i).fltype + " programmed deptime: " + flightsLanding.get(i).deptime;
                                    System.out.println("\nthe nxt is: "+nxt+" and the cost is: "+flightsLanding.get(i).flight_park_price);
                                }
                            }
                        }

                        //remove the parked flight from the flightset
                        //SUBSTITUTE WITH REMOVE (NOT REMOVEALL) SOS !!!!
                        if (!flightsLanding.isEmpty())
                            flightsLanding.removeAll(flightsParked);



                        //handle the holding flights now //
                      try {
                          flightAdder();     //park the holding flights if there are parkings available !
                      }catch(Exception e){
                          e.printStackTrace();
                      }


            /*here maybe implement the flights departing from the airports*/
            /*generate random minutes for departure -- earlier or later departure from the one planned
             the number must be between 10 and max value=60 !test value !probably change later the max
               the number */

            Collections.sort(flightsParked,new Comp());

            for(int i=0;i<flightsParked.size();i++){
                    System.out.println(" the flight with id "+ flightsParked.get(i).flid + " has total parking cost "+ flightsParked.get(i).flight_park_price);


                    //check for departures in next 10 minutes
                    if(flightsParked.get(i).progDepTime.equals(LocalTime.parse(timeLab.getText()).plusMinutes(10))){
                        ++tenCount;
                        departLab.setText(String.valueOf(tenCount));
                        tenText=tenText+"\nflight with id "+flightsParked.get(i).flid+" with flight type "+flightsParked.get(i).fltype+" with plane type "+flightsParked.get(i).planetype;
                    }

                    //departing flights ->their time has come
                    if(LocalTime.parse(timeLab.getText()).equals(flightsParked.get(i).switchStateTime) && !flightsParked.get(i).flstate.equals("Departed")){
                        //add to departing flights
                        flightsParked.get(i).setFlstate("Departed");

                        if(!flightsDep.contains(flightsParked.get(i)))
                                flightsDep.add(flightsParked.get(i));

                        //here should check if this works correctly // maybe the index is wrong !
                        System.out.println("the flight with park -flight id :"+flightsParked.get(i).park_flight_id+"is departing" );

                        //display to the user that the flight is departing//
                        popLab.setText(popLab.getText() + "\nThe flight with id: " + flightsParked.get(i).flid + " is departing \n");




                        //UPDATE THE COST ADDING THE COST OF THE FLIGHT  DEPARTING TO THE TOTAL COST ANCHORPANE --display to user //
                        earnLab.setText(String.valueOf(Double.parseDouble(earnLab.getText()) + flightsParked.get(i).flight_park_price));

                        //HERE WE MUST SET THE STATE OF THE CORRESPONDING PARKING TO FREE !//
                        //use the index that binds this flight to a certain parking from park array//
                        allParks.get(flightsParked.get(i).index).setParkstate("free");


                        //update the available spots //
                        spotLab.setText(String.valueOf(Integer.parseInt(spotLab.getText())+1));


                        //find the reserved parking and set it to free again--green
                        Rectangle rec = (Rectangle) parksPane.getScene().lookup("#" + "rec" + flightsParked.get(i).park_flight_id);
                        rec.setFill(Color.MEDIUMSEAGREEN);

                        //update the details --departing flight
                          flightext="\n DEPARTING flight: "+ flightsParked.get(i).flid + " city "+ flightsParked.get(i).city + " fltype "+ flightsParked.get(i).getFltype() +" and state "+flightsParked.get(i).getFlstate()+" leaves in: "+ flightsParked.get(i).deptime;



                    }

            }

            if(!flightsParked.isEmpty() && !flightsDep.isEmpty())
                flightsParked.removeAll(flightsDep);

            //update flights details
                fStage=popupTexter(fStage,flightext,"flightsDetails");
            //update parking details
                pStage=popupTexter(pStage,parksTexter(),"parksDetails");

            //update Delayed Flights details
                dStage=popupTexter(dStage,delayText,"delayDetails");

            //update flights departing in 10 minutes details
                tStage=popupTexter(tStage,tenText,"tenDetails");
            //update the holding flights details
                hStage=popupTexter(hStage,holdText,"holdDetails");

        });
        }
    }






    /*****--another inner class that implements the comparator of flightset priority queue ascending order of flpriority ***************************/
    class Comp implements Comparator<Flight> {
        @Override
        public int compare(Flight f1, Flight f2) {
           return f1.getFlpriority()-f2.getFlpriority();
        }
    }






    //creating hashsets for fast search for parkings
    //and priority queue for flights
    HashSet<Airpark> gate=new HashSet<>();
    HashSet<Airpark>  commerc=new HashSet<>();
    HashSet<Airpark>  zoneA=new HashSet<>();
    HashSet<Airpark>  zoneB=new HashSet<>();
    HashSet<Airpark>  zoneC=new HashSet<>();
    HashSet<Airpark>  general=new HashSet<>();
    HashSet<Airpark>  biglength=new HashSet<>();
    ArrayList <Flight> flightSet=new ArrayList<>();
    ArrayList <Airpark> allParks=new ArrayList<>();


    //CREATING HASHSET//arraylist FOR HOLDING THE PARKED flights //
    ArrayList <Flight> flightsParked =new ArrayList<>();

    //creating arraylist for landing flights(flights in landing state)//
    ArrayList<Flight> flightsLanding=new ArrayList<>();


    //creating Arraylist for departing flights //
    ArrayList< Flight> flightsDep=new ArrayList<>();


    //create arraylist to delete the unparked flights from
    //the default scenario
    List<Flight> flightsDeleted=new ArrayList<>();


    //create hashmap to hold the correspondence between flights and parkings //
    HashMap<Airpark,Flight> hm=new HashMap<>();

    //text for details static
    String flightext;
    String parktext;
    String delayText;
    String tenText;
    String holdText;

    //create detailsstages
    Stage fStage;
    Stage pStage;
    Stage dStage;
    Stage tStage;
    Stage hStage;

    //flightsLanding
    static int flightsCount=0;

    //flights departing in 10
    static int tenCount=0;


    //counter to count the number of default_scenario_flights:
    //will be used later
    static int default_flights=0;



    //Arraylist for Threads //
    ArrayList<Timer> timers=new ArrayList<>();


    //FUNCTION TO CREATE POPUPS FOR DETAILS MENU
    public Stage popupHandler(String textid) {
         VBox comp = new VBox();
         Stage newStage=new Stage();
         Scene newScene=new Scene(comp,200,200);
         Label gText = new Label("");
         gText.setId(textid);
         comp.getChildren().add(gText);

        //Scene newScene = new Scene(comp, 500, 500);
        newStage.setScene(newScene);


        return newStage;


    }

    //FUNCTION TO UPDATE POPUPS
    public Stage popupTexter(Stage newStage,String updates,String textid){
        Scene sc=newStage.getScene();
        Label label=(Label)sc.lookup("#"+textid);
        label.setText(updates);
        newStage.sizeToScene();
        return newStage;

    }


    //function to loop over the parkings on command and update parking details
    public String parksTexter(){
        parktext="";
        for (int k=0;k<allParks.size();k++) {
            Airpark ap=allParks.get(k);
            if(ap.getParkstate().equals("reserved")){
                parktext=parktext+"\nParking with id: "+ap.getParkid()+" with state "+ap.getParkstate()+" with parked flight "+hm.get(ap).flid+" and deptime "+hm.get(ap).deptime;
            }
            else{
                parktext=parktext+"\nParking with id: "+ap.getParkid()+" with state "+ap.getParkstate();
            }
        }
        return parktext;
    }



    //creating the visual representations of parkings --rectangles!
    public void rectangleCreator(HashSet<Airpark> hashset,ScrollPane scrolpane){

             Group g = new Group();
             int counter=0;
             for (Airpark airpark:hashset){

                Rectangle rectangle=new Rectangle();
                rectangle.setWidth(40f);
                rectangle.setHeight(40f);
                rectangle.setFill(Color.MEDIUMSEAGREEN);

                Text text=new Text(airpark.getParkid());
                StackPane sp=new StackPane();

                //set layouyt of each stackpane so we can draw rectangles in sequence
                sp.setLayoutX(0.0+60*counter);

                //set the stackpanes ids=the airpark ids so we can edit the spaces later(the space is free or reserved !!)
                sp.setId(airpark.getParkid());

                //set the airparks ids= "rec"+ airpark_ids so we can change them later
                rectangle.setId("rec"+airpark.getParkid());
                sp.getChildren().addAll(rectangle,text);
                g.getChildren().add(sp);
                ++counter;

             }

             scrolpane.setContent(g);

    }

    //function to implement searching for parkings for each flight in flightset and parking them,notifying user //
    //FOR THE DEFAULT FLIGHTS //
    public void flightChecker() throws Exception{
        boolean found=false;
        int fsize=flightSet.size();
        int psize=allParks.size();
        Random random=new Random();


        //DISPLAY AVAILABLE PARKINGS TO THE USER // UPDATE WHEN A PARKING IS RESERVED //

       spotLab.setText(String.valueOf(allParks.size()));


        for (int i=0;i<fsize;i++) {

            found=false;

            if (flightSet.get(i)!=null && !flightSet.get(i).getFlstate().equals("Parked") &&  !flightSet.get(i).getFlstate().equals("Landing") ){

                for ( int k = 0; k < psize && !found; k++) {

                    if (allParks.get(k)!=null &&  allParks.get(k).plane_type.contains(flightSet.get(i).planetype) && allParks.get(k).fl_type.contains(flightSet.get(i).fltype) && allParks.get(k).getParkstate().equals("free")) {
                        //must have (hour of arrival + minutes to depart )-(hour of arrival) <= max parking duration
                        // if (((loc.getMinute() + 60 * loc.getHour()) + flightSet.get(i).deptime) - (loc.getMinute() + 60 * loc.getHour()) < allParks.get(k).maxtime) {

                        if (flightSet.get(i).deptime < allParks.get(k).maxtime) {
                            allParks.get(k).setParkstate("reserved");   //se parking as reserved
                            flightSet.get(i).setFlstate("Parked");  //set flight as parked

                            //update available spots //
                            spotLab.setText(String.valueOf(Integer.parseInt(spotLab.getText())-1));

                            //add to parked flights arraylist
                            flightsParked.add(flightSet.get(i));

                            /*set the allparks parking index to index of the reserved parking--used later for retrieving
                             the actual parking to-->set the state to free again when flight departs*/
                            flightSet.get(i).index=k;


                            //correspond the flight to the used parking
                            hm.put(allParks.get(k),flightSet.get(i));



                            //Set the flight park id the flights id corresponding to the parking reserved //
                            flightSet.get(i).setPark_flight_id(allParks.get(k).getParkid());

                            //SET THEIR TIME OF LANDING -->switchStateTime
                            flightSet.get(i).switchStateTime=LocalTime.parse(timeLab.getText());


                            //set the programmed departing time
                            flightSet.get(i).progDepTime=flightSet.get(i).switchStateTime.plusMinutes(flightSet.get(i).deptime);

                            //set the initial price of the flight according to parking//
                            flightSet.get(i).flight_park_price=allParks.get(k).cost;

                            //print old cost
                            System.out.println("\nold cost "+flightSet.get(i).flight_park_price);

                           //-- HERE  SET THE NEW DEPARTURE TIME  USING THE MINUTES --//
                            int nxt = random.nextInt(9) +10 ;
                            System.out.println("old time to land" + flightSet.get(i).switchStateTime.toString());
                            flightSet.get(i).switchStateTime=(flightSet.get(i).switchStateTime).plusMinutes(nxt);
                            System.out.println( "new time to depart" + flightSet.get(i).switchStateTime.toString());

                            //check if the flight is landing on time /delayed/sooner and update price accordingly
                            flightSet.get(i).setCoefficient(nxt);

                            //update delayed flights
                            if(nxt>flightSet.get(i).deptime)
                                delayText=delayText+"\n"+"Delayed flight with id: "+flightSet.get(i).flid +" parked in :"+allParks.get(flightSet.get(i).index).getParkid()+" with plane type :"+flightSet.get(i).planetype +" with flight type "+flightSet.get(i).fltype+" programmed deptime: " +flightSet.get(i).deptime;


                            //TESTING THE COST UPDATE
                            System.out.println("old deptime: "+flightSet.get(i).deptime +"vs new deptime"+nxt+
                                    "\n new cost"+flightSet.get(i).flight_park_price);


                            //paint the rectangle -spot red !!it is now reserved
                            Rectangle rec = (Rectangle) parksPane.getScene().lookup("#" + "rec" + allParks.get(k).getParkid());
                            rec.setFill(Color.CRIMSON);
                            //Node node= parksPane.getScene().lookup(allParks.get(k).getParkid());
                            found=true;
                            //continue;
                            //notify that parking was found and the flight has parked

                            scrollast.setFitToHeight(true);
                           // scrollast.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                            Glow gl = new Glow();
                            popLab.setEffect(gl);
                            popLab.setText(popLab.getText() + "\nThe flight with id: " + flightSet.get(i).flid + " was  parked\n");

                            //start updating flight details
                            flightext=flightext+"\n Parked flight: "+ flightSet.get(i).flid + " city "+ flightSet.get(i).city + " fltype "+ flightSet.get(i).getFltype() + " state "+ flightSet.get(i).getFlstate()+" parked in "+ allParks.get(flightSet.get(i).index).parkid + " leaves in: "+ flightSet.get(i).deptime;



                        }

                    }




                }


            }
            //if the flight has not found free parking then notify the user on bottom region of site
            if(!found) {
                Glow gl = new Glow();
                popLab.setEffect(gl);
                popLab.setText(popLab.getText() + "\nThe flight with id: " + flightSet.get(i).flid + " was not parked and will be ignored !\n");

                //set the flights who have not parked to holding maybe!
                //On second thought just ignore these flights , we should never work with them
               // flightSet.get(i).setFlstate("IGNORED");
                flightsDeleted.add(flightSet.get(i));

            }
        }
        //remove the unparked flights from flightset
        flightSet.removeAll(flightsDeleted);
        //remove parked flights from the flightset
        flightSet.removeAll(flightsParked);

        for(Flight fl:flightSet){
            System.out.println(fl.flid);
        }

    }




    /* second function to add the new user input flights aNd handle their parking
       based on the time to land ,based on planetype
       FOR THE USER DEFINED FLIGHTS
     */
    public void flightAdder() throws Exception{
        boolean found=false;
        int fsize=flightSet.size();
        int psize=allParks.size();
        int timetoLand;

        for (int i=0;i<fsize;i++) {
            found=false;

            if(!flightSet.get(i).getFlstate().equals("Holding"))
                    flightSet.get(i).firstContact=LocalTime.parse(timeLab.getText());

            if ( !flightSet.get(i).getFlstate().equals("Parked") &&  !flightSet.get(i).getFlstate().equals("Landing") ){


                for (int k = 0; k < psize && found!=true ; k++) {

                    if ( allParks.get(k).plane_type.contains(flightSet.get(i).planetype) && allParks.get(k).fl_type.contains(flightSet.get(i).fltype) &&  allParks.get(k).pservices.containsAll(flightSet.get(i).services) && allParks.get(k).getParkstate().equals("free")) {

                        //time until departure + landing time <= parking time
                        //flightSet.get(i).setSwitchStateTime(flightSet.get(i).planetype);

                        if (flightSet.get(i).deptime + flightSet.get(i).setSwitchStateTime(flightSet.get(i).planetype)< allParks.get(k).maxtime) {
                            allParks.get(k).setParkstate("reserved");   //se parking as reserved

                            //update available spots //
                            spotLab.setText(String.valueOf(Integer.parseInt(spotLab.getText())-1));


                            flightSet.get(i).setFlstate("Landing");  //set flight as LANDING


                            Glow gl = new Glow();
                            popLab.setEffect(gl);
                            popLab.setText(popLab.getText() + "\nThe flight with id: " + flightSet.get(i).flid + " is landing\n");


                            //wait the extra minutes before filling the parkings
                           // LocalTime temptime=LocalTime.parse(timeLab.getText());
                            flightSet.get(i).switchStateTime=LocalTime.parse(timeLab.getText()).plusMinutes(flightSet.get(i).setSwitchStateTime(flightSet.get(i).planetype));


                            //add to parked flights arraylist
                            if(!flightsLanding.contains(flightSet.get(i)))
                                flightsLanding.add(flightSet.get(i));

                            //set the flight's parks id ==the id of the parking to be parked
                            flightSet.get(i).setPark_flight_id(allParks.get(k).getParkid());

                            //set the index of the parking for retrieval later (departure)//
                            flightSet.get(i).index=k;


                            //start updating details
                           // flightext="\n Parked flight: "+ flightSet.get(i).flid + " city "+ flightSet.get(i).city + " fltype "+ flightSet.get(i).getFltype() + " state "+ flightSet.get(i).getFlstate()+" parked in "+ allParks.get(flightSet.get(i).index).parkid+ "leaves in :"+ flightSet.get(i).deptime;



                            //correspond the flight to the used parking
                            hm.put(allParks.get(k),flightSet.get(i));



                            //set the flight's initial parking price (services + parking)  //+ sos if monojet free load
                            //cost of parking + coefficient based on the services !!
                            if(flightSet.get(i).flight_park_price==0) {
                                flightSet.get(i).flight_park_price = allParks.get(k).cost;
                                if (flightSet.get(i).services.contains("refill"))
                                    flightSet.get(i).flight_park_price += 0.25 * allParks.get(k).cost;
                                if (flightSet.get(i).services.contains("clean"))
                                    flightSet.get(i).flight_park_price += 0.02 * allParks.get(k).cost;
                                if (flightSet.get(i).services.contains("transport"))
                                    flightSet.get(i).flight_park_price += 0.02 * allParks.get(k).cost;
                                if (flightSet.get(i).services.contains("load") && !flightSet.get(i).checkJet())
                                    flightSet.get(i).flight_park_price += 0.05 * allParks.get(k).cost;
                            }

                            //here implement the cost -bonus/extra money to pay for parking and services //


                            //paint the rectangle -spot red !!it is now reserved
                            Rectangle rec = (Rectangle) parksPane.getScene().lookup("#" + "rec" + allParks.get(k).getParkid());
                            rec.setFill(Color.ORANGERED);
                            //Node node= parksPane.getScene().lookup(allParks.get(k).getParkid());

                            //continue;
                            //notify that parking was found and the flight has parked

                            found=true;


                        }

                    }

                }


            }
            //if the flight has not found free parking then notify the user on bottom region of site
            if(found==false && !flightSet.get(i).equals("Landing")){
                Bloom bloom = new Bloom();
                bloom.setThreshold(0.1);
                scrollast.setFitToHeight(true);
                popLab.setEffect(bloom);
                popLab.setText(popLab.getText() + "\nThe flight with id: " + flightSet.get(i).flid + " was not parked and is on hold!\n");

                //set the flights who have not parked to holding maybe!
                flightSet.get(i).setFlstate("Holding");

                //update holding flights in details both in flight and holding details !
                flightext=flightext+"\n Holding flight: "+ flightSet.get(i).flid + " city "+ flightSet.get(i).city + " fltype "+ flightSet.get(i).getFltype() + " state "+ flightSet.get(i).getFlstate()+" leaves in: "+ flightSet.get(i).deptime;
                holdText=holdText+"\n Holding flight: "+ flightSet.get(i).flid + " fltype "+ flightSet.get(i).getFltype() + " with plane type "+ flightSet.get(i).planetype+" and contact time: "+flightSet.get(i).firstContact.toString();
            }
        }

       if(!flightSet.isEmpty() && !flightsLanding.isEmpty()) flightSet.removeAll(flightsLanding);

        //sort the flightsLanding according to priority
        Collections.sort(flightsLanding,new Comp());


    }


    //function to reset the structures //
    public void cleanLists(){
        flightext="";
        delayText="";
        parktext="";
        tenText="";
        holdText="";
        //CLEAN THE LABS //
        flightsLanding.clear();
        flightsDeleted.clear();
        flightsParked.clear();
        allParks.clear();
        zoneC.clear();
        zoneB.clear();
        zoneA.clear();
        gate.clear();
        commerc.clear();
        biglength.clear();
        general.clear();

    }




//file initialize 
    public void fileInitializer(File f1,File f2,HashSet<Airpark> h0,HashSet<Airpark> h1,HashSet<Airpark> h2,HashSet<Airpark> h3,HashSet<Airpark> h4,HashSet<Airpark> h5,HashSet<Airpark> h6,ArrayList<Flight> h7,ArrayList<Airpark> h8){
        int categ;   //reading category
        int parks=0;     //reading number of parks of this category
        int parkscategcost=0;  //cost for this category parking
        char parkscategid=0; //the general id of this category parkings
        /*maybe create an arraylist for each parameteter --categ,it has all the catego
         ries of the created parks ,parks,cost....or a hashtable for each category
         */


        //create array of parks,flight objects for the parkings,flights
        //so i can loop over them
        //Airpark airpark[]=new Airpark[1000];
       // Flight flights[]=new Flight[1000];
        int counter=0;
        // int counter2=0;


        //call cleanLists to reset the structures //
        cleanLists();
        starter();

        //check if previous scenario is running //
        if(!timers.isEmpty()){
            for(int k=0;k<timers.size();k++){
                timers.get(k).cancel();
                timers.get(k).purge();
            }
        }





        File file=f1;



        /* reading the airport description file */
/*should put categ,parks,parkscategcost,parkscategid
of each line into an arrraylist/queue/list to save the info for each
category ---i have not done it yet--
*/

        try{
            Scanner sc=new Scanner(file);
            sc.useDelimiter(", |\n");


            while(sc.hasNextLine()){
                categ= Integer.parseInt(sc.next());
                parks=Integer.parseInt(sc.next()); //spaces of each parking
                parkscategcost=Integer.parseInt(sc.next()); //cost of parking
                parkscategid=sc.next().charAt(0); //id of the parking!!!sos must do single value not fot the whole array

           /* sos maybe call the Airport constructor here to create an
           airpark instance with different parkings for each category
           --line that i read */


                Airpark airpark=null;

                //create the parkings based on initial file
                for (counter=0;counter<parks;counter++) {
                    try {

                        airpark = new Airpark(categ);
                        airpark.setParkid(parkscategid + Integer.toString(counter)); //creating the single id for each park object based on category
                        airpark.categorySetter(); //set category and traits
                        // airpark[counter].setSpaces(parks); //set number of spaces of this parking
                        airpark.setCost(parkscategcost);  //!!read int but cost could be float
                        airpark.setParkstate("free");

                        //add to general arraylist
                        h8.add(airpark);

                        //add to hashset according to category

                        switch (airpark.getCategory()) {
                            case 1:
                                h0.add(airpark);
                                break;
                            case 2:
                                h1.add(airpark);
                                break;
                            case 3:
                                h2.add(airpark);
                                break;
                            case 4:
                                h3.add(airpark);
                                break;
                            case 5:
                                h4.add(airpark);
                                break;
                            case 6:
                                h5.add(airpark);
                                break;
                            case 7:
                                h6.add(airpark);
                                break;


                        }

                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }

                }

                // System.out.printf("%d %d %d %c\n",categ,parks,parkscategcost,parkscategid);

            }

            sc.close();


            /*initializing the current time */
            /*formatting to show only the hour:minutes:seconds*/

            Date datetime =new Date();
            System.out.println(datetime.toString());
            SimpleDateFormat ft =new SimpleDateFormat ("hh:mm:ss a zzz");
            System.out.println(ft.format(datetime));


            /*reading the initial flights (these flights are already at the airport to leave)*/

            String fl_id=null;
            String fl_city=null;
            int fl_type=0;
            int fl_planetype=0;
            int progr_deptime=0;


/*here try to call the new scanner to read the setup_scenario.txt/*
/* ----------*/

            File file2=f2;
            int counter2=0;

            try {

                String line;
                String[] splitstring;
                BufferedReader br = new BufferedReader(new FileReader(file2));

                while((line = br.readLine()) != null)
                {
                    splitstring  = line.split(", ");
                    fl_id=splitstring[0];
                    fl_city=splitstring[1];
                    fl_type=Integer.parseInt(splitstring[2]);
                    fl_planetype=Integer.parseInt((splitstring[3]));
                    progr_deptime=Integer.parseInt((splitstring[4]));

             /* sos maybe call the Flight constructor here to create an
            flight instance with different parameters for each line i read
            */

                  // flight[counter2]=new Flight(fl_id,fl_planetype,fl_city,fl_type,progr_deptime);
                  Flight flight=new Flight(fl_id,fl_planetype,fl_city,fl_type,progr_deptime,"not stated yet");
                  flight.setFlpriority(Flight.flcount); //set priority counter (smaller is earlier in list)
                  h7.add(flight);  //add to priority queue


                    System.out.printf("%s %s %d %d %d\n",fl_id,fl_city,fl_type,fl_planetype,progr_deptime);
                    ++counter2;

                }

                default_flights=counter2;


            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }


        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


    }



    //function to do initializing in scene //

    public void starter(){
        fStage=popupHandler("flightsDetails");
        pStage=popupHandler("parksDetails");
        dStage=popupHandler("delayDetails");
        tStage=popupHandler("tenDetails");
        hStage=popupHandler("holdDetails");


        //initializing the stats values to 0 (flights arriving,spots available ,flights programmed in 10',total income...)
        //initialize the total time to time_date format! so we can add minutes later

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime totaltime = LocalTime.parse("00:00");
        arriveLab.setText(Integer.toString(0));
        spotLab.setText(Integer.toString(0));
        departLab.setText(Integer.toString(0));
        earnLab.setText(Double.toString(0));
        timeLab.setText(totaltime.toString());
        fillLab.setText("no info yet");
    }


    // Add a public no-args constructor
    public Controller(){


    }

    @FXML
    private void initialize()
    {
        final int initValue=0;

        //set up-initialize details popup
            starter();

        //handle threads//
        //add thread to timer list//

        //timer.schedule(new timeCount(), 0, 5000);


        //handling menu and submenu !!
        //handle exit
        exitSel.setOnAction(event ->{
           Stage stage =(Stage)upperPane.getScene().getWindow();
           stage.close();
        });

        //handling load scenario
        loadConf.setOnAction(event->{

            //handling the stuff already running

           Stage newStage =new Stage();
           VBox comp =new VBox();
           Label newLabel=new Label("Please insert the scenario here !");
           Button sub=new Button("SUBMIT");
           TextField scenarioField=new TextField("scenario");
           comp.getChildren().add(newLabel);
           comp.getChildren().add(scenarioField);
            comp.getChildren().add(sub);
           Scene newScene=new Scene(comp,300,100);
           newStage.setScene(newScene);
           newStage.show();



           //handle button for submiting the requested scenario !
            //check if requested scenario-files (FLIGHT,AIRPORT) exist !
           sub.setOnAction(event2->{
               File tempFile1=new File("medialab/airport_"+scenarioField.getText()+".txt");
               File tempFile2=new File("medialab/setup_"+scenarioField.getText()+".txt");

               //check existence IF exists initialize app else alert error !
               if(!tempFile1.exists() || !tempFile2.exists()) {

                   Alert a = new Alert(Alert.AlertType.ERROR);
                   a.setContentText("THIS FILE DOES NOT EXIST!TRY AGAIN");
                   a.show();
               }
               else {
                   System.out.println("ok we have saved the requested configuration");

                   /*call fileInitializer() function to create the flights,parkings
                   according to given files (setup,flights) */
                   fileInitializer(tempFile1,tempFile2,gate,commerc,zoneA,zoneB,zoneC,general,biglength,flightSet,allParks);

                   //create the rectangles-visual representations of the parkings in the scroll-panes
                   //give the stackpanes,rectangulars the ids of the airparks !!(see function)
                   rectangleCreator(gate,scroll0);
                   rectangleCreator(commerc,scroll1);
                   rectangleCreator(zoneA,scroll2);
                   rectangleCreator(zoneB,scroll3);
                   rectangleCreator(zoneC,scroll4);
                   rectangleCreator(general,scroll5);
                   rectangleCreator(biglength,scroll6);

               }

           });

        });



        //implement the main process ---when user presses start //
        startSel.setOnAction(event->{
                try {


                    //Timer timer = new Timer(true);
                      Timer timer =new Timer();
                      timers.add(timer);
                      timer.schedule(new timeCount(), 0, 5000);

                    //sort the flights --set priority
                    Collections.sort(flightSet, new Comp());
                    //begin searching
                    LocalTime loc;
                    loc = LocalTime.parse(timeLab.getText());

                    //call the function to find parkings for the flights in the set !

                    flightChecker();


                }catch (ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                }catch (Exception p){
                    p.printStackTrace();
                }

        });



        //implement the user-input handle ..whe user enters new flights !!!
        submitButt.setOnAction(event->{
                //read user inputs and check if they are ok
           // Boolean ok=true;

            try {
                String inflid = idInput.getText();
                String incity = cityInput.getText();
                Integer infltype = Integer.parseInt(fltypeInput.getText());
                Integer inpltype = Integer.parseInt(pltypeInput.getText());
                Integer inprogdeptime = Integer.parseInt(prodepInput.getText());


                if(inflid.trim().isEmpty() || incity.trim().isEmpty() || (!infltype.equals(1) & !infltype.equals(2) & !infltype.equals(3)) || (!inpltype.equals(1) & !inpltype.equals(2) & !inpltype.equals(3))  ) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("planetype and flighttype can only be 1 ,2 or 3");
                    a.show();
                }
                else   //create flight,add the extra services and  add to flightset !!

                {   //set the starter state of this flight to "holding" as default
                    Flight inflight=new Flight(inflid,inpltype,incity,infltype,inprogdeptime,"not stated yet");
                    inflight.setFlpriority(Flight.flcount);

                    if(fuelCheck.isSelected()) inflight.services.add("refill");{
                        fuelCheck.setSelected(false);
                    }
                    if(cleanCheck.isSelected()) inflight.services.add("clean");{
                        cleanCheck.setSelected(false);
                    }
                    if(passCheck.isSelected()) inflight.services.add("transporting");{
                        passCheck.setSelected(false);
                    }
                    if(loadCheck.isSelected()) inflight.services.add("load");{
                        loadCheck.setSelected(false);
                    }

                    //add the new flight to flighSet
                    flightSet.add(inflight);
                    //sort based on the new flights added
                    Collections.sort(flightSet,new Comp());




                    for(Flight flight:flightSet){
                        System.out.println("flight with id: "+inflight.flid+"pr: " + inflight.getFlpriority() + " city "+ inflight.city);
                    }


                flightAdder();

                }





            }catch(Exception e){
                e.printStackTrace();
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("wrong input, please type correct input and do not leave any form field empty!");
                a.show();
            }



        });


        //handle Gates button //
        gateSel.setOnAction(eventer-> {
            pStage.show();

        });



        //handle Flights button//
        flightSel.setOnAction(eventer->{
            fStage.show();

        });


        //handle Delayed flights//
        delaySel.setOnAction(eventer->{
            dStage.show();

        });



        //handle flights departing in 10
        departSel.setOnAction(eventer->{
            tStage.show();

        });

        //handle holding flights //
        holdSel.setOnAction(eventer->{
            hStage.show();
        });
    }




}

