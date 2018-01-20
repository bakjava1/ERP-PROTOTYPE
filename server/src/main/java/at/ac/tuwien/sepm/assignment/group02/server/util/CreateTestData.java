package at.ac.tuwien.sepm.assignment.group02.server.util;

import com.github.javafaker.Faker;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateTestData {

    //timber table
    private final String TIMBER_TABLE = "TIMBER";
    private final String TIMBER_ID = "ID"; //int
    private final String TIMBER_WOOD_TYPE = "WOOD_TYPE"; //varchar(20)
    private final String TIMBER_FESTMETER = "FESTMETER"; //double
    private final String TIMBER_AMOUNT = "AMOUNT"; //int
    private final String TIMBER_MAX_AMOUNT = "MAX_AMOUNT"; //int
    private final String TIMBER_LENGTH = "LENGTH"; //int
    private final String TIMBER_QUALITY = "QUALITY"; //varchar(10)
    private final String TIMBER_DIAMETER = "DIAMETER"; //int
    private final String TIMBER_PRICE = "PRICE"; //int
    private final String TIMBER_LAST_EDITED = "LAST_EDITED"; //timestamp

    //lumber table
    private final String LUMBER_TABLE = "LUMBER";
    private final String LUMBER_ID = "ID"; //int
    private final String LUMBER_DESCRIPTION = "DESCRIPTION"; //varchar(50)
    private final String LUMBER_FINISHING = "FINISHING"; //varchar(30)
    private final String LUMBER_WOOD_TYPE = "WOOD_TYPE"; //varchar(20)
    private final String LUMBER_QUALITY = "QUALITY"; //varchar(20)
    private final String LUMBER_SIZE = "SIZE"; //int
    private final String LUMBER_WIDTH = "WIDTH"; //int
    private final String LUMBER_LENGTH = "LENGTH"; //int
    private final String LUMBER_QUANTITY = "QUANTITY"; //int
    private final String LUMBER_RESERVED_QUANTITY = "RESERVED_QUANTITY"; //int

    //order table
    private final String ORDER_TABLE = "ORDERS";
    private final String ORDER_ID = "ID"; //int
    private final String ORDER_CUSTOMER_NAME = "CUSTOMER_NAME"; //varchar(50)
    private final String ORDER_CUSTOMER_ADDRESS = "CUSTOMER_ADDRESS"; //varchar(50)
    private final String ORDER_CUSTOMER_UID = "CUSTOMER_UID"; //varchar(20)
    private final String ORDER_ORDER_DATE = "ORDER_DATE"; //datetime
    private final String ORDER_DELIVERY_DATE = "DELIVERY_DATE"; //timestamp
    private final String ORDER_INVOICE_DATE = "INVOICE_DATE"; //timestamp
    private final String ORDER_GROSS_AMOUNT = "GROSS_AMOUNT"; //int
    private final String ORDER_NET_AMOUNT = "NET_AMOUNT"; //int
    private final String ORDER_TAX_AMOUNT = "TAX_AMOUNT"; //int
    private final String ORDER_ISPAIDFLAG = "ISPAIDFLAG"; //boolean
    private final String ORDER_ISDONEFLAG = "ISDONEFLAG"; //boolean

    //task table
    private final String TASK_TABLE = "TASK";
    private final String TASK_ID = "ID"; //int
    private final String TASK_ORDER_ID = "ORDERID"; //int foreign key references ORDER_ID
    private final String TASK_DESCRIPTION = "DESCRIPTION"; //varchar(50)
    private final String TASK_FINISHING = "FINISHING"; //varchar(15);
    private final String TASK_WOOD_TYPE = "WOOD_TYPE"; //varchar(10);
    private final String TASK_QUALITY = "QUALITY"; //varchar(10)
    private final String TASK_SIZE = "SIZE"; //int
    private final String TASK_WIDTH = "WIDTH"; //int
    private final String TASK_LENGTH = "LENGTH"; //int
    private final String TASK_QUANTITY = "QUANTITY"; //int
    private final String TASK_PRODUCED_QUANTITY = "PRODUCED_QUANTITY"; //int
    private final String TASK_PRICE = "PRICE"; //int
    private final String TASK_DONE = "DONE"; //boolean
    private final String TASK_IN_PROGRESS = "IN_PROGRESS"; //bit
    private final String TASK_DELETED = "DELETED"; //boolean

    //assignment table
    private final String ASSIGNMENT_TABLE = "ASSIGNMENT";
    private final String ASSIGNMENT_ID = "ID"; //int
    private final String ASSIGNMENT_CREATION_DATE = "CREATION_DATE"; //datetime
    private final String ASSIGNMENT_AMOUNT = "AMOUNT"; //int
    private final String ASSIGNMENT_BOX_ID = "BOX_ID"; //int references TIMBER_ID
    private final String ASSIGNMENT_ISDONE = "ISDONE"; //boolean
    private final String ASSIGNMENT_TASK_ID = "TASK_ID"; //int foreign key references TASK_ID

    private Faker faker;

    private PrintWriter writer;

    private String[] descriptionLumber = new String[]{"Latten", "Kantholz", "Balken", "Dielen"};

    private static final int numOfOpenOrders = 150;
    private static final int numOfClosedOrders = 450;
    private String[] qualityLumber = new String[]{"O", "I", "II", "III", "IV", "V", "O/III", "III/IV", "III/V"};
    private int[] lengthLumber = new int[]{3500, 4000, 4500, 5000 };
    private String[] woodTypeLumber = new String[]{"Fi", "Ta", "Lae"};
    private String[] finishingLumber = new String[]{"roh", "gehobelt", "besäumt", "prismiert", "trocken", "lutro", "frisch", "imprägniert"};



    CreateTestData() {
        faker = new Faker();
        try {
            //writer = new PrintWriter("server/src/main/resources/insertFinalData.sql");
            writer = new PrintWriter("server/src/main/resources/testData.sql");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * VERY IMPORTANT: ADD ';' TO EVERY END OF COMMAND
     */
    public static void main(String[] args) {
        CreateTestData createTestData = new CreateTestData();

        createTestData.writer.println();
        createTestData.writer.println();

        for(int i = 0; i < numOfOpenOrders; i++){
            createTestData.insertOrder(i);
        }
        createTestData.writer.println();
        createTestData.writer.println();

        for(int i = numOfOpenOrders; i < (numOfOpenOrders+numOfClosedOrders); i++){
            createTestData.insertInvoice(i);
        }
        createTestData.closeWriter();
    }

    private void closeWriter(){
        writer.close();
    }


    private void insertOrder(int orderID){
        String command = "INSERT INTO "+ORDER_TABLE+"("+ORDER_CUSTOMER_NAME+", "+ORDER_CUSTOMER_ADDRESS+", "+ORDER_CUSTOMER_UID+", "+ORDER_ORDER_DATE+", "
                +ORDER_ISPAIDFLAG+", "+ORDER_ISDONEFLAG+") VALUES (";

        String customerName = faker.name().name();
        customerName = customerName.replace('\'','\u0000');
        command += "'"+customerName+"', ";

        String address = faker.address().streetAddress()+ " " + faker.address().buildingNumber();
        address = address.replace('\'','\u0000');
        command += "'"+address+"', ";

        String uid = "ATU" + faker.number().numberBetween(0, 56861421);
        command += "'"+uid+"', ";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderDate = format.format(faker.date().between(new Date(50000), new Date()));
        command += "'"+orderDate+"', ";

        command += "false, false";

        writer.println(command + ");");
        for(int i = 0; i<faker.number().numberBetween(1,7);i++){
            insertTaskForOrder(orderID+1);
        }
    }

    private void insertInvoice(int orderID){
        String command = "INSERT INTO "+ORDER_TABLE+"("+ORDER_CUSTOMER_NAME+", "+ORDER_CUSTOMER_ADDRESS+", "+ORDER_CUSTOMER_UID+", "+ORDER_ORDER_DATE+", "
                +ORDER_DELIVERY_DATE+", "+ORDER_INVOICE_DATE+", "+ORDER_GROSS_AMOUNT+", "+ORDER_NET_AMOUNT+", "+ORDER_TAX_AMOUNT+", "
                +ORDER_ISPAIDFLAG+", "+ORDER_ISDONEFLAG+") VALUES (";

        String customerName = faker.name().name();
        customerName = customerName.replace('\'','\u0000');
        command += "'"+customerName+"', ";

        String address = faker.address().streetAddress()+ " " + faker.address().buildingNumber();
        address = address.replace('\'','\u0000');
        command += "'"+address+"', ";

        String uid = "ATU" + faker.number().numberBetween(0, 56861421);
        command += "'"+uid+"', ";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date orderDate = faker.date().between(new Date(50000), new Date());
        command += "'"+format.format(orderDate)+"', ";

        Date deliveryDate = faker.date().between(orderDate, new Date());
        command += "'"+format.format(deliveryDate)+"', '"+format.format(deliveryDate)+"', ";

        int netAmount = faker.number().numberBetween(1000, 50000);
        int taxAmount = netAmount*(20/100);
        int grossAmount = netAmount+taxAmount;
        command += grossAmount+", "+netAmount+", "+taxAmount+", ";

        //ispaid and !isDone
        command += "true, false";

        writer.println(command + ");");
        for(int i = 0; i<faker.number().numberBetween(1,7);i++){
            insertTaskForInvoice(orderID+1);
        }
    }

    private void insertTaskForInvoice(int orderID){
        String command = "INSERT INTO "+TASK_TABLE+"("+TASK_ORDER_ID+", "+TASK_DESCRIPTION+", "+TASK_FINISHING+", "+TASK_WOOD_TYPE+", "+TASK_QUALITY+", "+TASK_SIZE+", "
                +TASK_WIDTH+", "+TASK_LENGTH+", "+TASK_QUANTITY+", "+TASK_PRODUCED_QUANTITY+", "+TASK_PRICE+", "+TASK_DONE+", "+TASK_IN_PROGRESS+", "+TASK_DELETED+") VALUES (";

        command+=orderID+", ";

        //description
        String description = descriptionLumber[faker.number().numberBetween(0,descriptionLumber.length)];
        command += "'"+description+"', ";

        //finishing
        String finishing = finishingLumber[faker.number().numberBetween(0,finishingLumber.length)];
        command += "'"+finishing+"', ";

        //wood type
        String woodType = woodTypeLumber[faker.number().numberBetween(0,woodTypeLumber.length)];
        command += "'"+woodType+"', ";

        //quality
        String quality = qualityLumber[faker.number().numberBetween(0,qualityLumber.length)];
        command += "'"+quality+"', ";

        int size = faker.number().numberBetween(20, 26);
        int width = faker.number().numberBetween(40, 46);
        command += size + ", " + width + ", ";

        //length
        int length = lengthLumber[faker.number().numberBetween(0, lengthLumber.length)];
        command += length+", ";

        //quantity
        int quantity = faker.number().numberBetween(2, 42);
        command += quantity + ", ";

        //produced quantity
        int producedQuantity = quantity;
        command += producedQuantity+ ", ";

        //price
        int price = faker.number().numberBetween(100, 400)*quantity;
        command += price+ ", ";

        //done, !inProgress, !deleted
        command += "true, false, false";

        writer.println(command+");");
        insertLumberForTask(description, finishing, woodType, quality, size, width, length, producedQuantity);

    }

    private void insertTaskForOrder(int orderID){
        String command = "INSERT INTO "+TASK_TABLE+"("+TASK_ORDER_ID+", "+TASK_DESCRIPTION+", "+TASK_FINISHING+", "+TASK_WOOD_TYPE+", "+TASK_QUALITY+", "+TASK_SIZE+", "
                +TASK_WIDTH+", "+TASK_LENGTH+", "+TASK_QUANTITY+", "+TASK_PRODUCED_QUANTITY+", "+TASK_PRICE+", "+TASK_DONE+", "+TASK_IN_PROGRESS+", "+TASK_DELETED+") VALUES (";

        command+=orderID+", ";

        //description
        String description = descriptionLumber[faker.number().numberBetween(0,descriptionLumber.length)];
        command += "'"+description+"', ";

        //finishing
        String finishing = finishingLumber[faker.number().numberBetween(0,finishingLumber.length)];
        command += "'"+finishing+"', ";

        //wood type
        String woodType = woodTypeLumber[faker.number().numberBetween(0,woodTypeLumber.length)];
        command += "'"+woodType+"', ";

        //quality
        String quality = qualityLumber[faker.number().numberBetween(0,qualityLumber.length)];
        command += "'"+quality+"', ";

        //1 = small, 2 = medium, 3 = big
        //comment because too many different types --> cannot find lumber when trying to reserve task
        int[] randomSize = new int[]{1, 2, 3};
        int rand = randomSize[faker.number().numberBetween(0, 3)];

            //small size and width
            int size = faker.number().numberBetween(18, 40);
            int width = faker.number().numberBetween(42, 60);

        if(rand == 2){
            //medium size and width
            size = faker.number().numberBetween(30, 60);
            width = faker.number().numberBetween(80, 180);
        }
        else if(rand == 3){
            //big size and with
            size = faker.number().numberBetween(200, 300);
            width = faker.number().numberBetween(250, 400);
        }

        command += size + ", " + width + ", ";
//        int size = faker.number().numberBetween(20, 26);
//        int width = faker.number().numberBetween(40, 46);
//        command += size + ", " + width + ", ";

        //length
        int length = lengthLumber[faker.number().numberBetween(0, lengthLumber.length)];
        command += length+", ";

        //quantity
        int quantity = faker.number().numberBetween(20, 100);
        command += quantity + ", ";

        //produced quantity
        int producedQuantity = faker.number().numberBetween(0, quantity);
        command += producedQuantity+ ", ";

        //price
        int price = faker.number().numberBetween(100, 400)*quantity;
        command += price+ ", ";

        //done
        if(quantity == producedQuantity){
            command += "true, ";
        }
        else if(quantity != producedQuantity){
            command += "false, ";
        }

        //in progress
        boolean inProgress = false;
        //probability of 80 % task is in progress
/*        if(faker.number().numberBetween(0, 100)>90){
            inProgress = true;
        }*/

        command += inProgress+", false";

        writer.println(command+");");

        insertLumberForTask(description, finishing, woodType, quality, size, width, length, producedQuantity);
    }

    private void insertLumberForTask(String description, String finishing, String woodType, String quality, int size, int width, int length, int reservedQuantity){
        String command = "INSERT INTO " + LUMBER_TABLE+"("+LUMBER_DESCRIPTION+", "+LUMBER_FINISHING+", "+LUMBER_WOOD_TYPE+", "+LUMBER_QUALITY+", "+LUMBER_SIZE+", "+LUMBER_WIDTH+", "
                +LUMBER_LENGTH+", "+LUMBER_QUANTITY+", "+LUMBER_RESERVED_QUANTITY+") VALUES (";

        //add description
        command += "'"+description +"', ";

        //finishing
        command += "'"+finishing+"', ";

        //wood type
        command += "'"+woodType+"', ";

        //quality
        command += "'"+quality+"', ";

        command += size + ", " + width + ", ";

        //length
        command += length+", ";


        //reserved quantity is produced quantity from task
        //quantity is reserved
        int quantity = reservedQuantity * faker.number().numberBetween(12, 20);
        command += quantity + ", " +reservedQuantity;

        writer.println(command + ");");
    }

    private void insertLumber(String description){
        String command = "INSERT INTO " + LUMBER_TABLE+"("+LUMBER_DESCRIPTION+", "+LUMBER_FINISHING+", "+LUMBER_WOOD_TYPE+", "+LUMBER_QUALITY+", "+LUMBER_SIZE+", "+LUMBER_WIDTH+", "
                +LUMBER_LENGTH+", "+LUMBER_QUANTITY+", "+LUMBER_RESERVED_QUANTITY+") VALUES (";

        //add description
        command += "'"+description +"', ";

        //finishing
        String finishing = finishingLumber[faker.number().numberBetween(0,finishingLumber.length)];
        command += "'"+finishing+"', ";

        //wood type
        String woodType = woodTypeLumber[faker.number().numberBetween(0,woodTypeLumber.length)];
        command += "'"+woodType+"', ";

        //quality
        String quality = qualityLumber[faker.number().numberBetween(0,qualityLumber.length)];
        command += "'"+quality+"', ";

        //1 = small, 2 = medium, 3 = big
//        int[] randomSize = new int[]{1, 2, 3};
//        int rand = randomSize[faker.number().numberBetween(0, 3)];
//        if(rand == 1){
//            //small size and width
//            int size = faker.number().numberBetween(18, 40);
//            int width = faker.number().numberBetween(42, 60);
//            command += size + ", " + width + ", ";
//
//        }
//        else if(rand == 2){
//            //medium size and width
//            int size = faker.number().numberBetween(30, 60);
//            int width = faker.number().numberBetween(80, 180);
//            command += size + ", " + width + ", ";
//        }
//        else if(rand == 3){
//            //big size and with
//            int size = faker.number().numberBetween(200, 300);
//            int width = faker.number().numberBetween(250, 400);
//            command += size + ", " + width + ", ";
//        }

        int size = faker.number().numberBetween(20, 26);
        int width = faker.number().numberBetween(40, 46);
        command += size + ", " + width + ", ";

        //length
        int length = lengthLumber[faker.number().numberBetween(0, lengthLumber.length)];
        command += length+", ";

        //quantity
        int quantity = faker.number().numberBetween(40, 500);
        command += quantity + ", ";

        //reserved quantity
        int reservedQuantity = faker.number().numberBetween(0, quantity);
        command += reservedQuantity;

        writer.println(command + ");");
    }

}
