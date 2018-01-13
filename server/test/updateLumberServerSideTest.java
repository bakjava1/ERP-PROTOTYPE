import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;

/**
 * Created by raquelsima on 01.01.18.
 */
public class updateLumberServerSideTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

   // private static LumberService lumberService;
    private static RestTemplate restTemplate;
    private static LumberConverter lumberConverter;
    //private static LumberControllerImpl lumberController;


    private static Connection dbConnection;
    private static LumberDAO lumberDAO;
    private static Lumber lumber1 = new Lumber();
    private static Lumber lumber2 = new Lumber();
    private static Lumber lumber3 = new Lumber();

    private static LumberDTO lumberDTO1 = new LumberDTO();
    private static LumberDTO lumberDTO2 = new LumberDTO();

    private static Lumber lumber;
    private static LumberDTO lumberDTO;






}
