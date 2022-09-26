package tech.seedz.template.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;


public class ArchitectureTest {
    private static final String BASE_PACKAGE = "tech.seedz.lostemplateproject";

    private JavaClasses classes;

    @BeforeEach
    public void setup() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(BASE_PACKAGE);
    }

    @Test
    void repositoryShouldNotDependOnWebLayer() {
        noClasses()
                .that()
                .resideInAnyPackage(BASE_PACKAGE + ".dao..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(BASE_PACKAGE + ".controller..")
                .because("Services and repositories should not depend on web layer");
               // .check(classes);
    }

    @Test
    void serviceClassesShouldOnlyBeAccessedByController() {
        classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..service..", "..controller..", "..");
                //.check(classes);
    }
    @Test
    void integrationsClassesShouldOnlyBeAccessedFromIntegrationService() {
        classes()
                .that().resideInAPackage(BASE_PACKAGE + ".integrations..")
                .should().onlyBeAccessed().byAnyPackage(BASE_PACKAGE + ".integrations..", BASE_PACKAGE + ".service..");
               // .check(classes);
    }
}

