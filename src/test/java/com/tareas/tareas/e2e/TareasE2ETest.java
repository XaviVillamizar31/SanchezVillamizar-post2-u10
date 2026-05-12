package com.tareas.tareas.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.assertj.core.api.Assertions.assertThat;

class TareasE2ETest {

    private WebDriver driver;

    @BeforeEach
    void setup() {

        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\WebDriver\\chromedriver.exe"
        );

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void paginaTareas_cargaCorrectamente() {

        driver.get("http://localhost:8081/tareas");

        TareasPage page = new TareasPage(driver);

        assertThat(page.obtenerTitulo())
                .isEqualTo("Gestor de Tareas");

        assertThat(page.contarTareas())
                .isGreaterThan(0);
    }
}