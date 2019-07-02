
import com.sun.xml.internal.ws.api.pipe.ServerTubeAssemblerContext;
import jdk.nashorn.internal.ir.IfNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ServerProtocol {
    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTREPLY = 2;
    private static final int ANOTHER = 3;
    private static final int ALLRESPONSES = 4;
    private static final int FINISHED = 5;
    private int state = WAITING;
    private static int  CURRENT_POSITION = 0;
    private static boolean IS_MORE = true;
    //choices of information that the server can request for
    ArrayList<String> server_choices = new ArrayList<String>(
            Arrays.asList( "Toy identification details", "Toy information details", "Manufacturer information", "All Toy information"));

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Knock! Knock! Send me Toy information";
            state = SENTKNOCKKNOCK;
        } else if (state == SENTKNOCKKNOCK) {
            if (theInput.equalsIgnoreCase("\nwhich information?")) {
                Random r = new Random();
                int randomNumber = 0;
                    randomNumber = r.nextInt(server_choices.size());
                    theOutput = server_choices.get(CURRENT_POSITION);
                    state = SENTREPLY;
                    server_choices.remove(CURRENT_POSITION);

            } else {
                theOutput = "You're supposed to say \"which information?\"" +
                        "! Try again. Knock! Knock!";
            }
        } else if (state == SENTREPLY ) {
            if (theInput.equalsIgnoreCase("\nmore information?")) {
                //check if the server has requested for all information
                if (server_choices.isEmpty()){
                    IS_MORE = false;
                    theOutput = String.valueOf(IS_MORE);
                    state = ALLRESPONSES;
                } else {
                    Random random = new Random();
                    state = SENTKNOCKKNOCK;
                    theOutput = String.valueOf(IS_MORE);
                }


            } else {
                theOutput = "You're supposed to say \"more information?\"";
            }
        } else if (state == ALLRESPONSES) {
            if (theInput.equalsIgnoreCase("want more?")) {
                theOutput = " \nSend Toy unique identification code";
                state = FINISHED;
            }
        }else if (state == FINISHED || state == ALLRESPONSES){
            if (theInput.equalsIgnoreCase("Done?")) {
                theOutput = "\nCommunication Successful Bye! ";
                state = WAITING;
            }
        }
        return theOutput;
    }
}