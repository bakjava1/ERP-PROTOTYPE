package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceDatabaseException;

import java.util.List;

public interface OrderService {

    /**
     * 1.1.2 Bestellung mit allen relevanten Daten + eindeutigem Schlüssel (id) erstellen.
     */
    void createOrder(OrderDTO orderDTO) throws ServiceDatabaseException;

    /**
     * 1.2.2 Bestellung löschen.
     */
    void deleteOrder(OrderDTO orderDTO);

    /**
     * 1.3.1 Alle offenen Bestellungen anfordern.
     * @return
     */
    List<OrderDTO> getAllOpen();

    /**
     * 1.4.2 Abzurechnende Bestellung bearbeiten (Abgerechnet Flag setzten, zusätzliche Werte speichern)
     */
    void updateOrder(OrderDTO orderDTO);

    /**
     * 1.5.1 Alle geschlossenen Bestellungen == Rechnugen anfordern.
     * @return
     */
    List<OrderDTO> getAllClosed();

    /**
     * 1.6.2 Rechnungsdetails (Geschlossene Bestellung) anfordern.
     */
    OrderDTO getOrderById(int order_id);

}
