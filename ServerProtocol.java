import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.Random;

public class ServerProtocol {
    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTREPLY = 2;
    private static final int ANOTHER = 3;
    private static final int RECEIVEALL = 4;
    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;



    private String[] server_replies = { "Send Toy information", "Send me the unique code", "Atch", "Who", "Who" };
    private String[] answers = { "Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?" };

    private String server_choice;

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Knock! Knock!";
            state = SENTKNOCKKNOCK;
        } else if (state == SENTKNOCKKNOCK) {
            if (theInput.equalsIgnoreCase("How can I help?")) {
                theOutput = server_replies[0];
                state = SENTREPLY;
            } else {
                theOutput = "You're supposed to say \"How can I help?\"! " +
                        "Try again. Knock! Knock!";
            }
        } else if (state == SENTREPLY) {
            if (theInput.equalsIgnoreCase("which information?")){
                String[] choices ={"All Toy information", "Toy identification details", "Toy information details", "Manufacturer information"};
                Random r=new Random();
                int randomNumber=r.nextInt(choices.length);
                theOutput = "Beatrice";
            }else if (theInput.equalsIgnoreCase("more information y/n")){
                String[] yes_no = {"yes", "No!"};
                Random random = new Random();//the server chooses randomly if it wants more information or to end the process. Warning this may lead to an infinite loop
                int randomNumber =random.nextInt(yes_no.length);

                if (yes_no[randomNumber].equalsIgnoreCase("No!")){
                    theOutput = yes_no[randomNumber] + " What is the unique identification code?";
                }
//                else{
//                    theOutput = "Knock! Knock!";
//                    state = SENTKNOCKKNOCK;
//
//                }
            }


        }else if (state == RECEIVEALL){
            String[] msg_string = theInput.split(" "); //put words of the input string in an array
            if (msg_string[0].equalsIgnoreCase("Thank")){
                theOutput = "Bye.";
                state = WAITING;
            }

        }
        return theOutput;
    }


}