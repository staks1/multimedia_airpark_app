package process;
import park.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class App {




    public static void main(String[] args) {
        //creating HashSets for the each cateogry parkings for fast search
        // for empty parkings ( fast search of parkings for each category)

        HashSet<Airpark>  gate=new HashSet<>();
        HashSet<Airpark>  commerc=new HashSet<>();
        HashSet<Airpark>  zoneA=new HashSet<>();
        HashSet<Airpark>  zoneB=new HashSet<>();
        HashSet<Airpark>  zoneC=new HashSet<>();
        HashSet<Airpark>  general=new HashSet<>();
        HashSet<Airpark>  biglength=new HashSet<>();

        // int[] number=new int[4]; //parameters on each line
        int categ;   //reading category
        int parks=0;     //reading number of parks of this category
        int parkscategcost=0;  //cost for this category parking
        char parkscategid=0; //the general id of this category parkings

    /*maybe create an arraylist for each paramteter --categ,it has all the catego
    ries of the created parks ,parks,cost....or a hashtable for each category
     */


        //create array of parks,flight objects for the parkings,flights
        //so i can loop over them
        //Airpark airpark[]=new Airpark[1000];
        Flight flight[]=new Flight[1000];
        int counter=0;
        // int counter2=0;



        File file=new File("medialab/airport_SCENARIO-ID.txt");



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


                        //add to hashset according to category

                        switch (airpark.getCategory()) {
                            case 1:
                                gate.add(airpark);
                                break;
                            case 2:
                                commerc.add(airpark);
                                break;
                            case 3:
                                zoneA.add(airpark);
                                break;
                            case 4:
                                zoneB.add(airpark);
                                break;
                            case 5:
                                zoneC.add(airpark);
                                break;
                            case 6:
                                general.add(airpark);
                                break;
                            case 7:
                                biglength.add(airpark);
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

            File file2=new File("medialab/setup_SCENARIO-ID.txt");
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

                    flight[counter2]=new Flight(fl_id,fl_planetype,fl_city,fl_type,progr_deptime,"not stated yet");




                    System.out.printf("%s %s %d %d %d\n",fl_id,fl_city,fl_type,fl_planetype,progr_deptime);
                    ++counter2;
                }



                /* MAIN PROCESS */
                /* Starting the main process of the app ,to find parkings for the initial flights and  */















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

}
