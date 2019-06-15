import java.util.Random;

public class ClientProtocol {

    private String toyName, descrription, company, street, zipCode, country, date_of_manufacture;
    private int toy_code, price, batchNumber;
    private static final int WAITING = 0;
    private static final int SENTREPLY = 2;
    private static final int ANOTHER = 3;

    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;


    public ClientProtocol(String toyName, String descrription, String company, String street,
                          String zipCode, String country, int toy_code, int price, int batchNumber, String date_of_manufacture) {
        this.toyName = toyName;
        this.descrription = descrription;
        this.company = company;
        this.street = street;
        this.zipCode = zipCode;
        this.country = country;
        this.toy_code = toy_code;
        this.price = price;
        this.batchNumber = batchNumber;
        this.date_of_manufacture = date_of_manufacture;
    }

    public String clientProcessInput(String theInput) {
        String theOutput = null;
        if (theInput.equalsIgnoreCase("All Toy information")){
            theOutput = getAllToYInformation();
        }else if (theInput.equalsIgnoreCase("Toy identification details")){
            theOutput = " Toy identification details\n Toy code: " + getToy_code() + "\nName: " +getToyName();
        }else if (theInput.equalsIgnoreCase("Toy information details")){
            theOutput = "Toy information\n Name: " +getToyName()+ "\nDescription : " +getDescrription() +
                    "\nPrice: " + getPrice() + "\nDate of manufacture:"+ getDate_of_manufacture() +"\nBatch number:" +getBatchNumber();
        }else if (theInput.equalsIgnoreCase("Manufacturer information")){
            theOutput = "Manufacturer information\n Company: " +getCompany() + "\nStreet: " +getStreet() + "\nZip code: " +getZipCode() +"\nCountry: " +getCountry();
        } else if (theInput.equalsIgnoreCase("false")){
            theOutput = "No";
        }
        else if (theInput.equalsIgnoreCase("true")) {
            theOutput = "Yes";
        }else if (theInput.contains("unique identification code")){
            theOutput = "Thank you! The unique identification code is "+ generateUniqueCode();
        }
        else{

            theOutput = theInput;
        }

        return theOutput;
    }


    /**
     * return all toy information
     * @return
     */
    public String getAllToYInformation(){
        return  "Details:  Toy code: " + getToy_code() + "\nName: " +getToyName()+ "\nDescription : " +getDescrription() +
                "\nPrice: " + getPrice() + "\nDate of manufacture: "+ getDate_of_manufacture() +"\nBatch number: " +getBatchNumber() +
                "\nCompany: " +getCompany() + "\nStreet: " +getStreet() + "\nZip code: " +getZipCode() +"\nCountry: " +getCountry();

    }

    /**
     * generate a unique code for the toy
     * @return
     */
    public int generateUniqueCode(){
        int maximum = 1000;
        int minimum = 1 ;
        Random rn = new Random();
        int range = maximum - minimum + 1;
        int randomNum =  rn.nextInt(range) + minimum;
        return randomNum;
    }
    public String getToyName() {
        return toyName;
    }

    public String getDescrription() {
        return descrription;
    }

    public String getCompany() {
        return company;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public int getToy_code() {
        return toy_code;
    }

    public int getPrice() {
        return price;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public String getDate_of_manufacture() {
        return date_of_manufacture;
    }
}