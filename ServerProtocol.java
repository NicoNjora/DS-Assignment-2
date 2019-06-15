
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

    //choices of information that the server can request for
    ArrayList<String> server_choices = new ArrayList<String>(
            Arrays.asList("All Toy information", "Toy identification details", "Toy information details", "Manufacturer information"));

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Knock! Knock! Send me Toy information";
            state = SENTKNOCKKNOCK;
        } else if (state == SENTKNOCKKNOCK) {
            if (theInput.equalsIgnoreCase("which information?")) {
                Random r = new Random();
                int randomNumber = 0;
                //check if the server has requested for all information
                if (server_choices.isEmpty()) {
                    theOutput = "Send Toy unique identification code";
                    state = ALLRESPONSES;
                } else {
                    randomNumber = r.nextInt(server_choices.size());
                    theOutput = server_choices.get(randomNumber);
                    state = SENTREPLY;
                    server_choices.remove(randomNumber);
                }
            } else {
                theOutput = "You're supposed to say \"which information?\"" +
                        "! Try again. Knock! Knock!";
            }
        } else if (state == SENTREPLY) {
            if (theInput.equalsIgnoreCase("more information?")) {
                Random random = new Random();
                boolean isMoreInfo = random.nextBoolean();
                if (isMoreInfo) {
                    state = SENTKNOCKKNOCK;
                    theOutput = String.valueOf(isMoreInfo);
                } else {
                    theOutput = String.valueOf(isMoreInfo);
                    state = ALLRESPONSES;
                }
            } else {
                theOutput = "You're supposed to say \"more information?\"";
            }
        } else if (state == ALLRESPONSES) {
            if (theInput.equalsIgnoreCase("want more?")) {
                theOutput = " Send Toy unique identification code";
                state = FINISHED;
            }
        }else if (state == FINISHED || state == ALLRESPONSES){
            if (theInput.equalsIgnoreCase("Done?")) {
                theOutput = "Successful communication";
                state = WAITING;
            }
        }
        return theOutput;
    }
}