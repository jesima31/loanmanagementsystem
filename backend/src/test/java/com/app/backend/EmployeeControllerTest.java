package com.app.backend;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

// import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.app.backend.communication.request.EmployeeRegisterRequest;
import com.app.backend.communication.response.EmployeeRegisterResponse;
import com.app.backend.model.Employee;
import com.app.backend.model.EmployeeLoan;
import com.app.backend.repository.CategoryRepository;
import com.app.backend.repository.EmployeeLoanRepository;
import com.app.backend.repository.EmployeeRepository;
import com.app.backend.repository.ItemCardRepository;
import com.app.backend.repository.LoanCardRepository;
import com.app.backend.repository.MakeRepository;
import com.app.backend.repository.RoleRepository;
import com.app.backend.repository.UserRepository;
import com.app.backend.service.CategoryService;
import com.app.backend.service.EmployeeLoanService;
import com.app.backend.service.EmployeeService;
import com.app.backend.service.ItemCardService;
import com.app.backend.service.LoanCardService;
import com.app.backend.service.MakeService;
import com.app.backend.service.auth.AuthenticationService;
import com.app.backend.service.auth.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters=false)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtTokenGenerator jwtTokenGenerator;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private EmployeeLoanService employeeLoanService;

    @MockBean
    private ItemCardService itemCardService;

    @MockBean
    private LoanCardService loanCardService;

    @MockBean
    private MakeService makeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private EmployeeLoanRepository employeeLoanRepository;

    @MockBean
    private ItemCardRepository itemCardRepository;

    @MockBean
    private LoanCardRepository loanCardRepository;

    @MockBean
    private MakeRepository makeRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    JdbcTemplate jdbcTemplate;



    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    /**
     * @return
     * @throws Exception
     */
    @Test
    public void register() throws Exception{
        EmployeeRegisterRequest employee = new EmployeeRegisterRequest();
        EmployeeRegisterResponse emplresponse = new EmployeeRegisterResponse();
        UUID num = UUID.fromString("acde070d-8c4c-4f0d-9d8a-162843c10333");
        Date date = new Date(0);
        
        emplresponse.setEmployeeID(num);
        employee.setEmail("myEmail@gmail.com");
        employee.setGender("myGender");
        employee.setDepartment("myDepartment");
        employee.setDesignation("myDesignation");
        employee.setName("myName");
        employee.setDepartment("myPassword@123");
        employee.setDob(date);
        employee.setDoj(date);

        // employee.setDob(Date.now());
        // employee.setName("Myname");
        // employee.setDepartment("MyDpt");
        // employee.setDesignation("MyDesignation");

        Mockito.when(employeeService.register(ArgumentMatchers.any())).thenReturn(emplresponse);
        String json = mapper.writeValueAsString(employee);
        String resp = mapper.writeValueAsString(emplresponse);
        MvcResult requestResult = mvc.perform(post("/api/employee/register")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(json))
        .andExpect(status().isCreated())
        .andReturn();

        String result = requestResult.getResponse().getContentAsString();
        System.out.print(result);
        assertEquals(resp, result);

    }

    @Test
    public void get() throws Exception {
        Employee employee = new Employee();
        UUID num = UUID.fromString("acde070d-8c4c-4f0d-9d8a-162843c10333");
        Date date = new Date(0);

        employee.setId(num);
        // employee.setLoans(null);
        employee.setDepartment(null);
        employee.setName("name");
        employee.setDesignation("designation");
        employee.setDob(date);
        employee.setDoj(date);
        employee.setGender("female");

        List<Employee> list = new ArrayList<>();
        list.add(employee);
        Mockito.when(employeeService.get()).thenReturn(list);
      //   String string = "Updated Successfully";
        mvc.perform(get("/api/employee")
        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());
            // .andExpect(content().string(id));
    }
}