package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;

import java.util.List;

public interface OrderController {

    /**
     * 1.1.2 Bestellung mit allen relevanten Daten + eindeutigem Schlüssel (id) erstellen.
     */
    void createOrder(OrderDTO orderDTO) throws PersistenceLayerException;

    /**
     * 1.2.2 Bestellung löschen.
     */
    void deleteOrder(OrderDTO orderDTO) throws PersistenceLayerException;

    /**
     * 1.3.1 Alle offenen Bestellungen anfordern.
     * @return
     */
    List<OrderDTO> getAllOpen() throws PersistenceLayerException;

    /**
     * 1.4.2 Abzurechnende Bestellung bearbeiten (Abgerechnet Flag setzten, zusätzliche Werte speichern)
     */
    void updateOrder(OrderDTO orderDTO) throws PersistenceLayerException;

    /**
     * 1.5.1 Alle geschlossenen Bestellungen == Rechnugen anfordern.
     * @return
     */
    List<OrderDTO> getAllClosed() throws PersistenceLayerException;

    /**
     * 1.6.2 Rechnungsdetails (Geschlossene Bestellung) anfordern.
     */
    OrderDTO getOrderById(int order_id) throws PersistenceLayerException;

    /**
     * takes an ordersDTO and sends it to the server
     * @author Philipp Klein
     * @param orderDTO orderDTO that will be invoiced
     * @throws PersistenceLayerException if selectedOrder cannot be stored because of error in server, or no connection to server
     */
    void invoiceOrder(OrderDTO orderDTO) throws PersistenceLayerException;
}
