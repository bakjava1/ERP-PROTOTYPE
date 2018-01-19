package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.entity.*;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class Validator implements ValidateInput{

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void isValidDate(String inDate) throws InvalidInputException {

        //case not set cause assignment getting created
        if (inDate == null)
            return;

        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            //parse the inDate parameter
            Date test = dateFormat.parse(inDate);
            if(!test.before(new Date())) {
                throw new InvalidInputException("Impossible Date");
            }
        }
        catch (ParseException pe) {
            throw new InvalidInputException("Date is not in correct format!");
        }
    }

    public int validateNumber(String toValidate,int size) throws NoValidIntegerException {
        int num;
        if(toValidate == null || toValidate.length() == 0) {
            throw new NoValidIntegerException("Empty Field, No Number entered");
        }
        try {
            num = Integer.parseInt(toValidate);
            if (num <= 0) {
                throw new NoValidIntegerException("Negative Integer or Null entered");
            }
            if(num > size && size != -1) {
                throw new NoValidIntegerException("Value entered was too big! Enter Value < " + size);
            }
        } catch (NumberFormatException e) {
            LOG.error("No valid Integer entered");
            throw new NoValidIntegerException("No valid Integer entered");
        }
        return num;
    }

    private void validateText(String toValidate, int length) throws EmptyInputException {
        if(toValidate == null || toValidate.length() == 0) {
            throw new EmptyInputException("Empty Field");
        }
        if(toValidate.length() > length && length != -1) {
            throw new EmptyInputException("Input was too long! Enter Input which is max. " + length + " long");
        }
    }

    private void isNumber(int toCheck,int limit) throws NoValidIntegerException {
        if (toCheck < 0) {
            throw new NoValidIntegerException("Negative Integer entered");
        }
        if(toCheck > limit && limit != -1) {
            throw new NoValidIntegerException("Integer entered too big! Value must be < " + limit);
        }
    }

    public int[] temporaryAddTaskToLumberValidation(String id, String amount) throws InvalidInputException {
        int[] result = new int[2];
        int validatedId;
        try {
            validatedId = validateNumber(id,Integer.MAX_VALUE);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Id: " + e.getMessage());
            throw new InvalidInputException("Error at Id: " + e.getMessage());
        }
        result[0] = validatedId;
        int validatedAmount;
        try {
            validatedAmount = validateNumber(amount,Integer.MAX_VALUE);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Amount: " + e.getMessage());
            throw new InvalidInputException("Error at Amount: " + e.getMessage());
        }
        result[1] = validatedAmount;
        return result;
    }

    public void inputValidationOrder(Order toValidate) throws InvalidInputException {

        try {
            isNumber(toValidate.getID(),-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Order Id: "+ e.getMessage());
            throw new InvalidInputException("Error in Order Id: " + e.getMessage());
        }

        try {
            validateText(toValidate.getCustomerName(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Name: " + e.getMessage());
            throw new InvalidInputException("Error at Customer Name: " + e.getMessage());
        }

        try {
            validateText(toValidate.getCustomerAddress(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Address: " + e.getMessage());
            throw new InvalidInputException("Error at Customer Address: " + e.getMessage());
        }

        try {
            validateText(toValidate.getCustomerUID(),20);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer UID: " + e.getMessage());
            throw new InvalidInputException("Error at Customer UID: " + e.getMessage());
        }

     /*   try {
            isValidDate(toValidate.getOrderDate().toString());
        } catch(InvalidInputException e) {
            LOG.error("Error at Order Date: " + e.getMessage());
            throw new InvalidInputException("Error at Order Date: " + e.getMessage());
        }*/

        if(toValidate.getTaskList() == null) {
            LOG.error("Error at Task List: List cannot be null");
            throw new InvalidInputException("Error at Order Date: List cannot be null");
        }
    }

    public void inputValidationInvoice(Order invoice) throws InvalidInputException{
        inputValidationOrder(invoice);
        if(invoice.getTaskList()==null || invoice.getTaskList().size()==0){
            LOG.error("Error in Invoice: No Tasks");
            throw new InvalidInputException("Error in Invoice: No Tasks");
        }
        if(invoice.getOrderDate() == null){
            LOG.error("Error in Invoice: No Order Date");
            throw new InvalidInputException("Error in Invoice: No Order Date");
        }
        if(invoice.getGrossAmount()<0){
            LOG.error("Error in Invoice: Gross Amount is negative");
            throw new InvalidInputException("Error in Invoice: Gross Amount is negative");
        }
        if(invoice.getTaxAmount()<0){
            LOG.error("Error in Invoice: Gross Amount is negative");
            throw new InvalidInputException("Error in Invoice: Gross Amount is negative");
        }
        if(invoice.getNetAmount()<0){
            LOG.error("Error in Invoice: Net Amount is negative");
            throw new InvalidInputException("Error in Invoice: Net Amount is negative");
        }
    }

    public void inputValidationTaskOnOrder(Task toValidate) throws InvalidInputException {
        try {
            validateText(toValidate.getDescription(), -1);
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Description: " + e.getMessage());
            throw new InvalidInputException("Error at Task Description: " + e.getMessage());
        }
        try {
            validateText(toValidate.getFinishing(), -1);
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Finishing: " + e.getMessage());
            throw new InvalidInputException("Error at Task Finishing: " + e.getMessage());
        }
        try {
            validateText(toValidate.getWood_type(), -1);
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Wood Type: " + e.getMessage());
            throw new InvalidInputException("Error at Task Wood Type: " + e.getMessage());
        }
        try {
            validateText(toValidate.getQuality(), -1);
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Quality: " + e.getMessage());
            throw new InvalidInputException("Error at Task Quality: " + e.getMessage());
        }
        if(toValidate.getSize()<=0){LOG.error("Error at Task Size: Size <= 0");
            throw new InvalidInputException("Error at Task Size: Size <= 0");
        }
        if(toValidate.getWidth()<=0){LOG.error("Error at Task Width: Width <= 0");
            throw new InvalidInputException("Error at Task Width: Width <= 0");
        }
        if(toValidate.getLength()<=0){LOG.error("Error at Task Length: Length <= 0");
            throw new InvalidInputException("Error at Task Length: Length <= 0");
        }
        if(toValidate.getQuantity()<=0){LOG.error("Error at Task Quantity: Quantity <= 0");
            throw new InvalidInputException("Error at Task Quantity: Quantity <= 0");
        }
        if(toValidate.getPrice()<=0){LOG.error("Error at Task Price: Price <= 0");
            throw new InvalidInputException("Error at Task Price: Price <= 0");
        }
    }

    public Task inputValidationTask(UnvalidatedTask toValidate) throws InvalidInputException {
        try {
            validateText(toValidate.getDescription(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Description: " + e.getMessage());
            throw new InvalidInputException("Error at Description: " + e.getMessage());
        }

        try {
            validateText(toValidate.getFinishing(),15);
            if(!toValidate.getFinishing().equals("roh") && !toValidate.getFinishing().equals("gehobelt") && !toValidate.getFinishing().equals("besäumt")
                    && !toValidate.getFinishing().equals("prismiert") && !toValidate.getFinishing().equals("trocken") && !toValidate.getFinishing().equals("lutro")
                    && !toValidate.getFinishing().equals("frisch") && !toValidate.getFinishing().equals("imprägniert")) {
                LOG.error("Error at Finishing: Unknown Finishing");
                throw new InvalidInputException("Error at Finishing: Unknown Finishing");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Finishing: " + e.getMessage());
            throw new InvalidInputException("Error at Finishing: " + e.getMessage());
        }

        try {
            validateText(toValidate.getWood_type(),10);
            if(!toValidate.getWood_type().equals("Fi") && !toValidate.getWood_type().equals("Ta") && !toValidate.getWood_type().equals("Lae")) {
                LOG.error("Error at Wood Type: Unknown Wood Type");
                throw new InvalidInputException("Error at Wood Type: Unknown Wood Type");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Wood Type: " + e.getMessage());
            throw new InvalidInputException("Error at Wood Type: " + e.getMessage());
        }

        try {
            validateText(toValidate.getQuality(),10);
            if(!toValidate.getQuality().equals("O") && !toValidate.getQuality().equals("I") && !toValidate.getQuality().equals("II") &&
                    !toValidate.getQuality().equals("III") && !toValidate.getQuality().equals("IV") && !toValidate.getQuality().equals("V") &&
                    !toValidate.getQuality().equals("O/III") && !toValidate.getQuality().equals("III/IV") && !toValidate.getQuality().equals("III/V") ) {
                LOG.error("Error at Quality: Unknown Quality");
                throw new InvalidInputException("Error at Quality: Unknown Quality");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Quality: " + e.getMessage());
            throw new InvalidInputException("Error at Quality: " + e.getMessage());
        }
        int validatedSize;
        try {
            validatedSize = validateNumber(toValidate.getSize(),1000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Size: " + e.getMessage());
            throw new InvalidInputException("Error at Size: " + e.getMessage());
        }
        int validatedWidth;
        try {
            validatedWidth = validateNumber(toValidate.getWidth(),1000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Width: " + e.getMessage());
            throw new InvalidInputException("Error at Width: " + e.getMessage());
        }
        int validatedLength;
        try {
            validatedLength = validateNumber(toValidate.getLength(),5000);
            if(validatedLength != 3500 && validatedLength != 4000 && validatedLength != 4500 && validatedLength != 5000) {
                LOG.error("No producable Length!");
                throw new InvalidInputException("Please enter a producable Length! (3500,4000,4500,5000");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Length: " + e.getMessage());
            throw new InvalidInputException("Error at Length: " + e.getMessage());
        }
        int validatedQuantity;
        try {
            validatedQuantity = validateNumber(toValidate.getQuantity(),1000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Quantity: " + e.getMessage());
            throw new InvalidInputException("Error at Quantity: " + e.getMessage());
        }
        int validatedPrice;
        try {
            validatedPrice = validateNumber(toValidate.getPrice(),10000000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Price: " + e.getMessage());
            throw new InvalidInputException("Error at Price: " + e.getMessage());
        }

        return new Task(-1,-1,toValidate.getDescription(),
                toValidate.getFinishing(),toValidate.getWood_type(),
                toValidate.getQuality(),validatedSize,validatedWidth,
                validatedLength,validatedQuantity,0,
                false,validatedPrice);

    }

    public FilterDTO validateFilter(FilterDTO filter) throws InvalidInputException{

        String description = filter.getDescription().trim();
        String finishing = filter.getFinishing().trim().equals("keine Angabe")? "" :
                filter.getFinishing().trim();
        String wood_type = filter.getWood_type().trim().equals("keine Angabe")? "" :
                filter.getWood_type().trim();
        String quality = filter.getQuality().trim().equals("keine Angabe")? "" :
                filter.getQuality().trim();
        String strength = filter.getSize().trim();
        String width = filter.getWidth().trim();
        String length = filter.getLength().trim();

        FilterDTO validatedFilter = new FilterDTO();

        try {
            if(!description.equals("")) {
                validateText(description, 50);
                validatedFilter.setDescription(description);
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Description: " + e.getMessage());
            throw new InvalidInputException("Error at Description: " + e.getMessage());
        }

        try {
            if(!finishing.equals("")) {
                validateText(finishing, 30);
                if (!finishing.equals("roh") && !finishing.equals("gehobelt") && !finishing.equals("besäumt")
                        && !finishing.equals("prismiert") && !finishing.equals("trocken") && !finishing.equals("lutro")
                        && !finishing.equals("frisch") && !finishing.equals("imprägniert")) {
                    LOG.error("Error at Finishing: Unknown Finishing");
                    throw new InvalidInputException("Error at Finishing: Unknown Finishing");
                }
                validatedFilter.setFinishing(finishing);
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Finishing: " + e.getMessage());
            throw new InvalidInputException("Error at Finishing: " + e.getMessage());
        }

        try {
            if(!wood_type.equals("")) {
                validateText(wood_type, 20);
                if (!wood_type.equals("Fi") && !wood_type.equals("Ta") && !wood_type.equals("Lae")) {
                    LOG.error("Error at Wood Type: Unknown Wood Type");
                    throw new InvalidInputException("Error at Wood Type: Unknown Wood Type");
                }
                validatedFilter.setWood_type(wood_type);
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Wood Type: " + e.getMessage());
            throw new InvalidInputException("Error at Wood Type: " + e.getMessage());
        }

        try {
            if(!quality.equals("")) {
                validateText(quality, 20);
                if (!quality.equals("O") && !quality.equals("I") && !quality.equals("II") &&
                        !quality.equals("III") && !quality.equals("IV") && !quality.equals("V") &&
                        !quality.equals("O/III") && !quality.equals("III/IV") && !quality.equals("III/V")) {
                    LOG.error("Error at Quality: Unknown Quality");
                    throw new InvalidInputException("Error at Quality: Unknown Quality");
                }
                validatedFilter.setQuality(quality);
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Quality: " + e.getMessage());
            throw new InvalidInputException("Error at Quality: " + e.getMessage());
        }


        try {
            if(strength.equals("")) { validatedFilter.setSize(null); }
            else {

                int validatedStrength = validateNumber(strength, 1000);
                validatedFilter.setSize(validatedStrength+"");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Size: " + e.getMessage());
            throw new InvalidInputException("Error at Size: " + e.getMessage());
        }

        try {
            if(width.equals("")) { validatedFilter.setWidth(null); }
            else {
                int validatedWidth = validateNumber(width, 1000);
                validatedFilter.setWidth(validatedWidth+"");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Width: " + e.getMessage());
            throw new InvalidInputException("Error at Width: " + e.getMessage());
        }

        try {
            if(length.equals("")) { validatedFilter.setLength(null); }
            else {
                int validatedLength = validateNumber(length, 5000);
                if (validatedLength != 3500 && validatedLength != 4000 && validatedLength != 4500 && validatedLength != 5000) {
                    throw new InvalidInputException("Please enter a producable Length! (3500,4000,4500,5000");
                }
                validatedFilter.setLength(validatedLength+"");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Length: " + e.getMessage());
            throw new InvalidInputException("Error at Length: " + e.getMessage());
        }

        return validatedFilter;

    }


    @Override
    public boolean isValid(Object input) throws InvalidInputException {
        return false;
    }
}
