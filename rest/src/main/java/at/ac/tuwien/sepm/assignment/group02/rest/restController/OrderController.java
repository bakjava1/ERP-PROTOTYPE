package at.ac.tuwien.sepm.assignment.group02.rest.restController;

import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;

import java.util.List;

public interface OrderController {

    /**
     * 1.1.2 Bestellung mit allen relevanten Daten + eindeutigem Schlüssel (id) erstellen.
     */
    void createOrder(OrderDTO orderDTO) throws EntityCreationException;

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
