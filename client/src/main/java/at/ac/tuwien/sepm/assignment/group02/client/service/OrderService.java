package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;

import java.util.List;

/**
 * CONVERSION HAPPENS ON THIS LAYER
 * VALIDATION HAPPENS ON THIS LAYER
 */
public interface OrderService {

    /**
     * 1.1 Bestellung anlegen
     * 1.1.2 (rest/OrderController) Bestellung Erstellung mit allen relevanten Daten + eindeutigem Schlüssel (id)
     * 1.1.3 (rest/TaskController) Aufträge Erstellung mit allen relevanten Daten + eindeutigem Schlüssel (id)
     * und Verbindung zu Bestellung (relationale Datenbank)
     * @param order
     */
    void addOrder(Order order, List<Task> tasks) throws InvalidInputException, ServiceLayerException;

    /**
     * 1.2 Bestellung löschen
     * 1.2.1 (rest/TaskController) Verknüpfte Aufträge löschen
     * 1.2.2 (rest/OrderController) Bestehende Bestellung löschen
     * @param order
     */
    void deleteOrder(Order order) throws InvalidInputException, ServiceLayerException;

    /**
     * 1.3 Übersicht Bestellungen
     * @return list of all open order
     * @throws ServiceLayerException is thrown if an error occurs in the client persistence layer
     */
    List<Order> getAllOpen() throws ServiceLayerException;

    /**
     * 1.4 Abrechnen von Bestellung
     * 1.4.2 (rest/OrderController) Abzurechnende Bestellung bearbeiten (Abgerechnet Flag setzten, zusätzliche Werte speichern)
     * 1.4.3 (rest/LumberController) Schnittholz aus dem Schnittholzlager entfernen.
     * @param order
     */
    void closeOrder(Order order) throws InvalidInputException, ServiceLayerException;

    /**
     * 1.5 Übersicht Rechnungen
     * (rest/OrderController)
     * @return list of all closed order
     * @throws ServiceLayerException is thrown if an error occurs in the client persistence layer
     */
    List<Order> getAllClosed() throws ServiceLayerException;

    /**
     * 1.6 Detailansicht Rechnungen
     * (rest/OrderController)
     * @param order_id
     * @return
     */
    Order getReceiptById(int order_id) throws InvalidInputException, ServiceLayerException;

    /**
     * 1.4.2 Erstellen der benoetigten Daten um Bestellung abzurechnen
     * @param selectedOrder
     * @throws InvalidInputException
     */
    void invoiceOrder(Order selectedOrder) throws InvalidInputException, ServiceLayerException;
}
