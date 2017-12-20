package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;

import java.util.List;

public interface OrderService {

    /**
     * 1.1.2 Bestellung mit allen relevanten Daten + eindeutigem Schlüssel (id) erstellen.
     */
    void createOrder(OrderDTO orderDTO) throws ServiceLayerException;

    /**
     * 1.2.2 Bestellung löschen.
     */
    void deleteOrder(OrderDTO orderDTO) throws ServiceLayerException;

    /**
     * 1.3.1 Alle offenen Bestellungen anfordern.
     * @return
     */
    List<OrderDTO> getAllOpen() throws ServiceLayerException;

    /**
     * 1.4.2 Abzurechnende Bestellung bearbeiten (Abgerechnet Flag setzten, zusätzliche Werte speichern)
     */
    void updateOrder(OrderDTO orderDTO) throws ServiceLayerException;

    /**
     * 1.5.1 Alle geschlossenen Bestellungen == Rechnugen anfordern.
     * @return
     */
    List<OrderDTO> getAllClosed() throws ServiceLayerException;

    /**
     * 1.6.2 Rechnungsdetails (Geschlossene Bestellung) anfordern.
     */
    OrderDTO getOrderById(int order_id) throws ServiceLayerException;

    /**
     * takes an orderDTO converts it into an order and invoices it
     * @author Philipp Klein
     * @param orderDTO orderDTO that will be invoiced
     * @throws ServiceLayerException if error when trying to invoice order
     */
    void invoiceOrder(OrderDTO orderDTO) throws ServiceLayerException;
}
