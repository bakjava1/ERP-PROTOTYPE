package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import io.swagger.models.auth.In;
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
            validatedId = validateNumber(id);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Id: " + e.getMessage());
            throw new InvalidInputException("Error at Id: " + e.getMessage());
        }
        result[0] = validatedId;
        int validatedAmount;
        try {
            validatedAmount = validateNumber(amount);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Amount: " + e.getMessage());
            throw new InvalidInputException("Error at Amount: " + e.getMessage());
        }
        result[1] = validatedAmount;
        return result;
    }

    public void inputValidationOrder(Order toValidate) throws InvalidInputException {
        try {
            validateText(toValidate.getCustomerName());
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Name: " + e.getMessage());
            throw new InvalidInputException("Error at Customer Name: " + e.getMessage());
        }
        try {
            validateText(toValidate.getCustomerAddress());
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Address: " + e.getMessage());
            throw new InvalidInputException("Error at Customer Address: " + e.getMessage());
        }
        try {
            validateText(toValidate.getCustomerUID());
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer UID: " + e.getMessage());
            throw new InvalidInputException("Error at Customer UID: " + e.getMessage());
        }
        //TODO maybe not working anymore --> if statement is new
        if(toValidate.getTaskList() == null || toValidate.getTaskList().size()==0){
            throw new InvalidInputException("Error at Tasks for Order: No Tasks");
        }
    }

    public void inputValidationInvoice(Order invoice) throws InvalidInputException{
        inputValidationOrder(invoice);
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
            validateText(toValidate.getDescription());
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Description: " + e.getMessage());
            throw new InvalidInputException("Error at Task Description: " + e.getMessage());
        }
        try {
            validateText(toValidate.getFinishing());
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Finishing: " + e.getMessage());
            throw new InvalidInputException("Error at Task Finishing: " + e.getMessage());
        }
        try {
            validateText(toValidate.getWood_type());
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Wood Type: " + e.getMessage());
            throw new InvalidInputException("Error at Task Wood Type: " + e.getMessage());
        }
        try {
            validateText(toValidate.getQuality());
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
            validateText(toValidate.getDescription());
        }catch(EmptyInputException e) {
            LOG.error("Error at Description: " + e.getMessage());
            throw new InvalidInputException("Error at Description: " + e.getMessage());
        }
        try {
            validateText(toValidate.getFinishing());
        }catch(EmptyInputException e) {
            LOG.error("Error at Finishing: " + e.getMessage());
            throw new InvalidInputException("Error at Finishing: " + e.getMessage());
        }
        try {
            validateText(toValidate.getWood_type());
        }catch(EmptyInputException e) {
            LOG.error("Error at Wood Type: " + e.getMessage());
            throw new InvalidInputException("Error at Wood Type: " + e.getMessage());
        }
        try {
            validateText(toValidate.getQuality());
        }catch(EmptyInputException e) {
            LOG.error("Error at Quality: " + e.getMessage());
            throw new InvalidInputException("Error at Quality: " + e.getMessage());
        }
        int validatedSize;
        try {
            validatedSize = validateNumber(toValidate.getSize());
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Size: " + e.getMessage());
            throw new InvalidInputException("Error at Size: " + e.getMessage());
        }
        int validatedWidth;
        try {
            validatedWidth = validateNumber(toValidate.getWidth());
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Width: " + e.getMessage());
            throw new InvalidInputException("Error at Width: " + e.getMessage());
        }
        int validatedLength;
        try {
            validatedLength = validateNumber(toValidate.getLength());
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Length: " + e.getMessage());
            throw new InvalidInputException("Error at Length: " + e.getMessage());
        }
        int validatedQuantity;
        try {
            validatedQuantity = validateNumber(toValidate.getQuantity());
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Quantity: " + e.getMessage());
            throw new InvalidInputException("Error at Quantity: " + e.getMessage());
        }
        int validatedPrice;
        try {
            validatedPrice = validateNumber(toValidate.getPrice());
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Price: " + e.getMessage());
            throw new InvalidInputException("Error at Price: " + e.getMessage());
        }
        Task validated = new Task(-1,-1,toValidate.getDescription(),toValidate.getFinishing(),toValidate.getWood_type(),toValidate.getQuality(),validatedSize,validatedWidth,validatedLength,validatedQuantity,0,false,validatedPrice);
        return validated;
    }

    private int validateNumber(String toValidate) throws NoValidIntegerException {
        int num = -1;
        if(toValidate.length() == 0 || toValidate == null) {
            throw new NoValidIntegerException("Empty Field, No Number entered");
        }
        try {
            num = Integer.parseInt(toValidate);
            if (num <= 0) {
                throw new NoValidIntegerException("Negative Integer or Null entered");
            }
        } catch (NumberFormatException e) {
            LOG.error("No valid Integer entered");
            throw new NoValidIntegerException("No valid Integer entered");
        }
        return num;
    }

    private void validateText(String toValidate) throws EmptyInputException {
        if(toValidate == null || toValidate.length() == 0) {
            throw new EmptyInputException("Empty Field");
        }
    }
}
