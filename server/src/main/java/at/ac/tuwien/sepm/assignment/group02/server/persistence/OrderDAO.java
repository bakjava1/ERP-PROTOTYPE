package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;

import java.util.List;

public interface OrderDAO {

    /**
     * 1.1.2 Bestellung mit allen relevanten Daten + eindeutigem Schlüssel (id) erstellen.
     */
    void createOrder(Order order) throws PersistenceLevelException;

    /**
     * 1.2.2 Bestellung löschen.
     */
    void deleteOrder(Order order) throws PersistenceLevelException;

    /**
     * 1.3.1 Alle offenen Bestellungen anfordern.
     * @return
     */
    List<Order> getAllOpen() throws PersistenceLevelException;

    /**
     * 1.4.2 Abzurechnende Bestellung bearbeiten (Abgerechnet Flag setzten, zusätzliche Werte speichern)
     */
    void updateOrder(Order order) throws PersistenceLevelException;

    /**
     * 1.5.1 Alle geschlossenen Bestellungen == Rechnugen anfordern.
     * @return
     */
    List<Order> getAllClosed() throws PersistenceLevelException;

    /**
     * 1.6.2 Rechnungsdetails (Geschlossene Bestellung) anfordern.
     */
    Order getOrderById(int order_id) throws PersistenceLevelException;

}
