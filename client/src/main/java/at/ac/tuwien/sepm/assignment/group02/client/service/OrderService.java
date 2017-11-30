package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;

import java.util.List;

/**
 * OrderDTO Management (Bestellungen/Rechnungen)
 */
public interface OrderService {

    /**
     * 1.1 Bestellung anlegen
     * 1.1.2 (rest/OrderController) Bestellung Erstellung mit allen relevanten Daten + eindeutigem Schlüssel (id)
     * 1.1.3 (rest/TaskController) Aufträge Erstellung mit allen relevanten Daten + eindeutigem Schlüssel (id)
     * und Verbindung zu Bestellung (relationale Datenbank)
     * @param order
     */
    void addOrder(Order order, List<Task> tasks);

    /**
     * 1.2 Bestellung löschen
     * 1.2.1 (rest/TaskController) Verknüpfte Aufträge löschen
     * 1.2.2 (rest/OrderController) Bestehende Bestellung löschen
     * @param order
     */
    void deleteOrder(Order order);

    /**
     * 1.3 Übersicht Bestellungen
     * @return
     */
    List<Order> getAllOpen();

    /**
     * 1.4 Abrechnen von Bestellung
     * 1.4.2 (rest/OrderController) Abzurechnende Bestellung bearbeiten (Abgerechnet Flag setzten, zusätzliche Werte speichern)
     * 1.4.3 (rest/LumberController) Schnittholz aus dem Schnittholzlager entfernen.
     * @param order
     */
    void closeOrder(Order order);

    /**
     * 1.5 Übersicht Rechnungen
     * (rest/OrderController)
     * @return
     */
    List<Order> getAllClosed();

    /**
     * 1.6 Detailansicht Rechnungen
     * (rest/OrderController)
     * @param order_id
     * @return
     */
    Order getReceiptById(int order_id);

}
