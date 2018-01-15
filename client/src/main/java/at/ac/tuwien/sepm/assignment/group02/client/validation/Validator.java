package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.entity.*;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class Validator {

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
            validateText(toValidate.getCustomerUID(),10);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer UID: " + e.getMessage());
            throw new InvalidInputException("Error at Customer UID: " + e.getMessage());
        }
    }

    public void inputValidationInvoice(Order invoice) throws InvalidInputException{
        inputValidationOrder(invoice);
        if(invoice.getTaskList()==null || invoice.getTaskList().size()==0){
            LOG.error("Error in Invoice: No Tasks");
            throw new InvalidInputException("Error in Invoice: No Tasks");
        }
        if(invoice.getDeliveryDate() == null){
            LOG.error("Error in Invoice: No Delivery Date");
            throw new InvalidInputException("Error in Invoice: No Delivery Date");
        }
        if(invoice.getInvoiceDate() == null){
            LOG.error("Error in Invoice: No Invoice Date");
            throw new InvalidInputException("Error in Invoice: No Invoice Date");
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
        }catch(EmptyInputException e) {
            LOG.error("Error at Finishing: " + e.getMessage());
            throw new InvalidInputException("Error at Finishing: " + e.getMessage());
        }
        try {
            validateText(toValidate.getWood_type(),10);
        }catch(EmptyInputException e) {
            LOG.error("Error at Wood Type: " + e.getMessage());
            throw new InvalidInputException("Error at Wood Type: " + e.getMessage());
        }
        try {
            validateText(toValidate.getQuality(),10);
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
            if(num > size) {
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


    public Lumber validateLumber(UnvalidatedLumber filter) throws InvalidInputException{

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

        Lumber validatedLumber = new Lumber();

        if (!description.equals("")){
            validatedLumber.setDescription(description);
        }
        if (!finishing.equals("")){
            validatedLumber.setFinishing(finishing);
        }

        if (!wood_type.equals("")){
            validatedLumber.setWood_type(wood_type);
        }

        if (!quality.equals("")){
            validatedLumber.setQuality(quality);
        }

        if (!strength.equals("")){
            if (strength.matches("^\\d+$")) {
                if (strength.length() < 10) {
                    validatedLumber.setSize(Integer.parseInt(strength));
                }else{
                    throw new InvalidInputException("Die angegebene Stärke ist zu groß.");
                }
            }
            else{
                throw new InvalidInputException("Die angegebene Stärke ist keine positive ganze Zahl.");
            }
        }else{
            validatedLumber.setSize(-1);
        }

        if (!width.equals("")){
            if (width.matches("^\\d+$")) {
                if (width.length() < 10) {
                    validatedLumber.setWidth(Integer.parseInt(width));
                }else{
                    throw new InvalidInputException("Die angegebene Breite ist zu groß.");
                }
            }else{
                throw new InvalidInputException("Die angegebene Breite ist keine positive ganze Zahl.");
            }
        }else{
            validatedLumber.setWidth(-1);
        }

        if (!length.equals("")){
            if (length.matches("^\\d+$")) {
                if (length.length() < 10) {
                    validatedLumber.setLength(Integer.parseInt(length));
                }else{
                    throw new InvalidInputException("Die angegebene Länge ist zu groß.");
                }
            }else{
                throw new InvalidInputException("Die angegebene Länge ist keine positive ganze Zahl.");
            }
        }else{
            validatedLumber.setLength(-1);
        }

        return validatedLumber;

    }


}
