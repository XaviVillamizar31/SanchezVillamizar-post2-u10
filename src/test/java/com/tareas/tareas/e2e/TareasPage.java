package com.tareas.tareas.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TareasPage {

    private final WebDriver driver;

    private final By titulo = By.tagName("h1");

    private final By listItems = By.tagName("li");

    public TareasPage(WebDriver driver) {
        this.driver = driver;
    }

    public String obtenerTitulo() {
        return driver.findElement(titulo).getText();
    }

    public int contarTareas() {
        return driver.findElements(listItems).size();
    }
}