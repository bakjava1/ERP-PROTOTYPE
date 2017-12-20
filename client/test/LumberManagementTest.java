import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;

/**
 * Created by raquelsima on 20.12.17.
 */
public class LumberManagementTest {


    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;
    private static Connection fakeDBConnection;

    //private static LumberDAO lumberDAO;
    //private static LumberDAO lumberDAOMock;

    private static LumberService lumberService;
    private static LumberService lumberServiceMock;
    private static LumberControllerImpl lumberController;

    private static LumberDTO lumberDTONegadtiveAmount=new LumberDTO();

    private static LumberDTO lumberDTO1=new LumberDTO();
    private static LumberDTO lumberDTO2=new LumberDTO();

    private static Lumber lumber1=new Lumber();
    private static Lumber lumber2=new Lumber();


}
