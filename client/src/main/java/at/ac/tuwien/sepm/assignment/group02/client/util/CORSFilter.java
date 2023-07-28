/*package at.ac.tuwien.sepm.assignment.group02.client.util;

/**
 * Created by raquelsima on 15.01.18.

public class CORSFilter extends OncePerRequestFilter{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());



    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {

        LOG.info("Adding CORS Headers ........................");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Max-Age", "3600");
        chain.doFilter(req, res);
    }
}
 */