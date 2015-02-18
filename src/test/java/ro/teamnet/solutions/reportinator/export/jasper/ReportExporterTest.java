package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstants;
import ro.teamnet.solutions.reportinator.convert.jasper.BeanCollectionJasperDataSourceConverter;
import ro.teamnet.solutions.reportinator.export.ExportType;
import ro.teamnet.solutions.reportinator.export.ExporterException;
import ro.teamnet.solutions.reportinator.generation.ReportGenerator;
import ro.teamnet.solutions.reportinator.generation.jasper.JasperReportGenerator;
import ro.teamnet.solutions.reportinator.load.jasper.DesignLoader;
import ro.teamnet.solutions.reportinator.utils.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ReportExporterTest {

    private static final String JRXML_PATH = JasperConstants.JASPER_TEST_TEMPLATE_RESOURCE_PATH;
    private JasperPrint reportPrint;
    private Map<String, Object> reportParameters;
    private OutputStream out;
    private List<Employee> employees;
    private Map<String, String> fields;
    private JRDataSource dataSource;
    private Map<String, Object> parameters;
    private Path path;





    @Before
    public void setUp() throws Exception {
        reportParameters = new HashMap();
        this.reportPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport((JasperDesign) DesignLoader.load(new File(JasperConstants.JASPER_TEST_TEMPLATE_RESOURCE_PATH))), reportParameters);
        out = new FileOutputStream( "test.pdf");

        employees = new ArrayList<>();
        employees.add(new Employee(1, "Bogdan", "Iancu", 1000, "Solutii"));
        employees.add(new Employee(2, "Cristi", "Dumitru", 1000, "Solutii"));
        employees.add(new Employee(3, "Oana", "Popescu", 1000, "Solutii"));
        employees.add(new Employee(4, "Alex", "Cojocaru", 1000, "Solutii"));
        employees.add(new Employee(5, "Mihaela", "Scripcaru", 1000, "Solutii"));
        employees.add(new Employee(6, "Sad", "Panda", 1000000, "Boss"));

        fields = new LinkedHashMap<>();
        fields.put("id", "Id");
        fields.put("firstName", "Prenume");
        fields.put("lastName", "Nume");
        fields.put("department", "Departament");
        fields.put("salary", "Salariu");

        dataSource = new BeanCollectionJasperDataSourceConverter<Employee>(fields.keySet()).convert(employees);
        parameters = new HashMap<>();
        parameters.put("REPORT_TITLE","Employees");
        path = Paths.get(".\\test.pdf");
    }

    @After
    public void tearDown() throws Exception {
        out.close();
        //Files.delete(path);
    }

    @Test(expected = ExporterException.class)
    public void testShouldPassIfParametersAreNull() throws Exception{
        ReportExporter.export(null, null, null);
    }

    @Test
    public void testShouldExportAFile() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder(JRXML_PATH)
                        .withDatasource(this.dataSource)
                        .withTableColumnsMetadata(fields)
                        .withParameters(parameters)
                        .build();
        JasperPrint print = reportGenerator.generate();
        ReportExporter.export(print, out, ExportType.PDF);
        assertTrue(Files.exists(path));
    }


}
