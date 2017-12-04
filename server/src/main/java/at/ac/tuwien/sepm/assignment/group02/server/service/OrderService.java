package at.ac.tuwien.sepm.assignment.group02.server.service;

/**
 * This interface defines all service layer methods needed for order handling.
 */
public interface OrderService {

    /**
     * deletes the corresponding order in the DB
     * @param id selected order id to be deleted
     */
    void deleteOrder(int id);
}
