package tech.example.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;


public class ArchitectureTest {
    private static final String BASE_PACKAGE = "tech.seedz.template";
    private static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller..";
    private static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service..";
    private static final String DAO_PACKAGE = BASE_PACKAGE + ".dao..";
    private static final String INFRA_PACKAGE = BASE_PACKAGE + ".infrastructure..";

    private JavaClasses classes;

    @BeforeEach
    public void setup() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(BASE_PACKAGE);
    }

    @Test
    public void controllerDependsOnService() {
        noClasses()
                .that()
                .resideInAnyPackage(CONTROLLER_PACKAGE)
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(DAO_PACKAGE, INFRA_PACKAGE)
                .check(classes);
    }

    @Test
    public void serviceDependsOnControllerAndDao() {
        noClasses()
                .that()
                .resideInAnyPackage(SERVICE_PACKAGE)
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(INFRA_PACKAGE)
                .check(classes);
    }

    @Test
    public void daoDependsOnNothing() {
        noClasses()
                .that()
                .resideInAnyPackage(DAO_PACKAGE)
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(CONTROLLER_PACKAGE, SERVICE_PACKAGE, INFRA_PACKAGE)
                .check(classes);
    }
    @Test
    public void infraDependsOnService() {
        noClasses()
                .that()
                .resideInAnyPackage(INFRA_PACKAGE)
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(CONTROLLER_PACKAGE, DAO_PACKAGE)
                .check(classes);
    }

}

