
import java.util.Random;

public class ServerProtocol {
    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTREPLY = 2;
    private static final int ANOTHER = 3;
    private static final int ALLRESPONSES = 4;
    private static final int FINISHED = 5;
    private int state = WAITING;

    private String reply =  "Send Toy information";
    private int count = 0 ;//Keep track of the number of times a server can request for information
    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Knock! Knock!";
            state = SENTKNOCKKNOCK;
        } else if (state == SENTKNOCKKNOCK) {
            if (theInput.equalsIgnoreCase("How can I help?")) {
                theOutput =reply;
                state = SENTREPLY;
            } else {
                theOutput = "You're supposed to say \"How can I help?\"! " +
                        "Try again. Knock! Knock!";
            }
        } else if (state == SENTREPLY) {
            if (theInput.equalsIgnoreCase("which information?")) {
                String[] choices ={"All Toy information", "Toy identification details", "Toy information details", "Manufacturer information"};
                Random r=new Random();
                int randomNumber=r.nextInt(choices.length);
                theOutput = choices[randomNumber];
                state = ANOTHER;
            } else {
                theOutput = "You're supposed to say \"which information?\"" +
                        "! Try again. Knock! Knock!";
                state = SENTKNOCKKNOCK;
            }
        } else if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("want more information?")) {
                String[] choice ={"no", "yes"};
                Random r=new Random();
                int randomNumber=r.nextInt(choice.length);

                if (choice[randomNumber].equalsIgnoreCase("yes")){
                    //if (count <= 3){
                        theOutput = choice[randomNumber] + " send toy information";
                        ++count;
                        state = SENTREPLY;
//                    }else{
//                        theOutput = "You have exceeded the number of requests allowed;";
//                        state = ALLRESPONSES;
//                    }

                }else if (choice[randomNumber].equalsIgnoreCase("no")){
                    theOutput = choice[randomNumber];
                    state = ALLRESPONSES;
                }else{
                    theOutput = "No option selected";
                }
            }
        }else  if (state == ALLRESPONSES){
            if (theInput.equalsIgnoreCase("want more?")){
                theOutput = " Send Toy unique identification code";
                state = FINISHED;
            }
        }else if (state == FINISHED){
            if (theInput.equalsIgnoreCase("Done?")){
                theOutput = "Successful communication";
                state = WAITING;
            }
        }
        return theOutput;
    }
}