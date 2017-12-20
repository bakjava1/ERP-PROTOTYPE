package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.util.List;

public interface OrderDAO {

    /**
     * 1.1.2 Bestellung mit allen relevanten Daten + eindeutigem Schlüssel (id) erstellen.
     * @param order order to be created
     * @throws PersistenceLayerException if an error with the database occured (e.g. SQLException)
     * @Invariant order got validated
     */
    void createOrder(Order order) throws PersistenceLayerException;

    /**
     * 1.2.2 Bestellung löschen.
     * @param order order to be deleted
     * @throws PersistenceLayerException if the database is not available
     */
    void deleteOrder(Order order) throws PersistenceLayerException;

    /**
     * 1.3.1 Alle offenen Bestellungen anfordern.
     * @return
     */
    List<Order> getAllOpen() throws PersistenceLayerException;

    /**
     * 1.4.2 Abzurechnende Bestellung bearbeiten (Abgerechnet Flag setzten, zusätzliche Werte speichern)
     */
    void updateOrder(Order order) throws PersistenceLayerException;

    /**
     * 1.5.1 Alle geschlossenen Bestellungen == Rechnugen anfordern.
     * @return
     */
    List<Order> getAllClosed() throws PersistenceLayerException;

    /**
     * 1.6.2 Rechnungsdetails (Geschlossene Bestellung) anfordern.
     */
    Order getOrderById(int order_id) throws PersistenceLayerException;

    void invoiceOrder(Order order) throws PersistenceLayerException;
}
