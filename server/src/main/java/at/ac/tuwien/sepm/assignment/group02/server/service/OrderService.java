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
     * @return list of all open orders
     * @throws ServiceLayerException if the database is not available for the persistence layer
     */
    List<OrderDTO> getAllOpen() throws ServiceLayerException;

    /**
     * 1.4.2 Abzurechnende Bestellung bearbeiten (Abgerechnet Flag setzten, zusätzliche Werte speichern)
     */
    void updateOrder(OrderDTO orderDTO) throws ServiceLayerException;

    /**
     * 1.5.1 Alle geschlossenen Bestellungen == Rechnugen anfordern.
     * @return list of all closed orders
     * @throws ServiceLayerException if the database is not available for the persistence layer
     */
    List<OrderDTO> getAllClosed() throws ServiceLayerException;

    /**
     * 1.6.2 Rechnungsdetails (Geschlossene Bestellung) anfordern.
     */
    OrderDTO getOrderById(int order_id) throws ServiceLayerException;

    void invoiceOrder(OrderDTO orderDTO) throws ServiceLayerException;
}
