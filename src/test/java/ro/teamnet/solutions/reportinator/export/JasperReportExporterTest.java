package ro.teamnet.solutions.reportinator.export;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;
import ro.teamnet.solutions.reportinator.convert.jasper.BeanCollectionJasperDataSourceConverter;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;
import ro.teamnet.solutions.reportinator.generation.JasperReportGenerator;
import ro.teamnet.solutions.reportinator.generation.ReportGenerator;
import ro.teamnet.solutions.reportinator.load.JasperDesignLoader;
import ro.teamnet.solutions.reportinator.utils.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class JasperReportExporterTest {

    private static final String JRXML_PATH = JasperConstantsTest.JRXML_BLANK_PORTRAIT_TEMPLATE_PATH;
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
        this.reportPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport((JasperDesign) JasperDesignLoader.load(new File(JasperConstantsTest.JRXML_BLANK_PORTRAIT_TEMPLATE_PATH))), reportParameters);


        employees = new ArrayList<>();
        employees.add(new Employee(1, "Bogdan", "Iancu", 1000, "Solutii", "home" , "developer", 8, 0 ));
        employees.add(new Employee(2, "Cristi", "Dumitru", 1000, "Solutii", "home" , "developer", 8, 0));
        employees.add(new Employee(3, "Oana", "Popescu", 1000, "Solutii", "home" , "developer", 8, 0));
        employees.add(new Employee(4, "Alex", "Cojocaru", 1000, "Solutii", "home" , "developer", 8, 0));
        employees.add(new Employee(5, "Mihaela", "Scripcaru", 1000, "Solutii", "home" , "developer", 8, 0));
        employees.add(new Employee(6, "Sad", "Panda", 1000000, "Management", "mansion" , "BOSS", 1, 100));

        fields = new LinkedHashMap<>();
        fields.put("id", "Id");
        fields.put("firstName", "Prenume");
        fields.put("lastName", "Nume");
        fields.put("department", "Departament");
        fields.put("salary", "Salariu");
        //fields.put("address", "Adresa");
        //fields.put("position", "Functie");
        //fields.put("hoursPerDay", "Ore pe zi");
        //fields.put("yearsOfExperience", "Ani de experienta");

        dataSource = new BeanCollectionJasperDataSourceConverter<Employee>(fields.keySet()).convert(employees);
        parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Employees");

    }


    @Test(expected = ExporterException.class)
    public void testUsingPrintForExportShouldPassIfParametersAreNull() throws Exception {
        JasperPrint print = null;
        JasperReportExporter.export(print, null, null);
    }

    @Test(expected = ExporterException.class)
    public void testUsingGeneratorForExportShouldPassIfParametersAreNull() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator = null;
        JasperReportExporter.export(reportGenerator, null, null); // Ignore warning 'is always null'
    }

    @Test
    public void testShouldExportAFile() throws Exception {
        out = new FileOutputStream("testReportExporter.pdf");
        //TODO Atentie!!! TableColumnsMetadata trebuie sa fie inainte de DataSource
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder(JRXML_PATH)
                        .withTableColumnsMetadata(fields)
                        .withDatasource(this.dataSource)
                        .withParameters(parameters)
                        .build();
        JasperPrint print = reportGenerator.generate();
        JasperReportExporter.export(print, out, ExportType.PDF);
        path = Paths.get(".\\testReportExporter.pdf");
        assertTrue(Files.exists(path));
        out.close();
        //Files.delete(path);
    }


}
