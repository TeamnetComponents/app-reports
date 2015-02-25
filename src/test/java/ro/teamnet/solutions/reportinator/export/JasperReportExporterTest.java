package ro.teamnet.solutions.reportinator.export;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.After;
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

/**
 * @author Bogdan.Iancu
 * @version 1.0 Date: 2/6/2015
 */

public class JasperReportExporterTest {

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
        employees.add(new Employee(23, "romanian", 1, "Bogdan", "Iancu", 1000, "Solutii", "home" , "developer", 8, 0 ));
        employees.add(new Employee(23, "romanian", 2, "Cristi", "Dumitru", 1000, "Solutii", "home" , "developer", 8, 0));
        employees.add(new Employee(23, "romanian", 3, "Oana", "Popescu", 1000, "Solutii", "home" , "developer", 8, 0));
        employees.add(new Employee(23, "romanian", 4, "Alex", "Cojocaru", 1000, "Solutii", "home" , "developer", 8, 0));
        employees.add(new Employee(23, "romanian", 5, "Mihaela", "Scripcaru", 1000, "Solutii", "home" , "developer", 8, 0));
        employees.add(new Employee(23, "romanian", 6, "Andrei", "Marica", 1000, "Solutii", "home" , "developer", 8, 0));
        employees.add(new Employee(100, "romanian", 7, "Sad", "Panda", 1000000, "Management", "mansion" , "BOSS", 1, 100));

        fields = new LinkedHashMap<>();
        fields.put("age", "Varsta");
        fields.put("nationality","Nationalitate");
        fields.put("id", "Id");
        fields.put("firstName", "Prenume");
        fields.put("lastName", "Nume");
        fields.put("department", "Departament");
        fields.put("salary", "Salariu");
        fields.put("address", "Adresa");
        fields.put("position", "Functie");
        fields.put("hoursPerDay", "Ore pe zi");
        fields.put("yearsOfExperience", "Ani de experienta");

        dataSource = new BeanCollectionJasperDataSourceConverter<Employee>(fields.keySet()).convert(employees);
        parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Employees");

    }

    @After
    public void cleanUp() throws Exception{
        if(out != null)
            out.close();
        path = Paths.get("testReportExporter.pdf");

        if (Files.exists(path) && Files.isWritable(path)) {
            File f = path.toFile();
            f.setWritable(true);
            f.delete();
        }
    }

    @Test(expected = ExporterException.class)
    public void testUsingPrintForExportShouldPassIfPrintOrOutputAreNull() throws Exception {
        JasperPrint print = null; // Specially crafted, to distinguish between exporter types
        JasperReportExporter.export(print, null, ExportType.PDF);
    }

    @Test(expected = ExporterException.class)
    public void testShouldPassIfExportTypeIsNull() throws Exception{
        out = new FileOutputStream("testReportExporter.pdf");
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder()
                        .withDatasource(this.dataSource)
                        .withParameters(parameters)
                        .withTableColumnsMetadata(fields)
                        .build();
        JasperPrint print = reportGenerator.generate();
        JasperReportExporter.export(print, out, null);
    }


    @Test(expected = ExporterException.class)
    public void testUsingGeneratorForExportShouldPassIfParametersAreNull() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator = null;
        JasperReportExporter.export(reportGenerator, null, null); // Ignore warning 'is always null'
    }

    @Test
    public void testShouldExportReportAsAPDFFile() throws Exception {
        out = new FileOutputStream("testReportExporter.pdf");
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder()
                        .withDatasource(this.dataSource)
                        .withParameters(parameters)
                        .withTableColumnsMetadata(fields)
                        .build();
        JasperPrint print = reportGenerator.generate();
        JasperReportExporter.export(print, out, ExportType.PDF);
        path = Paths.get("testReportExporter.pdf");
        assertTrue("PDF file was not created by the exporter.", Files.exists(path));
    }

}
