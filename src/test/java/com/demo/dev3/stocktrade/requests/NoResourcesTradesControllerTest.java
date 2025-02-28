package com.demo.dev3.stocktrade.requests;

import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import com.hackerrank.test.utility.ResultMatcher;
import com.hackerrank.test.utility.TestWatchman;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(OrderedTestRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoResourcesTradesControllerTest {

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
    
    @Rule
    public TestWatcher watchman = TestWatchman.watchman;
    
    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpClass() {
        TestWatchman.watchman.registerClass(NoResourcesTradesControllerTest.class);
    }

    @AfterClass
    public static void tearDownClass() {
        TestWatchman.watchman.createReport(NoResourcesTradesControllerTest.class);
    }

    /**
     *
     * @throws Exception
     *
     * It tests finding trades by non-existing stock symbol and trade type in given date range
     */
    @Test
    @Order(1)
    public void findAllTradesByNonExistingStockSymbolAndTradeTypeInDateRange() throws Exception {
        /**
         *
         * Find all the trades by stock symbol A and trade type buy in given date range 2016-12-28 and 2017-01-03 inclusive
         */
        mockMvc.perform(get("/trades/stocks/A?type=buy&start=2016-12-28&end=2017-01-03"))
                .andExpect(status().isNotFound());
    }

    /**
     *
     * @throws Exception
     *
     * It tests finding all the trades
     */
    @Test
    @Order(2)
    public void findAllTrades() throws Exception {
        /**
         *
         * Find all the trades
         *
         * The request response is:
         * []
         */
        String res = "[]";

        assertTrue(
                ResultMatcher.matchJsonArray(
                        mockMvc.perform(get("/trades"))
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        res,
                        true));
    }

    /**
     *
     * @throws Exception
     *
     * It tests finding all the trades by user id
     */
    @Test
    @Order(3)
    public void findAllTradesByNonExistingUserId() throws Exception {
        /**
         *
         * Find all the trades by non-existing user id 1
         */
        mockMvc.perform(get("/trades/users/1"))
                .andExpect(status().isNotFound());
    }

    /**
     *
     * @throws Exception
     *
     * It tests finding trade
     */
    @Test
    @Order(4)
    public void findTradeByNonExistingId() throws Exception {
        /**
         *
         * Find trade by non-existing id 1
         */
        mockMvc.perform(get("/trades/1"))
                .andExpect(status().isNotFound());
    }

    /**
     *
     * @throws Exception
     *
     * It tests creating trade
     */
    @Test
    @Order(5)
    public void createTrade() throws Exception {
        /**
         *
         * Create trade with id 1
         *
         * The request body is:
         * {
         *     "id": 1,
         *     "type": "buy",
         *     "user": {
         *         "id": 4,
         *         "name": "Derrick Garcia"
         *     },
         *     "symbol": "ZAYO",
         *     "shares": 11,
         *     "price": 154.77,
         *     "timestamp": "2016-12-28 11:44:37"
         * }
         */
        String json = "{\"id\": 1, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 11, \"price\": 154.77, \"timestamp\": \"2016-12-28 11:44:37\"}";

        mockMvc.perform(
                post("/trades")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
        )
                .andExpect(status().isCreated());
    }

    /**
     *
     * @throws Exception
     *
     * It tests creating trade
     */
    @Test
    @Order(6)
    public void createTradeWithExistingId() throws Exception {
        /**
         *
         * Create trade with existing id 1
         *
         * The request body is:
         * {
         *     "id": 1,
         *     "type": "buy",
         *     "user": {
         *         "id": 4,
         *         "name": "Derrick Garcia"
         *     },
         *     "symbol": "ZAYO",
         *     "shares": 11,
         *     "price": 154.77,
         *     "timestamp": "2016-12-28 11:44:37"
         * }
         */
        String json = "{\"id\": 1, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 11, \"price\": 154.77, \"timestamp\": \"2016-12-28 11:44:37\"}";

        mockMvc.perform(
                post("/trades")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
        )
                .andExpect(status().isBadRequest());
    }
}
