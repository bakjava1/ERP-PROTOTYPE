package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;

import java.util.List;

public interface OrderController {

    /**
     * 1.1.2 Bestellung mit allen relevanten Daten + eindeutigem Schlüssel (id) erstellen.
     * @param orderDTO order to be created
     * @throws PersistenceLayerException if an error at the server occurs or if the server is unreachable
     * @Invariant order got validated
     */
    void createOrder(OrderDTO orderDTO) throws PersistenceLayerException;

    /**
     * 1.2.2 Bestellung löschen.
     * @param orderDTO order to be deleted
     * @throws PersistenceLayerException if the server is not available
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

    void invoiceOrder(OrderDTO orderDTO) throws PersistenceLayerException;
}
